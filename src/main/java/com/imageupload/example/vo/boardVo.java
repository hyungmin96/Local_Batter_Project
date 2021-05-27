package com.imageupload.example.Vo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
public class boardVo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false, length = 25)
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
    @JsonIgnore
    @Cascade(value = CascadeType.ALL)
    private List<fileVo> files;

    @Column
    @CreationTimestamp()
    private Timestamp createTime;

    private String displayDate;

}
