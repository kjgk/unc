package com.unicorn.common.domain.po;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.unicorn.common.domain.DefaultIdentifiable;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "sys_roleauthority")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class RoleAuthority extends DefaultIdentifiable {

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "authority_id")
    private Authority authority;
}
