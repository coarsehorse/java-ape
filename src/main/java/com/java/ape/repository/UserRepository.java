package com.java.ape.repository;

import com.java.ape.entity.User;
import io.vavr.collection.Seq;
import io.vavr.control.Option;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by coarse_horse on 16/07/2020
 */
@Repository
public interface UserRepository extends BaseRepository<User> {
    
    Option<User> findByNickname(String nickname);
    
    Seq<User> findByEnabled(Boolean enabled, Pageable pageable);
}
