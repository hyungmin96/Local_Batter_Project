var stompClient = null;

$(document).ready(function(){
    connect();
})
    
window.closonbeforeunload = function(){
    stompClient.disconnect();
}

function connect(){
    loginAccountInfo = $('.user__name')[0];
    if(document.getElementsByClassName('user__name').length != 0 && loginAccountInfo.innerHTML != null && loginAccountInfo.innerHTML != ''){
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        // stompClient.debug = null;
        stompClient.connect({}, function(){
            stompClient.subscribe('/notification/' + loginAccountInfo.innerHTML, function(message){
                var content = JSON.stringify(message);
                showNotification(content);
            })
        })
    }
}

// 최초 연결수립시 db에 알림정보를 가져옴
function connectionMessage(user, message){
    stompClient.send('/app/send/connection/' + user, {}, message);
}

function showNotification(message){
    $('.remote__container').append(message);
}