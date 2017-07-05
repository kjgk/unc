package com.unicorn.system.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentService {

    @Value("${environment.path.temp}")
    private String tempPath;

    @Value("${environment.path.upload}")
    private String uploadPath;

    public String getTempPath() {
        return tempPath;
    }

    public String getUploadPath() {
        return uploadPath;
    }
}
