package com.java.ape.payload.response;

import com.java.ape.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by coarse_horse on 21/07/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    
    private Long id;
    private Long userId;
    private String text;
    private LocalDateTime creationDate;
    private Boolean deleted;
    
    public static PostResponse from(Post post) {
        return new PostResponse(
            post.getId(),
            post.getUser().getId(),
            post.getText(),
            post.getCreationDate(),
            post.getDeleted()
        );
    }
}
