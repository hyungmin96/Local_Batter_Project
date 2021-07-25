package com.imageupload.example.repositories;

import com.imageupload.example.entity.GroupChatEntity;
import com.imageupload.example.entity.GroupEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import com.imageupload.example.entity.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupUsersRepository extends JpaRepository<GroupUsersEntity, Long>{
    GroupUsersEntity findByuserIdAndGroupEntity(Long userId, GroupEntity groupEntity);
    GroupUsersEntity findBygroupEntityAndUserId(GroupEntity groupEntity, Long userId);

    @EntityGraph(attributePaths = {"groupChatEntity"})
    @Query(value = "select a from GroupUsersEntity a")
    List<GroupChatEntity> findAllEntityGraph();
}
