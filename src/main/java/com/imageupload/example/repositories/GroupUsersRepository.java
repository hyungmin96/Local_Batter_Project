package com.imageupload.example.repositories;

import com.imageupload.example.entity.GroupEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import com.imageupload.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupUsersRepository extends JpaRepository<GroupUsersEntity, Long>{
    GroupUsersEntity findBygroupEntityAndUserId(GroupEntity groupEntity, Long UserId);
    GroupUsersEntity findByUserId(Long UserId);
}
