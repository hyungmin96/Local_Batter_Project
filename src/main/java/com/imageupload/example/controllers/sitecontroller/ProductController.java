package com.imageupload.example.controllers.sitecontroller;

import java.util.List;
import com.imageupload.example.entity.BoardEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProductController {
    
    @GetMapping("/transaction/itemList")
    public List<BoardEntity> getTransactionBoardList(){
        return null;
    }

}
