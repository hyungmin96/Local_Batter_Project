<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div class="container__write">
    <div id="content__box" class="container">
        <form action="/upload" method="post" class="was-validated" encrtype="multipart/form-data">

            <label for="inputTitle"><strong>상품 이미지 등록</strong></label>
                <div>
                    <div class="upload-info">
                        <div class="upload-btn">
                            <img src="/images/upload.png" id="file_add" />
                            <input id="input_imgs" type="file" accept="image/jpg, image/jpeg, image/png"
                                class="custom-file-input" name="upload_file" multiple style="width: 50px;">
                        </div>
                        
                        <div class="write-info" id="info">
                            <b>* 상품 이미지는 640x640에 최적화 되어 있습니다.</b>
                            <br>- 이미지는 상품등록 시 정사각형으로 짤려서 등록됩니다.<br>
                            - 이미지를 클릭 할 경우 원본이미지를 확인할 수 있습니다.<br>
                            - 이미지를 클릭 후 이동하여 등록순서를 변경할 수 있습니다.<br>
                            - 큰 이미지일경우 이미지가 깨지는 경우가 발생할 수 있습니다.
                            <br>
                        </div>
                    </div>
                </div>
            <br>

            <label for="inputTitle"><strong>물품 이미지 미리보기</strong></label>
            <div id="previewBoard" class="previewBoard"></div>
            <hr />

                <div class="article__item__box">
                    <label for="inputTitle" class="item__value"><strong>제목</strong></label>
                    <div class="col-sm-10">
                        <input type="text" name="title" id="inputTitle" class="inputbox" placeholder="등록할 상품의 이름을 입력해주세요." />
                    </div>
                </div>
                <hr />

                <div class="article__item__box">
                    <label for="inputTitle" class="item__value"><strong>가격</strong></label>
                    <div class="col-sm-10">
                        <input type="text" name="title" id="inputPrice" class="inputbox" value=""
                        onkeyup="convertM(this);" placeholder="물품에 대한 판매가격을 입력해주세요">
                    </div>
                </div>
                <hr />

                <div class="article__item__box">
                    <label for="inputTitle" class="item__value"><strong>상품설명</strong></label>
                    <div class="col-sm-10">
                        <input type="text" name="title" id="inputDescript" class="inputbox" 
                            placeholder="물품에 대한 내용을 입력해주세요" >
                    </div>
                </div>
                <hr />

                <div class="article__item__box">
                    <label for="inputTitle" class="item__value"><strong>거래지역</strong></label>
                    <div class="col-sm-10">
                        <input type="text" class="inputbox" id="sample5_address" disabled = "true" placeholder="주소">
                        <div>
                            <input type="button" class="addr__btn" onclick="sample5_execDaumPostcode()" value="주소 검색">
                            <input type="button" class="addr__btn" value="최근 위치">
                        </div>
                        <div id="map" style="width:300px;height:300px;margin-top:10px;display:none"></div>
                    </div>
                </div>

            <div class="article__item__box float-right">
                <button type="button" id="btn_upload" class="btn btn-secondary">상품등록</button>
            </div>

            <div class="article__item__box dropdown" style="float: right; margin-right: 0;">
                <button type="button" id="toggleBtn" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                    게시판 설정
                </button>
                <div class="dropdown-menu">
                    <a class="dropdown-item">일반</a>
                    <a class="dropdown-item">긴급</a>
                </div>
            </div>

            <br />

        </form>
    </div>
</div>

<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2f665d933d93346898df736499236f77&libraries=services"></script>
<script>
    var mapContainer = document.getElementById('map'), // 지도를 표시할 div
        mapOption = {
            center: new daum.maps.LatLng(37.537187, 127.005476), // 지도의 중심좌표
            level: 5 // 지도의 확대 레벨
        };

    //지도를 미리 생성
    var map = new daum.maps.Map(mapContainer, mapOption);
    //주소-좌표 변환 객체를 생성
    var geocoder = new daum.maps.services.Geocoder();
    //마커를 미리 생성
    var marker = new daum.maps.Marker({
        position: new daum.maps.LatLng(37.537187, 127.005476),
        map: map
    });


    function sample5_execDaumPostcode() {
        new daum.Postcode({
            oncomplete: function(data) {
                var addr = data.address; // 최종 주소 변수

                // 주소 정보를 해당 필드에 넣는다.
                document.getElementById("sample5_address").value = addr;
                // 주소로 상세 정보를 검색
                geocoder.addressSearch(data.address, function(results, status) {
                    // 정상적으로 검색이 완료됐으면
                    if (status === daum.maps.services.Status.OK) {

                        var result = results[0]; //첫번째 결과의 값을 활용

                        // 해당 주소에 대한 좌표를 받아서
                        var coords = new daum.maps.LatLng(result.y, result.x);
                        // 지도를 보여준다.
                        mapContainer.style.display = "block";
                        map.relayout();
                        // 지도 중심을 변경한다.
                        map.setCenter(coords);
                        // 마커를 결과값으로 받은 위치로 옮긴다.
                        marker.setPosition(coords)
                    }
                });
            }
        }).open();
    }
</script>

<%@ include file="../common/footer.jsp" %>
