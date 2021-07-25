let GroupRoomInfoImgs = [];
let chatRoomImgDeleteIndex = [-1];
let roomValue;
var index;

$(document).ready(function(){
    $('#smartwizard').smartWizard({
    selected: 0,
    theme: 'dots',
    autoAdjustHeight:true,
    transitionEffect:'fade',
    showStepURLhash: false,
    });
    loadGroupChatRoomList();
});

$(function(){
    $('#enter__btn').click(function(){
        enterRoom();
    })
    $('#room__create').click(function(){
        createGroupRoom();
    })
    $('#datetimepicker4').datetimepicker({
        format: 'L'
    });
    $('#img__upload').click(function(){
        $('#input__Group__img').click();
    })
    $("#input__Group__img").change(function(e){
        GroupPreview(e);
    });
    $("#deletebtn").click(function(e){
        deleteRoom();
    });
})


function deleteRoom(){

    const data = {groupId: $('.groupId').val(), username: $('.user__name').val()};

    $.ajax({
        url: '/api/group/delete',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function (response){
            alert('채팅방이 삭제되었습니다.');
            window.location.reload();
        }
    })

}

function enterRoom(){

    const data = {
        userId: $('.join_user_id').val(),
        username: $('.join_user_name').val(),
        groupId: $('.groupId').val()
    };

    $.ajax({

        url: '/api/group/enter',
        type: 'POST',
        data:  data,
        contentType: 'application/x-www-form-urlencoded',
        success: function(response){
            $('#close__btn').click();
            window.location.href='/group/room/' + data.groupId;
        }
        }).fail(function(){
            alert('해당 그룹은 가입할 수 없습니다. - 설정인원 초과');
        })
}

let GroupChatRoomArray = [];

let page = 0;
let display = 30;
let pagination = false;
let boardPagination = false;

function loadBoardPagination(e, pages){

    Array.from(document.getElementsByClassName('page__number__box')[0].children, item=>{
        item.style.backgroundColor = 'rgb(238, 240, 243)';
    });

    if(e != null)
        e.style.backgroundColor = 'rgb(222,218,218)';

    if(!boardPagination){
        boardPagination = true;
        if(pages < 1) pages = 1;
        for(let i = 1; i <= pages; i++){
            $('.page__number__box').append(
                "<li id='boardnum-" + i + "' class='page__number' onclick='loadGroupChatRoomList(this, " + (i - 1) + ");'>" + 
                "<a href='javascript:void(0)'>" + i + "</a></li>"
            );
        }
    }
}

function loadGroupChatRoomList(e, page = 0){

    var data = {page: page, display: display};

    $.ajax({
        url: '/api/group/getlist',
        type: 'GET',
        data: data,
        success: function(response){
            
            var lastPage = document.getElementsByClassName('lastpage')[0];
            lastPage.dataset.value = response.totalPages

            $('.Group__room__list').empty();
            loadBoardPagination(e, response.totalPages);
            GroupChatRoomArray = [];

            $.each(response.content, function(key, value){

                console.log(value)
                GroupChatRoomArray.push(value);

                var files = (value.files.length > 0) ? value.files[0].name : '';

                $('.Group__room__list').append(
                    "<div id='item__box__" + key + "' onclick='javascript:showModal(); showDataToModal(this);' class='Group__item__box'>" + 
                    "<img src=/upload/" + files + ">" +
                    "<div class='Group__room__content'>" +
                        "<div class = 'Group__room__title'>" + value.title + "</div>" +
                            "<div class='user_number'>" +
                                "<span>인원 : </span>" +
                                "<span class = 'Group__room__users'>" + value.users.length + "명</span>" +
                            "</div>" +
                        "</div>" +
                        "</div>" +
                    "</div>" +
                    "</div>"
                );
            })
        }
    })
}

function showDataToModal(e){
    
    let id = e.id.split('item__box__')[1]
    let value = GroupChatRoomArray[id];
    
    $('.groupId')[0].value = value.id;
    $('.roomTitle')[0].value = value.roomTitle;

    globalThis.roomValue = value;

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

function createGroupRoom(){

    var formData = new FormData();
    var checkboxArary = $(".checkbox");

    formData.append('title', $('#Group__title').val());
    formData.append('description', $('#Group__desciption').val());
    formData.append('tag', $('#tags').val());
    formData.append('chk_1', $('#' + checkboxArary[0].children[0].id).val());
    formData.append('chk_2', $('#' + checkboxArary[1].children[0].id).val());
    formData.append('chk_3', $('#' + checkboxArary[2].children[0].id).val());
    formData.append('roomTitle', $('#Group__chat__title').val());
    formData.append('limit', $('#Group__chat__limit').val());

    for(var i = GroupRoomInfoImgs.length - 1; i > -1; i--)
        if(GroupRoomInfoImgs[i] == null) GroupRoomInfoImgs.splice(i, 1);

    for (var i = 0; i < GroupRoomInfoImgs.length; i++)
        formData.append('files', GroupRoomInfoImgs[i]);

    $.ajax({

        url: '/api/group/create',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function(){
            alert('채팅방을 생성하였습니다.')
            window.location.reload();
        }
    })
}

function GroupPreview(e){

    var files = e.target.files;
    var filesArr = Array.prototype.slice.call(files);

    filesArr.forEach(function(f){
        if(!f.type.match("image.*")){
            alert("이미지파일만 업로드가 가능합니다.");
            return;
        }
        GroupRoomInfoImgs.push(f);
        var reader = new FileReader();
        reader.onload = function(e){
            var html = "<a href=\"javascript:void(0);\"" +
            "onclick=\"previewDelete(" + index + ")\"" +
            "id=\"img_id_" + index + "\"><img style='object-fit: cover; display: inline-flex; padding: 10px;' src=\"" + e.target.result +
            "\"boarder='10' height='200px' width='230px' data-file='" + f.name + "'" +
            "' class='selProductFile' title='사진 삭제'></a>";

            $("#preview__img__container").append(html);
            index++;
        }
        reader.readAsDataURL(f);
    });
}

function previewDelete(index){
    GroupRoomInfoImgs[index] = null;
    var img_id = "#img_id_" + index;
    $(img_id).remove();
    chatRoomImgDeleteIndex.push(index);
}

function checkboxOnLoad(e){
    var checkboxArary = $(".checkbox");

    for(let i = 0; i < checkboxArary.length; i ++){
        if(checkboxArary[i].children[0].id != e.childNodes[0].id){
            document.getElementById(checkboxArary[i].children[0].id).checked = false;
            document.getElementById(checkboxArary[i].children[0].id).value = '0';
        }
        else{
            document.getElementById(e.childNodes[0].id).checked = true;
            document.getElementById(checkboxArary[i].children[0].id).value = '1';
        }
    }
}