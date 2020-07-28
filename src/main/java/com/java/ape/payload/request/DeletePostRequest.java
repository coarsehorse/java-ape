package com.java.ape.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * Created by coarse_horse on 23/07/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeletePostRequest {
    
    @NotNull
    private Long id;
}
