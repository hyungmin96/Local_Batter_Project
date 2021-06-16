package com.imageupload.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

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

}
