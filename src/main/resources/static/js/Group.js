

function deleteComment(e, commentId){

    const data = {
        groupId: globalThis.groupId,
        userId: $('.user').val(),
        commentId: commentId
    }

    $.ajax({
        url: '/api/group/comment/delete',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function(response){
            $('#commentId_' + commentId).remove();
            e.innerHTML = (e.innerHTML * 1) - 1;
        }
    })
}

function exitRoom(){

    const data = {
        groupId: $('.groupId').val(),
        username: $('.groupUsername').val(),
        userId: $('.user').val()
    }

    $.ajax({
        url: '/api/group/exit',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function(response){
            if(response.includes('Success')){
                alert('그룹을 나갔습니다.');
                location.href='http://localhost:8000/product/group';
            }
        }
    })
}

function loadLatestComments() {
    const data = {
        groupId: $('.groupId').val(),
        page: 0,
        display: 5
    }

    $.ajax({
        url: '/api/group/comment/get_latest_comments',
        type: 'GET',
        data: data,
        success: function (response) {
            $.each(response.content, function (key, value) {
                $('._latestCommentContainer').append(
                    "<div class='latest _latestCommentBox'>" +
                        "<span class='latest _commentContent'>" + value.comment + "</span>" +
                    "</div>"
                )
            })
        }
    })
}

function loadLatestImages() {
    const data = {
        groupId: $('.groupId').val(),
        page: 0,
        display: 9
    }

    $.ajax({
        url: '/api/group/board/get_latest_images',
        type: 'GET',
        data: data,
        success: function (response) {
            $.each(response.content, function (key, value) {
                $('._latestImageContainer').append(
                    "<div>" +
                    "<img src=/upload/" + value.name + ">" +
                    "</div>"
                )
            })
        }
    })
}

function loadNotices() {

    const data = {
        groupId: $('.groupId').val(),
        page: 0,
        display: 5
    }

    $.ajax({
        url: '/api/group/board/get_notice_list',
        type: 'GET',
        data: data,
        success: function (response) {

            if (response.numberOfElements > 0) {
                document.getElementsByClassName('_notice')[0].style.display = 'block'
                $.each(response.content, function (key, value) {
                    var content = (value.content == '') ? '사진을 등록하였습니다.' : value.content
                    $('.noticeContainer').append(
                        "<a href='#' class='_noticeContentBox'>" +
                        "<div class='notice _noticeContent'>" +
                        "<span class='notice _noticeEmphasis'>[공지]</span>" +
                        content +
                        "</div>" +
                        "<div style='margin-top: 3px;' class='notice _noticeDate'>" +
                        new Date(value.regTime).toLocaleString([], {
                            year: 'numeric',
                            month: 'numeric',
                            day: 'numeric',
                            hour: '2-digit',
                            minute: '2-digit'
                        }) +
                        "</div>" +
                        "</a>"
                    )
                })
            }
        }
    })
}

function updateNotice(e, noticeRegistered) {

    var noticeRegistered = (noticeRegistered) ? 'general' : 'notice'

    //type : general, fix, notice
    const boardId = $(e)[0].path[7].children[0].value
    const groupId = $('.groupId').val();
    const username = $('.user').val();

    const data = {
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
        success: function (response) {
            if (JSON.stringify(response).includes('general'))
                alert('해당 게시글 공지를 내렸습니다.')
            else
                alert('해당 게시글을 공지로 등록하였습니다.')
            window.location.reload();
        }
    })
}

function deletePost(e) {

    const boardId = $(e)[0].path[7].children[0].value
    const data = {
        boardId: boardId,
        username: $('.user__name').val()
    }

    $.ajax({
        url: '/api/group/board/delete',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function (response) {
            alert('해당 게시글이 삭제되었습니다.');
            window.location.reload();
        }
    })
}

function postContent() {

    if ($('#board_content_box').val().length > 301)  alert('300자 이하로 작성 가능합니다.')

    else if (infoImgs.length > 0 || $('#board_content_box').val().length > 0) {
        const groupId = $('.groupId').val();
        const formData = new FormData();

        formData.append('groupId', groupId);
        formData.append('writer', $('.user').val());
        formData.append('content', $('#board_content_box').val());

        for (let i = 0; i < infoImgs.length; i++)
            if (infoImgs[i] != null)
                formData.append('board_img', infoImgs[i]);

        $.ajax({

            url: '/api/group/board/post',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function (response) {
                $('.contentContainer').prepend(
                    inputPostBox(response)
                )
                $('._imgPreviewSlider').empty();
                infoImgs = [];
                document.getElementById('board_content_box').value = '';
                document.getElementsByClassName('contentEmptyContiner')[0].style.display = 'none';
            }
        })
    } else alert('작성할 내용을 입력해주세요.');

}

