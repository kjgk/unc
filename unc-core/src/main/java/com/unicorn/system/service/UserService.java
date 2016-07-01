package com.unicorn.system.service;

import com.unicorn.core.query.QueryInfo;
import com.unicorn.system.domain.po.RoleMenu;
import com.unicorn.system.domain.po.User;
import com.unicorn.system.domain.po.UserRole;
import com.unicorn.system.repository.AccountRepository;
import com.unicorn.system.repository.RoleMenuRepository;
import com.unicorn.system.repository.UserRepository;
import com.unicorn.system.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleMenuRepository roleMenuRepository;


    public Page<User> getUser(QueryInfo queryInfo) {

        return userRepository.findAll(queryInfo);
    }

    public User getUser(String objectId) {

        return userRepository.findOne(objectId);
    }

    public void saveUser(User user) {

        List<UserRole> userRoleList = user.getUserRoleList();

        if (StringUtils.isEmpty(user.getObjectId())) {
            userRepository.save(user);
        } else {
            User persistent = getUser(user.getObjectId());
            userRoleRepository.delete(persistent.getUserRoleList());
            persistent.merge(user);
        }

        if (!CollectionUtils.isEmpty(userRoleList)) {
            for (UserRole userRole : userRoleList) {
                userRole.setUser(user);
                userRoleRepository.save(userRole);
            }
        }
    }

    public User getCurrentUser() {

        return accountRepository.findByName(SecurityContextHolder.getContext().getAuthentication().getName()).getUser();
    }


    public void deleteUser(String objectId) {

        userRepository.logicDelete(objectId);
    }

    public List<String> getUserMenus(String objectId) {

        List<String> resultList = new ArrayList<>();
        List<UserRole> userRoleList = userRoleRepository.findByUserId(objectId);
        for (UserRole userRole : userRoleList) {
            List<RoleMenu> roleMenuList = roleMenuRepository.findByRoleId(userRole.getRole().getObjectId());
            for (RoleMenu roleMenu : roleMenuList) {
                resultList.add(roleMenu.getMenu().getObjectId());
            }
        }
        return resultList;
    }
}
