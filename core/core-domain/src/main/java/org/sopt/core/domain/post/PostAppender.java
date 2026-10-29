package org.sopt.core.domain.post;

import org.sopt.core.domain.member.MemberReader;
import org.springframework.stereotype.Component;

@Component
public class PostAppender {

    private final PostRepository postRepository;
    private final MemberReader memberReader;

    public PostAppender(PostRepository postRepository, MemberReader memberReader) {
        this.postRepository = postRepository;
        this.memberReader = memberReader;
    }

    public Post append(NewPost newPost) {
        memberReader.validateExists(newPost.memberId());
        return postRepository.save(newPost);
    }
}
