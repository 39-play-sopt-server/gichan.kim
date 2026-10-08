package org.sopt.core.domain.post;

public class PostUpdater {

    private final PostRepository postRepository;
    private final PostReader postReader;
    private final PostValidator postValidator;

    public PostUpdater(PostRepository postRepository, PostReader postReader, PostValidator postValidator) {
        this.postRepository = postRepository;
        this.postReader = postReader;
        this.postValidator = postValidator;
    }

    public Post update(int index, String title, String content, PostCategory category) {
        Post post = postReader.read(index);
        postValidator.validate(title, content);
        post.update(title, content, category);
        return postRepository.update(index, post);
    }
}
