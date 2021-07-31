package com.project.localbatter.controllers;

import com.project.localbatter.components.createTime;
import com.project.localbatter.entity.BoardEntity;
import com.project.localbatter.entity.NotificationEntity;
import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.repositories.UserRepository;
import com.project.localbatter.services.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardService boardService;
    private final UserRepository userRepository;

    @GetMapping("/")
    public String home(Principal user, Model model, HttpServletRequest request){

        if(user != null){
            UserEntity userEntity = userRepository.findByUsername(user.getName()).get();
            
            NotificationEntity notificationEntity = userEntity.getNotification();
            
            HttpSession session = request.getSession();
            session.setAttribute("notification", notificationEntity);
        }

        Page<BoardEntity> boardList = boardService.getFastItems();
        model.addAttribute("fast", boardList.getContent());
        return "/board/articleList";
    }

    @GetMapping({"/board/search/products//search={search}"})
    public String searchBoardsCondition(Model model, @PathVariable String search){

        Page<BoardEntity> searchBoards = boardService.getSearchBoards(search);

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
    public String articleUpdate(Model model, @PathVariable Long id){
        BoardEntity vo = boardService.findBoard(id);
        model.addAttribute("updateData", vo);
        model.addAttribute("boardId", id);
        return "/board/articleUpdate";
    }

    @GetMapping("/board/article/{id}")
    public String viewBoard(Model model, @PathVariable Long id) throws IOException, ParseException{
        
        List<BoardEntity> topBoards = boardService.viewBoardRandomList();
        
        boardService.updateViewCount(id);
        
        BoardEntity board = boardService.findBoard(id);
        String displayTime = new createTime(board.getCreateTime()).getTimeDiff();
        
        model.addAttribute("board", board);
        model.addAttribute("createTime", displayTime);
        model.addAttribute("topBoards", topBoards);
        model.addAttribute("img", board.getFiles());
        return "/board/article";
    }

}