package com.imageupload.example.Services;

import com.imageupload.example.JpaRepositories.boardRepository;
import com.imageupload.example.JpaRepositories.fileRepository;
import com.imageupload.example.Vo.boardVo;
import com.imageupload.example.methods.boardServiceMethod.generateFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class boardService {
    
    @Autowired
    private boardRepository boardRep;

    @Autowired 
    private fileRepository fileRep;

    public void boardWrite(boardVo vo, MultipartFile[] uploadFiles){

        generateFile gen = new generateFile(uploadFiles);
        gen.createFile();
        
        fileRep.saveAll(gen.generateFileVoList());

        boardRep.save(vo);
    }

}
