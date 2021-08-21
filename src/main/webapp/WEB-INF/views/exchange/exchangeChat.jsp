<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div>

    <div class="container" style="margin-top: 85px; width: 100%; height: 850px">

        <div style="padding: 5px 10px 5px 0; font-family: Pretendard-SemiBold; font-size: 23px;">물품교환 채팅목록</div>
        <div style="display: inline-flex; box-shadow: 0 0px 3px 0 rgba(0, 0, 0, 0.12); height: 725px; width: 100%">

            <div id="exchangeProcessChatList" style="border-right: 1px solid #e2dede; width: 27%; background: rgb(227, 230, 234); height: 100%">

            </div>

            <div id="currentChatContainer" style="width: 77%; height: 100%; background: #f7f8f9">
                <div class="chatUsernameContainer" style="display: inline-flex; width: 100%; height: 7%; background: white">
                    <div class="chatUsername" style="display: inline-flex; justify-content: center; font-size: 17px; width: 95%; font-family: Pretendard-SemiBold; margin: auto auto auto auto; text-align: center">
                        <div>
                            <img style="border-radius: 50%; object-fit: cover; width: 35px; height: 35px; margin-right: 10px;" class="chatUserProfile" src="/upload/">
                        </div>
                        <div class="targetUsernameBox" style="margin: auto 0 auto 0;">
                            Username
                        </div>
                    </div>

                    <div class="chatOptionButton" style="margin:auto auto auto auto; width: 5%">
                        <img style="cursor:pointer;" src="/images/menu_14px.png">
                    </div>

                </div>
                <div class="chatContainer" style="border-top: 1px solid #ebebeb; border-bottom: 1px solid #ebebeb; height: 78%">

                </div>
                <div class="chatInputCommand" style="position: relative; width: 100%; border-bottom: 1px solid #ebebeb; height: 5%; background: #F1F3FB;">
                    <ul class="chatInputList" style="list-style: none; display: flex;">

                        <li class="chatInputItem">
                            <button type="button" class="inputButton chatImageUploadButton" data-toggle="tooltip" data-placement="top" title="이미지 업로드">
                                <img src="/images/group/image_25px.png">
                            </button>
                        </li>

                        <li class="chatInputItem">
                            <button type="button" class="inputButton chatProfileUploadButton" data-toggle="tooltip" data-placement="top" title="프로필 전송">
                                <img src="/images/exchange/name_25px.png">
                            </button>
                        </li>

                        <li class="chatInputItem">
                            <button type="button" class="inputButton chatMarkerUploadButton" data-toggle="tooltip" data-placement="top" title="위치전송">
                                <img src="/images/exchange/place_marker_25px.png">
                            </button>
                        </li>

                    </ul>
                </div>
                <div id="sendChatContentContainer" style="background: white; height: 10%; padding: 11px 20px 11px 20px;">
                    <textarea style="width: 100%" id="SendChatContentBox" class="SendChatContentBox" type="text" placeholder="전송할 메세지 내용을 입력해주세요." value=""></textarea>
                </div>
            </div>

        </div>

    </div>

</div>
<%@ include file="../common/footer.jsp" %>
<link rel="stylesheet" type="text/css" href="/css/transaction.css">
<script>
    // login user's list of exchanging request

    // global variable
    const chatInfoObject = {
        userId: $('.g_user_id').val(),
        targetId: '',
        targetUsername: '',
        targetProfile: '',
        exchangeId: ''
    }

    var chatPage = 0
    var isLastPage = false
    var flag = false


    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })

    $(document).ready(function(){
        const data = {
            userId: chatInfoObject.userId
        }
        $.ajax({
            url: '/api/exchange/chat/get_chat_list',
            type: 'GET',
            data: data,
            success: (response) => {
                $.each(response, (key, value) => {
                    refreshChatList(value)
                })
            }
        })
    })

    $(document).on('click', '.exchangeBox', function (){

        var chatItems = Array.prototype.slice.call(document.getElementById('exchangeProcessChatList').children)
        chatItems.forEach(item => item.style.background = '')
        $(this)[0].style.background = 'rgb(234, 237, 241)'
        chatInfoObject.targetId = $(this).find('.exchangeTargetId')[0].getAttribute('data-target-id')
        chatInfoObject.targetUsername = $(this).find('.exchangeTargetUsername')[0].innerHTML
        chatInfoObject.targetProfile = $(this).find('img')[0].src.replace(/^.*[\\\/]/, '');
        chatInfoObject.exchangeId = $(this).find('.exchangeId')[0].getAttribute('data-target-id')
        $('.chatUserProfile')[0].src = '/upload/' + chatInfoObject.targetProfile
        $('.targetUsernameBox')[0].innerHTML = chatInfoObject.targetUsername + '님과의 채팅'

        $('.chatContainer').empty()
        chatPage = 0
        isLastPage = false
        getChatItemsAjax()
    })

    $('.chatContainer').scroll(function(){
        if($('.chatContainer')[0].scrollTop < 1){
            if(!globalThis.flag){
                globalThis.flag = true;
                getChatItemsAjax()
            }
        }
    });

    function getChatItemsAjax(){
        if(!isLastPage){
            $.ajax({
                url: '/api/exchange/chat/get_chat_item',
                type: 'GET',
                data: {exchangeId: chatInfoObject.exchangeId, page: chatPage, display: 20},
                success: (response) => {
                    if(response.last != false) isLastPage = true
                    let currentScrollbarPosition = $('.chatContainer')[0].scrollHeight - $('.chatContainer')[0].scrollTop
                    $.each(response.content, function(key, value){
                        $('.chatContainer').prepend(inputChat(value))
                    })
                    $('.chatContainer')[0].scrollTop = $('.chatContainer')[0].scrollHeight - currentScrollbarPosition - 2
                }
            })
            flag = false
            chatPage++
        }
    }

