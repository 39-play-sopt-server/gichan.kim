package org.sopt.core.controller;

import org.sopt.core.controller.request.CreatePostRequest;
import org.sopt.core.controller.request.UpdatePostRequest;
import org.sopt.core.controller.response.PostListResponse;
import org.sopt.core.controller.response.PostResponse;
import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostService;
import org.sopt.core.support.code.PostSuccessCode;
import org.sopt.core.support.response.SuccessResponse;
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
    public SuccessResponse<PostResponse> createPost(
            @RequestBody(required = true) CreatePostRequest request
    ) {
        Post post = postService.write(request.toNewPost());
        return SuccessResponse.of(PostSuccessCode.POST_CREATED, PostResponse.from(post));
    }

    @GetMapping
    public SuccessResponse<PostListResponse> readPosts(
            @RequestParam(name = "page", defaultValue = "1") int page
    ) {
        List<Post> posts = postService.readAll();
        return SuccessResponse.of(PostSuccessCode.POST_LIST_READ, PostListResponse.from(posts));
    }

    @GetMapping(path = "/{postId}")
    public SuccessResponse<PostResponse> readPost(
            @PathVariable(name = "postId") Long postId
    ) {
        Post post = postService.read(postId);
        return SuccessResponse.of(PostSuccessCode.POST_READ, PostResponse.from(post));
    }

    @PutMapping(path = "/{postId}")
    public SuccessResponse<PostResponse> updatePost(
            @PathVariable(name = "postId") Long postId,
            @RequestBody UpdatePostRequest request
    ) {
        Post post = postService.edit(postId, request.title(), request.content(), request.category());
        return SuccessResponse.of(PostSuccessCode.POST_UPDATED, PostResponse.from(post));

    }

    @DeleteMapping(path = "/{postId}")
    public SuccessResponse<Void> deletePost(
            @PathVariable(name = "postId") Long postId
    ) {
        postService.remove(postId);
        return SuccessResponse.of(PostSuccessCode.POST_DELETED);
    }
}
