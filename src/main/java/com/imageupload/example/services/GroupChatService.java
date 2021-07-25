package com.imageupload.example.services;

import com.imageupload.example.dto.GroupChatMessageDTO;
import com.imageupload.example.dto.GroupUsersDTO;
import com.imageupload.example.entity.GroupEntity;
import com.imageupload.example.entity.GroupUsersEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.UserRepository;
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

        UserEntity userEntity = userRepository.getOne(messageDTO.getUserId());
        GroupEntity groupEntity = groupService.findRoom(messageDTO.getGroupId());
        GroupUsersEntity groupUsersEntity = groupService.findUserEntity(groupEntity, userEntity);

        GroupUsersDTO groupUsersDTO = new GroupUsersDTO();
        groupUsersDTO.setUser(userEntity);

        messageDTO.setGroupUsersEntity(groupUsersEntity);

        groupService.saveChatEntity(messageDTO);
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
