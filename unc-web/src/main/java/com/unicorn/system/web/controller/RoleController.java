package com.unicorn.system.web.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.unicorn.common.domain.vo.BasicInfo;
import com.unicorn.common.query.PageInfo;
import com.unicorn.common.query.QueryInfo;
import com.unicorn.common.domain.po.QRole;
import com.unicorn.common.domain.po.Role;
import com.unicorn.core.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.unicorn.base.web.ApiNamespace.API_V1;

@RestController
@RequestMapping(API_V1 + "/system/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Role> query(PageInfo pageInfo, String keyword) {

        QRole role = QRole.role;
        BooleanExpression expression = role.isNotNull();
        if (!StringUtils.isEmpty(keyword)) {
            expression = expression.and(role.name.containsIgnoreCase(keyword).or(role.tag.containsIgnoreCase(keyword)));
        }
        QueryInfo queryInfo = new QueryInfo(expression, pageInfo, new Sort(Sort.Direction.DESC, "createdDate"));
        return roleService.getRole(queryInfo);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<BasicInfo> list() {

        return roleService.getRole();
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.GET)
    public Role get(@PathVariable Long objectId) {

        return roleService.getRole(objectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Role create(@RequestBody Role role) {

        return roleService.saveRole(role);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.PATCH)
    public void update(@RequestBody Role role, @PathVariable Long objectId) {

        roleService.saveRole(role);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("objectId") Long objectId) {

        roleService.deleteRole(objectId);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@RequestBody List<Long> objectIds) {

        roleService.deleteRole(objectIds);
    }

    @RequestMapping(value = "/{objectId}/menu", method = RequestMethod.GET)
    public List getRoleMenu(@PathVariable("objectId") Long objectId) {

        return roleService.getRoleMenuList(objectId);
    }

    @RequestMapping(value = "/{objectId}/menu", method = RequestMethod.POST)
    public void saveRoleMenu(@PathVariable("objectId") Long objectId, @RequestBody Long[] menus) {

        roleService.saveRoleMenu(objectId, menus);
    }
}
