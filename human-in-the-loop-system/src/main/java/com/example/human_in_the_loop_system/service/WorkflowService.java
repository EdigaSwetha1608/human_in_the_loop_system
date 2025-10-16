package com.example.human_in_the_loop_system.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.human_in_the_loop_system.model.ApprovalRequest;
import com.example.human_in_the_loop_system.model.EventLog;
import com.example.human_in_the_loop_system.model.Feedback;
import com.example.human_in_the_loop_system.model.WorkflowInstance;
import com.example.human_in_the_loop_system.repository.ApprovalRequestRepository;
import com.example.human_in_the_loop_system.repository.EventLogRepository;
import com.example.human_in_the_loop_system.repository.WorkflowInstanceRepository;

import java.time.Instant;
import java.util.List;


@Service
public class WorkflowService {
    
    private final WorkflowInstanceRepository workflowRepo;
    private final ApprovalRequestRepository approvalRepo;
    private final EventLogRepository eventLogRepo;
	public WorkflowService(WorkflowInstanceRepository workflowRepo,
			ApprovalRequestRepository approvalRequestRepository,
                           ApprovalRequestRepository approvalRepo,
                           EventLogRepository eventLogRepo) {
        this.workflowRepo = workflowRepo;
        this.approvalRepo = approvalRepo;
        this.eventLogRepo = eventLogRepo;
    }

    @Transactional
    public WorkflowInstance startNewWorkflow(String workflowName) {
        WorkflowInstance workflow = new WorkflowInstance();
        workflow.setWorkflowName(workflowName);
        workflow.setStatus("STARTED");
        workflow.setCreatedAt(Instant.now());
        workflow = workflowRepo.save(workflow);
        logEvent(workflow, "WORKFLOW_STARTED", "Workflow started");
        return workflow;
    }

    @Transactional
    public ApprovalRequest requestApproval(Long workflowInstanceId, String requestedBy) {
        WorkflowInstance workflow = workflowRepo.findById(workflowInstanceId)
                .orElseThrow(() -> new RuntimeException("Workflow not found"));

        workflow.setStatus("AWAITING_APPROVAL");
        workflowRepo.save(workflow);

        ApprovalRequest approval = new ApprovalRequest();
        approval.setWorkflowInstance(workflow);
        approval.setRequestedBy(requestedBy);
        approval.setRequestedAt(Instant.now());
        approval.setStatus(ApprovalRequest.ApprovalStatus.PENDING);
        approval = approvalRepo.save(approval);

        logEvent(workflow, "APPROVAL_REQUESTED", "Approval requested by " + requestedBy);
        return approval;
    }

    @Transactional
    public void processApprovalResponse(Long approvalRequestId, String status, String feedbackComment, String respondedBy) {
        ApprovalRequest approval = approvalRepo.findById(approvalRequestId)
                .orElseThrow(() -> new RuntimeException("Approval request not found"));

        ApprovalRequest.ApprovalStatus statusEnum = ApprovalRequest.ApprovalStatus.valueOf(status);
        approval.setStatus(statusEnum);
        approval.setRespondedAt(java.time.Instant.now());

        // Store who responded, and when, if approved
        if (statusEnum == ApprovalRequest.ApprovalStatus.APPROVED) {
            approval.setApprovedBy(respondedBy);
            approval.setApprovedAt(java.time.LocalDateTime.now());
        }

        // Feedback linkage (if applicable)
        Feedback feedback = approval.getFeedback(); // Try to get existing feedback
        if (feedback == null) {
            feedback = new Feedback();
            feedback.setApprovalRequest(approval);
        }
        feedback.setComments(feedbackComment);
        feedback.setRespondedBy(respondedBy);
        approval.setFeedback(feedback);

        approvalRepo.save(approval);

        WorkflowInstance workflow = approval.getWorkflowInstance();

        if (statusEnum == ApprovalRequest.ApprovalStatus.APPROVED) {
            workflow.setStatus("APPROVED");
            logEvent(workflow, "APPROVAL_GRANTED", "Approval granted by " + respondedBy);
            // Further processing for approved workflow...
        } else {
            workflow.setStatus("REJECTED");
            logEvent(workflow, "APPROVAL_DENIED", "Approval denied by " + respondedBy);
            // Trigger rollback or alternate flow here...
        }
        workflowRepo.save(workflow);
    }


    private void logEvent(WorkflowInstance workflow, String eventType, String message) {
        EventLog event = new EventLog();
        event.setWorkflowInstance(workflow);
        event.setEventType(eventType);
        event.setEventTime(Instant.now());
        event.setEventData(message);
        eventLogRepo.save(event);
    }

    public List<WorkflowInstance> getAllWorkflows() {
        return workflowRepo.findAll();
    }
    public List<ApprovalRequest> getAllApprovals() {
        return approvalRepo.findAll();
    }
    public WorkflowInstance createWorkflow(WorkflowInstance workflow) {
    	return workflowRepo.save(workflow);
    }

}
