package com.unicorn.system.security;

import com.unicorn.core.userdetails.UserDetail;
import com.unicorn.system.domain.po.User;
import com.unicorn.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAuditorAware implements AuditorAware<User> {

    @Autowired
    private UserService userService;

    @Override
    public User getCurrentAuditor() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return userDetail.getUser();
        } else {
            return userService.getSystemUser();
        }
    }
}
