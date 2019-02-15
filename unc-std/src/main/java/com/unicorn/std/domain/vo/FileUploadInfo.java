package com.unicorn.std.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FileUploadInfo implements Serializable {

    private String filename;

    private String tempFilename;
}
