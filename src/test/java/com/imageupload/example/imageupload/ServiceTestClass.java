package com.imageupload.example.imageupload;

import java.util.List;
import java.util.Optional;

import com.imageupload.example.entity.UserJoinRoomEntity;
import com.imageupload.example.models.UserVo;
import com.imageupload.example.repositories.ChatRoomRepository;
import com.imageupload.example.repositories.UserRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
public class ServiceTestClass {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void 채팅방_중복개설_확인_테스트(){

        UserJoinRoomEntity room = null;

        Optional<UserVo> user = userRepository.findById(3L);
        Optional<UserVo> target = userRepository.findById(2L);

        List<UserJoinRoomEntity> list = chatRoomRepository.findAllByuserVoOrTarget(user.get(), user.get());

        for(UserJoinRoomEntity item : list){
            if(item.getTarget().equals(target.get())){
                room = item;
                break;
            }
        }

        if(room != null)
            log.info("채팅방 존재");
        else
            log.info("채팅방 개설");
    

    }

}
