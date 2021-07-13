package com.imageupload.example.repositories;

import com.imageupload.example.entity.BuyingChatEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BuyingChatRepository extends JpaRepository<BuyingChatEntity,Long>{
    List<BuyingChatEntity> findAllByRoomId(Long roomId);
}
