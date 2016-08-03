package com.unicorn.system.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentService {

    @Value("${environment.path.temp}")
    private String tempPath;

    @Value("${environment.path.upload}")
    private String uploadPath;

    @Value("${environment.path.exploded}")
    private String explodedPath;

    @Value("${environment.webSocket.port}")
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
