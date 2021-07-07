var infoImgs = [];
var imgDeleteIndex = [-1];

$(document).ready(function(){
    $('#smartwizard').smartWizard({
    selected: 0,
    theme: 'dots',
    autoAdjustHeight:true,
    transitionEffect:'fade',
    showStepURLhash: false,
    });
    loadBuyingChatRoomList();
});

$(function(){
    $('#room__create').click(function(){
        createBuyingRoom();
    })
    $('#datetimepicker4').datetimepicker({
        format: 'L'
    });
    $('#img__upload').click(function(){
        $('#input__buying__img').click();
    })
    $("#input__buying__img").change(function(e){
        buyingPreview(e);
    });
})

var page = 0;
var display = 20;

function loadBuyingChatRoomList(){

    var data = {page: page, display: display};

    $.ajax({

        url: '/api/buying/getlist',
        type: 'GET',
        data: data,
        success: function(response){
            
            $.each(response.content, function(key, value){
                console.log(value)
            })

        }
    })

}

function createBuyingRoom(){

    var formData = new FormData();

    formData.append('title', $('#buying__title').val());
    formData.append('description', $('#buying__desciption').val());
    formData.append('date', $('#buying__date').val());
    formData.append('price', $('#buying__price').val());
    formData.append('roomTitle', $('#buying__chat__title').val());
    formData.append('limit', $('#buying__chat__limit').val());

    for(var i = infoImgs.length - 1; i > -1; i--)
        if(infoImgs[i] == null) infoImgs.splice(i, 1);

    for (var i = 0; i < infoImgs.length; i++) 
        formData.append('files', infoImgs[i]);

    $.ajax({

        url: '/api/buying/create',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function(){

        }

    })

}

function buyingPreview(e){
    // $("#preview__img__container").empty();

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
            "\"boarder='10' height='200px' width='230px' data-file='" + f.name + "'" +
            "' class='selProductFile' title='사진 삭제'></a>";

            $("#preview__img__container").append(html);
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
