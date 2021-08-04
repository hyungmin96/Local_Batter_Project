package com.project.localbatter.repositories;

import com.project.localbatter.entity.GroupBoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface GroupBoardFileRepository  extends JpaRepository<GroupBoardFileEntity, Long> {
    List<GroupBoardFileEntity> findTop9BygroupId(Long groupId);

    @Transactional
    @Modifying
    @Query(value = "delete from GroupBoardFileEntity f where f.id = :id")
    public void deleteItem(Long id);
}
