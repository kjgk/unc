package com.unicorn.core.userdetails;

import com.unicorn.system.domain.po.Account;
import com.unicorn.system.domain.po.User;
import com.unicorn.system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component(value = "userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {

        Account account = accountService.getAccountByName(name);
        User user = account.getUser();
        List authorities = new ArrayList();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new UserDetail(user, authorities);
    }
}
