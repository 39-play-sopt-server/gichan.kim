package org.sopt.storage.db.core.post;

import org.sopt.core.domain.post.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

    Optional<PostEntity> findById(Long id);

    List<PostEntity> findAll();

    Page<PostEntity> findAllByCategory(PostCategory category, Pageable pageable);

    void deleteById(Long id);
}
