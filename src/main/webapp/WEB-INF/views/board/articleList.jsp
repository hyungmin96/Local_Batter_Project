<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.TransportPortal.MyFunctions.* "%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div id="products" class="container">
    <div class="content-wrapper">

            <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="carousel">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1" aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2" aria-label="Slide 3"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active" data-bs-interval="4000">
                    <img src="/images/main/logo.gif" class="d-block w-100">
                </div>
                <div class="carousel-item" data-bs-interval="4000">
                    <img src="/images/main/delivery.png" class="d-block w-100">
                </div>
                <div class="carousel-item" data-bs-interval="4000">
                    <img src="/images/main/buy.png" class="d-block w-100">
                </div>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
            </div>

        <div class="fast__sale_products">
            <span class="fast__category">완전 급해요</span>
            <span class="sub__category">| 빠른 교환을 원하는 물품들이에요</span>
            <hr style="border: none; height: 2px; background-color: #ccc;" />
        </div>

        <div class="item-list">
            <div id="slider-div">
                <c:forEach var="board" items="${fast}" varStatus="i">
                    <div class="container-item">
                        <div class="fast-item-box">
                            <div class="fast-box">
                                <div class="fast-slide-img">
                                    <img src="/upload/${board.files[0].tempName}" onerror="this.style.display='none'" />
                                    <div class="overlay">
                                        <a href="/board/article/${board.id}" class="buy-btn">상세보기</a>
                                    </div>
                                </div>
                            </div>

                            <div class="fast-detail-box">
                                <div class="type">
                                    <div class="title">${board.title}</div>
                                    <div class="board-line"></div>
                                    <div class="line">
                                        <div class="price">${board.price}<span class="k-money">원</span></div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

            <div class="fast__sale_products">
                <span class="fast__category">바꾸고 싶어요</span>
                <span class="sub__category">| 마음에 드는 물품과 교환 해보세요</span>
                <hr style="border: none; height: 2px; background-color: #ccc;" />

            </div>

            <div class="today-item-list">
            </div>
            </div>
            </div>
        

<script type="text/javascript" src="/js/infiniteScroll.js"></script>
<%@ include file="../common/footer.jsp" %>


