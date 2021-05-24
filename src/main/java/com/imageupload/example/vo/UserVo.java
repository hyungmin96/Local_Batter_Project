package com.imageupload.example.Vo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserVo{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column(unique = true)
    // accout email info.
    private String username;

    @Column
    private String password;

    @Column
    private String phoneNum;

    @Column
    private String nickname;

    @Column
    @Enumerated(EnumType.STRING)
    private Role Role;

    @Column
    private int mannerScore;

    @Column
    private int mileage;

}
