package com.unicorn.core.config;

import com.unicorn.common.domain.po.User;
import com.unicorn.core.security.UserAuditorAware;
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
