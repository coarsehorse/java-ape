package com.java.ape.security.jwt;

import com.java.ape.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by coarse_horse on 28/07/2020
 */
@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtTokenServiceConfig {

    private final JwtProperties jwtProps;
    private final UserService userService;
    
    @Bean
    JwtTokenService getJwtTokenService() {
        return new JwtTokenService(jwtProps, userService);
    }
}
