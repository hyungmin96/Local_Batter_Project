package com.project.localbatter.api.group;

import com.project.localbatter.dto.GroupChatMessageDTO;
import com.project.localbatter.dto.GroupJoinRequestDTO;
import com.project.localbatter.services.group.GroupChatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/group/chat")
public class GroupChatApiController {

    private final GroupChatService groupChatService;

    @GetMapping("/get_chat_list")
    public Page<GroupChatMessageDTO> getChatList(GroupChatMessageDTO messageDTO){
        PageRequest page = PageRequest.of(messageDTO.getPage(), messageDTO.getDisplay());
        return groupChatService.getChatList(messageDTO, page);
    }

    @PostMapping("/upload")
    public ResponseImageChatDTO uploadImgToGroupChat(GroupChatMessageDTO messageDTO){
        groupChatService.sendImageToGroupChat(messageDTO);
        return new ResponseImageChatDTO(messageDTO);
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

    @Getter @Setter
    static class ResponseImageChatDTO{
        private Long userId;
        private Long groupId;
        private String username;
        private String message;
        private String type;

        public ResponseImageChatDTO(GroupChatMessageDTO entity){
            this.userId = entity.getUserId();
            this.groupId = entity.getGroupId();
            this.username = entity.getUsername();
            this.message = entity.getMessage();
            this.type = entity.getType();
        }
    }
}
