package com.unicorn.system.web.controller;

import com.unicorn.core.DateEditor;
import com.unicorn.core.userdetails.UserDetail;
import com.unicorn.system.domain.po.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

public class BaseController {

    protected static final String TREE_NODE_ROOT = "Root";

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.registerCustomEditor(Date.class, new DateEditor());
    }

    protected User getCurrentUser() {

        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetail.getUser();
    }
}
