var notificationStomp = null;

$(document).ready(function(){
    notificationConnect();
})
    
window.closonbeforeunload = function(){
    notificationStomp.disconnect();
}

function notificationConnect(){
    loginAccountInfo = $('.user__name')[0];
    if(document.getElementsByClassName('user__name').length != 0 && loginAccountInfo.innerHTML != null && loginAccountInfo.innerHTML != ''){
        var socket = new SockJS('/ws');
        notificationStomp = Stomp.over(socket);
        notificationStomp.debug = null;
        notificationStomp.connect({}, function(){
            notificationStomp.subscribe('/notification/' + loginAccountInfo.innerHTML, function(message){
                showNotification(message.body);
            })
        })
    }
}

function showNotification(message){
    if(message.includes('notificationType":"chat'))
        $('.chat')[0].innerHTML = parseInt(parseInt($('.chat')[0].innerHTML) + 1);
    else if(message.includes('notificationType":"transaction'))
        $('.trans')[0].innerHTML = parseInt(parseInt($('.trans')[0].innerHTML) + 1);
    else if(message.includes('notificationType":"notification'))
        $('.noti')[0].innerHTML = parseInt(parseInt($('.noti')[0].innerHTML) + 1);
}