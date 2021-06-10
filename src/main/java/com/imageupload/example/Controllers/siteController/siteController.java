package com.imageupload.example.controllers.sitecontroller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.IntStream;
import com.imageupload.example.models.boardVo;
import com.imageupload.example.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class SiteController {
    
    @Autowired
    private BoardService boardService;
    
    @GetMapping("/")
    public String home(Model model){
        LinkedHashMap<String, List<boardVo>> map = boardService.getAllboards();
        model.addAttribute("general", map.get("general"));
        model.addAttribute("fast", map.get("fast"));
        return "/board/articleList";
    }

    @GetMapping("/{userId}/chatlist")
    public String getChatList(){
        return "/chat/chatRoomList";
    }

    @GetMapping("/chat/target={target}/room={roomid}")
    public String getChatRoom(){
        
        return "/chat/chatroom";
    }

    @GetMapping({"/board/search/products//search={search}"})
    public String searchBoardsCondition(Model model, @PathVariable String search){

        Page<boardVo> searchBoards = boardService.getSearchBoards(search);
        // jsp에서 page형식을 못 읽는 경우가있음 jsp에서 page<t>.content로 넘겨주자
        // List<Board> list = result.getContent();
        // page형식의 result 객체를 list에 담을때 getContent() 메소드 활용하면 사용가능

        int[] pages = new int[searchBoards.getTotalPages()];
        IntStream.range(0, pages.length).forEach(index ->{
            pages[index] = index + 1;
        });

        model.addAttribute("keyword", search);
        model.addAttribute("totalPages", pages);
        model.addAttribute("endPages", (int)Math.ceil((double)(searchBoards.getTotalPages() / 10.0)));
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
