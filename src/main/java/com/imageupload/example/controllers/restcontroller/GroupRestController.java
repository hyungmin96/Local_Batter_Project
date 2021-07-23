package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.dto.GroupBoardDTO;
import com.imageupload.example.dto.GroupCommentDTO;
import com.imageupload.example.dto.GroupUsersDTO;
import com.imageupload.example.entity.GroupBoardEntity;
import com.imageupload.example.entity.GroupCommentEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.GroupBoardRepository;
import com.imageupload.example.repositories.GroupUsersRepository;
import com.imageupload.example.services.GroupBoardService;
import lombok.RequiredArgsConstructor;
import org.joda.time.DateTime;
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

    private final GroupBoardService groupBoardService;
    private final HttpSession session;

    @GetMapping("/get/userInfo")
    public GroupUsersDTO getUserInfo(){

        UserEntity userEntity = (UserEntity) session.getAttribute("user_id");

        GroupUsersDTO groupUserInfoDTO = new GroupUsersDTO();
        groupUserInfoDTO.setUser_id(userEntity.getId());
        groupUserInfoDTO.setUser_name(userEntity.getUsername());
        groupUserInfoDTO.setProfilePath(userEntity.getProfile().getProfilePath());

        return groupUserInfoDTO;
    }

    @PostMapping("/board/comment/write")
    public ResponseEntity<GroupCommentDTO> commentWrite(GroupCommentDTO groupCommentDTO){
        groupBoardService.commentWrite(groupCommentDTO);
        return new ResponseEntity<>(groupCommentDTO, HttpStatus.OK);
    }

    @PostMapping("/board/delete")
    public ResponseEntity<String> deletePost(@RequestParam Long boardId, @RequestParam String username){
        groupBoardService.delete(boardId, username);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/board/post")
    public ResponseEntity<String> groupPostContent(GroupBoardDTO groupBoardDTO){

        GroupUsersEntity groupUsersEntity = (GroupUsersEntity) session.getAttribute("group_user_entity");
        groupBoardDTO.setUser(groupUsersEntity);

        groupBoardService.post(groupBoardDTO);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @GetMapping("/get_board_list")
    public Page<GroupBoardEntity> getBoardList(@RequestParam Long groupId, @RequestParam int display, @RequestParam int page){
        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "boardId");
        return groupBoardService.getBoardList(groupId, request);
    }

}
