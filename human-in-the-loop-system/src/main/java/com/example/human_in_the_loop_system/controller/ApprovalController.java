package com.example.human_in_the_loop_system.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.human_in_the_loop_system.model.ApprovalRequest;
import com.example.human_in_the_loop_system.service.WorkflowService;

@RestController
@RequestMapping("/approvals")
public class ApprovalController {

    private final WorkflowService workflowService;

    public ApprovalController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @GetMapping
    public List<ApprovalRequest> getAllApprovals() {
        // Replace with your own WorkflowService method, e.g.:
        return workflowService.getAllApprovals(); // Ensure this method exists
    }

    @PostMapping("/{approvalRequestId}/respond")
    public ResponseEntity<?> respondToApproval(
            @PathVariable Long approvalRequestId,
            @RequestParam String status,
            @RequestParam String feedback,
            @RequestParam String respondedBy) {

        // Call service method to process approval response
        workflowService.processApprovalResponse(
            approvalRequestId, status, feedback, respondedBy
        ); // Ensure this method exists in WorkflowService

        Map<String, Object> body = new HashMap<>();
        body.put("approvalRequestId", approvalRequestId);
        body.put("status", status);
        body.put("feedback", feedback);
        body.put("respondedBy", respondedBy);
        body.put("timestamp", java.time.Instant.now().toString());

        return ResponseEntity.ok(body);
    }
}
