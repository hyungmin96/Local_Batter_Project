package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.dto.GroupBoardDTO;
import com.imageupload.example.dto.GroupBoardFileDTO;
import com.imageupload.example.dto.GroupCommentDTO;
import com.imageupload.example.entity.GroupBoardEntity;
import com.imageupload.example.entity.GroupBoardFileEntity;
import com.imageupload.example.entity.GroupCommentEntity;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupRestController {

    private final GroupBoardService groupBoardService;
    private final HttpSession session;

    @GetMapping("/comment/get_latest_comments")
    public Page<GroupCommentEntity> getLatestComments(GroupBoardDTO groupBoardDTO){
        return groupBoardService.getLatestComments(groupBoardDTO);
    }

    @GetMapping("/board/get_latest_images")
    public Page<GroupBoardFileEntity> getLatestImages(GroupBoardDTO groupBoardDTO){
        return groupBoardService.getLatestImages(groupBoardDTO);
    }

    @PostMapping("/board/update/notice")
    public ResponseEntity<GroupBoardDTO> updateNotice(GroupBoardDTO groupBoardDTO){
        return groupBoardService.updateNotice(groupBoardDTO);
    }

    @GetMapping("/board/get_notice_list")
    public Page<GroupBoardEntity> getNoticeList(GroupBoardDTO groupBoardDTO){
        return groupBoardService.getNoticeList(groupBoardDTO);
    }

    @PostMapping("/board/comment/write")
    public ResponseEntity<GroupCommentDTO> commentWrite(GroupCommentDTO groupCommentDTO){
        groupBoardService.commentWrite(groupCommentDTO);
        return new ResponseEntity<>(groupCommentDTO, HttpStatus.OK);
    }

    @PostMapping("/board/delete")
    public ResponseEntity<String> deletePost(GroupBoardDTO groupBoardDTO){
        groupBoardService.delete(groupBoardDTO);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/board/post")
    public ResponseEntity<GroupBoardDTO> groupPostContent(GroupBoardDTO groupBoardDTO){
        GroupUsersEntity groupUsersEntity = (GroupUsersEntity) session.getAttribute("group_user_entity");
        groupBoardDTO.setUser(groupUsersEntity);
        groupBoardService.post(groupBoardDTO);
        return new ResponseEntity<>(groupBoardDTO, HttpStatus.OK);
    }

    @GetMapping("/get_board_list")
    public Page<GroupBoardEntity> getBoardList(GroupBoardDTO groupBoardDTO){
        PageRequest request = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.DESC, "boardId");
        return groupBoardService.getBoardList(groupBoardDTO.getGroupId(), request);
    }

}
