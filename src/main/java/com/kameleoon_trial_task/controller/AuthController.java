package com.kameleoon_trial_task.controller;

import com.kameleoon_trial_task.controller.request.AuthRequest;
import com.kameleoon_trial_task.model.User;
import com.kameleoon_trial_task.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Void> addUser(@RequestBody AuthRequest authRequest) {
        User user = new User();
        user.setEmail(authRequest.getEmail());
        user.setName(authRequest.getName());
        user.setPassword(authRequest.getPassword());

        userService.addUser(user);

        return ResponseEntity.ok().build();
    }
}
