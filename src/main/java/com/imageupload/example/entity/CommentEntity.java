package com.imageupload.example.entity;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import org.hibernate.annotations.CreationTimestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentEntity {
    
    @Id @GeneratedValue
    private Long id;

    private String writer;

    private String target;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "boardId")
    private BoardEntity boardId;

    private String commentValue;

    @CreationTimestamp
    private Timestamp regDate;

}
