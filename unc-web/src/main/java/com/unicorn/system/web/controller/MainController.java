package com.unicorn.system.web.controller;

import com.unicorn.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class MainController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/getApplicationInfo", method = RequestMethod.GET)
    public Map getApplicationInfo() {

        Map result = new HashMap();
        result.put("currentUser", userService.getCurrentUser());
        result.put("serverTime", new Date());
        return result;
    }
}
