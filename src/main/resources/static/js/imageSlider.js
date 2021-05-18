$(document).ready(function () {
    // $(".grid-box").on("click", clickImg(this));
    $('.grid-box').click(function () {
        var id_check = $(this).attr('id');
        clickImg(id_check);
    });
});

function clickImg(small) {
    var imgBox = document.getElementById('imgBox');
    var smallbox = document.getElementById(small);
    imgBox.src = smallbox.src;
}