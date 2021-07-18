package com.imageupload.example.repositories;

import com.imageupload.example.entity.GroupChatEntity;

import com.imageupload.example.entity.GroupChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GroupChatRepository extends JpaRepository<GroupChatEntity,Long>{
    List<GroupChatEntity> findAllByRoomId(GroupChatRoomEntity roomId);
}
