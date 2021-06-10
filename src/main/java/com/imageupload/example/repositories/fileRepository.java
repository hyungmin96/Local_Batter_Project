package com.imageupload.example.repositories;


import com.imageupload.example.models.fileVo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface fileRepository extends JpaRepository<fileVo, Integer>{
    
}
