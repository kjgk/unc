package com.unicorn.system.config;

import com.unicorn.core.domain.po.User;
import com.unicorn.system.security.UserAuditorAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@Configuration
@EnableJpaAuditing
public class UserAuditorConfigurer {

    @Bean
    public AuditorAware<User> auditorProvider() {

        return new UserAuditorAware();
    }
}
