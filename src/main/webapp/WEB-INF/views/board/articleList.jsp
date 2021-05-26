<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div id="products" class="container">
    <div class="content-wrapper">

            <div class="banner__container">
                <div class="image__banner">
                    <img src="/images/logo.gif">
                </div>
                <div class="group__buy">
                    <div class="container">
                        <div class="group__buying__title">공동구매 목록</div>
                        <hr />
                        <div class="group-item-list">
                            
                        </div>
                    </div>
                </div>
            </div>

           <div class="fast__sale_products">
                <div class="fast__category">오늘의 긴급판매</div>
                <hr />
           </div>

        <div class="item-list">
            <div id="slider-div">
                <c:forEach var="board" items="${fast}" varStatus="i">
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
                <div class="fast__category">오늘의 등록물품</div>
                <hr />
            </div>

            <div class="today-item-list">
                <c:forEach var="board" items="${general}" varStatus="i">
                    <div OnClick="location.href = '/board/article/${board.id}'" class="today-item-box">
                        <div class="today-box">
                            <img src="/upload/${board.files[0].tempName}">
                            <div class="overlay">
                                <a href="/board/article/${board.id}" class="buy-btn">상세보기</a>
                            </div>
                        </div>
                        <div class="today-detail-box">
                            <div class="type">
                                <div class="title">${board.title}</div>
                                <div class="board-line"></div>
                                <div class="line">
                                    <div class="price">${board.price}<span class="k-money">원</span></div>
                                    <span class="time">${board.displayDate}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>

<%@ include file="../common/footer.jsp" %>


