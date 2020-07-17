package com.java.java_api.service;

import com.java.java_api.entity.User;
import io.vavr.control.Option;

/**
 * Created by coarse_horse on 16/07/2020
 */
public interface UserService extends BaseService<User> {
    
    Option<User> findByName(String username);
}
