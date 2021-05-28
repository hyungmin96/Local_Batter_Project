<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>

<div class="search__wrapper">
    <div id="search__container" class="container">

        <div class="search">
            <div class="search__keyword">
                <span class="keyword">'${keyword}'</span>
                <span style="margin-left: 5px">검색결과</span>
                <span class="search__keyword__count" style="margin-left: 3px; color: rgb(170, 170, 170);">${searchBoards.getTotalElements()}개</span>
            </div>

            <div class="sort__Type">
                <span class="accuracy">정확도순</span>
                |
                <span class="Bestprice">가격순</span>
                |
                <span class="Latest">최신순</span>
            </div>
        </div>

        <hr/>

        <c:choose> 
            <c:when test="${searchBoards.getTotalElements() > 0}">
                <div class="container">
                <%-- Page<T> 형식으로 List를 받아오려면 model에 키값 변수에 .content를 추가 --%>
                    <c:forEach var="board" items="${searchBoards.content}" varStatus="i">
                        <div class="search__item__list">
                            <div class="search__item__box">
                                <a href="/board/article/${board.id}">
                                    <div class="search__box__img">
                                        <img src="/upload/${board.files[0].tempName}" style="height: 180px; display='none'">
                                    </div>

                                    <div class="search__detail__box">
                                        <div class="info__box">
                                            <div class="title" style="color: black;">${board.title}</div>
                                                <div class="line">
                                                <div class="price">${board.price}<span class="k-money">원</span></div>
                                                <span class="time">${board.displayDate}</span>
                                            </div>
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </c:when>
        </c:choose>
    </div>

    <div class="container">
        <div class="page__container">
            <div class="page__box">
                <div class="previous"><img src="/images/back.png"></div>
                    <div class="page__number__box">
                        <c:forEach var="page" items="${totalPages}" varStatus="i">
                            <li id="pagenum-${page}" class="page__number" onclick="product_list_paging(this)">
                            <a href="javascript:void(0)">${page}</a></li>
                        </c:forEach>
                    </div>
                <div class="next"><img src="/images/next.png"></div>
            </div>
        </div>
    </div>
</div>

</div>


<%@ include file="../common/footer.jsp" %>
