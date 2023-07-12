package org.example.controller.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class PostMethodsAspects {
    @Pointcut("within(org.example.controller.*Controller)")
    public void isControllerClass() {
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping) && isControllerClass()")
    public void isPostMappingMethod() {
    }

    @Before(value = "isPostMappingMethod() " +
                    "&& target(service)")
    public void logBefore(JoinPoint joinPoint,
                                       Object service) {
        var methodName = joinPoint.getSignature().getName();
        log.info("Invoke {} post-method in class: {}, with args: {}", methodName, service, joinPoint.getArgs());
    }

    @AfterReturning(value = "isPostMappingMethod()" +
                            "&& target(service) ", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint,
                                  Object service,
                                  Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("After returning {} post-method in class {}, with result {}", methodName, service, result);
    }

    @AfterThrowing(value = "isPostMappingMethod()" +
                           "&& target(controller)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint,
                                  Object controller,
                                 Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Exception occurred during {} post-method in class {}: {}, caused by {}", methodName, controller, exception.getMessage(), exception.getCause());
    }
}
