package com.project.localbatter.components;

import com.project.localbatter.api.exchange.GroupExchangeApiController.ResponseClientAndWriterBoard;
import com.project.localbatter.api.exchange.GroupExchangeApiController.ResponseClientRequestDTO;
import com.project.localbatter.api.exchange.GroupExchangeApiController.ResponseRequestListDTO;
import com.project.localbatter.api.exchange.GroupExchangeApiController.ResponseWrtierExchangeDTO;
import com.project.localbatter.dto.Group.GroupBoardDTO;
import com.project.localbatter.dto.TransactionDTO;
import com.project.localbatter.dto.exchangeDTO.ClientExchangeDTO;
import com.project.localbatter.dto.exchangeDTO.ExchangeChatMessageDTO;
import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.QUserEntity;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import static com.project.localbatter.entity.Exchange.QClientExchangeEntity.clientExchangeEntity;
import static com.project.localbatter.entity.Exchange.QWriterClientJoinEntity.writerClientJoinEntity;
import static com.project.localbatter.entity.Exchange.QWriterExchangeEntity.writerExchangeEntity;
import static com.project.localbatter.entity.QGroupBoardEntity.groupBoardEntity;
import static com.project.localbatter.entity.QGroupBoardFileEntity.groupBoardFileEntity;
import static com.project.localbatter.entity.QGroupUserJoinEntity.groupUserJoinEntity;
import static com.project.localbatter.entity.QUserEntity.userEntity;

@Component
@RequiredArgsConstructor
public class ExchangeQueryComponent {

    private final JPAQueryFactory queryFactory;
    private final PagingUtil pagingUtil;

    // View the client'request board and writer's board infomation
    // client와 writer 게시글 정보를 조회
    public ResponseClientAndWriterBoard getClientAndWriterBoard(TransactionDTO transactionDTO){

        QUserEntity writerUserEntity = new QUserEntity("writerUserEntity");
        QUserEntity clientUserEntity = new QUserEntity("clientUserEntity");

        return queryFactory
                .selectDistinct(Projections.constructor(ResponseClientAndWriterBoard.class,
                        writerExchangeEntity.userId.as("writerId"),
                        clientExchangeEntity.userId.as("clientId"),
                        groupBoardEntity.as("board"),
                        clientExchangeEntity.id.as("clientExchangeId"),
                        writerUserEntity.username.as("writerUsername"),
                        writerUserEntity.profilePath.as("writerProfile"),
                        clientUserEntity.username.as("clientUsername"),
                        clientUserEntity.profilePath.as("clientProfile"),
                        writerExchangeEntity,
                        clientExchangeEntity
                ))
                .from(writerClientJoinEntity)
                .innerJoin(writerClientJoinEntity.clientExchangeEntity, clientExchangeEntity)
                .innerJoin(writerClientJoinEntity.writerExchangeEntity, writerExchangeEntity)
                .innerJoin(groupBoardEntity).on(writerExchangeEntity.id.eq(groupBoardEntity.writerExchangeEntity.id))
                .innerJoin(clientUserEntity).on(clientExchangeEntity.userId.eq(clientUserEntity.id))
                .innerJoin(writerUserEntity).on(writerExchangeEntity.userId.eq(writerUserEntity.id))
                .where(writerClientJoinEntity.id.eq(transactionDTO.getClientWriterExchangeId()))
                .fetchOne();
    }

    // View the list of clients requested for exchange to Writer's aritcle
    // 해당 게시글에 교환요청한 client 요청 게시글 조회
    @Transactional(readOnly = true)
    public Page<ResponseClientRequestDTO> getBoardClientRequestList(ClientExchangeDTO clientExchangeDTO, Pageable page){

        Long queryCount = queryFactory
                .select(writerClientJoinEntity.id)
                .from(writerClientJoinEntity)
                .where(writerClientJoinEntity.writerExchangeEntity.id.eq(clientExchangeDTO.getBoardId()))
                .fetchCount();

        if(queryCount > 0){
            JPAQuery<ResponseClientRequestDTO> query = queryFactory
                    .select(Projections.constructor(ResponseClientRequestDTO.class,
                            userEntity.id.as("id"),
                            userEntity.username.as("username"),
                            userEntity.profilePath.as("userProfile"),
                            clientExchangeEntity.thumbnail.as("filename"),
                            writerClientJoinEntity.id.as("exchangeId"),
                            clientExchangeEntity))
                    .from(writerClientJoinEntity)
                    .join(writerClientJoinEntity.clientExchangeEntity, clientExchangeEntity)
                    .join(userEntity).on(clientExchangeEntity.userId.eq(userEntity.id))
                    .where(writerClientJoinEntity.writerExchangeEntity.id.eq(clientExchangeDTO.getBoardId()))
                    .offset(page.getPageNumber())
                    .limit(page.getPageSize())
                    .orderBy(writerClientJoinEntity.id.desc());

            return pagingUtil.getPageImpl(page, query, queryCount, WriterClientJoinEntity.class);
        }
        return null;
    }

