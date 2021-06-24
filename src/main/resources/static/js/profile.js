var display = 8;
var profileFile = profile__img;
var pagination = false;

$(document).ready(function(){
    loadBoardData();
    loadCommentData();
})

function loadBoardData(e, page = 0, display = 8){

    $.ajax({

        url: '/api/transaction/dealList',
        type: 'GET',
        data: 'type=complete&page=' + page + '&display=' + display,
        success: function(response){

            loadPagination(e, 'board__number__box', response.totalPages + 1);

            $.each(response.content, function(key, value){

                $('.profile__exchange__list').append(
                    "<div class='comment__box'>" +
                    "<div class='score'>" +
                    "<img src='/images/star.png'>" +
                    "<img src='/images/star.png'>" +
                    "<img src='/images/star.png'>" +
                    "<img src='/images/star.png'>" +
                    "<img src='/images/star.png'>" +
                    "</div>" +
                    "<div class='comment'>" +
                        value.boardId.title +
                    "</div>" +
                    "<div class='writer'>" +
                    value.boardId.writer +
                    "</div>" +
                    "<div class='date'>" +
                    new Date(value.boardId.createTime).toLocaleDateString([], {'hour': '2-digit', 'minute' : '2-digit'}) + 
                    "</div>" +
                    "</div>"
                );

            })

        }

    })

}

function loadCommentData(e, pages = 0){

    $.ajax({

        url: '/api/profile/comments',
        type: 'GET',
        data: {page: pages, display: display},
        success: function(response){

            loadPagination(e, 'page__number__box', response.totalPages);

            document.getElementsByClassName('review__count')[0].innerHTML = response.totalElements + '개';

            $.each(response.content, function(key, value){
                $('.comment__list').append(
                    "<div class='comment__box'>" +
                    "<div class='score'>" +
                    "<img src='/images/star.png'>" +
                    "<img src='/images/star.png'>" +
                    "<img src='/images/star.png'>" +
                    "<img src='/images/star.png'>" +
                    "<img src='/images/star.png'>" +
                    "</div>" +
                    "<div class='comment'>" +
                        value.commentValue +
                    "</div>" +
                    "<div class='writer'>" +
                    value.writer +
                    "</div>" +
                    "<div class='date'>" +
                    new Date(value.regDate).toLocaleDateString([], {'hour': '2-digit', 'minute' : '2-digit'}) + 
                    "</div>" +
                    "</div>"
                );

            })

        }

    })

}

function loadPagination(e, containerName, pages){

    Array.from(document.getElementsByClassName(containerName)[0].children, item=>{
        item.style.backgroundColor = 'white';
    });

    if(e != null)
        e.style.backgroundColor = 'rgb(236, 236, 236)';

    var dataType = null;
    if(containerName.includes('page')){
        $('.comment__list').empty();
    }
    else{
        $('.profile__exchange__list').empty();
    }

    if(!pagination){
        pagination = true;
        if(pages < 1) pages = 1;
        $('.' + containerName).empty();
        for(var i = 1; i <= pages; i++){
            $('.' + containerName).append(
                "<li id='pagenum-" + i + "' class='page__number' onclick='loadBoardData(this, " + (i - 1) + ");'>" + 
                "<a href='javascript:void(0)'>" + i + "</a></li>"
            );
        }
    }
}

$(function(){
    $('#profile__img__upload').on("change", function(e){
        // 프로필 사진 미리보기
        var reader = new FileReader();
        reader.onload = function(f){
            profile__img.src = f.target.result;
            profileFile = e.target.files[0];
        }
        reader.readAsDataURL(e.target.files[0]);
    })
})

$('#img__upload__btn').on("click", function() {
    $('#profile__img__upload').click();
});

$('.setting__profile__btn').on("click", function() {
    $('#myModal').show();
});

$('#profile__save__btn').on("click", function(){
    profileSave();
})

function close_pop(flag) {
    $('#myModal').hide();
};

function profileSave(){
    
    const profileData = new FormData();


    profileData.append('nickname', $('#username').val());
    profileData.append('introduce', $('#introduce').val());
    profileData.append('profileImg', profileFile);
    profileData.append('location', $('#location').val());
    profileData.append('preferTime', $('#prefertime').val());
    profileData.append('accountNumber', $('#account').val());

    $.ajax({

        url: '/api/profile/save',
        type: 'POST',
        data: profileData,
        dataType: 'text',
        contentType: false,
        processData: false,
        success: function(response){
            if(response != null){
                alert('성공적으로 수정하였습니다.');
                location.reload();
            }else{
                alert('비밀번호가 잘못되었습니다.');
            }
        }

    })

}