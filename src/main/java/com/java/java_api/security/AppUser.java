package com.java.java_api.security;

import com.java.java_api.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by coarse_horse on 16/07/2020
 */
public class AppUser implements UserDetails {
    
    private final Set<? extends GrantedAuthority> grantedAuthorities;
    private final String password;
    private final String nickname;
    private final Boolean accountNonExpired;
    private final Boolean accountNonLocked;
    private final Boolean credentialsNonExpired;
    private final Boolean enabled;
    
    public AppUser(User user) {
        this.grantedAuthorities = user.getAuthorities()
            .stream()
            .map(SimpleGrantedAuthority::new)
            .collect(Collectors.toSet());
        this.password = user.getPassword();
        this.nickname = user.getNickname();
        this.accountNonExpired = true;
        this.accountNonLocked = true;
        this.credentialsNonExpired = true;
        this.enabled = user.getEnabled();
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthorities;
    }
    
    @Override
    public String getPassword() {
        return this.password;
    }
    
    @Override
    public String getUsername() {
        return this.nickname;
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }
    
    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
