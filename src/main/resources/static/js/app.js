var stompClient = null;

$(document).ready(function(){
    connect();
})

window.onbeforeunload = function () {
    stompClient.disconnect();
};

$(function () {
    $( ".chat__content__send" ).keyup(function(event) {
            if (event.keyCode === 13) sendMessage();
        });
    $( "#notification" ).click(function() { sendNotification(); });
});

function showMessage(message) {

    if($('.room__targetId').text() != message.user)
        // 로그인한 사용자가 보낸 채팅
        $(".chat__list").append(
            "<div class='user__send'>" + 
            "<span class='chat__message'>" + message.message + "</span>" + 
            "<span class='chat__time'>" + message.date + "</span>" +
            "</div>"
        );
    else
        // 상대 사용자가 보낸 채팅
        $(".chat__list").append(
            "<div class='target__send'>" + 
                "<span class='nickname'>" + message.user + "</span>" +
                "<span class='chat__message'>" + message.message + "</span>" +
                "<span class='chat__time'>" + message.date + "</span>" +
            "</div>"
        );

        $('.chat__log')[0].scrollTop = $('.chat__log')[0].scrollHeight;

}

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function (){
        stompClient.subscribe('/chat/' + document.getElementsByClassName('data__roomId')[0].dataset.chatroom, function (message) {
            var value = JSON.parse(message.body);
            showMessage(value);
        });
    });
}

function sendMessage() {
    data = {
            'roomId' : document.getElementsByClassName('data__roomId')[0].dataset.chatroom, 
            'sender' : '', 
            'target' : '', 
            'message' : $("#message").val(),
            'date' : new Date().toLocaleString([], {'hour': '2-digit', 'minute': '2-digit'}) 
            };

    stompClient.send('/app/send/chat', {}, JSON.stringify(data));
    $("#message").empty();
    document.getElementById('message').value = '';
    $('.chat__log')[0].scrollTop = $('.chat__log')[0].scrollHeight;
}