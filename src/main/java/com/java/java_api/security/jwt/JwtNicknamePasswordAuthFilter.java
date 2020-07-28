package com.java.java_api.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.java_api.exception.BadRequestException;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by coarse_horse on 27/07/2020
 */
@RequiredArgsConstructor
public class JwtNicknamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {
    
    private final ObjectMapper objectMapper;
    private final AuthenticationManager authManager;
    private final JwtTokenService jwtTokenService;
    private final JwtProperties jwtProps;
    
    @Override
    public Authentication attemptAuthentication(
        HttpServletRequest request,
        HttpServletResponse response
    ) throws AuthenticationException {
        NicknamePasswordDAO nicknamePasswordDAO = Try
            .of(() ->
                this.objectMapper.readValue(request.getInputStream(), NicknamePasswordDAO.class)
            )
            .getOrElseThrow(() -> new BadRequestException("Cannot map request body to the entity"));
        Authentication toAuthenticate = new UsernamePasswordAuthenticationToken(
            nicknamePasswordDAO.getNickname(),
            nicknamePasswordDAO.getPassword()
        );
        
        Authentication authenticated = this.authManager.authenticate(toAuthenticate);
        
        return authenticated;
    }
    
    @Override
    protected void successfulAuthentication(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain chain,
        Authentication authResult
    ) {
        String jwtToken = jwtTokenService.buildToken(authResult);
        response.addHeader(jwtProps.getHeader(), jwtProps.getTokenPrefix() + jwtToken);
    }
}
