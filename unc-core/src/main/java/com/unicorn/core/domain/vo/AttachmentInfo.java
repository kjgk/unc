package com.unicorn.core.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class AttachmentInfo implements Serializable {

    private String filename;

    private String tempFilename;

    private Long attachmentId;

    private String url;

    private String imageUrl;
}
