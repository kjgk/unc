package com.unicorn.core.service;

import com.unicorn.core.PasswordEncoder;
import com.unicorn.core.RSAKeyProducer;
import com.unicorn.common.exception.ServiceException;
import com.unicorn.common.domain.po.Account;
import com.unicorn.common.domain.po.User;
import com.unicorn.common.domain.po.UserRole;
import com.unicorn.core.repository.AccountRepository;
import com.unicorn.core.repository.UserRepository;
import com.unicorn.utils.RSAUtils;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private RSAKeyProducer rsaKeyProducer;

    public void saveAccount(Account account) {

        String passwordTransport = environmentService.getPasswordTransport();
        if (!StringUtils.isEmpty(passwordTransport)) {
            if (environmentService.getPasswordTransport().equalsIgnoreCase("rsa")) {
                String password = null;
                try {
                    password = RSAUtils.decryptByPrivateKey(account.getPassword(), rsaKeyProducer.getPrivateKey());
                } catch (Exception e) {
                    throw new ServiceException(e.getMessage());
                }
                account.setPassword(password);
            }
        }

        User user = userRepository.get(account.getUser().getObjectId());
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
                throw new ServiceException("帐号已经存在!", "E101");
            }
        }
    }

    public void modifyPassword(Long userId, String newPassword, String originPassword) {

        String passwordTransport = environmentService.getPasswordTransport();
        if (!StringUtils.isEmpty(passwordTransport)) {
            if (environmentService.getPasswordTransport().equalsIgnoreCase("rsa")) {
                try {
                    originPassword = RSAUtils.decryptByPrivateKey(originPassword, rsaKeyProducer.getPrivateKey());
                    newPassword = RSAUtils.decryptByPrivateKey(newPassword, rsaKeyProducer.getPrivateKey());
                } catch (Exception e) {
                    throw new ServiceException(e.getMessage());
                }
            }
        }

        User user = userRepository.get(userId);
        if (!passwordEncoder.matches(originPassword, user.getAccount().getPassword())) {
            throw new ServiceException("原始密码不正确!", "E102");
        }
        if (newPassword.equals(originPassword)) {
            throw new ServiceException("新密码不能与旧密码一致!", "E103");
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
            Hibernate.initialize(userRole.getRole().getRoleAuthorityList());
        }
        return account;
    }
}
