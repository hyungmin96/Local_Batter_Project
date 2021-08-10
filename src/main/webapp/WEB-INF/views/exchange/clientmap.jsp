<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div class="writerInfo"style="padding: 15px; background-color: rgb(238, 240, 243);">
    <div class="container">
        <input type="hidden" class="boardId" value="${board}">
            <div class="writerBoardInfo" style="margin-top: 70px;">
                <div class="exchangeHeader">등록된 물품 정보</div>
                <div>
                    <div class="writerProductInfoBox" style="display: flex; padding: 10px;">

                        <div id="carouselExampleIndicators" class="carousel slide clientExchangeImageSlider" data-ride="carousel">
                            <ol class="carousel-indicators">
                            </ol>
                            <div class="carousel-inner">
                            </div>
                            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                                <span class="carouselButton carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                                <span class="carouselButton carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>

                        <div class="writerContent" style="width: 100%; padding: 0 5px 5px 15px; border-left: 1px solid #efefef">
                            <div class="wrtierAccountInfoBox" style="display: inline-flex">
                                <div><img class="writerProfile" src="/upload/undefined"></div>
                                <div style="display: flex"><h4 class="writerUsername">사용자 이름</h4></div>
                            </div>
                            <div class="writerBoardContentContainer" style="padding: 0 15px 15px 15px;">

                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="clientExchangeContainer" style="margin-bottom: 80px; padding: 15px; background-color: #f7f8f9;">
        <div class="container">
            <div class="exchangeHeader">교환할 물품 정보</div>
            <div style="padding: 10px;">
                    <img src="/images/upload.png" id="file_add" />
                    <input id="input_imgs" type="file" accept="image/jpg, image/jpeg, image/png"
                       class="clientImgUpload" name="upload_file" multiple style="display: none">
                    <div id="clientBoardImageContinaer"></div>

                <div class="clientCategoryBox">
                    <label class="item__value"><strong>제품 및 추가금액</strong></label>
                    <div class="col-sm-10">
                        <input type="text" name="title" class="clientExchangeInfoBox clientAddPriceBox" value=""
                               placeholder="물품에 대한 판매가격을 입력해주세요">
                    </div>
                </div>

                <div class="clientCategoryBox">
                    <label class="item__value"><strong>제품 설명</strong></label>
                    <div class="col-sm-10">
                        <input type="text" name="title" class="inputbox" value=""
                               placeholder="물품에 대한 판매가격을 입력해주세요">
                    </div>
                </div>

                <div class="clientCategoryBox">
                    <label class="item__value"><strong>제품 및 추가금액</strong></label>
                    <div class="col-sm-10">
                        <input type="text" name="title" class="clientExchangeInfoBox clientContentBox" value=""
                               placeholder="물품에 대한 판매가격을 입력해주세요">
                    </div>
                </div>

                <div class="clientCategoryBox">
                    <label class="item__value"><strong>기타 문의사항</strong></label>
                    <div class="col-sm-10">
                        <input type="text" name="title" class="clientExchangeInfoBox clientRequestBox" value=""
                               placeholder="물품에 대한 판매가격을 입력해주세요">
                    </div>
                </div>

                <div class="clientCategoryBox">
                    <label class="item__value"><strong>교환지역 설정</strong></label>
                    <div class="col-sm-10">
                        <div class="serachAddr">
                            <input type="text" name="title" id="serachAddrText" class="inputbox" placeholder="검색할 주소를 입력해주세요."/>
                            <button class="searchButton">검색</button>
                        </div>
                    </div>
                </div>
                <div id="map" style="width:600px;height:500px;"></div>

            </div>
        </div>
    </div>

    <div class="exchangeButtonContainer" style="border-top: 1px solid #ccc">
        <div class="container" style="display: flex;">
            <button style="margin: 13px 0 auto auto;" class="clientExchangeButton clientUploadButton">교환요청</button>
            <button style="margin: 13px 0 auto 10px;" class="clientExchangeButton clientCancel">취소</button>
        </div>
    </div>

<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2f665d933d93346898df736499236f77&libraries=services"></script>
<script type="text/javascript" src="/js/exchange/clientMapAddress.js"></script>
<script type="text/javascript" src="/js/exchange/mapAPI.js"></script>
<script type="text/javascript" src="/js/exchange/lineTheMap.js"></script>
<link rel="stylesheet" type="text/css" href="/css/clientExchange.css">

<script>
    $(document).ready(function(){
        const data = {
            boardId: $('.boardId').val()
        }

        $.ajax({
            url: '/api/exchange/view/board',
            type: 'GET',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            success: function (response){

                console.log(response)

                $('.writerUsername').html(response.username)
                $('.writerProfile')[0].src = '/upload/' + response.profilePath

                $(document.body).append(
                    '<input type="hidden" class="writerlongitude" value=' + response.longitude + '>' +
                    '<input type="hidden" class="writerlatitude" value=' + response.latitude + '>'
                )

                $.each(response.boardFiles, function(key, value){
                    $('.carousel-indicators').append(
                        '<li data-target="#carouselExampleIndicators" class="firstImageSlider" data-slide-to="' + key + '"></li>'
                    )
                        $('.carousel-inner').append(
                        '<div class="carousel-item" data-bs-interval="3000">' +
                            '<img class="d-block w-100" src="/upload/' + value + '">'+
                        '</div>'
                    )
                })
                document.getElementsByClassName('firstImageSlider')[0].setAttribute('class', 'firstImageSlider', 'active')
                document.getElementsByClassName('carousel-item')[0].setAttribute('class', 'carousel-item active')

                $('.writerBoardContentContainer').append(
                    "<hr />" +
                    "<div class='writerProductContent'>" +
                        response.content +
                    "</div>" +
                    "<hr />" +
                    "<div class='writerProductLocation'>" +
                        response.location +
                    "</div>" +
                    "<div id='writerLocationMap' style='width:100%; height:270px; border: 1px solid #9d9d9d'>" +

                    "</div>"
            )

                var writerMapContainer = document.getElementById('map') // 지도를 표시할 div
                var clientMapContainer = document.getElementById('writerLocationMap'), // 지도를 표시할 div
                    mapOption = {
                        center: new kakao.maps.LatLng(response.longitude, response.latitude), // 지도의 중심좌표
                        level: 6 // 지도의 확대 레벨
                    };
                var writerMap = new kakao.maps.Map(writerMapContainer, mapOption);
                var clientMap = new kakao.maps.Map(clientMapContainer, mapOption);

                setMaker(writerMap, response.residence, response.longitude, response.latitude)
                // display marker to client's map
                setMaker(clientMap, response.residence, response.longitude, response.latitude)
                // move to specific cordinate in the map
                panTo(response.longitude, response.latitude)
            }
        })
    })
</script>
