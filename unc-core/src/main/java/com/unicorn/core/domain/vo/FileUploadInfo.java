package com.unicorn.core.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FileUploadInfo implements Serializable {

    private String filename;

    private String tempFilename;

    public FileUploadInfo() {
    }

    public FileUploadInfo(String tempFilename, String filename) {
        this.tempFilename = tempFilename;
        this.filename = filename;
    }

    public static FileUploadInfo valueOf(String tempFilename, String filename) {
        return new FileUploadInfo(tempFilename, filename);
    }

}
