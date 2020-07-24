package com.java.java_api.payload.response;

import com.java.java_api.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * Created by coarse_horse on 17/07/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    
    private Long id;
    private String nickname;
    private String fullName;
    private LocalDateTime creationDate;
    private Set<String> authorities;
    private Boolean enabled;
    
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getId(),
            user.getNickname(),
            user.getFullName(),
            user.getCreationDate(),
            user.getAuthorities(),
            user.getEnabled()
        );
    }
}
