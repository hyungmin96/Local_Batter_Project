package com.project.localbatter.services;

import com.project.localbatter.api.exchange.GroupExchangeApiController;
import com.project.localbatter.api.exchange.GroupExchangeApiController.ResponseClientRequestDTO;
import com.project.localbatter.api.exchange.GroupExchangeApiController.ResponseRequestListDTO;
import com.project.localbatter.api.exchange.GroupExchangeApiController.ResponseWrtierExchangeDTO;
import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.dto.Group.GroupBoardDTO;
import com.project.localbatter.dto.TransactionDTO;
import com.project.localbatter.dto.exchangeDTO.ClientExchangeDTO;
import com.project.localbatter.dto.exchangeDTO.ExchagneFileDTO;
import com.project.localbatter.dto.exchangeDTO.WriterClientJoinDTO;
import com.project.localbatter.entity.Exchange.*;
import com.project.localbatter.entity.GroupBoardEntity;
import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.repositories.Exchange.ClientExchangeFileRepository;
import com.project.localbatter.repositories.Exchange.ClientExchangeRepository;
import com.project.localbatter.repositories.Exchange.WriterClientJoinRepository;
import com.project.localbatter.repositories.Exchange.WriterExchangeRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import static com.project.localbatter.api.exchange.GroupExchangeApiController.ResponseRequestExchangeDTO;
import static com.project.localbatter.entity.Exchange.QClientExchangeEntity.clientExchangeEntity;
import static com.project.localbatter.entity.Exchange.QExchangeFileEntity.exchangeFileEntity;
import static com.project.localbatter.entity.Exchange.QWriterClientJoinEntity.writerClientJoinEntity;
import static com.project.localbatter.entity.Exchange.QWriterExchangeEntity.writerExchangeEntity;
import static com.project.localbatter.entity.QGroupBoardEntity.groupBoardEntity;
import static com.project.localbatter.entity.QGroupBoardFileEntity.groupBoardFileEntity;
import static com.project.localbatter.entity.QGroupUserJoinEntity.groupUserJoinEntity;
import static com.project.localbatter.entity.QUserEntity.userEntity;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ClientExchangeRepository clientExchangeRepository;
    private final ClientExchangeFileRepository clientExchangeFileRepository;
    private final WriterExchangeRepository writerExchangeRepository;
    private final WriterClientJoinRepository writerClientJoinRepository;
    private final JPAQueryFactory queryFactory;
    private final GenerateFile generateFile;

    // View the list of clients requested for exchange to Writer's aritcle
    // 해당 게시글에 교환요청한 client 요청 게시글 조회
    @Transactional(readOnly = true)
    public Page<ResponseClientRequestDTO> getBoardClientRequestList(ClientExchangeDTO clientExchangeDTO,Pageable page){
        Object loginUserId = ((UserEntity) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();

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
                            exchangeFileEntity.name.as("filename"),
                            clientExchangeEntity))
                    .from(writerClientJoinEntity)
                    .join(writerClientJoinEntity.clientExchangeEntity, clientExchangeEntity)
                    .join(clientExchangeEntity.files, exchangeFileEntity)
                    .join(userEntity).on(clientExchangeEntity.userId.eq(userEntity.id))
                    .where(writerClientJoinEntity.writerExchangeEntity.id.eq(clientExchangeDTO.getBoardId()))
                    .offset(page.getPageNumber())
                    .limit(page.getPageSize())
                    .orderBy(writerClientJoinEntity.id.desc());
            return new PageImpl<>(query.fetch(), page, queryCount);
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
                            groupBoardFileEntity.name.as("files")
                    ))
                    .from(groupBoardEntity)
                    .join(groupBoardEntity.files, groupBoardFileEntity)
                    .join(groupBoardEntity.groupUserJoinEntity, groupUserJoinEntity)
                    .join(groupUserJoinEntity.user, userEntity)
                    .join(groupBoardEntity.writerExchangeEntity, writerExchangeEntity)
                    .where(userEntity.id.eq(transactionDTO.getUserId())
                    .and(groupBoardEntity.BoardCategory.eq(GroupBoardEntity.BoardCategory.exchange)))
                    .offset(page.getPageNumber())
                    .limit(page.getPageSize())
                    .orderBy(groupBoardEntity.boardId.desc());
            return new PageImpl<>(query.fetch(), page, queryCount);
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
                            groupBoardFileEntity.name.as("files")
                            ))
                    .from(writerClientJoinEntity)
                    .join(writerClientJoinEntity.clientExchangeEntity, clientExchangeEntity)
                    .join(groupBoardEntity)
                    .on(groupBoardEntity.writerExchangeEntity.id.eq(writerClientJoinEntity.writerExchangeEntity.id))
                    .join(groupBoardEntity.groupUserJoinEntity, groupUserJoinEntity)
                    .join(groupBoardEntity.files, groupBoardFileEntity)
                    .join(userEntity).on(writerClientJoinEntity.clientId.eq(userEntity.id))
                    .where(writerClientJoinEntity.clientId.eq(transactionDTO.getUserId()))
                    .offset(page.getPageNumber())
                    .limit(page.getPageSize())
                    .orderBy(writerClientJoinEntity.id.desc());
            return new PageImpl<>(query.fetch(), page, queryCount);
        }
        return null;
    }

    // * writer와 client의 교환이 진행중이지 않다면 교환요청
    // * request to exchange user from client api
    @Transactional
    public ResponseRequestExchangeDTO selectRequest(ClientExchangeDTO clientExchangeDTO){
        // search to check if an exchange with another entity is already in progress
        Integer isProgressEntity = queryFactory
                .selectOne()
                .from(writerClientJoinEntity)
                .where(writerClientJoinEntity.clientExchangeEntity.boardId.eq(clientExchangeDTO.getBoardId())
                .and(writerClientJoinEntity.status.eq(WriterClientJoinEntity.status.process)))
                .fetchFirst();
        // if isProgress value is 'null', request exchange to writer entity
        if (isProgressEntity == null) {
            WriterClientJoinEntity writerClientJoinEntity = queryFactory
                    .selectFrom(QWriterClientJoinEntity.writerClientJoinEntity)
                    .join(QWriterClientJoinEntity.writerClientJoinEntity.writerExchangeEntity)
                    .fetchJoin()
                    .join(QWriterClientJoinEntity.writerClientJoinEntity.clientExchangeEntity)
                    .fetchJoin()
                    .where(QWriterClientJoinEntity.writerClientJoinEntity.clientExchangeEntity.id.eq(clientExchangeDTO.getClientId()))
                    .fetchOne();
            // update exchange status
            writerClientJoinEntity.updateStatus(WriterClientJoinEntity.status.process);
            writerClientJoinRepository.save(writerClientJoinEntity);
            return new GroupExchangeApiController.ResponseRequestExchangeDTO(writerClientJoinEntity);
        }
        return null;
    }

    // client 요청을 취소 및 삭제
    // reject the client's exchange request
    public void cancelRequest(ClientExchangeDTO clientExchangeDTO){
        WriterClientJoinEntity writerClientJoinEntity = writerClientJoinRepository.findByClientExchangeEntity(clientExchangeDTO.getClientExchangeId());
        writerClientJoinEntity.getWriterExchangeEntity().updateCount(-1);
        writerClientJoinRepository.delete(writerClientJoinEntity);
    }

    // client가 writer 게시글에 교환을 요청
    public ClientExchangeEntity clientPost(ClientExchangeDTO clientExchangeDTO){
        WriterExchangeEntity writerExchangeEntity = writerExchangeRepository.findWriterExchangeEntityByboardId(clientExchangeDTO.getBoardId());
        ClientExchangeEntity clientExchangeEntity = clientExchangeDTO.toEntity();
        clientExchangeRepository.save(clientExchangeEntity);
        WriterClientJoinDTO writerClientJoinEntity = new WriterClientJoinDTO(writerExchangeEntity, clientExchangeEntity);
        List<GenerateFileDTO> clientExchangeFiles = generateFile.createFile(clientExchangeDTO.getImages());
        clientExchangeFiles.stream().map(ExchagneFileDTO::new).collect(Collectors.toList())
                .forEach(item -> {
                    ExchangeFileEntity file = item.toEntity(clientExchangeEntity);
                    clientExchangeDTO.addFile(file);
                    clientExchangeFileRepository.save(file);
                });
        writerExchangeEntity.updateCount(1);
        writerClientJoinRepository.save(writerClientJoinEntity.toEntity());
        return clientExchangeEntity;
    }

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
