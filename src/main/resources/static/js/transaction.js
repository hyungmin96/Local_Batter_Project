
var page = 0;
var display = 10;
var pagination = false;

function dropBtnSelect(e){
    var dropBox = e.parentNode.parentNode.children[0].id;
    document.getElementById(dropBox).innerHTML = e.text;

    if(dropBox != 'manner__box')
    if(e.text == '현재 거래중인 상품목록'){
        page = 0
        pagination = false;
        loadTransactionData();
    }else if(e.text == '거래 완료된 상품목록'){
        page = 0
        pagination = false;
        loadTransactionData(null, 'complete');
    }else{
        page = 0
        pagination = false;
        loadTransactionData(null, 'cart');
    }
}

$(document).ready(function(){
    loadTransactionData();
})

function loadTransactionData(e = null, type = 'transaction', page = 0){

    $.ajax({

        url: '/api/transaction/dealList',
        type: 'GET',
        data: {type: type, page: page, display: display},
        success: function(response){

            loadPagination(type, response.totalPages);

            Array.from(document.getElementsByClassName('page__number__box')[0].children, item=>{
                item.style.backgroundColor = 'white';
            });

            if(e != null)
                e.style.backgroundColor = 'rgb(236, 236, 236)';

            $('.product__items__container').empty();

            $.each(response.content, function(key, value){

                var writer;
                var data = dataQuarterProcess(type, key, value);

                if($('.user__name').text() != value.buyer.username)
                    writer = value.buyer.username;
                else
                    writer = value.seller.username;

                $('.product__items__container').append(
                    "<div>" + 
                    "<div class='container__product__box'>" + 
                    data.status + 
                    "<a href='http://localhost:8000/" + data.files + "'><img class='thumbnail__img' src=/" + data.files + ">" + 
                    "<div style='width: 300px;'><a href='http://localhost:8000/board/article/" + value.boardId.id + "' target='_blank'>" +  value.boardId.title + "</a></div>" + 
                    "<div style='width: 100px;'>" + value.boardId.price + "원</div>" + 
                    "<div style='width: 100px;'><a href='http://localhost:8000/profile/user=" + writer + "' target='_blank'>" + writer + "</a></div>" + 
                    "<div style='color: rgb(185, 185, 185); width: 100px;'>" + new Date(value.createTime).toLocaleDateString() + "</div>" + 
                    data.action +
                    "</div>" + 
                    "<hr style='border: none; height: 1px; background-color: rgb(185, 185, 185);'/>" +
                    "</div>" + 
                    "</div>" +
                    "</div>"
                );

                window.scrollTo(0,0);

            })
        }
    })
}

function dataQuarterProcess(type, key, value, pageRequired = true, page = 0){

    var userId = value.buyer.id;
    var sellerId = value.seller.id;
    var boardId = value.boardId.id;
    var writer = value.buyer.username;

    var data = {'status' : '', 'files' : '', 'action' : ''};
    
    if(value.boardId.files.length > 0 )
        data['files'] = 'upload/' + value.boardId.files[0].tempName;
    else
        data['files'] ='images/noimage.png';
    // 거래 완료
    if(type == 'complete'){
        data['status'] = "<div style='width: 55px; color: #ccc'>거래완료</div>";
        data['action'] = (function(){
            if($('.user__name').text() == value.sellerCommentId || $('.user__name').text() == value.buyerCommentId)
                return "<button onclick='showModal(" + userId + ", " + sellerId + ", " + boardId + ", " + writer + ");' style='display: none; margin: auto auto; height: 40px;' type='button' id='submit__btn__" + value.boardId.id + "' class='btn btn-primary'>리뷰작성</button>";
            else
                return "<button onclick='showModal(" + userId + ", " + sellerId + ", " + boardId + ", " + writer + ");' style='margin: auto auto; height: 40px;' type='button' id='submit__btn__" + value.boardId.id + "' class='btn btn-primary'>리뷰작성</button>";
        })();

    // 관심물품
    }else if(type == 'cart'){
        
        data['status'] = "<div style='width: 55px; color: #ccc'>" + (key + 1) + "</div>";
        data['action'] = 
        "<div style='margin: auto auto'>" + 
        "<button onclick='javascript:cartToTransaction(" + value.boardId.id + ", " + value.seller.id + ", " + value.buyer.id + ");' style='margin-right: 10px; height: 40px;' type='button' id='transaction__btn__" + value.boardId.id + "' class='btn btn-primary'>교환</button>" + 
        "<button onclick='javascript:cartDelete(this, " + value.boardId.id + ", " + value.seller.id + ", " + value.buyer.id + ");' style='height: 40px;' type='button' id='delete__btn__" + value.boardId.id + "' class='btn btn-danger'>삭제</button>" +
        "</div>"

    // 거래 진행중
    }else{
        data['status'] = (function() {
            if (value.buyer.username == $('.user__name').text()){
                return "<div style='width: 40px; color: red'>구매중</div>";
            }else{
                return "<div style='width: 40px; color: blue'>판매중</div>";
            }
        })(); 

        data['action'] = (function(){

            var buyerStatus = '대기중', sellerStatus = '대기중';

            if(value.buyerComplete == 'true')
                buyerStatus = '교환확정';

            if(value.sellerComplete == 'true')
                sellerStatus = '교환확정';

            return "<div class='transaction__button'>" +
                    "<button onclick='javascript:submitTransaction(" + value.boardId.id + ", " + value.seller.id + ", " + value.buyer.id + ");' style='margin: 3px;' type='button' id='submit__btn__" + value.boardId.id + "' class='btn btn-primary'>교환확정</button>" +
                    "<button onclick='javascript:deleteTransaction(" + value.boardId.id + ", " + value.seller.id + ", " + value.buyer.id + ");' style='margin: 3px;' type='button' id='submit__btn__" + value.boardId.id + "' class='btn btn-danger'>교환취소</button>" + 
                    "<div class='transaction__status__container'>" +
                    "<div>판매자 상태 : " + sellerStatus + "</div>" +
                    "<div>구매자 상태 : " + buyerStatus + "</div>" +
                    "</div>" +
                    "</div>"
        })();
        
    }

    return data;

}

