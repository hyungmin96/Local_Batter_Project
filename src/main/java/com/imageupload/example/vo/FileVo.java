package com.imageupload.example.Vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class fileVo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileid;

    @Column
    private String tempName;
    
    @Column
    private String filePath;

    @Column
    private String originName;

    @Column
    private long fileSize;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    @JsonIgnore
    private boardVo board;
    
}
