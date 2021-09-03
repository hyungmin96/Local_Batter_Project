package com.project.localbatter.services;

import com.project.localbatter.api.group.GroupBoardApiController.ResponseGroupExchangeDTO;
import com.project.localbatter.components.PagingUtil;
import com.project.localbatter.dto.Group.GroupPageDTO;
import com.project.localbatter.entity.GroupBoardEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.project.localbatter.entity.Exchange.QWriterExchangeEntity.writerExchangeEntity;
import static com.project.localbatter.entity.QGroupBoardEntity.groupBoardEntity;
import static com.project.localbatter.entity.QGroupEntity.groupEntity;
import static com.project.localbatter.entity.QGroupUserJoinEntity.groupUserJoinEntity;
import static com.project.localbatter.entity.QUserEntity.userEntity;

@Service
@RequiredArgsConstructor
public class SearchExchangeService {

    private final JPAQueryFactory queryFactory;
    private final PagingUtil pagingUtil;

    public Page<GroupPageDTO> getSearchGroupList(String search, Pageable pageRequest){

        long queryCount = queryFactory.selectFrom(groupEntity).fetchCount();

        JPAQuery<GroupPageDTO> query = queryFactory.select(Projections.fields(GroupPageDTO.class,
                    groupEntity.id.as("groupId"),
                    groupEntity.groupTitle.as("groupTitle"),
                    groupEntity.description.as("description"),
                    groupEntity.owner.as("owner"),
                    groupEntity.filePath.as("filePath"),
                    groupEntity.location.as("location"),
                    groupEntity.tag.as("tag"),
                    groupEntity.memberCount.as("memberCount")
                ))
                .from(groupEntity)
                .where(groupEntity.groupTitle.contains(search));

        return pagingUtil.getPageImpl(pageRequest, query, queryCount, GroupPageDTO.class);

    }

    public Page<ResponseGroupExchangeDTO> getSearchExchangeList(String search, Pageable pageRequest){

        long queryCount = queryFactory.selectFrom(groupBoardEntity)
                .where(groupBoardEntity.content.contains(search)).fetchCount();

        JPAQuery<ResponseGroupExchangeDTO> query = queryFactory
                .select(Projections.fields(ResponseGroupExchangeDTO.class,
                        groupBoardEntity.boardId,
                        groupBoardEntity.title,
                        groupBoardEntity.content,
                        writerExchangeEntity.price,
                        writerExchangeEntity.location,
                        groupBoardEntity.regTime,
                        groupBoardEntity.thumnbnailPath.as("thumbnail"),
                        userEntity.username.as("writerUsername"),
                        userEntity.profilePath.as("writerProfile"),
                        groupEntity.id.as("groupId"),
                        groupEntity.filePath.as("groupProfile"),
                        groupEntity.groupTitle.as("groupTitle")
                        ))
                .from(groupBoardEntity)
                .leftJoin(groupBoardEntity.groupUserJoinEntity, groupUserJoinEntity)
                .leftJoin(groupUserJoinEntity.group, groupEntity)
                .leftJoin(groupBoardEntity.writerExchangeEntity, writerExchangeEntity)
                .leftJoin(userEntity)
                .on(groupBoardEntity.writer.eq(userEntity.id))
                .where(groupBoardEntity.content.contains(search)
                .and(groupBoardEntity.BoardCategory.eq(GroupBoardEntity.BoardCategory.exchange)));

        return pagingUtil.getPageImpl(pageRequest, query, queryCount, ResponseGroupExchangeDTO.class);
    }

}
