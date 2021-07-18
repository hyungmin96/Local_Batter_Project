package com.imageupload.example.repositories;

import com.imageupload.example.entity.GroupChatRoomEntity;
import com.imageupload.example.entity.GroupUsersEntity;

import com.imageupload.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupUsersRepository extends JpaRepository<GroupUsersEntity, Long>{
    GroupUsersEntity findByGroupChatRoomEntityAndUser(GroupChatRoomEntity GroupChatRoomEntity, UserEntity User);
    GroupUsersEntity findByuser(UserEntity user);
}
