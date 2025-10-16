package com.example.human_in_the_loop_system.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.human_in_the_loop_system.model.ApprovalRequest;

public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, Long> {
    // Add custom methods if needed
}
