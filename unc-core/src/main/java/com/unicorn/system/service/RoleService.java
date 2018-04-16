package com.unicorn.system.service;

import com.unicorn.core.query.QueryInfo;
import com.unicorn.system.domain.po.*;
import com.unicorn.system.repository.RoleAuthorityRepository;
import com.unicorn.system.repository.RoleMenuRepository;
import com.unicorn.system.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
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

    @Autowired
    private RoleAuthorityRepository roleAuthorityRepository;

    public Page<Role> getRole(QueryInfo queryInfo) {

        return roleRepository.findAll(queryInfo);
    }

    public Role getRole(String id) {

        return roleRepository.findOne(id);
    }

    public Role saveRole(Role role) {

        Role current;
        if (StringUtils.isEmpty(role.getObjectId())) {
            current = roleRepository.save(role);
        } else {
            current = roleRepository.findOne(role.getObjectId());
            current.setName(role.getName());
            current.setTag(role.getTag());
            current.setDescription(role.getDescription());
        }

        roleAuthorityRepository.deleteByRoleId(current.getObjectId());

        if (!CollectionUtils.isEmpty(role.getAuthorityList())) {
            for (RoleAuthority roleAuthority : role.getAuthorityList()) {
                roleAuthority.setRole(current);
                roleAuthorityRepository.save(roleAuthority);
            }
        }

        return current;
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
