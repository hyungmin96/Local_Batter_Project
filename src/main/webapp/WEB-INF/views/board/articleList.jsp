<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>

    <div id="item" class="container">
        <div class="item-list">
            <c:forEach var="board" items="${board}" varStatus="i">
                <span class="container-item">
                    <div class="container">

                        <div class="box">
                            <div class="slide-img">
                                <img src="/upload/${board.files[0].tempName}" style="height: 180px">
                                <div class="overlay">
                                    <a href="/board/article/${board.id}" class="buy-btn">상세보기</a>
                                </div>
                            </div>

                            <div class="detail-box">
                                <div class="type">
                                    <div class="title">${board.title}</div>
                                    <div class=board-line></div>
                                    <div class="line">
                                        <div class="price">${board.price}</div>
                                        <div class="time">${board.displayDate}</div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </span>   
            </c:forEach>
        </div>
    </div>