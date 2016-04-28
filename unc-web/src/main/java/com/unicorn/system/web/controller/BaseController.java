package com.unicorn.system.web.controller;

import com.unicorn.core.DateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.util.Date;

public class BaseController {

    protected static final String TREE_NODE_ROOT = "Root";

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        binder.registerCustomEditor(Date.class, new DateEditor());
    }
}
