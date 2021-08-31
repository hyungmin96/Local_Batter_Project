package com.project.localbatter.services;

import com.project.localbatter.api.ProfileApiController.RequestProfileDTO;
import com.project.localbatter.api.ProfileApiController.ResponseUserDTO;
import com.project.localbatter.api.group.GroupBoardApiController.ResponseGroupExchangeDTO;
import com.project.localbatter.components.PagingUtil;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.project.localbatter.entity.Exchange.QWriterExchangeEntity.writerExchangeEntity;
import static com.project.localbatter.entity.QGroupBoardEntity.groupBoardEntity;
import static com.project.localbatter.entity.QProfileEntity.profileEntity;
import static com.project.localbatter.entity.QUserEntity.userEntity;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final JPAQueryFactory queryFactory;
    private final PagingUtil pagingUtil;

    @Transactional(readOnly = true)
    public ResponseUserDTO getUsersInfo(RequestProfileDTO requestProfileDTO){
        return queryFactory
                .select(Projections.constructor(ResponseUserDTO.class,
                        userEntity.id.as("userId"),
                        profileEntity))
                .from(userEntity)
                .innerJoin(userEntity.profile, profileEntity)
                .where(userEntity.id.eq(requestProfileDTO.getUserId()))
                .fetchOne();
    }

    // Get all posts from the user who created the exchange request
    // 사용자가 작성한 교환요청 게시물 목록 조회
    @Transactional(readOnly = true)
    public Page<ResponseGroupExchangeDTO> getBoards(RequestProfileDTO requestProfileDTO, Pageable page){
        long queryCount = queryFactory.selectFrom(groupBoardEntity)
                .where(groupBoardEntity.writer.eq(requestProfileDTO.getUserId()))
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
                .where(groupBoardEntity.writer.eq(requestProfileDTO.getUserId()));

        return pagingUtil.getPageImpl(page, query, queryCount, ResponseGroupExchangeDTO.class);
    }

    public void getReviews(RequestProfileDTO requestProfileDTO){

    }

}
