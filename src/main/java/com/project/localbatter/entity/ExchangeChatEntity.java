package com.project.localbatter.entity;

import com.project.localbatter.dto.exchangeDTO.ExchangeChatMessageDTO;
import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_exchange_chat")
public class ExchangeChatEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "chat_id")
    private Long id;

    @Column(name = "chat_message")
    private String message;

    @Column(name = "sender_id")
    private Long senderId;

    @Column(name = "receive_id")
    private Long receiveId;

    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private ExchangeChatMessageDTO.ExchangeMessageType type;

    @Column(name = "exchange_id")
    private Long exchangeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_client_id")
    private WriterClientJoinEntity wrtierClientJoinId;

}
