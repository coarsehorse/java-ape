package com.java.java_api.api.v1.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by coarse_horse on 17/07/2020
 */
@RestController
@RequestMapping("api/v1/admin")
public class AdminController {
    
    // TODO: remove
    @GetMapping("getGreetings")
    @PreAuthorize("hasRole(T(com.java.java_api.security.AppRole).ADMIN.name())")
    public String getGreetings(Principal principal) {
        return String.format("Hello, admin %s!", principal.getName());
    }
}
