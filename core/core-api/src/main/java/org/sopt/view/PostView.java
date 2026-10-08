package org.sopt.view;

import org.sopt.core.controller.response.PostResponse;
import org.sopt.core.domain.post.PostCategory;
import org.sopt.core.support.response.ApiResponse;

import java.util.List;
import java.util.Scanner;

public class PostView {

    private final Scanner scanner = new Scanner(System.in);

    public void printMenu() {
        System.out.println("\n=== 게시판 ===");
        System.out.println("1. 게시글 작성");
        System.out.println("2. 게시글 목록 조회");
        System.out.println("3. 게시글 단건 조회");
        System.out.println("4. 게시글 수정");
        System.out.println("5. 게시글 삭제");
        System.out.println("6. 종료");
    }

    public int readCommand() {
        return readInt("선택: ");
    }

    public String readTitle() {
        System.out.print("제목: ");
        return scanner.nextLine();
    }

    public String readContent() {
        System.out.print("내용: ");
        return scanner.nextLine();
    }

    public PostCategory readCategory() {
        PostCategory[] categories = PostCategory.values();
        while (true) {
            for (int i = 0; i < categories.length; i++) {
                System.out.println((i + 1) + ". " + categories[i].value());
            }
            int number = readInt("카테고리: ");
            if (number >= 1 && number <= categories.length) {
                return categories[number - 1];
            }
            System.out.println("잘못된 입력입니다.");
        }
    }

    public int readPostNumber(String message) {
        return readInt(message);
    }

    public void printResponse(ApiResponse<?> response) {
        System.out.println(response.getMessage());
    }

    public void printPosts(ApiResponse<List<PostResponse>> response) {
        if (!response.isSuccess()) {
            printResponse(response);
            return;
        }
        List<PostResponse> posts = response.getData();
        if (posts.isEmpty()) {
            System.out.println("게시글이 없습니다.");
            return;
        }
        for (int i = 0; i < posts.size(); i++) {
            System.out.println((i + 1) + ". " + posts.get(i).title());
        }
    }

    public void printPost(ApiResponse<PostResponse> response) {
        if (!response.isSuccess()) {
            printResponse(response);
            return;
        }
        PostResponse post = response.getData();
        System.out.println("\n=== 게시글 ===");
        System.out.println("제목: " + post.title());
        System.out.println("내용: " + post.content());
        System.out.println("카테고리: " + post.category().value());
        System.out.println("좋아요: " + post.likeCount());
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("숫자를 입력해주세요.");
            }
        }
    }
}
