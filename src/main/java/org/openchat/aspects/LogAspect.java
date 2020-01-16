package org.openchat.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.util.Arrays;


@Aspect
public class LogAspect {

    @Before("execution(* org.openchat.api.FormatDateService.apply(..))")
    public void logInfo(JoinPoint joinPoint) {
        System.out.println("Log Aspect");
        System.out.println(Arrays.toString(joinPoint.getArgs()));
        System.out.println(joinPoint.getSignature());
    }
}
