package com.project.localbatter.repositories;

import com.project.localbatter.entity.GroupBoardFileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupBoardFileRepository  extends JpaRepository<GroupBoardFileEntity, Long> {
    List<GroupBoardFileEntity> findTop9BygroupId(Long groupId);
}
