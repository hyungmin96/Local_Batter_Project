let index = 0;
var infoImgs = [];

$(document).ready(function () {
    $('#btn_upload').on('click', post);
    $("#input_imgs").on("change", preview);

    $(function () {
        $('#file_add').click(function (e) {
            $('#input_imgs').click();
        });
    });
});

function post() {
    
    const formData = new FormData();

    if ($('#inputTitle').val() ||
        $('#inputPrice').val() ||
        $('#inputDescript').val()){
            
        formData.append('title', $('#inputTitle').val());
        formData.append('price', $('#inputPrice').val());
        formData.append('descryption', $('#inputDescript').val());
        formData.append('writer', 'hyungmin96');
        formData.append('location', $('#inputLocation').val());

        for(let i = infoImgs.length - 1; i > -1; i--){
            if(infoImgs[i] == null)
                infoImgs.splice(i, 1);
        }

        for (var i = 0; i < infoImgs.length; i++) {
            formData.append('uploadFiles', infoImgs[i]);
            console.log(infoImgs);
        }

        $.ajax({
            // 일반적으로 Data는 query String 형태로 전달되기때문에 아래 2가지 파라미터는
            // false로 설정해주어야 하낟.
            type: 'POST',
            url: '/upload',
            data: formData,
            dataType: 'text',
            contentType: false,
            processData: false,
            success: function (result) {
                if (result === '업로드 성공') {
                    location.href = '/';
                }
            },
        }).fail(function (error) {
            if (
                JSON.stringify(error).includes('Data too long for column')
            ) {
                alert('제목은 15자 이하로만 작성이 가능합니다.');
            }
        });
    } else {
        alert('필수항목을 입력 후 다시 시도해주세요.');
    }
}

function preview(e){

    $(".imgs_wrap").empty();

    var files = e.target.files;
    var filesArr = Array.prototype.slice.call(files);

    filesArr.forEach(function(f){
        if(!f.type.match("image.*")){
            alert("이미지파일만 업로드가 가능합니다.");
            return;
        }

        infoImgs.push(f);

        var reader = new FileReader();
        reader.onload = function(e){
            var html = "<a href=\"javascript:void(0);\"" +
            "onclick=\"previewDelete(" + index + ")\"" +
            "id=\"img_id_" + index + "\"><img style='display: inline-flex; padding: 10px;' src=\"" + e.target.result +
            "\"boarder='10' height='200px' width='200px' data-file='" + f.name + "'" +
            "' class='selProductFile' title='Click to remove'></a>";

            $(".previewBoard").append(html);
            index++;
        }

        reader.readAsDataURL(f);

    });

}

function previewDelete(index){

    infoImgs[index] = null;
    var img_id = "#img_id_" + index;
    $(img_id).remove();

}