package com.project.localbatter.api;

import com.project.localbatter.components.createTime;
import com.project.localbatter.dto.PageDTO;
import com.project.localbatter.entity.BoardEntity;
import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.services.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class BoardRestControllers {

    private final BoardService boardService;

    @GetMapping("/boards/api/")
    public Page<BoardEntity> scrollData(@RequestParam int page, @RequestParam int display){

        PageRequest pageRequest = PageRequest.of(page, display, Sort.Direction.DESC, "id");
        Page<BoardEntity> boards = boardService.getAJaxBoardList(pageRequest); 

        boards.forEach(action ->{
            try {
                action.setDisplayDate(new createTime(action.getCreateTime()).getTimeDiff());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        return boards;
    }

    @GetMapping({"/board/products/search={search}&display={display}&order={order}&page={page}"})
    public Page<BoardEntity> searchBoardsData(PageDTO pageVo){
        
        Page<BoardEntity> searchBoards = boardService.getBoardList(pageVo);
        
        Integer[] pages = new Integer[searchBoards.getTotalPages()];
        for(int i = 0; i < pages.length; i ++){
            pages[i] = (i + 1);
        }

        return searchBoards;
    }

    @PostMapping("/upload")
    public String uploadFiles(HttpSession session, BoardEntity board, MultipartFile[] uploadFiles) throws IOException {

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        if (userEntity != null) {
            board.setWriter(userEntity.getUsername());
            boardService.boardWrite(board, uploadFiles);
            return "업로드 성공";
        }

        return "권한이 없습니다.";
    }

    @PostMapping("/board/delete")
    public ResponseEntity<String> deletePost(HttpSession session, @RequestBody Map<String, String> param) {

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        if (userEntity != null && (param.get("writerId").equals(userEntity.getUsername()))) {
            Long boardId = Long.parseLong(param.get("boardId"));
            boardService.boardDelete(boardId);
            return new ResponseEntity<String>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<String>("failed", HttpStatus.FORBIDDEN);
    }

    @PostMapping("/update")
    public String updatePost(HttpSession session, BoardEntity vo, MultipartFile[] uploadFiles, @RequestParam Integer[] deleteIndex) {

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        if (userEntity != null && (vo.getWriter().equals(userEntity.getUsername()))) {
            boardService.boardUpdate(vo, uploadFiles, deleteIndex);
            return "수정 성공";
        }
            return "권한이 없습니다.";
    }
}
