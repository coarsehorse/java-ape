package com.java.java_api.security;

import com.java.java_api.util.Constants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Created by coarse_horse on 17/07/2020
 */
@Configuration
public class PasswordEncoderConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(Constants.PASSWORD_ENCODER_STRENGTH);
    }
}
