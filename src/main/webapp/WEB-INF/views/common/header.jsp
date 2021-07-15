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

<div class="remote__container" style="top:280px; right: 0px; position: fixed; background-color: rgb(78, 78, 78); width: 40px; height: 180px">
    <div class="remote__list">
        <li>
            <div style="position: relative; cursor: pointer;" onclick="window.location.href='#';"><img src="/images/remote/bell.png">
            <div style="position: absolute; top: -10px; right: -5px; display: none;" class="noti">${notification.notification}</div>
            </div>
        </li>

        <li>
            <div style="position: relative; cursor: pointer" onclick="window.location.href='/transaction/itemList';"><img src="/images/remote/cart.png">
            <div style="position: absolute; top: -10px; right: -5px; display: none;" class="trans">${notification.transaction}</div>
            </div>
        </li>

        <li>
            <div style="position: relative; cursor: pointer" onclick="window.location.href='/chatlist/${principal.username}';"><img src="/images/remote/chat.png">
            <div style="position: absolute; top: -10px; right: -5px; display: none;" class="chat">${notification.chat}</div>
            </div>
        </li>

        <li>
            <div style="cursor: pointer" onclick="window.scrollTo(0,0);"><img src="/images/remote/up.png">
            </div>
        </li>
    </div>
</div>

<div class="header">
    <div class="container">
        <div class="header_box" style="margin: auto auto;">
            <div><a class="site_icon" href="/"></a></div>
            <a href="/" class="logo__text">Local Batter</a>

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
                    <div class="btn-header-btns">
                        <div class="header_ btn-board-reg">
                            <button class="header-product-reg" onclick="location.href='/write'">
                                <img src="/images/header/notification.png"/>
                            </button>
                        </div>

                        <div class="header_ btn-profile">
                            <button class="header-product-reg" onclick="location.href='/transaction/itemList'">
                                <img src="/images/header/chat.png"/>
                            </button>
                        </div>

                        <div class="header_ btn-chat">
                            <div class="dropdown">
                                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton1" data-bs-toggle="dropdown" aria-expanded="false">
                                    <img src="/images/header/user.png">
                                </button>
                                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton1">
                                    <li><a href="/profile/user=${principal.username}" class="dropdown-item" href="#">프로필 보기</a><hr/></li>
                                    <li><a href="/write" class="dropdown-item" href="#">판매글 작성</a></li>
                                    <li><a class="dropdown-item" href="#">내 상점</a><hr/></li>
                                    <li><a href="/user/logout" class="dropdown-item" href="#">로그아웃</a></li>
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

<script>

var notiElement = document.getElementsByClassName('noti')[0];
var transElement = document.getElementsByClassName('trans')[0];
var chatElement = document.getElementsByClassName('chat')[0];

$(document).ready(function(){
    if(parseInt(notiElement.innerHTML) > 0)
        notiElement.style.display = 'block';
    if(parseInt(transElement.innerHTML) > 0)
        transElement.style.display = 'block';
    if(parseInt(chatElement.innerHTML) > 0)
        chatElement.style.display = 'block';
})

notiElement.addEventListener("DOMSubtreeModified", function(){
    if(parseInt(notiElement.innerHTML) < 1)
        notiElement.style.display = 'none';
    else
        notiElement.style.display = 'block';
})

transElement.addEventListener("DOMSubtreeModified", function(){
    if(parseInt(transElement.innerHTML) < 1)
        transElement.style.display = 'none';
    else
        transElement.style.display = 'block';
})

chatElement.addEventListener("DOMSubtreeModified", function(){
    if(parseInt(chatElement.innerHTML) < 1)
        chatElement.style.display = 'none';
    else
        chatElement.style.display = 'block';
})
</script>

<style>
    body{
        background-color: #f5f5f5;
    }
</style>