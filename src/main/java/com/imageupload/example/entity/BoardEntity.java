package com.imageupload.example.entity;

import java.sql.Timestamp;
import java.util.List;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.CreationTimestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class BoardEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false, length = 500)
    private String title;
    
    @Column(nullable = false, length = 10)
    private String price;

    @Column(nullable = false, length = 1000)
    private String descryption;

    @Column(nullable = false)
    private String writer;

    @Column
    private String location;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER)
    @Cascade(value = CascadeType.ALL)
    private List<FileEntity> files;

    @OneToOne(mappedBy = "boardId")
    @Cascade(value  = CascadeType.ALL)
    @JsonIgnore
    private TransactionEntity transactionEntity;

    @Column
    @CreationTimestamp()
    private Timestamp createTime;

    private String displayDate;

    private int view;

    private int cart;

}
