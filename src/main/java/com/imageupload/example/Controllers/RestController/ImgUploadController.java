package com.imageupload.example.Controllers.RestController;

import java.io.IOException;
import java.util.Map;

import com.imageupload.example.Services.boardService;
import com.imageupload.example.Vo.boardVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImgUploadController {

    @Autowired
    private boardService BoardService;

    @PostMapping(value = "/upload")
    public String uploadFiles(boardVo board, MultipartFile[] uploadFiles) throws IOException {
        BoardService.boardWrite(board, uploadFiles);
        return "업로드 성공";
    }

    @PostMapping("/board/delete")
    public String deletePost(@RequestBody Map<String, String> param){
        int boardId = Integer.parseInt(param.get("boardId"));
        BoardService.boardDelete(boardId);
        return "삭제 성공";
    }

    @PostMapping("/update")
    public String updatePost(boardVo vo, MultipartFile[] uploadFiles, @RequestParam Integer[] deleteIndex){
        BoardService.boardUpdate(vo, uploadFiles, deleteIndex);
        return "수정 성공";
    }
    

}
