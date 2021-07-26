
let stompClient = null;
let groupId;

$(document).ready(function (){
    connect();
    loadGroupRoomData();
})

$('#chatroom_comment_box').on('keyup', function (event){
    const textBoxMessage = $('#chatroom_comment_box').val();
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

    $('#img_upload').click(function(){
        $('#upload_dialog').click();
    })

    $('#upload_dialog').change(function(e){
        GroupRoomImageUpload(e);
    })
})


function GroupRoomImageUpload(e){

    const files = e.target.files;
    const fileArray = Array.prototype.slice.call(files);
    const formData = new FormData();

    formData.append('groupId', globalThis.groupId);
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
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = null
    stompClient.connect({}, function(){
        stompClient.subscribe('/chat/group/' + globalThis.groupId, function(message){
            var value = JSON.parse(message.body);
            showMessage(value);
        })
    })
}

function sendMessage(message, type = 'chat'){
    const data = {
        groupId: globalThis.groupId,
        userId: $('.login_user_id').val(),
        type: type,
        message: message,
    };
    stompClient.send('/app/send/chat/group/' + globalThis.groupId, {}, JSON.stringify(data));
    $('.Groupchatroom__chat__list')[0].scrollTop = $('.Groupchatroom__chat__list')[0].scrollHeight;
}

var preDate = null;
function showDate(message){
    if(globalThis.preDate == null || globalThis.preDate !== new Date(message.regTime).toLocaleDateString()){
        $('.Groupchatroom__chat__list').append(
            "<div class='message_box_event'>" +
            "<div class='message_noti'>" + new Date(message.regTime).toLocaleDateString() + "</div>" +
            "</div>"
        );
        globalThis.preDate = new Date(message.regTime).toLocaleDateString();
    }
}

function showGreeting(message){
    $('.Groupchatroom__chat__list').append(
        "<div class='message_box_event'>" +
            "<div class='message_noti'>" + message.message + "</div>" +
        "</div>"
    );
}

function showExit(message){
    $('.Groupchatroom__chat__list').append(
        "<div class='message_box_event'>" +
            "<div class='message_noti'>" + message.message + "</div>" +
        "</div>"
    );
}

function showContent(message){

    let userProfile = (message.grouUserEntity != null) ? message.groupUsersEntity.user.profile.profilePath : null
    let username = (message.grouUserEntity != null) ? message.groupUsersEntity.user.username : '알수없음'
    let content;

    if(message.type === 'image')
        content = "<img style='width: 260px; height: 200px; object-fit: cover;' src=/upload/" + message.message + ">";
    else
        content = message.message;

    const chatTime = new Date(message.regTime).toLocaleTimeString([], {'hour': '2-digit', 'minute': '2-digit'});

    const myMessage = "<div class='message_box'>" +
        "<div class='me_message_box'>" +
        "<div class='me_message_content'>" + content + "</div>" +
        "<div class='aside'>" +
        "<span id = message_" + message.id + " onclick='chatButton(this);'><img class='message_menu' src='/images/menu_14px.png'></span>" +
        "<span class='message_date'>" + chatTime + "</span>" +
        "</div>" +
        "</div>" +
        "</div>";

    const targetMessage = "<div class='message_box'>" +
        "<div class='target_message_box'>" +
        "<div><img class='target_profile_img' src=/upload/" + userProfile + "></div>" +
        "<div style='margin-left: 10px;'>" +
        "<div class='message_sender'>" + username + "</div>" +
        "<div class='message_content'>" + content + "</div>" +
        "<div style='display: flex; flex-direction: row'>" +
        "<div class='message_date'>" + chatTime + "</div>" +
        "<span id = message_" + message.id + " onclick='chatButton(this);'><img class='message_menu' src='/images/menu_14px.png'></span>" +
        "</div>" +
        "</div>" +
        "</div>" +
        "</div>";

        if(message.groupUsersEntity != null && $('.login_user_id').val() == message.groupUsersEntity.user.id)
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

    if(message.type === 'greeting')
        showGreeting(message);
    else if(message.type === 'exit')
        showExit(message);
    else
        showContent(message)

    $('.Groupchatroom__chat__list')[0].scrollTop = $('.Groupchatroom__chat__list')[0].scrollHeight;
}

function loadGroupRoomData(){
    globalThis.groupId = $('.groupId').val();
    const data = {
        groupId: globalThis.groupId
    };

    $.ajax({
        url: '/api/group/getRoomInfo',
        type: 'GET',
        data: data,
        success: function(response){

            document.getElementsByClassName('chatroom__title')[0].innerHTML = response.title + ' 채팅방';
            document.getElementById('offcanvasRightLabel').innerHTML = '대화멤버 목록[' + response.users.length + '명]';

            $.each(response.users, function(key, value){
                $('.offcanvas-body').append(
                    "<div class=userbox_" + key + ">" +
                    "<div class='room_user_box'>" +
                    "<img class='room_user_profile' src=/upload/" + value.user.profile.profilePath + ">" +
                    "<div class='room_user_name'>" + value.user.username + "</div>" +
                    "<div>" +
                    "<div>"
                );
            })

            $.each(response.chats, function(key, message){
                showMessage(message);
            });
        }
    })
}

