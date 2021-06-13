
var flag = false;
var page = 0;
var previousDate = null;
var displayDateTrigger = false;
var lastPage = false;
var itemDate
var roomId;

$('.chat__log').scroll(function(){
    if($('.chat__log').scrollTop() < 1){
        if(!flag){
            flag = true;
            loadChatData(globalThis.roomId);
        }
    }
});

function loadChatData(roomId){
    
    var display = 30;

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

                if($('.user__name').text() != value.userVo.username)
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