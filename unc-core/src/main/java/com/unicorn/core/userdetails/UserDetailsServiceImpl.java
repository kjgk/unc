package com.unicorn.core.userdetails;

import com.unicorn.system.domain.po.Account;
import com.unicorn.system.domain.po.RoleAuthority;
import com.unicorn.system.domain.po.User;
import com.unicorn.system.domain.po.UserRole;
import com.unicorn.system.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
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
        if (account == null) {
            throw new UsernameNotFoundException("用户不存在!");
        }
        if (account.getStatus() != null && account.getStatus() == 4) {
            throw new LockedException("该用户被禁止登录！");
        }
        User user = account.getUser();
        List authorities = new ArrayList();
        for (UserRole userRole : user.getUserRoleList()) {
            for (RoleAuthority roleAuthority : userRole.getRole().getRoleAuthorityList()) {
                authorities.add(new SimpleGrantedAuthority(roleAuthority.getAuthority().getTag()));
            }
        }
        return new UserDetail(user, authorities);
    }
}
