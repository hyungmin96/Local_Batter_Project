package com.project.localbatter.entity.Exchange;

import com.project.localbatter.entity.BaseTimeEntity;
import com.project.localbatter.entity.ExchangeChatEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "tbl_exchage_chat_file")
public class ExchangeChatFileEntity extends BaseTimeEntity {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tbl_chat_id")
    private ExchangeChatEntity chat;

}
