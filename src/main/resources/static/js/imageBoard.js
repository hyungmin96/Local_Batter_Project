$(document).ready(function(){
    $("#input_imgs").on("change", preview);
});


let index = 0;

$(function () {
    $('#file_add').click(function (e) {
        $('#input_imgs').click();
    });
});

function preview(e){

    infoImgs = [];

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
            var html = "<div style='display: inline-flex; padding: 10px;'>" + 
            "<class='imgs_wrap'><img id='img'/>" +
            "<a href=\"javascript:void(0);\"" +
            "onclick=\"deleteImageAction(" + index + ")\"" +
            "id=\"img_id_" + index + "\"><img src=\"" + e.target.result +
            "\"boarder='10[x' height='200px' width='200px' data-file='" + f.name +"'" + 
            "class='selProductFile' title='Click to remove'></a>";

            $(".previewBoard").append(html);
            showSlides(index);
            
            index++;
        }

        reader.readAsDataURL(f);

    });

}