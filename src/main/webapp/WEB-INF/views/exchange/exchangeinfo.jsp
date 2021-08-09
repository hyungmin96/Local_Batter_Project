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

<div class="writerExchangeImgContainer">
    <input type="file" class="upload" multiple>
</div>
<div id="previewImageContainer">

</div>

<div>
    <textarea class="contentBox"></textarea>
</div>

<div class="residenceContainer">
    <h2>거주지역 설정</h2>
    <input type="text" class="inputbox" id="sample5_address" disabled = "true" placeholder="주소">
    <div><input type="text" class="inputbox" enabled = "true" placeholder="세부주소"></div>
    <div><input type="button" class="addr__btn" onclick="sample5_execDaumPostcode()" value="주소 검색"></div>
</div>
<div id="map" style="width:300px;height:300px;margin-top:10px;display:none"></div>

<div class="exchangeLocationContainer">
<h2>거래지역 설정</h2>
    <div>
        <input type="text" class="inputbox" id="exchange_address" disabled = "true" placeholder="주소">
        <button onclick="openSerachContent('/writer/map', ''); getAddress();">검색</button>
        <div>
            <input type="hidden" class="longitudeValue" value="">
            <input type="hidden" class="latitudeValue" value="">
        </div>
    </div>
</div>

<div class="exchangeTimeContainer">
    <h2>거래 선호시간 설정</h2>
    <div>
        <input type="text" class="inputbox" id="exchange_time" enabled = "true" placeholder="선호 시간대를 입력해주세요">
    </div>
</div>

<div class="submitButtonContainer">
    <button class="saveExchangeInfoButton">저장</button>
    <button>닫기</button>
</div>

</body>
<script>
    var openWin;
    function openSerachContent(url, title) {
        openWin = window.open(url, title, "width=850, height=750, resizable = no, scrollbars = no");
    }

    function getAddress(data){
        if(data != null){
            document.getElementById('exchange_address').value = data.location;
            $('.longitudeValue')[0].value = data.longitude;
            $('.latitudeValue')[0].value = data.latitude;
        }
    }
</script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2f665d933d93346898df736499236f77&libraries=services"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script type="text/javascript" src="/js/residenceMap.js"></script>
<script type="text/javascript" src="/js/mapAPI.js"></script>
</html>