package com.imageupload.example.repositories;

import com.imageupload.example.entity.BuyingChatRoomEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyingChatRoomRepository extends JpaRepository<BuyingChatRoomEntity, Long>{
    Page<BuyingChatRoomEntity> findAll(Pageable request);
    Page<BuyingChatRoomEntity> findAllByOwner(String owner, Pageable request);
}
