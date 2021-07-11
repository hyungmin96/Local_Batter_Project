package com.imageupload.example.repositories;

import com.imageupload.example.entity.BuyingChatRoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BuyingChatRoomRepository extends JpaRepository<BuyingChatRoomEntity, Long>{
    Page<BuyingChatRoomEntity> findAll(Pageable request);

    @Modifying
    @Query(value = "update buyingchatroomentity set currentUsers = currentUsers + 1 where roomId =:roomId", nativeQuery = true)
    void updateCurrentUsers(Long roomId);
}
