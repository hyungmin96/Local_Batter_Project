package com.imageupload.example.imageupload;

import com.imageupload.example.repositories.UserRepository;

import java.util.Optional;

import com.imageupload.example.entity.ChatEntity;
import com.imageupload.example.entity.RoomEntity;
import com.imageupload.example.entity.UserJoinRoomEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.models.Role;
import com.imageupload.example.repositories.BoardRepository;
import com.imageupload.example.repositories.ChatRepository;
import com.imageupload.example.repositories.ChatRoomRepository;
import com.imageupload.example.repositories.RoomRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.yml")
public class RepositoryTestClass{
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private ChatRepository chatRepository;

    @Test
    public void 채팅방_개설_테스트(){

        chatRoomRepository.deleteAll();

        Optional<UserEntity> user = userRepository.findById(2L);
        Optional<UserEntity> target = userRepository.findById(1L);

        RoomEntity room = new RoomEntity();
        roomRepository.save(room);

        UserJoinRoomEntity userJoinRoomEntity = new UserJoinRoomEntity();
        userJoinRoomEntity.setTarget(user.get());
        userJoinRoomEntity.setUserVo(target.get());
        userJoinRoomEntity.setRoomEntity(room);

        chatRoomRepository.save(userJoinRoomEntity);
    }

    @Test
    public void 채팅내용_저장_테스트(){

        for(int i = 0; i < 100; i ++){
        Optional<UserEntity> user = userRepository.findById(21L);

        Optional<RoomEntity> room = roomRepository.findById(22L);

        ChatEntity chat = new ChatEntity();
        chat.setMessage("message");
        chat.setUserVo(user.get());
        chat.setRoomEntity(room.get());

            chatRepository.save(chat);
        }

    }

    @Test
    public void 회원가입_테스트(){

        // userRepository.deleteAll();

        for(int i = 1; i < 10; i ++){
            
            UserEntity user = UserEntity.builder()
            .username(i + "")
            .password(new BCryptPasswordEncoder().encode(String.valueOf(i)))
            .Role(Role.ROLE_USER)
            .build();
            
            userRepository.save(user);

        }

    }
    
}
