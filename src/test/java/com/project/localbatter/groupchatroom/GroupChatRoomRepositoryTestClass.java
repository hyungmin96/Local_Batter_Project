package com.project.localbatter.groupchatroom;
import com.project.localbatter.dto.GroupBoardDTO;
import com.project.localbatter.dto.GroupCommentDTO;
import com.project.localbatter.entity.*;
import com.project.localbatter.repositories.*;
import com.project.localbatter.services.GroupBoardService;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.project.localbatter.entity.QGroupBoardEntity.groupBoardEntity;
import static com.project.localbatter.entity.QGroupUserJoinEntity.groupUserJoinEntity;
import static com.project.localbatter.entity.QUserEntity.userEntity;

@SpringBootTest
@TestPropertySource("classpath:application.yml")
public class GroupChatRoomRepositoryTestClass {

    @Autowired private GroupRepository groupRepository;
    @Autowired private GroupFileRepository groupFileRepository;
    @Autowired private GroupUserJoinRepository groupUserJoinRepository;
    @Autowired private GroupChatRepository groupChatRepository;
    @Autowired private GroupUserJoinQueryRepository groupUserJoinQuseryRepository;
    @Autowired private GroupCommentRepository groupCommentRepository;
    @Autowired private GroupBoardRepository groupBoardRepository;
    @Autowired private UserRepository userRepository;
    @Autowired private GroupBoardFileRepository groupBoardFileRepository;
    @Autowired private GroupBoardService groupBoardService;
    @Autowired private JPAQueryFactory queryFactory;
    private final Logger log = LogManager.getLogger();
    private final String root = "D:\\Spring projects\\SpringBoot LocalBatter\\src\\main\\downloads\\";


    @Test
    void 그룹_게시글_작성(){

        UserEntity userEntity = userRepository.getOne(1L);
        GroupEntity groupEntity = groupRepository.getOne(1L);

        GroupUserJoinEntity groupUserJoinEntity = groupUserJoinQuseryRepository.findGroupUserJoinEntity(groupEntity.getId(), userEntity.getId());

        for(int i = 1; i < 9756; i++){
            List<GroupBoardFileEntity> GroupBoardFiles = new ArrayList<>();

            GroupBoardDTO groupBoardDTO = new GroupBoardDTO();
            groupBoardDTO.setGroupId(1L);
            groupBoardDTO.setContent("test content");

            GroupBoardEntity groupBoardEntity = groupBoardDTO.toEntity(groupUserJoinEntity);
            groupBoardRepository.save(groupBoardEntity);

            groupBoardFileRepository.saveAll(GroupBoardFiles);
        }
    }

    @Test
    void 게시글_덧글작성(){
        UserEntity userEntity = userRepository.getOne(1L);
        GroupEntity groupEntity = groupRepository.getOne(1L);

        GroupUserJoinEntity groupUserJoinEntity = groupUserJoinQuseryRepository.findGroupUserJoinEntity(groupEntity.getId(), userEntity.getId());

        GroupBoardEntity groupBoardEntity = groupBoardRepository.getOne(3L);

        for(int i = 1; i < 5; i ++){

            GroupCommentDTO groupCommentDTO = new GroupCommentDTO();
            groupCommentDTO.setComment("content " + i);
            GroupCommentEntity commentEntity = groupCommentDTO.toEntity(groupBoardEntity, groupUserJoinEntity);
            groupCommentRepository.save(commentEntity);
        }
    }

    @Test
    @Transactional(readOnly = true)
    void 게시글_정보_조회(){

        String start = new SimpleDateFormat("mm:ss").format(System.currentTimeMillis());

        List<GroupBoardEntity> test = queryFactory
                .selectDistinct(groupBoardEntity)
                .from(groupBoardEntity)
                .leftJoin(groupBoardEntity.groupUserJoinEntity, groupUserJoinEntity)
                .fetchJoin()
                .leftJoin(groupUserJoinEntity.user, userEntity)
                .fetchJoin()
                .where(groupUserJoinEntity.user.id.eq(1L))
                .limit(5)
                .fetch();

        for(GroupBoardEntity item : test){
            log.info(
                    "\nvalue : " +
                        "\n게시글 수 : " + test.size() +
                        "\n게시글 작성자 : " + item.getGroupUserJoinEntity().getUser().getUsername() +
                        "\n게시글 프로필 : " + item.getGroupUserJoinEntity().getUser().getProfilePath() +
                        "\n게시글 내용 : " + item.getContent() +
                        "\n게시글 이미지 갯수 : " + item.getFiles().size() +
                        "\n덧글 갯수 : " + item.getComments().size());
                    for(GroupCommentEntity comment : item.getComments()){
                        log.info(
                        "\n게시글 덧글 작성자 : " + comment.getGroupUserJoinEntity().getUser().getUsername() +
                        "\n게시글 덧글 프로필 : " + comment.getGroupUserJoinEntity().getUser().getProfilePath() +
                        "\n게시글 덧글 내용 : " + comment.getComment());
                    }
        }

        log.info("test start : " + start);
        log.info("test end : " + new SimpleDateFormat("mm:ss").format(System.currentTimeMillis()));
    }

    @Test
    void innerJoin_Test(){
        GroupUserJoinEntity groupUserJoinEntity = queryFactory
                .selectFrom(QGroupUserJoinEntity.groupUserJoinEntity)
                .where(QGroupUserJoinEntity.groupUserJoinEntity.user.id.eq(1L)
                .and(QGroupUserJoinEntity.groupUserJoinEntity.group.id.eq(1L)))
                .fetchOne();

        log.info("value : " + groupUserJoinEntity.getId());
    }

}


