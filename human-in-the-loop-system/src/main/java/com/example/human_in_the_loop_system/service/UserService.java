package com.example.human_in_the_loop_system.service;

import org.springframework.stereotype.Service;

import com.example.human_in_the_loop_system.model.User;
import com.example.human_in_the_loop_system.repository.UserRepository;
import java.util.List;


import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }
}
