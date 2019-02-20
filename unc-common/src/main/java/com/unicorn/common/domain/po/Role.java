package com.unicorn.common.domain.po;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.unicorn.common.domain.DefaultNomenclator;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "sys_role")
@EntityListeners({AuditingEntityListener.class})
@JsonIdentityInfo(generator = JSOGGenerator.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role extends DefaultNomenclator {

    private String tag;

    @OneToMany(mappedBy = "role")
    private List<RoleAuthority> roleAuthorityList;
}