</script>
<script>
    // chatting Stomp Script
    var exchangeStomp = null

    $(document).ready(() => connect())
    $('#testMessageButton').click(() => sendMessage())

    function connect(){
        chatInfoObject.userId = $('.g_user_id').val()
        const socket = new SockJS('/ex')
        exchangeStomp = Stomp.over(socket)
        exchangeStomp.debug = null
        exchangeStomp.connect({}, function(){
            exchangeStomp.subscribe('/exchange/userId=' + chatInfoObject.userId, (message) => {
                showMessage(JSON.parse(message.body))
            })
        })
    }

    function sendMessage(message){
        var sendMessageObject = {
            'exchangeId': '', // exchange uinque Id
            'userId' : chatInfoObject.userId,
            'targetId' : chatInfoObject.targetId,
            'targetUsername' : chatInfoObject.targetUsername,
            'targetProfile' : chatInfoObject.targetProfile,
            'exchangeId' : chatInfoObject.exchangeId,
            'type' : 'text',
            'message' : message,
            'regTime' : new Date(),
            'result' : 'success'
        }
        exchangeStomp.send('/app/userId=' + chatInfoObject.targetId, {}, JSON.stringify(sendMessageObject))
        $('.chatContainer').append(inputChat(sendMessageObject))
        refreshChatList(sendMessageObject)
    }

    function showMessage(message){
        refreshChatList(message)
        if(chatInfoObject.exchangeId == message.exchangeId){
            $('.chatContainer').append(inputChat(message))
        }
    }

    function refreshChatList(value){
        $('#exchangeItemBoxId_' + value.exchangeId).remove()
        $('#exchangeProcessChatList').append(
            "<div id='exchangeItemBoxId_" + value.exchangeId + "' class='exchangeBox' style='cursor: pointer'>" +
            "<div style='display: flex; padding: 15px 10px 15px 10px;'>" +
            "<div class='exchangeTargetId' data-target-id='" + value.targetId + "'></div>" +
            "<div class='exchangeId' data-target-id='" + value.exchangeId + "'></div>" +
            "<div class='exchangeTargetProfile'>" +
            "<img src=/upload/" + value.targetProfile + ">" +
            "</div>" +
            "<div style='margin: 0 0 auto 10px'>" +
            "<div class='exchangeTargetUsername'>" + value.targetUsername + "</div>" +
            "<div class='exchangeTargetChat'>" + value.message + "</div>" +
            "<div class='chatRegTime'>" + new Date(value.regTime).toLocaleTimeString([],
                    {'hour': '2-digit',
                    'minute' : '2-digit'}) + "</div>" +
            "</div>" +
            "</div>" +
            "</div>"
        )
    }

    function inputChat(message){

        if(message.type == 'enter'){
            return "<div class='notiChatContent' style='padding: 10px 25px 10px 25px;'>" +
                "<div class='notiContent' style='text-align: center'>" + message.message + "</div>" +
                "</div>"
        }
        else if(message.userId == chatInfoObject.userId){
            // 내가 보낸 채팅내용
            return "<div class='meChatContent' style='text-align: right; padding: 25px;'>" +
                    "<div class='meContent'>" + message.message + "</div>" +
                    "<div class='chatRegTime' style='margin-top: 0;'>" + new Date(message.regTime).toLocaleTimeString([],
                        {'hour': '2-digit',
                        'minute' : '2-digit'}) +
                    "</div>" +
                    "</div>"
        }else{
            // 채팅상대가 보낸 채팅내용
            return "<div class='targetChatContent' style='text-align: left; padding: 25px;'>" +
                "<div style='display: inline-flex;'>" +
                "<div class='targetProfile'><img src=/upload/" + message.targetProfile + "></div>" +
                "<div class='userContentBox' style='margin-left: 10px;'>" +
                "<div class='targetUsername' style='font-family: Pretendard-SemiBold; font-size: 15px;'>" + message.targetUsername + "</div>" +
                "<div class='targetContent'>" + message.message + "</div>" +
                "<div class='chatRegTime'>" + new Date(message.regTime).toLocaleTimeString([],
                        {'hour': '2-digit',
                        'minute' : '2-digit'}) +
                "</div>" +
                "</div>" +
                "</div>" +
                "</div>"
        }
    }

    $('#SendChatContentBox').keyup(function(event) {
        const textBoxMessage = $('#SendChatContentBox').val();
        if(event.keyCode == 13)
            if (event.shiftKey){
                event.preventDefault();
            }else{
                if(textBoxMessage.length > 1){
                    // 보낼 메세지의 마지막에 \n 값을 빼고 전송
                    sendMessage(textBoxMessage.substring(0, textBoxMessage.lastIndexOf('\n')));
                    document.getElementById('SendChatContentBox').value = '';
                }else alert('메세지 내용을 입력해주세요.')
            }
    });

</script>















