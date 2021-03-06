<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<%--로그인한 사용자가 현재 진행중인 물품교환 목록을 보여줍니다--%>
<div>

    <div class="container" style="margin-top: 100px; margin-bottom: 60px;">

        <div id="transactionListContainer" style="max-height: 1600px; min-height: 801px">
            <div class="textItem _listHeader">작성한 교환 게시글</div>
            <div class="itemWrapper" style="box-shadow: 1px 1px 17px 2px rgba(0, 0, 0, 0.12); background: white; margin: 10px 15px 25px 15px;">

                <div style="height: 55px; padding: 15px; box-shadow: 0 1px 0 0 rgba(0, 0, 0, 0.12)">
                    <div class="itemHeader" style="height: 20px">
                        작성 게시글
                        <img id="myBoardUpDown" class="requestListUpButton" src="/images/exchange/up.png" style="cursor: pointer">
                    </div>
                </div>

                <div id="myBoardItemsContainer" style="overflow-y: scroll; max-height: 450px">

                </div>

            </div>

        <div class="textItem _listHeader" style="margin-top: 20px">요청한 교환 게시글</div>
            <div class="itemWrapper" style="box-shadow: 1px 1px 17px 2px rgba(0, 0, 0, 0.12); background: white; margin: 10px 15px 25px 15px;">
                <div style="height: 55px; padding: 15px; box-shadow: 0 1px 0 0 rgba(0, 0, 0, 0.12)">
                    <div class="itemHeader" style="height: 20px">
                        교환요청 목록
                        <img id="upDownButton" class="requestListUpButton" src="/images/exchange/up.png" style="cursor: pointer">
                    </div>
                </div>
                <div id="transactionItemsContainer" style="overflow-y: scroll; max-height: 450px; ">

                </div>
            </div>

            <div class="textItem _listHeader" style="margin-top: 20px">교환완료된 게시글</div>
                <div class="itemWrapper" style="box-shadow: 1px 1px 17px 2px rgba(0, 0, 0, 0.12); background: white; margin: 10px 15px 45px 15px;">
                    <div style="height: 55px; padding: 15px; box-shadow: 0 1px 0 0 rgba(0, 0, 0, 0.12)">
                        <div class="itemHeader" style="height: 20px">
                            교환요청 목록
                            <img id="exchangedUpDownButton" class="requestListUpButton" src="/images/exchange/up.png" style="cursor: pointer">
                        </div>
                    </div>
                    <div id="exchangedItemsContainer" style="overflow-y: scroll; max-height: 450px; ">

                    </div>
                </div>
            </div>

    </div>
</div>

<!-- review write modal -->
<div class="modal fade" id="reviewWriterModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top: 10%; margin-left: 33%;">
        <div class="modal-content" style="width: 600px; border-radius: 0;">
            <div class="modal-header" style="border: none;">
                <div class="modal-title" id="reviewWrite" style="margin: 0 0 0 auto">교환후기 작성</div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" style="width: 20px; height: 20px;"></button>
            </div>
            <div class="reviewWriteModal" style="width: 600px; height: 100%; padding: 0.5rem;">
                <div class="container" style="padding: 10px;">
                    <div class="targetUserWrapper" style="display: flex; flex-direction: row">
                        <div class="targetUserProfileImageBox">
                            <img class="targetUserProfileImage" src="" style="border-radius: 50%; margin: auto 0 auto 0; width: 45px; height: 45px; object-fit: cover">
                        </div>
                        <div class="targetUsername" style="margin: auto 0 auto 0; padding: 5px;">유저 아이디</div>
                    </div>
                    <div style="display: flex; flex-direction: row">
                        <div style="margin: auto 0 auto 0">매너점수</div>
                        <div class="rating" style="margin: 10px 0 auto 0; padding: 1px 10px 0 10px">
                            <input type="hidden" name="rate" id="rate" value="0"/>
                            <input type="checkbox" name="rating" id="rating1" value="1" class="rate_radio" title="1점">
                            <label for="rating1"></label>
                            <input type="checkbox" name="rating" id="rating2" value="2" class="rate_radio" title="2점">
                            <label for="rating2"></label>
                            <input type="checkbox" name="rating" id="rating3" value="3" class="rate_radio" title="3점" >
                            <label for="rating3"></label>
                            <input type="checkbox" name="rating" id="rating4" value="4" class="rate_radio" title="4점">
                            <label for="rating4"></label>
                            <input type="checkbox" name="rating" id="rating5" value="5" class="rate_radio" title="5점">
                            <label for="rating5"></label>
                        </div>
                    </div>
                    <div>
                        <textarea class="reviewContentBox" rows="5" placeholder="후기내용을 입력해주세요" style="padding: 10px;"></textarea>
                    </div>
                    <div>
                        <button class="btn-primary float-right submitReviewButton">후기작성</button>
                    </div>
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
                <div class="modal-title" id="clientRequestModal" style="margin: 0 0 0 auto">교환요청 목록</div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close" style="width: 20px; height: 20px;"></button>
            </div>
            <div class="clientBoardListModal clientRequestModal" style="width: 1100px; height: 586px; padding: 0.5rem;">

                <div class="modalColumn" style="display: inline-flex">
                    <div style="width: 160px">작성자</div>
                    <div style="width: 160px">물품 이미지</div>
                    <div style="width: 310px">물품 이름</div>
                    <div style="width: 235px">위치</div>
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

