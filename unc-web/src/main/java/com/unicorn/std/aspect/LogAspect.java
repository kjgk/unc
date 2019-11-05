package com.unicorn.std.aspect;

import com.unicorn.core.annotation.ControllerLog;
import com.unicorn.std.domain.po.OperateLog;
import com.unicorn.std.service.OperateLogService;
import com.unicorn.system.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class LogAspect {

    private OperateLogService logService;

    private UserService userService;

    @Pointcut("@annotation(com.unicorn.core.annotation.ControllerLog)")
    public void controllerAspect() {
    }

    @Around("controllerAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(ControllerLog.class).description();
                    break;
                }
            }
        }

        OperateLog operateLog = new OperateLog();
        operateLog.setName(description);
        operateLog.setOperator(userService.getCurrentUser());
        operateLog.setServletPath(request.getServletPath());
        operateLog.setQueryString(request.getQueryString());
        operateLog.setMethod(request.getMethod());
        operateLog.setClientIp(getRealIp(request));
        operateLog.setStatus(0);
        OperateLog current = logService.createOperateLog(operateLog);

        Integer status = 1;
        String errorMessage = null;
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            status = 4;
            errorMessage = throwable.getMessage();
            return null;
        } finally {
            logService.updateOperateLog(current.getObjectId(), status, errorMessage);
        }
    }

    private static String getRealIp(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
