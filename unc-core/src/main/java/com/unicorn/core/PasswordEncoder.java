package com.unicorn.core;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service(value = "passwordEncoder")
public class PasswordEncoder extends BCryptPasswordEncoder {

}
