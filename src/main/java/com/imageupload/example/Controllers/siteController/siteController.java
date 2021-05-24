package com.imageupload.example.Controllers.SiteController;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import com.imageupload.example.Services.boardService;
import com.imageupload.example.Vo.UserPrincipalVo;
import com.imageupload.example.Vo.boardVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

        // try{
        //     Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        //     UserPrincipalVo userPrincipalVo = (UserPrincipalVo) auth.getPrincipal();
        //     model.addAttribute("principal", userPrincipalVo);
        // }catch(Exception ex){}

        model.addAttribute("general", map.get("general"));
        model.addAttribute("fast", map.get("fast"));

        return "/board/articleList";
    }

    @GetMapping("/board/article/search={search}")
    public String searchArticle(Model model, @PathVariable String search){

        List<boardVo> searchBoards = BoardService.searchBoards(search);
        model.addAttribute("searchBoards", searchBoards);
        return "/board/searchList";
    }

    @GetMapping("/write")
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
