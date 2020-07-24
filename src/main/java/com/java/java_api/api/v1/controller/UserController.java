package com.java.java_api.api.v1.controller;

import com.java.java_api.entity.User;
import com.java.java_api.exception.BadRequestException;
import com.java.java_api.payload.request.CreateUserRequest;
import com.java.java_api.payload.request.DeleteUserRequest;
import com.java.java_api.payload.request.SetUserRoleRequest;
import com.java.java_api.payload.request.UpdateUserRequest;
import com.java.java_api.payload.response.UserResponse;
import com.java.java_api.service.UserService;
import com.java.java_api.util.OffsetBasedPageRequest;
import com.java.java_api.util.Utils;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by coarse_horse on 17/07/2020
 */
@RestController
@RequestMapping("api/v1/user")
@Validated
public class UserController {
    
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    
    @PostMapping("createUser")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).ADMIN_WRITE.name())")
    public UserResponse createUser(
        @Valid @RequestBody CreateUserRequest payload
    ) {
        User newUser = new User(
            payload.getNickname(),
            payload.getFullName(),
            passwordEncoder.encode(payload.getPassword()),
            payload.getUserRole().getGrantedAuthorities(),
            true
        );
        newUser = userService.save(newUser);
        
        return UserResponse.from(newUser);
    }
    
    /**
     * Returns page of users. If enabled is specified it will be used as a filter, true by default.
     */
    @GetMapping(path = "getUsers")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).ADMIN_READ.name())")
    public List<UserResponse> getUsers(
        @RequestParam(required = false) Boolean enabled,
        @RequestParam @Min(0) Long offset,
        @RequestParam @Min(1) Integer size
    ) {
        return userService
            .findByEnabled(
                Option.of(enabled).getOrElse(true),
                OffsetBasedPageRequest.of(offset, size)
            )
            .map(UserResponse::from)
            .collect(Collectors.toList());
    }
    
    @PostMapping(path = "updateUser")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).USER_WRITE.name())")
    public UserResponse updateUser(
        @Valid @RequestBody UpdateUserRequest payload,
        Authentication authentication
    ) {
        User user = userService.findByIdOrThrow(payload.getId());
    
        Utils.checkOwnership(authentication, user);
        checkIsUserDisabled(user);
        
        user.setNickname(Option.of(payload.getNickname()).getOrElse(user.getNickname()));
        user.setFullName(Option.of(payload.getFullName()).getOrElse(user.getFullName()));
        user.setPassword(
            Option.of(payload.getPassword())
                .map(passwordEncoder::encode)
                .getOrElse(user.getPassword())
        );
        user.setEnabled(Option.of(payload.getEnabled()).getOrElse(user.getEnabled()));
        user = userService.save(user);
        
        return UserResponse.from(user);
    }
    
    private void checkIsUserDisabled(User user) {
        if (!user.getEnabled()) {
            throw new BadRequestException(String.format("User id=%d is disabled", user.getId()));
        }
    }
    
    @PostMapping(path = "setUserRole")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).ADMIN_WRITE.name())")
    public UserResponse setUserRole(
        @Valid @RequestBody SetUserRoleRequest payload
    ) {
        User user = userService.findByIdOrThrow(payload.getUserId());
        
        checkIsUserDisabled(user);
    
        user.setAuthorities(payload.getRole().getGrantedAuthorities());
        user = userService.save(user);
    
        return UserResponse.from(user);
    }
    
    @PostMapping(path = "deleteUser")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).ADMIN_WRITE.name())")
    public UserResponse deleteUser(
        @Valid @RequestBody DeleteUserRequest payload,
        Authentication authentication
    ) {
        User user = userService.findByIdOrThrow(payload.getId());
        
        Utils.checkIsAdminOrOwner(authentication, user);
        checkIsUserDisabled(user);
        
        user.setEnabled(false);
        user = userService.save(user);
        
        return UserResponse.from(user);
    }
}
