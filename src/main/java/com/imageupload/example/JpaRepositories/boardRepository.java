package com.imageupload.example.JpaRepositories;

import com.imageupload.example.Vo.boardVo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface boardRepository extends JpaRepository<boardVo, Integer>{
    
}
