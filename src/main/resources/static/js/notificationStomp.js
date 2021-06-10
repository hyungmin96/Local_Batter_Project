var stompClient = null;

$(document).ready(function(){
    connect();
})

window.closonbeforeunload = function(){
    stompClient.disconnect();
}

function connect(){
    var loginAccountInfo = $('.user__name')[0].innerHTML;
    if(loginAccountInfo != null && loginAccountInfo != ''){
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, function(){
            stompClient.subscribe('/notification', function(message){
                var content = JSON.stringify(message);
                console.log(content);
            })
        })
    }
}
