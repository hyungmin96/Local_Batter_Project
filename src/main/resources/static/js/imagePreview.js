$(document).ready(function(){
    $("#input_imgs").on("change", handleImgFileSelect);
});

function fileUploadAction(){
    $("#input_imgs").trigger("click");
}

function handleImgFileSelect(e){

    infoImgs = [];

    $(".imgs_wrap").empty();

    var files = e.target.files;
    var filesArr = Array.prototype.slice.call(files);

    var index = 0;
    filesArr.forEach(function(f){
        if(!f.type.match("image.*")){
            alert("이미지파일만 업로드가 가능합니다.");
            return;
        }

        infoImgs.push(f);

        var reader = new FileReader();
        reader.onload = function(e){
            var html = "<a href=\"javascript:void(0);\" onclick=\"deleteImageAction(" + index + ")\" id=\"img_id_" + index + "\"><img src=\"" + e.target.result + "\" data-file='" + f.name +"' class='selProductFile' title='Click to remove'></a>";
            $(".imgs_wrap").append(html);
            index++;
        }

        var slider = ""

        reader.readAsDataURL(f);

    });

}