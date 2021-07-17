$(document).ready(function(){
    $(".icon").on("click", function(){
        keyword = document.getElementById("search-value").value;
        location.href = '/board/search/products/search=' + keyword;
    });
    
    
    $(function(){
        if(location.href.includes('board/search/products'))
            callBoardsDataToJson();
        $('.accuracy').on("click", accuracy);
        $('.Bestprice').on("click", Bestprice);
        $('.Latest').on("click", Latest);
        $('.next').click(function(e){
            nextBtn(e.currentTarget.parentElement.children[3].className);
        });
        $('.previous').click(function(e){
            previousBtn(e.currentTarget.parentElement.children[3].className);
        });
    });

});

function accuracy(){
    colorChnage(this.className);
    callBoardsDataToJson();
}

function Bestprice(){
    colorChnage(this.className);
    callBoardsDataToJson();
}

function Latest(){
    colorChnage(this.className);
    callBoardsDataToJson();
}

function callBoardsDataToJson(page = 0) {
    var keyword = $('.keyword').text().replaceAll("'", '');
    var order =
        $('.active').attr('id') == 'undefined' ? 'accuracy' : $('.active').attr('id');

    $.ajax({
        type: 'GET',
        url: '/board/products/search=' + keyword + '&display=30&order=' + order +'&page=' +page,
        success: function (data) {
            var search__items__container = $('.search__item__list');
            search__items__container.empty();

            var html = '';

            $.each(data.content, function (index, value) {

                let totalPages = data.totalPages;
                let fileName = value.files.length > 0 ? value.files[0].tempName : '';

                html += '<div id="item__' + value.id + '" class="search__item__box">';
                html += '<a href="/board/article/' + value.id + '">';
                html += '<div class="search__box__img">';
                html +='<img src="/upload/' + fileName + '"onerror=this.src="/images/noImage.png" style="height: 180px; display=none">';
                html += '</div>';
                html += '<div class="search__detail__box">';
                html += '<div class="info__box">';
                html += '<div class="title" style="color: black;">' + value.title + '</div>';
                html += '<div class="line">';
                html += '<div class="price">' + value.price + '<span class="k-money">원</span></div>';
                html += '<span class="time">' + value.displayDate + '</span>';
                html += '</div>';
                html += '<hr style="border: none; height: 1px; background-color: #848484; margin: 5px 0 5px 0;"/>';
                html += '<span class="badge bg-secondary">' + value.location + '</span>';
                html += '</div>';
                html += '</a>';
                html += '</div>';
                html += '</div>';
            });

            search__items__container.html(html);
        },
    });
}

function colorChnage(inputClassname){

    let array = ['accuracy', 'Bestprice', 'Latest'];

    let index = 0;
    Array.from(document.getElementsByClassName('sort__Type')[0].children, 
        item => {
            item.className = array[index];
            index++;
        });

    document.getElementsByClassName(inputClassname)[0].className = 'active';

};
