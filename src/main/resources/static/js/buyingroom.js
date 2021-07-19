$(document).ready(function(){
    getBoardList();
})

$(function(){
    $('._imgUploadDialog').click(function (){
        $('#uploadFile').click();
    })
    $('.uploadBtn').click(function(){
        postContent();
    })
})

let infoImgs = [];
$('#uploadFile').change(function (e){

    var fileArray = Array.prototype.slice.call(e.target.files);
    var imgIndex = 0;

    fileArray.forEach(function(f){
        if(!f.type.match("image.*")){
            alert("이미지파일만 업로드가 가능합니다.");
            return;
        }
        infoImgs.push(f);
        var reader = new FileReader();
        reader.onload = function(e){

            $('._imgPreviewSlider').prepend(
                "<div class='imgBox' '>" +
                    "<img id=img_" + imgIndex + " onclick='previewImgDelete(this);' src=" + e.target.result + ">" +
                "</div>"
            );

            imgIndex++;
        }
        reader.readAsDataURL(f);
    });
})

function deletePost(boardId){

    var data = {
        boardId: boardId,
        username: $('.user__name').val()
    }

    $.ajax({

        url: '/api/group/board/delete',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function (response){
            alert('해당 게시글이 삭제되었습니다.');
            window.location.reload();
        }
    })
}

function postContent(){

    var groupId = $('.groupId').val();
    var formData = new FormData();

    formData.append('groupId', groupId);
    formData.append('writer', $('.user').val());
    formData.append('content', $('#chatroom_comment_box').val());

    for(let i = 0; i < infoImgs.length; i++)
        if(infoImgs[i] != null)
            formData.append('board_img', infoImgs[i]);

    $.ajax({

        url: '/api/group/board/post',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function(response){
            window.location.reload();
        }
    })
}

function previewImgDelete(e){
    var index = e.id.split('_')[1];
    infoImgs[index] = null;
    $('#' + e.id).parent().remove();
}

var page = 0;
var display = 10;
function getBoardList(){

    var data = {groupId: $('.groupId').val(), display: display, page: page}

    $.ajax({
        url: '/api/group/get_board_list',
        type: 'GET',
        data: data,
        success: function(response){

            if(response.content.length > 1){

                $('.contentEmptyContiner').remove();
                document.getElementsByClassName('_contentListWrapper')[0].setAttribute("style","hieght:500px");

                $.each(response.content, function(key, value){

                    $('#contentWrapper').append(
                        "<input type='hidden' value=" + value.boardId + ">" +
                        "<div style='margin-bottom: 20px; box-shadow: 0 2px 3px 0 rgba(161, 161, 161, 0.12);'>" +
                        "<input type='hidden' class='boardId' value=" + value.boardId + ">" +
                            "<div class='contentItemBox'>" +
                                "<div class='contentAuthorBox'>" +
                                    "<div><img class='board _userProfileImg' src=/upload/" + value.groupUsersEntity.user.profile.profilePath + "></div>" +
                                    "<div class='contentInfoBox'>" +
                                        "<div style='width: 100%; display: inline-flex; justify-content: space-between;'>" +
                                            "<div>" +
                                                "<div class='board _username'>" + value.groupUsersEntity.user.username + "</div>" +
                                                "<div class='board _regDate'>" + new Date(value.regDate).toLocaleTimeString() + "</div>" +
                                            "</div>" +
                                            "<div onclick='deletePost(" + value.boardId + ")' className='boardMenuButton'><img style='cursor: pointer; object-fit: cover;' src='/images/menu_14px.png'></div>" +
                                        "</div>" +
                                    "</div>" +
                                "</div>" +
                                "<div class='board _content' style='padding: 10px 10px 0 10px;'>" + value.content + "</div>" +
                            imgShow(value.boardId, value.files) +
                            "</div>" +
                            "<div class='board _eventBottom'>" +
                                "<div class='eventButtonContainer' ' class='eventButton _emotionBtn'>좋아요</button></div>" +
                                "<div class='eventButtonContainer' ' class='eventButton _commentBtn'>덧글작성</button></div>" +
                            "</div>"+
                        "</div>"
                    );
                })
            }else document.getElementsByClassName('emptyIcon')[0].style.display = 'block';
        }
    })
    page++;
}

function imgShow(id, fileArray){

        let dataNumber = (fileArray.length >= 4) ? 4 : fileArray.length;
        var imgBox = '';

        if(fileArray.length > 0){

        var displayBoolean = 'none';
        var imgContainer = '';
        for(let i = 0; i < fileArray.length; i ++){
            var moreButton = (function(){
                if (i == 3 && fileArray.length != dataNumber) {
                    return "<button type=\"button\" class=\"moreMedia _moreMedia\"><span class=\"moreText\">+" + (fileArray.length - dataNumber) + "장</span></button>"
                }else if(i >= 4){
                    displayBoolean = 'none';
                }else{
                    displayBoolean = 'block';
                }
            })()

            imgContainer += "<li style=display:" + displayBoolean + " data-viewname=\"DPhotoCollageImageItemView\" class=\"collageItem\">" +
            "<button type=\"button\" class=\"collageImage _imageButton\">" +
            "<img src=/upload/" + fileArray[i].name + " alt='' class='_image'>" +
            "</button>" +
            moreButton +
            "</li>"
        }

            imgBox = "<div data-viewname=\"DPostPhotoListView\" class=\"uWidget -displayBlock gCursorPointer\">" +
            "<ul id=board_" + id + " onclick='showImgModal(this);' data-viewname=\"DPhotoCollageView\" class=\"uCollage -horizontal\" data-collage=" + (dataNumber) + ">" +
            imgContainer +
            "</ul>" +
            "</div>"

        }
        return imgBox;
}