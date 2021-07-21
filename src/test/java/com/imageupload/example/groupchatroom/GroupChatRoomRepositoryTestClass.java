package com.imageupload.example.groupchatroom;

import com.imageupload.example.components.GenerateFile;
import com.imageupload.example.dto.*;
import com.imageupload.example.entity.*;
import com.imageupload.example.enumtype.GroupUsersEnumType;
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
    private GroupRepository groupRepository;

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

    @Autowired
    private GroupBoardFileRepository groupBoardFileRepository;

    @Autowired
    private GroupUsersRepository groupUsersRepository;

    private final Logger log = LogManager.getLogger();
    private final String root = "D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\downloads\\";

//    @Test
//    void 그룹_게시글_덧글작성(){
//
//        GroupBoardEntity groupBoardEntity = GroupBoardRepository.findById(22L).get();
//        GroupUsersEntity groupUsersEntity = GroupUsersRepository.findById(1L).get();
//
//        for(int i = 0; i < 10; i ++){
//
//            GroupCommentEntity groupCommentEntity = GroupCommentEntity.builder()
//                    .groupBoard(groupBoardEntity)
//                    .comment("test" + i)
//                    .writer(groupUsersEntity)
//                    .build();
//
//            GroupCommentRepository.save(groupCommentEntity);
//        }
//    }

    @Test
    void 그룹_게시글_작성(){

        GroupBoardRepository.deleteAll();

        GroupUsersEntity groupUsersEntity = GroupUsersRepository.getOne(3L);

        for(int i = 1; i <= 50; i++){
            List<GroupBoardFileEntity> GroupBoardFiles = new ArrayList<>();

            GroupBoardDTO groupBoardDTO = new GroupBoardDTO();
            groupBoardDTO.setGroupId(3L);
            groupBoardDTO.setContent("test content");
            groupBoardDTO.setUser(groupUsersEntity);

            GroupBoardEntity groupBoardEntity = groupBoardDTO.toEntity();
            GroupBoardRepository.save(groupBoardEntity);

            GroupBoardFileDTO groupBoardFileDTO = new GroupBoardFileDTO();
            groupBoardFileDTO.setPath(root + "6d2cb620-6add-4fb1-8efa-771458093a6b.jpg");
            groupBoardFileDTO.setName("6d2cb620-6add-4fb1-8efa-771458093a6b.jpg");
            groupBoardFileDTO.setBoardId(groupBoardEntity);

            GroupBoardFileEntity groupBoardFileEntity = groupBoardFileDTO.toEntity();
            GroupBoardFiles.add(groupBoardFileEntity);

            groupBoardFileRepository.saveAll(GroupBoardFiles);

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

        groupRepository.deleteAll();

        for(int i = 1; i <= 1; i++){

            GroupInfoDTO groupInfoDTO = new GroupInfoDTO();
            groupInfoDTO.setDescription("description");
            groupInfoDTO.setOwner("owner");
            groupInfoDTO.setCurrentUsers(0);
            groupInfoDTO.setTitle("title");
            groupInfoDTO.setType(GroupEntity.roomEnumType.공개);

            GroupEntity groupEntity = groupInfoDTO.toEntity();

            GroupUsersDTO groupUserInfoDTO = new GroupUsersDTO();
//            groupUserInfoDTO.setUser(user);
            groupUserInfoDTO.setAuthorization(GroupUsersEnumType.member);
            groupUserInfoDTO.setGroupEntity(groupEntity);

            GroupFileDTO groupFileDTO = new GroupFileDTO();
            groupFileDTO.setName("upload.png");
            groupFileDTO.setPath("D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\resources\\static\\images\\upload.png");
            groupFileDTO.setGroupEntity(groupEntity);

            groupRepository.save(groupEntity);
            groupUsersRepository.save(groupUserInfoDTO.toEntity());

        }
    }
}