let page = 0;
let display = 6;
let lastPage = false;

function getBoardList() {
    const data = {groupId: $('.groupId').val(), display: display, page: page}

    if (!lastPage) {

        $.ajax({
            url: '/api/group/get_board_list',
            type: 'GET',
            data: data,
            success: function (response) {
                if (response.last == true) lastPage = true;
                if (response.content.length > 0) {
                    $('.contentEmptyContiner').remove();
                    document.getElementsByClassName('_contentListWrapper')[0].setAttribute("style", "hieght:500px");

                    $.each(response.content, function (key, value) {
                        $('.contentContainer').append(
                            inputPostBox(value)
                        );
                    })
                } else document.getElementsByClassName('contentEmptyContiner')[0].style.display = 'block';
            }
        })
        page++;
    }
}

function inputPostBox(value) {
    const boardType = (value.type !== 'general') ? "<span class='notice _noticeText'>공지</span>" : '';
    return "<input type='hidden' value=" + value.boardId + ">" +
        "<div class='boardItemBox' style='animation: fadein 1.5s; margin-bottom: 20px; box-shadow: 0 2px 3px 0 rgba(161, 161, 161, 0.12);'>" +
        "<input type='hidden' class='boardId' value=" + value.boardId + ">" +
        "<div class='contentItemBox'>" +
        "<div class='contentAuthorBox'>" +
        "<div><img class='board _userProfileImg' src=/upload/" + value.user.user.profile.profilePath + "></div>" +
        "<div class='contentInfoBox'>" +
        "<div style='width: 100%; display: inline-flex; justify-content: space-between;'>" +
        "<div>" +
        "<div class='board _username'>" + value.user.user.username + "</div>" +
        "<div class='boardMoreInfoBox' style='display: inline-flex'>" +
        boardType +
        "<div class='board _regDate'>" + new Date(value.regTime).toLocaleString([], {
            year: 'numeric',
            month: 'numeric',
            day: 'numeric',
            hour: '2-digit',
            minute: '2-digit'
        }) + "</div>" +
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
        "</div>" +
        "<div class='boardCommentView' style='background-color: white;'>" +
        "<div style='display: none;' class='commentListView'>" +
            commentBox(value.comments) +
        "</div>" +
        "<div class='commentInputBox'>" +

        "</div>" +
        "</div>"
}

// 덧글 수정
$(document).on('click', '._commentUpdateButton', function(){

    var index = $('._commentUpdateButton').index(this)

    const boardId = $(this).closest('.boardItemBox').find('.boardId')[0].value;
    const commentId = $(this).closest('._commentBox')[0].id.split('_')[1];

    var commentBox = $(this).closest('.boardItemBox').find(".commentContentBox")[index]
    var previousCommentText = commentBox.innerHTML
    commentBox.innerHTML = ''

    const commentTextbox = document.createElement('input');
    commentTextbox.setAttribute('class', 'commentInputbox _commentUpdateText');
    commentTextbox.setAttribute('type', 'text');
    commentTextbox.setAttribute('placeholder', previousCommentText);
    commentTextbox.addEventListener('keyup', (e) => {
        if(e.key === 'Enter'){
            updateCommentText(commentBox, boardId, commentId, commentTextbox.value);
        }
    })
    commentBox.append(
        commentTextbox
    )
})

function updateCommentText(commentBox, boardId, commentId, comment){

    if(comment.length > 0 ){

        const commentContentBox = document.createElement('div');
        commentContentBox.setAttribute('class', 'commentContentBox');
        commentContentBox.innerHTML = comment

        const data = {
            groupId: $('.groupId').val(),
            commentId: commentId,
            boardId: boardId,
            comment: comment,
        }

        $.ajax({
            url: '/api/group/comment/update',
            type: 'POST',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            success: function (response){
                if(JSON.stringify(response).includes('Success')){
                    commentBox.children[0].remove()
                    commentBox.append(commentContentBox)
                }
            }
        })
    } else alert('수정할 내용을 입력해주세요.')
}

$(document).on('click', '._commentDeleteButton', function (e){

    let index = $('._commentDeleteButton').index(this);
    let commentCountBox = $(this).closest('.boardItemBox').find(".commentCountBox")[0];
    let commentBox = document.getElementsByClassName('_commentDeleteButton')[index].parentNode.parentNode.parentNode;
    let commentId = commentBox.getAttribute('id').split('_')[1];

    deleteComment(commentCountBox, commentId);

})

$('.chattingButton').click(function(){
    window.open("/group/chat/" + $('.groupId')[0].value, $('._room_title')[0].value, "width=400, height=650");
})

