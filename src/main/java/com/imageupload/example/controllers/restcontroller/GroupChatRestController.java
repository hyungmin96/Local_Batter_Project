package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.dto.GroupChatMessageDTO;
import com.imageupload.example.dto.GroupInfoDTO;
import com.imageupload.example.entity.GroupEntity;
import com.imageupload.example.services.GroupChatService;
import com.imageupload.example.services.GroupService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupChatRestController {

    private final GroupService groupService;
    private final GroupChatService groupChatService;
    private final HttpSession session;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImgToChatRoom(GroupChatMessageDTO messageDTO){
        groupService.uploadImgToGroupChatroom(messageDTO);
        groupChatService.sendImageToGroupChatRoom(messageDTO);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteRoom(@RequestParam Long roomId, @RequestParam String username){
        groupService.deleteRoom(roomId, username);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exitRoom(@RequestParam Long roomId, @RequestParam String username){
        GroupChatMessageDTO messageDTO = GroupChatMessageDTO.builder()
                .roomId(roomId)
                .type("exit")
                .sender(username)
                .message(null)
                .localDate(LocalDate.now().toString())
                .build();

        groupChatService.sendGroupRoomToChat(messageDTO);
        groupService.exitRoom(roomId);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/enter")
    public ResponseEntity<String> enterRoom(@RequestParam Long room_Id, @RequestParam Long user_id, @RequestParam String user_name){
        switch(groupService.GroupChatRoomEnter(room_Id, user_name)) {
            case greeting:
                GroupChatMessageDTO messageDTO = GroupChatMessageDTO.builder()
                        .roomId(room_Id)
                        .type("greeting")
                        .sender(user_name)
                        .message(null)
                        .localDate(LocalDate.now().toString())
                        .build();

                groupChatService.sendGroupRoomToChat(messageDTO);
            case enter:
                return new ResponseEntity<String>("Enter", HttpStatus.OK);
        }

        return new ResponseEntity<String>("Failed - exceed Users number", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getlist")
    public Page<GroupEntity> getGroupRoomList(@RequestParam int page, @RequestParam int display){
        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "id");
        return groupService.getGroupRooms(request);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createGroupRoom(GroupInfoDTO GroupDTO){
        groupService.createGroupRoom(GroupDTO);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @GetMapping("/getRoomInfo")
    public GroupEntity getRoomInfo(@RequestParam Long roomId){
        return groupService.getRoomInfo(roomId);
    }

}
