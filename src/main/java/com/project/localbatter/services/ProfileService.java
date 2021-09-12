package com.project.localbatter.services;

import com.project.localbatter.api.ProfileApiController.RequestProfileDTO;
import com.project.localbatter.api.ProfileApiController.ResponseUserDTO;
import com.project.localbatter.api.group.GroupBoardApiController.ResponseGroupExchangeDTO;
import com.project.localbatter.components.GenerateFile;
import com.project.localbatter.components.PagingUtil;
import com.project.localbatter.dto.GenerateFileDTO;
import com.project.localbatter.dto.ProfileEditDTO;
import com.project.localbatter.dto.exchangeDTO.ReviewDTO;
import com.project.localbatter.entity.ProfileEntity;
import com.project.localbatter.entity.UserEntity;
import com.project.localbatter.repositories.ProfileRepository;
import com.project.localbatter.repositories.UserRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.project.localbatter.entity.Exchange.QReviewEntity.reviewEntity;
import static com.project.localbatter.entity.Exchange.QWriterExchangeEntity.writerExchangeEntity;
import static com.project.localbatter.entity.QGroupBoardEntity.groupBoardEntity;
import static com.project.localbatter.entity.QProfileEntity.profileEntity;
import static com.project.localbatter.entity.QUserEntity.userEntity;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProfileService {

    private final JPAQueryFactory queryFactory;
    private final PagingUtil pagingUtil;
    private final GenerateFile generateFile;
    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    // Edit profile info
    // 사용자 프로필 정보 수정
    @Transactional
    public ProfileEditDTO editProfile(ProfileEditDTO profileEditDTO){
        List<GenerateFileDTO> file = generateFile.createFile(profileEditDTO.getProfileImage());

        UserEntity userEntity = userRepository.findById(profileEditDTO.getUserId()).get();
        ProfileEntity profileEntity = userEntity.getProfile();

        userEntity.updateProfileImage(file.get(0).getName());
        profileEntity.updateNickname(profileEditDTO.getUsername());
        profileEntity.updateIntroduce(profileEditDTO.getIntroduce());

        userRepository.save(userEntity);

        profileEditDTO.setProfileImage(null);
        return profileEditDTO;
    }

    // Get user's all reviews
    // 사용자에게 등록된 리뷰 목록을 조회
    public List<ReviewDTO> getProfileReviews(Long userId, Pageable pageable){

        List<Long> index = queryFactory
                .select(reviewEntity.reviewId).from(reviewEntity)
                .where(reviewEntity.reviewReceiveId.eq(userId))
                .orderBy(reviewEntity.reviewId.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getPageNumber())
                .fetch();

        return queryFactory
                .select(Projections.fields(ReviewDTO.class,
                        reviewEntity.reviewWriterId,
                        reviewEntity.reviewReceiveId,
                        reviewEntity.content,
                        reviewEntity.score,
                        userEntity.profilePath.as("reviewWriterProfile"),
                        userEntity.username.as("reviewWriterUsername")
                        ))
                .from(reviewEntity)
                .innerJoin(userEntity).on(reviewEntity.reviewWriterId.eq(userEntity.id))
                .where(reviewEntity.reviewId.in(index))
                .fetch();
    }

    // Get user's info
    // 사용자의 info를 조회
    public ResponseUserDTO getUsersInfo(RequestProfileDTO requestProfileDTO){
        return queryFactory
                .select(Projections.constructor(ResponseUserDTO.class,
                        userEntity.id.as("userId"),
                        profileEntity))
                .from(userEntity)
                .innerJoin(userEntity.profile, profileEntity)
                .where(userEntity.id.eq(requestProfileDTO.getUserId()))
                .fetchOne();
    }

    // Get all posts from the user who created the exchange request
    // 사용자가 작성한 교환요청 게시물 목록 조회
    public Page<ResponseGroupExchangeDTO> getBoards(RequestProfileDTO requestProfileDTO, Pageable page){
        long queryCount = queryFactory.selectFrom(groupBoardEntity)
                .where(groupBoardEntity.writer.eq(requestProfileDTO.getUserId()))
                .fetchCount();

        JPAQuery<ResponseGroupExchangeDTO> query = queryFactory
                .select(Projections.fields(ResponseGroupExchangeDTO.class,
                        groupBoardEntity.boardId,
                        groupBoardEntity.title,
                        groupBoardEntity.content,
                        writerExchangeEntity.price,
                        writerExchangeEntity.location,
                        groupBoardEntity.regTime,
                        groupBoardEntity.thumbnailPath.as("thumbnail")
                        ))
                .from(groupBoardEntity)
                .innerJoin(groupBoardEntity.writerExchangeEntity, writerExchangeEntity)
                .where(groupBoardEntity.writer.eq(requestProfileDTO.getUserId()));

        return pagingUtil.getPageImpl(page, query, queryCount, ResponseGroupExchangeDTO.class);
    }

    public void getReviews(RequestProfileDTO requestProfileDTO){

    }

}
