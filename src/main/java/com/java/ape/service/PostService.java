package com.java.ape.service;

import com.java.ape.entity.Post;
import io.vavr.collection.Stream;
import org.springframework.data.domain.Pageable;

/**
 * Created by coarse_horse on 21/07/2020
 */
public interface PostService extends BaseService<Post> {
    
    Stream<Post> findNotDeletedByUserId(Long userId, Pageable pageable);
}
