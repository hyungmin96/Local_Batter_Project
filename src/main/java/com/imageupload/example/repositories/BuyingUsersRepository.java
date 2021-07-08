package com.imageupload.example.repositories;

import com.imageupload.example.entity.BuyingUsersEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyingUsersRepository extends JpaRepository<BuyingUsersEntity, Long>{
    
}
