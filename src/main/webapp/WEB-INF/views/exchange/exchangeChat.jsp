<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div>

    <div class="container" style="margin-top: 85px; width: 100%; height: 850px">

        <div style="padding: 5px 10px 5px 0; font-family: Pretendard-SemiBold; font-size: 23px;">물품교환 채팅목록</div>
        <div style="display: inline-flex; height: 725px; width: 100%">

            <div id="exchangeProcessChatList" style="box-shadow: 2px 2px 3px -1px rgba(0, 0, 0, 0.12); width: 40%; background: rgb(227, 230, 234); height: 100%">

            </div>

            <div id="currentChatContainer" style="box-shadow: 2px 2px 3px -1px rgba(0, 0, 0, 0.12); width: 77%; margin-left: 20px; height: 100%; background: rgb(238, 240, 243)">
                <div class="chatUsernameContainer" style="display: inline-flex; width: 100%; height: 7%; background: white">
                    <div class="chatUsername" style="display: inline-flex; justify-content: center; font-size: 17px; width: 95%; font-family: Pretendard-SemiBold; margin: auto auto auto auto; text-align: center">
                        <div>
                            <img style="border-radius: 50%; object-fit: cover; width: 35px; height: 35px; margin-right: 10px;" class="chatUserProfile" src="" onerror="this.style.display='none'">
                        </div>
                        <div class="targetUsernameBox" style="margin: auto 0 auto 0;">

                        </div>
                    </div>

                    <div style="position: relative; height: 0;" class="menuOptionBox">
                        <div class="chatOptionButton">
                            <img style="float: right; cursor: pointer; object-fit: cover; margin: 15px 10px;" src="/images/menu_14px.png">
                        </div>
                    </div>

                </div>

                <div class="chatContainer" style="border-top: 1px solid #ebebeb; background: rgb(247, 248, 249); border-bottom: 1px solid #ebebeb; height: 78%">

                </div>
                <div class="chatInputCommand" style="position: relative; width: 100%; border-bottom: 1px solid #ebebeb; height: 5%; background: #F1F3FB;">
                    <ul class="chatInputList" style="list-style: none; display: flex;">

                        <li class="chatInputItem">
                            <button type="button" class="inputButton chatImageUploadButton" data-toggle="tooltip" data-placement="top" title="이미지 업로드">
                                <img id="uploadImageButton" src="/images/group/image_25px.png">
                                <input id="uploadChatImage" type="file" multiple="multiple" style="display: none">
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

            <div id="chatUsingInfoBox" style="width: 45%; height: 70%; margin: 0 0 0 20px;">
                <div style="padding: 5px; box-shadow: 2px 2px 3px -1px rgba(0, 0, 0, 0.12); background: white">
                    <div style="font-family: Pretendard-SemiBold; margin: 0 11px 0 11px; font-size: 17px; border-bottom: 1px solid #e3dfdf; padding: 10px">
                        채팅 유의사항
                    </div>

                    <div style="font-size: 14px;">
                        <div style="margin: 10px 20px">
                            <div>
                                반드시 물품을 교환할 상대의 프로필 정보와 계좌인증 여부를 확인하세요
                            </div>
                        </div>

                        <hr style="border: none; height: 1px; background: #ccc; margin: 0 20px 0 20px;">

                        <div style="margin: 10px 20px">
                            <div>
                                늦은 시간에 교환을 진행하실 경우 동행자와 함께 진행하세요
                            </div>
                        </div>

                        <hr style="border: none; height: 1px; background: #ccc; margin: 0 20px 0 20px;">

                        <div style="margin: 10px 20px">
                            <div>
                                상대와 정한 시간과 약속을 반드시 준수해주세요
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <div id="requestBatterServiceContainer" style="display: none;width: 40%; margin: 0 0 0 20px;">
                <div class="requestBatterService" style="padding: 0 0 20px 0; box-shadow: 2px 2px 3px -1px rgba(0, 0, 0, 0.12); width: 100%; background: white">

                    <div style="display: inline-flex; width:90%; justify-content: space-between; border-bottom: 1px solid #e3dfdf; margin: 0 11px 0 11px; padding: 10px">
                        <div style="font-family: Pretendard-SemiBold;font-size: 17px;">
                            로컬바터 서비스 등록
                        </div>
                        <div class = "closeLocalBatterBox" style="margin: auto 0;">
                            <div>
                                <img src="/images/delete_35px.png" style="object-fit: cover; width: 17px; height: 17px; float: right; cursor: pointer;">
                            </div>
                        </div>
                    </div>

                    <div style="margin: 10px 20px">
                        <div style="width: 100%; padding: 5px; font-weight: 600; font-size: 13px;">
                            <div>
                                물품을 받을 위치
                            </div>
                            <div style="margin-top: 10px; padding: 10px; width: 100%; height: 90px; background: rgb(252, 252, 252); ">
                                <div>
                                    <input type="text" class="inputbox receiveAddr" value="" placeholder="도로명, 지번주소">
                                </div>
                                <div>
                                    <input type="text" class="inputbox receiveDetailAddr" value="" placeholder="상세주소">
                                </div>
                            </div>
                        </div>
                    </div>

                    <div style="margin: 10px 20px">
                        <div style="width: 100%; padding: 5px; font-weight: 600; font-size: 13px;">
                            <div>
                                상대방과 만나는 위치
                            </div>
                            <div style="margin-top: 10px; padding: 10px; width: 100%; height: 90px; background: rgb(252, 252, 252); ">
                                <div>
                                    <input type="text" class="inputbox exchangeAddr" value="" placeholder="도로명, 지번주소">
                                </div>
                                <div>
                                    <input type="text" class="inputbox exchangeDetailAddr" value="" placeholder="상세주소">
                                </div>
                            </div>
                        </div>
                    </div>

                    <div style="margin: 10px 20px">
                        <div style="width: 100%; padding: 5px; font-weight: 600; font-size: 13px;">
                            <div>
                                요청사항 및 결제금액
                            </div>
                            <div style="margin-top: 10px; padding: 10px; width: 100%; height: 100%; background: rgb(252, 252, 252); ">
                                <div>
                                    <textarea type="text" class="inputbox requestContentBox" value="" placeholder="요청사항" rows="3"></textarea>
                                </div>
                                <div>
                                    <input type="text" class="inputbox preferTimeBox" value="" placeholder="물품교환 날짜" style="margin-top: 7px; font-size: 12px; width: 100%;">
                                </div>
                                <div>
                                    <input type="text"  onkeyup="convertM(this);" class="inputbox priceBox" value="" placeholder="결제금액">
                                </div>
                            </div>
                        </div>
                    </div>

                    <div style="text-align: center;">
                        <button class="btn btn-primary batterServiceCreateButton" style="font-family: Pretendard-Regular; width: 86%; border-radius: 0; font-size: 16px; padding: 5px;">서비스 등록</button>
                    </div>
                </div>

                <div style="height: 15px; background: rgb(238, 240, 243)">

                </div>

                <div class="viewBatterService">
                    <div class="viewBatterServiceContainer">

                    </div>
                </div>

            </div>
        </div>
    </div>
