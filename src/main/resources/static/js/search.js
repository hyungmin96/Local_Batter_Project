$(document).ready(function(){
    $(".icon").on("click", function(){
        var search = document.getElementById("search-value").value;
        location.href = '/board/article/search=' + search;
    });
});