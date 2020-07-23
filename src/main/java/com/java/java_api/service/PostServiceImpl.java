package com.java.java_api.service;

import com.java.java_api.entity.Post;
import com.java.java_api.repository.PostRepository;
import io.vavr.collection.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Created by coarse_horse on 21/07/2020
 */
@Service
public class PostServiceImpl extends BaseServiceImpl<Post, PostRepository> implements PostService {
    
    @Autowired
    public PostServiceImpl(PostRepository repository) {
        super(repository);
    }
    
    @Override
    public Stream<Post> findNotDeletedByUserId(Long userId, Pageable pageable) {
        return Stream.ofAll(getRepository().findByUserIdAndDeletedIsFalse(userId, pageable));
    }
}
