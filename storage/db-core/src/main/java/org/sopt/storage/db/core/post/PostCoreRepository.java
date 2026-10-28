package org.sopt.storage.db.core.post;

import org.sopt.core.domain.post.NewPost;
import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostCategory;
import org.sopt.core.domain.post.PostRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class PostCoreRepository implements PostRepository {

    private final PostJpaRepository postJpaRepository;

    public PostCoreRepository(PostJpaRepository postJpaRepository) {
        this.postJpaRepository = postJpaRepository;
    }

    @Override
    public Post save(NewPost newPost) {
        PostEntity saved = postJpaRepository.save(PostEntity.of(null, newPost));
        return saved.toDomain();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Post> findById(Long id) {
        return postJpaRepository.findById(id).map(PostEntity::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return postJpaRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Post> findAll(PostCategory category, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.ASC, "id"));
        List<PostEntity> entities = category == null
                ? postJpaRepository.findAll(pageable).getContent()
                : postJpaRepository.findAllByCategory(category, pageable).getContent();
        return entities.stream().map(PostEntity::toDomain).toList();
    }

    @Override
    public Optional<Post> update(Long id, String title, String content, PostCategory category) {
        return postJpaRepository.findById(id)
                .map(entity -> {
                    entity.update(title, content, category);
                    return entity.toDomain();
            });
    }

    @Override
    public Optional<Post> increaseViewCount(Long id) {
        return postJpaRepository.findById(id)
                .map(entity -> {
                    entity.viewed();
                    return entity.toDomain();
                });
    }

    @Override
    public void deleteById(Long id) {
        postJpaRepository.deleteById(id);
    }
}
