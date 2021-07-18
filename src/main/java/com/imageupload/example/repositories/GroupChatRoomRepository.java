package com.imageupload.example.repositories;

import com.imageupload.example.entity.GroupChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface GroupChatRoomRepository extends JpaRepository<GroupChatRoomEntity, Long>{
    @Transactional
    @Modifying
    @Query(value = "update GroupChatRoomEntity set currentUsers = currentUsers + 1 where roomId =:roomId", nativeQuery = true)
    void enterCurrentUsers(Long roomId);

    @Transactional
    @Modifying
    @Query(value = "update GroupChatRoomEntity set currentUsers = currentUsers - 1 where roomId =:roomId", nativeQuery = true)
    void exitCurrentUsers(Long roomId);
}
