package com.project.localbatter.api;

import com.project.localbatter.api.group.GroupBoardApiController;
import com.project.localbatter.dto.exchangeDTO.ReviewDTO;
import com.project.localbatter.entity.ProfileEntity;
import com.project.localbatter.services.ProfileService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileApiController {

    private final ProfileService profileService;

    @GetMapping("/api/profile/info/user")
    public ResponseUserDTO getUsersInfo(RequestProfileDTO requestProfileDTO){
        return profileService.getUsersInfo(requestProfileDTO);
    }

    @GetMapping("/api/profile/list/get_user_exchange")
    public Page<GroupBoardApiController.ResponseGroupExchangeDTO> getBoards(RequestProfileDTO requestProfileDTO){
        Pageable page = PageRequest.of(requestProfileDTO.getPage(), requestProfileDTO.getDisplay());
        return profileService.getBoards(requestProfileDTO, page);
    }

    @GetMapping("/api/profile/list/get_user_review")
    public List<ReviewDTO> getReviews(RequestProfileDTO requestProfileDTO){
            Pageable pageable = PageRequest.of(requestProfileDTO.getPage(), requestProfileDTO.getDisplay());
            return profileService.getProfileReviews(requestProfileDTO.getUserId(), pageable);
    }

    @Getter @Setter
    public static class ResponseUserDTO{

        private Long userId;
        private float mannerScore;
        private String nickname;
        private String introduce;
        private String phoneNum;
        private String location;
        private String accountNumber;
        private String preferTime;

        public ResponseUserDTO(Long userId, ProfileEntity profile) {
            this.userId = userId;
            this.mannerScore = profile.getMannerScore();
            this.nickname = profile.getNickname();
            this.introduce = profile.getIntroduce();
            this.phoneNum = profile.getPhoneNum();
            this.location = profile.getLocation();
            this.accountNumber = profile.getAccountNumber();
            this.preferTime = profile.getPreferTime();
        }
    }

    @Getter @Setter
    public static class RequestProfileDTO{
        private Long userId;
        private int page;
        private int display;
    }

}
