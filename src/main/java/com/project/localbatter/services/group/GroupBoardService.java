package com.project.localbatter.services.group;

import com.project.localbatter.components.DeleteFile;
import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.components.PagingUtil;
import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.dto.GroupBoardDTO;
import com.project.localbatter.dto.GroupBoardFileDTO;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.GroupBoardFileEntity;
import com.project.localbatter.entity.GroupUserJoinEntity;
import com.project.localbatter.repositories.GroupBoardFileRepository;
import com.project.localbatter.repositories.GroupBoardRepository;
import com.project.localbatter.repositories.GroupUserJoinQueryRepository;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.project.localbatter.entity.QGroupBoardEntity.groupBoardEntity;
import static com.project.localbatter.entity.QGroupUserJoinEntity.groupUserJoinEntity;
import static com.project.localbatter.entity.QUserEntity.userEntity;

@Service
@RequiredArgsConstructor
@Transactional
public class GroupBoardService {

    private final GroupBoardRepository groupBoardRepository;
    private final GroupBoardFileRepository groupBoardFileRepository;
    private final GroupUserJoinQueryRepository groupUserJoinQuseryRepository;
    private final GenerateFile generateFile;
    private final JPAQueryFactory queryFactory;
    private final PagingUtil pagingUtil;

    @Transactional(readOnly = true)
    public Page<GroupBoardEntity> getBoardList(Long groupId, PageRequest request) {
        JPAQuery<GroupBoardEntity> query = queryFactory
                .selectDistinct(groupBoardEntity)
                .from(groupBoardEntity)
                .leftJoin(groupBoardEntity.groupUserJoinEntity, groupUserJoinEntity)
                .fetchJoin()
                .leftJoin(groupUserJoinEntity.user, userEntity)
                .fetchJoin()
                .where(groupUserJoinEntity.group.id.eq(groupId));

        return pagingUtil.getPageImpl(request, query, GroupBoardEntity.class);
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

    public Page<GroupBoardEntity> getNoticeList(GroupBoardDTO groupBoardDTO){
        PageRequest request = PageRequest.of(groupBoardDTO.getPage(), groupBoardDTO.getDisplay(), Sort.Direction.DESC, "boardId");
        return groupBoardRepository.findAllBygroupIdAndType(groupBoardDTO.getGroupId(), GroupBoardEntity.BoardType.notice, request);
    }

    public void delete(GroupBoardDTO groupBoardDTO){
        GroupBoardEntity groupBoardEntity = groupBoardRepository.getOne(groupBoardDTO.getBoardId());
        String[] filePathArray = groupBoardEntity.getFiles().stream().map(GroupBoardFileEntity::getName).toArray(String[]::new);
        new DeleteFile(filePathArray).deleteFile();
        groupBoardRepository.deleteById(groupBoardDTO.getBoardId());
    }

    public GroupBoardEntity post(GroupBoardDTO groupBoardDTO){
        GroupUserJoinEntity groupUserJoinEntity = groupUserJoinQuseryRepository.findGroupUserJoinEntity(groupBoardDTO.getUserId(), groupBoardDTO.getGroupId());
        GroupBoardEntity groupBoardEntity = groupBoardDTO.toEntity(groupUserJoinEntity);

        if(groupUserJoinEntity != null){
            groupBoardRepository.save(groupBoardEntity);
            List<GenerateFileDTO> groupBoardFiles = generateFile.createFile(groupBoardDTO.getBoard_img());
            groupBoardFiles.stream().map(GroupBoardFileDTO::new).collect(Collectors.toList())
                    .forEach(item -> {
                        GroupBoardFileEntity file = item.toEntity(groupBoardEntity);
                        groupBoardDTO.addFile(file);
                        groupBoardFileRepository.save(file);
                    });
        }
        return groupBoardEntity;
    }
}
