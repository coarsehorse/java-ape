package com.java.java_api.security;

import io.vavr.collection.Stream;
import lombok.Getter;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by coarse_horse on 16/07/2020
 */
@Getter
public enum AppRole {
    ADMIN(
        Stream.of(
            AppAuthority.USER_READ,
            AppAuthority.USER_WRITE,
            AppAuthority.ADMIN_READ,
            AppAuthority.ADMIN_WRITE
        )
    ),
    USER(
        Stream.of(
            AppAuthority.USER_READ,
            AppAuthority.USER_WRITE
        )
    );
    
    private static final String ROLE_PREFIX = "ROLE_";
    
    private Stream<AppAuthority> authorities;
    
    AppRole(Stream<AppAuthority> authorities) {
        this.authorities = authorities;
    }
    
    public Set<String> getGrantedAuthorities() {
        return this.authorities
            .map(Enum::name)
            .append(ROLE_PREFIX + this.name()) // add role as authority
            .collect(Collectors.toSet());
    }
}
