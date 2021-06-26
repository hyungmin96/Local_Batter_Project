<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp"%>

<link rel="stylesheet" type="text/css" href="/css/chatRoomList.css">

<!-- Modal -->
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true" style="margin-top: 150px;">
        <div class="modal-dialog">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">채팅방 나가기</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <div>방을 나가면 모든 채팅내역이 삭제됩니다.</div>
                <div>현재 채팅방을 나가시겠습니까?</div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                <button type="button" id="exit__btn__perform" data-bs-dismiss="modal" class="btn btn-primary">나가기</button>
            </div>
            </div>
        </div>
        </div>
<!-- Modal -->

<div class="container" style="margin-top: 125px;">
    
        <div style="width: 100%;">
            <div>
                    <span class="chatroom__list">채팅방 목록</span>

                    <div class="form-group row float-right" style="margin-right: 5px;">
                        <button type="button" id="exit__btn" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#exampleModal">나가기</button>
                    </div>
            </div>
            <hr style='width: 100%; border: none; height: 1px; background-color: rgb(185, 185, 185);'/>
        </div>

    <div class="chat__container" style="display: flex; flex-direction: row;">

        <div class="roomsContainer"></div>
        
        <div class="chat_wrapper" style="width: 100%; height: 100%; display: flex; margin-left: 15px;">
            <div class="chat__log" style="width: 100%; height: 600px;">
                <ul>
                    <div class="message__container">
                        <li class="chat__list">

                        </li>
                    </div>
                </ul>
            </div>

            <hr style="background-color: rgb(155, 155, 155); border:none; height:1px; margin: 5px;"/>
            
            <div class="send__info__message">
                <span class="profile__send">프로필 전송</span> | <span class="number__send">계좌번호 전송</span>
            </div>
            
            <div class="chat__send" style="display: flex; flex-direction: row; margin-bottom: 25px;">
                <input type="text" id="message" placeholder="내용을 입력해주세요" class="chat__content__send" value="">
                <button class="sendMessage" style="margin-left: 5px; background-color: white; border: none"><img src="/images/paper_plane_21px.png"></button>
            </div>
        </div>
    </div>

</div>

<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>
<script src="/js/app.js"></script>
<link rel="stylesheet" href="/css/chatCreateList.css">

<%@ include file="../common/footer.jsp"%>
