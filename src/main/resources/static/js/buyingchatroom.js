
var stompClient = null;
var roomId;

$(document).ready(function (){
    connect();
    loadBuyingRoomData();
})

$('#chatroom_comment_box').on('keyup', function (event){
    var textBoxMessage = $('#chatroom_comment_box').val();
    if(event.keyCode == 13)
        if (event.shiftKey){
            event.preventDefault();
        }else{
            if(textBoxMessage.length > 0){
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
    // stompClient.debug = null
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
        sender: $('.buying_login_user').val(),
        message: message,
        localDate: new Date().toLocaleString([], {'hour': '2-digit', 'minute': '2-digit'})
    }

    stompClient.send('/app/send/chat/buying/' + globalThis.roomId, {}, JSON.stringify(data));
}

function showMessage(message){
    console.log(message)
    $('.buyingchatroom__chat__list').append(
        "<div class='message_box'>" +
        "<div class='message_sender'>" + message.sender + "</div>" +
        "<div class='message_content'>" + message.message + "</div>" +
        "<div class='message_date'>" + message.localDate + "</div>" +
        "</div>"
    );
}

function exitRoom(){

    var data = { roomId: globalThis.roomId, username: $('.buying_login_user').val()};

    $.ajax({
        url: '/api/buying/exit',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function(response){

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