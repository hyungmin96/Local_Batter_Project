package com.project.localbatter.repositories;

import com.project.localbatter.entity.ChatEntity;
import com.project.localbatter.entity.RoomEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<ChatEntity, Long>{
    public Page<ChatEntity> findAllByroomEntity(RoomEntity roomEntity, Pageable request);
}
