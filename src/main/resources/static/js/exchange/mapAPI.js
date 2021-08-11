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
        if(f != null)
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
    let index = 0
    files.forEach(function(f) {
        imageFile.push(f)
        var reader = new FileReader()
        reader.onload = function (e) {
            $('#' + container).append(
                "<img id=img_" + index + " onclick='previewImgDelete(this);' style='width: 180px; height: 180px;' src=" + e.target.result + ">"
            )
            index++
        }
        reader.readAsDataURL(f)
    })
    return imageFile
}

function previewImgDelete(e) {
    $('#' + e.id).remove();
    data.files[e.id.split('_')[1]] = null
}













