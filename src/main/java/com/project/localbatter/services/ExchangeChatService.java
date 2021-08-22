package com.project.localbatter.services;

import com.project.localbatter.api.exchange.ExchangeChatApiController.ResponseChatListDTO;
import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.components.PagingUtil;
import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.dto.exchangeDTO.ExchangeChatMessageDTO;
import com.project.localbatter.entity.ExchangeChatEntity;
import com.project.localbatter.entity.QUserEntity;
import com.project.localbatter.repositories.Exchange.ExchangeChatRepository;
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

    public void sendMessage(ExchangeChatMessageDTO messageDTO){
        exchangeChatRepository.save(messageDTO.toEntity());
        simpMessagingTemplate.convertAndSend("/exchange/userId=" + messageDTO.getTargetId(), messageDTO);
    }

    public ExchangeChatMessageDTO uploadImageToChat(ExchangeChatMessageDTO messageDTO, MultipartFile[] files){
        List<GenerateFileDTO> items = generateFile.createFile(files);
        items.forEach(item -> {
            messageDTO.setMessage(item.getName());
            exchangeChatRepository.save(messageDTO.toEntity());
            simpMessagingTemplate.convertAndSend("/exchange/userId=" + messageDTO.getTargetId(), messageDTO);
        });
        return messageDTO;
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
                        exchangeChatEntity.type.as("type"),
                        exchangeChatEntity.message.as("message"),
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

        List<ResponseChatListDTO> items = queryFactory
                .select(Projections.constructor(ResponseChatListDTO.class,
                        writerId.id.as("userId"),
                        writerId.username.as("username"),
                        writerId.profilePath.as("userProfile"),
                        clientId.id.as("targetId"),
                        clientId.username.as("targetUsername"),
                        clientId.profilePath.as("targetProfile"),
                        writerClientJoinEntity.clientExchangeEntity.id.as("exchangeId"),
                        exchangeChatEntity.type.as("type"),
                        exchangeChatEntity.message.as("message"),
                        exchangeChatEntity.regTime.as("regTime")
                ))
                .from(writerClientJoinEntity)
                .innerJoin(writerId).on(writerClientJoinEntity.writerId.eq(writerId.id))
                .innerJoin(clientId).on(writerClientJoinEntity.clientId.eq(clientId.id))
                .innerJoin(exchangeChatEntity).on(writerClientJoinEntity.clientExchangeEntity.id.eq(exchangeChatEntity.exchangeId))
                .where(exchangeChatEntity.id.eq(JPAExpressions.select(exchangeChatEntity.id.max())
                .from(exchangeChatEntity)
                .where(exchangeChatEntity.exchangeId.eq(writerClientJoinEntity.clientExchangeEntity.id))))
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
