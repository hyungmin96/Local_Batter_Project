$(document).ready(function(){
    getBoardList();
})

$(function(){
    $('._imgUploadDialog').click(function (){
        $('#uploadFile').click();
    })
})

let infoImgs = [];
$('#uploadFile').change(function (e){

    var fileArray = Array.prototype.slice.call(e.target.files);
    console.log(fileArray);

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
                    "<img src=" + e.target.result + ">" +
                "</div>"
            );

            index++;
        }
        reader.readAsDataURL(f);
    });
})

function postContent(){

    var groupId = $('.groupId').val();

    var formData = new FormData();

    formData.append('groupId', groupId);
    formData.append('writer', $('.user').val());
    formData.append('content', document.getElementById(chatroom_comment_box).value);

    $.ajax({

        url: '/api/group/post/' + groupId,
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,

    })

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
            console.log(response)
            if(response.content.length > 1){

                $('.contentEmptyContiner').remove();
                document.getElementsByClassName('_contentListWrapper')[0].setAttribute("style","hieght:500px");

                $.each(response.content, function(key, value){

                    $('#contentWrapper').append(
                        "<input type='hidden' value=" + value.boardId + ">" +
                        "<div style='margin-bottom: 20px; box-shadow: 0 2px 3px 0 rgba(161, 161, 161, 0.12);'>" +
                            "<div class='contentItemBox'>" +
                                "<div class='contentAuthorBox'>" +
                                    "<div><img class='board _userProfileImg' src=/upload/" + value.groupUsersEntity.user.profile.profilePath + "></div>" +
                                    "<div class='contentInfoBox'>" +
                                        "<div class='board _username'>" + value.groupUsersEntity.user.username + "</div>" +
                                        "<div class='board _regDate'>" + new Date(value.regDate).toLocaleTimeString() + "</div>" +
                                    "</div>" +
                                "</div>" +
                                "<div class='board _content' style='padding: 20px;'>" + value.content + "</div>" +
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