function commentBox(comments) {
    let result = '';
    if (comments.length > 0) {
        for (let i = 0; i < comments.length; i++) {
            const date = (comments[i].regDate == null) ? new Date().toLocaleString([], {
                year: 'numeric',
                month: 'numeric',
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit'
            }) : new Date(comments[i].regDate).toLocaleString([], {
                year: 'numeric',
                month: 'numeric',
                day: 'numeric',
                hour: '2-digit',
                minute: '2-digit'
            })

            result +=
                "<div id=commentId_" + comments[i].commentId + " class='commentList _commentBox'>" +
                "<div class='commentProfileBox'>" +
                "<img src=/upload/" + comments[i].writer.user.profile.profilePath + ">" +
                "</div>" +
                "<div class='userInfoComment'>" +
                "<div class='commentUsername'>" +
                    comments[i].writer.user.username +
                "</div>" +
                "<div class='commentContentBox'>" +
                    comments[i].comment +
                "</div>" +
                "<div class='commenOptionBox'>" +
                    "<div style='margin-right: 7px;' class='commentRegTime'>" + date +  "</div>" +
                    "<button class='optionButton _commentUpdateButton'>수정</button>" +
                    "<button class='optionButton _commentDeleteButton'>삭제</button>" +
                    "<button class='optionButton _commentReplyButton'>답글</button>" +
                "</div>" +
                "</div>" +
                "</div>"
        }
    }
    return result;
}


$(function () {
    $('._imgUploadDialog').click(function () {
        $('#uploadFile').click();
    })
    $('.uploadBtn').click(function () {
        postContent();
    })
    $('.carousel-control-prev').click(function () {
        const currentPage = document.getElementsByClassName('_currentPageNumber')[0];
        const lastPage = document.getElementsByClassName('_lastPageNumber')[0];
        if ((currentPage.innerHTML * 1) > 1 && (lastPage.innerHTML * 1) >= currentPage.innerHTML)
            currentPage.innerHTML = (currentPage.innerHTML * 1) - 1;
        else
            currentPage.innerHTML = lastPage.innerHTML;
    })
    $('.carousel-control-next').click(function () {
        const currentPage = document.getElementsByClassName('_currentPageNumber')[0];
        const lastPage = document.getElementsByClassName('_lastPageNumber')[0];
        if ((currentPage.innerHTML * 1) < (lastPage.innerHTML * 1))
            currentPage.innerHTML = (currentPage.innerHTML * 1) + 1
        else
            currentPage.innerHTML = 1
    })
    $(document).on('click', '.boardMenuButton', function () {
        generateMenuBox(this)
    })

    document.body.addEventListener('click', removeMenuBox, true);
})

function removeMenuBox() {
    if (document.getElementsByClassName('boardMenuBox').length != 0)
        $('.boardMenuBox').remove();
}

function generateMenuBox(e) {

    if (document.getElementsByClassName('boardMenuBox').length != 0)
        $('.boardMenuBox').remove();

    else {

        const noticeRegistered = $(e).closest('.boardItemBox')[0].childNodes[1].children[0].innerText.includes('공지');
        const noticeText = (noticeRegistered) ? '공지 내리기' : '공지로 등록'

        const line = document.createElement('hr');
        const line2 = document.createElement('hr');

        const boardMenuBox = document.createElement('div');
        boardMenuBox.setAttribute('class', 'boardMenuBox')

        const noticeButton = document.createElement('div')
        noticeButton.setAttribute('class', 'boardmenu _boardNotice')
        noticeButton.addEventListener('click', function (e) {
            updateNotice(e, noticeRegistered)
        })
        noticeButton.innerText = noticeText
        boardMenuBox.append(noticeButton)
        boardMenuBox.append(line)

        const updateButton = document.createElement('div')
        updateButton.setAttribute('class', 'boardmenu _boardUpdate')
        updateButton.innerText = '수정'
        updateButton.addEventListener('click', function (e) {
            update(e)
        })
        boardMenuBox.append(updateButton)
        boardMenuBox.append(line2)

        var deleteButton = document.createElement('div')
        deleteButton.setAttribute('class', 'boardmenu _boardDelete')
        deleteButton.innerText = '삭제'
        deleteButton.addEventListener('click', function (event) {
            deletePost(event)
        })

        boardMenuBox.append(deleteButton)

        e.parentNode.append(boardMenuBox)
    }

}

