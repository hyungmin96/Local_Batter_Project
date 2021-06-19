<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div class="container" style="margin-top: 155px;">

    <div>
        <span class="dropdown">
            <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                현재 거래중인 상품목록
            </button>
            <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                <a class="dropdown-item" onclick="dropBtnSelect(this);" href="#">현재 거래중인 상품목록</a>
                <a class="dropdown-item" onclick="dropBtnSelect(this);" href="#">거래 완료된 상품목록</a>
                <a class="dropdown-item" onclick="dropBtnSelect(this);" href="#">내가 찜한 상품목록</a>
            </div>
        </span>        
    </div>
    <hr style="border: none; height: 1px; background-color: rgb(185, 185, 185);"/>

    <div class="product__items__container" style="Max-height: 1320px;">


    </div>

        <div class="page__container">
            <div class="page__box">
                <div class="previous"><img src="/images/back.png"></div>
                    <input type="hidden" class="curpage" data-value="1">
                    <input type="hidden" class="lastpage" data-value="${endPages}">
                    <div class="page__number__box" style="max-width: 355px;">

                    </div>
                <div class="next"><img src="/images/next.png"></div>
            </div>
        </div>

</div>
<%@ include file="../common/footer.jsp" %>

<script>

var page = 0;
var display = 10;
var pagination = false;

function dropBtnSelect(e){
    document.getElementById('dropdownMenuButton').innerHTML = e.text;
    if(e.text == '현재 거래중인 상품목록'){
        page = 0
        pagination = false;
        loadTransactionData();
    }else if(e.text == '거래 완료된 상품목록'){
        page = 0
        pagination = false;
        loadTransactionData(null, 'complete');
    }
}

$(document).ready(function(){
    loadTransactionData();
})

function loadTransactionData(e = null, type = 'transaction', page = 0){

    var submit = function(bordId, sellerId, buyerId){
        return "<button onclick='javascript:submitTransaction(" + bordId + ", " + sellerId + ", " + buyerId + ");' style='margin: 3px;' type='button' id='submit__btn__" + bordId + "' class='btn btn-primary'>교환확정</button>";
    }

    var cancel = function(bordId, sellerId, buyerId){
        return "<button onclick='javascript:deleteTransaction(" + bordId + ", " + sellerId + ", " + buyerId + ");' style='margin: 3px;' type='button' id='submit__btn__" + bordId + "' class='btn btn-danger'>교환취소</button>";
    }

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
                var status = '';
                var buyerStatus = '대기중', sellerStatus = '대기중';
                var file;

                if(value.buyerComplete == 'true')
                    buyerStatus = '교환확정';

                if(value.sellerComplete == 'true')
                    sellerStatus = '교환확정';

                if(value.boardId.files.length > 0 )
                    file = 'upload/' + value.boardId.files[0].tempName;
                else
                    file = 'images/noimage.png';

                if (value.buyer.username == $('.user__name').text()){
                    status = "<div style='width: 40px; color: red'>구매중</div>";
                }else{
                    status = "<div style='width: 40px; color: blue'>판매중</div>";
                }

                $('.product__items__container').append(
                    "<div class='container__product__box'>" + 
                    status + 
                    "<a href='http://localhost:8000/" + file + "'><img class='thumbnail__img' src=/" + file + ">" + 
                    "<div style='width: 300px;'><a href='http://localhost:8000/board/article/" + value.boardId.id + "' target='_blank'>" +  value.boardId.title + "</a></div>" + 
                    "<div style='width: 100px;'>" + value.boardId.price + "원</div>" + 
                    "<div style='width: 100px;'><a href='http://localhost:8000/profile/user=" + value.boardId.writer + "' target='_blank'>" + value.boardId.writer + "</a></div>" + 
                    "<div style='color: rgb(185, 185, 185); width: 100px;'>" + new Date(value.boardId.createTime).toLocaleDateString() + "</div>" + 
                    "<div class='transaction__button'>" +
                    submit(value.boardId.id, value.seller.id, value.buyer.id) + 
                    cancel(value.boardId.id, value.seller.id, value.buyer.id) + 
                    "<div class='transaction__status__container'>" +
                    "<div>판매자 상태 : " + sellerStatus + "</div>" +
                    "<div>구매자 상태 : " + buyerStatus + "</div>" +
                    "</div>" + 
                    "</div>" + 
                    "</div>" + 
                    "<hr style='border: none; height: 1px; background-color: rgb(185, 185, 185);'/>"
                );

            window.scrollTo(0,0);

            })
        }
    })
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

function submitTransaction(boardId, sellerId, buyerId){
    
    var data = 'type=submit&boardId=' + boardId + '&sellerId=' + sellerId + '&buyerId=' + buyerId;

    $.ajax({
        url: '/api/transaction/submit',
        type: 'POST',
        data: data,
        contentType: 'application/x-www-form-urlencoded',
        success: function(response){

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

</script>
<link rel="stylesheet" href="/css/transaction.css">
