package com.java.java_api.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by coarse_horse on 27/07/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NicknamePasswordDAO {
    
    private String nickname;
    private String password;
}