let infoImgs = [];
$('#uploadFile').change(function (e) {
    const fileArray = Array.prototype.slice.call(e.target.files);
    let imgIndex = 0;

    fileArray.forEach(function (f) {
        if (!f.type.match("image.*")) {
            alert("이미지파일만 업로드가 가능합니다.");
            return;
        }
        infoImgs.push(f);
        const reader = new FileReader();
        reader.onload = function (e) {
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

function previewImgDelete(e) {
    const index = e.id.split('_')[1];
    infoImgs[index] = null;
    $('#' + e.id).parent().remove();
}

// 덧글입력 element 생성
$(document).on('click', '._commentBtn', function () {
    showCommentInputBox(this);
});

// 덧글목록 element 생성
$(document).on('click', '._commentButton', function () {
    const index = $('._commentButton').index(this);

    if (document.getElementsByClassName('commentListView')[index].style.display == 'none')
        document.getElementsByClassName('commentListView')[index].style.display = 'block';
    else
        document.getElementsByClassName('commentListView')[index].style.display = 'none';

    showCommentInputBox(this);
});

function showCommentInputBox(e) {

    const inputBox = $(e).closest('.boardItemBox')[0].childNodes[3].childNodes[1];

    if (!inputBox.hasChildNodes()) {
        const commentInputBox = document.createElement('div');
        commentInputBox.setAttribute('class', 'commentWriteBox');
        commentInputBox.style.cssText = 'padding: 13px;'

        const commentTextbox = document.createElement('input');
        commentTextbox.setAttribute('class', 'commentInputbox commentInputText');
        commentTextbox.setAttribute('type', 'text');
        commentTextbox.setAttribute('placeholder', '작성할 덧글내용을 입력해주세요.');

        const commentSendButton = document.createElement('button');
        commentSendButton.setAttribute('class', 'commentSendBtn');
        commentSendButton.innerHTML = '작성';

        commentInputBox.appendChild(commentTextbox);
        commentInputBox.appendChild(commentSendButton);

        inputBox.appendChild(commentInputBox);
    }
}

// comment Write
$(document).on('click', '.commentSendBtn', function (e) {
    const commentInfoArray = [];
    const boardId = $(this).closest('.boardItemBox').find('.boardId')[0].value;
    const commentListView = $(this).closest('.boardItemBox').find('.commentListView')[0]
    const userId = $(document)[0].all[57].value;
    const commentValue = $(this).closest('.boardItemBox').find('.commentInputText')[0]
    const commentCount = $(this).closest('.boardItemBox').find(".commentCountBox")[0]

    const data = {
        groupId: $('.groupId').val(),
        boardId: boardId,
        userId: userId,
        comment: commentValue.value,
    };

    $.ajax({
        url: '/api/group/board/comment/write',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data: data,
        dataType: 'JSON',
        success: function (response) {
            commentInfoArray.push(response)
            commentListView.style.display = 'block';

            const xmlString = commentBox(commentInfoArray);
            const wrapper = document.createElement('div');
            wrapper.innerHTML = xmlString;

            commentListView.append(
                wrapper.firstChild
            )
            commentCount.innerHTML = (commentCount.innerHTML * 1) + 1;
        }
    })
    $(this).closest('.boardItemBox').children()[3].children[1].children[0].children[0].value = '';
})

function imgShow(id, fileArray) {

    let dataNumber = (fileArray.length >= 4) ? 4 : fileArray.length;
    let imgBox = '';

    if (fileArray.length > 0) {

        let displayBoolean = 'none';
        let imgContainer = '';
        for (let i = 0; i < fileArray.length; i++) {
            const moreButton = (function () {
                if (i == 3 && fileArray.length != dataNumber) {
                    return "<button type=\"button\" class=\"moreMedia _moreMedia\"><span class=\"moreText\">+" + (fileArray.length - dataNumber) + "장</span></button>"
                } else if (i >= 4) {
                    displayBoolean = 'none';
                } else {
                    displayBoolean = 'block';
                }
            })();

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

// 그룹 나가기
$('._groupExitButton').click(function(){
    exitRoom();
})

// Group article 글자수 제한
$('#board_content_box').keyup(function (){
    const article = $('#board_content_box')
    const currentWords = document.getElementsByClassName('currentWords')[0];

    if(article.val().length < 301)
        currentWords.innerHTML = article.val().length
    else
        article.val(article.val().substring(0, 300))
})

document.addEventListener("DOMContentLoaded", function (event) {
    const scrollpos = localStorage.getItem('scrollpos');
    if (scrollpos) {
        window.scrollTo(0, 0);
        loadNotices();
        getBoardList();
        loadLatestImages();
        loadLatestComments();
    }
});

window.onbeforeunload = function (e) {
    localStorage.setItem('scrollpos', window.scrollY);
};

$(document).scroll(function () {
    if ($(window).scrollTop() + $(window).height() == getDocHeight())
        getBoardList();
});

function getDocHeight() {
    const D = document;
    return Math.max(
        D.body.scrollHeight, D.documentElement.scrollHeight,
        D.body.offsetHeight, D.documentElement.offsetHeight,
        D.body.clientHeight, D.documentElement.clientHeight
    );
}