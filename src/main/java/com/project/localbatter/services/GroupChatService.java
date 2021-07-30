package com.project.localbatter.services;

import com.project.localbatter.dto.GroupChatMessageDTO;
import com.project.localbatter.repositories.UserRepository;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;

@Service
@RequiredArgsConstructor
public class GroupChatService {

    private final UserRepository userRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final GroupService groupService;

    public void sendGroupRoomToChat(GroupChatMessageDTO messageDTO){

        simpMessagingTemplate.convertAndSend("/chat/group/" + messageDTO.getGroupId(), messageDTO);
    }

    public void sendImageToGroupChatRoom(GroupChatMessageDTO messageDTO){
        messageDTO.setImg(null);
        for(String img : messageDTO.getImgPath()){
            messageDTO.setMessage(img);
            simpMessagingTemplate.convertAndSend("/chat/group/" + messageDTO.getGroupId(), messageDTO);
        }
    }

}
