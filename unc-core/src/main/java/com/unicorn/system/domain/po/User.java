package com.unicorn.system.domain.po;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.unicorn.core.domain.DefaultNomenclator;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "SYS_USER")
@EntityListeners({AuditingEntityListener.class})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class User extends DefaultNomenclator {

    @OneToOne(mappedBy = "user")
    private Account account;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<UserRole> userRoleList;
}
