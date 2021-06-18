package com.imageupload.example.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.imageupload.example.entitylistener.TransactionListener;

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

    @OneToOne
    @JoinColumn(name="board")
    private BoardEntity boardId;

    @OneToOne
    @JoinColumn(name="buyer")
    private UserEntity buyer;

    @OneToOne
    @JoinColumn(name="seller")
    private UserEntity seller;

    private String sellerComplete;

    private String buyerComplete;

    private String transactionResult;

}
