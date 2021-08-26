var cordinates = {
    clientMap: '',
    residence: '',
    longitude : '',
    latitude : ''
}

// * 교환 요청 게시글 업로드
$(document).on('change', '.clientImgUpload', function(e){
    data.files = previewImage(e, 'clientBoardImageContinaer')
})

$(document).on('click', '.clientUploadButton', function (){
    uploadClientExchange(data.files)
})

function uploadClientExchange(files){

    var formData = new FormData()
    // client user id that request exchange to writer
    formData.append('clientExchangeId', $('.g_user_id').val())
    formData.append('userId', $('.g_user_id').val())
    formData.append('writerId', $('.writerId').val())
    formData.append('boardId', $('.boardId')[0].value)
    formData.append('title', $('.clientTitleBox')[0].value)
    formData.append('content', $('.clientDescriptionBox')[0].value)
    formData.append('price', $('.clientAddPriceBox')[0].value)
    formData.append('request', $('.clientRequestBox')[0].value)
    formData.append('address', $('#clientExchangeAddr').val() + ' ' + $('#clientExchangeAddrDetail').val())
    formData.append('longtitude', $('#clientLongtitude').val())
    formData.append('latitude', $('#clientLatitude').val())

    files.forEach(function(f){
        formData.append('images', f)
    })

    $.ajax({
        url: '/api/exchange/client/post',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function (response){
            console.log(response)
            alert('물품 교환을 성공적으로 요청하였습니다.')
            window.location.href = '/view/client/confirm/exchange/' + response.clientId
        }
    })
}

var clientMapContainer = document.getElementById('map'), // 지도를 표시할 div
    mapOption = {
        center: new kakao.maps.LatLng(cordinates.longitude, cordinates.latitude), // 지도의 중심좌표
        level: 5 // 지도의 확대 레벨
    };
var clientMap = new kakao.maps.Map(clientMapContainer, mapOption);

$(document).on('click', '.clientImageUploadButton', function(){
    $('#clientImagePreview').click()
})

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
            $('.writerId')[0].value = response.id // writer Id
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
            if(response.boardFiles.length > 0){
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
                " 원</div>" +
                "<div id='writerLocationMap' style='margin-top: 10px; width:100%; height:270px; border: 1px solid #9d9d9d'>" +
                "</div>"
            )

            var writerMapContainer = document.getElementById('writerLocationMap') // 지도를 표시할 div
            var writerMap = new kakao.maps.Map(writerMapContainer, mapOption);

            setMaker(writerMap, response.location + '<br/>' + response.locationDetail, response.longitude, response.latitude)
            // display marker to client's map
            setOtherMaker(clientMap, response.location + '<br/>' + response.locationDetail, response.longitude, response.latitude)
            // move to specific cordinate in the map
            panTo(response.longitude, response.latitude)
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