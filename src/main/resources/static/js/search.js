$(document).ready(function(){
    $(".icon").on("click", function(){
        keyword = document.getElementById("search-value").value;
        location.href = '/board/article/search=' + keyword;
    });

    $('.accuracy').on("click", accuracy);
    $('.Bestprice').on("click", Bestprice);
    $('.Latest').on("click", Latest);
});

function accuracy(){
    location.href = '/board/search=1&display=30&order=accuracy&page=0';
    colorChnage(this.className);
}

function Bestprice(){
    location.href = '/board/search=1&display=30&order=Bestprice&page=0';
    colorChnage(this.className);
}

function Latest(){
    location.href = '/board/search=1&display=30&order=Latest&page=0';
    colorChnage(this.className);
}

function colorChnage(inputClassname){
    var array = []
    Array.from(document.getElementsByClassName('sort__Type')[0].children, 
                item => array.push(item.className));

    for(let i = 0; i < array.length; i ++){
        if(array[i] == inputClassname){
            document.getElementsByClassName(array[i])[0].style["color"] = "rgb(228, 28, 28)";
        }else{
            document.getElementsByClassName(array[i])[0].style["color"] = "rgb(0, 0, 0)";
        }
    }
};