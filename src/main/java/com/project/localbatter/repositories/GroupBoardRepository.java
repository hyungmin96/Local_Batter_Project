package com.project.localbatter.repositories;

import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupBoardRepository extends JpaRepository<GroupBoardEntity, Long> {
    Page<GroupBoardEntity> findAllBygroupId(Long groupId, Pageable request);
    Page<GroupBoardEntity> findAllBygroupIdAndType(Long groupId, GroupBoardEntity.BoardType type, Pageable pageable);

}
