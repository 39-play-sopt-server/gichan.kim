package org.sopt.core.domain.post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(NewPost newPost);

    Optional<Post> findById(Long id);

    boolean existsById(Long id);

    List<Post> findAll(PostCategory category, int page, int size);

    Optional<Post> update(Long id, String title, String content, PostCategory category);

    Optional<Post> increaseViewCount(Long id);

    void deleteById(Long id);
}
