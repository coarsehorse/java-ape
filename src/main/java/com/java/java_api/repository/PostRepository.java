package com.java.java_api.repository;

import com.java.java_api.entity.Post;
import io.vavr.collection.Seq;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * Created by coarse_horse on 21/07/2020
 */
@Repository
public interface PostRepository extends BaseRepository<Post> {
    
    Seq<Post> findByUserIdAndDeletedIsFalse(Long userId, Pageable pageable);
}
