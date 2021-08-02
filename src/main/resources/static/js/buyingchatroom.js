
const userInfo = {
    userId: $('.login_user_id').val(),
    groupId: $('.groupId').val(),
    username: '',
    userProfile: ''
}

let loadChatList = false
$('.group_chat_box').scroll(function(){
    if(loadChatList && $('.group_chat_box').scrollTop() < 1){
        loadChatList = false
        getChatList()
    }
});

$(document).ready(function (){
    connect()
    loadGroupRoomData()
    getChatList()
})

$(function (){
    $('#img_upload').click(function(){
        $('#upload_dialog').click()
    })

    $('#upload_dialog').change(function(e){
        GroupRoomImageUpload(e)
    })
})

var chatPage = 0
let lastPage = false;
function getChatList(){

    let currentScrollPosition = $('.group_chat_box')[0].scrollHeight;

    const data = {
        page: chatPage,
        display: 10,
        groupId: userInfo.groupId
    }

    if(!lastPage){
        $.ajax({
            url: '/api/v2/group/chat/get_chat_list',
            type: 'GET',
            data: data,
            success: function(response){

                $.each(response.content, function(key, value){
                    $('.Groupchatroom__chat__list').prepend(showContent(value))
                })
                if(chatPage == 0)
                    $('.group_chat_box')[0].scrollTop = $('.group_chat_box')[0].scrollHeight;
                else
                    $('.group_chat_box')[0].scrollTop = $('.group_chat_box')[0].scrollHeight - currentScrollPosition

                if(response.last == true) {
                    lastPage = true;
                    showDate(response.content[response.content.length-1], true)
                }

                chatPage++
            }

        })
        loadChatList = true;
    }
}

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

function GroupRoomImageUpload(e){

    const files = e.target.files;
    const fileArray = Array.prototype.slice.call(files);
    const formData = new FormData();

    formData.append('groupId', globalThis.groupId);
    formData.append('userId', $('.login_user_id').val());
    formData.append('message', null);
    formData.append('type', 'image');
    formData.append('localDate', new Date().toISOString());

    fileArray.forEach(function(value){
        formData.append('img', value);
    })

    $.ajax({
        url: '/api/v2/group/chat/upload',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function(response){
            console.log(response)
        }
    })
}

function connect(){
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.debug = null
    stompClient.connect({}, function(){
        stompClient.subscribe('/group/chat/' + globalThis.groupId, function(message){
            var value = JSON.parse(message.body);
            showMessage(value);
        })
    })
}

function sendMessage(message, type = 'chat'){
    const data = {
        groupId: globalThis.groupId,
        userId: userInfo.userId,
        username: userInfo.username,
        profile: userInfo.userProfile,
        message: message
    };
    stompClient.send('/app/send/group/chat/' + globalThis.groupId, {}, JSON.stringify(data));
}

var preDate = new Date().toLocaleDateString();
function showDate(message, last = false){

    if(last || preDate != new Date(message.regTime).toLocaleDateString()){
        $('.Groupchatroom__chat__list').prepend(
            "<div class='message_box_event'>" +
            "<div class='message_noti'>" + preDate + "</div>" +
            "</div>"
        );
        preDate = new Date(message.regTime).toLocaleDateString();
    }
}

function showContent(message){

    let userProfile = (message.profile != null) ? message.profile : null
    let username = (message.username != null) ? message.username : '알수없음'
    let content;

    if(message.type == 'image')
        content = "<img style='width: 260px; height: 200px; object-fit: cover;' src=/upload/" + message.message + ">";
    else
        content = message.message;

    const chatTime = new Date(message.regTime).toLocaleTimeString([], {'hour': '2-digit', 'minute': '2-digit'});

    const myMessage = "<div class='message_box'>" +
        "<div class='me_message_box'>" +
            "<div class='me_message_content'>" + content + "</div>" +
                "<div class='aside'>" +
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
                    "</div>" +
                "</div>" +
            "</div>" +
        "</div>";

        showDate(message)

        return (message.userId != null && $('.login_user_id').val() == message.userId) ? myMessage : targetMessage
}

function showMessage(message){
    $('.Groupchatroom__chat__list').append(showContent(message))
    $('.group_chat_box')[0].scrollTop = $('.group_chat_box')[0].scrollHeight;
}

var memberPage = 0
function loadGroupRoomData(){
    globalThis.groupId = $('.groupId').val();
    const data = {
        page: memberPage,
        display: 50,
        groupId: $('.groupId').val()
    }

    $.ajax({
        url: '/api/group/get_member_list',
        type: 'GET',
        data: data,
        success: function(response){
            document.getElementById('offcanvasRightLabel').innerHTML = '대화멤버 목록[' + response.content.length + '명]';

            $.each(response.content, function(key, value){
                let thisUserisMe = ''

                if(value.userId == userInfo.userId){
                    userInfo.username = value.username
                    userInfo.userProfile = value.profile
                    thisUserisMe = '나'
                }

                $('.offcanvas-body').append(
                    "<div class=userbox_" + key + ">" +
                    "<div class='room_user_box'>" +
                    "<img class='room_user_profile' src=/upload/" + value.profile + ">" +
                    "<div class='room_user_name'>" + value.username + "</div>" +
                    thisUserisMe +
                    "<div>" +
                    "<div>"
                );
            })
        }
    })
    memberPage++
}

