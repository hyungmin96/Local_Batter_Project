<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>

<div class="container">

    <input type="hidden" class="userId" value="${userId}">

    <div style="margin: 105px 0 45px 0; height: 100%; background: rgb(255, 255, 255)">
        <div class="userProfileInfoContainer" style="box-shadow: 1px 2px 5px 2px rgba(0, 0, 0, 0.05); width: 100%; display: inline-flex; padding: 15px;">
            <div class="userInfoContainer" style="margin: auto; justify-content: center; text-align: center; width: 40%; height: 100%;">
                <div class="userProfileBox">
                    <img class="userProfileImage" src="/upload/default_profile_img.png">
                </div>
                <div class="usernameInfoBox">
                    사용자 이름
                </div>
                <div class="userMannerScoreBox">
                    <img class="score1" src="/images/nonestar_15px.png">
                    <img class="score2" src="/images/nonestar_15px.png">
                    <img class="score3" src="/images/nonestar_15px.png">
                    <img class="score4" src="/images/nonestar_15px.png">
                    <img class="score5" src="/images/nonestar_15px.png">
                </div>
                <div class="mannerScoreTextBox" style="font-size: 13px; margin-top: 5px">
                    <span class="currentMannerScore">0점</span> / 5점
                </div>
            </div>

            <div class="userExchangeInfoBox" style="border-left: 1px solid #eaeaea; padding:25px; width: 100%; height: 310px;">
                <div style="display: inline-flex; justify-content: space-between; width: 100%;">
                    <div class="usernameBox" style="font-size: 17px; font-weight: 600">
                        사용자 이름
                    </div>
                    <div>
                        <button class="profileEditButton"><img src="/images/settings_21px.png"></button>
                    </div>
                </div>

                <div class="userRecentActivities" style="padding: 10px; margin: 20px 0 20px 0; border-top: 1px solid rgb(234 233 233); border-bottom: 1px solid rgb(234 233 233);">
                    <span class="badge badge-primary">최근거래</span>
                    <span class="badge badge-secondary">상품 수</span>
                    <span class="badge badge-success">리뷰 수</span>
                    <span class="badge badge-danger">신고</span>
                </div>

                <div class="userIntroduceBox">
                    사용자 소개
                </div>
            </div>
        </div>
    </div>

    <div style="box-shadow: 1px 2px 5px 2px rgba(0, 0, 0, 0.05);">
        <div class="tabPageContainer" style="text-align: center; background: white; margin: 40px 0 0 0; display: flex; flex-direction: row;">
            <div style="width: 100%;"><button class="exchangePageButton" style="border-top: 1px solid rgb(78, 78, 78); border-left: 1px solid rgb(78, 78, 78); background: rgb(251, 252, 253); border-right: 1px solid rgb(78, 78, 78);">등록한 물품</button></div>
            <div style="width: 100%;"><button class="reviewPageButton" style="color: #bfbfbf; border-bottom: 1px solid rgb(78, 78, 78);">리뷰</button></div>
        </div>

        <div class="viewTabPageContainer" style="display: flex; flex-wrap: wrap; padding: 15px; width:100%; min-height: 345px; height: 100%; background: white; margin-bottom: 40px;">

        </div>
        <div class="viewReviewContainer" style="display: none; padding: 15px; width:100%; min-height: 345px; height: 100%; background: white; margin-bottom: 40px;">

        </div>
        <div class="emptyViewContainer" style="display: flex; width: 100%; height: 300px;">
            <div style="width: 100%; margin: auto auto auto auto; text-align: center;">등록된 내용이 없습니다.</div>
        </div>
    </div>

    <!-- profile edit modal -->
    <div class="modal fade show" id="editProfileModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-modal="true" role="dialog" style="display: none;">
        <div class="modal-dialog" style="margin: 100px auto 20px auto; width: 600px; left: -4%">
            <div class="modal-content" style="width: 600px;">
                <div class="modal-header" style="border: none;">
                    <div class="modal-title" id="writerBoardModalTitle" style="margin: 0 0 0 auto"></div>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body" style="padding: 0.5rem 0.5rem 0 0.5rem; height: 400px">
                    <div class="boardModal" style="padding: 0.5rem; text-align: center;">

                        <input type="file" class="uploadImageFile" style="display: none"/>

                        <div class="editProfile">
                            <div>
                                <img style="border: 1px solid #e3dfdf; cursor: pointer; width: 120px; height: 120px; object-fit:cover; border-radius: 50%" class="previousProfileImage" src="/images/default_profile_img.png">
                            </div>
                            <div>
                                <input style="width: 70%;" type="text" class="editValueInput editUsername" placeholder="닉네임">
                            </div>
                            <div>
                                <textarea style="width: 70%;" type="text" rows="5" class="editValueInput editIntroduce" placeholder="자기소개"></textarea>
                            </div>
                            <div>
                                <button class="btn btn-primary editConfirmButton">수정</button>
                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<link rel="stylesheet" href="/css/profile.css">

