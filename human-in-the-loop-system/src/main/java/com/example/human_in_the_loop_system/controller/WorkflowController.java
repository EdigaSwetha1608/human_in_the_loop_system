package com.example.human_in_the_loop_system.controller;

import org.springframework.web.bind.annotation.*;

import com.example.human_in_the_loop_system.model.ApprovalRequest;
import com.example.human_in_the_loop_system.model.WorkflowInstance;
import com.example.human_in_the_loop_system.service.WorkflowService;

import java.util.List;

@RestController
@RequestMapping
public class WorkflowController {

    private final WorkflowService workflowService;

    public WorkflowController(WorkflowService workflowService) {
        this.workflowService = workflowService;
    }

    @PostMapping("/start")
    public WorkflowInstance startWorkflow(@RequestParam String workflowName) {
        return workflowService.startNewWorkflow(workflowName);
    }

    @GetMapping("/workflows")
    public List<WorkflowInstance> getAllWorkflows() {
        return workflowService.getAllWorkflows();
    }
    
    

    @PostMapping("/{workflowInstanceId}/request-approval")
    public ApprovalRequest requestApproval(
            @PathVariable Long workflowInstanceId,
            @RequestParam String requestedBy) {
        return workflowService.requestApproval(workflowInstanceId, requestedBy);
    }
    @PostMapping
    public WorkflowInstance createWorkflow(@RequestBody WorkflowInstance workflow) {
        return workflowService.createWorkflow(workflow);
    }

   
}