<div class="modal fade show" id="writerBoardModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-modal="true" role="dialog" style="display: none;">
    <div class="modal-dialog" style="margin: 100px auto 20px auto; width: 800px;">
        <div class="modal-content" style="width: 600px;">
            <div class="modal-header" style="border: none;">
                <div class="modal-title" id="writerBoardModalTitle" style="margin: 0 0 0 auto">작성한 게시글 정보</div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" style="padding: 0.5rem 0.5rem 0 0.5rem">
                <div class="boardModal" style="padding: 0.5rem;">

                </div>
            </div>
        </div>
    </div>
</div>

<!-- image view modal -->
<div id="imgModal" style="padding: 20px; display: none; width: 1300px; height: 820px;">
    <div class = "modal_close_btn"><img src="/images/delete_35px.png" style="width: 30px; height: 30px;float: right; cursor: pointer;"></div>

    <div id="carouselExampleControls" class="carousel" data-interval="10000000" data-bs-ride="carousel">
        <div class="carousel-inner">

        </div>
        <div>
            <button class="orderButton carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
        </div>
        <div>
            <button class="orderButton carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>

    <div class="imgviewer _page">
        <span class="number _currentPageNumber"></span> /
        <span class="number _lastPageNumber"></span>
    </div>

</div>

<%@ include file="../common/footer.jsp" %>
<script type="text/javascript" src="/js/group/groupClickEvents.js"></script>
<script type="text/javascript" src="/js/group/groupAPis.js"></script>
<script type="text/javascript" src="/js/imgModal.js"></script>
<link rel="stylesheet" href="/css/imgGridGallery.css">
<link rel="stylesheet" href="/css/imgModal.css">
<link rel="stylesheet" href="/css/buyingroom.css">

