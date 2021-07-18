
var stompClient = null;
var roomId;

$(document).ready(function (){
    connect();
    loadGroupRoomData();
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

    $('#img_upload').click(function(){
        $('#upload_dialog').click();
    })

    $('#upload_dialog').change(function(e){
        GroupRoomImageUpload(e);
    })

})


function GroupRoomImageUpload(e){

    var files = e.target.files;
    var fileArray = Array.prototype.slice.call(files);
    var formData = new FormData();

    formData.append('roomId', globalThis.roomId);
    formData.append('userId', $('.login_user_id').val());
    formData.append('profilePath', 'profileId');
    formData.append('sender', $('.Group_login_user').val());
    formData.append('message', null);
    formData.append('type', 'image');
    formData.append('localDate', new Date().toISOString());

    fileArray.forEach(function(value){
        formData.append('img', value);
    })

    $.ajax({
        url: '/api/group/upload/',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false
    })
}

function connect(){
    var socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = null
    stompClient.connect({}, function(){
        stompClient.subscribe('/chat/Group/' + globalThis.roomId, function(message){
            var value = JSON.parse(message.body);
            showMessage(value);
        })
    })
}

function sendMessage(message, type = 'chat'){

    var data = {
        userId: $('.login_user_id').val(),
        roomId: globalThis.roomId,
        type: type,
        sender: $('.Group_login_user').val(),
        message: message,
        profilePath: '/upload/' + globalThis.profilePath,
        localDate: new Date().toISOString()
    }
    stompClient.send('/app/send/chat/Group/' + globalThis.roomId, {}, JSON.stringify(data));
    $('.Groupchatroom__chat__list')[0].scrollTop = $('.Groupchatroom__chat__list')[0].scrollHeight;
}

var preDate = null;
function showDate(message){

    if(globalThis.preDate == null || globalThis.preDate != new Date(message.localDate).toLocaleDateString()){
        $('.Groupchatroom__chat__list').append(
            "<div class='message_box_event'>" +
            "<div class='message_noti'>" + new Date(message.localDate).toLocaleDateString() + "</div>" +
            "</div>"
        );
        globalThis.preDate = new Date(message.localDate).toLocaleDateString();
    }
}

function showGreeting(message){
    $('.Groupchatroom__chat__list').append(
        "<div class='message_box_event'>" +
            "<div class='message_noti'>" + message.sender + "님이 대화방에 참여하였습니다.</div>" +
        "</div>"
    );
}

function showExit(message){
    $('.Groupchatroom__chat__list').append(
        "<div class='message_box_event'>" +
            "<div class='message_noti'>" + message.sender + "님이 나갔습니다.</div>" +
        "</div>"
    );
}

GroupChatRoomUserArray = new Array();
function showContent(message){

    var content;

    var profileId = (function() {
        for (var key in GroupChatRoomUserArray)
            if (GroupChatRoomUserArray[key].userObject.user.id == message.userId)
                return GroupChatRoomUserArray[key].userObject.user.profile.profilePath;
    }())

    if(message.type == 'image'){
        content = "<img style='width: 260px; height: 200px; object-fit: cover;' src=/upload/" + message.message + ">";
    }else{
        content = message.message;
    }

    var chatTime = new Date(message.localDate).toLocaleTimeString([], {'hour': '2-digit', 'minute': '2-digit'});

    var myMessage = "<div class='message_box'>" +
                        "<div class='me_message_box'>" +
                        "<div class='me_message_content'>" + content + "</div>" +
                        "<div class='aside'>" +
                        "<span id = message_" + message.id + " onclick='chatButton(this);'><img class='message_menu' src='/images/menu_14px.png'></span>" +
                        "<span class='message_date'>" + chatTime + "</span>" +
                        "</div>" +
                        "</div>" +
                        "</div>"

    var targetMessage = "<div class='message_box'>" +
                        "<div class='target_message_box'>" +
                        "<div><img class='target_profile_img' src=/upload/" + profileId + "></div>" +
                        "<div style='margin-left: 10px;'>" +
                        "<div class='message_sender'>" + message.sender + "</div>" +
                        "<div class='message_content'>" + content + "</div>" +
                        "<div style='display: flex; flex-direction: row'>" +
                        "<div class='message_date'>" + chatTime + "</div>" +
                        "<span id = message_" + message.id + " onclick='chatButton(this);'><img class='message_menu' src='/images/menu_14px.png'></span>" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "</div>"

        if($('.Group_login_user').val() == message.sender)
            $('.Groupchatroom__chat__list').append(myMessage);
        else
            $('.Groupchatroom__chat__list').append(targetMessage);
}

function chatButton(e){

    if (document.querySelector('.message_menu_list') == null){
        let menu =  "<div class='message_menu_list'>" +
                    "<ul class='lyMenu message_menu_list'>" +
                    "<li><button data-uiselector='menuButton' type='button' class='reg_notice'>공지로 등록</button><hr/></li>" +
                    "<li><button data-uiselector='menuButton' type='button' class='message_delete'>삭제하기</button><hr/></li>" +
                    "<li><button data-uiselector='menuButton' type='button' class='menu_close'>닫기</button><hr/></li>" +
                    "</ul>" +
                    "</div>"

        $('#' + e.id).append(menu);
    } else $('.message_menu_list').remove();

}

function showMessage(message){

    showDate(message);

    if(message.type == 'greeting')
        showGreeting(message);
    else if(message.type == 'exit')
        showExit(message);
    else
        showContent(message)

    $('.Groupchatroom__chat__list')[0].scrollTop = $('.Groupchatroom__chat__list')[0].scrollHeight;
}

function loadGroupRoomData(){
    globalThis.roomId = $('.Group_roomId').val();

    var data = {roomId: globalThis.roomId}
    $.ajax({
        url: '/api/group/getRoomInfo',
        type: 'GET',
        data: data,
        success: function(response){

            document.getElementsByClassName('chatroom__title')[0].innerHTML = response.roomTitle + ' 채팅방';
            document.getElementById('offcanvasRightLabel').innerHTML = '대화멤버 목록[' + response.users.length + '/' + response.limitUsers + ']';

            $.each(response.users, function(key, value){
                $('.offcanvas-body').append(
                    "<div class=userbox_" + key + ">" +
                    "<div class='room_user_box'>" +
                    "<img class='room_user_profile' src=/upload/" + value.user.profile.profilePath + ">" +
                    "<div class='room_user_name'>" + value.user.username + "</div>" +
                    "<div>" +
                    "<div>"
                );
                GroupChatRoomUserArray.push({userObject: value});
            })

            $.each(response.chats, function(key, message){
                showMessage(message);
            });

        }
    })
}

function exitRoom(){

    var data = { roomId: globalThis.roomId, username: $('.Group_login_user').val()};

    $.ajax({
        url: '/api/group/exit',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function(response){
            if(response.includes('Success')){
                alert('대화방을 나갔습니다.');
                window.close();
                location.href='http://localhost:8000/product/Group';
            }
        }
    })
}