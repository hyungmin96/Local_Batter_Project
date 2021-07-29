package com.project.localbatter.controllers.restcontroller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import com.project.localbatter.dto.NotificationDTO;
import com.project.localbatter.enumtype.NotificationEnumType;
import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.entity.ChatEntity;
import com.project.localbatter.entity.RoomEntity;
import com.project.localbatter.entity.UserJoinRoomEntity;
import com.project.localbatter.repositories.ChatRoomRepository;
import com.project.localbatter.repositories.RoomRepository;
import com.project.localbatter.repositories.UserJoinRommEnumType;
import com.project.localbatter.repositories.UserRepository;
import com.project.localbatter.services.ChatService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChatRestController {
    
    final private ChatService chatService;
    final private UserRepository userRepository;
    final private ChatRoomRepository chatRoomRepository;
    final private RoomRepository roomRepository;

    @PostMapping("/chat/delete/room")
    public void deleteRoom(@RequestParam String roomId, @RequestParam String userId, @RequestParam String targetUsername){
        chatService.deleteChatRoom(roomId, userId, targetUsername);
    }

    @GetMapping("/chat/rooms")
    public List<UserJoinRoomEntity> getChatRooms(Principal principal){
        return chatService.getChatRoomList(principal);
    }

    @GetMapping("/chat/chats/")
    public Page<ChatEntity> getChats(@RequestParam long roomId, @RequestParam int display, @RequestParam int page){
        return chatService.getChatList(roomId, display, page);
    }

    @PostMapping("/create/room")
    public NotificationDTO createRoom(HttpSession session, @RequestParam("target") String targetString, @RequestParam("user") String user)
    {

        UserJoinRoomEntity room = null;

        UserEntity userEntity = (UserEntity) session.getAttribute("userId");

        Optional<UserEntity> target = userRepository.findByUsername(targetString);

        List<UserJoinRoomEntity> list = chatRoomRepository.findAllByuserVoOrTarget(userEntity, userEntity);

        for(UserJoinRoomEntity item : list){
            if(item.getTarget().equals(target.get())){
                room = item;
                break;
            }
        }

        NotificationDTO notification = NotificationDTO.builder()
        .notificationType(NotificationEnumType.chat)
        .sender(userEntity.getUsername())
        .target(targetString)
        .date(LocalDateTime.now().toString())
        .build();

        if(room == null){

            RoomEntity roomEntity = new RoomEntity();
            roomRepository.save(roomEntity);
    
            UserJoinRoomEntity userJoinRoomEntity = new UserJoinRoomEntity();
            userJoinRoomEntity.setTarget(target.get());
            userJoinRoomEntity.setUserVo(userEntity);
            userJoinRoomEntity.setTargetConnectionType(UserJoinRommEnumType.connected);
            userJoinRoomEntity.setUserConnectionType(UserJoinRommEnumType.connected);
            userJoinRoomEntity.setRoomEntity(roomEntity);
    
            chatRoomRepository.save(userJoinRoomEntity);
    
            notification.setResult("success");
            notification.setRoomId(userJoinRoomEntity.getRoomEntity().getId());

        }else{
            notification.setResult("exist");
            notification.setRoomId(room.getRoomEntity().getId());
        }
        
        chatService.sendNotification(session, notification);

        return notification;
    }
}