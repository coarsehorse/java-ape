package com.java.java_api.service;

import com.java.java_api.entity.User;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import org.springframework.data.domain.Pageable;

/**
 * Created by coarse_horse on 16/07/2020
 */
public interface UserService extends BaseService<User> {
    
    Option<User> findByNickname(String nickname);
    
    Stream<User> findByEnabled(Boolean enabled, Pageable pageable);
}
