package com.example.human_in_the_loop_system.model;


import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "workflow_instances")
public class WorkflowInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String workflowName;

    @Column(nullable = false)
    private String status; // e.g., "PAUSED_FOR_APPROVAL", "COMPLETED", "ROLLBACK"

    @Column(nullable = false)
    private Instant createdAt;

    @OneToMany(mappedBy = "workflowInstance", cascade = CascadeType.ALL)
    private List<ApprovalRequest> approvalRequests;

    @OneToMany(mappedBy = "workflowInstance", cascade = CascadeType.ALL)
    private List<EventLog> eventLogs;
    
    @ManyToOne
    private WorkflowInstance workflowInstance;


	

    // getters and setters
}