</div>

<!-- Localbatter Service 보기 알림창 -->
<div class="modal fade" id="viewServiceModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top: 100px; margin-left: 31%;">
        <div class="modal-content" style="width: 700px; border-radius: 0;">
            <div class="modal-header" style="border: none;">
                <div class="modal-title" id="serviceRegistModal" style="margin: 0 0 0 auto">등록한 요청</div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" style="width: 20px; height: 20px;"></button>
            </div>
            <div class="registServiceContentWrapper" style="height: 386px; padding: 0.5rem;">

                <div class="serviceContainer" style="padding: 10px 25px 0 37px">
                    <div style="padding: 10px;">
                        <div style="display: inline-flex;">
                            <div class="itemCategory">교환일시</div>
                            <div class="serviceContent serviceTimeBox"></div>
                        </div>
                    </div>
                    <div style="padding: 10px;">
                        <div style="display: inline-flex;">
                            <div class="itemCategory">교환위치</div>
                            <div class="serviceContent serviceLocationBox"></div>
                        </div>
                    </div>
                    <div style="padding: 10px;">
                        <div style="display: inline-flex;">
                            <div class="itemCategory">수령위치</div>
                            <div class="serviceContent serviceReceiveBox"></div>
                        </div>
                    </div>
                    <div style="padding: 10px;">
                        <div style="display: inline-flex;">
                            <div class="itemCategory">요청사항</div>
                            <div class="serviceContent serviceRequestBox"></div>
                        </div>
                    </div>
                    <div style="padding: 10px;">
                        <div style="display: inline-flex;">
                            <div class="itemCategory">결제금액</div>
                            <div class="serviceContent servicePriceBox"></div>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>
