<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<%--로그인한 사용자가 현재 진행중인 물품교환 목록을 보여줍니다--%>
<div>

    <div class="container" style="margin-top: 100px;">

        <div id="transactionListContainer" style="max-height: 1600px; min-height: 801px">
            <div class="textItem _listHeader">작성한 교환 게시글</div>
            <div class="itemWrapper" style="box-shadow: 1px 1px 17px 2px rgba(0, 0, 0, 0.12); background: white; margin: 10px 15px 25px 15px;">

                <div style="display: flex; height: 55px;">
                    <div class="itemHeader" style="height: 20px; margin: 13px auto auto 15px;">
                        작성 게시글
                        <img id="myBoardUpDown" class="requestListUpButton" src="/images/exchange/up.png" style="cursor: pointer">
                    </div>
                </div>

                <div id="myBoardItemsContainer" style="margin-top: 5px;">
                    <hr style="width: 97%; margin: 0 25px 0 20px;" />
                </div>

            </div>

        <div class="textItem _listHeader" style="margin-top: 20px">요청한 교환 게시글</div>
        <div class="itemWrapper" style="box-shadow: 1px 1px 17px 2px rgba(0, 0, 0, 0.12); background: white; margin: 10px 15px 45px 15px;">

            <div style="display: flex; height: 55px;">
                <div class="itemHeader" style="height: 20px; margin: 13px auto auto 15px;">
                    진행중인 물품
                    <img id="upDownButton" class="requestListUpButton" src="/images/exchange/up.png" style="cursor: pointer">
                </div>
            </div>

            <div id="transactionItemsContainer" style="margin-top: 5px;">
                <hr style="width: 97%; margin: 0 25px 0 20px;" />
            </div>

        </div>
    </div>
    </div>
</div>

<!--group board view modal -->
<div class="modal fade" id="requestListModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top: 100px; margin-left: 20%;">
        <div class="modal-content" style="width: 1100px; border-radius: 0;">
            <div class="modal-header" style="border: none;">
                <div class="modal-title" id="groupBoardModal" style="margin: 0 0 0 auto">교환요청 목록</div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" style="width: 20px; height: 20px;"></button>
            </div>
            <div class="boardModal" style="width: 1100px; height: 700px; padding: 0.5rem;">

                <div class="modalColumn" style="display: inline-flex">
                    <div style="width: 160px">작성자</div>
                    <div style="width: 160px">물품 이미지</div>
                    <div style="width: 310px">물품 이름</div>
                    <div style="width: 210px">문의사항</div>
                    <div style="width: 180px"></div>
                </div>

                <div class="requestBoardItemContainer">

                </div>

            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

