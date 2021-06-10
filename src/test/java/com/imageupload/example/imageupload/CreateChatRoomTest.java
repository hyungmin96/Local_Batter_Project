package com.imageupload.example.imageupload;

import com.imageupload.example.dto.RoomDTO;
import com.imageupload.example.dto.UserJoinRoomDTO;
import com.imageupload.example.models.UserVo;
import com.imageupload.example.repositories.ChatRoomRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:application.yml")
public class CreateChatRoomTest {
    
    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Test
    public void 채팅방_개설_테스트(){

        UserVo user = UserVo.builder().username("1").password("1").build();
        RoomDTO room = new RoomDTO();

        UserJoinRoomDTO userJoinRoomDTO = new UserJoinRoomDTO();
        userJoinRoomDTO.setId(0L);
        userJoinRoomDTO.setTarget(user);
        userJoinRoomDTO.setUserVo(user);
        userJoinRoomDTO.setRoomDTO(room);

        chatRoomRepository.save(userJoinRoomDTO);
    }

}
