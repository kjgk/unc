package com.unicorn.system.service;

import com.unicorn.core.query.QueryInfo;
import com.unicorn.system.domain.po.Menu;
import com.unicorn.system.domain.po.Role;
import com.unicorn.system.domain.po.RoleMenu;
import com.unicorn.system.reposiory.RoleMenuRepository;
import com.unicorn.system.reposiory.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleMenuRepository roleMenuRepository;

    public Page<Role> getRole(QueryInfo queryInfo) {

        return roleRepository.findAll(queryInfo);
    }

    public Role getRole(String id) {

        return roleRepository.findOne(id);
    }

    public void saveRole(Role role) {

        if (StringUtils.isEmpty(role.getObjectId())) {
            roleRepository.save(role);
        } else {
            getRole(role.getObjectId()).merge(role);
        }
    }

    public void deleteRole(String objectId) {

        roleRepository.logicDelete(objectId);
    }

    public List<RoleMenu> getRoleMenuList(String objectId) {

        return roleMenuRepository.findByRoleId(objectId);
    }

    public void saveRoleMenu(String objectId, String[] menuList) {

        Role role = getRole(objectId);

        roleMenuRepository.deleteByRoleId(role.getObjectId());

        for (String menuId : menuList) {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRole(role);
            roleMenu.setMenu(new Menu());
            roleMenu.getMenu().setObjectId(menuId);
            roleMenuRepository.save(roleMenu);
        }
    }
}
