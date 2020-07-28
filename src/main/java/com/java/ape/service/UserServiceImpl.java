package com.java.ape.service;

import com.java.ape.entity.User;
import com.java.ape.repository.UserRepository;
import io.vavr.collection.Stream;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by coarse_horse on 16/07/2020
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User, UserRepository> implements UserService {
    
    @Autowired
    public UserServiceImpl(UserRepository repository) {
        super(repository);
    }
    
    @Override
    public Option<User> findByNickname(String nickname) {
        return getRepository().findByNickname(nickname);
    }
    
    @Override
    public Stream<User> findByEnabled(Boolean enabled, Pageable pageable) {
        return getRepository().findByEnabled(enabled, pageable).toStream();
    }
}
