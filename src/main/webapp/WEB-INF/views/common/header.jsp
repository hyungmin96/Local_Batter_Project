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
                <link rel="stylesheet" href="/css/modal.css">
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-+0n0xVW2eSR5OomGNYDnhzAbDsOXxcvSN1TPprVMTNDbiYZCxYbOOl7+AMvyTG2x" crossorigin="anonymous">
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-gtEjrD/SeCtmISkJkNUaaKMoLD0//ElJ19smozuHV6z3Iehds+3Ulb9Bn9Plx0x4" crossorigin="anonymous"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                <script src="/js/search.js"></script>
            </head>

<body>

<div class="header">

    <input type="hidden" class="g_user_id" value="${g_user.id}">
    <input type="hidden" class="g_user_name" value="${g_user.username}">

    <div class="container" style="margin: 5px auto auto auto;">
        <div class="header_box">
                <div style="margin-left: 30px; width: 120px;">
                    <a href="/" class="logo__text">Local Batter</a>
                </div>
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
            <c:choose>
                <c:when test="${principal ne null}">

                    <input type="hidden" class="user__name" value="${principal.username}">

                    <div class="btn-header-btns">
                        <div class="header_ btn-board-reg">
                            <button class="header-product-reg" onclick="location.href='/write'">
                                <img src="/images/header/notification.png"/>
                            </button>
                        </div>

                        <div class="header_ btn-profile">
                            <button class="header-product-reg" onclick="location.href='/view/exchange/chat/list'">
                                <img src="/images/header/chat.png"/>
                            </button>
                        </div>

                        <div class="header_ btn-chat">
                            <div class="dropdown">
                                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                    <img src="/images/header/user.png">
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                    <li><a href="/profile/user=${principal.username}" class="dropdown-item">프로필 보기</a><hr/></li>
                                    <li><a href="/product/group" class="dropdown-item">가입 그룹목록</a></li>
                                    <li><a href="/view/transaction/my/boards" class="dropdown-item">물품 교환목록</a><hr/></li>
                                    <li><a href="/user/logout" class="dropdown-item">로그아웃</a></li>
                                </ul>
                            </div>
                        </div>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="none_login">
                        <a href="/user/login" style="color: rgb(113 113 113);">로그인</a>
                        <a href="/user/join" style="color: rgb(113 113 113);">회원가입</a>
                    </div>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>
<style>
    body{
        background-color: rgb(238, 240, 243);
        overflow-x: hidden;
    }
</style>