package com.java.java_api.service;

import com.java.java_api.entity.User;
import com.java.java_api.repository.UserRepository;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
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
}
