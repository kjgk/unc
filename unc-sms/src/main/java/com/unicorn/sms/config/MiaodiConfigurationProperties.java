package com.unicorn.sms.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(
        prefix = "sms.miaodi"
)
@Getter
@Setter
public class MiaodiConfigurationProperties {

    private String url = "https://openapi.miaodiyun.com/distributor/sendSMS";

    private String accountSid;

    private String authToken;

    private String templateId;
}
