package com.imageupload.example.entity;

import java.sql.Timestamp;

import javax.persistence.*;

import com.imageupload.example.entitylistener.TransactionListener;
import com.imageupload.example.enumtype.TransactionEnumType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EntityListeners(TransactionListener.class)
public class TransactionEntity {
    
    @Id @GeneratedValue
    private long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="board")
    private BoardEntity boardId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="buyer")
    private UserEntity buyer;

    private String sellerCommentId;

    private String buyerCommentId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="seller")
    private UserEntity seller;

    @Enumerated(EnumType.STRING)
    private TransactionEnumType type;

    private String sellerComplete;

    private String buyerComplete;

    private String transactionResult;

    @CreationTimestamp
    private Timestamp createTime;

}
