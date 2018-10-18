package com.unicorn.sms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unicorn.core.exception.ServiceException;
import com.unicorn.sms.config.MiaodiConfigurationProperties;
import com.unicorn.utils.Identities;
import com.unicorn.utils.Md5Utils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class MiaodiService {

    private final static Logger logger = LoggerFactory.getLogger(MiaodiService.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private MiaodiConfigurationProperties configurationProperties;


    public void sendVerifyCode(String phoneNo, String tunnel) {

        String code = Long.toString(Identities.randomLong()).substring(0, 6);

        // 将验证码保存到redis
        String key = getRedisKey(phoneNo, tunnel);
        redisTemplate.opsForValue().set(key, code, 30, TimeUnit.MINUTES);

        OkHttpClient okHttpClient = new OkHttpClient();

        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String sign = Md5Utils.encrypt(configurationProperties.getAccountSid() + configurationProperties.getAuthToken() + timestamp);
        Request request = new Request.Builder()
                .url(configurationProperties.getUrl())
                .post(RequestBody.create(null, "accountSid=" + configurationProperties.getAccountSid() + "&smsContent=" + code + "&templateid=" + configurationProperties.getTemplateId() + "&param=" + code + "&to=" + phoneNo + "&timestamp=" + timestamp + "&sig=" + sign))
                .header("Content-Type", "application/x-www-form-urlencoded").build();

        try {
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            JSONObject jsonObject = JSON.parseObject(result);
            if (!"00000".equals(jsonObject.getString("respCode"))) {
                String message = "短信发送失败：" + jsonObject.getString("respDesc");
                logger.error(message);
                throw new ServiceException(message);
            }
        } catch (IOException e) {
            String message = "短信发送失败：" + e.getMessage();
            logger.error(message);
            throw new ServiceException(message);
        }
    }

    public String getVerifyCode(String phoneNo, String tunnel) {

        return redisTemplate.opsForValue().get(getRedisKey(phoneNo, tunnel));
    }

    public void removeVerifyCode(String phoneNo, String tunnel) {

        redisTemplate.delete(getRedisKey(phoneNo, tunnel));
    }

    private String getRedisKey(String phoneNo, String tunnel) {

        return ("sms_verification:" + phoneNo + ":" + tunnel).toLowerCase();
    }
}
