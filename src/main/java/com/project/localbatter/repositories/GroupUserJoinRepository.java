package com.project.localbatter.repositories;

import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.entity.GroupUserJoinEntity;
import com.project.localbatter.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupUserJoinRepository extends JpaRepository<GroupUserJoinEntity, Long> {
    GroupUserJoinEntity findByGroupAndUser(GroupEntity Group, UserEntity User);
}
