package com.unicorn.system.web.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.unicorn.core.query.PageInfo;
import com.unicorn.core.query.QueryInfo;
import com.unicorn.core.userdetails.UserDetail;
import com.unicorn.system.domain.po.Account;
import com.unicorn.system.domain.po.QUser;
import com.unicorn.system.domain.po.User;
import com.unicorn.system.service.AccountService;
import com.unicorn.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.unicorn.base.web.ApiNamespace.API_V1;

@RestController
@RequestMapping(API_V1 + "/system/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<User> list(PageInfo pageInfo, @RequestParam(value = "role", required = false) Long roleId, String keyword) {

        QUser user = QUser.user;
        BooleanExpression expression = user.isNotNull();
        if (!StringUtils.isEmpty(keyword)) {
            expression = expression.and(user.name.containsIgnoreCase(keyword).or(user.userRoleList.any().role.name.containsIgnoreCase(keyword)));
        }
        if (!StringUtils.isEmpty(roleId)) {
            expression = expression.and(user.userRoleList.any().role.objectId.eq(roleId));
        }
        QueryInfo queryInfo = new QueryInfo(expression, pageInfo, new Sort(Sort.Direction.DESC, "createdDate"));
        return userService.getUser(queryInfo);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.GET)
    public User get(@PathVariable Long objectId) {

        return userService.getUser(objectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User create(@RequestBody User user) {

        return userService.saveUser(user);
    }

    @RequestMapping(value = "{objectId}", method = RequestMethod.PATCH)
    public void update(@RequestBody User user, @PathVariable Long objectId) {

        userService.saveUser(user);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("objectId") Long objectId) {
        userService.deleteUser(objectId);
    }

    /**
     * ***************** 帐号 *******************
     */
    @RequestMapping(value = "/{objectId}/account", method = RequestMethod.PUT)
    public void saveAccount(@RequestBody Map data, @PathVariable Long objectId) throws Exception {

        User user = userService.getUser(objectId);
        Account account = new Account();
        account.setName((String) data.get("name"));
        account.setPassword((String) data.get("password"));
        account.setUser(user);
        accountService.saveAccount(account);
    }

    @RequestMapping(value = "/modifyPassword", method = RequestMethod.PUT)
    public void modifyPassword(@RequestBody Map data) {

        UserDetail userDetail = (UserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        accountService.modifyPassword(userDetail.getUser().getObjectId(), (String) data.get("newPassword"), (String) data.get("originPassword"));
    }
}
