package com.unicorn.system.web.controller;

import com.mysema.query.types.Predicate;
import com.unicorn.core.query.QueryInfo;
import com.unicorn.system.domain.po.QRole;
import com.unicorn.system.domain.po.Role;
import com.unicorn.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<Role> list(
            String keyword,
            @RequestParam(defaultValue = DEFAULT_PAGE_NO) int pageNo,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {

        QRole role = QRole.role;
        Predicate predicate = null;
        if (!StringUtils.isEmpty(keyword)) {
            predicate = role.name.containsIgnoreCase(keyword).or(role.tag.containsIgnoreCase(keyword));
        }
        Pageable pageable = new PageRequest(pageNo - 1, pageSize, new Sort(Sort.Direction.ASC, "name"));
        QueryInfo queryInfo = new QueryInfo(predicate, pageable);

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
