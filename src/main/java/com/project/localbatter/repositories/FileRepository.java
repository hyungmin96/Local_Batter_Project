package com.project.localbatter.repositories;


import com.project.localbatter.entity.FileEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<FileEntity, Integer>{
    
}
