package org.example.service.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect
@Slf4j
@Component
public class VoidMethodsAspects {
    @Pointcut("within(org.example.service.*Service)")
    public void isServiceClass() {
    }

    @Pointcut("execution(public void * (*)) && isServiceClass()")
    public void isVoidMethod() {
    }

    @Before(value = "isVoidMethod() " +
                    "&& target(service)")
    public void logBefore(JoinPoint joinPoint,
                          Object service) {
        var methodName = joinPoint.getSignature().getName();
        log.info("Invoke {} void-method in class: {}, with args: {}", methodName, service, joinPoint.getArgs());
    }


    @AfterThrowing(value = "isVoidMethod()" +
                           "&& target(service)", throwing = "exception")
    public void logAfterThrowing(JoinPoint joinPoint,
                                 Object service,
                                 Throwable exception) {
        String methodName = joinPoint.getSignature().getName();
        log.info("Exception occurred during {} void-method in class {}: {}, caused by {}", methodName, service, exception.getMessage(), exception.getCause());
    }
}
