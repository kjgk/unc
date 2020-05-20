package com.unicorn.std.domain.po;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unicorn.core.domain.DefaultLoggable;
import com.unicorn.core.domain.po.User;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "std_operatelog")
@EntityListeners({AuditingEntityListener.class})
@JsonIdentityInfo(generator = JSOGGenerator.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OperateLog extends DefaultLoggable {

    @OneToOne
    @JoinColumn(name = "operator")
    private User operator;

    private String name;

    private String clientIp;

    @Column(columnDefinition = "text")
    private String servletPath;

    @Column(columnDefinition = "text")
    private String queryString;

    private String method;

    private Date completeTime;

    @Column(columnDefinition = "text")
    private String errorMessage;

    // 0=进行中，1=正常返回，4=异常返回
    private Integer status;
}
