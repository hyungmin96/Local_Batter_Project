package com.imageupload.example.japrepositorys;

import com.imageupload.example.vo.FileVo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface imgJpaRepsitory extends JpaRepository<FileVo, Integer>{
    
}
