package com.project.localbatter.services;

import java.io.File;
import java.text.ParseException;
import java.util.List;
import javax.transaction.Transactional;
import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.components.createTime;
import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.dto.PageDTO;
import com.project.localbatter.entity.BoardEntity;
import com.project.localbatter.entity.FileEntity;
import com.project.localbatter.repositories.BoardRepository;
import com.project.localbatter.repositories.FileRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRep;
    private final FileRepository fileRep;

    public List<BoardEntity> viewBoardRandomList(){
        return boardRep.viewRandomBoardList();
    }

    @org.springframework.transaction.annotation.Transactional
    public void updateViewCount(Long id){
        boardRep.updateView(id);
    }

    @org.springframework.transaction.annotation.Transactional
    public void updateCartCount(Long id){
        boardRep.updateCart(id);
    }

    public void boardWrite(BoardEntity vo, MultipartFile[] uploadFiles) {
        boardRep.save(vo);
        if (uploadFiles != null && uploadFiles.length > 0) {

            List<GenerateFileDTO> files = new GenerateFile(uploadFiles).createFile();

            for(GenerateFileDTO file : files){
                FileEntity fileEntity = FileEntity.builder()
                        .fileSize(file.getFileSize())
                        .filePath(file.getPath())
                        .tempName(file.getFileName())
                        .originName(file.getFileName())
                        .board(vo)
                        .build();

                fileRep.save(fileEntity);
            }
        }
    }

    @Transactional
    public void boardUpdate(BoardEntity vo, MultipartFile[] uploadFiles, Integer[] deleteArr) {
        
        BoardEntity inputVo = boardRep.findById(vo.getId()).orElse(null);

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
                List<GenerateFileDTO> files = new GenerateFile(uploadFiles).createFile();

                for(GenerateFileDTO file : files){
                    FileEntity fileEntity = FileEntity.builder()
                            .fileSize(file.getFileSize())
                            .filePath(file.getPath())
                            .originName(file.getFileName())
                            .board(vo)
                            .build();

                    fileRep.save(fileEntity);
                }
            }

            vo.setCreateTime(inputVo.getCreateTime());
            boardRep.save(vo);
        }
    }

    public void boardDelete(Long id) {
        BoardEntity vo = boardRep.findById(id).orElse(null);
        if (vo != null)
            vo.getFiles().forEach(element -> new File(element.getFilePath()).delete());

        boardRep.deleteById(id);
    }

    public Page<BoardEntity> getFastItems(){
        Page<BoardEntity> boards = boardRep.findAllBycategory("긴급" ,PageRequest.of(0, 30, Sort.Direction.DESC, "id"));
        return boards;
    }
    
    public Page<BoardEntity> getSearchBoards(String search) {
        PageRequest pageable = PageRequest.of(0, 30);
        Page<BoardEntity> board = boardRep.findByTitleContaining(search, pageable);

        board.forEach(action -> {
            try {
                action.setDisplayDate(new createTime(action.getCreateTime()).getTimeDiff());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        return board;
    }

    public Page<BoardEntity> getBoardList(PageDTO page) {
        PageRequest pageable = page.getPageRequest();
        Page<BoardEntity> board = boardRep.findByTitleContaining(page.getSearch(), pageable);
        
        board.forEach(action -> {
            try {
                action.setDisplayDate(new createTime(action.getCreateTime()).getTimeDiff());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        return board;
    }

    public Page<BoardEntity> getAJaxBoardList(PageRequest page) {
        Page<BoardEntity> board = boardRep.findAllBycategory("일반", page);
        return board;
    }

    public BoardEntity findBoard(Long id){
        return boardRep.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("정보없음");
        });
    }

}
