package com.unicorn.std.domain.po;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unicorn.common.domain.DefaultIdentifiable;
import com.unicorn.std.domain.vo.FileUploadInfo;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "std_contentattachment")
@EntityListeners({AuditingEntityListener.class})
@JsonIdentityInfo(generator = JSOGGenerator.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ContentAttachment extends DefaultIdentifiable {

    private Long relatedId;

    private String relatedType;

    @OneToOne
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    private String category;

    private Integer orderNo;

    @Transient
    private FileUploadInfo fileInfo;
}