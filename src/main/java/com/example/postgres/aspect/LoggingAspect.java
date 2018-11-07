package com.example.postgres.aspect;

import com.example.postgres.model.ServiceTracker;
import com.example.postgres.service.TrackerService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Aspect
public class LoggingAspect {

    /*@Autowired
    private TrackerService trackerService;*/


    @Async
    //@Before("execution(* com.example.postgres.controller..*(..))")
    @Before("@within(mytest.sharedaspect.annotation.ServiceTrackerAnnotation)")
    public void logMethod(JoinPoint jp) throws InterruptedException {
        System.out.println("=============LoggingAspect Before Advice=================");
       /* Thread.sleep(5000);
        System.out.println("advice sleeping complete");*/
        /*ServiceTracker tracker = new ServiceTracker();
        tracker.setId(UUID.randomUUID().toString());
        tracker.setMethod(jp.getSignature().toString());
        trackerService.logServiceTracker(tracker);*/
    }

    @Around(value = "@within(mytest.sharedaspect.annotation.ServiceTrackerAnnotation)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("=============LoggingAspect Around Advice=================");
        return joinPoint.proceed();
    }

    @EventListener
    public void started(ContextRefreshedEvent event) {
        System.err.println("LoggingAspect Started" + ": " + event);
    }
}
