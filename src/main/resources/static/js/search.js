$(document).ready(function(){
    $(".icon").on("click", function(){
        keyword = document.getElementById("search-value").value;
        location.href = '/board/search/products/search=' + keyword;
    });

    $(document).ready(function(){
        
        callBoardsDataToJson();
    
        $('.accuracy').on("click", accuracy);
        $('.Bestprice').on("click", Bestprice);
        $('.Latest').on("click", Latest);
        $('.next').on("click", nextBtn);
        $('.previous').on("click", previousBtn);
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
        $('.active').attr('id') == 'undefined'
            ? 'accuracy'
            : $('.active').attr('id');

    $.ajax({
        url:
            '/board/products/search=' +
            keyword +
            '&display=30&order=' +
            order +
            '&page=' +
            page,
        type: 'GET',
        dataType: 'json',
        success: function (data) {
            var search__items__container = $('.search__item__list');
            search__items__container.empty();

            var html = '';
            var pageHtml = '';
            var totalPages;
            var pageArray = [];
            Array.from(
                document.getElementsByClassName('page__number__box')[0]
                    .children,
                (item) => {
                    pageArray.push(item.className);
                }
            );

            $.each(data.content, function (index, value) {
                totalPages = data.totalPages;

                let fileName =
                    value.files.length > 0 ? value.files[0].tempName : '';

                html +=
                    '<div id="item__' +
                    value.id +
                    '"class="search__item__box">';
                html += '<a href="/board/article/' + value.id + '">';
                html += '<div class="search__box__img">';
                html +=
                    '<img src="/upload/' +
                    fileName +
                    '"onerror=this.src="/images/noImage.png" style="height: 180px; display=none">';
                html += '</div>';
                html += '<div class="search__detail__box">';
                html += '<div class="info__box">';
                html +=
                    '<div class="title" style="color: black;">' +
                    value.title +
                    '</div>';
                html += '<div class="line">';
                html +=
                    '<div class="price">' +
                    value.price +
                    '<span class="k-money">원</span></div>';
                html += '<span class="time">' + value.displayDate + '</span>';
                html += '</div>';
                html += '</div>';
                html += '</div>';
                html += '</a>';
                html += '</div>';
                html += '</div>';

                //data.totalPages
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
