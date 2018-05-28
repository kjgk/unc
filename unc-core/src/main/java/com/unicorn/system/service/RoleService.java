package com.unicorn.system.service;

import com.unicorn.core.query.QueryInfo;
import com.unicorn.system.domain.po.Menu;
import com.unicorn.system.domain.po.Role;
import com.unicorn.system.domain.po.RoleAuthority;
import com.unicorn.system.domain.po.RoleMenu;
import com.unicorn.system.repository.RoleAuthorityRepository;
import com.unicorn.system.repository.RoleMenuRepository;
import com.unicorn.system.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
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

    public List getRole() {

        return roleRepository.list();
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

            roleAuthorityRepository.delete(current.getRoleAuthorityList());
        }

        if (!CollectionUtils.isEmpty(role.getRoleAuthorityList())) {
            for (RoleAuthority roleAuthority : role.getRoleAuthorityList()) {
                roleAuthority.setRole(current);
                roleAuthorityRepository.save(roleAuthority);
            }
            current.setRoleAuthorityList(role.getRoleAuthorityList());
        }
        return current;
    }

    public void deleteRole(String objectId) {

        roleMenuRepository.deleteByRoleId(objectId);
        roleAuthorityRepository.deleteByRoleId(objectId);
        roleRepository.delete(objectId);
    }

    public void deleteRole(List<String> ids) {

        for (String objectId : ids) {
            deleteRole(objectId);
        }
    }

    public List<String> getRoleMenuList(String objectId) {

        List result = new ArrayList();
        List<RoleMenu> list = roleMenuRepository.findByRoleId(objectId);
        for (RoleMenu roleMenu : list) {
            result.add(roleMenu.getMenu().getObjectId());
        }
        return result;
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
