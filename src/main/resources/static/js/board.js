$(document).ready(function () {
    $('#delete-Post').on('click', deletePost);
    $('#update-Post').on('click', moveUpdate);
    $('#btn_update').on('click', updatePost);
    $('#toggleBtn').on('click', dropBtnSelect);
});

function deletePost() {
    let data = {
        boardId: $('.boardId').val(),
        writerId: $('.item__writer').text()
    };

    $.ajax({
        type: 'POST',
        url: '/board/delete',
        data: JSON.stringify(data),
        contentType: 'application/json',
        success: function (result) {
            if (result === '삭제 성공') {
                alert('해당 게시글을 삭제하였습니다.');
                location.href = '/';
            }
        },
        })
        .done(function (resp) {
            if(resp.includes("권한")){
                alert('권한이 없습니다');
            }
        })
        .fail(function (error) {
            console.log(error);
        });
}

function moveUpdate() {
    var boardId = $('.boardId').val();

    $.ajax({
        type: 'GET',
        url: '/board/article/update/' + boardId,
    });

    location.href = '/board/article/update/' + boardId;
}

function dropBtnSelect(){

    let div = document.getElementsByClassName('dropdown-menu');
  
    for (var i = 0; i < div.length; i++) {
        for (var j = 0; j < div[i].children.length; j++) {
            div[i].children[j].addEventListener('click', function () {
                this.parentNode.previousElementSibling.innerHTML =
                    this.innerHTML;
            });
        }
    }

}
