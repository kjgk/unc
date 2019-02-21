package com.unicorn.core.domain.po;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.unicorn.core.domain.DefaultNomenclator;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "sys_account")
@EntityListeners({AuditingEntityListener.class})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Account extends DefaultNomenclator {

    @JsonIgnore
    private String password;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * 1: 正常
     * 3：账户被禁用
     * 4：账户被锁定
     */
    private Integer status;
}
