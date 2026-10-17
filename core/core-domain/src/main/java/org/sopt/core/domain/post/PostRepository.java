package org.sopt.core.domain.post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(NewPost newPost);

    Optional<Post> findById(Long id);

    boolean existsById(Long id);

    List<Post> findAll(PostCategory category, int page, int size);

    // 제목, 본문, 카테고리를 바꾼 결과를 돌려준다. 없으면 empty
    Optional<Post> update(Long id, String title, String content, PostCategory category);

    // 조회수를 1 올린 결과를 돌려준다. 없으면 empty
    Optional<Post> increaseViewCount(Long id);

    void deleteById(Long id);
}
