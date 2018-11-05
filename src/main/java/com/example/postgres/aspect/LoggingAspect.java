package com.example.postgres.aspect;

import com.example.postgres.model.ServiceTracker;
import com.example.postgres.service.TrackerService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;

import java.util.UUID;


@Aspect
public class LoggingAspect {

    @Autowired
    private TrackerService trackerService;


    @Async
    @Before("execution(* com.example.postgres.controller..*(..))")
    public void logMethod(JoinPoint jp) throws InterruptedException {
        System.out.println(jp.getThis());
        System.out.println(jp.getTarget());
        System.out.println(jp.toString());
        /*Thread.sleep(5000);
        System.out.println("advice sleeping complete");*/
        ServiceTracker tracker = new ServiceTracker();
        tracker.setId(UUID.randomUUID().toString());
        tracker.setMethod(jp.getSignature().toString());
        trackerService.logServiceTracker(tracker);
    }

    @EventListener
    public void started(ContextRefreshedEvent event) {
        System.err.println("Started" + ": " + event);
    }
}
