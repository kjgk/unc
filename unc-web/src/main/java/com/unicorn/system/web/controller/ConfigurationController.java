package com.unicorn.system.web.controller;

import com.unicorn.system.domain.po.Configuration;
import com.unicorn.system.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/configuration")
public class ConfigurationController extends BaseController {

    @Autowired
    private ConfigurationService configurationService;

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public Configuration get(@PathVariable String key) {

        return configurationService.getConfiguration(key);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void save(@RequestBody Configuration configuration) {

        configurationService.saveConfiguration(configuration);
    }


}