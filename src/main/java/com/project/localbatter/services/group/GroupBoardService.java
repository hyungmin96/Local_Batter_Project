package com.project.localbatter.services.group;

import com.project.localbatter.api.group.GroupBoardApiController.ResponseGroupExchangeDTO;
import com.project.localbatter.api.group.GroupBoardApiController.ResponseBoardViewDTO;
import com.project.localbatter.components.DeleteFile;
import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.components.PagingUtil;
import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.dto.Group.GroupBoardDTO;
import com.project.localbatter.dto.Group.GroupBoardFileDTO;
import com.project.localbatter.entity.Exchange.WriterExchangeEntity;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupBoardFileEntity;
import com.project.localbatter.entity.GroupUserJoinEntity;
import com.project.localbatter.entity.QGroupBoardEntity;
import com.project.localbatter.repositories.GroupBoardFileRepository;
import com.project.localbatter.repositories.GroupBoardRepository;
import com.project.localbatter.repositories.GroupUserJoinQueryRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.project.localbatter.entity.Exchange.QWriterExchangeEntity.writerExchangeEntity;
import static com.project.localbatter.entity.QGroupBoardEntity.groupBoardEntity;
import static com.project.localbatter.entity.QGroupBoardFileEntity.groupBoardFileEntity;
import static com.project.localbatter.entity.QGroupUserJoinEntity.groupUserJoinEntity;
import static com.project.localbatter.entity.QUserEntity.userEntity;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

@Service
@RequiredArgsConstructor
public class GroupBoardService {

    private final GroupBoardRepository groupBoardRepository;
    private final GroupBoardFileRepository groupBoardFileRepository;
    private final GroupUserJoinQueryRepository groupUserJoinQuseryRepository;
    private final GenerateFile generateFile;
    private final JPAQueryFactory queryFactory;
    private final PagingUtil pagingUtil;

    // view the all group's exchange boards
    // 그룹에 작성된 모든 교환글을 조회
    @Transactional(readOnly = true)
    public Page<ResponseGroupExchangeDTO> getGroupExchangeBoards(Pageable page){
        Long queryCount = queryFactory
                .select(groupBoardEntity.boardId)
                .from(groupBoardEntity)
                .fetchCount();

        JPAQuery<ResponseGroupExchangeDTO> query = queryFactory
                .select(Projections.fields(ResponseGroupExchangeDTO.class,
                            groupBoardEntity.boardId,
                            groupBoardEntity.title,
                            groupBoardEntity.content,
                            writerExchangeEntity.price,
                            writerExchangeEntity.location,
                            groupBoardEntity.regTime,
                            groupBoardEntity.thumnbnailPath.as("thumbnail")
                        ))
                .from(groupBoardEntity)
                .innerJoin(groupBoardEntity.writerExchangeEntity, writerExchangeEntity)
                .orderBy(groupBoardEntity.writerExchangeEntity.regTime.desc())
                .offset(page.getPageNumber())
                .limit(page.getPageSize());

        return pagingUtil.getPageImpl(page, query, queryCount, ResponseGroupExchangeDTO.class);
    }

    // 그룹 게시글 수정 api
    // update groub's board title, content
    @Transactional
    public GroupBoardEntity update(GroupBoardDTO groupBoardDTO){
        GroupBoardEntity groupBoardEntity = queryFactory
                .selectDistinct(QGroupBoardEntity.groupBoardEntity)
                .from(QGroupBoardEntity.groupBoardEntity)
                .leftJoin(QGroupBoardEntity.groupBoardEntity.files)
                .fetchJoin()
                .where(QGroupBoardEntity.groupBoardEntity.boardId.eq(groupBoardDTO.getBoardId()))
                .fetchOne();
        List<GenerateFileDTO> files = generateFile.createFile(groupBoardDTO.getBoard_img());
        List<GroupBoardFileEntity> boardFiles = groupBoardEntity.getFiles();
        // convert fileDTO to FileEntity
        files.forEach(item -> boardFiles.add(new GroupBoardFileDTO(item).toEntity(groupBoardEntity)));
        try{
            if(groupBoardDTO.getDeleteImageIndex() != null && boardFiles.size() > 0){
                Arrays.stream(groupBoardDTO.getDeleteImageIndex()).forEach(item -> {
                    Long id = boardFiles.get(item).getId();
                    groupBoardFileRepository.deleteItem(id);
                    boardFiles.set(item, null);
                });
                for(int i = boardFiles.size() - 1; i > 0; i --)
                    if(boardFiles.get(i) == null)  boardFiles.remove(i);
            }
        }catch(Exception ignored){}
        groupBoardEntity.update(groupBoardDTO, boardFiles);
        groupBoardRepository.save(groupBoardEntity);
        return groupBoardEntity;
    }

