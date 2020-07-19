package com.java.java_api.api.v1.controller;

import com.java.java_api.entity.User;
import com.java.java_api.payload.request.CreateUserRequest;
import com.java.java_api.payload.response.CreateUserResponse;
import com.java.java_api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * Created by coarse_horse on 17/07/2020
 */
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public AdminController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping("createUser")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).ADMIN_WRITE.name())")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest payload) {
        User newUser = new User(
            payload.getNickname(),
            payload.getFullName(),
            passwordEncoder.encode(payload.getPassword()),
            payload.getUserRole().getGrantedAuthorities(),
            true
        );
        newUser = userService.save(newUser);
        
        return CreateUserResponse.from(newUser);
    }
    
    @GetMapping("getGreetings")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).ADMIN_READ.name())")
    public String getGreetings() {
        return "Hello, admin!";
    }
}
