package org.sopt.core.controller;

import org.sopt.core.controller.request.CreatePostRequest;
import org.sopt.core.controller.request.UpdatePostRequest;
import org.sopt.core.controller.response.PostListResponse;
import org.sopt.core.controller.response.PostResponse;
import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostService;
import org.sopt.core.support.response.ApiResponse;
import org.sopt.core.support.response.SuccessCode;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping
    public ApiResponse<PostResponse> createPost(
            @RequestBody(required = true) CreatePostRequest request
    ) {
        Post post = postService.write(request.toNewPost());
        return ApiResponse.success(SuccessCode.POST_CREATED, PostResponse.from(post));
    }

    @GetMapping
    public ApiResponse<PostListResponse> readPosts(
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        List<Post> posts = postService.readAll();
        return ApiResponse.success(SuccessCode.POST_LIST_READ, PostListResponse.from(posts));
    }

    @GetMapping(path = "/{postId}")
    public ApiResponse<PostResponse> readPost(
            @PathVariable(name = "postId") Long postId
    ) {
        Post post = postService.read(postId);
        return ApiResponse.success(SuccessCode.POST_READ, PostResponse.from(post));
    }

    @PutMapping(path = "/{postId}")
    public ApiResponse<PostResponse> updatePost(
            @PathVariable(name = "postId") Long postId,
            @RequestBody UpdatePostRequest request
    ) {
        Post post = postService.edit(postId, request.title(), request.content(), request.category());
        return ApiResponse.success(SuccessCode.POST_UPDATED, PostResponse.from(post));

    }

    @DeleteMapping(path = "/{postId}")
    public ApiResponse<?> deletePost(
            @PathVariable(name = "postId") Long postId
    ) {
        postService.remove(postId);
        return ApiResponse.success(SuccessCode.POST_DELETED);
    }
}
