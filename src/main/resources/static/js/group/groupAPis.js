const userInfo = {
    userId: $('.g_user_id').val(),
    groupId: $('.groupId').val(),
    username: '',
    userProfile: ''
}



function updateGroupBoard(index, boardId){

    const postBox = document.getElementById('board_content_box')
    const title = document.getElementsByClassName('groupTitleInputBox')[index].value

    if (postBox.innerText.length > 301)  alert('300자 이하로 작성 가능합니다.')
    else if (infoImgs.length > 0 || postBox.innerText.length > 0) {
        var boardId = boardId;
        var formData = new FormData();

        formData.append('title', title);
        formData.append('content', postBox.innerHTML.split('<div class=\"imgBox\"')[0]);
        formData.append('boardId', boardId);

        for (let i = 0; i < infoImgs.length; i++)
            if (infoImgs[i] != null)
                formData.append('board_img', infoImgs[i]);

        for (let i = 0; i < deleteImageIndex.length; i++)
            formData.append('deleteImageIndex', deleteImageIndex[i]);

        $.ajax({
            url: '/api/v2/group/board/update',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function () {
                alert('게시글을 수정하였습니다.')
                $('#groupBoardWriteModal').modal('hide')
            }
        })
    } else alert('작성할 내용을 입력해주세요.');
}

function postContent() {

    const postBox = document.getElementById('board_content_box')

    let category = ($('.boardDropDownButton')[0].innerHTML === '교환 글') ? 'exchange' : 'article'
    if (postBox.innerText.length > 301) alert('300자 이하로 작성 가능합니다.')

    else if (infoImgs.length > 0 || postBox.innerText.length > 0) {
        const groupId = $('.groupId').val();
        const formData = new FormData();
        formData.append('locationDetail', $('.locationDetailTinfo')[0].value)
        formData.append('title', $('.groupTitleInputBox').val())
        formData.append('residence', $('#address').val())
        formData.append('buildingcode', $('#buildingcode').val())
        formData.append('detailAddr', $('.detailAddr')[0].value)
        formData.append('longitude', $('.longitudeValue')[0].value)
        formData.append('latitude', $('.latitudeValue')[0].value)
        formData.append('location', $('#exchange_address').val())
        formData.append('userId', $('.g_user_id').val());
        formData.append('groupId', groupId);
        formData.append('writer', $('.g_user_id').val());
        formData.append('content', postBox.innerHTML.split('<div class=\"imgBox\"')[0]);
        formData.append('preferTime', $('#exchange_time').val())
        formData.append('category', category)

        for (let i = 0; i < infoImgs.length; i++)
            if (infoImgs[i] != null)
                formData.append('board_img', infoImgs[i]);

        $.ajax({
            url: '/api/v2/group/board/post',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            success: function (response) {
                $('.contentContainer').prepend(inputPostBox(response))
                $('._imgPreviewSlider').empty();
                infoImgs = [];
                document.getElementById('board_content_box').value = '';
                if (document.getElementsByClassName('contentEmptyContiner')[0] != null)
                    document.getElementsByClassName('contentEmptyContiner')[0].style.display = 'none';
                $('#groupBoardWriteModal').modal('hide')
            }
        })
    } else alert('작성할 내용을 입력해주세요.');
}


function exchangeInfoShow(value){
    let result = ''
    if(value.boardCategory == 'exchange'){
        result = "" +
            "<div class='locationInfoBox'>" +
            "<div style='display: inline-flex;' class='exchangeInfoContainer'>" +
            "<div>" +
            "<img src='/images/boardDetail/google_maps_old_98px.png'>" +
            "</div>" +
            "<div class='locationTextInfoBox'>" +
            "<div>" + value.location + "</div>" +
            "<div>" + value.locationDetail + "</div>" +
            "</div>" +
            "</div>" +
            "</div>"
    }
    return result
}


function updateGroup(){

    let formData = new FormData();

    formData.append('groupId', userInfo.groupId)
    formData.append('userId', userInfo.userId)
    formData.append('title', $('#Group__title').val())
    formData.append('description', $('#Group__desciption').val())
    if(groupProfileImg.length > 0)
        formData.append('files', groupProfileImg[0])

    $.ajax({
        url: '/api/group/update',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function(){
            alert('그룹정보를 수정 하였습니다.')
            window.location.reload();
        }
    })
}

