
var stompClient = null;
var roomId;

$(document).ready(function (){
    connect();
    loadBuyingRoomData();
    loadBuyingRoomChatData();
})

$('#chatroom_comment_box').on('keyup', function (event){
    var textBoxMessage = $('#chatroom_comment_box').val();
    if(event.keyCode == 13)
        if (event.shiftKey){
            event.preventDefault();
        }else{
            if(textBoxMessage.length > 1){
                // 보낼 메세지의 마지막에 \n 값을 빼고 전송
                sendMessage(textBoxMessage.substring(0, textBoxMessage.lastIndexOf('\n')));
                document.getElementById('chatroom_comment_box').value = '';
            }else alert('메세지 내용을 입력해주세요.')
        }
})

$(function (){
    $('.room_quit_btn').click(function(){
        exitRoom();
    })
})

function connect(){
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = null
    stompClient.connect({}, function(){
        stompClient.subscribe('/chat/buying/' + globalThis.roomId, function(message){
            var value = JSON.parse(message.body);
            showMessage(value);
        })
    })
}

function sendMessage(message){
    var data = {
        roomId: globalThis.roomId,
        type: 'chat',
        sender: $('.buying_login_user').val(),
        message: message,
        profilePath: '/upload/' + $('.login_user_profile').val(),
        localDate: new Date().toISOString()
    }
    stompClient.send('/app/send/chat/buying/' + globalThis.roomId, {}, JSON.stringify(data));
    $('.buyingchatroom__chat__list')[0].scrollTop = $('.buyingchatroom__chat__list')[0].scrollHeight;
}

var preDate = null;
function showDate(message){

    if(globalThis.preDate == null || globalThis.preDate != new Date(message.localDate).toLocaleDateString()){
        $('.buyingchatroom__chat__list').append(
            "<div class='message_box_event'>" +
            "<div class='message_noti'>" + new Date(message.localDate).toLocaleDateString() + "</div>" +
            "</div>"
        );
        globalThis.preDate = new Date(message.localDate).toLocaleDateString();
    }
}

function showGreeting(message){
    $('.buyingchatroom__chat__list').append(
        "<div class='message_box_event'>" +
            "<div class='message_noti'>" + message.sender + "님이 대화방에 참여하였습니다.</div>" +
        "</div>"
    );
}

function showExit(message){
    $('.buyingchatroom__chat__list').append(
        "<div class='message_box_event'>" +
            "<div class='message_noti'>" + message.sender + "님이 나갔습니다.</div>" +
        "</div>"
    );
}

function showContent(message){

    var chatTime = new Date(message.localDate).toLocaleTimeString([], {'hour': '2-digit', 'minute': '2-digit'});

    var myMessage = "<div class='message_box'>" +
                        "<div class='me_message_box'>" +
                        "<div class='message_sender'>" + message.sender + "</div>" +
                        "<div class='me_message_content'>" + message.message + "</div>" +
                        "<div class='message_date'>" + chatTime + "</div>" +
                        "</div>" +
                        "</div>"

    var targetMessage = "<div class='message_box'>" +
                        "<div class='target_message_box'>" +
                        "<div><img class='target_profile_img' src=/upload/" + $('.login_user_profile').val() + "></div>" +
                        "<div style='margin-left: 10px;'>" +
                        "<div class='message_sender'>" + message.sender + "</div>" +
                        "<div style='display: inline-flex'>" +
                        "<div class='message_content'>" + message.message + "</div>" +
                        "<div class='message_date' style='margin-top: 15px; margin-left: 10px;'>" + chatTime + "</div>" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "</div>"

    if($('.buying_login_user').val() == message.sender)
            $('.buyingchatroom__chat__list').append(myMessage);
        else
            $('.buyingchatroom__chat__list').append(targetMessage);
}

function showMessage(message){

    showDate(message);

    if(message.type == 'greeting')
        showGreeting(message);
    else if(message.type == 'exit')
        showExit(message);
    else
        showContent(message)

    $('.buyingchatroom__chat__list')[0].scrollTop = $('.buyingchatroom__chat__list')[0].scrollHeight;
}

function loadBuyingRoomChatData(){

    var data = { roomId: globalThis.roomId };

    $.ajax({
        url: '/api/buying/chats',
        type: 'GET',
        data: data,
        success: function (response) {
            console.log(response)
            $.each(response, function(key, message) {
                showMessage(message);
            })
        }
    })
}

function loadBuyingRoomData(){
    globalThis.roomId = $('.buying_roomId').val();

    var data = {roomId: globalThis.roomId}
    $.ajax({
        url: '/api/buying/getRoomInfo',
        type: 'GET',
        data: data,
        success: function(response){

            document.getElementsByClassName('chatroom__title')[0].innerHTML = response.roomTitle + ' 채팅방';
            document.getElementById('offcanvasRightLabel').innerHTML = '대화멤버 목록[' + response.users.length + '/' + response.limitUsers + ']';

            $.each(response.users, function(value){
                $('.offcanvas-body').append(
                    "<div class='room_user_box'>" +
                    "<img class='room_user_profile' src=/upload/" + response.users[value].user.profile.profilePath + ">" +
                    "<div class='room_user_name'>" + response.users[value].user.username + "</div>" +
                    "<div>"
                );
            })
        }
    })
}

function exitRoom(){

    var data = { roomId: globalThis.roomId, username: $('.buying_login_user').val()};

    $.ajax({
        url: '/api/buying/exit',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function(response){
            if(response.includes('Success')){
                alert('대화방을 나갔습니다.');
                window.close();
                location.href='http://localhost:8000/product/buying';
            }
        }
    })
}