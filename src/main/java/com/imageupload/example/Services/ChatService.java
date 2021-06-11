package com.imageupload.example.services;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import com.imageupload.example.entity.ChatEntity;
import com.imageupload.example.entity.RoomEntity;
import com.imageupload.example.entity.UserJoinRoomEntity;
import com.imageupload.example.models.UserVo;
import com.imageupload.example.repositories.ChatRepository;
import com.imageupload.example.repositories.ChatRoomRepository;
import com.imageupload.example.repositories.RoomRepository;
import com.imageupload.example.repositories.UserRepository;
import com.imageupload.example.vo.MessageVo;
import com.imageupload.example.vo.NotificationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {
    
    @Autowired
    private SimpMessagingTemplate simpMessageTemplate;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    public void sendNotification(NotificationVo message){
        simpMessageTemplate.convertAndSend("/notification/" + message.getTargetUser(), message);
    }

    public List<UserJoinRoomEntity> getChatRoomList(Principal userVo){

        UserVo user = userRepository.findByUsername(userVo.getName()).get();

        List<UserJoinRoomEntity> roomList = chatRoomRepository.findAllByuserVoOrTarget(user, user);
        return roomList;
            
    }

    public void sendMessage(Principal principal, MessageVo message){

        Optional<UserVo> user = userRepository.findByUsername(principal.getName());

        ChatEntity chatEntity = ChatEntity.builder()
        .message(message.getMessage())
        .userVo(user.get())
        .roomEntity(roomRepository.findById(message.getRoomId()).get())
        .build();

        chatRepository.save(chatEntity);

        simpMessageTemplate.convertAndSend("/chat/" + message.getRoomId(), message);

    }

    public Page<ChatEntity> getChatList(long roomId, int display, int page){
                                            
        Optional<RoomEntity> roomEntity = roomRepository.findById(roomId);
        RoomEntity room = roomEntity.get();

        PageRequest request = PageRequest.of(page, display, Sort.Direction.DESC, "id");
        Page<ChatEntity> chatList = chatRepository.findAllByroomEntity(room, request);

        return chatList;
    }

}
