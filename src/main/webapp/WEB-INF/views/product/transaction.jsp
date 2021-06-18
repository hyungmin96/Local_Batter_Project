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

    <div class="product__items__container" style="height: 800px;">


    </div>

</div>
<%@ include file="../common/footer.jsp" %>

<script>

var page = 0;
var display = 10;

function dropBtnSelect(e){
    document.getElementById('dropdownMenuButton').innerHTML = e.text;
}

$(document).ready(function(){
    loadTransactionData();
})

function loadTransactionData(){

    var submit = function(bordId, sellerId, buyerId){
        return "<button onclick='javascript:submitTransaction(" + bordId + ", " + sellerId + ", " + buyerId + ");' style='margin: 3px;' type='button' id='submit__btn__" + bordId + "' class='btn btn-primary'>교환확정</button>";
    }

    var cancel = function(bordId, sellerId, buyerId){
        return "<button onclick='javascript:deleteTransaction(" + bordId + ", " + sellerId + ", " + buyerId + ");' style='margin: 3px;' type='button' id='submit__btn__" + bordId + "' class='btn btn-danger'>교환취소</button>";
    }

    $.ajax({

        url: '/api/transaction/dealList',
        type: 'GET',
        data: {page: page, display: display},
        success: function(response){

            $.each(response.content, function(key, value){
                
                console.log(value)

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

            })

        }

    })

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

        }
    })

}

</script>
<link rel="stylesheet" href="/css/transaction.css">
