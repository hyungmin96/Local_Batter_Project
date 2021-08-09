// * 게시글 작성자 api
const data = {
    residence: '',
    longitude: '',
    latitude: '',
    location: '',
    preferTime: '',
    files: []
}

// * 작성자 게시글 업로드
$(document).on('change', '.upload', function(e){
    data.files = previewImage(e, 'previewImageContainer')
})

$(document).on('click', '.saveExchangeInfoButton', function (){
    uploadImagesToBoard(data.files)
})

// * 교환 요청 게시글 업로드
$(document).on('change', '.clientImgUpload', function(e){
    data.files = previewImage(e, 'clientBoardImageContinaer')
})

$(document).on('click', '.clientUploadButton', function (){
    uploadClientExchange(data.files)
})

function uploadClientExchange(files){

    var formData = new FormData()
    formData.append('userId', '1')
    formData.append('boardId', $('.boardId')[0].value)
    formData.append('content', $('.clientContentBox')[0].value)
    formData.append('price', $('.clientAddPriceBox')[0].value)
    formData.append('request', $('.clientRequestBox')[0].value)

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
        }
    })

}

// 이미지 업로드 시 미리보기 기능
function uploadImagesToBoard(files){

    var formData = new FormData()
    formData.append('residence', $('#sample5_address').val() + ' ' + $('.inputbox')[0].value)
    formData.append('longitude', $('.longitudeValue')[0].value)
    formData.append('latitude', $('.latitudeValue')[0].value)
    formData.append('groupId', 1)
    formData.append('location', $('#exchange_address').val())
    formData.append('preferTime', $('#exchange_time').val())
    formData.append('content', $('.contentBox').val())
    formData.append('category', 'exchange')

    files.forEach(function(f){
        formData.append('board_img', f)
    })

    $.ajax({
        url: '/api/exchange/post',
        type: 'POST',
        data: formData,
        processData: false,
        contentType: false,
        success: function (response){
            console.log(response)
        }
    })

}

function previewImage(e, container){
    let files = Array.prototype.slice.call(e.target.files)
    let imageFile = []
    files.forEach(function(f) {
        imageFile.push(f)
        var reader = new FileReader()
        reader.onload = function (e) {
            $('#' + container).append(
                "<img style='width: 100px; height: 100px;' src=" + e.target.result + ">"
            )
        }
        reader.readAsDataURL(f)
    })
    return imageFile
}















