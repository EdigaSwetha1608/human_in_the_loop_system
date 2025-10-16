package com.example.human_in_the_loop_system.model;

import java.time.Instant;
import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "approval_requests")
public class ApprovalRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "workflow_instance_id")
    private WorkflowInstance workflowInstance;
    
    @OneToOne(mappedBy = "approvalRequest", cascade = CascadeType.ALL)
    private Feedback feedback;

    @Column(nullable = false)
    private String requestedBy;

    @Column(nullable = false)
    private Instant requestedAt;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus status;

    @Column
    private Instant respondedAt;


    // --- Add these two fields ---
    @Column
    private String approvedBy;

    @Column
    private LocalDateTime approvedAt;

    // --- Add these two setter methods if not using Lombok for these fields ---
    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public void setApprovedAt(LocalDateTime approvedAt) {
        this.approvedAt = approvedAt;
    }

    public enum ApprovalStatus {
        PENDING,
        APPROVED,
        REJECTED,
        TIMEOUT
    }
}


