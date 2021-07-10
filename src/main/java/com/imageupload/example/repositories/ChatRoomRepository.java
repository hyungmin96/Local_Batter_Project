package com.imageupload.example.repositories;

import java.util.List;

import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.entity.UserJoinRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<UserJoinRoomEntity, Long>{
    List<UserJoinRoomEntity> findAllByuserVoOrTarget(UserEntity userVo, UserEntity target);
    UserJoinRoomEntity findByuserVoAndTarget(UserEntity userVo, UserEntity target);
}
