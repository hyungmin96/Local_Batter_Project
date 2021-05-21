<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div id="products" class="container">
    <div class="content-wrapper">
        <div class="category-content">빠른판매 물품</div>

        <div class="item-list">
            <div id="slider-div">
                <c:forEach var="board" items="${board}" varStatus="i">
                    <div class="container-item">
                        <div class="fast-item-box">
                            <div class="fast-box">
                                <div class="fast-slide-img">
                                    <img src="/upload/${board.files[0].tempName}">
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
                                        <div class="price">${board.price}</div>
                                        <span class="time">${board.displayDate}</span>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

    <div class="category-content">공동구매 게시글</div>
    <div class="item-list"></div>

    <div class="category-content">오늘의 물품</div>
    <div class="container">
        <c:forEach var="board" items="${board}" varStatus="i">
            <span class="container-item">

                <div class="box">
                    <div class="slide-img">
                        <img src="/upload/${board.files[0].tempName}">
                        <div class="overlay">
                            <a href="/board/article/${board.id}" class="buy-btn">상세보기</a>
                        </div>
                    </div>

                    <div class="detail-box">
                        <div class="type">
                            <div class="title">${board.title}</div>
                            <div class="board-line"></div>
                            <div class="line">
                                <div class="price">${board.price}</div>
                                <span class="time">${board.displayDate}</span>
                            </div>
                        </div>
                    </div>
                </div>

            </span>
        </c:forEach>
    </div>
</div></div></div></div>

