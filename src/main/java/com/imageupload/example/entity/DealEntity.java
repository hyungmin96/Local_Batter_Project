package com.imageupload.example.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DealEntity {
    
    @Id @GeneratedValue
    private Long id;

    @OneToOne
    @JoinColumn(name = "boardId")
    private BoardEntity board;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;
    

}
