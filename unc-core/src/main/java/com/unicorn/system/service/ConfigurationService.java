package com.unicorn.system.service;

import com.alibaba.fastjson.JSON;
import com.unicorn.system.domain.po.Configuration;
import com.unicorn.system.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ConfigurationService {

    @Autowired
    private ConfigurationRepository configurationRepository;

    public Configuration getConfiguration(String key) {

        return configurationRepository.findByKey(key);
    }

    public void saveConfiguration(Configuration configuration) {

        Configuration temp = getConfiguration(configuration.getKey());
        if (temp == null) {
            configurationRepository.save(configuration);
        } else {
            temp.setValue(configuration.getValue());
            configurationRepository.save(temp);
        }
    }

    public <T> T getConfiguration(String key, Class<T> configurationType) {

        Configuration configuration = getConfiguration(key);
        return JSON.parseObject(configuration.getValue(), configurationType);
    }
}
