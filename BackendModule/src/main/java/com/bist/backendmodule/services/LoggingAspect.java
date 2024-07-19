package com.bist.backendmodule.services;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Aspect for logging execution of service and repository Spring components.
 */
@Aspect
@Component
public class LoggingAspect {
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    /**
     * Logs a message before the execution of methods in the specified package.
     *
     * @param joinPoint provides reflective access to the state available at a join point
     */
    @Before("execution(* com.bist.backendmodule.modules..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : "Anonymous";

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        LocalDateTime timestamp = LocalDateTime.now();

        logger.info("User: {} accessed {}.{} at {}", username, className, methodName, timestamp);
    }

    /**
     * Logs a message after the successful execution of methods in the specified package.
     *
     * @param joinPoint provides reflective access to the state available at a join point
     * @param result    the result of the method execution
     */
    @AfterReturning(pointcut = "execution(* com.bist.backendmodule.modules..*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication != null ? authentication.getName() : "Anonymous";

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        LocalDateTime timestamp = LocalDateTime.now();

        logger.info("User: {} successfully executed {}.{} at {}", username, className, methodName, timestamp);
    }
}
