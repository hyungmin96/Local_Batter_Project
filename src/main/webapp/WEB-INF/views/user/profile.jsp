<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>

<div class="profile__container" style="margin-top: 115px;">

    <div class="container">

    <div class="profile__container" style="display: inline-flex;">

            <div class="profile__box" style="width: 340px; height: 100%">

                <div onclick="showModal();" class="setting__profile__btn"><img src="/images/settings_20px.png" style="cursor: pointer; float: right;"></div>
                
                                
                <div style="width: 300px; height: 150px; display: flex;">
                    <img src="/upload/${profile.profilePath}" onerror="this.src='/images/default_profile_img.png'" style="margin: auto auto; width: 100px; height: 100px;">
                </div>
                
                <div style="display: flex; height: 40px;">
                    <div class="user__profile__name">${profile.nickname}</div>
                </div>

                <div style="display: flex; height: 140px; margin-bottom: 20px;">
                    <div class="user__pr">${profile.introduce}</div>
                </div>

                <hr style="border: 0; height: 1px; background: #ccc;" />

                <div class="user__info">
                    <div class="profile__item">매너점수</div>
                    <span>${profile.mannerScore}</span>
                    <div class="profile__item">최근 거래횟수</div>
                    <span>10회</span>
                    <div class="profile__item">거래 선호지역</div>
                    <span>${profile.location}</span>
                    <div class="profile__item">거래 시간</div>
                    <span>${profile.preferTime}</span>
                </div>

            </div>

            <div>
                <div class="profile__box" style="width: 950px; height: 400px">
                    <div style="display:inline-flex;">
                        <div class="category__list">거래내역</div>
                        <div class="transaction__count">0개</div>
                    </div>
                    
                    <div class="profile__exchange__list">

                    </div>

                    <div class="page__container">
                        <div class="page__box" style="display: inline-flex; max-width: 395px;">
                            <div class="previous"><img src="/images/back.png"></div>
                                <input type="hidden" class="curpage" data-value="1">
                                <input type="hidden" class="lastpage" data-value="${endPages}">
                                <div class="board__number__box">

                                </div>
                            <div class="next"><img src="/images/next.png"></div>
                        </div>
                    </div>

                </div>

                <div class="profile__box" style="width: 950px; height: 400px;">
                    <div style="display:inline-flex;">
                        <div class="category__list">거래리뷰 목록</div>
                        <div class="review__count">0개</div>
                    </div>
                    
                    <div class="comment__list">

                    </div>

                    <div class="page__container">
                        <div class="page__box" style="display: inline-flex; max-width: 395px;">
                            <div class="previous"><img src="/images/back.png"></div>
                                <input type="hidden" class="curpage" data-value="1">
                                <input type="hidden" class="lastpage" data-value="${endPages}">
                                <div class="page__number__box">

                                </div>
                            <div class="next"><img src="/images/next.png"></div>
                        </div>
                    </div>

                </div>
            </div>

        <div>

    </div>

</div>
    <!-- modal -->
        <div id="my_modal" style="height: 650px;">
            <div class = "modal_close_btn"><img src="/images/delete_35px.png" style="width: 20px; height: 20px;float: right; cursor: pointer;"></div>

        <div style="margin-bottom: 5px;">
                    프로필 설정
                </div>

                <span id="img__upload__btn" style="width: 1px;">
                    <img id="profile__img" type="file" src="/upload/${profile.profilePath}" onerror="this.src='/images/default_profile_img.png'" style="cursor: pointer; width: 100px; height: 100px;">
                </span>
                
                <input id="profile__img__upload" type="file" accept="image/jpg, image/jpeg, image/png"
                class="custom__profile__upload" name="upload_file" style="display: none;">

                <div style="margin-bottom: 5px;">
                    닉네임
                </div>
                <input type="text" class="form-control" id="username" value="${profile.nickname}" style="margin-bottom: 15px;">

                <div style="margin-bottom: 5px; ">
                    자기소개
                </div>
                <textarea id="introduce" class="form-control" style="margin-bottom: 15px;">${profile.introduce}</textarea>

                <div style="margin-bottom: 5px; ">
                    계좌번호
                </div>
                <input type="text" class="form-control" id="account" value="${profile.accountNumber}" style="margin-bottom: 15px;">

                <div style="margin-bottom: 5px;">
                    거래 선호지역
                </div>
                <input type="text" class="form-control" id="location" value="${profile.location}" style="margin-bottom: 15px;">

                <div style="margin-bottom: 5px;">
                    거래 시간
                </div>
                <input type="text" class="form-control" id="prefertime" value="${profile.preferTime}" style="margin-bottom: 15px;">

                <div class="form-group row float-right" style="position:absolute; right:30px; bottom:0px;">
                    <button type="button" id="profile__save__btn" class="btn btn-secondary">저장</button>
                </div>

        </div>
    <!-- modal -->
</div>

<script src="/js/pagination.js"></script>
<script src="/js/profile.js"></script>
<script src="/js/modal.js"></script>
<link rel="stylesheet" href="/css/profile.css">

<%@ include file="../common/footer.jsp" %>


