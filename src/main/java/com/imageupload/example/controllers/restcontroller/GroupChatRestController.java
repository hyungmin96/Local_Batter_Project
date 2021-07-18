package com.imageupload.example.controllers.restcontroller;

import com.imageupload.example.dto.GroupChatMessageDTO;
import com.imageupload.example.dto.GroupDTO;
import com.imageupload.example.services.GroupChatService;
import com.imageupload.example.entity.GroupChatRoomEntity;
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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImgToChatRoom(GroupChatMessageDTO messageDTO){
        groupService.uploadImgToGroupChatroom(messageDTO);
        groupChatService.sendImageToGroupChatRoom(messageDTO);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/delete")
    public ResponseEntity<String> deleteRoom(HttpSession session, @RequestParam Long roomId, @RequestParam String username){
        groupService.deleteRoom(session, roomId, username);
        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exitRoom(HttpSession session, @RequestParam Long roomId, @RequestParam String username){

        GroupChatMessageDTO messageDTO = GroupChatMessageDTO.builder()
                .roomId(roomId)
                .type("exit")
                .sender(username)
                .message(null)
                .localDate(LocalDate.now().toString())
                .build();

        groupChatService.sendGroupRoomToChat(messageDTO);
        groupService.exitRoom(session, roomId);
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
                return new ResponseEntity<String>("Success", HttpStatus.OK);
            case enter:
                return new ResponseEntity<String>("Enter", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Failed - exceed Users number", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getlist")
    public Page<GroupChatRoomEntity> getGroupRoomList(@RequestParam int page, @RequestParam int display){
        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "id");
        return groupService.getGroupRooms(request);
    }

    @PostMapping("/create")
    public ResponseEntity<String> createGroupRoom(HttpSession session, GroupDTO GroupDTO){
        groupService.createGroupRoom(session, GroupDTO);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

    @GetMapping("/getRoomInfo")
    public GroupChatRoomEntity getRoomInfo(@RequestParam Long roomId){
        return groupService.getRoomInfo(roomId);
    }

}
