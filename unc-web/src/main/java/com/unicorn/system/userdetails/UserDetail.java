package com.unicorn.system.userdetails;


import com.unicorn.core.domain.po.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserDetail extends org.springframework.security.core.userdetails.User {

    private User user;

    public UserDetail(User user, Collection<? extends GrantedAuthority> authorities) {

        super(user.getAccount() == null ? "null" :
                        user.getAccount().getName(),
                user.getAccount() == null ? "******" :
                        user.getAccount().getPassword(), authorities);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
