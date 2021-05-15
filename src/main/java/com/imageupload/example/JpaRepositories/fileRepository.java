package com.imageupload.example.JpaRepositories;


import com.imageupload.example.Vo.fileVo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface fileRepository extends JpaRepository<fileVo, Integer>{
    
}
