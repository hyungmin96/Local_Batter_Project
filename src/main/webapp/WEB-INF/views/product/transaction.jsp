<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div class="container" style="margin-top: 155px;">

    <!-- modal -->
        <div id="my_modal">
            <div class = "modal_close_btn"><img src="/images/delete_35px.png" style="width: 20px; height: 20px;float: right; cursor: pointer;"></div>

            <div style="display: inline-flex">
                <div class="manner__score" style="margin: auto auto;">
                    매너점수
                </div>

                <div class="btn-group">
                    <button id="manner__box" type="button" class="btn btn-danger dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        선택
                    </button>
                        <ul class="dropdown-menu">
                            <a onclick="dropBtnSelect(this);" class="dropdown-item" href="#">0.0</a>
                            <a onclick="dropBtnSelect(this);" class="dropdown-item" href="#">1.0</a>
                            <a onclick="dropBtnSelect(this);" class="dropdown-item" href="#">2.0</a>
                            <a onclick="dropBtnSelect(this);" class="dropdown-item" href="#">3.0</a>
                            <a onclick="dropBtnSelect(this);" class="dropdown-item" href="#">4.0</a>
                            <a onclick="dropBtnSelect(this);" class="dropdown-item" href="#">5.0</a>
                        </ul>
                </div>
            </div>

            <hr style="border: none; height: 1px; background-color: rgb(185, 185, 185);"/>

            <div class="comment">
                리뷰작성
                <hr style="border: none; height: 1px; background-color: rgb(185, 185, 185);"/>
                <textarea id="comment_box" class="form-control" style="height: 150px;"></textarea>
                <div class="text__container"></div>
            </div>
        </div>
    <!-- modal -->

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
    
        <div style="height: 1380px;">

            <div class="product__items__container" style="max-height: 1400px;">

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


</div>
<%@ include file="../common/footer.jsp" %>

<script src="/js/modal.js"></script>
<script src="/js/transaction.js"></script>
<link rel="stylesheet" href="/css/transaction.css">