    @Transactional(readOnly = true)
    public List<ResponseBoardViewDTO> getBoardInfo(GroupBoardDTO groupBoardDTO){
        Map<GroupBoardEntity, List<GroupBoardFileEntity>> map = queryFactory
                .from(groupBoardEntity)
                .leftJoin(groupBoardEntity.files, groupBoardFileEntity)
                .leftJoin(groupBoardEntity.groupUserJoinEntity, groupUserJoinEntity)
                .fetchJoin()
                .leftJoin(groupUserJoinEntity.user, userEntity)
                .fetchJoin()
                .where(groupBoardEntity.boardId.eq(groupBoardDTO.getBoardId()))
                .transform(groupBy(groupBoardEntity).as(list(groupBoardFileEntity)));

        return map.entrySet().stream()
                .map(item -> new ResponseBoardViewDTO(item.getKey(), item.getValue()))
                .collect(Collectors.toList());
    }

    public Page<GroupBoardEntity> getBoardList(Long groupId, PageRequest request) {
        JPAQuery<GroupBoardEntity> query = queryFactory
                .selectDistinct(groupBoardEntity)
                .from(groupBoardEntity)
                .innerJoin(groupBoardEntity.groupUserJoinEntity, groupUserJoinEntity)
                .fetchJoin()
                .innerJoin(groupUserJoinEntity.user, userEntity)
                .fetchJoin()
                .where(groupUserJoinEntity.group.id.eq(groupId))
                .orderBy(groupBoardEntity.boardId.desc());

        return pagingUtil.getPageImpl(request, query, query.fetchCount(), GroupBoardEntity.class);
    }

    public GroupBoardEntity updateNotice(GroupBoardDTO groupBoardDTO){
        // Check Authorization to enter user
        GroupUserJoinEntity groupUserJoinEntity = groupUserJoinQuseryRepository
                            .findGroupUserJoinEntity(groupBoardDTO.getUserId(), groupBoardDTO.getGroupId());
        GroupBoardEntity groupBoardEntity = groupBoardRepository.getOne(groupBoardDTO.getBoardId());
        if(!groupUserJoinEntity.getType().equals(GroupUserJoinEntity.userAuthority.member)){
            groupBoardEntity.updateNotice(groupBoardDTO.getType());
            groupBoardRepository.save(groupBoardEntity);
            return groupBoardDTO.toEntity(groupUserJoinEntity);
        }
        groupBoardDTO.setResult("Failed - No request authority");
        return groupBoardEntity;
    }

    public List<GroupBoardDTO> getNoticeList(GroupBoardDTO groupBoardDTO){
        return queryFactory
                .select(Projections.fields(GroupBoardDTO.class,
                        groupBoardEntity.boardId.as("boardId"),
                        groupBoardEntity.content.as("content"),
                        groupBoardEntity.regTime.as("regTime")))
                .from(groupBoardEntity)
                .where(groupBoardEntity.groupId.eq(groupBoardDTO.getGroupId())
                .and(groupBoardEntity.type.eq(GroupBoardEntity.BoardType.notice)))
                .offset(0)
                .limit(5)
                .fetch();
    }

    public void delete(GroupBoardDTO groupBoardDTO){
        GroupBoardEntity groupBoardEntity = groupBoardRepository.getOne(groupBoardDTO.getBoardId());
        String[] filePathArray = groupBoardEntity.getFiles().stream().map(GroupBoardFileEntity::getName).toArray(String[]::new);
        new DeleteFile(filePathArray).deleteFile();
        groupBoardRepository.deleteById(groupBoardDTO.getBoardId());
    }

    public GroupBoardEntity post(GroupBoardDTO groupBoardDTO){
        WriterExchangeEntity writerExchangeEntity = groupBoardDTO.getWriterExchangeEntity();
        GroupUserJoinEntity groupUserJoinEntity = groupUserJoinQuseryRepository.findGroupUserJoinEntity(groupBoardDTO.getUserId(), groupBoardDTO.getGroupId());
        if(groupUserJoinEntity != null){ // 게시글작성 사용자가 권한이 있는지 확인
            List<GenerateFileDTO> groupBoardFiles = generateFile.createFile(groupBoardDTO.getBoard_img());
            if(groupBoardFiles.size() > 0) groupBoardDTO.setThumnbnailPath(groupBoardFiles.get(0).getName());
            GroupBoardEntity groupBoardEntity = groupBoardDTO.toEntity(groupUserJoinEntity, writerExchangeEntity);
            groupBoardRepository.save(groupBoardEntity);
            groupBoardFiles.stream().map(GroupBoardFileDTO::new).collect(Collectors.toList())
                    .forEach(item -> {
                        GroupBoardFileEntity file = item.toEntity(groupBoardEntity);
                        groupBoardDTO.addFile(file);
                        groupBoardFileRepository.save(file);
                    });
            return groupBoardEntity;
        }
        return null;
    }
}
