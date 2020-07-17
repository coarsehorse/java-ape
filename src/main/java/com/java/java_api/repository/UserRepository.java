package com.java.java_api.repository;

import com.java.java_api.entity.User;
import io.vavr.control.Option;
import org.springframework.stereotype.Repository;

/**
 * Created by coarse_horse on 16/07/2020
 */
@Repository
public interface UserRepository extends BaseRepository<User> {
    
    Option<User> findByUsername(String username);
}
