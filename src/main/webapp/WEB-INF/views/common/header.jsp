<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
                <meta name="_csrf" th:content="${_csrf.token}">
                <meta name="_csrf_header" th:content="${_csrf.headerName}">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
                <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
                <link rel="stylesheet" href="/css/imgWrap.css">
                <link rel="stylesheet" href="/css/articleList.css">
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
                <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
                <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css"/>
                <script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
                <link rel="stylesheet" href="/css/articleDetail.css">
                <link rel="stylesheet" href="/css/join.css">
                <link rel="stylesheet" href="/css/header.css">
                <link rel="stylesheet" href="/css/searchList.css">
                <link rel="stylesheet" href="/css/articleWrite.css">
                <link rel="stylesheet" href="/css/footer.css">
                <script src="/js/boardPost.js"></script>
                <script type="text/javascript" src="/js/Login.js"></script>
                <script type="text/javascript" src="/js/pagination.js"></script>
                <script src="/js/imageSlider.js"></script>
                <script src="/js/join.js"></script>
                <script src="/js/board.js"></script>
                <script src="/js/search.js"></script>
                <script type="text/javascript" src="/js/slider.js"></script>
            </head>

            <body>

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
                                        <div class="user_name_info" style="color: rgb(255, 255, 255);">[${principal.username}]님</div>
                                        <a href="/user/logout" style="color: rgb(255, 255, 255);">로그아웃</a>
                                        <a href="/user/mypage" style="color: rgb(255, 255, 255);">마이페이지</a>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <hr />
                    </div>

                    <div class="container">
                        <div class="header_box">
                            <a class="site_icon" href="/">
                            <img src="/images/header/icon.png"><span class="logo__text">로컬바터</span></a>

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
                                        <img src="/images/header/money.png"/>
                                        <span class="btn__text">판매하기</span>
                                    </button>
                                </div>

                                <div class="btn-profile">
                                    <button class="header-product-reg">
                                        <img src="/images/header/shopping.png"/>
                                        <span class="btn__text">장바구니</span>
                                        <!-- <a href="/board/article/write"></a> -->
                                    </button>
                                </div>

                                <div class="btn-chat">
                                    <button class="header-product-reg">
                                        <img src="/images/header/message.png"/>
                                        <span class="btn__text">채팅하기</span>
                                        <!-- <a href="/board/article/write"></a> -->
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>