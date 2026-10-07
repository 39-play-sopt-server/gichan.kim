package org.sopt;

import org.sopt.post.presentation.controller.PostController;
import org.sopt.post.presentation.view.PostView;

public class Main {
    public static void main(String[] args) {
        PostView view = new PostView();
        PostController controller = new PostController(view);
        controller.run();
    }
}