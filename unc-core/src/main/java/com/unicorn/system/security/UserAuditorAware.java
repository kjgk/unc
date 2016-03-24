package com.unicorn.system.security;

import com.unicorn.core.userdetails.UserDetail;
import com.unicorn.system.domain.po.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserAuditorAware implements AuditorAware<User> {

    @Override
    public User getCurrentAuditor() {

        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetail.getUser();
    }
}
