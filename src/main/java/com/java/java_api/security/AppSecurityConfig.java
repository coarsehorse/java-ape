package com.java.java_api.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by coarse_horse on 17/07/2020
 */
@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/*/admin/**").hasRole(AppRole.ADMIN.name())
            .antMatchers("/api/*/user/get*/**").hasAuthority(AppAuthority.USER_READ.name())
            .anyRequest().permitAll()
            .and()
            .httpBasic();
    }
}
