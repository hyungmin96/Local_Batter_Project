<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div class="writerInfo"style="padding: 15px; background-color: rgb(238, 240, 243);">
    <div class="container">
        <input type="hidden" class="boardId" value="${board}">
            <div class="writerBoardInfo" style="margin-top: 70px;">
                <div class="exchangeHeader">등록된 물품 정보</div>
                <div>
                    <div class="writerProductInfoBox" style="display: flex; padding: 10px;">

                        <div id="carouselExampleIndicators" class="carousel slide clientExchangeImageSlider" data-ride="carousel">
                            <ol class="carousel-indicators">
                            </ol>
                            <div class="carousel-inner">
                            </div>
                            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                                <span class="carouselButton carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                                <span class="carouselButton carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>

                        <div class="writerContent" style="width: 100%; padding: 0 5px 5px 15px; border-left: 1px solid #efefef">
                            <div class="wrtierAccountInfoBox" style="display: inline-flex">
                                <div><img class="writerProfile" src="/upload/undefined"></div>
                                <div style="display: flex"><h4 class="writerUsername">사용자 이름</h4></div>
                            </div>
                            <div class="writerBoardContentContainer" style="padding: 0 15px 15px 15px;">

                            </div>
                        </div>

                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="clientExchangeContainer" style="margin-bottom: 80px; padding: 15px; background-color: #f7f8f9;">
        <div class="container">
            <div class="exchangeHeader">교환할 물품 정보</div>
            <div style="padding: 10px;">
                <input id="clientImagePreview" type="file" accept="image/jpg, image/jpeg, image/png"
                       class="clientImgUpload" name="upload_file" multiple style="display: none">

                <div class="clientCategoryBox">
                    <label class="item__value"><strong>제품 이미지</strong></label>
                    <div class="col-sm-10" style="display: inline-flex;">
                        <div id="clientBoardImageContinaer">
                            <img src="/images/upload.png" class="clientImageUploadButton"/>
                        </div>
                    </div>
                </div>

                <hr style="border: none; height: 1px; background: #a9a9a9; margin: 3px 0 3px 0;" />

                <div class="clientCategoryBox">
                    <label class="item__value"><strong>제품 및 추가금액</strong></label>
                    <div class="col-sm-10">
                        <input type="text" name="title" class="clientExchangeInfoBox clientAddPriceBox" value=""
                               placeholder="교환없이 금액으로 교환할 경우 입력해주세요">
                    </div>
                </div>

                <hr style="border: none; height: 1px; background: #a9a9a9; margin: 3px 0 3px 0;" />

                <div class="clientCategoryBox">
                    <label class="item__value"><strong>제품 설명</strong></label>
                    <div class="col-sm-10">
                        <textarea name="title" class="clientExchangeInfoBox clientDescriptionBox" value="" row="10"
                                  placeholder="물품에 대한 설명을 입력해주세요"></textarea>
                    </div>
                </div>

                <hr style="border: none; height: 1px; background: #a9a9a9; margin: 3px 0 3px 0;" />

                <div class="clientCategoryBox">
                    <label class="item__value"><strong>기타 문의사항</strong></label>
                    <div class="col-sm-10">
                        <textarea name="title" class="clientExchangeInfoBox clientRequestBox" value=""
                                  placeholder="교환할 물품에 대해 궁금한 사항을 입력해주세요"></textarea>
                    </div>
                </div>

                <hr style="border: none; height: 1px; background: #a9a9a9; margin: 3px 0 3px 0;" />

                <div class="clientCategoryBox">
                    <label class="item__value"><strong>교환지역 설정</strong></label>
                    <div class="col-sm-10">
                        <div class="serachAddr">
                            <input style="width: 94%;" type="text" name="title" id="serachAddrText" class="inputbox" placeholder="교환을 원하는 장소를 검색합니다."  />
                            <button class="clientExchangeButton searchButton">검색</button>
                        </div>

                        <div class="exchangeAddr" style="display: inline-flex; width: 100%;">
                            <input style="width: 500px; margin-right: 5px;" type="text" id="clientExchangeAddr" class="inputbox" disabled="true" placeholder="주소"  />
                            <input style="width: 300px;" type="text" id="clientExchangeAddrDetail" class="inputbox" placeholder="세부주소"  />
                        </div>

                        <input type="hidden" id="clientLongtitude" value=""/>
                        <input type="hidden" id="clientLatitude" value=""/>

                    </div>
                </div>
                <div id="map" style="width:100%; height:500px;"></div>
            </div>
        </div>
    </div>

    <div class="exchangeButtonContainer" style="border-top: 1px solid #ccc">
        <div class="container" style="display: flex;">
            <button style="margin: 13px 0 auto auto;" class="clientExchangeButton clientUploadButton">교환요청</button>
            <button style="margin: 13px 0 auto 10px;" class="clientExchangeButton clientCancel">취소</button>
        </div>
    </div>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.22.2/moment.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=2f665d933d93346898df736499236f77&libraries=services"></script>
<script type="text/javascript" src="/js/exchange/clientMapAddress.js"></script>
<link rel="stylesheet" type="text/css" href="/css/clientExchange.css">
<script type="text/javascript" src="/js/exchange/clientMap.js"></script>
<script type="text/javascript" src="/js/exchange/lineTheMap.js"></script>
<script type="text/javascript" src="/js/exchange/mapAPI.js"></script>
