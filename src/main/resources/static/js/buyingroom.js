
function loadNotices(){

    var data = {
        groupId: $('.groupId').val(),
        page: 0,
        display: 5
    }

    $.ajax({
        url: '/api/group/board/get_notice_list',
        type: 'GET',
        data: data,
        success: function (response){

            if(response.numberOfElements > 0){
                document.getElementsByClassName('_notice')[0].style.display = 'block'
                $.each(response.content, function(key, value){

                    $('.noticeContainer').append(
                        "<a href='#' class='_noticeContentBox'>" +
                            "<div class='notice _noticeContent'>" +
                                value.content +
                            "</div>" +
                            "<div class='notice _noticeDate'>" +
                                new Date(value.regDate).toLocaleString([], {year: 'numeric', month: 'numeric', day: 'numeric', hour: '2-digit', minute: '2-digit'}) +
                            "</div>" +
                        "</a>"
                    )
                })
            }
        }
    })

}

function updateNotice(e, noticeRegistered){

    console.log($(e).closest('.boardItemBox')[0])

    var noticeRegistered = (noticeRegistered) ? 'general' : 'notice'

    //type : general, fix, notice
    var boardId = $(e)[0].path[7].children[0].value
    var groupId = $('.groupId').val();
    var username = $('.user').val();

    var data = {
        groupId: groupId,
        boardId: boardId,
        type: noticeRegistered,
        username: username
    }

    $.ajax({
        url: '/api/group/board/update/notice',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function (response){
        }
    })
}

function deletePost(e){

    var boardId = $(e)[0].path[7].children[0].value
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

    if(infoImgs.length > 0 || $('#board_content_box').val().length > 0){
        var groupId = $('.groupId').val();
        var formData = new FormData();

        formData.append('groupId', groupId);
        formData.append('writer', $('.user').val());
        formData.append('content', $('#board_content_box').val());

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
                $('.contentContainer').prepend(
                    inputPostBox(response)
                )
                document.getElementById('board_content_box').value = '';
                $('._imgPreviewSlider').empty();
                document.getElementsByClassName('contentEmptyContiner')[0].style.display = 'none';
            }
        })
    }else alert('작성할 내용을 입력해주세요.');

}

var page = 0;
var display = 12;
var lastPage = false;
function getBoardList(){
    var data = {groupId: $('.groupId').val(), display: display, page: page}

    if(!lastPage){

        $.ajax({
            url: '/api/group/get_board_list',
            type: 'GET',
            data: data,
            success: function(response){
                if(response.last == true) lastPage = true;
                if(response.content.length > 0){
                    $('.contentEmptyContiner').remove();
                    document.getElementsByClassName('_contentListWrapper')[0].setAttribute("style","hieght:500px");

                    $.each(response.content, function(key, value){
                        console.log(value)
                        $('.contentContainer').append(
                            inputPostBox(value)
                        );
                    })
                }else document.getElementsByClassName('contentEmptyContiner')[0].style.display = 'block';
            }
        })
        page++;
    }
}

function inputPostBox(value){

    var boardType = (value.type != 'general') ? "<span class='notice _noticeText'>공지</span>" : '';
    return "<input type='hidden' value=" + value.boardId + ">" +
            "<div class='boardItemBox' style='animation: fadein 1.5s; margin-bottom: 20px; box-shadow: 0 2px 3px 0 rgba(161, 161, 161, 0.12);'>" +
            "<input type='hidden' class='boardId' value=" + value.boardId + ">" +
            "<div class='contentItemBox'>" +
            "<div class='contentAuthorBox'>" +
            "<div><img class='board _userProfileImg' src=/upload/" + value.user.profilePath + "></div>" +
            "<div class='contentInfoBox'>" +
            "<div style='width: 100%; display: inline-flex; justify-content: space-between;'>" +
            "<div>" +
                "<div class='board _username'>" + value.user.userName + "</div>" +
                    "<div class='boardMoreInfoBox' style='display: inline-flex'>" +
                        boardType +
                        "<div class='board _regDate'>" + new Date(value.regDate).toLocaleString([], {year: 'numeric', month: 'numeric', day: 'numeric', hour: '2-digit', minute: '2-digit'}) + "</div>" +
                    "</div>" +
                "</div>" +
            "<div style='position: relative; height: 0;' class='menuOptionBox'>" +
                "<div class='boardMenuButton'><img style='float: right; cursor: pointer; object-fit: cover;' src='/images/menu_14px.png'></div>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='board _content' style='padding: 10px 10px 0 10px;'>" + value.content + "</div>" +
            imgShow(value.boardId, value.files) +
            "<div style='display: inline-flex; margin-top: 15px;'>" +
            "<div style='display: inline-flex;' class='boardInfo _likeButton'>" +
            "<div class='commentLikeBox'><img src=/images/group/heart_22px.png></div>" +
            "<div style='margin-left: 5px; padding: 0' class='boardInfo commentLikeBox'>" + value.boardLike + "</div>" +
            "</div>" +
            "<div style='display: inline-flex' class='boardInfo _commentButton'>" +
            "<div class='commentIconBox'><img src=/images/group/chat_bubble_22px.png></div>" +
            "<div style='margin-left: 5px; padding: 0' class='boardInfo commentCountBox'>" + value.comments.length + "</div>" +
            "</div>" +
            "</div>" +
            "</div>" +
            "<div class='board _eventBottom'>" +
            "<div class='eventButtonContainer eventButton _emotionBtn'><img style='margin: 0 4px 4px 0;' src='/images/group/facebook_like_21px.png'>좋아요</div>" +
            "<div class='eventButtonContainer eventButton _commentBtn'><img style='margin: 0 4px 4px 0;' src='/images/group/speech_21px.png'>덧글작성</div>" +
            "</div>"+
            "<div class='boardCommentView' style='background-color: white;'>" +
            "<div style='display: none;' class='commentListView'>" +
            commentBox(value.comments) +
            "</div>" +
            "<div class='commentInputBox'>" +

            "</div>" +
            "</div>" +
            "</div>"
}

