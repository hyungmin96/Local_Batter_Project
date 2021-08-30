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
                    <img class="userMannerScrore" src="/images/nonestar_15px.png">
                </div>
            </div>

            <div class="userExchangeInfoBox" style="border-left: 1px solid #eaeaea; padding:25px; width: 100%; height: 310px;">
                <div class="userIntroduceBox">
                    사용자 소개
                </div>
            </div>
        </div>
    </div>

    <div class="tabPageContainer" style="background: white; margin: 40px 0 40px 0; display: flex; flex-direction: row;">
        <div><button class="exchangePageButton">등록한 물품</button></div>
        <div><button class="reviewPageButton">리뷰</button></div>
    </div>

</div>

<link rel="stylesheet" href="/css/profile.css">
<script>
</script>
<%@ include file="../common/footer.jsp" %>


