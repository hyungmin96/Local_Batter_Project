package com.project.localbatter.groupchatroom;
import com.project.localbatter.dto.*;
import com.project.localbatter.entity.*;
import com.project.localbatter.repositories.*;
import com.project.localbatter.services.GroupBoardService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource("classpath:application.yml")
public class GroupChatRoomRepositoryTestClass {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private com.project.localbatter.repositories.GroupFileRepository GroupFileRepository;

    @Autowired
    private GroupUsersRepository GroupUsersRepository;

    @Autowired
    private GroupChatRepository groupChatRepository;

    @Autowired
    private GroupCommentRepository GroupCommentRepository;

    @Autowired
    private com.project.localbatter.repositories.GroupBoardRepository GroupBoardRepository;

    @Autowired
    private GroupBoardFileRepository groupBoardFileRepository;

    @Autowired
    private GroupUsersRepository groupUsersRepository;

    @Autowired
    private GroupBoardService groupBoardService;

    private final Logger log = LogManager.getLogger();
    private final String root = "D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\downloads\\";


    @Test
    void 최신_게시글_사진가져오기(){

        GroupBoardDTO groupBoardDTO = new GroupBoardDTO();
        groupBoardDTO.setGroupId(16L);

        Page<GroupBoardFileEntity> test = groupBoardService.getLatestImages(groupBoardDTO);

        log.info("test");
    }

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

            GroupCreateDTO groupInfoDTO = new GroupCreateDTO();
            groupInfoDTO.setDescription("description");
            groupInfoDTO.setOwner("owner");
            groupInfoDTO.setType(GroupEntity.roomEnumType.공개);

            GroupEntity groupEntity = groupInfoDTO.toEntity();

            GroupUsersDTO groupUserInfoDTO = new GroupUsersDTO();
//            groupUserInfoDTO.setUser(user);
            groupUserInfoDTO.setAuthorization(GroupUsersEntity.GroupUsersEnumType.member);
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