</div>
<!--  -->

<!-- 채팅 위치발송 알림창 -->
<div class="modal fade" id="sendMapAddressModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top: 100px; margin-left: 27%;">
        <div class="modal-content" style="width: 900px; border-radius: 0;">
            <div class="modal-header" style="border: none;">
                <div class="modal-title" id="MapAddressModal" style="margin: 0 0 0 auto">위치 보내기</div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" style="width: 20px; height: 20px;"></button>
            </div>
            <div class="clientBoardListModal clientRequestModal" style="height: 586px; padding: 0.5rem;">

                <div style="display: none" id="longitude"></div>
                <div style="display: none" id="latitudeValue"></div>
                <div class="serachAddr">
                    <input style="width: 400px;" type="text" id="serachAddrText" class="clientExchangeInfoBox inputbox" placeholder="검색할 지역의 주소를 입력해주세요">
                    <button class="clientExchangeButton searchButton">검색</button>
                    <input style="width: 250px;" type="text" id="detailAddrText" class="clientExchangeInfoBox inputbox" placeholder="세부주소를 입력해주세요">

                </div>
                <div id="currentAddr">지역을 선택하면 해당 위치가 표기됩니다.</div>
                <div id="map" style="width:99%; height:433px;"></div>

                <div>
                    <button class="sendMapButton sendMapAddress">확인</button>
                    <button class="sendMapButton sendMapClose">닫기</button>
                </div>

            </div>
        </div>
    </div>
