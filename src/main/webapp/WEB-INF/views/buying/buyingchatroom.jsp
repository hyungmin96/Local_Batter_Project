<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type="hidden" class="buying_roomId" value="${roomId}">
<input type="hidden" class="buying_login_user" value="${username}">
<input type="hidden" class="login_user_profile" value="${userProfile}">

        <div class="title__container">
                <div class="chatroom__title">ChatRoom Title</div>
                <div class="menu__option">
                        <img src="/images/menu24.png" type="button" data-bs-toggle="offcanvas" data-bs-target="#offcanvasRight" aria-controls="offcanvasRight">
                </div>
        </div>
        <div class="buyingchatroom__chat__list" style="height: 469px;">
</div>

<div class="chatroom__file__container">
        <div id="img_upload" style="margin: auto 0; cursor: pointer;">
                <div><img src="/images/image_gallery_23px.png" style="margin-right: 5px; height: 23px; width: 23px;">이미지 첨부</div>
        </div>
        <input id="upload_dialog" type="file" accept="image/jpg, image/jpeg, image/png" class="custom-file-input" name="upload_file" multiple style="display: none;">
</div>

<div>
        <textarea cols="20" rows="2" class="commentWrite _use_keyup_event" id="chatroom_comment_box" placeholder="보낼 메시지를 입력하세요."></textarea>
</div>

<div class="offcanvas offcanvas-end" tabindex="-1" id="offcanvasRight" aria-labelledby="offcanvasRightLabel">
        <div>
                <div class="search_box">
                        <div>
                                <h5 id="offcanvasRightLabel">대화멤버 목록</h5>
                        </div>
                        <div>
                                <input type="text" id="user_name" placeholder="사용자 이름">
                                <img class="search_img" src="/images/search_26px.png">
                        </div>
                </div>
       </div>

        <div class="offcanvas-body" style="padding: 5px; background-color: #eaeaea">

        </div>

        <div class="room_btn_container">
                <button class="room_quit_btn"><img src="/images/exit_32px.png">대화방 나가기</button>
        </div>
</div>
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link rel="stylesheet" href="/css/buyingchatroom.css">
<script src="/js/buyingchatroom.js"></script>
