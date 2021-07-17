package com.imageupload.example.repositories;

import com.imageupload.example.entity.CommentEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import io.lettuce.core.dynamic.annotation.Param;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Page<CommentEntity> findAllBytarget(String target, Pageable request);

    @Query(value = "select sum(mannerScore) / count(mannerScore) from CommentEntity where target =:target", nativeQuery = true)
    float findAllBymannerScore(@Param("target") String target);
}