</div>
<!--  -->

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2f665d933d93346898df736499236f77&libraries=services"></script>
<script type="text/javascript" src="/js/exchange/registMapAddress.js"></script>
<link rel="stylesheet" type="text/css" href="/css/transaction.css">
<link rel="stylesheet" type="text/css" href="/css/clientExchange.css">
<%@ include file="../common/footer.jsp" %>
<script>
    // login user's list of exchanging request

    // global variable
    const chatInfoObject = {
        userId: $('.g_user_id').val(),
        username : '',
        userProfile : '',
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
                    if($('.g_user_id').val() == value.senderId){
                        chatInfoObject.userId = value.senderId
                        chatInfoObject.username = value.senderUsername
                        chatInfoObject.userProfile = value.senderProfile
                    }else{
                        chatInfoObject.userId = value.receiveId
                        chatInfoObject.username = value.receiveUsername
                        chatInfoObject.userProfile = value.receiveProfile
                    }
                    refreshChatList(value)
                })
            }
        })
    })

    // 로컬 바터 서비스 등록
    $(document).on('click', '.batterServiceCreateButton', ()=>{
        const data = {
            userId: chatInfoObject.userId,
            writerClientJoinId: chatInfoObject.exchangeId,
            receiveAddr: $('.receiveAddr').val(),
            receiveDetailAddr: $('.receiveDetailAddr').val(),
            receiveLongitude: '',
            receiveLatitude: '',
            exchangeAddr: $('.exchangeAddr').val(),
            exchangeDetailAddr: $('.exchangeDetailAddr').val(),
            exchangeLongitude: '',
            exchangeLatitude: '',
            request: $('.requestContentBox').val(),
            price: $('.priceBox').val(),
            time: $('.preferTimeBox').val(),
        }

        $.ajax({
            url: '/api/exchange/save/service',
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded',
            data: data,
            success: (response) =>{
                var html = ''

                html += '<div class="batterServiceItem">'
                html += '<input type="hidden" class="serviceId" value=' + response.writerClientJoinId + '>'
                html += '<input type="hidden" class="ServiceIdValue" value=' + response.writerClientJoinId + '>'
                html += '<input type="hidden" class="serviceTimeBoxValue" value=' + response.time + '>'
                html += '<input type="hidden" class="serviceLocationBoxValue" value=' + response.exchangeAddr + ' ' + response.exchangeDetailAddr + '>'
                html += '<input type="hidden" class="serviceReceiveBoxValue" value=' + response.receiveAddr + ' ' + response.receiveDetailAddr + '>'
                html += '<input type="hidden" class="serviceRequestBoxValue" value=' + response.request + '>'
                html += '<input type="hidden" class="servicePriceBoxValue" value=' + response.price + '>'
                html += '<div style="display: inline-flex; width: 100%">'
                html += '<div style="margin: auto auto auto 5px;">물품 대리교환을 등록하였습니다.</div>'
                html += '<div class="serviceButton showServiceInfoButton" style="margin: auto 0 auto 0"><button>보기</button></div>'
                html += '<div class="serviceButton deleteServiceButton" style="margin: auto 0 auto 10px"><button>삭제</button></div>'
                html += '</div>'
                html += '</div>'

                $('.viewBatterServiceContainer').append(html)
            }
        })

    })

    // 로컬 바터 서비스 삭제
    $(document).on('click', '.deleteServiceButton', ()=>{
        const data = {
            userId: chatInfoObject.userId,
            writerClientJoinId: chatInfoObject.exchangeId,
        }

        $.ajax({
            url: '/api/exchange/delete/service',
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded',
            data: data,
            success: () =>{
                $('.viewBatterServiceContainer').empty()
            }
        })

    })

    // 로컬바터 서비스 등록 창 닫기
    $(document).on('click', '.closeLocalBatterBox', () =>{
        $('#requestBatterServiceContainer')[0].style.display = 'none'
    })

    // 개설된 채팅방 목록 중 특정 방 선택 이벤트
    $(document).on('click', '.exchangeBox', function (){

        $('#chatUsingInfoBox')[0].style.display = 'none'
        $('#requestBatterServiceContainer')[0].style.display = 'block'

        var chatItems = Array.prototype.slice.call(document.getElementById('exchangeProcessChatList').children)
        chatItems.forEach(item => item.style.background = '')
        $(this)[0].style.background = 'rgb(234, 237, 241)'
        chatInfoObject.targetId = $(this).find('.exchangeTargetId')[0].getAttribute('data-target-id')
        chatInfoObject.targetUsername = $(this).find('.exchangeTargetUsername')[0].innerHTML
        chatInfoObject.targetProfile = $(this).find('img')[0].src.replace(/^.*[\\\/]/, '');
        chatInfoObject.exchangeId = $(this).find('.exchangeId')[0].getAttribute('data-target-id')
        $('.chatUserProfile')[0].src = '/upload/' + chatInfoObject.targetProfile
        $('.chatUserProfile')[0].style.display = 'block'
        $('.targetUsernameBox')[0].innerHTML = chatInfoObject.targetUsername + '님과의 채팅'

        $('.chatContainer').empty()
        chatPage = 0
        isLastPage = false
        getChatItemsAjax()
        getLocalbatterServiceAjax()
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
                    console.log(response)
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

    function getLocalbatterServiceAjax(){

        const data = {
            userId: chatInfoObject.userId,
            exchangeId: chatInfoObject.exchangeId
        }

        $.ajax({
            url: '/api/exchange/chat/get/service/item',
            type: 'GET',
            data: data,
            success: (response) =>{
                $('.viewBatterServiceContainer').empty()
                var html = ''

                if(JSON.stringify(response).includes('userId')){
                    html += '<div class="batterServiceItem">'
                    html += '<input type="hidden" class="ServiceIdValue" value=' + response.writerClientJoinId + '>'
                    html += '<input type="hidden" class="serviceTimeBoxValue" value=' + response.time + '>'
                    html += '<input type="hidden" class="serviceLocationBoxValue" value=' + response.exchangeAddr + ' ' + response.exchangeDetailAddr + '>'
                    html += '<input type="hidden" class="serviceReceiveBoxValue" value=' + response.receiveAddr + ' ' + response.receiveDetailAddr + '>'
                    html += '<input type="hidden" class="serviceRequestBoxValue" value=' + response.request + '>'
                    html += '<input type="hidden" class="servicePriceBoxValue" value=' + response.price + '>'
                    html += '<div style="display: inline-flex; width: 100%">'
                    html += '<div style="margin: auto auto auto 5px;">등록된 대리교환 요청이 있습니다.</div>'
                    html += '<div class="serviceButton showServiceInfoButton" style="margin: auto 0 auto 0"><button>보기</button></div>'
                    html += '<div class="serviceButton deleteServiceButton" style="margin: auto 0 auto 10px"><button>삭제</button></div>'
                    html += '</div>'
                    html += '</div>'

                    $('.viewBatterServiceContainer').append(html)
                }
            }
        })

    }

    function uploadImageToChat(f){

        var formData = new FormData()
        formData.append('senderId', chatInfoObject.userId)
        formData.append('senderUsername', chatInfoObject.username)
        formData.append('senderProfile', chatInfoObject.userProfile)
        formData.append('receiveId', chatInfoObject.targetId)
        formData.append('receiveUsername', chatInfoObject.targetUsername)
        formData.append('receiveProfile', chatInfoObject.targetProfile)
        formData.append('exchangeId', chatInfoObject.exchangeId)
        formData.append('messageId', chatInfoObject.userId)
        formData.append('type', 'image')
        formData.append('message', null)
        formData.append('result', 'success')
        formData.append('files', f)

        $.ajax({
            url: '/api/exchange/chat/upload/image',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function(response){
                showMessage(response)
            }
        })
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
        // exchangeStomp.debug = null
        exchangeStomp.connect({}, function(){
            exchangeStomp.subscribe('/exchange/userId=' + chatInfoObject.userId, (message) => {
                showMessage(JSON.parse(message.body))
            })
        })
    }

    function sendMessage(message, type = 'text'){
        if (chatInfoObject.targetId != null && chatInfoObject.targetId != '') {
            var sendMessageObject = {
                'exchangeId': '', // exchange uinque Id
                'senderId': Number(chatInfoObject.userId),
                'senderUsername': chatInfoObject.username,
                'senderProfile': chatInfoObject.userProfile,
                'receiveId': Number(chatInfoObject.targetId),
                'receiveUsername': chatInfoObject.targetUsername,
                'receiveProfile': chatInfoObject.targetProfile,
                'exchangeId': Number(chatInfoObject.exchangeId),
                'messageId': Number(chatInfoObject.userId),
                'type': type,
                'message': message,
                'coordinate': '',
                'regTime': new Date(),
                'result': 'success'
            }
            switch (type) {
                case 'image':
                    sendMessageObject.message = "<img src=/upload/" + message + ">"
                    break
                case 'location':
                    sendMessageObject.message = message.currentAddr + '\n' + message.detailAddr
                    sendMessageObject.coordinate = message.longtitude + ',' + message.langtitude
                    break
                default:
                    break
            }

            exchangeStomp.send('/app/userId=' + chatInfoObject.targetId, {}, JSON.stringify(sendMessageObject))
            $('.chatContainer').append(inputChat(sendMessageObject))
            refreshChatList(sendMessageObject)
            $('.chatContainer')[0].scrollTop = $('.chatContainer')[0].scrollHeight
        } else alert('대화할 상대를 선택 후 다시 시도해주세요')
    }

    function showMessage(message){
        refreshChatList(message)
        if(chatInfoObject.exchangeId == message.exchangeId){
            $('.chatContainer').append(inputChat(message))
            $('.chatContainer')[0].scrollTop = $('.chatContainer')[0].scrollHeight
        }
    }


    // 로그인한 user의 교환 진행중인 채팅목록을 불러옵니다.
    function refreshChatList(value){

        var targetId
        var targetUsername
        var targetProfile


        var messageType = (value.type == 'image') ? '이미지 파일' : value.message
        // 내가 보낸 채팅일 경우 메세지 앞에 '나'를 표시
        var fromChat = ($('.g_user_id').val() == value.messageId) ? '<span class="displayMeChat">나</span>' : ''

        if(value.receiveId == null){
            // 채팅방의 상대 user가 대화방을 나갈경우
            targetUsername = '알수없음'
        }
        else if($('.g_user_id').val() == value.senderId){
        // 채팅방목록 조회 시 로그인한 계정과 대화중인 계정의 정보를 조회
             targetId = value.receiveId
             targetUsername = value.receiveUsername
             targetProfile = value.receiveProfile
        }else{
            targetId = value.senderId
            targetUsername = value.senderUsername
            targetProfile = value.senderProfile
        }

            $('#exchangeItemBoxId_' + value.exchangeId).remove()
            $('#exchangeProcessChatList').prepend(
            "<div id='exchangeItemBoxId_" + value.exchangeId + "' class='exchangeBox' style='cursor: pointer'>" +
            "<div style='display: flex; padding: 15px 10px 5px 10px;'>" +
            "<div class='exchangeTargetId' data-target-id='" + targetId + "'></div>" +
            "<div class='exchangeId' data-target-id='" + value.exchangeId + "'></div>" +
            "<div class='exchangeTargetProfile'>" +
            "<img src=/upload/" + targetProfile + ">" +
            "</div>" +
            "<div style='margin: 0 0 auto 10px; width: 100%'>" +
            "<div style='display: inline-flex; width: 100%'>" +
            "<div class='exchangeTargetUsername'>" + targetUsername + "</div>" +
            "<div class='chatRegTime' style='margin: auto 0 auto auto'>" + new Date(value.regTime).toLocaleTimeString([],
                {
                    'hour': '2-digit',
                    'minute' : '2-digit'
                }) +
            "</div>" +
            "</div>" +
            "<div class='exchangeTargetChat' style='margin-bottom: 5px;'>" + fromChat + messageType + "</div>" +
            "</div>" +
            "</div>" +
            "</div>"
        )
    }

    // 선택한 채팅방의 채팅내용목록을 불러옵니다.
    function inputChat(message){
        switch(true){
            case message.type == 'enter':
                return "<div class='notiChatContent' style='padding: 10px 25px 10px 25px;'>" +
                    "<div class='notiContent' style='text-align: center'>" + message.message + "</div>" +
                    "</div>"

            case message.type == 'quit':
                return "<div class='notiChatContent' style='padding: 10px 25px 10px 25px;'>" +
                    "<div class='notiContent' style='text-align: center'>" + message.message + "</div>" +
                    "</div>"

            // 내가 보낸 채팅내용
            case message.senderId == Number(chatInfoObject.userId):
            return "<div class='meChatContent' style='text-align: right; padding: 15px 25px 15px 25px;'>" +
                    "<div class='meContent'>" + returnValueOfChatType(message) + "</div>" +
                    "<div class='chatRegTime' style='margin-top: 0;'>" + new Date(message.regTime).toLocaleTimeString([],
                        {
                            'hour': '2-digit',
                            'minute' : '2-digit'
                        }) +
                    "</div>" +
                    "</div>"

            // 채팅상대가 보낸 채팅내용
            case message.senderId != Number(chatInfoObject.userId):
                return "<div class='targetChatContent' style='text-align: left; padding: 15px 25px 15px 25px;'>" +
                    "<div style='display: inline-flex;'>" +
                    "<div class='targetProfile'><img class='targetProfileImage' src=/upload/" + message.receiveProfile + "></div>" +
                    "<div class='userContentBox' style='margin-left: 10px;'>" +
                    "<div class='targetUsername' style='font-family: Pretendard-SemiBold; font-size: 15px;'>" + message.receiveUsername + "</div>" +
                    "<div class='targetContent'>" + returnValueOfChatType(message) + "</div>" +
                    "<div class='chatRegTime'>" + new Date(message.regTime).toLocaleTimeString([],
                        {
                            'hour': '2-digit',
                            'minute' : '2-digit'
                        }) +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>"
        }
    }


    // 사용자가 보낸 메세지의 Type에 따라 값을 변환합니다.
    function returnValueOfChatType(message){
        switch (message.type){
            case 'text':
                return message.message
            case 'image':
                return "<img class='userChatImageFile' src=/upload/" + message.message + ">"
            case 'location':
                return "<div>" +
                        message.message.split('\n')[0] +
                            "<br>" +
                        message.message.split('\n')[1] +
                        "<div>" +
                            "<input type='hidden' class='coordinateValue' value=" + message.coordinate + ">" +
                            "<a href='https://map.kakao.com/link/map/" + message.message.split('\n')[0] + ',' + message.coordinate + "' target='_blank'>" +
                                "<button class='actionButton findMapButton' style='margin: 5px 0 0 0; padding: 5px; width: 100%;'>" +
                                        "길 찾기" +
                                "</button>" +
                            "</a>" +
                        "</div>" +
                        "</div>"
        }
    }

    $('.chatMarkerUploadButton').click(function(){
        $('#sendMapAddressModal').modal('show')
    })

    $(document).on('click', '.chatOptionButton', ()=>{
        $('.optionMoreContainer').remove()

        var html = ''
        html += '<div class="optionMoreContainer">'
        html += '<div class="optionMenuButton showProfileMenuButton">프로필 보기</div>'
        html += '<hr />'
        html += '<div class="optionMenuButton exchangeConfirmMenuButton">교환확정</div>'
        html += '<hr />'
        html += '<div class="optionMenuButton chatExitMenuButton">나가기</div>'
        html += '<hr />'
        html += '</div>'

        $('.menuOptionBox').append(html)

    })

    $(document).on('click', '.exchangeConfirmMenuButton', ()=>{

        const data = {
            userId: chatInfoObject.userId,
            exchangeId: chatInfoObject.exchangeId
        }

        $.ajax({
            url: '/api/exchange/confirm',
            type: 'POST',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            success: (response) =>{

            }
        })
    })

    $(document).on('click', '.chatExitMenuButton', ()=>{

        if(confirm('채팅방을 나가면 대화목록에서 삭제되며 교환요청이 취소됩니다. \n 나가시겠습니까?')){
            const data = {
                userId: chatInfoObject.userId,
                receiveId: (chatInfoObject.targetId == 'undefined') ? 0 : chatInfoObject.targetId,
                exchangeId: chatInfoObject.exchangeId
            }

            $.ajax({
                url: '/api/exchange/chat/exit',
                type: 'POST',
                data: data,
                contentType: 'application/x-www-form-urlencoded',
                success: () =>{
                    alert('해당 채팅방을 나갔습니다.')
                }
            })
        }
    })

    $(document).on('click', '.showProfileMenuButton', ()=>{
        window.open('http://localhost:8000/my/profile/' + chatInfoObject.targetId)
    })

    document.body.addEventListener('click', removeMenuBox, true);

    function removeMenuBox(){
        if (document.getElementsByClassName('optionMoreContainer').length != 0)
            $('.optionMoreContainer').remove();
    }

    $(document).on('click', '.showServiceInfoButton', ()=>{
        $('#viewServiceModal').modal('show')

        $('.serviceTimeBox')[0].innerHTML = $('.serviceTimeBoxValue').val()
        $('.serviceLocationBox')[0].innerHTML = $('.serviceLocationBoxValue').val()
        $('.serviceReceiveBox')[0].innerHTML = $('.serviceReceiveBoxValue').val()
        $('.serviceRequestBox')[0].innerHTML = $('.serviceRequestBoxValue').val()
        $('.servicePriceBox')[0].innerHTML = $('.servicePriceBoxValue').val()
    })

    $('#sendMapAddressModal').on('shown.bs.modal', function (e) {
        reloadKakaoMap()
    })

    $('.sendMapAddress').click(function(){
        const locationInfoObject = {
            currentAddr: $('#currentAddr')[0].innerHTML,
            detailAddr: $('#detailAddrText').val(),
            longtitude: $('#longitude')[0].innerHTML,
            langtitude: $('#latitudeValue')[0].innerHTML
        }
        sendMessage(locationInfoObject, 'location')
    })

    $('.sendMapClose').click(function(){
        $('#sendMapAddressModal').modal('hide')
    })

    $(function(){
        $('#uploadImageButton').click(function () {
            $('#uploadChatImage').click()
        })

        $('#uploadChatImage').change(function (e) {
            const fileArray = Array.prototype.slice.call(e.target.files);
            fileArray.forEach(function (f) {
                if (!f.type.match("image.*")) {
                    alert("이미지파일만 업로드가 가능합니다.");
                    return;
                }
                uploadImageToChat(f)
            });
        })
    })


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

    function convertM(object){
        var len, point, str;
        var num = object.value.replaceAll(',', '');
        num = num + "";
        point = num.length % 3 ;
        len = num.length;
        if(num.length < 9){
            str = num.substring(0, point);
            while (point < len) {
                if (str != "") str += ",";
                str += num.substring(point, point + 3);
                point += 3;
            }
            object.value = str;
        }else{
            alert('최대 입력가능한 숫자를 초과하였습니다.');
            object.value = num.substr(0, 8)
        }
    }

</script>