function loadPagination(type, pages){
    if(!pagination){
        pagination = true;
        if(pages < 1) pages = 1;
        $('.page__number__box').empty();
        for(var i = 1; i <= pages; i++){
            $('.page__number__box').append(
                "<li id='pagenum-" + i + "' class='page__number' onclick='loadTransactionData(this, \"" + type + "\", " + (i - 1) + ");'>" + 
                "<a href='javascript:void(0)'>" + i + "</a></li>"
            );
        }
    }
}

function cartToTransaction(boardId, seller, buyer){

    $.ajax({
        url: '/api/transaction/cart/move',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data : 'boardId=' + boardId + '&sellerId=' + seller + '&buyerId=' + buyer,
        success: function(response){

        }
    })

}

function cartDelete(e, boardId, seller, buyer){
    console.log(e.parentNode.parentNode.parentNode)
    e.parentNode.parentNode.parentNode.remove();
    $.ajax({
        url: '/api/transaction/cart/delete',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data : 'boardId=' + boardId + '&sellerId=' + seller + '&buyerId=' + buyer,
        success: function(response){
            if(response == 'success'){
                alert('해당 물품을 삭제하였습니다.');
            }
        }
    })

}

function submitTransaction(boardId, sellerId, buyerId){
    
    var data = 'type=transaction&boardId=' + boardId + '&sellerId=' + sellerId + '&buyerId=' + buyerId;

    $.ajax({
        url: '/api/transaction/submit',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function(response){
            alert('해당 물품의 교환을 확정하였습니다.');
            window.location.reload(false);
        }
    })

}

function deleteTransaction(boardId, sellerId, buyerId){
    var data = 'type=delete&boardId=' + boardId + '&sellerId=' + sellerId + '&buyerId=' + buyerId;
    $.ajax({
        url: '/api/transaction/delete',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function(response){
            alert('선택하신 물품교환이 취소되었습니다.');
        }
    })
}

function commentWrite(userId, seller, boardId, writer){

    var comment = $('#comment_box').val();
    var mannerScore = $('#manner__box').text();

    if(comment.length > 0){

        $.ajax({

            url: '/api/comment/write',
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded',
            data : 'type=comment&userId=' + userId + '&seller=' + seller + '&boardId=' + boardId + '&comment=' + comment + '&mannerScore=' + mannerScore + '&writer=' + writer,
            success: function(response){
                if(response == 'success'){
                    alert('리뷰를 작성하였습니다.');
                    document.getElementById('my_modal').style.display = 'none';
                    document.getElementById('modal__layer').style.display = 'none';
                }
            }

        })
    }else alert('리뷰내용을 입력해주세요.');

}