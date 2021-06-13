<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp"%>

<link rel="stylesheet" type="text/css" href="/css/chatRoomList.css">

<div class="container" style="margin-top: 165px;">

    <div class="chat__container" style="display: flex; flex-direction: row;">

        <div class="roomsContainer">
        

        </div>
        
        <div class="chat_wrapper" style="width: 100%; height: 100%; display: flex; margin-left: 15px;">
            <div class="target__text">
                <div class="data__tatgetId">
                    <span class="room__targetId">${target}</span>님과 대화
                </div>
            </div>

            <div class="chat__log" style="width: 100%; height: 600px;">
                <ul>
                    <div class="message__container">
                        <li class="chat__list">

                        </li>
                    </div>
                </ul>
            </div>

            <hr style="background-color: rgb(155, 155, 155); border:none; height:1px; margin 3px 3px;"/>
            <div class="chat__send">
                <input type="text" id="message" placeholder="내용을 입력해주세요" class="chat__content__send" value="">
            </div>
        </div>

    </div>
</div>

<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>
<script src="/js/app.js"></script>
<link rel="stylesheet" href="/css/chatCreateList.css">
<link rel="stylesheet" type="text/css" href="/css/chatroom.css">