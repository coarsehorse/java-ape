package com.java.ape.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by coarse_horse on 24/07/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeleteUserRequest {
    
    @NotNull
    private Long id;
}
