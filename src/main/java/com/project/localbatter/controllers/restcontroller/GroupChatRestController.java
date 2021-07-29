package com.project.localbatter.controllers.restcontroller;

import com.project.localbatter.dto.GroupChatMessageDTO;
import com.project.localbatter.dto.GroupJoinRequestDTO;
import com.project.localbatter.entity.GroupEntity;
import com.project.localbatter.services.GroupChatService;
import com.project.localbatter.services.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSession;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class GroupChatRestController {

    private final GroupService groupService;
    private final GroupChatService groupChatService;
    private final HttpSession session;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImgToChatRoom(GroupChatMessageDTO messageDTO){
        groupChatService.sendImageToGroupChatRoom(messageDTO);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exitRoom(GroupJoinRequestDTO groupJoinRequestDTO){
        GroupChatMessageDTO messageDTO = new GroupChatMessageDTO();
        messageDTO.setGroupId(groupJoinRequestDTO.getGroupId());
        messageDTO.setUserId(groupJoinRequestDTO.getUserId());
        messageDTO.setMessage(groupJoinRequestDTO.getUsername() + " 님이 나갔습니다.");
        messageDTO.setType("exit");

        groupChatService.sendGroupRoomToChat(messageDTO);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }


    @GetMapping("/getlist")
    public Page<GroupEntity> getGroupRoomList(@RequestParam int page, @RequestParam int display){
        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "id");
        return null;
    }

}
