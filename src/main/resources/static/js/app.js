var stompClient = null;
var roomId = null;
var flag = false;
var page = 0;
var previousDate = null;
var displayDateTrigger = false;
var lastPage = false;
var itemDate
var targetUsername;

// 새로운 채팅방이 클릭될 경우만 실행되는 메소드
function loadChatList(e){
    if(globalThis.roomId == null || globalThis.roomId != e.id){
        targetUsername = $('#targetId')[0].innerHTML;
        $(".chat__list").empty();
        clearBackColor();
        globalThis.roomId = e.id;
        globalThis.page = 0;
        globalThis.flag = true;
        connect(globalThis.roomId);
        loadChatData(globalThis.roomId);
        document.getElementById(globalThis.roomId).style.backgroundColor  = 'rgb(245, 245, 245)';
    }
}

// 클릭 이벤트
$(function () {

    $('#exit__btn__perform').click(function(){
        exitChatRoom();
    })

    $('.profile__send').click(function(){
        sendMessage('profile');
    })

    $('.chat__content__send').keyup(function(event) {
        if (event.keyCode === 13) sendMessage('message');
    });

    $('.sendMessage').click(function() {
        sendMessage('message');
    });

    $('.number__send').click(function() {
        sendMessage('account');
    });

    $( "#notification" ).click(function() { sendNotification(); });
});

function exitChatRoom(){

    $.ajax({
        url: '/api/chat/delete/room',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data: 'roomId=' + globalThis.roomId + '&userId=' + $('.user__name').text() + '&targetUsername=' + globalThis.targetUsername,
        success: function(response){
            console.log(response);
        }
    })

}

function clearBackColor(){
    if(globalThis.roomId != null)
        document.getElementById(globalThis.roomId).style.backgroundColor  = 'white';
}

window.onbeforeunload = function () {
    stompClient.disconnect();
};

$('.chat__log').scroll(function(){
    if($('.chat__log').scrollTop() < 1){
        if(!globalThis.flag){
            loadChatData();
        }
    }
});

function loadChatData(){
    
    var display = 30;

    $.ajax({
        url: '/api/chat/chats/',
        type: 'GET',
        data: {roomId: globalThis.roomId, display: display, page: globalThis.page},
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

                if($('.user__name').text() == value.userVo.username)
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

            globalThis.page++;
            globalThis.flag = false;
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

function connect() {
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function (){
        stompClient.subscribe('/chat/' + globalThis.roomId, function (message) {
            var value = JSON.parse(message.body);
            showMessage(value);
        });
    });
}

function sendMessage(type) {
    if(globalThis.roomId != null){
        
        var message;
        var pubUrl;

        if(type == 'message'){
            message = $('#message').val()
            pubUrl = '/app/send/chat';
        }else if(type == 'profile'){
            pubUrl = '/app/send/chat/profile';
        }else if(type == 'account'){
            pubUrl = '/app/send/chat/number';
        }

        data = {
                'roomId' : globalThis.roomId, 
                'sender' : $('.user__name').text(), 
                'target' : '', 
                'message' : message,
                'date' : new Date().toLocaleString([], {'hour': '2-digit', 'minute': '2-digit'}) 
                };

        stompClient.send(pubUrl, {}, JSON.stringify(data));
        $("#message").empty();
        document.getElementById('message').value = '';
        $('.chat__log')[0].scrollTop = $('.chat__log')[0].scrollHeight;
    }else{
        alert('대화할 사용자를 선택 후 다시 시도해주세요.');
    }
}

function showMessage(message) {
    if($('.user__name').text() == message.sender)
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
                "<span class='nickname'>" + message.sender + "</span>" +
                "<span class='chat__message'>" + message.message + "</span>" +
                "<span class='chat__time'>" + message.date + "</span>" +
            "</div>"
        );
        $('.chat__log')[0].scrollTop = $('.chat__log')[0].scrollHeight;
}

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
                    var currentChatMessageTime = new Date().toLocaleTimeString();

                    if(currentChatMessage.length > 0){
                        content = currentChatMessage[currentChatMessage.length-1].message;
                        currentChatMessageTime = new Date(currentChatMessage[currentChatMessage.length - 1].createTime).toLocaleTimeString([], {'hour': '2-digit', 'minute' : '2-digit'});
                    }
                    else
                        content = '채팅방이 개설되었습니다.'

                        var connectionPerform = 'disconnected';
                        if(value.userVo.username != $('.user__name').text()){
                            connectionPerform = value.userConnectionType;
                        }else{
                            connectionPerform = value.targetConnectionType;
                        }

                        if(connectionPerform == 'connected'){

                            $('.roomsContainer').append(
                                "<div class='room__box' style='width: 100%;'>" + 
                                "<div id='" + value.roomEntity.id + "'onclick='javascript:loadChatList(this);' style='width: 100%;'>" + 
                                "<div style='padding: 5px; display: flex; flex-direction: row;'>" +
                                "<div style='margin:10px 10px;'><img src='/upload/" + value.target.profile.profilePath +"' onerror=this.src='/images/default_profile_img.png' style='width: 50px; height: 50px;'></div>" + 
                                "<div style='margin-left: 10px; width: 100%; margin-top: 10px;'>" + 
                                "<div class='room__title'>" + 
                                "<span id='targetId' style='color: blue;' onclick='NewTab(" + value.target.username + ");'>" + value.target.username + "</span>" + 
                                "<span class='chat__time__text'>" + currentChatMessageTime + "</span>" + 
                                "</div>" +
                                "<div class='room_chatting'>" + 
                                content + 
                                "</div>" + 
                                "</div>" + 
                                "</div>" + 
                                "</div>" + 
                                "<hr style='margin: 0; border: none; height: 1px; background-color: rgb(185, 185, 185);'/>" +
                                "</div>"
                                );

                        }

                })

            }

        })

    })

    function NewTab(target){
        window.open('http://localhost:8000/profile/user=' + target, '_blank');
    }