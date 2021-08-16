package com.project.localbatter.entity;

import com.project.localbatter.entity.Exchange.ExchangeChatFileEntity;
import com.project.localbatter.entity.Exchange.WriterClientJoinEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

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

    @Column(name = "wrtier_id")
    private Long writerId;

    @Column(name = "client_id")
    private Long clientId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exchange_id")
    private WriterClientJoinEntity writerClientJoinEntity;

    @OneToMany(mappedBy = "chat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ExchangeChatFileEntity> files;

}
