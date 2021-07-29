package com.project.localbatter.entity;

import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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

    private float mannerScore;

    private String commentValue;

    @CreationTimestamp
    private Timestamp regDate;

}
