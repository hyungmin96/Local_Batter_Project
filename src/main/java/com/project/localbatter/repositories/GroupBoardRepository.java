package com.project.localbatter.repositories;

import com.project.localbatter.entity.GroupBoardEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroupBoardRepository extends JpaRepository<GroupBoardEntity, Long> {
    Page<GroupBoardEntity> findAllBygroupId(Long groupId, Pageable request);
    Page<GroupBoardEntity> findAllBygroupIdAndType(Long groupId, GroupBoardEntity.BoardType type, Pageable pageable);

    @Query(value = "select b from GroupBoardEntity b " +
            "join fetch b.groupUserJoinEntity g " +
            "where b.boardId = :boardId")
    GroupBoardEntity findUserIdUsingBoardId(Long boardId);
}
