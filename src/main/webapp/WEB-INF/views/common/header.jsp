<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.TransportPortal.MyFunctions.* "%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

            <sec:authorize access="isAuthenticated()">
                <sec:authentication property="principal" var="principal" />
            </sec:authorize>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <title>로컬바터 - 지역 물품교환 및 거래</title>
                <meta charset="UTF-8">
                <meta http-equiv="X-UA-Compatible" content="IE=edge">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
                <link rel="stylesheet" href="/css/imgWrap.css">
                <link rel="stylesheet" href="/css/articleList.css">
                <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
                <link rel="stylesheet" href="/css/articleDetail.css">
                <link rel="stylesheet" href="/css/join.css">
                <link rel="stylesheet" href="/css/header.css">
                <link rel="stylesheet" href="/css/searchList.css">
                <link rel="stylesheet" href="/css/articleWrite.css">
                <link rel="stylesheet" href="/css/footer.css">
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
            </head>

<body>

<div class="remote__container" style="top:280px; right: 0px; position: fixed; background-color: rgb(78, 78, 78); width: 40px; height: 180px">
    <div class="remote__list">
        <li>
            <div style="cursor: pointer;" onclick="window.location.href='/board/article/847';"><img src="/images/remote/bell.png">
            </div>
        </li>

        <li>
            <div style="cursor: pointer" onclick="window.location.href='/board/article/847';"><img src="/images/remote/cart.png">
            </div>
        </li>

        <li>
            <div style="cursor: pointer" onclick="window.location.href='/board/article/847';"><img src="/images/remote/chat.png">
            </div>
        </li>

        <li>
            <div style="cursor: pointer" onclick="window.scrollTo(0,0);"><img src="/images/remote/up.png">
            </div>
        </li>
    </div>
</div>

<div class="header">
    <div class="user-info-box">
        <div class="container">
                <c:choose>
                    <c:when test="${empty principal}">
                        <div class="none-login">
                            <a href="/user/login" style="color: rgb(255, 255, 255);">로그인</a>
                            <a href="/user/join" style="color: rgb(255, 255, 255);">회원가입</a>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="on-login">
                            <div class="user_name_info" style="color: rgb(133, 191, 238);">
                                [<span class="user__name">${principal.username}</span>]님
                            </div>
                            <a href="/user/logout" style="color: rgb(255, 255, 255);">로그아웃</a>
                            <a href="/profile/user=${principal.username}" style="color: rgb(255, 255, 255);">마이페이지</a>
                        </div>
                    </c:otherwise>
                </c:choose>
        </div>
        <hr />
    </div>

    <div class="container">
        <div class="header_box" style="margin: auto auto;">
            <a class="site_icon" href="/">
            <img src="/images/header/icon.png" style="margin-left: 50px;"><span class="logo__text">로컬바터</span></a>

                <div class="wrapper">
                    <div class="search-input">
                        <a href="" target="_blank" hidden></a>
                        <input type="text" id="search-value" placeholder="검색하실 상품을 입력해주세요">
                        <div class="autocom-box"></div>
                            <div class="icon">
                                <i class="fa fa-search"></i>
                            </div>
                    </div>
                </div>

            <div class="btn-header-btns">
                <div class="btn-board-reg">
                    <button class="header-product-reg" onclick="location.href='/write'">
                        <img src="/images/header/paper_money_28px.png"/>
                        <span class="btn__text">판매하기</span>
                    </button>
                </div>

                <div class="btn-profile">
                    <button class="header-product-reg" onclick="location.href='/transaction/itemList'">
                        <img src="/images/header/packaging_28px.png"/>
                        <span class="btn__text">상품관리</span>
                        <!-- <a href="/board/article/write"></a> -->
                    </button>
                </div>

                <div class="btn-chat">
                    <button onclick="location.href='/${principal.username}/chatlist'" class="header-product-reg">
                        <img src="/images/header/message.png"/>
                        <span class="btn__text">채팅하기</span>
                        <!-- <a href="/board/article/write"></a> -->
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>