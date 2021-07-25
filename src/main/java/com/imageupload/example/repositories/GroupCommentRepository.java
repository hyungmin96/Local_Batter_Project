package com.imageupload.example.repositories;

import com.imageupload.example.entity.GroupBoardEntity;
import com.imageupload.example.entity.GroupCommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupCommentRepository extends JpaRepository<GroupCommentEntity, Long>{
    List<GroupCommentEntity> findAllByGroupBoard(GroupBoardEntity GroupBoard);
    Page<GroupCommentEntity> findTop5ByGroupId(Long groupId, Pageable request);
}
