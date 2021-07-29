package com.project.localbatter.repositories;

import com.project.localbatter.entity.GroupBoardFileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupBoardFileRepository  extends JpaRepository<GroupBoardFileEntity, Long> {
    Page<GroupBoardFileEntity> findTop9BygroupId(Long groupId, Pageable request);
}
