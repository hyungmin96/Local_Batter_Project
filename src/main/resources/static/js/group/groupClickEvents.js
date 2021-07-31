
// 그룹 가입
$(document).on('click', '._groupEnterButton', function(){
    enterGroup();
})

// 그룹 나가기기
$(document).on('click', '._groupExitButton', function(){
    exitGroup();
})

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
                "<img src=/upload/" + fileArray[i] + " alt='' class='_image'>" +
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
        isMember();
        getBoardList();
        // loadNotices();
        // loadLatestImages();
        // loadLatestComments();
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