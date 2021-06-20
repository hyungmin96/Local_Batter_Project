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
                <span id="accuracy" class="accuracy">정확도순</span>
                |
                <span id="Bestprice" class="Bestprice">가격순</span>
                |
                <span id="Latest" class="Latest">최신순</span>
            </div>
        </div>

        <hr/>


        <div class="container">
        <%-- Page<T> 형식으로 List를 받아오려면 model에 키값 변수에 .content를 추가 --%>
            <div class="search__item__list">
                
            </div>
        </div>

    </div>

    <div class="container">
        <div class="page__container">
            <div class="page__box">
                <div class="previous"><img src="/images/back.png"></div>
                    <input type="hidden" class="curpage" data-value="1">
                    <input type="hidden" class="lastpage" data-value="${endPages}">
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
