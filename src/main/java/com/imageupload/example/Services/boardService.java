package com.imageupload.example.Services;


import java.io.File;
import java.util.List;

import com.imageupload.example.Components.boardServiceMethod.generateFile;
import com.imageupload.example.JpaRepositories.boardRepository;
import com.imageupload.example.JpaRepositories.fileRepository;
import com.imageupload.example.Vo.boardVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class boardService {

    @Autowired
    private boardRepository boardRep;

    @Autowired
    private fileRepository fileRep;

    public void boardWrite(boardVo vo, MultipartFile[] uploadFiles) {
        boardRep.save(vo);
        if (uploadFiles != null && uploadFiles.length > 0) {
            generateFile gen = new generateFile(vo, uploadFiles);
            fileRep.saveAll(gen.generateFileVoList());
        }
    }

    public void boardDelete(int id) {

        boardVo vo = boardRep.findById(id).orElse(null);
        if (vo != null)
            vo.getFiles().forEach(element -> new File(element.getFilePath()).delete());

        boardRep.deleteById(id);
    }

    public List<boardVo> getBoardList(){
        return boardRep.findAllByOrderByIdDesc();
    }

    public boardVo findBoard(int id){
        return boardRep.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("정보없음");
        });
    }

}
