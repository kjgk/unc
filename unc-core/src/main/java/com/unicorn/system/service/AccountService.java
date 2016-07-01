package com.unicorn.system.service;

import com.unicorn.core.PasswordEncoder;
import com.unicorn.core.exception.ServiceException;
import com.unicorn.system.domain.po.Account;
import com.unicorn.system.domain.po.User;
import com.unicorn.system.domain.po.UserRole;
import com.unicorn.system.repository.AccountRepository;
import com.unicorn.system.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveAccount(Account account) throws ServiceException {

        User user = userRepository.findOne(account.getUser().getObjectId());
        Account temp = getAccountByName(account.getName());
        if (temp == null) {

            if (user.getAccount() == null) {
                account.setPassword(passwordEncoder.encode(account.getPassword()));
                accountRepository.save(account);
            } else {
                Account persistent = user.getAccount();
                persistent.setName(account.getName());
                persistent.setPassword(passwordEncoder.encode(account.getPassword()));
                accountRepository.save(persistent);
            }
        } else {
            if (temp.getUser().getObjectId().equals(account.getUser().getObjectId())) {
                temp.setPassword(passwordEncoder.encode(account.getPassword()));
            } else {
                throw new ServiceException("帐号已经存在!");
            }
        }
    }

    public Account getAccountByName(String name) {

        Account account = accountRepository.findByName(name);
        Hibernate.initialize(account.getUser().getUserRoleList());
        for (UserRole userRole : account.getUser().getUserRoleList()) {
            Hibernate.initialize(userRole.getRole().getAuthorityList());
        }
        return account;
    }
}
