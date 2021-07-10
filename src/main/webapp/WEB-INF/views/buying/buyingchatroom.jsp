<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div class="title__container">
        <div class="chatroom__title">ChatRoom Title</div>
        <div class="menu__option">
                <img src="/images/menu24.png" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">
        </div>
</div>

<div class="chatroom__chat__list">

</div>

<div class="chatroom__file__container">
        <div>이미지 첨부</div>
        <div>프로필 전송</div>
        <div>계좌 전송</div>
</div>

<div>
        <textarea class="commentWrite _use_keyup_event" id="chatroom_comment_box" placeholder="보낼 메시지를 입력하세요."></textarea>
</div>

<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
        <div>
                <div class="offcanvas-header">
                        <div>
                                <h5 id="offcanvasRightLabel">대화멤버 목록</h5>
                        </div>
                </div>
                <div class="search_box">
                        <input type="text" id="user_name" placeholder="사용자 이름">
                </div>
       </div>

        <div class="offcanvas-body">

        </div>
</div>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<script src="/js/buyingchatroom.js"></script>
<link rel="stylesheet" href="/css/buyingchatroom.css">