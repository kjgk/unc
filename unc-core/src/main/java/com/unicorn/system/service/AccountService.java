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
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.List;

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

    public void modifyPassword(String userId, String newPassword, String originPassword) {

        if (newPassword.equals(originPassword)) {
            throw new ServiceException("新密码不能与旧密码一致!");
        }
        User user = userRepository.findOne(userId);
        if (!passwordEncoder.matches(originPassword, user.getAccount().getPassword())) {
            throw new ServiceException("原始密码不正确!");
        }
        user.getAccount().setPassword(passwordEncoder.encode(newPassword));
        accountRepository.save(user.getAccount());
    }

    public Account getAccountByName(String name) {

        List<Account> accounts = accountRepository.findByName(name);
        if (CollectionUtils.isEmpty(accounts)) {
            return null;
        }
        Account account = accounts.get(0);
        Hibernate.initialize(account.getUser().getUserRoleList());
        for (UserRole userRole : account.getUser().getUserRoleList()) {
            Hibernate.initialize(userRole.getRole().getAuthorityList());
        }
        return account;
    }
}
