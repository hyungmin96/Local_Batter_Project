package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.dto.GroupBoardDTO;
import com.imageupload.example.entity.GroupBoardEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import com.imageupload.example.services.GroupBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupRestController {

    private final GroupBoardService groupGroupService;

    @PostMapping("/board/delete")
    public ResponseEntity<String> deletePost(@RequestParam Long boardId, @RequestParam String username){
        groupGroupService.delete(boardId, username);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/board/post")
    public ResponseEntity<String> groupPostContent(HttpSession session , GroupBoardDTO groupBoardDTO){
        GroupUsersEntity groupUsersEntity = (GroupUsersEntity) session.getAttribute("group_user_entity");
        groupGroupService.post(groupUsersEntity, groupBoardDTO);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @GetMapping("/get_board_list")
    public Page<GroupBoardEntity> getBoardList(@RequestParam Long groupId, @RequestParam int display, @RequestParam int page){
        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "boardId");
        return groupGroupService.getBoardList(groupId, request);
    }

}
