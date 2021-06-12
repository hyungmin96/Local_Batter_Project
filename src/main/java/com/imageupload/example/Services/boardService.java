package com.imageupload.example.services;


import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.transaction.Transactional;

import com.imageupload.example.components.boardServiceMethod.createTime;
import com.imageupload.example.components.boardServiceMethod.generateFile;
import com.imageupload.example.entity.boardEntity;
import com.imageupload.example.models.PageableVo;
import com.imageupload.example.repositories.BoardRepository;
import com.imageupload.example.repositories.fileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRep;

    @Autowired
    private fileRepository fileRep;

    public void boardWrite(boardEntity vo, MultipartFile[] uploadFiles) {
        boardRep.save(vo);
        if (uploadFiles != null && uploadFiles.length > 0) {
            generateFile gen = new generateFile(vo, uploadFiles);
            fileRep.saveAll(gen.generateFileVoList());
        }
    }

    @Transactional
    public void boardUpdate(boardEntity vo, MultipartFile[] uploadFiles, Integer[] deleteArr) {
        
        boardEntity inputVo = boardRep.findById(vo.getId()).orElse(null);

        if (deleteArr != null) {
            for (Integer index : deleteArr) {
                if (index >= 0) {
                    Integer item = inputVo.getFiles().get(index).getFileid();
                    new File(inputVo.getFiles().get(index).getFilePath()).delete();
                    fileRep.deleteById(item);
                }
            }
        }

        if(inputVo != null){
            if (uploadFiles != null && uploadFiles.length > 0) {
                generateFile gen = new generateFile(vo, uploadFiles);
                fileRep.saveAll(gen.generateFileVoList());
            }

            vo.setCreateTime(inputVo.getCreateTime());
            boardRep.save(vo);
        }
    }

    public void boardDelete(int id) {
        boardEntity vo = boardRep.findById(id).orElse(null);
        if (vo != null)
            vo.getFiles().forEach(element -> new File(element.getFilePath()).delete());

        boardRep.deleteById(id);
    }

    public Page<boardEntity> searchBoards(String search, PageRequest page){
        Page<boardEntity> items = boardRep.findByTitleContainingOrderByIdDesc(search, page);
        return items;
    }

    public Page<boardEntity> getFastItems(){

        Page<boardEntity> boards = boardRep.findAllBycategory("긴급" ,PageRequest.of(0, 30, Sort.Direction.DESC, "id"));

        //new createTime(action.getCreateTime()).getTimeDiff()
        return boards;
    }
    
    public Page<boardEntity> getSearchBoards(String search) {
        PageRequest pageable = PageRequest.of(0, 30);
        Page<boardEntity> board = boardRep.findByTitleContaining(search, pageable);

        board.forEach(action -> {
            try {
                action.setDisplayDate(new createTime(action.getCreateTime()).getTimeDiff());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        return board;
    }

    public Page<boardEntity> getBoardList(PageableVo page) {
        PageRequest pageable = page.getPageRequest();
        Page<boardEntity> board = boardRep.findByTitleContaining(page.getSearch(), pageable);
        
        board.forEach(action -> {
            try {
                action.setDisplayDate(new createTime(action.getCreateTime()).getTimeDiff());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        return board;
    }

    public Page<boardEntity> getAJaxBoardList(PageRequest page) {
        Page<boardEntity> board = boardRep.findAllBycategory("일반", page);
        return board;
    }

    public boardEntity findBoard(int id){
        return boardRep.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("정보없음");
        });
    }

    public Page<boardEntity> getTopBoard(Pageable page){
        return boardRep.findAll(page);
    }

}
