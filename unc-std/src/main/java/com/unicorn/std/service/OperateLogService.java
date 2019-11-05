package com.unicorn.std.service;

import com.unicorn.core.query.QueryInfo;
import com.unicorn.std.domain.po.OperateLog;
import com.unicorn.std.repository.OperateLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
@AllArgsConstructor
public class OperateLogService {

    private OperateLogRepository operateLogRepository;

    public Page<OperateLog> getOperateLog(QueryInfo queryInfo) {

        return operateLogRepository.findAll(queryInfo);
    }

    public OperateLog createOperateLog(OperateLog operateLog) {

        return operateLogRepository.save(operateLog);
    }

    public OperateLog updateOperateLog(Long id, Integer status, String errorMessage) {

        OperateLog operateLog = operateLogRepository.get(id);
        operateLog.setStatus(status);
        operateLog.setErrorMessage(errorMessage);
        operateLog.setCompleteTime(new Date());
        return operateLog;
    }
}
