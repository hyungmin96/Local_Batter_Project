package com.imageupload.example.groupchatroom;

import com.imageupload.example.entity.*;
import com.imageupload.example.repositories.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:application.yml")
public class GroupChatRoomRepositoryTestClass {

    @Autowired
    private GroupChatRoomRepository GroupChatRoomRepository;

    @Autowired
    private GroupFileRepository GroupFileRepository;

    @Autowired
    private GroupUsersRepository GroupUsersRepository;

    @Autowired
    private GroupChatRepository GroupChatRepository;

    @Autowired
    private GroupCommentRepository GroupCommentRepository;

    @Autowired
    private GroupBoardRepository GroupBoardRepository;

    private final Logger log = LogManager.getLogger();
    private final String root = "D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\downloads\\";

    @Test
    void 그룹_게시글_덧글작성(){

        GroupBoardEntity groupBoardEntity = GroupBoardRepository.findById(22L).get();
        GroupUsersEntity groupUsersEntity = GroupUsersRepository.findById(1L).get();

        for(int i = 0; i < 10; i ++){

            GroupCommentEntity groupCommentEntity = GroupCommentEntity.builder()
                    .groupBoard(groupBoardEntity)
                    .comment("test" + i)
                    .writer(groupUsersEntity)
                    .build();

            GroupCommentRepository.save(groupCommentEntity);
        }
    }

    @Test
    void 그룹_게시글_작성(){

        GroupBoardRepository.deleteAll();

        for(int i = 1; i <= 155; i++){
            List<GroupBoardFileEntity> GroupBoardFiles = new ArrayList<>();
            List<GroupCommentEntity> GroupBoardComment = new ArrayList<>();

            GroupUsersEntity groupUsersEntity = GroupUsersRepository.findById(1L).get();

            GroupBoardEntity GroupBoardEntity = com.imageupload.example.entity.GroupBoardEntity.builder()
                    .content("content" + i)
                    .groupId(100L)
                    .groupUsersEntity(groupUsersEntity)
                    .build();

            GroupBoardFileEntity GroupBoardFileEntity = com.imageupload.example.entity.GroupBoardFileEntity.builder()
                    .path(root + "6d2cb620-6add-4fb1-8efa-771458093a6b.jpg")
                    .name("6d2cb620-6add-4fb1-8efa-771458093a6b.jpg")
                    .groupBoard(GroupBoardEntity)
                    .size(312155L)
                    .build();

            GroupBoardFiles.add(GroupBoardFileEntity);

            GroupCommentEntity GroupBoardCommentEntity = GroupCommentEntity.builder()
                    .comment("comment")
//                    .writer("tester")
                    .groupBoard(GroupBoardEntity)
                    .build();
            GroupBoardComment.add(GroupBoardCommentEntity);

            GroupBoardEntity.setFiles(GroupBoardFiles);
            GroupBoardEntity.setComments(GroupBoardComment);

            GroupBoardRepository.save(GroupBoardEntity);
        }
    }

    @Test
    void 게시글_정보_조회(){
        GroupBoardEntity GroupBoardEntity = GroupBoardRepository.findByboardId(21L);
        List<GroupCommentEntity> test = GroupCommentRepository.findAllByGroupBoard(GroupBoardEntity);
        for(GroupCommentEntity item : test){
            log.info("comment : " + item.getComment());
        }
    }

    @Test
    void 그룹_생성_테스트(){

        GroupChatRoomRepository.deleteAll();

        for(int i = 1; i <= 100; i++){

            GroupChatRoomEntity groupChatRoomEntity = com.imageupload.example.entity.GroupChatRoomEntity.builder()
                    .roomTitle("공동구매 채팅방" + i)
                    .currentUsers(1)
                    .owner("tester")
                    .type(com.imageupload.example.entity.GroupChatRoomEntity.roomEnumType.공개)
                    .limitUsers(5)
                    .description("description")
                    .build();

            GroupFileEntity GroupFileEntity = com.imageupload.example.entity.GroupFileEntity.builder()
                    .name("upload.png")
                    .path("D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\resources\\static\\images\\upload.png")
                    .groupChatRoomEntity(groupChatRoomEntity)
                    .build();

            GroupChatRoomRepository.save(groupChatRoomEntity);

            GroupFileRepository.save(GroupFileEntity);
        }
    }
}
