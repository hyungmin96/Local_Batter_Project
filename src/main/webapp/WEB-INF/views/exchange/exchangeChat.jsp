<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div>

    <div class="container" style="margin-top: 85px; width: 100%; height: 850px">

        <div style="padding: 10px; font-family: Pretendard-SemiBold; font-size: 23px;">물품교환 채팅목록</div>

        <div style="display: inline-flex; height: 725px; width: 100%">

            <div id="exchangeProcessChatList" style="width: 30%; background: rgb(227, 230, 234); height: 100%">
                <div class="chatListContainer">

                </div>
            </div>

            <div id="currentChatContainer" style="width: 70%; height: 100%; background: #f7f8f9">
                <div class="chatContainer" style="height: 88%">

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
                <div id="sendChatContentContainer" style="background: white; height: 7%; padding: 11px 20px 11px 20px;">
                    <input style="width: 100%" id="SendChatContentBox" class="SendChatContentBox" type="text" placeholder="전송할 메세지 내용을 입력해주세요." value="">
                </div>
            </div>

        </div>

    </div>

</div>
<%@ include file="../common/footer.jsp" %>
<link rel="stylesheet" type="text/css" href="/css/transaction.css">
<script>
    $(function () {
        $('[data-toggle="tooltip"]').tooltip()
    })

    $(document).ready(function(){

        const data = {
            userId: $('.g_user_id').val()
        }

        $.ajax({
            url: '/api/exchange/chat/get_chat_list',
            type: 'GET',
            data: data,
            success: function (response){
                console.log(response)
            }
        })

    })

</script>
