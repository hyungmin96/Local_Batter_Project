var stompClient = null;

$(document).ready(function(){
    connect();
})

window.closonbeforeunload = function(){
    stompClient.disconnect();
}

function connect(){
    var loginAccountInfo = $('.user__name')[0];
    if(document.getElementsByClassName('user__name').length != 0 && loginAccountInfo.innerHTML != null && loginAccountInfo.innerHTML != ''){
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        // stompClient.debug = null;
        stompClient.connect({}, function(){
            stompClient.subscribe('/notification/' + loginAccountInfo.innerHTML, function(message){
                var content = JSON.stringify(message);
                console.log(content);
                showNotification(content);
            })
        })
    }
}

function showNotification(message){
    $('.remote__container').append(message);
}