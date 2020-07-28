package com.java.java_api.security.jwt;

import com.java.java_api.entity.User;
import com.java.java_api.exception.BadRequestException;
import com.java.java_api.security.AppUser;
import com.java.java_api.service.UserService;
import com.java.java_api.util.Utils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * Created by coarse_horse on 27/07/2020
 * Class that encapsulates JWT data schema methods to make it easy to change schema.
 */
@RequiredArgsConstructor
public class JwtTokenService {
    
    private final JwtProperties jwtProps;
    private final UserService userService;
    private final WebAuthenticationDetailsSource webAuthDetailsSource = new WebAuthenticationDetailsSource();
    
    public String buildToken(Authentication authentication) {
        LocalDateTime now = LocalDateTime.now();
        
        String jwtToken = Jwts.builder()
            .setSubject(authentication.getName())
            .setIssuedAt(Utils.localDateTime2Date(now))
            .setExpiration(Utils.localDateTime2Date(now.plusDays(jwtProps.getTokenExpirationAfterDays())))
            .signWith(getSecretKey())
            .compact();
    
        return jwtToken;
    }
    
    private SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(jwtProps.getSecretKey().getBytes());
    }
    
    public Authentication parseToken(HttpServletRequest request) {
        String authHeader = request.getHeader(jwtProps.getHeader());
        
        if (StringUtils.isEmpty(authHeader)) {
            throw new BadRequestException("No JWT token");
        } else if (!authHeader.startsWith(jwtProps.getTokenPrefix())) {
            throw new BadRequestException(String.format("JWT token must starts with %s", jwtProps.getTokenPrefix()));
        }
        
        String jwtToken = authHeader.replace(jwtProps.getTokenPrefix(), "");
        
        UsernamePasswordAuthenticationToken auth = Try
            .of(() -> {
                Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(jwtToken);
                Claims claimsBody = claims.getBody();
                String nickname = claimsBody.getSubject();
                
                User user = userService.findByNickname(nickname)
                    .getOrElseThrow(() -> new BadRequestException("Bad user nickname in JWT token"));
                AppUser appUser = new AppUser(user);
                
                return new UsernamePasswordAuthenticationToken(
                    appUser,
                    null,
                    appUser.getAuthorities()
                );
            })
            .getOrElseThrow(BadRequestException::new);
        auth.setDetails(webAuthDetailsSource.buildDetails(request));
        
        return auth;
    }
}
