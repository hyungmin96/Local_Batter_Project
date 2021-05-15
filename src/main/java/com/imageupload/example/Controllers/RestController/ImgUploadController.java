package com.imageupload.example.Controllers.RestController;

import java.io.IOException;

import com.imageupload.example.Services.boardService;
import com.imageupload.example.Vo.boardVo;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ImgUploadController {

    @Autowired
    private boardService BoardService;

    @PostMapping(value = "/upload")
    public String uploadFiles(boardVo board, MultipartFile[] uploadFiles) throws IOException {
        System.out.println("########################");
        System.out.println("title : " + board.getTitle());
        System.out.println("price : " + board.getPrice());
        System.out.println("des : " + board.getDescryption());
        System.out.println("writer : " + board.getWriter());
        System.out.println("Location : " + board.getLocation());
        System.out.println("########################");

        BoardService.boardWrite(board, uploadFiles);

        return "업로드 성공";

    }

}
