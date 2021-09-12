package com.project.localbatter.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "tbl_user_profile")
public class ProfileEntity implements Serializable {

    @Id @GeneratedValue
    private Long id;
    private double mannerScore;
    private int mileage;
    private String nickname;
    private String introduce;
    private String phoneNum;
    private String location;
    private String accountNumber;
    private String preferTime;

    public void updateMannerScore(double value){
        this.mannerScore = value;
    }

    public void updateNickname(String nickname){
        this.nickname = nickname;
    }

    public void updateIntroduce(String introduce){
        this.introduce = introduce;
    }

}
