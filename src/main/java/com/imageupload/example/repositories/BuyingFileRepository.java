package com.imageupload.example.repositories;

import com.imageupload.example.entity.BuyingFileEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyingFileRepository extends JpaRepository<BuyingFileEntity, Long>{
    
}
