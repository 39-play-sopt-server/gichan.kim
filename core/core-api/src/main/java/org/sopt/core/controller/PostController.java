package org.sopt.core.controller;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.sopt.core.controller.request.CreatePostRequest;
import org.sopt.core.controller.request.UpdatePostRequest;
import org.sopt.core.controller.response.PostListResponse;
import org.sopt.core.controller.response.PostResponse;
import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostCategory;
import org.sopt.core.domain.post.PostErrorCode;
import org.sopt.core.domain.post.PostService;
import org.sopt.core.support.code.CommonErrorCode;
import org.sopt.core.support.code.PostSuccessCode;
import org.sopt.core.support.response.SuccessResponse;
import org.sopt.core.support.swagger.ApiException;
import org.sopt.core.support.swagger.ApiSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @ApiSuccess(value = PostSuccessCode.class, codes = "POST_CREATED")
    @ApiException(value = PostErrorCode.class, codes = {"POST_TITLE_EMPTY", "POST_CONTENT_EMPTY"})
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SuccessResponse<PostResponse> createPost(
            @RequestBody(required = true) CreatePostRequest request
    ) {
        Post post = postService.write(request.toNewPost());
        return SuccessResponse.of(PostSuccessCode.POST_CREATED, PostResponse.from(post));
    }

    @ApiSuccess(value = PostSuccessCode.class, codes = "POST_LIST_READ")
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @GetMapping
    public SuccessResponse<PostListResponse> readPosts(
            @RequestParam(required = false) PostCategory category,
            @RequestParam(name = "page", defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size
    ) {
        List<Post> posts = postService.readAll(category, page, size);
        return SuccessResponse.of(PostSuccessCode.POST_LIST_READ, PostListResponse.from(posts));
    }

    @ApiSuccess(value = PostSuccessCode.class, codes = "POST_READ")
    @ApiException(value = PostErrorCode.class, codes = "POST_NOT_FOUND")
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @GetMapping(path = "/{postId}")
    public SuccessResponse<PostResponse> readPost(
            @PathVariable(name = "postId") Long postId
    ) {
        Post post = postService.read(postId);
        return SuccessResponse.of(PostSuccessCode.POST_READ, PostResponse.from(post));
    }

    @ApiSuccess(value = PostSuccessCode.class, codes = "POST_UPDATED")
    @ApiException(value = PostErrorCode.class, codes = {"POST_NOT_FOUND", "POST_TITLE_EMPTY", "POST_CONTENT_EMPTY"})
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @PatchMapping(path = "/{postId}")
    public SuccessResponse<PostResponse> updatePost(
            @PathVariable(name = "postId") Long postId,
            @RequestBody UpdatePostRequest request
    ) {
        Post post = postService.edit(postId, request.title(), request.content(), request.category());
        return SuccessResponse.of(PostSuccessCode.POST_UPDATED, PostResponse.from(post));

    }

    @ApiSuccess(value = PostSuccessCode.class, codes = "POST_DELETED")
    @ApiException(value = PostErrorCode.class, codes = "POST_NOT_FOUND")
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @DeleteMapping(path = "/{postId}")
    public SuccessResponse<Void> deletePost(
            @PathVariable(name = "postId") Long postId
    ) {
        postService.remove(postId);
        return SuccessResponse.of(PostSuccessCode.POST_DELETED);
    }
}
