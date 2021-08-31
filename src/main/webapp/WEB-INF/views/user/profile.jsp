<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>

<div class="container">

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
                <div class="usernameBox" style="font-size: 17px; font-weight: 600">
                    사용자 이름
                </div>

                <div class="userRecentActivities" style="padding: 10px; margin: 20px 0 20px 0; border-top: 1px solid rgb(250, 250, 250); border-bottom: 1px solid rgb(250, 250, 250);">
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
            <div class="emptyViewContainer" style="display: flex; width: 100%; height: 300px;">
                <div style="width: 100%; margin: auto auto auto auto; text-align: center;">등록된 내용이 없습니다.</div>
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

    function getUserProfileDataAjax() {

        // profile user 정보 조회
        const data = {
            userId: $('.g_user_id').val(),
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

    var profileBoardPage = 0
    function getProfileBoardsDataAjax(){

        // profile url 의 게시글 목록을 조회
        const data = {
            userId: $('.g_user_id').val(),
            display: 10,
            page: profileBoardPage
        }

        $.ajax({
            url: '/api/profile/list/get_user_exchange',
            type: 'GET',
            data: data,
            success: (response) => {
                console.log(response)
                $('.badge-secondary')[0].innerHTML = '상품 수 : ' + response.numberOfElements + '개'
                $('.badge-success')[0].innerHTML = '리뷰 수 : ' + response.numberOfElements + '개'
                var html = ''

                if(response.numberOfElements > 0){
                    $('.emptyViewContainer')[0].style.display = 'none'
                    $.each(response.content, (key, value) =>{
                        html += '<div class="img_box fadein" style="border: 1px solid rgb(238, 238, 238); cursor: pointer; width: 196px; height: 316px; margin: 0 12px 0 12px;">';
                        html += '<div onclick="goToUrl(' + value.boardId + ')" class="today-item-box">';
                        html += '<div class="today-box">';
                        html += '<img style="width: 189px; height: 196px;" src="/upload/' + value.thumbnail + '" onerror="this.style.display=none">';
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
                        html += '<div style="border-radius: 0" class="badge bg-secondary">' + value.location + '</div>';
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
        $('.reviewPageButton')[0].style.background = 'white'
        $('.reviewPageButton')[0].style.color = '#bfbfbf'
        $('.reviewPageButton')[0].style.fontsize = '15'
        $('.exchangePageButton')[0].style.color = 'black'
        $('.exchangePageButton')[0].style.fontsize = '17'
        $('.exchangePageButton')[0].style.background = 'rgb(251, 252, 253)'
        $('.reviewPageButton')[0].style.border = 'none'
        $('.exchangePageButton')[0].style.border = 'none'
        $('.exchangePageButton')[0].style.borderTop = '1px solid #4e4e4e'
        $('.exchangePageButton')[0].style.borderLeft = '1px solid #4e4e4e'
        $('.exchangePageButton')[0].style.borderRight = '1px solid #4e4e4e'
        $('.reviewPageButton')[0].style.borderBottom = '1px solid #4e4e4e'
    })

    $('.reviewPageButton').click(() =>{
        $('.exchangePageButton')[0].style.background = 'white'
        $('.exchangePageButton')[0].style.color = '#bfbfbf'
        $('.exchangePageButton')[0].style.fontsize = '15'
        $('.reviewPageButton')[0].style.color = 'black'
        $('.reviewPageButton')[0].style.fontsize = '17'
        $('.reviewPageButton')[0].style.background = 'rgb(251, 252, 253)'
        $('.reviewPageButton')[0].style.border = 'none'
        $('.exchangePageButton')[0].style.border = 'none'
        $('.reviewPageButton')[0].style.borderTop = '1px solid #4e4e4e'
        $('.reviewPageButton')[0].style.borderLeft = '1px solid #4e4e4e'
        $('.reviewPageButton')[0].style.borderRight = '1px solid #4e4e4e'
        $('.reviewPageButton')[0].style.borderRight = '1px solid #4e4e4e'
        $('.exchangePageButton')[0].style.borderBottom = '1px solid #4e4e4e'
    })
</script>
<%@ include file="../common/footer.jsp" %>


