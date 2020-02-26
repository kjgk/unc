package com.unicorn.core.service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Setter
@ConfigurationProperties(prefix = "environment")
public class EnvironmentService {

    private EnvironmentService.Path path;

    public String getTempPath() {
        return path.getTemp();
    }

    public String getUploadPath() {
        return path.getUpload();
    }

    @Getter
    @Setter
    public static class Path {

        private String temp = "./temp";

        private String upload = "./upload";

        public Path() {
        }
    }

}


