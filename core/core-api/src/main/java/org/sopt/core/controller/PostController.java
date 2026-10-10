package org.sopt.core.controller;

import org.sopt.core.controller.response.PostResponse;
import org.sopt.core.domain.post.NewPost;
import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostCategory;
import org.sopt.core.domain.post.PostService;
import org.sopt.core.error.CoreException;
import org.sopt.core.support.response.ApiResponse;
import org.sopt.core.support.response.SuccessCode;
import org.sopt.view.PostView;

import java.util.List;

public class PostController {

    private final PostView view;
    private final PostService postService;

    public PostController(PostView view, PostService postService) {
        this.view = view;
        this.postService = postService;
    }

    public void run() {
        while (true) {
            view.printMenu();
            int command = view.readCommand();
            switch (command) {
                case 1 -> createPost();
                case 2 -> readPosts();
                case 3 -> readPost();
                case 4 -> updatePost();
                case 5 -> deletePost();
                case 6 -> {
                    view.printMessage("프로그램을 종료합니다.");
                    return;
                }
                default -> view.printMessage("잘못된 입력입니다.");
            }
        }
    }

    private void createPost() {
        String title = view.readTitle();
        String content = view.readContent();
        PostCategory category = view.readCategory();

        ApiResponse<PostResponse> response;
        try {
            Post post = postService.write(new NewPost(title, content, category));
            response = ApiResponse.success(SuccessCode.POST_CREATED, PostResponse.from(post));
        } catch (CoreException e) {
            response = ApiResponse.error(e.getErrorCode());
        }
        view.printResponse(response);
    }

    private void readPosts() {
        ApiResponse<List<PostResponse>> response;
        try {
            List<PostResponse> posts = postService.readAll().stream()
                    .map(PostResponse::from)
                    .toList();
            response = ApiResponse.success(SuccessCode.POST_LIST_READ, posts);
        } catch (CoreException e) {
            response = ApiResponse.error(e.getErrorCode());
        }
        view.printPosts(response);
    }

    private void readPost() {
        int index = view.readPostNumber("조회할 게시글 번호: ") - 1;

        ApiResponse<PostResponse> response;
        try {
            Post post = postService.read(index);
            response = ApiResponse.success(SuccessCode.POST_READ, PostResponse.from(post));
        } catch (CoreException e) {
            response = ApiResponse.error(e.getErrorCode());
        }
        view.printPost(response);
    }

    private void updatePost() {
        int index = view.readPostNumber("수정할 게시글 번호: ") - 1;

        ApiResponse<PostResponse> response;
        try {
            postService.read(index);
            String title = view.readTitle();
            String content = view.readContent();
            PostCategory category = view.readCategory();
            Post post = postService.edit(index, title, content, category);
            response = ApiResponse.success(SuccessCode.POST_UPDATED, PostResponse.from(post));
        } catch (CoreException e) {
            response = ApiResponse.error(e.getErrorCode());
        }
        view.printResponse(response);
    }

    private void deletePost() {
        int index = view.readPostNumber("삭제할 게시글 번호: ") - 1;

        ApiResponse<Void> response;
        try {
            postService.remove(index);
            response = ApiResponse.success(SuccessCode.POST_DELETED);
        } catch (CoreException e) {
            response = ApiResponse.error(e.getErrorCode());
        }
        view.printResponse(response);
    }
}
