package com.example.postgres.service;

import com.example.postgres.model.ServiceTracker;
import com.example.postgres.repository.tracker.TrackerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TrackerService {
/*
    @Autowired
    private TrackerRepository trackerRepository;

   // @Transactional(value = "dbTransactionManager", isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
    public void logServiceTracker(ServiceTracker tracker) {
        this.trackerRepository.save(tracker);
    }*/
}
