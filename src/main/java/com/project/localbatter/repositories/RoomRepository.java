package com.project.localbatter.repositories;

import com.project.localbatter.entity.RoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoomRepository extends JpaRepository<RoomEntity, Long>{
    
}
