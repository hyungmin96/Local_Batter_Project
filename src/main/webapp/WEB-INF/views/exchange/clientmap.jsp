<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>로컬바터 - 지역 물품교환 및 거래</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>
<input type="hidden" class="boardId" value="${board}">

<div class="writerBoardInfo">
    <h4 class="writerUsername">사용자 이름</h4>
    <h4 class="writerProfile">사용자 프로필</h4>
    <h2>제품 이미지</h2>
    <div class="writerBoardImageContainer">

    </div>
    <h2>제품 설명</h2>
    <div class="writerBoardContentContainer">

    </div>
</div>

<div>
    <h2>교환할 제품 이미지</h2>
    <input type="file" class="clientImgUpload" multiple>
    <div id="clientBoardImageContinaer"></div>

<div>
    <h2>제품 및 추가금액</h2>
    <input type="text" class="clientAddPriceBox" value="">
</div>

</div>
    <h2>제품 설명</h2>
    <textarea class="clientContentBox"></textarea>
</div>

<div>
    <h2>기타 문의사항</h2>
    <textarea class="clientRequestBox"></textarea>
</div>

<div class="serachAddr">
    <input type="text" name="title" id="serachAddrText" class="inputbox" placeholder="검색할 주소를 입력해주세요."/>
    <button class="searchButton">검색</button>
</div>
<div id="map" style="width:600px;height:500px;"></div>
<div>
    <button class="clientUploadButton">교환요청</button>
    <button class="clientCancel">취소</button>
</div>

</body>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2f665d933d93346898df736499236f77&libraries=services"></script>
<script type="text/javascript" src="/js/clientMapAddress.js"></script>
<script type="text/javascript" src="/js/lineTheMap.js"></script>
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

                $('.writerUsername').html('response.username')
                $('.writerProfile').html('response.profilePath')

                $(document.body).append(
                    '<input type="hidden" class="writerlongitude" value=' + response.longitude + '>' +
                    '<input type="hidden" class="writerlatitude" value=' + response.latitude + '>'
                )

                $.each(response.boardFiles, function(key, value){
                    $('.writerBoardImageContainer').append(
                        "<div>" +
                            "<img src=/upload/" + value + ">" +
                        "</div>"
                    )
                })

                $('.writerBoardContentContainer').append(
                    "<div>" +
                        response.content + "<br />" +
                        response.location +
                    "</div>"
                )
                // display marker to map
                setMaker(response.residence, response.longitude, response.latitude)
                // move to specific cordinate in the map
                panTo(response.longitude, response.latitude)
            }
        })
    })
</script>
<script type="text/javascript" src="/js/mapAPI.js"></script>
</html>