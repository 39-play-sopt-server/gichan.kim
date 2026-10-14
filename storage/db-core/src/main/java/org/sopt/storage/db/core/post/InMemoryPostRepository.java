package org.sopt.storage.db.core.post;

import org.sopt.core.domain.post.NewPost;
import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class InMemoryPostRepository implements PostRepository {

    private final Map<Long, PostEntity> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    @Override
    public Post save(NewPost newPost) {
        Long id = sequence.incrementAndGet();
        PostEntity entity = PostEntity.of(id, newPost);
        store.put(id, entity);
        return entity.toDomain();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.ofNullable(store.get(id)).map(PostEntity::toDomain);
    }

    @Override
    public List<Post> findAll() {
        return store.values().stream()
                .sorted(Comparator.comparing(PostEntity::getId))
                .map(PostEntity::toDomain)
                .toList();
    }

    @Override
    public Post update(Long id, Post post) {
        PostEntity entity = PostEntity.from(post);
        store.put(id, entity);
        return entity.toDomain();
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}
