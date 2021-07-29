package com.project.localbatter.repositories;

import java.util.List;

import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.entity.UserJoinRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<UserJoinRoomEntity, Long>{
    List<UserJoinRoomEntity> findAllByuserVoOrTarget(UserEntity userVo, UserEntity target);
    UserJoinRoomEntity findByuserVoAndTarget(UserEntity userVo, UserEntity target);
}
