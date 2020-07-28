package com.java.ape.api.v1.controller;

import com.java.ape.entity.Post;
import com.java.ape.exception.BadRequestException;
import com.java.ape.payload.request.CreatePostRequest;
import com.java.ape.payload.request.DeletePostRequest;
import com.java.ape.payload.request.UpdatePostRequest;
import com.java.ape.payload.response.PostResponse;
import com.java.ape.security.AppUser;
import com.java.ape.service.PostService;
import com.java.ape.util.OffsetBasedPageRequest;
import com.java.ape.util.Utils;
import io.vavr.control.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * Created by coarse_horse on 17/07/2020
 */
@RestController
@RequestMapping("api/v1/post")
@Validated
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostController {
    
    private final PostService postService;
    
    @PostMapping("createPost")
    @PreAuthorize("hasAuthority(T(com.java.ape.security.AppAuthority).USER_WRITE.name())")
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
    @PreAuthorize("hasAuthority(T(com.java.ape.security.AppAuthority).USER_READ.name())")
    public List<PostResponse> getPosts(
        @RequestParam(required = false) Long userId,
        @RequestParam @Min(0) Long offset,
        @RequestParam @Min(1) Integer size,
        Authentication authentication
    ) {
        AppUser appUser = Utils.castAuthToAppUser(authentication);
        Long actualUserId = Option.of(userId).getOrElse(appUser.getUser().getId());
    
        return postService
            .findNotDeletedByUserId(
                actualUserId,
                OffsetBasedPageRequest.of(offset, size)
            )
            .map(PostResponse::from)
            .toJavaList();
    }
    
    @PostMapping("updatePost")
    @PreAuthorize("hasAuthority(T(com.java.ape.security.AppAuthority).USER_WRITE.name())")
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
            throw new BadRequestException(String.format("Post id=%d is deleted", post.getId()));
        }
    }
    
    @PostMapping("deletePost")
    @PreAuthorize("hasAuthority(T(com.java.ape.security.AppAuthority).USER_WRITE.name())")
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
