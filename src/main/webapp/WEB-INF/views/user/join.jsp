<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>

<div class="join-wrapper" style="margin-top: 155px;">

 <div class="container" style="width: 500px;">

        <div class="col-md-12 order-md-1">
            <h4 class="mb-3">회원정보</h4>

                <div class="mb-3">
                    <label for="username">아이디(Email)</label>
                    <div class="input-group">
                        <input type="text" class="form-control" id="username" placeholder="" required name="user-id">
                        <div class="invalid-feedback" style="width: 100%;">Your username is required.</div>
                        <button class="id-certification">중복확인</button>
                    </div>
                </div>
                
                <div class="mb-3">
                    <label for="password">비밀번호</label> 
                    <input type="password" class="form-control" id="password" placeholder="" value="" required name="user-password">
                    <div class="invalid-feedback"></div>
                </div>

                <div class="mb-3">
                    <label for="password">비밀번호 확인</label> 
                    <input type="password" class="form-control" id="password-chk" placeholder="" value="" required name="user-password">
                    <div class="invalid-feedback">비밀번호가 틀립니다.</div>
                </div>

                <div class="mb-3">
                    <label for="firstName">닉네임</label> 
                    <input type="text" class="form-control" id="nickname" placeholder="" value="" required name="user-real-name">
                    <div class="invalid-feedback">유효한 이름을 입력해야합니다.</div>
                </div>

                <div class="mb-3">
                    <label for="firstName">핸드폰번호</label> 
                    <input type="text" class="form-control" id="phone" placeholder="" value="" required name="user-real-name">
                    <div class="invalid-feedback">유효한 이름을 입력해야합니다.</div>
                </div>

                <button id="join-btn" class="btn btn-primary btn-lg btn-block">회원가입</button>

        </div>

    </div>
</div>