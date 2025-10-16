package com.example.human_in_the_loop_system.controller;

import org.springframework.web.bind.annotation.*;

import com.example.human_in_the_loop_system.model.User;
import com.example.human_in_the_loop_system.model.WorkflowInstance;
import com.example.human_in_the_loop_system.repository.WorkflowInstanceRepository;
import com.example.human_in_the_loop_system.service.UserService;
import com.example.human_in_the_loop_system.dto.UserDTO;

import java.util.List; 
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final WorkflowInstanceRepository workflowInstanceRepository;

    public UserController(
        UserService userService,
        WorkflowInstanceRepository workflowInstanceRepository
    ) {
        this.userService = userService;
        this.workflowInstanceRepository = workflowInstanceRepository;
    }

    @GetMapping
    public List<UserDTO> getAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(UserDTO::new)
                .toList();
    }

    @GetMapping("/{username}")
    public Optional<User> findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @PostMapping
    public User createUser(@RequestBody User user, @RequestParam Long workflowId) {
        WorkflowInstance workflow = workflowInstanceRepository.findById(workflowId)
            .orElseThrow(() -> new RuntimeException("Workflow not found"));
        user.setWorkflowInstance(workflow);
        return userService.saveUser(user);
    }
}
