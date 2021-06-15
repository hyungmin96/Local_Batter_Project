package com.imageupload.example.repositories;


import com.imageupload.example.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer>{
    
}
