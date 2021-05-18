$(document).ready(function () {
    $('#delete-Post').on('click', deletePost);
    $('#update-Post').on('click', moveUpdate);
    $('#btn_update').on('click', updatePost);
});

function deletePost() {
    let data = {
        boardId: $('.boardId').text(),
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
            console.log(resp);
        })
        .fail(function (error) {
            console.log(error);
        });
}

function moveUpdate() {
    var boardId = $('.boardId').text();

    $.ajax({
        type: 'GET',
        url: '/board/article/update/' + boardId,
    });

    location.href = '/board/article/update/' + boardId;
}
