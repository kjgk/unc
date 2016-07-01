package com.unicorn.system.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentService {

    @Value("${temp.path}")
    private String tempPath;

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${exploded.path}")
    private String explodedPath;

    @Value("${webSocket.port}")
    private Integer webSocketPort;

    public String getTempPath() {
        return tempPath;
    }

    public String getExplodedPath() {
        return explodedPath;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public Integer getWebSocketPort() {
        return webSocketPort;
    }
}
