package org.sopt.storage.db.core.post;

import org.sopt.core.domain.post.NewPost;
import org.sopt.core.domain.post.Post;
import org.sopt.core.domain.post.PostCategory;
import org.sopt.core.domain.post.PostRepository;
import org.sopt.core.support.PageResult;
import org.springframework.data.domain.Page;
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
        PostEntity saved = postJpaRepository.save(PostEntity.of(newPost));
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
    public List<Long> findIdsByMemberId(Long memberId) {
        return postJpaRepository.findIdsByMemberId(memberId);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<Post> findAll(PostCategory category, String keyword, int page, int size) {
        Page<PostEntity> entities = keyword == null || keyword.isBlank()
                ? findByCategory(category, page, size)
                : searchByTitle(category, keyword, page, size);
        return new PageResult<>(
                entities.getContent().stream().map(PostEntity::toDomain).toList(),
                page,
                size,
                entities.getTotalElements()
        );
    }

    private Page<PostEntity> findByCategory(PostCategory category, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by(Sort.Direction.DESC, "createdAt").and(Sort.by(Sort.Direction.DESC, "id")));
        return category == null
                ? postJpaRepository.findAll(pageable)
                : postJpaRepository.findAllByCategory(category, pageable);
    }

    private Page<PostEntity> searchByTitle(PostCategory category, String keyword, int page, int size) {
        String phrase = "\"" + keyword.replace("\"", "") + "\"";
        String categoryName = category == null ? null : category.name();
        return postJpaRepository.searchByTitle(phrase, categoryName, PageRequest.of(page - 1, size));
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
        if (postJpaRepository.increaseViewCount(id) == 0) {
            return Optional.empty();
        }
        return postJpaRepository.findById(id).map(PostEntity::toDomain);
    }

    @Override
    public boolean increaseLikeCount(Long id) {
        return postJpaRepository.increaseLikeCount(id) > 0;
    }

    @Override
    public boolean decreaseLikeCount(Long id) {
        return postJpaRepository.decreaseLikeCount(id) > 0;
    }

    @Override
    public void decreaseLikeCountAll(List<Long> ids) {
        postJpaRepository.decreaseLikeCountAll(ids);
    }

    @Override
    public void deleteById(Long id) {
        postJpaRepository.deleteById(id);
    }

    @Override
    public void deleteAllByMemberId(Long memberId) {
        postJpaRepository.deleteAllByMemberId(memberId);
    }
}
