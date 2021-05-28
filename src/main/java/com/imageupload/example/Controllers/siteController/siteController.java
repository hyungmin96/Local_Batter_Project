package com.imageupload.example.Controllers.SiteController;

import java.io.IOException;
import java.text.ParseException;
import java.util.LinkedHashMap;
import java.util.List;

import com.imageupload.example.Components.boardServiceMethod.createTime;
import com.imageupload.example.Services.BoardService;
import com.imageupload.example.Vo.PageableVo;
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
        LinkedHashMap<String, List<boardVo>> map = boardService.getAllboards();
        model.addAttribute("general", map.get("general"));
        model.addAttribute("fast", map.get("fast"));
        return "/board/articleList";
    }

    @GetMapping({"/board/article/search={search}", 
    "/board/search={search}&display={display}&order={order}&page={page}"})
    public String searchBoardsCondition(Model model, PageableVo pageVo){

        Page<boardVo> searchBoards;

        if(pageVo.getOrder() == null)
            searchBoards = boardService.searchBoards(pageVo.getSearch(), PageRequest.of(0, 30));
        else
            searchBoards = boardService.getBoardList(pageVo);
        // jsp에서 page형식을 못 읽는 경우가있음 jsp에서 page<t>.content로 넘겨주자
        // List<Board> list = result.getContent();
        // page형식의 result 객체를 list에 담을때 getContent() 메소드 활용하면 사용가능

        searchBoards.forEach(action -> {
            try {
                action.setDisplayDate(new createTime(action.getCreateTime()).getTimeDiff());
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });

        Integer[] pages = new Integer[searchBoards.getTotalPages()];
        for(int i = 0; i < pages.length; i ++){
            pages[i] = (i + 1);
        }

        model.addAttribute("keyword", pageVo.getSearch());
        model.addAttribute("totalPages", pages);
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

        PageRequest page = PageRequest.of(0, 6, Sort.Direction.DESC, "id");

        Page<boardVo> topBoards = boardService.getTopBoard(page);

        model.addAttribute("board", board);
        model.addAttribute("topBoards", topBoards);
        model.addAttribute("img", board.getFiles());
        return "/board/article";
    }

}
