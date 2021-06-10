package com.imageupload.example.repositories;

import com.imageupload.example.dto.UserJoinRoomDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<UserJoinRoomDTO, Long>{
    
}
