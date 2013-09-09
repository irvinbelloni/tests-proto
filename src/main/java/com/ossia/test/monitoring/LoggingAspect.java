package com.ossia.test.monitoring;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private final Log log = LogFactory.getLog(LoggingAspect.class);

    @Before ("@annotation (org.springframework.transaction.annotation.Transactional)")
    public void logBeforeExec(JoinPoint jp) {
        log.info(jp.getSignature() + " will be executed");
    }

    @After ("@annotation (org.springframework.transaction.annotation.Transactional)")
    public void logAfterExec(JoinPoint jp) {
        log.info(jp.getSignature() + " has been executed");
    }
}
