package com.imageupload.example.repositories;

import com.imageupload.example.entity.BuyingChatRoomEntity;
import com.imageupload.example.entity.BuyingUsersEntity;

import com.imageupload.example.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyingUsersRepository extends JpaRepository<BuyingUsersEntity, Long>{
    BuyingUsersEntity findBybuyingChatRoomEntityAndUser(BuyingChatRoomEntity buyingChatRoomEntity, UserEntity User);
}
