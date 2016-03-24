package com.unicorn.system.web.controller;

import com.mysema.query.types.Predicate;
import com.unicorn.core.exception.ServiceException;
import com.unicorn.core.query.QueryInfo;
import com.unicorn.system.domain.po.Account;
import com.unicorn.system.domain.po.QUser;
import com.unicorn.system.domain.po.User;
import com.unicorn.system.service.AccountService;
import com.unicorn.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/system/user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<User> list(
            String keyword,
            @RequestParam(defaultValue = DEFAULT_PAGE_NO) int pageNo,
            @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize) {

        QUser user = QUser.user;
        Predicate predicate = null;
        if (!StringUtils.isEmpty(keyword)) {
            predicate = user.name.containsIgnoreCase(keyword)
                    .or(user.userRoleList.any().role.name.containsIgnoreCase(keyword));
        }
        Pageable pageable = new PageRequest(pageNo - 1, pageSize, new Sort(Sort.Direction.ASC, "name"));
        QueryInfo queryInfo = new QueryInfo(predicate, pageable);

        return userService.getUser(queryInfo);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.GET)
    public User get(@PathVariable String objectId) {

        return userService.getUser(objectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public User create(@RequestBody User user) {

        userService.saveUser(user);
        return user;
    }

    @RequestMapping(value = "{objectId}", method = RequestMethod.PUT)
    public void update(@RequestBody User user, @PathVariable String objectId) {

        userService.saveUser(user);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("objectId") String objectId) {
        userService.deleteUser(objectId);
    }

    /**
     * ***************** 帐号 *******************
     */
    @RequestMapping(value = "/{objectId}/account", method = RequestMethod.PUT)
    public void saveAccount(@RequestBody Map data, @PathVariable String objectId) throws ServiceException {

        User user = userService.getUser(objectId);
        Account account = new Account();
        account.setName((String) data.get("name"));
        account.setPassword((String) data.get("password"));
        account.setUser(user);
        accountService.saveAccount(account);
    }

    /******************** 菜单 ********************/
    @RequestMapping(value = "/{objectId}/menu", method = RequestMethod.GET)
    public List<String> getMenus(@PathVariable("objectId") String objectId) {

        return userService.getUserMenus(objectId);
    }
}
