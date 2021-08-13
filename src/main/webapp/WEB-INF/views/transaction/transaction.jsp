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

                <div id="myBoardItemsContainer" style="overflow-y: scroll; max-height: 450px;margin-top: 5px;">
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

            <div id="transactionItemsContainer" style="overflow-y: scroll; max-height: 450px;margin-top: 5px;">
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
            <div class="boardModal clientRequestModal" style="width: 1100px; height: 586px; padding: 0.5rem;">

                <div class="modalColumn" style="display: inline-flex">
                    <div style="width: 160px">작성자</div>
                    <div style="width: 160px">물품 이미지</div>
                    <div style="width: 310px">물품 이름</div>
                    <div style="width: 210px">문의사항</div>
                    <div style="width: 180px"></div>
                </div>

                <div class="requestBoardItemContainer">

                </div>

                <div id="pageContainer" style="margin-top: 7px;">
                    <input type="hidden" class="totalPage" data-total-page="">
                    <input type="hidden" class="currentPage" data-current-page="1">

                    <div class="requestModalPageBox">
                        <button class="pageMoveButton previusPageButton"><img src="/images/exchange/left.png"></button>
                        <div class="pagenumberList">

                        </div>
                        <button class="pageMoveButton nextPageButton"><img src="/images/exchange/right.png"></button>
                    </div>

                </div>

            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>

<link rel="stylesheet" text="javascript/css" href="/css/transaction.css">
<script>
    $(document).ready(function (){
        setTimeout(function(){
            getWriterBoardList();
        }, 100);
        getRequestList();
    })

    // global varialbes
    const clientRequestVariable = {
        requestClientToBoardId: '', // 교환요청을 받은 특정게시글 id
        totalPages: '',
        pageArray: []
    }


    $('.nextPageButton').click(function () {
        if (Number($('.currentPage').attr('data-current-page')) < clientRequestVariable.pageArray.length){
            var currentPage = Number($('.currentPage').attr('data-current-page'))
            clientRequestVariable.pageArray.forEach(function (item) {
                if (item == clientRequestVariable.pageArray[currentPage])
                    item.style.display = 'inline-flex'
                else
                    item.style.display = 'none'
            })
            $('.currentPage')[0].setAttribute('data-current-page', currentPage + 1)
        }
    })

    $('.previusPageButton').click(function () {
        if (Number($('.currentPage').attr('data-current-page')) > 1){
            var currentPage = Number($('.currentPage').attr('data-current-page'))
            clientRequestVariable.pageArray.forEach(function (item) {
                if (item == clientRequestVariable.pageArray[currentPage - 2])
                    item.style.display = 'inline-flex'
                else
                    item.style.display = 'none'
            })
            $('.currentPage')[0].setAttribute('data-current-page', currentPage - 1)
        }
    })

    function pageArrayGenerater(inputPageContainer, totalPages){
        clientRequestVariable.pageArray = []
        for(let i = 0; i < totalPages; i++){
            var pageStep
            if(i % 5 == 0) {
                pageStep = document.createElement('div')
                pageStep.setAttribute('id', 'page_' + i)
                pageStep.setAttribute('class', 'pageStep')
                pageStep.style.width = '100%'
                pageStep.style.alignItems = 'center'
                if(i == 0)
                    pageStep.style.display = 'inline-flex'
                else
                    pageStep.style.display = 'none'

                $('.' + inputPageContainer).append(pageStep)
                clientRequestVariable.pageArray.push(pageStep)
            }
            $('#' + pageStep.getAttribute('id')).append("<div class='pageValue'>" + (i + 1) + "</div>")
        }
    }

    // client 게시글 목록 페이지 조회
    $(document).on('click', '.pageValue', function(){
        var pageValue = ($(this)[0].innerHTML * 1) - 1 // page는 0부터 시작하므로 -1
        getBoardClientRequest(pageValue)
        $.each($('.pageValue'), function(item){
            if(pageValue == item)
                $('.pageValue')[item].style.background = '#ececec'
            else
                $('.pageValue')[item].style.background = ''
        })
    })

    // 특정 게시글에 교환요청한 client 게시글 목록 조회
    $(document).on('click', '.showClientRequestList', function (){
        $('#requestListModal').modal('show')
        clientRequestVariable.requestClientToBoardId = $(this).attr('id').split('_')[1]
        $('.pagenumberList').empty()
        getBoardClientRequest()
        pageArrayGenerater('pagenumberList', clientRequestVariable.totalPages)
    })

    // 특정 게시글에 교환요청한 client 게시글 목록 조회
    var clientRequestPage = 0
    function getBoardClientRequest(clientRequestPage = 0){
        const data = {
            page: clientRequestPage,
            display: 5,
            userId: $('.g_user_id').val(),
            boardId: clientRequestVariable.requestClientToBoardId
        }

        $.ajax({
            url: '/api/exchange/view/board/client_reqeust_list',
            type:'GET',
            async: false,
            data: data,
            success: function(response){
                $('.requestBoardItemContainer').empty()
                $('.totalPage').attr('data-total-page', response.totalPages)
                clientRequestVariable.totalPages = response.totalPages
                console.log(response)

                $.each(response.content, function(key, value){
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
                        "<div style='margin: auto 10px auto; width: 240px;' class='clientRequestLocationField'>" +
                        value.clientExchange.address +
                        "</div>" +
                        "<div style='margin: auto 10px auto -10px; width: 156px;' class='clientRequestField'>" +
                        "<button class='actionButton showRequestDetailButton'>보기</button>" +
                        "<button id='clientExchangeId_" + value.clientExchange.id + "' class='actionButton rejectRequestButton'>삭제</button>" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "<hr style='border:none; background: #cccccc; height: 1px; margin: 0 30px 0 30px;' />"
                    )
                })
            }
        })
    }

    // writer 작성글 목록 조회
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
                        '<button id="writerExchangeId_' + value.writerExchangeId + '" type="button" class="actionButton showClientRequestList position-relative">요청목록' +
                        '<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">' +
                            value.reqeustCount +
                        '<span class="visually-hidden">unread messages</span>' +
                        "</span>" +
                        '</button>' +
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
                        "<button id='clientShowExhchangeId_" + value.clientExchangeId + "' class='actionButton showMyRequestBoard'>내용보기</button>" +
                        "<button id='clientCancelExhchangeId_" + value.clientExchangeId + "' class='actionButton requestCancelButton'>교환취소</button>" +
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

    // 교환요청 글 삭제
    $(document).on('click', '.requestCancelButton, .rejectRequestButton', function () {
        console.log('run')
        const data = {
            // clientId: clientData.clientId,
            clientExchangeId: $(this).attr('id').split('_')[1]
        }
        $.ajax({
            url: '/api/exchange/cancel/request',
            type: 'POST',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            success: function(response){
                alert('교환요청을 삭제하였습니다.')
                $(this).remove()
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