package com.imageupload.example.Controllers.RestController;

import java.io.IOException;
import java.util.Map;

import com.imageupload.example.Services.boardService;
import com.imageupload.example.Vo.UserPrincipalVo;
import com.imageupload.example.Vo.boardVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImgUploadController {

    @Autowired
    private boardService BoardService;

    @PostMapping("/upload")
    public String uploadFiles(@AuthenticationPrincipal UserPrincipalVo principal, boardVo board,
            MultipartFile[] uploadFiles) throws IOException {

        // Authentication authentication =
        // SecurityContextHolder.getContext().getAuthentication();
        // String currentPrincipalName = authentication.getName();
        if (principal != null) {
            board.setWriter(principal.getUsername());
            BoardService.boardWrite(board, uploadFiles);
            return "업로드 성공";
        }

        return "권한이 없습니다.";
    }

    @PostMapping("/board/delete")
    public String deletePost(@AuthenticationPrincipal UserPrincipalVo principal,
            @RequestBody Map<String, String> param) {

        if (principal != null && (param.get("writerId").equals(principal.getUserVo().getUsername()))) {
            int boardId = Integer.parseInt(param.get("boardId"));
            BoardService.boardDelete(boardId);
            return "삭제 성공";
        }
        return "권한이 없습니다.";
    }

    @PostMapping("/update")
    public String updatePost(@AuthenticationPrincipal UserPrincipalVo principal, boardVo vo,
            MultipartFile[] uploadFiles, @RequestParam Integer[] deleteIndex) {
                
        if (principal != null && (vo.getWriter().equals(principal.getUserVo().getUsername()))) {
            BoardService.boardUpdate(vo, uploadFiles, deleteIndex);
            return "수정 성공";
        }
            return "권한이 없습니다.";
    }
}
