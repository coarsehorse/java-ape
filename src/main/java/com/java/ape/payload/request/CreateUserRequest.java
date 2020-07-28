package com.java.ape.payload.request;

import com.java.ape.security.AppRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Created by coarse_horse on 17/07/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest {
    
    @NotBlank
    private String nickname;
    private String fullName;
    @NotBlank
    private String password;
    @NotNull
    private AppRole userRole;
}
