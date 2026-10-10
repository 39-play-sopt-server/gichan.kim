package org.sopt;

import org.sopt.core.controller.PostController;
import org.sopt.core.domain.post.PostAppender;
import org.sopt.core.domain.post.PostReader;
import org.sopt.core.domain.post.PostRemover;
import org.sopt.core.domain.post.PostRepository;
import org.sopt.core.domain.post.PostService;
import org.sopt.core.domain.post.PostUpdater;
import org.sopt.core.domain.post.PostValidator;
import org.sopt.storage.db.core.post.InMemoryPostRepository;
import org.sopt.view.PostView;

public class Main {

    public static void main(String[] args) {
        PostRepository postRepository = new InMemoryPostRepository();
        PostValidator postValidator = new PostValidator();
        PostReader postReader = new PostReader(postRepository);
        PostAppender postAppender = new PostAppender(postRepository, postValidator);
        PostUpdater postUpdater = new PostUpdater(postRepository, postReader, postValidator);
        PostRemover postRemover = new PostRemover(postRepository, postReader);
        PostService postService = new PostService(postReader, postAppender, postUpdater, postRemover);

        PostView view = new PostView();
        PostController controller = new PostController(view, postService);
        controller.run();
    }
}
