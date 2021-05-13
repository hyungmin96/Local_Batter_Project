package com.imageupload.example.japrepositorys;

import com.imageupload.example.vo.FileUploadMapper;

import org.springframework.data.jpa.repository.JpaRepository;

public interface imgJpaRepsitory extends JpaRepository<FileUploadMapper, Integer>{
    
}
