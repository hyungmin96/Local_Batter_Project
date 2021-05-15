<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="common/header.jsp" %>

    <script src="/js/imageBoard.js"></script>

<div class="container">
    <form action="/upload" method="post" class="was-validated" encrtype="multipart/form-data">

        <label for="inputTitle"><strong>상품 이미지 등록</strong></label>
            <div id="wrapper">
                <div id="info">
                    <b>* 상품 이미지는 640x640에 최적화 되어 있습니다.</b>
                    <br>- 이미지는 상품등록 시 정사각형으로 짤려서 등록됩니다.<br>
                    - 이미지를 클릭 할 경우 원본이미지를 확인할 수 있습니다.<br>
                    - 이미지를 클릭 후 이동하여 등록순서를 변경할 수 있습니다.<br>
                    - 큰 이미지일경우 이미지가 깨지는 경우가 발생할 수 있습니다.
                    <br>최대 지원 사이즈인 640 X 640 으로 리사이즈 해서 올려주세요.(개당 이미지 최대 10M)
                </div>
                <div class="card">
                    <img src="/images/upload.png" id="file_add"/>
                    <input type="file" accept="image/jpg, image/jpeg, image/png" class="custom-file-input" id="input_imgs"
                    name="upload_file" multiple>
                </div>
            </div>
            <script src="/js/imgSlider.js"></script>

        <hr/>
            <label for="inputTitle"><strong>물품 이미지 미리보기</strong></label>
            <div id="previewBoard" class="previewBoard"></div>
        <hr/>

        <div>
            <div class="form-group row">
                <label for="inputTitle" class="col-sm-2 col-form-label"><strong>제목</strong></label>
                <div class="col-sm-10">
                    <input type="text" name="title" class="form-control" id="inputTitle" placeholder="등록할 상품의 이름을 입력해주세요." required>
                    <div class="invalid-feedback">* 필수항목</div>
                </div>
            </div>
            <hr/>
        </div>

        <div>
            <div class="form-group row">
                <label for="inputTitle" class="col-sm-2 col-form-label"><strong>가격</strong></label>
                <div class="col-sm-10">
                    <input type="text" name="title" class="form-control" id="inputPrice" placeholder="물품에 대한 판매가격을 입력해주세요" required>
                        <div class="invalid-feedback">* 필수항목</div>
                </div>
            </div>
            <hr/>
        </div>

        <div>
            <div class="form-group row">
                <label for="inputTitle" class="col-sm-2 col-form-label"><strong>상품설명</strong></label>
                <div class="col-sm-10">
                    <input type="text" name="title" class="form-control" id="inputDescript" placeholder="무품에 대한 내용을 입력해주세요" required>
                    <div class="invalid-feedback">* 필수항목</div>
                </div>
            </div>
            <hr/>
        </div>

        <div>
            <div class="form-group row">
                <label for="inputTitle" class="col-sm-2 col-form-label"><strong>거래지역</strong></label>
                <div class="col-sm-10">
                    <input type="text" name="title" class="form-control" id="inputLocation" placeholder="선호하는 위치를 입력해주세요.">
                </div>
            </div>
            <hr/>
        </div>

        <div class="form-group row float-right">
            <button type="button" id="btn_upload" class="btn btn-secondary btn-lg">상품등록</button>
        </div>

        <br />

    </form>
</div>