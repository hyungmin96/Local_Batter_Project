package com.imageupload.example.repositories;

import com.imageupload.example.entity.ChatEntity;
import com.imageupload.example.entity.RoomEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, Long>{
    public Page<ChatEntity> findAllByroomEntity(RoomEntity roomEntity, Pageable request);
}
