package org.sopt.storage.db.core.post;

import org.sopt.core.domain.post.NewPost;
import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostCategory;
import org.sopt.core.domain.post.PostRepository;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@Repository
public class InMemoryPostRepository implements PostRepository {

    private final Map<Long, PostEntity> store = new ConcurrentHashMap<>();
    private final AtomicLong sequence = new AtomicLong();

    // 생성: id 발급은 AtomicLong의 CAS, 저장은 고유 key put. 둘 다 원자적이라 잠금이 필요 없다
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
    public boolean existsById(Long id) {
        return store.containsKey(id);
    }

    @Override
    public List<Post> findAll(PostCategory category, int page, int size) {
        Stream<PostEntity> entities = store.values()
                .stream()
                .sorted(Comparator.comparing(PostEntity::getId));
        if (category != null) {
            entities = entities.filter(entity -> entity.getCategory() == category);
        }
        return page(entities, page, size);
    }

    // 임계구역: 한 게시글을 "읽고 → 바꾸고 → 쓰는" 구간.
    // computeIfPresent는 같은 key의 람다를 한 번에 하나만 실행하므로 게시글 단위로 직렬화된다.
    // 없는 key에는 아무것도 하지 않아 삭제된 글이 되살아나지 않는다.
    @Override
    public Optional<Post> update(Long id, String title, String content, PostCategory category) {
        return Optional.ofNullable(store.computeIfPresent(id, (key, entity) -> entity.updated(title, content, category)))
                .map(PostEntity::toDomain);
    }

    @Override
    public Optional<Post> increaseViewCount(Long id) {
        return Optional.ofNullable(store.computeIfPresent(id, (key, entity) -> entity.viewed()))
                .map(PostEntity::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }

    private List<Post> page(Stream<PostEntity> entities, int page, int size) {
        return entities.skip((long) (page - 1) * size).limit(size).map(PostEntity::toDomain).toList();
    }
}
