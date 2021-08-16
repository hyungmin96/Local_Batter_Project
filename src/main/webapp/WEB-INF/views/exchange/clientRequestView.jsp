<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div class="writerInfo"style="padding: 15px; background-color: rgb(238, 240, 243);">
    <div class="container">

        <input type="hidden" class="clientWriterexchangeId" value="${clientId}">

        <input type="hidden" class="boardId" value="${board}">
        <input type="hidden" class="writerId" value="${writerId}">
            <div class="writerBoardInfo" style="margin-top: 70px;">
                <div class="exchangeHeader">게시된 물품 정보</div>
                    <div>
                        <div class="writerProductInfoBox" style="display: flex; padding: 10px;">
                            <div id="carouselExampleIndicators" class="carousel slide clientExchangeImageSlider" data-ride="carousel">
                                <ol class="writerIndications carousel-indicators">
                                </ol>
                                <div class="writerCarousel carousel-inner">
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

                            <div class="writerContent" style="width: 100%; padding: 0 5px 10px 36px; border-left: 1px solid #efefef">
                                <div class="wrtierAccountInfoBox" style="display: inline-flex">
                                    <div><img class="userProfile writerProfile" src="/upload/undefined"></div>
                                    <div style="display: flex"><h4 class="writerUsername writerBoardUsername">사용자 이름</h4></div>
                                </div>
                                <div class="writerBoardContentContainer" style="height: 290px; padding: 15px 15px 5px 10px;">

                                </div>
                                <div id="writerLocationMap" style="width:100%; height:270px; border: 1px solid #9d9d9d"></div>
                            </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="clientExchangeContainer" style="margin-bottom: 80px; padding: 15px; background-color: #f7f8f9;">
        <div class="container">
            <div class="writerBoardInfo" style="margin-top: 70px;">
            <div class="exchangeHeader">교환요청한 물품 정보</div>
                <div>
                    <div class="writerProductInfoBox" style="display: flex; padding: 10px;">
                        <div id="clientImageIndicators" class="carousel slide clientExchangeImageSlider" data-ride="carousel">
                            <ol class="clientIndications carousel-indicators">
                            </ol>
                            <div class="clientCarousel carousel-inner">
                            </div>
                            <a class="carousel-control-prev" href="#clientImageIndicators" role="button" data-slide="prev">
                                <span class="carouselButton carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="carousel-control-next" href="#clientImageIndicators" role="button" data-slide="next">
                                <span class="carouselButton carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>

                        <div class="clientContent" style="width: 100%; padding: 0 5px 10px 36px;">
                            <div class="clientAccountInfoBox" style="display: inline-flex">
                                <div><img class="userProfile clientProfile" src="/upload/undefined"></div>
                                <div style="display: flex"><h4 class="writerUsername clientBoardUsername">사용자 이름</h4></div>
                            </div>
                            <div class="clientBoardContentContainer" style="height: 290px; padding: 15px 15px 5px 10px;">

                            </div>
                            <div id="clientLocationMap" style="width:100%; height:270px; border: 1px solid #9d9d9d"></div>
                        </div>
                    </div>
                </div>
            </div>

        </div>
    </div>

    <div class="exchangeButtonContainer" style="border-top: 1px solid #ccc">
        <div class="container" style="display: flex;">
            <button style="margin: 13px 0 auto auto;" class="clientExchangeButton acceptRequestButton">교환수락</button>
            <button style="margin: 13px 0 auto 10px;" class="clientExchangeButton closeButton">닫기</button>
        </div>
    </div>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2f665d933d93346898df736499236f77&libraries=services"></script>
<link rel="stylesheet" type="text/css" href="/css/clientExchange.css">
<script>
    $('.closeButton').click(function(){
        window.close();
    })
    $('.acceptRequestButton').click(function(){
        acceptClientExchangeRequest()
    })
