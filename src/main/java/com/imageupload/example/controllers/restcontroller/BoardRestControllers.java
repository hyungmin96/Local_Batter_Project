package com.imageupload.example.controllers.restcontroller;

import java.text.ParseException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.entity.BoardEntity;
import com.imageupload.example.components.createTime;
import com.imageupload.example.dto.PageableVo;
import com.imageupload.example.services.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
    public Page<BoardEntity> searchBoardsData(PageableVo pageVo){
        
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
    public String deletePost(@AuthenticationPrincipal UserEntity principal,
            @RequestBody Map<String, String> param) {

        if (principal != null && (param.get("writerId").equals(principal.getUsername()))) {
            Long boardId = Long.parseLong(param.get("boardId"));
            boardService.boardDelete(boardId);
            return "삭제 성공";
        }
        return "권한이 없습니다.";
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
