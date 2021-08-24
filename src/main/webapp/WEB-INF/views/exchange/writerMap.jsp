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
<div style="display: none" id="longitude"></div>
<div style="display: none" id="latitudeValue"></div>
<div class="serachAddr">
    <input style="width: 400px;" type="text" id="serachAddrText" class="clientExchangeInfoBox inputbox" placeholder="검색할 지역의 주소를 입력해주세요"/>
    <button class="clientExchangeButton searchButton">검색</button>
</div>
<div id="currentAddr">지역을 선택하면 해당 위치가 이곳에 표기됩니다.</div>
<div id="map" style="width:800px;height:600px;"></div>
<div>
    <button class="clientExchangeButton" onclick="getValue();">확인</button>
    <button class="clientExchangeButton" onclick="window.close();">닫기</button>
</div>
<script>
    // 값 전달
    function getValue(){
        const value = {
            location: document.getElementById('currentAddr').innerText,
            longitude: document.getElementById('longitude').innerHTML,
            latitude: document.getElementById('latitudeValue').innerHTML
        }
        window.opener.getAddress(value, 'exchange_address')
    }
</script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2f665d933d93346898df736499236f77&libraries=services"></script>
<script type="text/javascript" src="/js/exchange/registMapAddress.js"></script>
<link rel="stylesheet" type="text/css" href="/css/clientExchange.css">
</body>
</html>