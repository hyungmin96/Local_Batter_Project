package com.imageupload.example.services;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import com.imageupload.example.dto.MessageDTO;
import com.imageupload.example.dto.NotificationDTO;
import com.imageupload.example.entity.ChatEntity;
import com.imageupload.example.entity.RoomEntity;
import com.imageupload.example.entity.UserJoinRoomEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.ChatRepository;
import com.imageupload.example.repositories.ChatRoomRepository;
import com.imageupload.example.repositories.RoomRepository;
import com.imageupload.example.repositories.UserJoinRommEnumType;
import com.imageupload.example.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    
    final private SimpMessagingTemplate simpMessageTemplate;
    final private ChatRepository chatRepository;
    final private RoomRepository roomRepository;
    final private ChatRoomRepository chatRoomRepository;
    final private UserRepository userRepository;

    public void deleteChatRoom(String roomId, String userId, String targetUsername){

        UserJoinRoomEntity userJoinRoomEntity = chatRoomRepository.findById(Long.parseLong(roomId)).get();
        if(targetUsername.equals(userJoinRoomEntity.getUserVo().getUsername()))
            userJoinRoomEntity.setUserConnectionType(UserJoinRommEnumType.disconnected);
        else
            userJoinRoomEntity.setTargetConnectionType(UserJoinRommEnumType.disconnected);

        if(userJoinRoomEntity.getTargetConnectionType() == UserJoinRommEnumType.disconnected && userJoinRoomEntity.getUserConnectionType() == UserJoinRommEnumType.disconnected)
            chatRoomRepository.delete(userJoinRoomEntity);            
        else
            chatRoomRepository.save(userJoinRoomEntity);

    }

    public void sendNotification(NotificationDTO message){
        simpMessageTemplate.convertAndSend("/notification/" + message.getTargetUser(), message);
    }

    public List<UserJoinRoomEntity> getChatRoomList(Principal userVo){

        UserEntity user = userRepository.findByUsername(userVo.getName()).get();

        List<UserJoinRoomEntity> roomList = chatRoomRepository.findAllByuserVoOrTarget(user, user);

        roomList.forEach(action -> {
            if(action.getTarget().getUsername().equals(userVo.getName())){
                action.setTarget(action.getUserVo());
            }
        });

        roomList.sort((o1, o2) -> {
            int chatSizeofO1 = o1.getRoomEntity().getChats().size() -1;
            int chatSizeofO2 = o2.getRoomEntity().getChats().size() -1;
            if(chatSizeofO1 > -1 && chatSizeofO2 > -1){
                ChatEntity chatEntityofO1 = o1.getRoomEntity().getChats().get(chatSizeofO1);
                ChatEntity chatEntityofO2 = o2.getRoomEntity().getChats().get(chatSizeofO2);

                return chatEntityofO2.getCreateTime().compareTo(chatEntityofO1.getCreateTime());

            }else return 0;
        });

        return roomList;
            
    }

    // 프로필 전송
    public void sendProfile(Principal user, MessageDTO message){

        UserEntity userEntity = userRepository.findByUsername(user.getName()).get();

        message.setMessage("거래 선호시간 : " + userEntity.getProfile().getPreferTime() +
                        "<br> 핸드폰 번호 : " + userEntity.getProfile().getAccountNumber() +
                        "<br> 거래 선호지역 : " + userEntity.getProfile().getLocation());

        simpMessageTemplate.convertAndSend("/chat/" + message.getRoomId(), message);
    }

    // 계좌번호 전송
    public void sendNumber(Principal user, MessageDTO message){
        UserEntity userEntity = userRepository.findByUsername(user.getName()).get();

        message.setMessage("국민은행 : " + userEntity.getProfile().getAccountNumber());

        simpMessageTemplate.convertAndSend("/chat/" + message.getRoomId(), message);
    }

    public void sendMessage(Principal principal, MessageDTO message){

        Optional<UserEntity> user = userRepository.findByUsername(principal.getName());

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
