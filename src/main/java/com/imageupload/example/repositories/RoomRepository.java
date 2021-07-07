package com.imageupload.example.repositories;

import com.imageupload.example.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoomRepository extends JpaRepository<RoomEntity, Long>{
    
}
