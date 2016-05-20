package com.unicorn.system.web.controller;

import com.mysema.query.types.expr.BooleanExpression;
import com.unicorn.core.query.PageInfo;
import com.unicorn.core.query.QueryInfo;
import com.unicorn.system.domain.po.QRole;
import com.unicorn.system.domain.po.Role;
import com.unicorn.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/role")
public class RoleController extends BaseController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Role> list(PageInfo pageInfo, String keyword) {

        QRole role = QRole.role;
        BooleanExpression expression = role.isNotNull();
        if (!StringUtils.isEmpty(keyword)) {
            expression = expression.and(role.name.containsIgnoreCase(keyword).or(role.tag.containsIgnoreCase(keyword)));
        }
        QueryInfo queryInfo = new QueryInfo(expression, pageInfo, new Sort(Sort.Direction.ASC, "name"));
        return roleService.getRole(queryInfo);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.GET)
    public Role get(@PathVariable String objectId) {

        return roleService.getRole(objectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Role create(@RequestBody Role role) {

        roleService.saveRole(role);
        return role;
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.PUT)
    public void update(@RequestBody Role role, @PathVariable String objectId) {

        roleService.saveRole(role);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("objectId") String objectId) {

        roleService.deleteRole(objectId);
    }


    @RequestMapping(value = "/{objectId}/menu", method = RequestMethod.GET)
    public List getRoleMenu(@PathVariable("objectId") String objectId) {

        return roleService.getRoleMenuList(objectId);
    }

    @RequestMapping(value = "/{objectId}/menu", method = RequestMethod.POST)
    public void saveRoleMenu(@PathVariable("objectId") String objectId, @RequestBody String[] menus) {

        roleService.saveRoleMenu(objectId, menus);
    }
}
