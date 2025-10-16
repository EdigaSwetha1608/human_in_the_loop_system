package com.example.human_in_the_loop_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.human_in_the_loop_system.model.EventLog;

public interface EventLogRepository extends JpaRepository<EventLog, Long> {
    // You may wish to query logs by workflow instance or event type
}
