package com.imageupload.example.repositories;

import com.imageupload.example.entity.GroupFileEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupFileRepository extends JpaRepository<GroupFileEntity, Long>{
    
}
