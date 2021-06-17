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

    var blueBtn = "<button style='margin: 3px;' type='button' id='transaction__btn' class='btn btn-primary'>교환확정</button>";
    var redBtn = "<button style='margin: 3px;' type='button' id='transaction__btn' class='btn btn-danger'>교환취소</button>";

    $.ajax({

        url: '/api/transaction/dealList',
        type: 'GET',
        data: {page: page, display: display},
        success: function(response){

            $.each(response.content, function(key, value){

                var status = '';

                if (value.buyer.username == $('.user__name').text()){
                    status = '구매중';
                }else{
                    status = '판매중';
                }

                $('.product__items__container').append(
                    "<div class='container__product__box'>" + 
                    "<div style='width: 40px;'>" + status + "</div>" + 
                    "<img class='thumbnail__img' src=/upload/" + value.boardId.files[0].tempName + ">" + 
                    "<div style='width: 300px;'>" + value.boardId.title + "</div>" + 
                    "<div style='width: 100px;'>" + value.boardId.price + "원</div>" + 
                    "<div style='width: 100px;'>" + value.boardId.writer + "</div>" + 
                    "<div style='width: 100px;'>" + new Date(value.boardId.createTime).toLocaleDateString() + "</div>" + 
                    "</div>" + 
                    "<div class='transaction__button'>" +
                    blueBtn + 
                    redBtn + 
                    "</div>" + 
                    "<hr style='border: none; height: 1px; background-color: rgb(185, 185, 185);'/>"
                );

            })

        }

    })

}

</script>
<link rel="stylesheet" href="/css/transaction.css">
