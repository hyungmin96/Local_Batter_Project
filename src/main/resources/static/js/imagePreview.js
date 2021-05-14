$(document).ready(function(){
    $("#input_imgs").on("change", handleImgFileSelect);
});

function fileUploadAction(){
    $("#input_imgs").trigger("click");
}

let index = 0;

function handleImgFileSelect(e){

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
            var html = "<div class='mySlides fade2'>" + 
            "<class='imgs_wrap'><img id='img'/>" +
            "<a href=\"javascript:void(0);\"" +
            "onclick=\"deleteImageAction(" + index + ")\"" +
            "id=\"img_id_" + index + "\"><img src=\"" + e.target.result +
            "\"height='200px' width='100%' data-file='" + f.name +"'" + 
            "class='selProductFile' title='Click to remove'></a>";

            $(".slideshow-container").append(html);
            btn = "<span class='dot' onclick='currentSlide(" + (index +1)+ ")'></span>";
            $(".imgInfo").append(btn);
            
            console.log(index);

            showSlides(index);
            
            index++;
        }

        reader.readAsDataURL(f);

    });

}