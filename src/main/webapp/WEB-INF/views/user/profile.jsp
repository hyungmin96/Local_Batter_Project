<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>

<div class="profile__container" style="margin-top: 115px;">

    <div class="container">

    <div class="profile__container" style="display: inline-flex;">

            <div class="profile__box" style="width: 340px; height: 100%">

                <div class="setting__profile__btn"><img src="/images/settings_20px.png" style="cursor: pointer; float: right;"></div>
                
                                
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
                    <span>4.5</span>
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
                    <div class="category__list">거래내역</div>
                    <hr />
                    <div class="profile__exchange__list">
                    </div>
                </div>

                <div class="profile__box" style="width: 950px; height: 400px">
                    <div class="category__list">거래후기 목록</div>
                    <hr />
                    <div class="profile__exchange__list">
                    </div>
                </div>
            </div>

        <div>

    </div>

</div>

    <!-- The Modal -->
        <div id="myModal" class="modal">
    
            <div class="modal-content" style="width: 500px; height: 720px; margin: 200px 780px; padding: 15px; border: none; box-shadow: 1px 1px 10px 0px rgba(0, 0, 0, 0.12)">
                <div><img src="/images/delete_35px.png" onClick="close_pop();" style="float: right; cursor: pointer;"></div>

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
        </div>
    <!--End Modal-->

<script>

    var profileFile = profile__img;

    $(function(){
        $('#profile__img__upload').on("change", function(e){
            // 프로필 사진 미리보기
            var reader = new FileReader();
            reader.onload = function(f){
                profile__img.src = f.target.result;
                profileFile = e.target.files[0];
            }
            reader.readAsDataURL(e.target.files[0]);
        })
    })

    $('#img__upload__btn').on("click", function() {
        $('#profile__img__upload').click();
    });

    $('.setting__profile__btn').on("click", function() {
        $('#myModal').show();
            console.log(document.getElementById('profile__img'))
    });

    $('#profile__save__btn').on("click", function(){
        profileSave();
    })

    function close_pop(flag) {
        $('#myModal').hide();
    };

    function profileSave(){
        
        const profileData = new FormData();


        profileData.append('nickname', $('#username').val());
        profileData.append('introduce', $('#introduce').val());
        profileData.append('profileImg', profileFile);
        profileData.append('location', $('#location').val());
        profileData.append('preferTime', $('#prefertime').val());
        profileData.append('accountNumber', $('#account').val());

        $.ajax({

            url: '/api/profile/save',
            type: 'POST',
            data: profileData,
            dataType: 'text',
            contentType: false,
            processData: false,
            success: function(response){
                if(response != null){
                    alert('성공적으로 수정하였습니다.');
                    location.reload();
                }else{
                    alert('비밀번호가 잘못되었습니다.');
                }
            }

        })

    }

</script>

<style>
@font-face {
    font-family: 'ChosunBg';
    src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@1.0/ChosunBg.woff') format('woff');
    font-weight: normal;
    font-style: normal;
}

.profile__box{
    box-shadow: 0px 0px 10px 1px rgba(0, 0, 0, 0.12);
    border-radius: 5px;
    padding: 20px;
    margin: 30px 20px;
}

.profile__item{
    font-size: 13px;
    color: rgb(165, 165, 165);
    margin: 5px 5px;
}

.profile__box .category__list{
    font-family: ChosunBg;
    font-size: 16px;
}

.user__pr{
    max-width: 200px; 
    height: :30px;
    font-family: Noto Sans KR;
    font-size: 14px;
    color: rgb(187, 187, 187);
    margin: auto auto;
    text-align: center;
    word-break: break-all;
    word-wrap: break-word;
    overflow: hidden;
}

.user__profile__name{
    font-family: ChosunBg;
    font-size: 22px;
    color: black;
    margin: auto auto;
}

.user__info span{
    font-family: ChosunBg;
    font-size: 14px;
    color: black;
    margin: 15px;
}

</style>

