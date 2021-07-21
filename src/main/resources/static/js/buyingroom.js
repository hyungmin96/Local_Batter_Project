

function get_comments(){

    var data = {
        groupId: '',
        boardId: ''
    }

    $.ajax({
        url: '/api/group/get_comments',
        type: 'GET',
        data: data,
        success: function(response){

        }
    })
}

function commentWrite(e, boardId, userId, comment){

    var data = {
        boardId: boardId,
        userId: userId,
        comment: comment
    }

    $.ajax({

        url: '/api/group/board/comment/write',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data: data,
        dataType: 'JSON',
        success: function (response){
            var commentList = $(e).closest('.boardItemBox')[0].childNodes[3].childNodes[0];

            commentBox(commentList,
                response.comment_id,
                userInfoObject[0].profile,
                userInfoObject[0].username,
                response.comment,
                new Date(response.date).toLocaleString())
        }
    })
}

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


var userInfoObject = [];
function getUserInfo(){
    $.ajax({
        url: '/api/group/get/userInfo',
        type: "GET",
        success: function (response){
            console.log(response)
            userInfoObject.push({
                id: response.id,
                username: response.user.profile.nickname,
                profile: response.user.profile.profilePath
            })
        }
    })
}

var page = 0;
var display = 12;
var commentJsonObject = [];
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
                    commentJsonObject.push({ [value.boardId] : value.comments})

                    $('#contentWrapper').append(
                        "<input type='hidden' value=" + value.boardId + ">" +
                        "<div class='boardItemBox' style='margin-bottom: 20px; box-shadow: 0 2px 3px 0 rgba(161, 161, 161, 0.12);'>" +
                        "<input type='hidden' class='boardId' value=" + value.boardId + ">" +
                        "<div class='contentItemBox'>" +
                        "<div class='contentAuthorBox'>" +
                        "<div><img class='board _userProfileImg' src=/upload/" + value.groupUsersEntity.profilePath + "></div>" +
                        "<div class='contentInfoBox'>" +
                        "<div style='width: 100%; display: inline-flex; justify-content: space-between;'>" +
                        "<div>" +
                        "<div class='board _username'>" + value.groupUsersEntity.user_name + "</div>" +
                        "<div class='board _regDate'>" + new Date(value.regDate).toLocaleTimeString() + "</div>" +
                        "</div>" +
                        "<div onclick='deletePost(" + value.boardId + ")' className='boardMenuButton'><img style='cursor: pointer; object-fit: cover;' src='/images/menu_14px.png'></div>" +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "<div class='board _content' style='padding: 10px 10px 0 10px;'>" + value.content + "</div>" +
                        imgShow(value.boardId, value.files) +
                        "<div style='display: inline-flex'>" +
                        "<div class='boardInfo _likeButton'>" +
                        "<img src=/images/group/heart_22px.png> " + value.boardLike +
                        "</div>" +
                        "<div class='boardInfo _commentButton'>" +
                        "<img src=/images/group/chat_bubble_22px.png> " + value.comments.length +
                        "</div>" +
                        "</div>" +
                        "</div>" +
                        "<div class='board _eventBottom'>" +
                        "<div class='eventButtonContainer eventButton _emotionBtn'><img style='margin: 0 4px 4px 0;' src='/images/group/facebook_like_21px.png'>좋아요</div>" +
                        "<div class='eventButtonContainer eventButton _commentBtn'><img style='margin: 0 4px 4px 0;' src='/images/group/speech_21px.png'>덧글작성</div>" +
                        "</div>"+
                        "<div class='boardCommentView' style='background-color: white;'>" +
                        "<div class='commentListView'>" +

                        "</div>" +
                        "<div class='commentInputBox'>" +

                        "</div>" +
                        "</div>" +
                        "</div>"
                    );
                })

            }else document.getElementsByClassName('emptyIcon')[0].style.display = 'block';
        }
    })

    page++;
}

$(function(){

    $('._imgUploadDialog').click(function (){
        $('#uploadFile').click();
    })
    $('.uploadBtn').click(function(){
        postContent();
    })
    $('.carousel-control-prev').click(function(){
        var currentPage = document.getElementsByClassName('_currentPageNumber')[0];
        var lastPage = document.getElementsByClassName('_lastPageNumber')[0];
        if((currentPage.innerHTML * 1) > 1 && (lastPage.innerHTML * 1) >= currentPage.innerHTML)
            currentPage.innerHTML = (currentPage.innerHTML * 1) - 1;
        else
            currentPage.innerHTML = lastPage.innerHTML;
    })
    $('.carousel-control-next').click(function(){
        var currentPage = document.getElementsByClassName('_currentPageNumber')[0];
        var lastPage = document.getElementsByClassName('_lastPageNumber')[0];
        if((currentPage.innerHTML * 1) < (lastPage.innerHTML * 1))
            currentPage.innerHTML = (currentPage.innerHTML * 1) + 1;
        else
            currentPage.innerHTML = 1;
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

function previewImgDelete(e){
    var index = e.id.split('_')[1];
    infoImgs[index] = null;
    $('#' + e.id).parent().remove();
}

// 덧글입력 element 생성
$(document).on('click', '._commentBtn', function(){
    showCommentInputBox(this);
});

// 덧글목록 element 생성
$(document).on('click', '._commentButton', function (){
    showCommentList(this);
    showCommentInputBox(this);
});

function showCommentInputBox(e){

    var inputBox = $(e).closest('.boardItemBox')[0].childNodes[3].childNodes[1];

    if(!inputBox.hasChildNodes()){
        var commentInputBox = document.createElement('div');
        commentInputBox.setAttribute('class', 'commentWriteBox');
        commentInputBox.style.cssText = 'padding: 13px;'

        var commentTextbox = document.createElement('input');
        commentTextbox.setAttribute('class', 'commentInputText');
        commentTextbox.setAttribute('type', 'text');
        commentTextbox.setAttribute('placeholder', '작성할 덧글내용을 입력해주세요.');

        var commentSendButton = document.createElement('button');
        commentSendButton.setAttribute('class', 'commentSendBtn');
        commentSendButton.innerHTML = '작성';

        commentInputBox.appendChild(commentTextbox);
        commentInputBox.appendChild(commentSendButton);

        inputBox.appendChild(commentInputBox);
    }
}

$(document).on('click', '.commentSendBtn', function(){
    var boardId = $(this).closest('.boardItemBox')[0].children[0].value;
    var userId = $(document)[0].all[57].value;
    var commentValue = $(this).closest('.boardItemBox').children()[3].children[1].children[0].children[0].value;
    commentWrite(this, boardId, userId, commentValue);
})

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

document.addEventListener("DOMContentLoaded", function(event) {
    var scrollpos = localStorage.getItem('scrollpos');
    if (scrollpos){
        window.scrollTo(0, 0);
        getBoardList();
    }
});

window.onbeforeunload = function(e) {
    localStorage.setItem('scrollpos', window.scrollY);
};

$(document).scroll(function() {
    if($(window).scrollTop() + $(window).height() == getDocHeight())
        getBoardList();
});

$(document).ready(function(){
    getUserInfo();
})

function getDocHeight() {
    var D = document;
    return Math.max(
        D.body.scrollHeight, D.documentElement.scrollHeight,
        D.body.offsetHeight, D.documentElement.offsetHeight,
        D.body.clientHeight, D.documentElement.clientHeight
    );
}