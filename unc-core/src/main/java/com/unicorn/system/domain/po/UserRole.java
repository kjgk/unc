package com.unicorn.system.domain.po;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.unicorn.core.domain.DefaultIdentifiable;
import com.voodoodyne.jackson.jsog.JSOGGenerator;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "SYS_USER_ROLE")
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class UserRole extends DefaultIdentifiable {

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToOne
    @JoinColumn(name = "ROLE_ID")
    private Role role;
}
