package com.unicorn.std.service;

import com.unicorn.core.query.QueryInfo;
import com.unicorn.std.domain.po.SecurityLog;
import com.unicorn.std.repository.SecurityLogRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@AllArgsConstructor
public class SecurityLogService {

    private SecurityLogRepository securityLogRepository;

    public Page<SecurityLog> getSecurityLog(QueryInfo queryInfo) {

        return securityLogRepository.findAll(queryInfo);
    }

    public void saveLogin(String account, String ip, String source) {

        SecurityLog securityLog = new SecurityLog();
        securityLog.setAccount(account);
        securityLog.setClientIp(ip);
        securityLog.setSource(source);
        securityLog.setCategory(1);
        securityLogRepository.save(securityLog);
    }

    public void saveLogout() {
    }
}