</script>
<script>
    function setMapConainer(container, location, lng, lat){
        var mapContainer = document.getElementById(container), // 지도를 표시할 div
            mapOption = {
                center: new kakao.maps.LatLng(lng, lat), // 지도의 중심좌표
                level: 6 // 지도의 확대 레벨
            };

        var map = new kakao.maps.Map(mapContainer, mapOption);
        var geocoder = new kakao.maps.services.Geocoder();
        var marker = new kakao.maps.Marker(), // 클릭한 위치를 표시할 마커입니다
            infowindow = new kakao.maps.InfoWindow({zindex:1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

        infowindow = new kakao.maps.InfoWindow({zindex:1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

        function panTo(lng, lat) {
            // 이동할 위도 경도 위치를 생성합니다
            var moveLatLon = new kakao.maps.LatLng(lng, lat);
            // 지도 중심을 부드럽게 이동시킵니다
            // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
            map.panTo(moveLatLon);
        }
        let markerPosition  = new kakao.maps.LatLng(lng, lat);
        let setMarker = new kakao.maps.Marker({
            position: markerPosition
        });
        setMarker.setMap(map);
        var iwContent = '<div class="windowInfoMarker" style="padding:5px;">' + location + '<br>' +
                '<a href="https://map.kakao.com/link/to/' + location + ',' + lng + ',' + lat + '" style="color:blue" target="_blank">길찾기</a></div>',// 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
            iwPosition = new kakao.maps.LatLng(lng, lat); //인포윈도우 표시 위치입니다

        // 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
            position : iwPosition,
            content : iwContent,
        });
        infowindow.open(map, setMarker);

    }

</script>
<script>

    // writer가 client의 교환요청을 수락
    function acceptClientExchangeRequest(){

        const data = {
            clientWriterExchangeId: $('.clientWriterexchangeId').val()
        }

        $.ajax({
            url: '/api/exchange/select/request',
            type: 'POST',
            data: data,
            success: function(response){
                console.log(response)
            }
        })
    }

    $(document).ready(function(){

        const data = {
            userId: $('g_user_id').val(),
            clientWriterExchangeId: $('.clientWriterexchangeId').val()
        }

        $.ajax({
            url: '/api/exchange/view/client_writer_exchange',
            type: 'GET',
            data: data,
            success: function(response){
                console.log(response)
                // writer request info
                $('.writerBoardUsername').html(response.writerUsername)
                $('.writerId')[0].value = response.writerId // writer Id
                $('.writerProfile')[0].src = '/upload/' + response.writerProfile

                $(document.body).append(
                    '<input type="hidden" class="writerlongitude" value=' + response.longitude + '>' +
                    '<input type="hidden" class="writerlatitude" value=' + response.latitude + '>'
                )

                $.each(response.writerImages, function(key, value){
                    $('.writerIndications').append(
                        '<li data-target="#carouselExampleIndicators" class="firstImageSlider" data-slide-to="' + key + '"></li>'
                    )
                    $('.writerCarousel').append(
                        '<div class="carousel-item" data-bs-interval="3000">' +
                        '<img class="d-block w-100" src="/upload/' + value + '">'+
                        '</div>'
                    )
                })
                if(response.writerImages.length > 0){
                    document.getElementsByClassName('firstImageSlider')[0].setAttribute('class', 'firstImageSlider', 'active')
                    document.getElementsByClassName('carousel-item')[0].setAttribute('class', 'carousel-item active')
                }

                $('.writerBoardContentContainer').append(
                    "<div class='writerProductTitle'>" +
                    response.title +
                    "</div>" +
                    "<div class='writerProductContent'>" +
                    response.content +
                    "</div>" +
                    "<div class='writerProductPrice'>" +
                    convert(response.price) +
                    " 원</div>"
                )
                setMapConainer('writerLocationMap', response.writerExchangeEntity.location, response.writerExchangeEntity.longitude, response.writerExchangeEntity.latitude)

                // client request info
                $('.clientBoardUsername').html(response.clientUsername)
                $('.clientProfile')[0].src = '/upload/' + response.clientProfile

                $(document.body).append(
                    '<input type="hidden" class="clientlongitude" value=' + response.clientExchangeEntity.longitude + '>' +
                    '<input type="hidden" class="clientlatitude" value=' + response.clientExchangeEntity.latitude + '>'
                )

                $.each(response.clientImages, function(key, value){
                    $('.clientIndications').append(
                        '<li data-target="#clientImageIndicators" class="clientImageSlider" data-slide-to="' + key + '"></li>'
                    )
                    $('.clientCarousel').append(
                        '<div class="clientCarouselItem carousel-item" data-bs-interval="3000">' +
                        '<img class="d-block w-100" src="/upload/' + value + '">'+
                        '</div>'
                    )
                })
                if(response.clientImages.length > 0){
                    document.getElementsByClassName('clientImageSlider')[0].setAttribute('class', 'firstImageSlider', 'active')
                    document.getElementsByClassName('clientCarouselItem')[0].setAttribute('class', 'carousel-item active')
                }

                $('.clientBoardContentContainer').append(
                    "<div class='writerProductTitle'>" +
                    response.clientExchangeEntity.title +
                    "</div>" +
                    "<div class='writerProductContent'>" +
                    response.clientExchangeEntity.content +
                    "</div>" +
                    "<div class='writerProductPrice'>" +
                    convert(response.clientExchangeEntity.price) +
                    " 원</div>"
                )
                setMapConainer('clientLocationMap', response.clientExchangeEntity.address, response.clientExchangeEntity.longtitude, response.clientExchangeEntity.latitude)
            }
        })

    })

    function convert(num){
        var len, point, str;
        num = num + "";
        point = num.length % 3 ;
        len = num.length;
        str = num.substring(0, point);
        while (point < len) {
            if (str != "") str += ",";
            str += num.substring(point, point + 3);
            point += 3;
        }
        return str;
    }

</script>
