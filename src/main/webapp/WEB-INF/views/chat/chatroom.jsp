<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<input type="hidden" class="data__roomId" data-chatroom = "${roomid}">

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
<script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
<script src="/webjars/sockjs-client/sockjs.min.js"></script>
<script src="/webjars/stomp-websocket/stomp.min.js"></script>
<script src="/js/app.js"></script>
<script>

var flag = false;
var page = 0;
var previousDate = null;
var displayDateTrigger = false;
var lastPage = false;
var itemDate

$(document).ready(function(){
    loadChatData();
})

$('.chat__log').scroll(function(){
    if($('.chat__log').scrollTop() < 1){
        if(!flag){
            flag = true;
            loadChatData();
        }
    }
});

function loadChatData(){
    
    var display = 50;
    var roomId = $('.data__roomId')[0].dataset.chatroom;

    $.ajax({
        url: '/api/chat/chats/',
        type: 'GET',
        data: {roomId: roomId, display: display, page: page},
        dataType: 'json',
        success: function(response){
            let currentScrollTop = $('.chat__log')[0].scrollHeight;

            $.each(response.content, function(key, value){
                
                if(response.last == true){
                    lastPage = true;
                    displayDateTrigger = true;
                }

                itemDate = new Date(value.createTime).toLocaleDateString();
                var messageTime = new Date(value.createTime).toLocaleTimeString([], {'hour': '2-digit', 'minute' : '2-digit'})

                if(previousDate == null)
                    previousDate = itemDate

                if(previousDate != itemDate){
                    displayDateTrigger = true;
                    displayDate(previousDate);
                    previousDate = itemDate
                }

                if($('.room__targetId').text() != value.userVo.username)
                // 로그인한 사용자가 보낸 채팅
                    $(".chat__list").prepend(
                        "<div class='user__send'>" + 
                        "<span class='chat__message'>" + value.message + "</span>" + 
                        "<span class='chat__time'>" + messageTime + "</span>" +
                        "</div>"
                    );
                else
                // 상대 사용자가 보낸 채팅
                    $(".chat__list").prepend(
                        "<div class='target__send'>" + 
                            "<span class='nickname'>" + value.userVo.username + "</span>" +
                            "<span class='chat__message'>" + value.message + "</span>" +
                            "<span class='chat__time'>" + messageTime + "</span>" +
                        "</div>"
                );      

            })

            if(lastPage)
                displayDate(itemDate);

            let atferScrollTop = $('.chat__log')[0].scrollHeight;

            $('.chat__log')[0].scrollTop = atferScrollTop - currentScrollTop;

            page++;
            flag = false;
        }
    })
    
}

function displayDate(time){
    if(displayDateTrigger){
        $(".chat__list").prepend(
            "<span class='date__box'>" +
            (new Date(time).getMonth() + 1) + '월 ' +
            new Date(time).getDate() + '일' +
            "</span>"
        )
        displayDateTrigger = false;
    }
}

</script>
<link rel="stylesheet" type="text/css" href="/css/chatroom.css">
