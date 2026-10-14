package org.sopt.core.domain.post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(NewPost newPost);

    Optional<Post> findById(Long id);

    List<Post> findAll();

    Post update(Long id, Post post);

    void deleteById(Long id);
}
