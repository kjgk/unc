package com.unicorn.system.web.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.unicorn.common.domain.vo.BasicInfo;
import com.unicorn.common.query.PageInfo;
import com.unicorn.common.query.QueryInfo;
import com.unicorn.common.domain.po.Authority;
import com.unicorn.common.domain.po.QAuthority;
import com.unicorn.core.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.unicorn.base.web.ApiNamespace.API_V1;

@RestController
@RequestMapping(API_V1 + "/system/authority")
public class AuthorityController {

    @Autowired
    private AuthorityService authorityService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Authority> list(PageInfo pageInfo, String keyword) {

        QAuthority authority = QAuthority.authority;

        BooleanExpression expression = authority.isNotNull();
        if (!StringUtils.isEmpty(keyword)) {
            expression = expression.and(authority.name.containsIgnoreCase(keyword).or(authority.tag.containsIgnoreCase(keyword)));
        }
        QueryInfo queryInfo = new QueryInfo(expression, pageInfo, new Sort(Sort.Direction.DESC, "createdDate"));
        return authorityService.getAuthority(queryInfo);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<BasicInfo> list() {

        return authorityService.getAuthority();
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.GET)
    public Authority get(@PathVariable Long objectId) {

        return authorityService.getAuthority(objectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Authority create(@RequestBody Authority authority) {

        return authorityService.saveAuthority(authority);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.PATCH)
    public void update(@RequestBody Authority authority, @PathVariable Long objectId) {

        authorityService.saveAuthority(authority);
    }

    @RequestMapping(value = "/{objectId}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("objectId") Long objectId) {

        authorityService.deleteAuthority(objectId);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public void delete(@RequestBody List<Long> objectIds) {

        authorityService.deleteAuthority(objectIds);
    }
}
