package com.project.localbatter.repositories;

import com.project.localbatter.entity.GroupBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupBoardRepository extends JpaRepository<GroupBoardEntity, Long> {
    GroupBoardEntity findByboardId(Long boardId);
    Page<GroupBoardEntity> findAllBygroupId(Long groupId, Pageable request);
    Page<GroupBoardEntity> findAllBygroupIdAndType(Long groupId, GroupBoardEntity.BoardType type, Pageable pageable);

}
