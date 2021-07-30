package com.project.localbatter.entity;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

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
    private float mannerScore;
    private int mileage;
    private String nickname;
    private String introduce;
    private String phoneNum;
    private String location;
    private String accountNumber;
    private String preferTime;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private UserEntity user;
}
