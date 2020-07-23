package com.java.java_api.payload.response;

import com.java.java_api.entity.Post;
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
public class CreatePostResponse {
    
    private Long id;
    private Long userId;
    private String text;
    private LocalDateTime creationDate;
    private Boolean deleted;
    
    public static CreatePostResponse from(Post post) {
        return new CreatePostResponse(
            post.getId(),
            post.getUser().getId(),
            post.getText(),
            post.getCreationDate(),
            post.getDeleted()
        );
    }
}
