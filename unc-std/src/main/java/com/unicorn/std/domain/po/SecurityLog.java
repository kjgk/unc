package com.unicorn.std.domain.po;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unicorn.core.domain.DefaultLoggable;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "std_securitylog")
@EntityListeners({AuditingEntityListener.class})
@JsonIdentityInfo(generator = JSOGGenerator.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecurityLog extends DefaultLoggable {

    private String account;

    private String clientIp;

    private String source;

    // 1=login,2=logout
    private Integer category;
}
