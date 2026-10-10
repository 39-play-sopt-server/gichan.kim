package org.sopt.core.domain.post;

public class PostAppender {

    private final PostRepository postRepository;
    private final PostValidator postValidator;

    public PostAppender(PostRepository postRepository, PostValidator postValidator) {
        this.postRepository = postRepository;
        this.postValidator = postValidator;
    }

    public Post append(NewPost newPost) {
        postValidator.validate(newPost.title(), newPost.content());
        return postRepository.save(newPost);
    }
}
