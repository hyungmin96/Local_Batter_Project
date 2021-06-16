package com.imageupload.example.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;
    private int mannerScore;
    private int mileage;
    private String nickname;
    private String profilePath;
    private String introduce;
    private String phoneNum;
    private String location;
    private String accountNumber;
    private String preferTime;
    
    @OneToOne(mappedBy = "profile")
    @JsonIgnore
    private UserEntity user;

}
