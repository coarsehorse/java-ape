package com.java.java_api.payload.request;

import com.java.java_api.security.AppRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by coarse_horse on 17/07/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    
    private String username;
    private String password;
    private AppRole userRole;
}
