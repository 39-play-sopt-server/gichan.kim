package org.sopt.core.domain.post;

public class PostRemover {

    private final PostRepository postRepository;
    private final PostReader postReader;

    public PostRemover(PostRepository postRepository, PostReader postReader) {
        this.postRepository = postRepository;
        this.postReader = postReader;
    }

    public void remove(int index) {
        postReader.read(index);
        postRepository.deleteByIndex(index);
    }
}
