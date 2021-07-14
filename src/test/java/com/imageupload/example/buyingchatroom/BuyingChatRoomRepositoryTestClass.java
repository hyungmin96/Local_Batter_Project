package com.imageupload.example.buyingchatroom;

import com.imageupload.example.entity.BuyingChatRoomEntity;
import com.imageupload.example.repositories.BuyingChatRepository;
import com.imageupload.example.repositories.BuyingChatRoomRepository;
import com.imageupload.example.repositories.BuyingFileRepository;
import com.imageupload.example.repositories.BuyingUsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:application.yml")
public class BuyingChatRoomRepositoryTestClass {

    @Autowired
    private BuyingChatRoomRepository buyingChatRoomRepository;

    @Autowired
    private BuyingFileRepository buyingFileRepository;

    @Autowired
    private BuyingUsersRepository buyingUsersRepository;

    @Autowired
    private BuyingChatRepository buyingChatRepository;

    @Test
    void 공동구매_채팅방_생성_테스트(){

        for(int i = 1; i <= 10; i++){
            BuyingChatRoomEntity buyingChatRoomEntity = BuyingChatRoomEntity.builder()
                    .roomTitle("공동구매 채팅방" + i)
                    .currentUsers(1)
                    .owner("tester")
                    .limitUsers(5)
                    .description("description")
                    .price(200000)
                    .build();
            buyingChatRoomRepository.save(buyingChatRoomEntity);
        }
    }
}