<script>
    $(document).ready(() =>{
        getUserProfileDataAjax()
        getProfileBoardsDataAjax()
    })
</script>

<script>

    profileImageArray = []

    $(document).on('click', '.previousProfileImage', ()=>{
        $('.uploadImageFile').click()
    })

    $(document).on('click', '.profileEditButton', () => {
        $('#editProfileModal').modal('show')
    })

    $(document).on('click', '.editConfirmButton', () => {
        editProfileUserAjax()
    })

    $(function (){
        $('.uploadImageFile').change(function (e){
            profileImagePreview(e)
        })
    })

    function profileImagePreview(e){

        const files = e.target.files;
        const filesArr = Array.prototype.slice.call(files);

        filesArr.forEach(function(f){
            if(!f.type.match("image.*")){
                alert("이미지파일만 업로드가 가능합니다.");
                return;
            }
            profileImageArray.push(f);
            const reader = new FileReader();
            reader.onload = function(e){
                $('.previousProfileImage')[0].src = e.target.result
            }
            reader.readAsDataURL(f);
        });
    }

    function editProfileUserAjax(){

        var formData = new FormData()

        formData.append('userId', $('.g_user_id').val())
        formData.append('profileImage', profileImageArray[0])
        formData.append('username', $('.editUsername').val())
        formData.append('introduce', $('.editIntroduce').val())

        $.ajax({
            url: '/api/profile/edit/user',
            type: 'POST',
            contentType: false,
            processData: false,
            data: formData,
            success: (response) => {
                console.log(response)
                alert('수정하였습니다')
            }
        })
    }


    function getUserProfileDataAjax() {

        // profile user 정보 조회
        const data = {
            userId: $('.userId').val(),
        }

        $.ajax({
            url: '/api/profile/info/user',
            type: 'GET',
            data: data,
            success: (response) => {
                console.log(response)
                $('.usernameInfoBox')[0].innerHTML = response.nickname
                $('.currentMannerScore')[0].innerHTML = response.mannerScore
                $('.usernameBox')[0].innerHTML = response.nickname
                $('.userIntroduceBox')[0].innerHTML = response.introduce

                var score = Number(response.mannerScore)
                var scoreIndex = 1
                while(score > 0){
                    if(score >= 1.0){
                        $('.score' + scoreIndex)[0].src = '/images/star_15px.png'
                        score -= 1.0
                    }
                    else if(score > 0.01 || score <= 0.5){
                        $('.score' + scoreIndex)[0].src = '/images/star_half_empty_15px.png'
                        score -= 0.5
                    }
                    else
                        $('.score' + scoreIndex)[0].src = '/images/nonestar_15px.png'
                    scoreIndex ++
                }
            }
        })
    }

    var reviewPage = 0
    function getProfileReviewDataAjax(){

        const data = {
            userId: $('.userId').val(),
            display: 30,
            page: reviewPage
        }

        $.ajax({

            url: '/api/profile/list/get_user_review',
            type: 'GET',
            data: data,
            success: (response) => {
                console.log(response)
                $('.viewTabPageContainer')[0].style.display = 'none'
                $('.viewReviewContainer')[0].style.display = 'block'
                $('.badge-success')[0].innerHTML = '리뷰 수 : ' + response.length + '개'
                var html = ''

                $.each(response, (key, value) => {
                    html += '<div class="reviewWrapper" style="height: 100%; width: 100%; display: flex; flex-direction: row; padding: 10px;">'
                    html += '<div class="reviewWriterProfile">'
                    html += '<img class="reviewWriterProfileImage" src="/upload/' + value.reviewWriterProfile + '" style="width: 45px; height: 45px; object-fit: cover;">'
                    html += '</div>'
                    html += '<div class="reviewInfoBox" style="width: 100%; padding: 0 10px 10px 15px">'
                    html += '<div style="display: inline-flex">'
                    html += '<div class="reviewWriterUsername">' + value.reviewWriterUsername + '</div>'
                    html += '<div class="reviewWriterScore" style="margin: auto 0 auto 10px">'
                    for(let i = 1; i <= value.score; i ++)
                        html += '<img src="/images/star_15px.png">'
                    html += '</div>'
                    html += '</div>'
                    html += '<div class="reviewContent">' + value.content + '</div>'
                    html += '</div>'
                    html += '</div>'
                    html += '</div>'
                })
                $('.viewReviewContainer').append(html)
            }
        })
    }

    var profileBoardPage = 0
    function getProfileBoardsDataAjax(){

        // profile url 의 게시글 목록을 조회
        const data = {
            userId: $('.userId').val(),
            display: 10,
            page: profileBoardPage
        }

        $.ajax({
            url: '/api/profile/list/get_user_exchange',
            type: 'GET',
            data: data,
            success: (response) => {
                $('.badge-secondary')[0].innerHTML = '상품 수 : ' + response.numberOfElements + '개'
                var html = ''
                $('.viewTabPageContainer')[0].style.display = 'flex'
                $('.viewReviewContainer')[0].style.display = 'none'

                if(response.numberOfElements > 0){
                    $('.emptyViewContainer')[0].style.display = 'none'
                    $.each(response.content, (key, value) =>{
                        html += '<div class="img_box fadein" style="border: 1px solid rgb(238, 238, 238); cursor: pointer; width: 196px; height: 310px; margin: 10px 6px 10px 6px;">';
                        html += '<div onclick="goToUrl(' + value.boardId + ')" class="today-item-box">';
                        html += '<div class="today-box">';
                        html += '<img style="width: 194px; height: 196px;" src="/upload/' + value.thumbnail + '" onerror="this.style.display=none">';
                        html += '</div>';
                        html += '<div class="today-detail-box" style="border-top: 1px solid rgb(238, 238, 238)">';
                        html += '<div style="padding: 10px;" ss="type">';
                        html += '<div class="title">' + value.title + '</div>';
                        html += '<div class="board-line"></div>';
                        html += '<div class="line">';
                        html += '<div class="price">' + convert(value.price) + '<span class="k-money" style="font-size: 12px;"> 원</span></div>';
                        html += '</div>';
                        html += '</div>';
                        html += '<div style="border-top: 1px solid rgb(238, 238, 238); padding: 5px;">';
                        html += '<div style="border-radius: 0; font-size: 11px;" class="badge bg-secondary">' + value.location + '</div>';
                        html += '</div>';
                        html += '</div>';
                        html += '</div>';
                        html += '</div>';
                    })
                    $('.viewTabPageContainer').append(html)
                }
            }
        })
    }

    function convert(num){
        var len, point, str;
        num = num + "";
        point = num.length % 3 ;
        len = num.length;
        str = num.substring(0, point);
        while (point < len) {
            if (str != "") str += ",";
            str += num.substring(point, point + 3);
            point += 3;
        }
        return str;
    }

    function goToUrl(boardId){
        location.href = 'http://localhost:8000/request/exchange/' + boardId;
    }

