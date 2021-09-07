package com.project.localbatter.services.Exchange;

import com.project.localbatter.api.exchange.ExchangeChatApiController.ResponseChatListDTO;
import com.project.localbatter.api.exchange.ExchangeChatApiController.ResponseServiceDTO;
import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.components.PagingUtil;
import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.dto.exchangeDTO.ExchangeChatMessageDTO;
import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import com.project.localbatter.entity.ExchangeChatEntity;
import com.project.localbatter.entity.QUserEntity;
import com.project.localbatter.repositories.Exchange.ExchangeChatRepository;
import com.project.localbatter.repositories.Exchange.WriterClientJoinRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.project.localbatter.entity.Exchange.QLocalBatterServiceEntity.localBatterServiceEntity;
import static com.project.localbatter.entity.Exchange.QWriterClientJoinEntity.writerClientJoinEntity;
import static com.project.localbatter.entity.QExchangeChatEntity.exchangeChatEntity;

@Service
@RequiredArgsConstructor
public class ExchangeChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final JPAQueryFactory queryFactory;
    private final PagingUtil pagingUtil;
    private final GenerateFile generateFile;
    private final ExchangeChatRepository exchangeChatRepository;
    private final WriterClientJoinRepository writerClientJoinRepository;


    public void sendMessage(ExchangeChatMessageDTO messageDTO){
        exchangeChatRepository.save(messageDTO.toEntity());
        simpMessagingTemplate.convertAndSend("/exchange/userId=" + messageDTO.getReceiveId(), messageDTO);
    }

    public ExchangeChatMessageDTO uploadImageToChat(ExchangeChatMessageDTO messageDTO, MultipartFile[] files){
        List<GenerateFileDTO> items = generateFile.createFile(files);
        items.forEach(item -> {
            messageDTO.setMessage(item.getName());
            exchangeChatRepository.save(messageDTO.toEntity());
            simpMessagingTemplate.convertAndSend("/exchange/userId=" + messageDTO.getReceiveId(), messageDTO);
        });
        return messageDTO;
    }

    // Exit exchangeId's chatting room
    // exchangeId의 채팅방 나가기
    public void exitChat(Long userId, Long exchangeId){
        WriterClientJoinEntity writerClientJoinEntity = writerClientJoinRepository.findByExchangeId(exchangeId);
        writerClientJoinEntity.exitChatRoom(userId);
        writerClientJoinRepository.save(writerClientJoinEntity);
    }

    // Get registed service of exchangeId's room
    // exchangeId 채팅방에 등록된 서비스를 조회
    public ResponseServiceDTO getService(Long userId, Long exchangeId){
        return queryFactory.select(Projections.constructor(ResponseServiceDTO.class, localBatterServiceEntity))
                .from(localBatterServiceEntity)
                .where(localBatterServiceEntity.userId.eq(userId)
                .and(localBatterServiceEntity.writerClientJoinId.eq(exchangeId)))
                .fetchOne();
    }

    // Get all chats of exchangeId's room
    // exchangeId 채팅방의 모든 채팅내역을 조회
    public Page<ResponseChatListDTO> getChatItems(Long exchangeId, Pageable pageRequest){
        QUserEntity senderEntity = new QUserEntity("senderEntity");
        QUserEntity receiveEntity = new QUserEntity("receiveEntity");
        Long queryCount = queryFactory
                .selectFrom(exchangeChatEntity)
                .where(exchangeChatEntity.exchangeId.eq(exchangeId))
                .fetchCount();
        JPAQuery<ResponseChatListDTO> query = queryFactory
                .select(Projections.constructor(ResponseChatListDTO.class,
                        senderEntity.id.as("userId"),
                        senderEntity.username.as("username"),
                        senderEntity.profilePath.as("userProfile"),
                        receiveEntity.id.as("targetId"),
                        receiveEntity.username.as("targetUsername"),
                        receiveEntity.profilePath.as("targetProfile"),
                        exchangeChatEntity.exchangeId.as("exchangeId"),
                        exchangeChatEntity.senderId.as("senderId"),
                        exchangeChatEntity.type.as("type"),
                        exchangeChatEntity.message.as("message"),
                        exchangeChatEntity.coordinate.as("coordinate"),
                        exchangeChatEntity.regTime.as("regTime")
                        ))
                .from(exchangeChatEntity)
                .innerJoin(senderEntity).on(exchangeChatEntity.senderId.eq(senderEntity.id))
                .innerJoin(receiveEntity).on(exchangeChatEntity.receiveId.eq(receiveEntity.id))
                .where(exchangeChatEntity.exchangeId.eq(exchangeId))
                .offset(pageRequest.getPageNumber())
                .limit(pageRequest.getPageSize())
                .orderBy(exchangeChatEntity.regTime.desc());
        return pagingUtil.getPageImpl(pageRequest, query, queryCount, ExchangeChatEntity.class);
    }


    // Get List that writer accept request for exchange of client
    // writer가 교환을 수락한 목록 조회
    public List<ResponseChatListDTO> getExchageChatList(Long userId){
        QUserEntity writerId = new QUserEntity("writerEntity");
        QUserEntity clientId = new QUserEntity("clientEntity");
        return queryFactory
                .select(Projections.constructor(ResponseChatListDTO.class,
                        writerId.id.as("userId"),
                        writerId.username.as("username"),
                        writerId.profilePath.as("userProfile"),
                        clientId.id.as("targetId"),
                        clientId.username.as("targetUsername"),
                        clientId.profilePath.as("targetProfile"),
                        writerClientJoinEntity.clientExchangeEntity.id.as("exchangeId"),
                        exchangeChatEntity.senderId.as("senderId"),
                        exchangeChatEntity.type.as("type"),
                        exchangeChatEntity.message.as("message"),
                        exchangeChatEntity.coordinate.as("coordinate"),
                        exchangeChatEntity.regTime.as("regTime")
                ))
                .from(writerClientJoinEntity)
                .innerJoin(writerId).on(writerClientJoinEntity.writerId.eq(writerId.id))
                .innerJoin(clientId).on(writerClientJoinEntity.clientId.eq(clientId.id))
                .innerJoin(exchangeChatEntity).on(writerClientJoinEntity.clientExchangeEntity.id.eq(exchangeChatEntity.exchangeId))
                .where(writerClientJoinEntity.writerId.eq(userId).or(writerClientJoinEntity.clientId.eq(userId))
                        , exchangeChatEntity.id.eq(JPAExpressions.select(exchangeChatEntity.id.max())
                .from(exchangeChatEntity)
                .where(exchangeChatEntity.exchangeId.eq(writerClientJoinEntity.clientExchangeEntity.id))))
                .orderBy(exchangeChatEntity.regTime.asc())
                .fetch();
    }
}