    // view writer's board List
    @Transactional(readOnly = true)
    public Page<ResponseWrtierExchangeDTO> getWriterBoards(TransactionDTO transactionDTO, Pageable page){
        Long queryCount = queryFactory
                .select(writerExchangeEntity.id)
                .from(writerExchangeEntity)
                .where(writerExchangeEntity.userId.eq(transactionDTO.getUserId()))
                .fetchCount();

        if(queryCount > 0){
            JPAQuery<ResponseWrtierExchangeDTO> query = queryFactory
                    .select(Projections.constructor(ResponseWrtierExchangeDTO.class,
                            writerExchangeEntity.userId.as("writerId"),
                            writerExchangeEntity.id.as("writerExchangeId"),
                            groupBoardEntity.boardId.as("boardId"),
                            writerExchangeEntity.requestCount.as("requestCount"),
                            groupBoardEntity.title.as("title"),
                            groupBoardEntity.content.as("content"),
                            groupBoardEntity.regTime.as("regTime"),
                            groupBoardEntity.thumnbnailPath.as("tumbnail")
                    ))
                    .from(groupBoardEntity)
                    .leftJoin(groupBoardEntity.groupUserJoinEntity, groupUserJoinEntity)
                    .leftJoin(groupUserJoinEntity.user, userEntity)
                    .leftJoin(groupBoardEntity.writerExchangeEntity, writerExchangeEntity)
                    .where(userEntity.id.eq(transactionDTO.getUserId())
                            .and(groupBoardEntity.BoardCategory.eq(GroupBoardEntity.BoardCategory.exchange)))
                    .offset(page.getPageNumber())
                    .limit(page.getPageSize())
                    .orderBy(writerExchangeEntity.requestCount.desc());

            return pagingUtil.getPageImpl(page, query, queryCount, GroupBoardEntity.class);
        }
        return null;
    }

    // view client's(login user) request exchange for board
    @Transactional(readOnly = true)
    public Page<ResponseRequestListDTO> getRequestList(TransactionDTO transactionDTO, Pageable page) {
        Long queryCount = queryFactory
                .select(writerClientJoinEntity.id)
                .from(writerClientJoinEntity)
                .where(writerClientJoinEntity.clientId.eq(transactionDTO.getUserId()))
                .fetchCount();

        if (queryCount > 0) {
            JPAQuery<ResponseRequestListDTO> query = queryFactory
                    .select(Projections.constructor(ResponseRequestListDTO.class,
                            writerClientJoinEntity.clientId.as("clientId"),
                            clientExchangeEntity.id.as("clientExchangeId"),
                            userEntity.username.as("clientUsername"),
                            userEntity.profilePath.as("clientProfile"),
                            writerClientJoinEntity.writerId.as("writerId"),
                            groupBoardEntity.boardId,
                            groupBoardEntity.title,
                            groupBoardEntity.content,
                            writerClientJoinEntity.status.as("status"),
                            writerClientJoinEntity.regTime.as("regTime"),
                            clientExchangeEntity.thumbnail.as("thumbnail")
                    ))
                    .from(writerClientJoinEntity)
                    .leftJoin(writerClientJoinEntity.clientExchangeEntity, clientExchangeEntity)
                    .leftJoin(groupBoardEntity)
                    .on(groupBoardEntity.writerExchangeEntity.id.eq(writerClientJoinEntity.writerExchangeEntity.id))
                    .leftJoin(groupBoardEntity.groupUserJoinEntity, groupUserJoinEntity)
                    .leftJoin(userEntity).on(writerClientJoinEntity.clientId.eq(userEntity.id))
                    .where(writerClientJoinEntity.clientId.eq(transactionDTO.getUserId()))
                    .offset(page.getPageNumber())
                    .limit(page.getPageSize())
                    .orderBy(writerClientJoinEntity.id.desc());

            return pagingUtil.getPageImpl(page, query, queryCount, WriterClientJoinEntity.class);
        }
        return null;
    }

    // writer와 client의 교환이 진행중이지 않다면 교환요청
    // request to exchange user from client api
    public ExchangeChatMessageDTO getWriterClientExchangeEntity(TransactionDTO transactionDTO){
        // search to check if an exchange with another entity is already in progress
        return queryFactory
                .select(Projections.fields(ExchangeChatMessageDTO.class,
                        writerExchangeEntity.userId.as("userId"),
                        clientExchangeEntity.userId.as("targetId"),
                        userEntity.username.as("targetUsername"),
                        userEntity.profilePath.as("targetProfile"),
                        writerClientJoinEntity.clientExchangeEntity.id.as("exchangeId")
                        ))
                .from(writerClientJoinEntity)
                .join(writerClientJoinEntity.writerExchangeEntity, writerExchangeEntity)
                .join(writerClientJoinEntity.clientExchangeEntity, clientExchangeEntity)
                .join(userEntity).on(clientExchangeEntity.userId.eq(userEntity.id))
                .where(writerClientJoinEntity.id.eq(transactionDTO.getClientWriterExchangeId()))
                .fetchOne();
    }

    // get Client's exchange board Info
    public GroupBoardEntity getExchangeInfo(GroupBoardDTO groupBoardDTO){
        return queryFactory
                .selectFrom(groupBoardEntity)
                .innerJoin(groupBoardEntity.groupUserJoinEntity, groupUserJoinEntity)
                .fetchJoin()
                .innerJoin(groupUserJoinEntity.user, userEntity)
                .fetchJoin()
                .innerJoin(groupBoardEntity.writerExchangeEntity, writerExchangeEntity)
                .fetchJoin()
                .leftJoin(groupBoardEntity.files, groupBoardFileEntity)
                .fetchJoin()
                .where(groupBoardEntity.boardId.eq(groupBoardDTO.getBoardId()))
                .fetchFirst();
    }
}
