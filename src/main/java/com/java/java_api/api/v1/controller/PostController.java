package com.java.java_api.api.v1.controller;

import com.java.java_api.entity.Post;
import com.java.java_api.payload.request.CreatePostRequest;
import com.java.java_api.payload.request.DeletePostRequest;
import com.java.java_api.payload.request.UpdatePostRequest;
import com.java.java_api.payload.response.PostResponse;
import com.java.java_api.security.AppUser;
import com.java.java_api.service.PostService;
import com.java.java_api.util.OffsetBasedPageRequest;
import com.java.java_api.util.Utils;
import io.vavr.control.Option;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by coarse_horse on 17/07/2020
 */
@RestController
@RequestMapping("api/v1/post")
public class PostController {
    
    private final PostService postService;
    
    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
    
    @PostMapping("createPost")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).USER_WRITE.name())")
    public PostResponse createPost(
        @Valid @RequestBody CreatePostRequest payload,
        Authentication authentication
    ) {
        AppUser appUser = Utils.castAuthToAppUser(authentication);
        
        Post post = new Post(payload.getText(), appUser.getUser(), false);
        post = postService.save(post);
    
        return PostResponse.from(post);
    }
    
    /**
     * Returns a posts of specified user or authenticated user if userId is empty.
     */
    @GetMapping(path = "getPosts")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).USER_READ.name())")
    public List<PostResponse> getPosts(
        @RequestParam(required = false) Long userId,
        @RequestParam Long offset,
        @RequestParam Integer size,
        Authentication authentication
    ) {
        AppUser appUser = Utils.castAuthToAppUser(authentication);
        Long actualUserId = Option.of(userId).getOrElse(appUser.getUser().getId());
    
        return postService.findNotDeletedByUserId(actualUserId, OffsetBasedPageRequest.of(offset, size))
            .map(PostResponse::from)
            .toJavaList();
    }
    
    @PostMapping("updatePost")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).USER_WRITE.name())")
    public PostResponse updatePost(
        @Valid @RequestBody UpdatePostRequest payload,
        Authentication authentication
    ) {
        Post post = postService.findByIdOrThrow(payload.getId());
        
        Utils.checkOwnership(authentication, post.getUser());
        checkIsPostDeleted(post);
        
        post.setText(payload.getText());
        post = postService.save(post);
        
        return PostResponse.from(post);
    }
    
    private void checkIsPostDeleted(Post post) {
        if (post.getDeleted()) {
            throw new IllegalArgumentException(String.format("Specified post id=%d is deleted", post.getId()));
        }
    }
    
    @PostMapping("deletePost")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).USER_WRITE.name())")
    public PostResponse deletePost(
        @Valid @RequestBody DeletePostRequest payload,
        Authentication authentication
    ) {
        Post post = postService.findByIdOrThrow(payload.getId());
        
        Utils.checkIsAdminOrOwner(authentication, post.getUser());
        checkIsPostDeleted(post);
        
        post.setDeleted(true);
        post = postService.save(post);
    
        return PostResponse.from(post);
    }
}
