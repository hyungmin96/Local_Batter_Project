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
    <hr style="border: none; height: 2px; background-color: rgb(185, 185, 185);"/>

    <div class="product_items__container">

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

    $.ajax({

        url: '/api/transaction/dealList',
        type: 'GET',
        data: {page: page, display: display},
        success: function(response){

            $.each(response.content, function(key, value){

                console.log(value);

            })

        }

    })

}

</script>

<link rel="stylesheet" href="/css/transaction.css">
