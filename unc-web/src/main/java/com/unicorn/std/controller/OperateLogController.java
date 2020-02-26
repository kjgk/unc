package com.unicorn.std.controller;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.unicorn.core.query.PageInfo;
import com.unicorn.core.query.QueryInfo;
import com.unicorn.std.domain.po.OperateLog;
import com.unicorn.std.domain.po.QOperateLog;
import com.unicorn.std.service.OperateLogService;
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
@RequestMapping(API_V1 + "/system/operate-log")
@AllArgsConstructor
@Secured("ROLE_ADMIN")
public class OperateLogController {

    private OperateLogService operateLogService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<OperateLog> list(PageInfo pageInfo, String keyword) {

        QOperateLog operateLog = QOperateLog.operateLog;

        BooleanExpression expression = operateLog.isNotNull();
        if (!StringUtils.isEmpty(keyword)) {
            expression = expression.and(
                    operateLog.name.containsIgnoreCase(keyword)
                            .or(operateLog.operator.name.containsIgnoreCase(keyword))
                            .or(operateLog.servletPath.containsIgnoreCase(keyword))
            );
        }
        QueryInfo queryInfo = new QueryInfo(expression, pageInfo, Sort.by(Sort.Direction.DESC, "createdDate"));
        return operateLogService.getOperateLog(queryInfo);
    }
}
