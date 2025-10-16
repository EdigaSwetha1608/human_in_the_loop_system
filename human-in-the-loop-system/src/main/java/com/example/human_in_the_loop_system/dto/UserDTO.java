package com.example.human_in_the_loop_system.dto;
import com.example.human_in_the_loop_system.model.User;

public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String channel;
    private String workflowName;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.channel = user.getChannel();
        this.workflowName = user.getWorkflowInstance() != null ? user.getWorkflowInstance().getWorkflowName() : null;
    }

    // Getters and setters here if needed

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getChannel() { return channel; }
    public String getWorkflowName() { return workflowName; }
}

