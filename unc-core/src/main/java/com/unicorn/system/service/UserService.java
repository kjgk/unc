package com.unicorn.system.service;

import com.unicorn.core.exception.ServiceException;
import com.unicorn.core.query.QueryInfo;
import com.unicorn.core.userdetails.UserDetail;
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
    private UserRoleRepository userRoleRepository;

    @Autowired
    private RoleMenuRepository roleMenuRepository;


    private final String ADMINISTRATOR_USER_ID = "36e6754b82894343919a6b42a1a3216d";

    private final String SYSTEM_USER_ID = "ff0d905985564798853e531fe0dc98ac";


    public Page<User> getUser(QueryInfo queryInfo) {

        return userRepository.findAll(queryInfo);
    }

    public User getUser(String objectId) {

        return userRepository.get(objectId);
    }

    public User saveUser(User user) {

        User current;
        if (StringUtils.isEmpty(user.getObjectId())) {
            current = userRepository.save(user);
        } else {
            current = userRepository.get(user.getObjectId());
            current.setName(user.getName());
            current.setDescription(user.getDescription());
            userRoleRepository.deleteAll(current.getUserRoleList());
        }

        if (!CollectionUtils.isEmpty(user.getUserRoleList())) {
            for (UserRole userRole : user.getUserRoleList()) {
                userRole.setUser(current);
                userRoleRepository.save(userRole);
            }
            current.setUserRoleList(user.getUserRoleList());
        }
        return current;
    }

    public User getCurrentUser() {

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        if (name.equals("anonymousUser")) {
            return null;
        }
        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetail.getUser();
    }

    public void deleteUser(String objectId) {

        if (objectId.equalsIgnoreCase(ADMINISTRATOR_USER_ID) || objectId.equalsIgnoreCase(SYSTEM_USER_ID)) {
            throw new ServiceException("该用户不允许删除！");
        }

        userRepository.logicDelete(objectId);

        accountRepository.deleteByUserId(objectId);
    }

    public List<String> getUserMenus(String objectId) {

        List<String> resultList = new ArrayList<>();
        List<UserRole> userRoleList = userRoleRepository.findByUserId(objectId);
        for (UserRole userRole : userRoleList) {
            List<RoleMenu> roleMenuList = roleMenuRepository.findByRoleId(userRole.getRole().getObjectId());
            for (RoleMenu roleMenu : roleMenuList) {
                if (roleMenu.getMenu().getEnabled() == 1) {
                    resultList.add(roleMenu.getMenu().getObjectId());
                }
            }
        }
        return resultList;
    }

    public User getAdministrator() {

        return userRepository.get(ADMINISTRATOR_USER_ID);
    }

    public User getSystemUser() {

        return userRepository.get(SYSTEM_USER_ID);
    }
}
