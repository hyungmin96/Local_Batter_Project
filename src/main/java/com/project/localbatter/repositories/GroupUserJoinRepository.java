package com.project.localbatter.repositories;

import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.entity.GroupUserJoinEntity;
import com.project.localbatter.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface GroupUserJoinRepository extends JpaRepository<GroupUserJoinEntity, Long> {

    @Transactional
    @Modifying
    @Query("delete from GroupUserJoinEntity g where g.group = :group and g.user = :user")
    void deleteByGroup(UserEntity user, GroupEntity group);
}

