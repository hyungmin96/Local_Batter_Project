<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div id="products" class="container">
    <div class="content-wrapper">

        <div class="banner__container">
            <div class="image__banner">
                <img src="/images/main/logo.gif">
            </div>

            <div class="sub__container">
                <div class="sub__banner">
                    <img src="/images/main/delivery.png">
                </div>
                
                <div class="sub__banner" style="margin-top: 5px;">
                    <div class="delivery__container">
                        <img src="/images/main/buy.png">
                    </div>
                </div>
            </div>

        </div>

        <div class="fast__sale_products">
            <div class="fast__category">완전 급해요</div>
            <hr />
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
                <div class="fast__category">바꾸고 싶어요</div>
                <hr />
            </div>

            <div class="today-item-list">
            </div>
        </div>

<%@ include file="../common/footer.jsp" %>


