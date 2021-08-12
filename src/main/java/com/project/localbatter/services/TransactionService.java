package com.project.localbatter.services;

import com.project.localbatter.api.exchange.TransactionApiController.ResponseRequestListDTO;
import com.project.localbatter.dto.TransactionDTO;
import com.project.localbatter.repositories.Exchange.WriterClientJoinRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.project.localbatter.entity.Exchange.QWriterClientJoinEntity.writerClientJoinEntity;
import static com.project.localbatter.entity.QGroupBoardEntity.groupBoardEntity;
import static com.project.localbatter.entity.QGroupUserJoinEntity.groupUserJoinEntity;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final WriterClientJoinRepository writerClientJoinRepository;
    private final JPAQueryFactory queryFactory;

    // view client's(login user) request exchange for board
    public Page<ResponseRequestListDTO> getRequestList(TransactionDTO transactionDTO, Pageable page){
        JPAQuery<ResponseRequestListDTO> query = queryFactory
                .select(Projections.constructor(ResponseRequestListDTO.class,
                        writerClientJoinEntity, groupBoardEntity, groupUserJoinEntity.user))
                .from(writerClientJoinEntity)
                .join(groupBoardEntity)
                .on(groupBoardEntity.writerExchangeEntity.id.eq(writerClientJoinEntity.writerExchangeEntity.id))
                .join(groupBoardEntity.groupUserJoinEntity, groupUserJoinEntity)
                .where(writerClientJoinEntity.clientId.eq(transactionDTO.getUserId()))
                .offset(page.getPageNumber())
                .limit(page.getPageSize())
                .orderBy(writerClientJoinEntity.id.desc());
        return new PageImpl<>(query.fetch(), page, query.fetchCount());
    }

}
