package com.example.postgres.repository.tracker;

import com.example.postgres.model.ServiceTracker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrackerRepository extends CrudRepository<ServiceTracker, String> {
}
