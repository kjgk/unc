package com.unicorn.system.web.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.unicorn.core.domain.po.QRole;
import com.unicorn.core.domain.po.Role;
import com.unicorn.core.domain.vo.BasicInfo;
import com.unicorn.core.query.PageInfo;
import com.unicorn.core.query.QueryInfo;
import com.unicorn.system.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.unicorn.base.web.ApiNamespace.API_V1;

@RestController
@RequestMapping(API_V1 + "/system/role")
@AllArgsConstructor
@Secured("ROLE_ADMIN")
public class RoleController {

    private RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Role> query(PageInfo pageInfo, String keyword) {

        QRole role = QRole.role;
        BooleanExpression expression = role.isNotNull();
        if (!StringUtils.isEmpty(keyword)) {
            expression = expression.and(role.name.containsIgnoreCase(keyword).or(role.tag.containsIgnoreCase(keyword)));
        }
        QueryInfo queryInfo = new QueryInfo(expression, pageInfo, new Sort(Sort.Direction.DESC, "name"));
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
