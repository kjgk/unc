package com.unicorn.sms.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.unicorn.core.exception.ServiceException;
import com.unicorn.sms.config.MiaodiConfigurationProperties;
import com.unicorn.utils.Identities;
import com.unicorn.utils.Md5Utils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

@Component
@Slf4j
@AllArgsConstructor
public class MiaodiService {

    private StringRedisTemplate redisTemplate;

    private MiaodiConfigurationProperties configurationProperties;

    public void sendVerifyCode(String phoneNo, String category) {

        if (StringUtils.isEmpty(phoneNo) || !Pattern.matches("^1[3456789][0-9]{9}$", phoneNo)) {
            throw new ServiceException("手机号码不合法！");
        }

        if (StringUtils.isEmpty(category)) {
            throw new ServiceException("category 不能为空！");
        }

        String code = Long.toString(Identities.randomLong()).substring(0, 6);

        // 将验证码保存到redis
        String key = getRedisKey(phoneNo, category);
        redisTemplate.opsForValue().set(key, code, 10, TimeUnit.MINUTES);

        // 发送
        sendMessage(phoneNo, configurationProperties.getTemplateId(), code);
    }

    public String getVerifyCode(String phoneNo, String category) {

        return redisTemplate.opsForValue().get(getRedisKey(phoneNo, category));
    }

    public void removeVerifyCode(String phoneNo, String category) {

        redisTemplate.delete(getRedisKey(phoneNo, category));
    }


    public void sendMessage(String phoneNo, String templateId, String... params) {

        sendMessage(new String[]{phoneNo}, templateId, params);
    }

    public void sendMessage(String[] phoneNumbers, String templateId, String... params) {

        OkHttpClient okHttpClient = new OkHttpClient();
        String timestamp = System.currentTimeMillis() + "";
        String sign = Md5Utils.encrypt(configurationProperties.getAccountSid() + configurationProperties.getAuthToken() + timestamp);
        String paramsText = "";
        for (String param : params) {
            paramsText += (StringUtils.isEmpty(paramsText) ? "" : ",") + param;
        }
        String to = "";
        for (String phoneNo : phoneNumbers) {
            to += (StringUtils.isEmpty(to) ? "" : ",") + phoneNo;
        }

        try {
            Request request = new Request.Builder()
                    .url(configurationProperties.getUrl())
                    .post(RequestBody.create(MediaType.get("application/x-www-form-urlencoded"),
                            "accountSid=" + configurationProperties.getAccountSid()
                                    + "&templateid=" + templateId
                                    + "&param=" + URLEncoder.encode(paramsText, "utf-8")
                                    + "&to=" + to
                                    + "&timestamp=" + timestamp
                                    + "&sig=" + sign))
                    .header("Content-Type", "application/x-www-form-urlencoded").build();
            Response response = okHttpClient.newCall(request).execute();
            String result = response.body().string();
            JSONObject jsonObject = JSON.parseObject(result);
            if (!"0000".equals(jsonObject.getString("respCode"))) {
                String message = "短信发送失败：" + jsonObject.getString("respDesc");
                log.error(message);
                throw new ServiceException(message);
            }
        } catch (IOException e) {
            String message = "短信发送失败：" + e.getMessage();
            log.error(message);
            throw new ServiceException(message);
        }
    }

    private String getRedisKey(String phoneNo, String category) {

        return ("sms_verification:" + phoneNo + ":" + category).toLowerCase();
    }
}
