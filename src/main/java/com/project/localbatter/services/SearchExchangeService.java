package com.project.localbatter.services;

import com.project.localbatter.api.group.GroupBoardApiController.ResponseGroupExchangeDTO;
import com.project.localbatter.components.PagingUtil;
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

@Service
@RequiredArgsConstructor
public class SearchExchangeService {

    private final JPAQueryFactory queryFactory;
    private final PagingUtil pagingUtil;

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
                        groupBoardEntity.thumnbnailPath.as("thumbnail")
                        ))
                .from(groupBoardEntity)
                .innerJoin(groupBoardEntity.writerExchangeEntity, writerExchangeEntity)
                .where(groupBoardEntity.content.contains(search)
                .and(groupBoardEntity.BoardCategory.eq(GroupBoardEntity.BoardCategory.exchange)));

        return pagingUtil.getPageImpl(pageRequest, query, queryCount, ResponseGroupExchangeDTO.class);
    }

}
