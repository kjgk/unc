package com.unicorn.core;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class CaptchaProducer extends DefaultKaptcha {

    public CaptchaProducer() {

        Properties properties = new Properties();
        properties.put(Constants.KAPTCHA_BORDER, "no");
        properties.put(Constants.KAPTCHA_IMAGE_WIDTH, "180");
        properties.put(Constants.KAPTCHA_IMAGE_HEIGHT, "64");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "0123456789abcdefghjkmnpqrstuvwxyz");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        this.setConfig(new Config(properties));
    }
}
