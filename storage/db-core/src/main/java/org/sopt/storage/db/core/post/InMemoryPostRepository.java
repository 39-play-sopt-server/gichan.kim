package org.sopt.storage.db.core.post;

import org.sopt.core.domain.post.NewPost;
import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class InMemoryPostRepository implements PostRepository {

    private final List<PostEntity> store = new ArrayList<>();

    @Override
    public Post save(NewPost newPost) {
        PostEntity entity = PostEntity.of(newPost);
        store.add(entity);
        return entity.toDomain();
    }

    @Override
    public Optional<Post> findByIndex(int index) {
        if (index < 0 || index >= store.size()) {
            return Optional.empty();
        }
        return Optional.of(store.get(index).toDomain());
    }

    @Override
    public List<Post> findAll() {
        return store.stream()
                .map(PostEntity::toDomain)
                .toList();
    }

    @Override
    public Post update(int index, Post post) {
        PostEntity entity = PostEntity.from(post);
        store.set(index, entity);
        return entity.toDomain();
    }

    @Override
    public void deleteByIndex(int index) {
        store.remove(index);
    }
}
