package com.imageupload.example.imageupload;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.imageupload.example.entity.UserJoinRoomEntity;
import com.imageupload.example.entity.BoardEntity;
import com.imageupload.example.entity.TransactionEntity;
import com.imageupload.example.entity.UserEntity;
import com.imageupload.example.repositories.ChatRoomRepository;
import com.imageupload.example.repositories.TransactionRepository;
import com.imageupload.example.repositories.UserRepository;
import com.imageupload.example.services.BoardService;
import com.imageupload.example.services.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServiceTestClass {

    private static Logger log = LogManager.getLogger();

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private UserService userService;

    @Autowired
    private BoardService boardService;

    @Test
    public void 거래요청_게시글_저장(){

        String user = "1";
        String seller = "2";
        Long boardId = 1L;

        BoardEntity board = boardService.findBoard(boardId);

        UserEntity sellerEntity = userService.findUserOne(seller);
        UserEntity buyerEntity = userService.findUserOne(user);

        TransactionEntity transactionEntity = TransactionEntity.builder()
        .boardId(board)
        .seller(sellerEntity)
        .buyer(buyerEntity)
        .build();

        transactionRepository.save(transactionEntity);
    }

    @Test
    void 채팅방_중복개설_확인_테스트(){

        UserJoinRoomEntity room = null;

        Optional<UserEntity> user = userRepository.findById(3L);
        Optional<UserEntity> target = userRepository.findById(2L);

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
