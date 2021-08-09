var mapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(33.450701, 126.570667), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
    };

function panTo(long, lat) {
    // 이동할 위도 경도 위치를 생성합니다
    var moveLatLon = new kakao.maps.LatLng(long, lat);

    // 지도 중심을 부드럽게 이동시킵니다
    // 만약 이동할 거리가 지도 화면보다 크면 부드러운 효과 없이 이동합니다
    map.panTo(moveLatLon);
}

function setMaker(residence, long, lat){
    let markerPosition  = new kakao.maps.LatLng(long, lat);
    let setMarker = new kakao.maps.Marker({
        position: markerPosition
    });
    setMarker.setMap(map);
    var iwContent = '<div style="padding:5px;">' + residence + '<br>' +
            '<a href="https://map.kakao.com/link/to/' + residence + ',' + long + ',' + lat + '" style="color:blue" target="_blank">길찾기</a></div>',// 인포윈도우에 표출될 내용으로 HTML 문자열이나 document element가 가능합니다
        iwPosition = new kakao.maps.LatLng(long, lat); //인포윈도우 표시 위치입니다

    // 인포윈도우를 생성합니다
    var infowindow = new kakao.maps.InfoWindow({
        position : iwPosition,
        content : iwContent
    });
    infowindow.open(map, setMarker);
}

var map = new kakao.maps.Map(mapContainer, mapOption);
var geocoder = new kakao.maps.services.Geocoder();
var marker = new kakao.maps.Marker(), // 클릭한 위치를 표시할 마커입니다
    infowindow = new kakao.maps.InfoWindow({zindex:1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

infowindow = new kakao.maps.InfoWindow({zindex:1}); // 클릭한 위치에 대한 주소를 표시할 인포윈도우입니다

function searchAddrFromCoords(coords, callback) {
    geocoder.coord2RegionCode(coords.getLng(), coords.getLat(), callback);
}

function searchDetailAddrFromCoords(coords, callback) {
    geocoder.coord2Address(coords.getLng(), coords.getLat(), callback);
}

document.getElementsByClassName('searchButton')[0].addEventListener('click', function (){
    console.log($('#serachAddrText').val())
    geocoder.addressSearch($('#serachAddrText').val(), function (result, status) {
        if (status === kakao.maps.services.Status.OK) {
            var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
            infowindow.open(map, marker);
            map.setCenter(coords);
        }
    });
})

