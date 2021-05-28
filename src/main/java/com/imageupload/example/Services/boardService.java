package com.imageupload.example.Services;


import java.io.File;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.transaction.Transactional;
import com.imageupload.example.Components.boardServiceMethod.createTime;
import com.imageupload.example.Components.boardServiceMethod.generateFile;
import com.imageupload.example.JpaRepositories.BoardRepository;
import com.imageupload.example.JpaRepositories.fileRepository;
import com.imageupload.example.Vo.PageableVo;
import com.imageupload.example.Vo.boardVo;
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

    public void boardWrite(boardVo vo, MultipartFile[] uploadFiles) {
        boardRep.save(vo);
        if (uploadFiles != null && uploadFiles.length > 0) {
            generateFile gen = new generateFile(vo, uploadFiles);
            fileRep.saveAll(gen.generateFileVoList());
        }
    }

    @Transactional
    public void boardUpdate(boardVo vo, MultipartFile[] uploadFiles, Integer[] deleteArr) {
        
        boardVo inputVo = boardRep.findById(vo.getId()).orElse(null);

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
        boardVo vo = boardRep.findById(id).orElse(null);
        if (vo != null)
            vo.getFiles().forEach(element -> new File(element.getFilePath()).delete());

        boardRep.deleteById(id);
    }

    public Page<boardVo> searchBoards(String search, PageRequest page){
        Page<boardVo> items = boardRep.findByTitleContainingOrderByIdDesc(search, page);
        return items;
    }

    public LinkedHashMap<String, List<boardVo>> getAllboards(){

        Page<boardVo> boards = boardRep.findAll(PageRequest.of(0, 30, Sort.Direction.DESC, "id"));

        List<boardVo> generalBoards = new ArrayList<>();
        List<boardVo> fastBoards = new ArrayList<>();

        LinkedHashMap<String, List<boardVo>> map = new LinkedHashMap<>();

        boards.forEach(action -> {
            try {
                action.setDisplayDate(new createTime(action.getCreateTime()).getTimeDiff());
                if(action.getCategory().equals("일반"))
                    generalBoards.add(action);
                else
                    fastBoards.add(action);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        map.put("general", generalBoards);
        map.put("fast", fastBoards);

        return map;
    }
    
    public Page<boardVo> getSearchBoards(String search) {
        PageRequest pageable = PageRequest.of(0, 30);
        Page<boardVo> board = boardRep.findByTitleContaining(search, pageable);
        return board;
    }

    public Page<boardVo> getBoardList(PageableVo page) {
        PageRequest pageable = page.getPageRequest();
        Page<boardVo> board = boardRep.findByTitleContaining(page.getSearch(), pageable);
        return board;
    }

    public boardVo findBoard(int id){
        return boardRep.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("정보없음");
        });
    }

    public Page<boardVo> getTopBoard(Pageable page){
        return boardRep.findAll(page);
    }

}
