package com.project.localbatter.controllers.restcontroller;

import com.project.localbatter.dto.*;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupBoardFileEntity;
import com.project.localbatter.entity.GroupCommentEntity;
import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.repositories.GroupRepository;
import com.project.localbatter.services.GroupBoardService;
import com.project.localbatter.services.GroupCommentService;
import com.project.localbatter.services.GroupService;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupRestController {

    private final GroupService groupService;
    private final GroupBoardService groupBoardService;
    private final GroupCommentService groupCommentService;

    @PostMapping("/enter")
    public ResponseEntity<String> enterGroup(GroupPageDTO groupPageDTO){
        return groupService.enterGroup(groupPageDTO);
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exitGroup(GroupPageDTO groupPageDTO){
        return groupService.exitGroup(groupPageDTO);
    }

    @GetMapping("/check/isMember")
    public ResponseEntity<String> checkMember(@RequestParam Long userId, @RequestParam Long groupId){
        return groupService.isMember(userId, groupId);
    }

    @GetMapping("/getInfo/{id}")
    public GroupPageDTO getGroupInfo(GroupPageDTO groupPageDTO){
        return groupService.getGroupInfo(groupPageDTO);
    }

    @GetMapping("/getlist")
    public List<GroupPageDTO> getGroupList(GroupPageDTO groupPageDTO){
        return groupService.getGroupList(groupPageDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<GroupCreateDTO> createGroup(GroupCreateDTO groupCreateDTO){
        return groupService.createGroupRoom(groupCreateDTO);
    }

    @PostMapping("/comment/update")
    public ResponseEntity<GroupCommentDTO> updateComment(GroupCommentDTO groupCommentDTO){
        return groupCommentService.updateComment(groupCommentDTO);
    }

    @PostMapping("/comment/delete")
    public ResponseEntity<GroupCommentDTO> deleteComment(GroupCommentDTO groupCommentDTO){
        return groupCommentService.deleteComment(groupCommentDTO);
    }

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
        groupCommentService.commentWrite(groupCommentDTO);
        return new ResponseEntity<>(groupCommentDTO, HttpStatus.OK);
    }

    @PostMapping("/board/delete")
    public ResponseEntity<String> deletePost(GroupBoardDTO groupBoardDTO){
        groupBoardService.delete(groupBoardDTO);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/board/post")
    public ResponseEntity<GroupBoardDTO> groupPostContent(GroupBoardDTO groupBoardDTO){
        groupBoardService.post(groupBoardDTO);
        return new ResponseEntity<>(groupBoardDTO, HttpStatus.OK);
    }

    @GetMapping("/get_board_list")
    public Page<GroupBoardEntity> getBoardList(GroupBoardDTO groupBoardDTO){
        PageRequest request = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.DESC, "boardId");
        return groupBoardService.getBoardList(groupBoardDTO.getGroupId(), request);
    }

}
