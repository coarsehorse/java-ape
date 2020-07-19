package com.java.java_api.api.v1.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by coarse_horse on 17/07/2020
 */
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    
    @GetMapping("getGreetings")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).USER_READ.name())")
    public String getGreetings() {
        return "Hello, user!";
    }
}