function getBoardInfo(boardId){

    const data = {
        groupId: $('.groupId').val(),
        boardId: boardId
    }

    $.ajax({
        url: '/api/v2/group/board/getInfo',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function (response){
            $('.boardModal').empty()
            $('.boardModal').append(showBoardInfo(response))
        }
    })
}

function deleteGroup(){
    const data = {
        groupId: userInfo.groupId,
        userId: userInfo.userId
    }

    $.ajax({
        url: '/api/group/delete',
        type: 'POST',
        data: data,
        success: function(){
            alert('그룹을 삭제하였습니다.')
            window.location.href = 'http://localhost:8000/product/group'
        }
    })
}

let memberPage = 0;
function getMemberList(){

    const data = {
        page: memberPage,
        display: 50,
        groupId: $('.groupId').val()
    }

    $.ajax({
        url: '/api/group/get_member_list',
        type: 'GET',
        data: data,
        success: function (response){
            $.each(response.content, function(key, value){
                $('.memberList').append(
                    "<div class='userList _userItemBox' style='cursor: pointer;'>" +
                        "<div class='userProfileBox'>" +
                            "<img src=/upload/" + value.profile + ">" +
                        "</div>" +
                        "<div class='userName' style='margin: auto 0 auto 8px;'>" +
                            value.username +
                        "</div>" +
                    "</div>"
                )
            })
        }
    })
    memberPage++;
}

function enterGroup(){

    const data = {
        userId: $('.g_user_id').val(),
        groupId: $('.groupId').val(),
    }

    $.ajax({
        url: '/api/group/enter',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function(response){
            alert('그룹에 가입하였습니다.')
            window.location.reload();
        }
    })
}

function exitGroup(){

    const data = {
        userId: $('.g_user_id').val(),
        groupId: $('.groupId').val(),
    }

    $.ajax({
        url: '/api/group/exit',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function(response){
            alert('그룹에서 나갔습니다.')
            window.location.reload();
        }
    })
}

function isMember(){

    const data = {
        userId: $('.g_user_id').val(),
        groupId: $('.groupId').val(),
    }

    $.ajax({
        url: '/api/group/check/isMember',
        type: 'GET',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function(response){
            if(response != '')
                $('._groupExitButton').show()
            else
                $('._groupEnterButton').show()
        }
    })
}

