<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<%--로그인한 사용자가 현재 진행중인 물품교환 목록을 보여줍니다--%>
<div>

    <div class="container" style="margin-top: 100px;">

        <div id="transactionListContainer" style="max-height: 1600px; min-height: 801px;">
            <div class="textItem _listHeader">내가 작성한 교환 글</div>
            <div class="itemWrapper" style="box-shadow: 1px 1px 17px 2px rgba(0, 0, 0, 0.12); background: white; margin: 10px 15px 5px 15px;">

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

        <div class="textItem _listHeader" style="margin-top: 20px">내가 요청한 교환 글</div>
        <div class="itemWrapper" style="box-shadow: 1px 1px 17px 2px rgba(0, 0, 0, 0.12); background: white; margin: 10px 15px 5px 15px;">

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


<%@ include file="../common/footer.jsp" %>

<link rel="stylesheet" text="javascript/css" href="/css/transaction.css">
<script src="/js/modal.js"></script>
<script>

    const clientData = {
        clientId: '', // user id
        boardId: '',
        writerId: '', // writer id
        clientExchangeId: ''
    }

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

    $(document).ready(function(){

        const data = {
            pageNum: 0,
            pageSize: 10,
            userId: $('.g_user_id').val()
        }

        $.ajax({
            url: '/api/exchange/my/get_write_list',
            type: 'GET',
            data: data,
            success: function(response){
                console.log(response)

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
                        "<div class='item requestThumbnail' style='text-align: center; width: 10%;'><img src=/upload/" + value.files[0] + "></div>" +
                        "<div style='width: 40%; padding: 5px 20px 10px 15px;'>" +
                        "<div class='item requestTitle' >" + value.title + "</div>" +
                        "<div class='item requestContent' style='height: 65px'>" + value.content + "</div>" +
                        "</div>" +
                        "<div class='item requestUserProfile' style='margin: auto 0 auto 0; width: 20%'>" +
                        "<img style='margin-right: 10px;' src=/upload/" + value.userProfile + ">" +
                        value.username +
                        "</div>" +
                        "<div class='item _requestStatus' style='width: 15%; margin: auto 0 auto 0;'>" +
                        "<button class='actionButton showClientRequestList'>요청보기</button>" +
                        "<button class='actionButton stopRequest'>마감하기</button>" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "</div>"
                    )
                    if(key % 2 != 0) document.getElementById('requestBox_' + key).style.background = '#fcfcfc'
                })
            }
        })

    })

    $(document).ready(function(){

        const data = {
            pageNum: 0,
            pageSize: 10,
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

                    var statsButton = (value.status === 'process') ? "<button class='actionButton requestShowButton'>보기</button>" : ''

                    $('#transactionItemsContainer').append(
                        "<div class='requestItemBox'>" +
                        "<div id='requestBox_" + key + "' style='padding: 15px 25px 15px 25px;'>" +
                        "<div style='width:100%; display:inline-flex'>" +
                        "<div class='item requestDate' style='width: 15%; margin: auto auto; text-align: center;'>" +
                        new Date(value.regTime).toLocaleTimeString(
                            [], {'year':'2-digit', 'month': '2-digit', 'day':'2-digit', 'hour': '2-digit', 'minute' : '2-digit'}
                        ) +
                        "</div>" +
                        "<div class='item requestThumbnail' style='text-align: center; width: 10%;'><img src=/upload/" + value.files[0] + "></div>" +
                        "<div style='width: 40%; padding: 5px 20px 10px 15px;'>" +
                        "<div class='item requestTitle' >" + value.title + "</div>" +
                        "<div class='item requestContent' style='height: 65px'>" + value.content + "</div>" +
                        "</div>" +
                        "<div class='item requestUserProfile' style='margin: auto 0 auto 0; width: 20%'>" +
                        "<img style='margin-right: 10px;' src=/upload/" + value.clientProfile + ">" +
                        value.clientUsername +
                        "</div>" +
                        "<div class='item _requestStatus' style='width: 15%; margin: auto 0 auto 0;'>" +
                        statsButton +
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

    })

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