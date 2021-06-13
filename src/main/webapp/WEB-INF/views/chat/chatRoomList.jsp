<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp"%>



<link rel="stylesheet" type="text/css" href="/css/chatRoomList.css">

<div class="container" style="margin-top: 165px;">

    <div class="chat__container" style="display: flex; flex-direction: row;">

        <div class="roomsContainer">
        

        </div>
        
        <div class="chat_wrapper" style="width: 100%; height: 100%; display: flex;">
            <div class="target__text">
                <div class="data__tatgetId">
                    <span class="room__targetId">${target}</span>님과 대화
                </div>
            </div>

            <div class="chat__log">
                <ul>
                    <div class="message__container">
                        <li class="chat__list">

                        </li>
                    </div>
                </ul>
            </div>

            <div class="chat__send">
                <input type="text" id="message" placeholder="내용을 입력해주세요" class="chat__content__send" value="">
            </div>
        </div>

    </div>
</div>

<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>
<script src="/js/chatting.js"></script>
<script src="/js/app.js"></script>
<link rel="stylesheet" type="text/css" href="/css/chatroom.css">
<script>

    // 채팅방 목록
    $(document).ready(function(){

        $.ajax({

            url: '/api/chat/rooms',
            type: 'GET',
            dataType: 'json',
            success: function(response){
                
                $.each(response, function(key, value){
                    
                    var currentChatMessage = value.roomEntity.chats;
                    var content;

                    if(currentChatMessage.length > 0)
                        content = currentChatMessage[currentChatMessage.length-1].message;
                    else
                        content = '채팅방이 개설되었습니다.'

                    var currentChatMessageTime = new Date(currentChatMessage[currentChatMessage.length - 1].createTime).toLocaleTimeString([], {'hour': '2-digit', 'minute' : '2-digit'});

                        $('.roomsContainer').append(
                            "<div class='room__box' style='width: 350px;'>" + 
                            "<div id='" + value.roomEntity.id + "'onclick='javascript:loadChatList(this);' style='width: 100%;'>" + 
                            "<div style='display: flex; flex-direction: row;'>" +
                            "<div><img src='/images/default_profile_img.png' style='width: 60px; height: 60px;'></div>" + 
                            "<div style='margin-left: 10px; width: 100%; margin-top: 5px;'>" + 
                            "<div class='room__title'>" + 
                            "<a href='#'>" + value.target.username + "</a>" + 
                            "<span class='chat__time__text'>" + currentChatMessageTime + "</span>" + 
                            "</div>" +
                            "<div class='room_chatting'>" + 
                            content + 
                            "</div>" + 
                            "</div>" + 
                            "</div>" + 
                            "</div>" + 
                            "</div>"
                        );

                })

            }

        })

    })

function loadChatList(e){
    var roomId = e.id;
    $(".chat__list").empty();
    loadChatData(roomId);
    connect(roomId);
}


</script>
<link rel="stylesheet" href="/css/chatCreateList.css">