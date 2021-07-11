let buyingRoomInfoImgs = [];
let chatRoomImgDeleteIndex = [-1];
let roomValue;

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
    $('#enter__btn').click(function(){
        enterRoom();
    })
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


function enterRoom(){

    var data = { roomId: $('.roomId').val() * 1, username: $('.user__name').text()}
    $.ajax({

        url: '/api/buying/enter',
        type: 'POST',
        data:  data,
        contentType: 'application/x-www-form-urlencoded',
        success: function(){
            window.open("/buying/chat/" + $('.roomId')[0].value, $('.roomTitle')[0].value, "width=400, height=650");
            $('#close__btn').click();
        }
    })
}

let buyingChatRoomArray = [];

let page = 0;
let display = 10;
let pagination = false;
let boardPagination = false;

function loadBoardPagination(e, pages){

    Array.from(document.getElementsByClassName('page__number__box')[0].children, item=>{
        item.style.backgroundColor = 'white';
    });

    if(e != null)
        e.style.backgroundColor = 'rgb(236, 236, 236)';

    if(!boardPagination){
        boardPagination = true;
        if(pages < 1) pages = 1;
        for(let i = 1; i <= pages; i++){
            $('.page__number__box').append(
                "<li id='boardnum-" + i + "' class='page__number' onclick='loadBuyingChatRoomList(this, " + (i - 1) + ");'>" + 
                "<a href='javascript:void(0)'>" + i + "</a></li>"
            );
        }
    }
}

function loadBuyingChatRoomList(e, page = 0){

    var data = {page: page, display: display};

    $.ajax({
        url: '/api/buying/getlist',
        type: 'GET',
        data: data,
        success: function(response){
            
            var lastPage = document.getElementsByClassName('lastpage')[0];
            lastPage.dataset.value = response.totalPages

            $('.buying__room__list').empty();
            loadBoardPagination(e, response.totalPages);
            buyingChatRoomArray = [];

            $.each(response.content, function(key, value){
            
                buyingChatRoomArray.push(value);

                var files = (value.files.length > 0) ? value.files : null;

                $('.buying__room__list').append(
                    "<div id='item__box__" + key + "' onclick='javascript:showModal(); showDataToModal(this);' class='buying__item__box'>" + 
                    "<img src=/upload/" + files[0].name + ">" + 
                        "<div class='buying__room__content'>" +
                        "<div class = 'buying__room__title'>" + value.roomTitle + "</div>" +
                        
                        "<div style = 'display: flex; flex-direction: row;'>" +
                            "<div>" +
                                "<div class = 'buying__room__description'>" + value.description + "</div>" +
                                "<div class = 'buying__room__price'> 필요금액 : " + convertPrice(value.price) + "원</div>" +
                            "</div>" +
                            "<div>" +
                                "<div class = 'buying__date'>" +
                                    "<span>참여인원 : </span>" +
                                        "<span class = 'buying__room__users'>" + value.users.length + "명</span>" + ' / ' +
                                        "<span class = 'buying__room__limit'>" + value.limitUsers + "명</span>" +
                                    "</div>" +
                                "<div class = 'buying__room__expiration'> 만료일자 : " + value.roomDate + "</div>" +
                                "</div>" +
                            "</div>" +
                        "</div>" +

                    "</div>" +
                    "<hr />"
                );  
            })
        }
    })
}

function showDataToModal(e){
    
    let id = e.id.split('item__box__')[1]
    let value = buyingChatRoomArray[id];
    
    $('.roomId')[0].value = value.id;
    $('.roomTitle')[0].value = value.roomTitle;

    globalThis.roomValue = value;

    console.log(value)

    $('.product__img__container').empty();
    $('.product__img__desc').empty();

    value.files.forEach(item => {
        $('.product__img__container').append(
            "<div class='product__img__item'>" +
                "<img src='/upload/" + item.name + "'>" +
            "</div>"
        )
    })

    $('.product__img__desc').append(
        "<div class='product__desc__item'>" +
            value.description + 
        "</div>"
    )
}

function convertPrice(num){
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

function createBuyingRoom(){

    var formData = new FormData();

    formData.append('title', $('#buying__title').val());
    formData.append('description', $('#buying__desciption').val());
    formData.append('date', $('#buying__date').val());
    formData.append('price', $('#buying__price').val());
    formData.append('roomTitle', $('#buying__chat__title').val());
    formData.append('limit', $('#buying__chat__limit').val());

    for(var i = buyingRoomInfoImgs.length - 1; i > -1; i--)
        if(buyingRoomInfoImgs[i] == null) buyingRoomInfoImgs.splice(i, 1);

    for (var i = 0; i < buyingRoomInfoImgs.length; i++)
        formData.append('files', buyingRoomInfoImgs[i]);

    $.ajax({

        url: '/api/buying/create',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function(){
            alert('채팅방을 생성하였습니다.')
        }
    })
}

function buyingPreview(e){

    var files = e.target.files;
    var filesArr = Array.prototype.slice.call(files);

    filesArr.forEach(function(f){
        if(!f.type.match("image.*")){
            alert("이미지파일만 업로드가 가능합니다.");
            return;
        }
        buyingRoomInfoImgs.push(f);
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
    buyingRoomInfoImgs[index] = null;
    var img_id = "#img_id_" + index;
    $(img_id).remove();
    chatRoomImgDeleteIndex.push(index);
}