<script>
    $(document).ready(function (){
        setTimeout(function(){
            getWriterWaitBoard()
            getWriterCompleteBoard()
        }, 100)
        getRequestList()
    })

    // global varialbes
    const clientRequestVariable = {
        requestClientToBoardId: '', // 교환요청을 받은 특정게시글 id
        totalPages: '',
        pageArray: []
    }

    // 리뷰 작성 modal

    const reviewInfoObject = {
        reviewWriterId: $('.g_user_id').val(),
        reviewReceiveId: '',
        reviewReceiveUsername: '',
        reviewReceiveProfile: '',
        writerClientJoinId: '',
        content: '',
        score: ''
    }

    $(document).on('click', '.reviewButton', function (){
        $('#reviewWriterModal').modal('show')
        var reviewIndex = $('.reviewButton').index(this)
        reviewInfoObject.reviewReceiveId = ($('.g_user_id').val() == $('.reviewWriterUserId')[reviewIndex].value) ? $('.reviewReceiveUserId')[reviewIndex].value : $('.reviewWriterUserId')[reviewIndex].value
        reviewInfoObject.writerClientJoinId = $('.reviewWriterClientJoinId')[reviewIndex].value
        reviewInfoObject.reviewReceiveProfile = ($('.g_user_id').val() == $('.reviewWriterUserId')[reviewIndex].value) ? $('.reviewReceiveProfile')[reviewIndex].value : $('.reviewWriterProfile')[reviewIndex].value
        reviewInfoObject.reviewReceiveUsername = ($('.g_user_id').val() == $('.reviewWriterUserId')[reviewIndex].value) ? $('.reviewReceiveUserId')[reviewIndex].value : $('.reviewWriterUserId')[reviewIndex].value
        $('.targetUserProfileImage')[reviewIndex].src = '/upload/' + reviewInfoObject.reviewReceiveProfile
        $('.targetUsername')[reviewIndex].innerHTML = reviewInfoObject.reviewReceiveUsername
    })

    $(document).on('click', '.submitReviewButton', ()=>{

        reviewInfoObject.content = $('.reviewContentBox').val()
        reviewInfoObject.score = $('#rate').val()

        $.ajax({
            url: '/api/exchange/write/review',
            type: 'POST',
            data: reviewInfoObject,
            contentType: 'application/x-www-form-urlencoded',
            success: (response) =>{
                alert('교환후기를 작성하였습니다.')
                location.reload()
            }
        })
    })

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

    // 교환요청목록 보기
    $(document).on('click', '.showRequestDetailButton', function(){
        var clientWriterExchangeId = $(this).attr('id').split('_')[1]
        window.open('/view/client/request/' + clientWriterExchangeId, '_blank')
    })

    // writer가 작성한 게시글 내용 조회
    $(document).on('click', '.viewWriterBoardBox', function(){
        $('#writerBoardModal').modal('show')
        var boardId = $(this).attr('id').split('_')[1]

        const data = {
            groupId: $('.groupId').val(),
            boardId: boardId
        }

        $.ajax({
            url: '/api/v2/group/board/getInfo',
            type: 'POST',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            success: function (response){
                $('.boardModal').empty()
                $('.boardModal').append(showBoardInfo(response[0], false))
            }
        })
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
                        "<button id='clientWriterExchangeId_" + value.exchangeId + "' class='actionButton showRequestDetailButton'>보기</button>" +
                        "<button id='clientExchangeId_" + value.clientExchange.id + "' class='actionButton rejectRequestButton'>삭제</button>" +
                        "</div>" +
                        "</div>" +
                        "<hr style='border:none; background: #cccccc; height: 1px; margin: 0 30px 0 30px;' />" +
                        "</div>"
                    )
                })
            }
        })
    }

    // writer 교환이 완료되지 않은 작성글 목록 조회
    function getWriterWaitBoard(){
        const data = {
            page: 0,
            display: 10,
            userId: $('.g_user_id').val(),
            status: 'wait'
        }

        $.ajax({
            url: '/api/exchange/my/get_write_list',
            type: 'GET',
            data: data,
            success: function(response){
                $.each(response.content, function(key, value){
                    $('#myBoardItemsContainer').append(appendBoardToContainer(key, value))
                    if(key % 2 != 0) document.getElementById('requestBox_' + key).style.background = '#fcfcfc'
                })
            }
        })
    }

    // writer 교환이 완료된 작성글 목록 조회
    function getWriterCompleteBoard(){
        const data = {
            page: 0,
            display: 10,
            userId: $('.g_user_id').val(),
            status: 'complete'
        }

        $.ajax({
            url: '/api/exchange/my/get_complete_list',
            type: 'GET',
            data: data,
            success: function(response){
                $.each(response.content, function(key, value){
                    console.log(value)
                    $('#exchangedItemsContainer').append(appendBoardToContainer(key, value, 'complete'))
                    if(key % 2 != 0) document.getElementById('requestBox_' + key).style.background = '#fcfcfc'
                })
            }
        })
    }

    // writer 작성글 목록을 그리는 함수
    function appendBoardToContainer(key, value, type = 'wait'){

        let displayCountValue
        displayCountValue = (value.requestCount > 0) ? 'position-relative' : ''
        var isReviewButton = (value.isReviewWrite != null) ? 'disabled' : ''
        var actionButtonContainer

        if(type == 'wait'){
            actionButtonContainer = "<div class='item _requestStatus' style='text-align:center; width: 15%; margin: auto 0 auto 0;'>" +
                '<button id="writerExchangeId_' + value.writerExchangeId + '" type="button" class="' + displayCountValue + ' actionButton showClientRequestList">요청목록' +
                '<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">' +
                value.requestCount +
                "</span>" +
                '</button>' +
                "<button class='actionButton stopRequest'>교환마감</button>" +
                "</div>"
        }else{
            actionButtonContainer = "<input type='hidden' class='reviewWriterUserId' value='" + value.writerId + "'>" +
                "<input type='hidden' class='reviewReceiveUserId' value='" + value.clientId + "'>" +
                "<input type='hidden' class='reviewWriterClientJoinId' value='" + value.writerClientJoinId + "'>" +
                "<input type='hidden' class='reviewReceiveProfile' value='" + value.reviewReceiveProfile + "'>" +
                "<input type='hidden' class='reviewReceiveUsername' value='" + value.reviewReceiveUsername + "'>" +
                "<input type='hidden' class='reviewWriterProfile' value='" + value.reviewWriterProfile + "'>" +
                "<input type='hidden' class='reviewWriterUsername' value='" + value.reviewWriterUsername + "'>" +
                "<button " + isReviewButton + " id='reviewWriterClientId_" + value.writerClientJoinId + "' style='height: 37px; margin: auto;' class='actionButton reviewButton'>리뷰작성</button>"
        }

        return "<div class='requestItemBox'>" +
                "<div id='requestBox_" + key + "' style='padding: 15px 25px 15px 25px;'>" +
                "<div style='width:100%; display:inline-flex'>" +
                "<div class='item requestDate' style='width: 15%; margin: auto auto; text-align: center;'>" +
                new Date(value.regTime).toLocaleTimeString([], {
                    'year':'2-digit',
                    'month': '2-digit',
                    'day':'2-digit',
                    'hour': '2-digit',
                    'minute' : '2-digit'
                }) +
                "</div>" +
                "<div class='item requestThumbnail' style='display: flex; text-align: center; width: 10%;'><img src=/upload/" + value.thumbnail + "></div>" +
                "<div id='writerBoardBoxId_" + value.boardId + "' class='viewWriterBoardBox' style='cursor: pointer; width: 60%; padding: 5px 20px 10px 15px;'>" +
                "<div class='item requestTitle' >" + value.title + "</div>" +
                "<div class='item requestContent' style='height: 65px'>" + value.content + "</div>" +
                "</div>" +
                actionButtonContainer +
                "</div>" +
                "</div>" +
                "</div>"
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
                        "<div class='item requestThumbnail' style='display:flex; text-align: center; width: 10%;'><img src=/upload/" + value.thumbnail + "></div>" +
                        "<div style='cursor: pointer; width: 40%; padding: 5px 20px 10px 15px;'>" +
                        "<div class='item requestTitle' >" + value.title + "</div>" +
                        "<div class='item requestContent' style='height: 65px'>" + value.content + "</div>" +
                        "</div>" +
                        "<div class='item requestUserProfile' style='text-align: center; margin: auto 0 auto 0; width: 20%'>" +
                        "<img style='margin-right: 10px;' src=/upload/" + value.clientProfile + ">" +
                        value.clientUsername +
                        "</div>" +
                        "<div class='item _requestStatus' style='text-align: center; width: 15%; margin: auto 0 auto 0;'>" +
                        "<button id='clientShowExhchangeId_" + value.clientExchangeId + "' class='actionButton showMyRequestBoard'>보기</button>" +
                        "<button id='clientCancelExhchangeId_" + value.clientExchangeId + "' class='actionButton requestCancelButton'>취소</button>" +
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

    // 교환 요청한 게시글 보기
    $(document).on('click', '.showMyRequestBoard', ()=>{
        const exchangeId = $('.showMyRequestBoard').attr('id').split('_')[1]
        window.open('/view/client/confirm/exchange/' + exchangeId, '_blank')
    })

    // 교환요청 글 삭제
    $(document).on('click', '.requestCancelButton, .rejectRequestButton', function () {
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
                if($(this)[0].id.indexOf('clientCancelExhchangeId') >= 0)
                    $(this).closest('.requestItemBox').remove()
                else
                    $(this).closest('.clientRequestItemBox').remove()
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

    $(document).on('click', '#exchangedUpDownButton', function(){
        var upDownButton = document.getElementById('exchangedUpDownButton')
        if(upDownButton.className === 'requestListDownButton'){
            $('#exchangedItemsContainer').show()
            upDownButton.className = 'requestListUpButton'
            upDownButton.setAttribute('src', '/images/exchange/up.png')
        }else{
            $('#exchangedItemsContainer').hide()
            upDownButton.className = 'requestListDownButton'
            upDownButton.setAttribute('src', '/images/exchange/down.png')
        }
    })

    function Rating(){};
    Rating.prototype.rate = 0;
    Rating.prototype.setRate = function(newrate){
        //별점 마킹 - 클릭한 별 이하 모든 별 체크 처리
        this.rate = newrate;
        let items = document.querySelectorAll('.rate_radio');
        var score = 0
        items.forEach(function(item, idx){
            if(idx < newrate){
                item.checked = true;
                score = idx
            }else{
                item.checked = false;
            }
        });
        $('#rate')[0].value = Number(score + 1)
    }
    let rating = new Rating();

    document.addEventListener('DOMContentLoaded', function(){
        document.querySelector('.rating').addEventListener('click',function(e){
            let elem = e.target;
            if(elem.classList.contains('rate_radio')){
                rating.setRate(parseInt(elem.value));
            }
        })
    });
</script>
<link rel="stylesheet" href="/css/transaction.css" />
