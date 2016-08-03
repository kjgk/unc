package com.unicorn.system.service;

import com.unicorn.core.query.QueryInfo;
import com.unicorn.system.domain.po.Role;
import com.unicorn.system.domain.po.RoleMenu;
import com.unicorn.system.domain.po.User;
import com.unicorn.system.domain.po.UserRole;
import com.unicorn.system.repository.*;
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
    private RoleRepository roleRepository;

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
        if (!StringUtils.isEmpty(user.getObjectId())) {
            userRoleRepository.delete(getUser(user.getObjectId()).getUserRoleList());
        }
        userRepository.save(user);
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

    public List<User> getUserByRoleTag(String roleTag) {

        List<User> userList = new ArrayList();
        Role role = roleRepository.findByTag(roleTag);
        if (role != null) {
            List<UserRole> userRoleList = userRoleRepository.findByRoleId(role.getObjectId());
            for (UserRole userRole : userRoleList) {
                userList.add(getUser(userRole.getUser().getObjectId()));
            }
        }
        return userList;
    }

    public List<String> getUserRoleTags(User user) {

        List<String> roleTags = new ArrayList();
        if (user != null && !CollectionUtils.isEmpty(user.getUserRoleList())) {
            for (UserRole userRole : user.getUserRoleList()) {
                roleTags.add(userRole.getRole().getTag());
            }
        }
        return roleTags;
    }

    public User getAdministrator() {

        return userRepository.findOne("36e6754b82894343919a6b42a1a3216d");
    }

    public User getSystemUser() {

        return userRepository.findOne("ff0d905985564798853e531fe0dc98ac");
    }
}