<link rel="stylesheet" text="javascript/css" href="/css/transaction.css">
<script>

    const clientData = {
        clientId: '', // user id
        boardId: '',
        writerId: '', // writer id
        clientExchangeId: ''
    }

    $(document).on('click', '.showClientRequestList', function (){
        $('#requestListModal').modal('show')

        const data = {
            page: 0,
            display: 10,
            userId: $('.g_user_id').val(),
            boardId: $(this).attr('id').split('_')[1]
        }

        $.ajax({
            url: '/api/exchange/view/board/client_reqeust_list',
            type:'GET',
            data: data,
            success: function(response){
                $('.requestBoardItemContainer').empty()

                $.each(response.content, function(key, value){
                    console.log(value)
                    $('.requestBoardItemContainer').append(
                        "<div style='width: 97%'>" +
                            "<div class='clientRequestItemBox'>" +
                                "<div class='clientUserField' style='justify-content: center; margin: auto; width: 160px; display: inline-flex'" +
                                    "<div>" +
                                        "<div style='display: inherit'><img style='margin: auto 10px auto; border-radius: 50%; width: 40px; height: 40px;' src='/upload/" + value.userProfile + "' </div>" +
                                        "<div style='margin: auto auto;'>" + value.username + "</div>" +
                                    "</div>" +
                                "</div>" +
                                "<div style='width: 160px;' class='clientRequestImageField'>" +
                                    "<div><img style='border-radius: 10px; border: 1px solid #f5f4f4; width: 60px; height: 60px;' src='/upload/" + value.filename + "' </div>" +
                                "</div>" +
                                "</div>" +
                                "<div style='margin: auto 10px auto; width: 310px;' class='clientRequestTitleField'>" +
                                    value.clientExchange.title +
                                "</div>" +
                                "<div style='margin: auto 10px auto; width: 180px;' class='clientRequestLocationField'>" +
                                    value.clientExchange.address +
                                "</div>" +
                                "<div style='margin: auto 10px auto -10px; width: 210px;' class='clientRequestField'>" +
                                    "<button class='actionButton showRequestDetailButton'>보기</button>" +
                                    "<button class='actionButton rejectRequestButton'>삭제</button>" +
                                "</div>" +
                            "</div>" +
                        "</div>" +
                        "<hr style='border:none; background: #cccccc; height: 1px; margin: 0 30px 0 30px;' />"
                    )
                })

            }
        })
    })

    $(document).ready(function (){
        setTimeout(function(){
            getWriterBoardList();
        }, 100);
        getRequestList();
    })

    // 교환요청 글 삭제
    $(document).on('click', '.requestCancelButton', function () {

        const data = {
            clientId: clientData.clientId,
            clientExchangeId: clientData.clientExchangeId
        }
        $.ajax({
            url: '/api/exchange/cancel/request',
            type: 'POST',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            success: function(response){
                alert('교환요청을 삭제하였습니다.')
                window.location.reload()
            }
        })
    })

    function getWriterBoardList(){

        const data = {
            page: 0,
            display: 10,
            userId: $('.g_user_id').val()
        }

        $.ajax({
            url: '/api/exchange/my/get_write_list',
            type: 'GET',
            data: data,
            success: function(response){
                $.each(response.content, function(key, value){
                    $('#myBoardItemsContainer').append(
                        "<div class='requestItemBox'>" +
                        "<div id='requestBox_" + key + "' style='padding: 15px 25px 15px 25px;'>" +
                        "<div style='width:100%; display:inline-flex'>" +
                        "<div class='item requestDate' style='width: 15%; margin: auto auto; text-align: center;'>" +
                        new Date(value.regTime).toLocaleTimeString(
                            [], {'year':'2-digit', 'month': '2-digit', 'day':'2-digit', 'hour': '2-digit', 'minute' : '2-digit'}
                        ) +
                        "</div>" +
                        "<div class='item requestThumbnail' style='text-align: center; width: 10%;'><img src=/upload/" + value.files + "></div>" +
                        "<div style='width: 60%; padding: 5px 20px 10px 15px;'>" +
                        "<div class='item requestTitle' >" + value.title + "</div>" +
                        "<div class='item requestContent' style='height: 65px'>" + value.content + "</div>" +
                        "</div>" +
                        "<div class='item _requestStatus' style='width: 15%; margin: auto 0 auto 0;'>" +
                        "<button id='writerExchangeId_" + value.writerExchangeId + "' class='actionButton showClientRequestList'>요청목록</button>" +
                        "<button class='actionButton stopRequest'>교환마감</button>" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "</div>"
                    )
                    if(key % 2 != 0) document.getElementById('requestBox_' + key).style.background = '#fcfcfc'
                })
            }
        })
    }

    function getRequestList(){

        const data = {
            page: 0,
            display: 10,
            userId: $('.g_user_id').val()
        }

        $.ajax({
            url: '/api/exchange/my/request_list',
            type: 'GET',
            data: data,
            success: function(response){
                $.each(response.content, function(key, value){
                    clientData.writerId = value.writerId;
                    clientData.boardId = value.boardId;
                    clientData.clientId = value.clientId;
                    clientData.clientExchangeId = value.clientExchangeId;
                    console.log(value)
                    $('#transactionItemsContainer').append(
                        "<div class='requestItemBox'>" +
                        "<div id='requestBox_" + key + "' style='padding: 15px 25px 15px 25px;'>" +
                        "<div style='width:100%; display:inline-flex'>" +
                        "<div class='item requestDate' style='width: 15%; margin: auto auto; text-align: center;'>" +
                        new Date(value.regTime).toLocaleTimeString(
                            [], {'year':'2-digit', 'month': '2-digit', 'day':'2-digit', 'hour': '2-digit', 'minute' : '2-digit'}
                        ) +
                        "</div>" +
                        "<div class='item requestThumbnail' style='text-align: center; width: 10%;'><img src=/upload/" + value.files + "></div>" +
                        "<div style='width: 40%; padding: 5px 20px 10px 15px;'>" +
                        "<div class='item requestTitle' >" + value.title + "</div>" +
                        "<div class='item requestContent' style='height: 65px'>" + value.content + "</div>" +
                        "</div>" +
                        "<div class='item requestUserProfile' style='margin: auto 0 auto 0; width: 20%'>" +
                        "<img style='margin-right: 10px;' src=/upload/" + value.clientProfile + ">" +
                        value.clientUsername +
                        "</div>" +
                        "<div class='item _requestStatus' style='width: 15%; margin: auto 0 auto 0;'>" +
                        "<button class='actionButton showMyRequestBoard'>내용보기</button>" +
                        "<button class='actionButton requestCancelButton'>교환취소</button>" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "</div>"
                    )
                    if(key % 2 != 0) document.getElementById('requestBox_' + key).style.background = '#fcfcfc'
                })
            }
        })
    }

    $(document).on('click', '#upDownButton', function(){
        var upDownButton = document.getElementById('upDownButton')
        if(upDownButton.className === 'requestListDownButton'){
            $('#transactionItemsContainer').show()
            upDownButton.className = 'requestListUpButton'
            upDownButton.setAttribute('src', '/images/exchange/up.png')
        }else{
            $('#transactionItemsContainer').hide()
            upDownButton.className = 'requestListDownButton'
            upDownButton.setAttribute('src', '/images/exchange/down.png')
        }
    })

    $(document).on('click', '#myBoardUpDown', function(){
        var upDownButton = document.getElementById('myBoardUpDown')
        if(upDownButton.className === 'requestListDownButton'){
            $('#myBoardItemsContainer').show()
            upDownButton.className = 'requestListUpButton'
            upDownButton.setAttribute('src', '/images/exchange/up.png')
        }else{
            $('#myBoardItemsContainer').hide()
            upDownButton.className = 'requestListDownButton'
            upDownButton.setAttribute('src', '/images/exchange/down.png')
        }
    })

</script>