function deleteComment(e, commentId){

    const data = {
        groupId: globalThis.groupId,
        userId: $('.user').val(),
        commentId: commentId
    }

    $.ajax({
        url: '/api/v2/group/comment/delete',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function(response){
            $('#commentId_' + commentId).remove();
            e.innerHTML = (e.innerHTML * 1) - 1;
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
        url: '/api/v2/group/board/get_notice_list',
        type: 'GET',
        data: data,
        success: function (response) {
            if (response.length > 0) {

                $.each(response, function(key, value){
                document.getElementsByClassName('_notice')[0].style.display = 'block'
                    var content = (value.content == '<br>' || value.content == '') ? '사진을 등록하였습니다.' : value.content
                    $('.noticeContainer').append(
                        "<div class='showBoard _boardIdInfo'>" +
                        "<input type='hidden' name='boardId' class='boardId_" + value.boardId + "'>" +
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
    const userId = $('.g_user_id').val();
    const groupId = $('.groupId').val();
    const username = $('.user').val();

    const data = {
        groupId: groupId,
        userId: userId,
        boardId: boardId,
        type: noticeRegistered,
        username: username
    }

    $.ajax({
        url: '/api/v2/group/board/update/notice',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function (response) {
            if (JSON.stringify(response).includes('general'))
                alert('해당 게시글 공지를 내렸습니다.')
            else
                alert('해당 게시글을 공지로 등록하였습니다.')
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
        url: '/api/v2/group/board/delete',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function (response) {
            alert('해당 게시글이 삭제되었습니다.');
            window.location.reload();
        }
    })
}

let display = 6;
let lastPage = false;
let boardPage = 0;
function getBoardList() {
    const data = {groupId: $('.groupId').val(), display: display, page: boardPage}

    if (!lastPage) {

        $.ajax({
            url: '/api/v2/group/board/get_board_list',
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
        boardPage++;
    }
}

function showBoardInfo(value){
    const boardType = (value.type !== 'general') ? "<span class='notice _noticeText'>공지</span>" : '';
    return "<input type='hidden' value=" + value.boardId + ">" +
        "<div class='boardItemBox' style='animation: fadein 1.5s; margin-bottom: 20px;>" +
        "<input type='hidden' class='boardId' value=" + value.boardId + ">" +
        "<div class='contentItemBox'>" +
        "<div class='contentAuthorBox'>" +
        "<div><img class='board _userProfileImg' src=/upload/" + value.profilePath + "></div>" +
        "<div class='contentInfoBox'>" +
        "<div style='width: 100%; display: inline-flex; justify-content: space-between;'>" +
        "<div>" +
        "<div class='board _username'>" + value.username + "</div>" +
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
        imgShow(value.boardId, value.boardFiles)
}

function inputPostBox(value) {
    const boardType = (value.type !== 'general') ? "<span class='notice _noticeText'>공지</span>" : '';
    const boardCategory = (value.boardCategory == 'exchange') ? "<span class='boardCategory'>교환 게시글</span>" : '';

    return "<div class='boardItemBox' style='animation: fadein 1.5s; margin-bottom: 20px; box-shadow: 0 2px 3px 0 rgba(161, 161, 161, 0.12);'>" +
        "<input type='hidden' class='boardId' value=" + value.boardId + ">" +
        "<div class='contentItemBox'>" +
        "<div class='contentAuthorBox'>" +
        "<div><img class='board _userProfileImg' src=/upload/" + value.profilePath + "></div>" +
        "<div class='contentInfoBox'>" +
        "<div style='width: 100%; display: inline-flex; justify-content: space-between;'>" +
        "<div>" +
        "<div class='board _username'>" + value.username + "</div>" +
        "<div class='boardMoreInfoBox' style='display: inline-flex'>" +
        boardType +
        boardCategory +
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
        "<div class='groupBoardTitleBox' style='padding: 10px;'>" +
        "<div id='groupBoardTitleInputBox' class='groupBoardTitle'>" + value.title + "</div>" +
        "<hr style='border: none; height: 1px; background-color: #cccccc; margin-top: 10px' />" +
        "</div>" +
        "<div class='board _content' style='padding: 5px 10px 0 10px;'>" + value.content + "</div>" +
        imgShow(value.boardId, value.boardFiles) +
        exchangeInfoShow(value) +
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
        var previousCommentText = commentBox.innerText
        commentBox.innerHTML = ''

        const commentTextbox = document.createElement('input');
        commentTextbox.setAttribute('class', 'commentInputbox _commentUpdateText');
        commentTextbox.setAttribute('type', 'text');
        commentTextbox.setAttribute('placeholder', previousCommentText);

        commentTextbox.addEventListener('keyup', (e) => {
            if(e.key === 'Enter')
                updateCommentText(commentBox, boardId, commentId, commentTextbox.value);
            else if(e.key === 'Escape')
                commentBox.innerHTML = previousCommentText
        })
        commentBox.append(commentTextbox)
        commentTextbox.focus()

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
            url: '/api/v2/group/comment/update',
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
                "<img src=/upload/" + comments[i].profilePath + ">" +
                "</div>" +
                "<div class='userInfoComment'>" +
                "<div class='commentUsername'>" +
                    comments[i].username +
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

// comment Write
$(document).on('click', '.commentSendBtn', function (e) {
    const commentInfoArray = [];
    const boardId = $(this).closest('.boardItemBox').find('.boardId')[0].value;
    const commentListView = $(this).closest('.boardItemBox').find('.commentListView')[0]
    const commentValue = $(this).closest('.boardItemBox').find('.commentInputText')[0]
    const commentCount = $(this).closest('.boardItemBox').find(".commentCountBox")[0]

    const data = {
        groupId: $('.groupId').val(),
        boardId: boardId,
        userId: $('.g_user_id').val(),
        comment: commentValue.value,
    };

    $.ajax({
        url: '/api/v2/group/comment/write',
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

