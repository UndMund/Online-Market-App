package org.example.service.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class NotVoidMethodsAspects {
    @Pointcut("within(org.example.service.*Service)")
    public void isServiceClass() {
    }

    @Pointcut("execution(public * *(*)) && isServiceClass()")
    public void isNotVoidMethod() {
    }

    @Before(value = "isNotVoidMethod() " +
                    "&& target(service)")
    public void logBefore(JoinPoint joinPoint,
                                       Object service) {
        var methodName = joinPoint.getSignature().getName();
        log.info("Invoke {} method in class: {}, with args: {}", methodName, service, joinPoint.getArgs());
    }

    @AfterReturning(value = "isNotVoidMethod()" +
                            "&& target(service) ", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint,
                                  Object service,
                                  Object result) {
        String methodName = joinPoint.getSignature().getName();
        log.info("After returning {} method in class {}, with result {}", methodName, service, result);
    }

    @AfterThrowing(value = "isNotVoidMethod()" +
                           "&& target(controller)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint,
                                  Object controller,
                                 Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Exception occurred during {} method in class {}: {}, caused by {}", methodName, controller, exception.getMessage(), exception.getCause());
    }
}
