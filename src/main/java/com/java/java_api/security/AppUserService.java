package com.java.java_api.security;

import com.java.java_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by coarse_horse on 16/07/2020
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AppUserService implements UserDetailsService {
    
    private final UserService userService;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userService.findByNickname(username)
            .map(AppUser::new)
            .getOrElseThrow(() ->
                new RuntimeException(String.format("Cannot find user by nickname \"%s\"", username))
            );
    }
}