</script>

<script>
    $('.exchangePageButton').click(() =>{
        $('.viewTabPageContainer').empty()
        $('.viewReviewContainer').empty()
        $('.reviewPageButton')[0].style.background = 'white'
        $('.reviewPageButton')[0].style.color = '#bfbfbf'
        $('.reviewPageButton')[0].style.fontsize = '15'
        $('.exchangePageButton')[0].style.color = 'black'
        $('.exchangePageButton')[0].style.background = 'rgb(251, 252, 253)'
        $('.reviewPageButton')[0].style.border = 'none'
        $('.exchangePageButton')[0].style.border = 'none'
        $('.exchangePageButton')[0].style.borderTop = '1px solid #4e4e4e'
        $('.exchangePageButton')[0].style.borderLeft = '1px solid #4e4e4e'
        $('.exchangePageButton')[0].style.borderRight = '1px solid #4e4e4e'
        $('.reviewPageButton')[0].style.borderBottom = '1px solid #4e4e4e'
        getProfileBoardsDataAjax()
    })

    $('.reviewPageButton').click(() =>{
        $('.viewTabPageContainer').empty()
        $('.viewReviewContainer').empty()
        $('.exchangePageButton')[0].style.background = 'white'
        $('.exchangePageButton')[0].style.color = '#bfbfbf'
        $('.exchangePageButton')[0].style.fontsize = '15'
        $('.reviewPageButton')[0].style.color = 'black'
        $('.reviewPageButton')[0].style.background = 'rgb(251, 252, 253)'
        $('.reviewPageButton')[0].style.border = 'none'
        $('.exchangePageButton')[0].style.border = 'none'
        $('.reviewPageButton')[0].style.borderTop = '1px solid #4e4e4e'
        $('.reviewPageButton')[0].style.borderLeft = '1px solid #4e4e4e'
        $('.reviewPageButton')[0].style.borderRight = '1px solid #4e4e4e'
        $('.reviewPageButton')[0].style.borderRight = '1px solid #4e4e4e'
        $('.exchangePageButton')[0].style.borderBottom = '1px solid #4e4e4e'
        getProfileReviewDataAjax()
    })
</script>
<%@ include file="../common/footer.jsp" %>


