<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>

<div class="login-wrapper" style="margin-top: 155px;">
    <div class="container">
        <div class="panel-body">
            <fieldset>
            
            <div class="form-group">
                <input type="text" name="username" id="input-id" class="form-control" placeholder="ID" path="id"/>
            </div>

            <div class="form-group">
                <input type="password" name="pw" id="input-pw" class="form-control" placeholder="Password" path="pw"/>
            </div>

            <div class="checkbox">
                <label>
                    <input type="checkbox" path="rememberId"/>아이디 기억
                </label>
            </div>

            <button id="login-btn" class="btn btn-lg btn-success btn-block">로그인</button>
            </fieldset>
        </div>
    </div>
</div>