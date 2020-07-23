package com.java.java_api.api.v1.controller;

import com.java.java_api.entity.Post;
import com.java.java_api.payload.request.CreatePostRequest;
import com.java.java_api.payload.request.DeletePostRequest;
import com.java.java_api.payload.request.UpdatePostRequest;
import com.java.java_api.payload.response.CreatePostResponse;
import com.java.java_api.security.AppAuthority;
import com.java.java_api.security.AppUser;
import com.java.java_api.service.PostService;
import com.java.java_api.util.OffsetBasedPageRequest;
import com.java.java_api.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

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
    public CreatePostResponse createPost(
        @Valid @RequestBody CreatePostRequest payload,
        Authentication authentication
    ) {
        AppUser appUser = Utils.castAuthToAppUser(authentication);
        
        Post post = new Post(payload.getText(), appUser.getUser(), false);
        post = postService.save(post);
    
        return CreatePostResponse.from(post);
    }
    
    /**
     * Returns a posts of specified user or authenticated user if userId is empty.
     */
    @GetMapping(path = "getPosts")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).USER_READ.name())")
    public List<CreatePostResponse> getPosts(
        @RequestParam("userId") Optional<Long> userIdOpt,
        @RequestParam Long offset,
        @RequestParam Integer size,
        Authentication authentication
    ) {
        AppUser appUser = Utils.castAuthToAppUser(authentication);
        Long userId = userIdOpt.orElseGet(() -> appUser.getUser().getId());
        
        return postService.findNotDeletedByUserId(userId, OffsetBasedPageRequest.of(offset, size))
            .map(CreatePostResponse::from)
            .toJavaList();
    }
    
    @PostMapping("updatePost")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).USER_WRITE.name())")
    public CreatePostResponse updatePost(
        @Valid @RequestBody UpdatePostRequest payload,
        Authentication authentication
    ) {
        Post post = postService.findByIdOrThrow(payload.getId());
        
        checkPostOwner(authentication, post);
        if (post.getDeleted()) {
            throw new IllegalArgumentException(String.format("Specified post id=%d is deleted", post.getId()));
        }
        
        post.setText(payload.getText());
        post = postService.save(post);
        
        return CreatePostResponse.from(post);
    }
    
    @PostMapping("deletePost")
    @PreAuthorize("hasAuthority(T(com.java.java_api.security.AppAuthority).USER_WRITE.name())")
    public CreatePostResponse deletePost(
        @Valid @RequestBody DeletePostRequest payload,
        Authentication authentication
    ) {
        Post post = postService.findByIdOrThrow(payload.getId());
        
        Boolean hasAdminWritePerms = Utils.hasAppAuthority(authentication, AppAuthority.ADMIN_WRITE);
        // Admin can delete others posts
        if (!hasAdminWritePerms) {
            // Otherwise check the ownerships
            checkPostOwner(authentication, post);
        }
        
        post.setDeleted(true);
        post = postService.save(post);
    
        return CreatePostResponse.from(post);
    }
    
    /**
     * Throws an exception if post owner is not a current user.
     */
    private void checkPostOwner(Authentication authentication, Post post) {
        AppUser appUser = Utils.castAuthToAppUser(authentication);
    
        // Protection from editing not yourth posts
        Long postOwnerId = post.getUser().getId();
        Long appUserId = appUser.getUser().getId();
        if (!postOwnerId.equals(appUserId)) {
            throw new IllegalArgumentException(
                String.format("User id=%d is not an owner of the post id=%d", appUserId, post.getId())
            );
        }
    }
}
