package com.java.ape.payload.request;

import com.java.ape.security.AppRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by coarse_horse on 24/07/2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SetUserRoleRequest {
    
    @NotNull
    private Long userId;
    @NotNull
    private AppRole role;
}
