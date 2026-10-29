package org.sopt.core.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import org.sopt.core.controller.request.CreatePostRequest;
import org.sopt.core.controller.request.UpdatePostRequest;
import org.sopt.core.controller.response.PostResponse;
import org.sopt.core.domain.member.MemberErrorCode;
import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostCategory;
import org.sopt.core.domain.post.PostErrorCode;
import org.sopt.core.domain.post.PostService;
import org.sopt.core.domain.reaction.ReactionErrorCode;
import org.sopt.core.support.PageResult;
import org.sopt.core.support.auth.FixedMemberIdProvider;
import org.sopt.core.support.code.CommonErrorCode;
import org.sopt.core.support.code.PostSuccessCode;
import org.sopt.core.support.response.PageResponse;
import org.sopt.core.support.response.SuccessResponse;
import org.sopt.core.support.swagger.ApiException;
import org.sopt.core.support.swagger.ApiSuccess;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/posts")
public class PostController {
    private final PostService postService;
    private final FixedMemberIdProvider fixedMemberIdProvider;

    public PostController(PostService postService, FixedMemberIdProvider fixedMemberIdProvider) {
        this.postService = postService;
        this.fixedMemberIdProvider = fixedMemberIdProvider;
    }

    @ApiSuccess(value = PostSuccessCode.class, codes = "POST_CREATED")
    @ApiException(value = MemberErrorCode.class, codes = "MEMBER_NOT_FOUND")
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public SuccessResponse<PostResponse> createPost(
            @Valid @RequestBody(required = true) CreatePostRequest request
    ) {
        Post post = postService.write(request.toNewPost(fixedMemberIdProvider.memberId()));
        return SuccessResponse.of(PostSuccessCode.POST_CREATED, PostResponse.from(post));
    }

    @ApiSuccess(value = PostSuccessCode.class, codes = "POST_LIST_READ")
    @ApiException(value = MemberErrorCode.class, codes = "MEMBER_NOT_FOUND")
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @GetMapping
    public SuccessResponse<PageResponse<PostResponse>> readPosts(
            @RequestParam(required = false) PostCategory category,
            @RequestParam(required = false) @Size(min = 2, max = 50) String keyword,
            @RequestParam(name = "page", defaultValue = "1") @Min(1) int page,
            @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size
    ) {
        PageResult<Post> posts = postService.readAll(category, keyword, page, size);
        return SuccessResponse.of(PostSuccessCode.POST_LIST_READ, PageResponse.from(posts.map(PostResponse::from)));
    }

    @ApiSuccess(value = PostSuccessCode.class, codes = "POST_READ")
    @ApiException(value = PostErrorCode.class, codes = "POST_NOT_FOUND")
    @ApiException(value = MemberErrorCode.class, codes = "MEMBER_NOT_FOUND")
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @GetMapping(path = "/{postId}")
    public SuccessResponse<PostResponse> readPost(
            @PathVariable(name = "postId") Long postId
    ) {
        Post post = postService.read(postId);
        return SuccessResponse.of(PostSuccessCode.POST_READ, PostResponse.from(post));
    }

    @ApiSuccess(value = PostSuccessCode.class, codes = "POST_UPDATED")
    @ApiException(value = PostErrorCode.class, codes = "POST_NOT_FOUND")
    @ApiException(value = MemberErrorCode.class, codes = "MEMBER_NOT_FOUND")
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @PatchMapping(path = "/{postId}")
    public SuccessResponse<PostResponse> updatePost(
            @PathVariable(name = "postId") Long postId,
            @Valid @RequestBody UpdatePostRequest request
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

    @ApiSuccess(value = PostSuccessCode.class, codes = "POST_LIKED")
    @ApiException(value = PostErrorCode.class, codes = "POST_NOT_FOUND")
    @ApiException(value = ReactionErrorCode.class, codes = "LIKE_ALREADY_EXISTS")
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @PostMapping(path = "/{postId}/likes")
    public SuccessResponse<Void> likePost(
            @PathVariable(name = "postId") Long postId
    ) {
        postService.like(postId, fixedMemberIdProvider.memberId());
        return SuccessResponse.of(PostSuccessCode.POST_LIKED);
    }

    @ApiSuccess(value = PostSuccessCode.class, codes = "POST_UNLIKED")
    @ApiException(value = PostErrorCode.class, codes = "POST_NOT_FOUND")
    @ApiException(value = ReactionErrorCode.class, codes = "LIKE_NOT_FOUND")
    @ApiException(value = CommonErrorCode.class, codes = "INVALID_INPUT")
    @DeleteMapping(path = "/{postId}/likes")
    public SuccessResponse<Void> unlikePost(
            @PathVariable(name = "postId") Long postId
    ) {
        postService.unlike(postId, fixedMemberIdProvider.memberId());
        return SuccessResponse.of(PostSuccessCode.POST_UNLIKED);
    }
}
