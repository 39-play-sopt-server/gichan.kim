package org.sopt.core.domain.post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {

    Post save(NewPost newPost);

    Optional<Post> findByIndex(int index);

    List<Post> findAll();

    Post update(int index, Post post);

    void deleteByIndex(int index);
}
