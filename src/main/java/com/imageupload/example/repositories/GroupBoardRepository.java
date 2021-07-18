package com.imageupload.example.repositories;

import com.imageupload.example.entity.GroupBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupBoardRepository extends JpaRepository<GroupBoardEntity, Long> {
    GroupBoardEntity findByboardId(Long boardId);
    Page<GroupBoardEntity> findAllBygroupId(Long groupId, Pageable request);
}