function commentBox(comments){
    var result = '';
    if(comments.length > 0){
        for(let i = 0; i < comments.length; i ++){
            var date = (comments[i].regDate == null) ? new Date().toLocaleString([], {year: 'numeric', month: 'numeric', day: 'numeric', hour: '2-digit', minute: '2-digit'}) : new Date(comments[i].regDate).toLocaleString([], {year: 'numeric', month: 'numeric', day: 'numeric', hour: '2-digit', minute: '2-digit'})
            result +=
                    "<div id=commentId_" + comments[i].commentId + " class='commentList _commentBox'>" +
                        "<div class='commentProfileBox'>" +
                        "<img src=/upload/" + comments[i].writer.profilePath + ">" +
                        "</div>" +
                    "<div class='userInfoComment'>" +
                        "<div class='commentUsername'>" +
                            comments[i].writer.userName +
                        "</div>" +
                        "<div class='commentContentBox'>" +
                            comments[i].comment +
                        "</div>" +
                        "<div class='commentRegTime'>" +
                            date +
                        "</div>" +
                    "<img class='commentMenuButton' src='/images/menu_14px.png'>" +
                    "</div>" +
                    "</div>"
        }
    }
    return result;
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
            currentPage.innerHTML = (currentPage.innerHTML * 1) + 1
        else
            currentPage.innerHTML = 1
    })
    $(document).on('click', '.boardMenuButton', function(){
        generateMenuBox(this)
    })

    document.body.addEventListener('click', removeMenuBox, true);
})

function removeMenuBox(){
    if(document.getElementsByClassName('boardMenuBox').length != 0)
        $('.boardMenuBox').remove();
}

function generateMenuBox(e){

    if(document.getElementsByClassName('boardMenuBox').length != 0)
        $('.boardMenuBox').remove();

    else{

        var noticeRegistered = $(e).closest('.boardItemBox')[0].childNodes[1].children[0].innerText.includes('공지');
        var noticeText = (noticeRegistered) ? '공지 내리기' : '공지로 등록'

        var line = document.createElement('hr');
        var line2 = document.createElement('hr');

        var boardMenuBox = document.createElement('div');
        boardMenuBox.setAttribute('class', 'boardMenuBox')

        var noticeButton = document.createElement('div')
        noticeButton.setAttribute('class', 'boardmenu _boardNotice')
        noticeButton.addEventListener('click', function (e){ updateNotice(e, noticeRegistered) })
        noticeButton.innerText = noticeText
        boardMenuBox.append(noticeButton)
        boardMenuBox.append(line)

        var updateButton = document.createElement('div')
        updateButton.setAttribute('class', 'boardmenu _boardUpdate')
        updateButton.innerText = '수정'
        updateButton.addEventListener('click', function (e){ update(e) })
        boardMenuBox.append(updateButton)
        boardMenuBox.append(line2)

        var deleteButton = document.createElement('div')
        deleteButton.setAttribute('class', 'boardmenu _boardDelete')
        deleteButton.innerText = '삭제'
        deleteButton.addEventListener('click', function (event){ deletePost(event) })

        boardMenuBox.append(deleteButton)

        e.parentNode.append(boardMenuBox)
    }

}

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
    var index = $('._commentButton').index(this);

    if(document.getElementsByClassName('commentListView')[index].style.display == 'none')
        document.getElementsByClassName('commentListView')[index].style.display = 'block';
    else
        document.getElementsByClassName('commentListView')[index].style.display = 'none';

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

// comment Write
$(document).on('click', '.commentSendBtn', function(){
    var boardId = $(this).closest('.boardItemBox')[0].children[0].value;
    var index = $('.commentSendBtn').index(this);
    var userId = $(document)[0].all[57].value;
    var commentValue = $(this).closest('.boardItemBox').children()[3].children[1].children[0].children[0].value;

    var commentInfoArray = [];

    var data = {
        boardId: boardId,
        userId: userId,
        comment: commentValue,
    }

    $.ajax({
        url: '/api/group/board/comment/write',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data: data,
        dataType: 'JSON',
        success: function (response){
            commentInfoArray.push(response)
            document.getElementsByClassName('commentListView')[index].style.display = 'block';

            var xmlString = commentBox(commentInfoArray);
            var wrapper= document.createElement('div');
            wrapper.innerHTML= xmlString;

            $('.commentListView')[index].append(
                wrapper.firstChild
            )
            $('.commentCountBox')[index].innerText = ($('.commentCountBox')[index].innerText * 1) + 1;
        }
    })
    $(this).closest('.boardItemBox').children()[3].children[1].children[0].children[0].value = '';
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
        loadNotices();
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

function getDocHeight() {
    var D = document;
    return Math.max(
        D.body.scrollHeight, D.documentElement.scrollHeight,
        D.body.offsetHeight, D.documentElement.offsetHeight,
        D.body.clientHeight, D.documentElement.clientHeight
    );
}