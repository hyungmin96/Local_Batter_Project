package com.imageupload.example.Controllers.siteController;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import com.imageupload.example.Services.boardService;
import com.imageupload.example.Vo.boardVo;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class siteController {
    
    @Autowired
    private boardService BoardService;
    
    @GetMapping("/")
    public String home(Model model){

        LinkedHashMap<String, List<boardVo>> map = BoardService.getBoardList();

        List<boardVo> general = map.get("general");
        List<boardVo> fast = map.get("fast");
        
        model.addAttribute("general", general);
        model.addAttribute("fast", fast);

        return "/board/articleList";
    }

    @GetMapping("/board/article/search={search}")
    public String searchArticle(Model model, @PathVariable String search){

        List<boardVo> searchBoards = BoardService.searchBoards(search);
        model.addAttribute("searchBoards", searchBoards);
        return "/board/searchList";
    }

    @GetMapping("/board/article/write")
    public String articleWrite(){
        return "/board/articleWrite";
    }

    @GetMapping("/board/article/update/{id}")
    public String articleUpdate(Model model, @PathVariable int id){
        boardVo vo = BoardService.findBoard(id);
        model.addAttribute("updateData", vo);
        model.addAttribute("boardId", id);
        return "/board/articleUpdate";
    }

    @GetMapping("/board/article/{id}")
    public String viewBoard(Model model, @PathVariable int id) throws IOException{
        boardVo board = BoardService.findBoard(id);
        model.addAttribute("board", board);
        model.addAttribute("img", board.getFiles());
        return "/board/article";
    }

}
