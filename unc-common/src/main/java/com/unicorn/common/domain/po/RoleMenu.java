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
@Table(name = "sys_rolemenu")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class RoleMenu extends DefaultIdentifiable {

    @OneToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
