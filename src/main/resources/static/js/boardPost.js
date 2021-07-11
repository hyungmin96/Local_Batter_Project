let index = 0;
let infoImgs = [];
let imgDeleteIndex = [-1];

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

    if ($('#inputTitle').val().length > 0 &&
        $('#inputPrice').val().length > 0 &&
        $('#inputDescript').val().length > 0){

        formData.append('category', $('#toggleBtn').text());
        formData.append('title', $('#inputTitle').val());
        formData.append('price', $('#inputPrice').val());
        formData.append('descryption', $('#inputDescript').val());
        formData.append('writer', 'hyungmin96');
        formData.append('location', $('#sample5_address').val());

        for(let i = infoImgs.length - 1; i > -1; i--){
            if(infoImgs[i] == null)
                infoImgs.splice(i, 1);
        }

        for (var i = 0; i < infoImgs.length; i++) {
            formData.append('uploadFiles', infoImgs[i]);
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

function updatePost() {    
    const formData = new FormData();

    if ($('#inputTitle').val().length > 0 &&
        $('#inputPrice').val().length > 0 &&
        $('#inputDescript').val().length > 0){
            
        formData.append('id', $('.boardId').val());
        formData.append('category', $('#toggleBtn').text());
        formData.append('title', $('#inputTitle').val());
        formData.append('price', $('#inputPrice').val());
        formData.append('descryption', $('#inputDescript').val());
        formData.append('writer', $('.writer__user_name').val());
        formData.append('location', $('#sample5_address').val());

        for(let i = 0; i < imgDeleteIndex.length; i++){
            formData.append('deleteIndex', imgDeleteIndex[i]);
        }
        
        for(let i = infoImgs.length - 1; i > -1; i--){
            if(infoImgs[i] == null)
                infoImgs.splice(i, 1);
        }

        for (var i = 0; i < infoImgs.length; i++) {
            formData.append('uploadFiles', infoImgs[i]);
        }

        $.ajax({
            // 일반적으로 Data는 query String 형태로 전달되기때문에 아래 2가지 파라미터는
            // false로 설정해주어야 하낟.
            type: 'POST',
            url: '/update',
            data: formData,
            dataType: 'text',
            contentType: false,
            processData: false,
            success: function (result) {
                if (result === '수정 성공') {
                    location.href = '/';
                }
            },
        }).fail(function(error) {
            if (JSON.stringify(error).includes('Data too long for column')) 
                alert('제목은 15자 이하로만 작성이 가능합니다.');
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
            "' class='selProductFile' title='사진 삭제'></a>";

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
    imgDeleteIndex.push(index);
}

function convertM(object){
    var len, point, str;  
    var num = object.value.replaceAll(',', '');
    num = num + ""; 
    point = num.length % 3 ;
    len = num.length; 
    if(num.length < 9){
        str = num.substring(0, point); 
        while (point < len) { 
            if (str != "") str += ","; 
            str += num.substring(point, point + 3); 
            point += 3; 
        } 
        object.value = str;
    }else{
        alert('최대 입력가능한 숫자를 초과하였습니다.');
    }
}