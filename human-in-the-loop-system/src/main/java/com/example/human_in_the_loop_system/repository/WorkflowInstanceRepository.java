package com.example.human_in_the_loop_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.human_in_the_loop_system.model.WorkflowInstance;

public interface WorkflowInstanceRepository extends JpaRepository<WorkflowInstance, Long> {
    // You can add custom queries as needed
}

