package org.sopt.storage.db.core.post;

import org.sopt.core.domain.post.PostCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostJpaRepository extends JpaRepository<PostEntity, Long> {

    Page<PostEntity> findAllByCategory(PostCategory category, Pageable pageable);

    @Query("select p.id from PostEntity p where p.memberId = :memberId")
    List<Long> findIdsByMemberId(@Param("memberId") Long memberId);

    @Query(
            value = """
                    SELECT * FROM post
                    WHERE MATCH(title) AGAINST(:keyword IN BOOLEAN MODE)
                    AND (:category IS NULL OR category = :category)
                    ORDER BY created_at DESC, id DESC
                    """,
            countQuery = """
                    SELECT COUNT(*) FROM post
                    WHERE MATCH(title) AGAINST(:keyword IN BOOLEAN MODE)
                    AND (:category IS NULL OR category = :category)
                    """,
            nativeQuery = true
    )
    Page<PostEntity> searchByTitle(@Param("keyword") String keyword, @Param("category") String category, Pageable pageable);

    @Modifying(clearAutomatically = true)
    @Query("update PostEntity p set p.viewCount = p.viewCount + 1 where p.id = :id")
    int increaseViewCount(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update PostEntity p set p.likeCount = p.likeCount + 1 where p.id = :id")
    int increaseLikeCount(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update PostEntity p set p.likeCount = case when p.likeCount > 0 then p.likeCount - 1 else 0 end where p.id = :id")
    int decreaseLikeCount(@Param("id") Long id);

    @Modifying(clearAutomatically = true)
    @Query("update PostEntity p set p.likeCount = case when p.likeCount > 0 then p.likeCount - 1 else 0 end where p.id in :ids")
    int decreaseLikeCountAll(@Param("ids") List<Long> ids);

    @Modifying(clearAutomatically = true)
    @Query("delete from PostEntity p where p.memberId = :memberId")
    int deleteAllByMemberId(@Param("memberId") Long memberId);
}
