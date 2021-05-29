
// 중복요청 방지
var flag = false;
var page = 0;

$(window).scroll(function(){
    if($(window).scrollTop() + 150 >= $(document).height() - $(window).height()){
        // 스크롤이 맨 밑에 왔을때
        if(!flag){
            flag = true;
            loadData();
        }
    }else{
        // 아닐때
    }
});

function loadData(){

    var display = 60;

    $.ajax({

        url: '/boards/api/',
        type: 'GET',
        data: {page: page, display: display},
        dataType : 'json',
        success: function(data){

            var html = '';

            var todayItemBox = $('.today-item-list');

            var title;
            var price;
            var displayDate;
            // var descryption;
            // var location;
            // var wrtier;

            $.each(data.content, function(key, value){

                boardId = value.id;
                title = value.title;
                price = value.price;
                descryption = value.descryption;
                displayDate = value.displayDate;
                // location = value.location;
                // wrtier = value.wrtier;

                let fileName = value.files.length > 0 ? value.files[0].tempName : '';

                html += '<div class="img_box fadein">';
                html += '<div onclick="goToUrl(' + boardId + ')" class="today-item-box">';
                html += '<div class="today-box">';
                html += '<img src="/upload/' + fileName + '" onerror="this.style.display=none">';
                html += '</div>';
                html += '<div class="today-detail-box">';
                html += '<div class="type">';
                html += '<div class="title">' + title + '</div>';
                html += '<div class="board-line"></div>';
                html += '<div class="line">';
                html += '<div class="price">' + price + '<span class="k-money">원</span></div>';
                html += '<span class="time">' + displayDate + '</span>';
                html += '</div>';
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