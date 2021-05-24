<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ include file="../common/header.jsp" %>

    <div class="detail-wraper">
        <div class="container">

            <div class="product_info">
                <div class="product">

                    <div class="img-container">
                        <img id="imgBox" src="/upload/${board.files[0].tempName}">
                    </div>

                    <div class="product-small-img">
                        <c:forEach var="files" items="${board.files}">
                            <img class="grid-box" id="img-${files.fileid}" src="/upload/${files.tempName}">
                        </c:forEach>
                    </div>
                </div>

                <div class="container">
                    <div class="content_info">
                        <span>상품번호 : <span class="boardId">${board.id}</span></span>
                        <p> 물품이름 : ${board.title}</p>
                        <p> 판매가격 : ${board.price}</p>
                        <p> 물품설명 : ${board.descryption}</p>
                        <p> 작성자 : ${board.writer}</p>
                        <p> 거래지역 : ${board.location}</p>
                        <button type="button" id="delete-Post" class="btn btn-danger">게시글 삭제</button>
                        <button type="button" id="update-Post" class="btn btn-primary">게시글 수정</button>
                    </div>
                </div>
            </div>

            <div class="container">
                <h3>최근 등록물품</h3>
            </div>

        </div>
    </div>