package com.unicorn.std.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.unicorn.core.query.PageInfo;
import com.unicorn.core.query.QueryInfo;
import com.unicorn.std.domain.po.QSecurityLog;
import com.unicorn.std.domain.po.SecurityLog;
import com.unicorn.std.service.SecurityLogService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.annotation.Secured;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static com.unicorn.base.web.ApiNamespace.API_V1;

@RestController
@RequestMapping(API_V1 + "/system/security-log")
@AllArgsConstructor
@Secured("ROLE_ADMIN")
public class SecurityLogController {

    private SecurityLogService securityLogService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<SecurityLog> list(PageInfo pageInfo, String keyword) {

        QSecurityLog securityLog = QSecurityLog.securityLog;

        BooleanExpression expression = securityLog.isNotNull();
        if (!StringUtils.isEmpty(keyword)) {
            expression = expression.and(securityLog.account.containsIgnoreCase(keyword).or(securityLog.clientIp.containsIgnoreCase(keyword)));
        }
        QueryInfo queryInfo = new QueryInfo(expression, pageInfo, Sort.by(Sort.Direction.DESC, "createdDate"));
        return securityLogService.getSecurityLog(queryInfo);
    }
}
