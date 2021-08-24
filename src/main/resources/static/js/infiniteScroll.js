
// 중복요청 방지
var flag = false;
var page = 0;

$(window).scroll(function(){
    if($(window).scrollTop() + 10 >= $(document).height() - $(window).height()){
        if(!flag){
            flag = true;
            loadData();
        }
    }
});

function loadData(){

    var display = 60;

    $.ajax({

        url: '/api/v2/group/board/get_exchange_list',
        type: 'GET',
        data: { pageNum: page, display: display},
        success: function(data){

            var html = '';

            var todayItemBox = $('.groupExchangeBoardList');

            $.each(data.content, function(key, value){

                console.log(value)
                var boardId = value.id;
                var title = value.title;
                var price = value.price;
                var location = value.location;

                let fileName = value.thumbnail != null ? value.thumbnail : '';
                html += '<div class="img_box fadein" style="width: 230px; margin: 0 12px 0 12px;">';
                html += '<div onclick="goToUrl(' + boardId + ')" class="today-item-box">';
                html += '<div class="today-box">';
                html += '<img src="/upload/' + fileName + '" onerror="this.style.display=none">';
                html += '</div>';
                html += '<div class="today-detail-box">';
                html += '<div class="type">';
                html += '<div class="title">' + title + '</div>';
                html += '<div class="board-line"></div>';
                html += '<div class="line">';
                html += '<div class="price">' + convert(price) + '<span class="k-money">원</span></div>';
                html += '</div>';
                html += '<hr style="border: none; height: 1px; background-color: #848484; margin: 5px 0 5px 0;"/>';
                html += '<span class="badge bg-secondary">' + location + '</span>';
                html += '</div>';
                html += '</div>';
                html += '</div>';
                html += '</div>';

            });

            todayItemBox.append(html);

            page++;
            flag = false;
        }

    })

}

function goToUrl(id){
    location.href = 'http://localhost:8000/board/article/' + id;
}

function convert(num){
    var len, point, str;
    num = num + "";
    point = num.length % 3 ;
    len = num.length;
    str = num.substring(0, point);
    while (point < len) {
        if (str != "") str += ",";
        str += num.substring(point, point + 3);
        point += 3;
    }
    return str;
}