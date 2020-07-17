package com.java.java_api.payload.response;

import com.java.java_api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Created by coarse_horse on 17/07/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserResponse {
    
    private String username;
    private Set<String> authorities;
    private Boolean enabled;
    
    public static CreateUserResponse from(User user) {
        return new CreateUserResponse(
            user.getUsername(),
            user.getAuthorities(),
            user.getEnabled()
        );
    }
}
