
// 중복요청 방지
var flag = false;
var page = 0;

$(window).scroll(function(){
    if($(window).scrollTop() + 150 >= $(document).height() - $(window).height()){
        if(!flag){
            flag = true;
            loadData();
        }
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

            $.each(data.content, function(key, value){

                var boardId = value.id;
                var title = value.title;
                var price = value.price;
                var displayDate = value.displayDate;
                var location = value.location;
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