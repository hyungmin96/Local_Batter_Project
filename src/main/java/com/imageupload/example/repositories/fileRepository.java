package com.imageupload.example.repositories;


import com.imageupload.example.entity.fileEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface fileRepository extends JpaRepository<fileEntity, Integer>{
    
}
