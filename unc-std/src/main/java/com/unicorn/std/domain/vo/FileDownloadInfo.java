package com.unicorn.std.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FileDownloadInfo implements Serializable {

    private String url;

    private String filename;

    public FileDownloadInfo() {
    }

    public FileDownloadInfo(String url, String filename) {
        this.url = url;
        this.filename = filename;
    }

    public static FileDownloadInfo valueOf(String url, String filename) {
        return new FileDownloadInfo(url, filename);
    }
}