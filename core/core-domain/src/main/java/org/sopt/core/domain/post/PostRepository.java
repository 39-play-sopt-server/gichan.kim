package org.sopt.core.domain.post;

import org.sopt.core.support.PageResult;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(NewPost newPost);

    Optional<Post> findById(Long id);

    boolean existsById(Long id);

    List<Long> findIdsByMemberId(Long memberId);

    PageResult<Post> findAll(PostCategory category, String keyword, int page, int size);

    Optional<Post> update(Long id, String title, String content, PostCategory category);

    Optional<Post> increaseViewCount(Long id);

    boolean increaseLikeCount(Long id);

    boolean decreaseLikeCount(Long id);

    void decreaseLikeCountAll(List<Long> ids);

    void deleteById(Long id);

    void deleteAllByMemberId(Long memberId);
}
