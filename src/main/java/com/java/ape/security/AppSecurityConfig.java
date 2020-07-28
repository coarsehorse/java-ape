package com.java.ape.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.ape.security.jwt.JwtNicknamePasswordAuthFilter;
import com.java.ape.security.jwt.JwtProperties;
import com.java.ape.security.jwt.JwtTokenService;
import com.java.ape.security.jwt.JwtTokenVerifierFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

/**
 * Created by coarse_horse on 17/07/2020
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    
    private final ObjectMapper objectMapper;
    private final JwtProperties jwtProps;
    private final JwtTokenService jwtTokenService;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            // TODO: rewrite using Controller with @Valid body
            .addFilter(
                new JwtNicknamePasswordAuthFilter(
                    objectMapper,
                    authenticationManager(),
                    jwtTokenService,
                    jwtProps
                )
            )
            .addFilterAfter(
                new JwtTokenVerifierFilter(jwtTokenService),
                JwtNicknamePasswordAuthFilter.class
            )
            .authorizeRequests()
            .anyRequest()
            .authenticated();
    }
}
