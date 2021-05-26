package com.imageupload.example.Controllers.SiteController;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import com.imageupload.example.Services.BoardService;
import com.imageupload.example.Vo.boardVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class siteController {
    
    @Autowired
    private BoardService boardService;
    
    @GetMapping("/")
    public String home(Model model){

        LinkedHashMap<String, List<boardVo>> map = boardService.getBoardList();

        model.addAttribute("general", map.get("general"));
        model.addAttribute("fast", map.get("fast"));

        return "/board/articleList";
    }

    @GetMapping("/board/article/search={search}")
    public String searchArticle(Model model, @PathVariable String search){

        List<boardVo> searchBoards = boardService.searchBoards(search);
        model.addAttribute("searchBoards", searchBoards);
        return "/board/searchList";
    }

    @GetMapping("/write")
    public String articleWrite(){
        return "/board/articleWrite";
    }

    @GetMapping("/board/article/update/{id}")
    public String articleUpdate(Model model, @PathVariable int id){
        boardVo vo = boardService.findBoard(id);
        model.addAttribute("updateData", vo);
        model.addAttribute("boardId", id);
        return "/board/articleUpdate";
    }

    @GetMapping("/board/article/{id}")
    public String viewBoard(Model model, @PathVariable int id) throws IOException{
        boardVo board = boardService.findBoard(id);

        Page<boardVo> topBoards = boardService.getBoardList(3);

        model.addAttribute("board", board);
        model.addAttribute("topBoards", topBoards);
        model.addAttribute("img", board.getFiles());
        return "/board/article";
    }

}
