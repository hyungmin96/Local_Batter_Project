package com.project.localbatter.services;

import com.project.localbatter.api.exchange.ExchangeChatApiController.ResponseChatListDTO;
import com.project.localbatter.components.PagingUtil;
import com.project.localbatter.dto.exchangeDTO.ExchangeChatMessageDTO;
import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import com.project.localbatter.entity.ExchangeChatEntity;
import com.project.localbatter.entity.QUserEntity;
import com.project.localbatter.repositories.Exchange.ExchangeChatRepository;
import com.project.localbatter.repositories.Exchange.WriterClientJoinRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.util.List;
import static com.project.localbatter.entity.Exchange.QWriterClientJoinEntity.writerClientJoinEntity;
import static com.project.localbatter.entity.QExchangeChatEntity.exchangeChatEntity;

@Service
@RequiredArgsConstructor
public class ExchangeChatService {

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final JPAQueryFactory queryFactory;
    private final PagingUtil pagingUtil;
    private final ExchangeChatRepository exchangeChatRepository;
    private final WriterClientJoinRepository writerClientJoinRepository;
    private final Logger log = LogManager.getLogger();

    public void sendMessage(ExchangeChatMessageDTO messageDTO){
        WriterClientJoinEntity writerClientJoinEntity = writerClientJoinRepository.findByExchangeId(messageDTO.getExchangeId());
        exchangeChatRepository.save(messageDTO.toEntity(writerClientJoinEntity));
        simpMessagingTemplate.convertAndSend("/exchange/userId=" + messageDTO.getTargetId(), messageDTO);
    }

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
                        exchangeChatEntity.message.as("message")
                        ))
                .from(exchangeChatEntity)
                .innerJoin(senderEntity).on(exchangeChatEntity.senderId.eq(senderEntity.id))
                .innerJoin(receiveEntity).on(exchangeChatEntity.receiveId.eq(receiveEntity.id))
                .where(exchangeChatEntity.exchangeId.eq(exchangeId))
                .offset(pageRequest.getPageNumber())
                .limit(pageRequest.getPageSize())
                .orderBy(exchangeChatEntity.regTime.asc());

        return pagingUtil.getPageImpl(pageRequest, query, queryCount, ExchangeChatEntity.class);
    }

    // 방 마다 가장 최근 채팅내역을 불러옴
    // 매우 비효율적인 쿼리작성인듯하다.. db설계도 이 부분은 다시 공부해봐야함
    private BooleanExpression isMaxIdvalue(){
        Long maxId = queryFactory
                .select(exchangeChatEntity.id.max())
                .from(writerClientJoinEntity)
                .leftJoin(writerClientJoinEntity.chat, exchangeChatEntity)
                .where(writerClientJoinEntity.clientExchangeEntity.id.eq(exchangeChatEntity.exchangeId))
                .fetchFirst();
        return (maxId != null) ?  exchangeChatEntity.id.eq(maxId) : null;
    }

    // Get List that writer accept request for exchange of client
    // writer가 교환을 수락한 목록 조회
    public List<ResponseChatListDTO> getExchageChatList(Long userId){
        QUserEntity writerId = new QUserEntity("writerEntity");
        QUserEntity clientId = new QUserEntity("clientEntity");

        List<ResponseChatListDTO> items = queryFactory
                .select(Projections.constructor(ResponseChatListDTO.class,
                        writerId.id.as("userId"),
                        writerId.username.as("username"),
                        writerId.profilePath.as("userProfile"),
                        clientId.id.as("targetId"),
                        clientId.username.as("targetUsername"),
                        clientId.profilePath.as("targetProfile"),
                        writerClientJoinEntity.clientExchangeEntity.id.as("exchangeId"),
                        exchangeChatEntity.message.as("message")
                ))
                .from(writerClientJoinEntity)
                .innerJoin(writerId).on(writerClientJoinEntity.writerId.eq(writerId.id))
                .innerJoin(clientId).on(writerClientJoinEntity.clientId.eq(clientId.id))
                .leftJoin(writerClientJoinEntity.chat, exchangeChatEntity)
                .where(writerClientJoinEntity.status.eq(WriterClientJoinEntity.status.process), isMaxIdvalue())
                .fetch();
        /*
        *  로그인한 userId를 기준으로 정렬
        *  로그인한 계정이 거래를 수락하여 writer 객체에 들어가거나
        *  client 객체에 들어갈 경우 로그인한 user 정보를 userId 객체에 담음
        *  원래의 userId 정보였던 객체는 targetId 객체에 담김
        */
        items.forEach(item -> {
            if(item.getTargetId().equals(userId)){
                Long userTemp = item.getUserId();
                String usernameTemp = item.getUsername();
                String userProfileTemp = item.getUserProfile();

                item.setUserId(userId);
                item.setUsername(item.getTargetUsername());
                item.setUserProfile(item.getUserProfile());

                item.setTargetId(userTemp);
                item.setTargetUsername(usernameTemp);
                item.setUserProfile(userProfileTemp);
            }
        });
        return items;
    }

}
