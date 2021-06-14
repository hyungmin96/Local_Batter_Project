package com.imageupload.example.models;

import lombok.Data;

@Data
public class UserProfileInfo {

    private String nickname;
    private String introduce;
    private String profilePath;
    private String location;
    private String preferTime = "상관없음";

}
