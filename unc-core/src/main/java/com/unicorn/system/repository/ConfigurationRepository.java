package com.unicorn.system.repository;

import com.unicorn.core.repository.BaseRepository;
import com.unicorn.system.domain.po.Configuration;

public interface ConfigurationRepository extends BaseRepository<Configuration> {

    Configuration findByKey(String key);
}
