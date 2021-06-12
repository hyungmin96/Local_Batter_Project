package com.imageupload.example.controllers.restcontroller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.imageupload.example.entity.ChatEntity;
import com.imageupload.example.entity.RoomEntity;
import com.imageupload.example.entity.UserJoinRoomEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.ChatRoomRepository;
import com.imageupload.example.repositories.RoomRepository;
import com.imageupload.example.repositories.UserRepository;
import com.imageupload.example.services.ChatService;
import com.imageupload.example.vo.NotificationVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ChatRestController {
    
    @Autowired
    private ChatService chatService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private RoomRepository roomRepository;

    
    @GetMapping("/chat/rooms")
    public List<UserJoinRoomEntity> getChats(Principal principal){

        List<UserJoinRoomEntity> chatList = chatService.getChatRoomList(principal);
        
        chatList.forEach(action -> {
            if(action.getTarget().getUsername().equals(principal.getName())){
                action.getTarget().setUsername(action.getUserVo().getUsername());
            }
        });

        return chatList;
    }

    @GetMapping("/chat/chats/")
    public Page<ChatEntity> getChats(@RequestParam long roomId,
                                        @RequestParam int display,
                                        @RequestParam int page){
                                            
        return chatService.getChatList(roomId, display, page);
    }

    @PostMapping("/create/room")
    public NotificationVo createRoom(@AuthenticationPrincipal UserEntity principal, 
        @RequestParam("target") String targetString, 
        @RequestParam("user") String user)
    {

        UserJoinRoomEntity room = null;

        Optional<UserEntity> target = userRepository.findByUsername(targetString);

        List<UserJoinRoomEntity> list = chatRoomRepository.findAllByuserVoOrTarget(principal, target.get());

        for(UserJoinRoomEntity item : list){
            if(item.getTarget().equals(target.get())){
                room = item;
                break;
            }
    }

        NotificationVo notification = NotificationVo.builder()
        .notificationType("create")
        .sendUser(principal.getUsername())
        .targetUser(targetString)
        .date(LocalDateTime.now())
        .build();

        if(room == null){

            RoomEntity roomEntity = new RoomEntity();
            roomRepository.save(roomEntity);
    
            UserJoinRoomEntity userJoinRoomEntity = new UserJoinRoomEntity();
            userJoinRoomEntity.setTarget(target.get());
            userJoinRoomEntity.setUserVo(principal);
            userJoinRoomEntity.setRoomEntity(roomEntity);
    
            chatRoomRepository.save(userJoinRoomEntity);
    
            notification.setResult("success");
            notification.setRoomId(userJoinRoomEntity.getRoomEntity().getId());

        }else{
            notification.setResult("exist");
            notification.setRoomId(room.getRoomEntity().getId());
        }
        

        chatService.sendNotification(notification);

        return notification;

    }   

}
