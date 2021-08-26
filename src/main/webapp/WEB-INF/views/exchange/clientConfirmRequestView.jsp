<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

<div class="clientConfirmRequestWrapper" style="margin-top: 100px;">
    <div class="container">
        <input type="hidden" class="exchangeId" value="${exchangeId}">

        <div class="exchangeInfoContainer" style="padding: 20px; background: rgb(227, 230, 234);">
            <div class="info_1"><span style="color: #4b834b">성공적으로</span> 교환을 요청하였습니다.</div>
            <div class="info_2">아래는 작성한 물품의 정보입니다.</div>
        </div>

        <div class="exchangeWrapper" style="display: inline-flex; height: 100%; width: 100%; padding: 35px;">

            <div class="clientImageContainer">
                <div>
                    <img style="border: 1px solid #e7e7e7; width: 615px; height: 430px;" class="thumbnailImage" src="">
                </div>
                <div class="otherImageThumbnail" style="text-align: center; width: 630px; display: inline-flex; flex-wrap: wrap">

                </div>
            </div>

            <div class="clientRequestExchangeInfoContainer" style="padding: 0 20px 0 20px; width: 626px;">

                <div class="clientExchangeTitle"></div>
                <div class="clientExchangePrice"></div>

                <hr style="border: none; background: #908d8d; height: 1px; margin: 10px 0 10px 0;"/>

                <ul class="exchangeDetail">
                    <div class="infoCategory" style="display: inline-flex; width: 100%">

                    <li>상대 프로필 </li>
                        <div class="writerProfileImageContainer">
                            <img class="writerProfile" src="">
                        </div>
                        <div class="infoDetail writerUsernameBox">상대 닉네임</div>
                    </div>

                    <div class="infoCategory" style="display: inline-flex; width: 100%">
                        <li>거래위치 </li>
                        <div class="infoDetail clientExchangeLocation"></div>
                    </div>

                    <div class="infoCategory" style="width: 100%">
                        <li>물품설명 </li>
                        <div class="infoDetail clientExchangeDescription" style="background: #dcdcdc; width: 100%; max-height: 80px; padding: 10px">

                        </div>
                    </div>

                    <div class="infoCategory" style="width: 100%">
                        <li>요청사항 </li>
                        <div class="infoDetail clientExchangeRequest" style="background: #dcdcdc; width: 100%; max-height: 80px; padding: 10px">

                        </div>
                    </div>
                </ul>

                <div class="clientActionButtonContainer" style="margin-left: 35px">
                    <button class="clientActionButton editExchange">수정하기</button>
                    <button class="clientActionButton cancelExchange">취소하기</button>
                </div>

            </div>

        </div>
    </div>
</div>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="/css/clientConfirmRequestView.css">
<script>
    // global variables
    const data = {
        exchangeId: $('.exchangeId').val()
    }

    $(document).ready(() => {

        $.ajax({
            url: '/api/exchange/view/client/exchange',
            type: 'GET',
            data: data,
            success: (response) => {
                var otherThumbnailImage = ''
                console.log(response)

                $('.clientExchangeTitle').append(response.title)
                $('.clientExchangePrice').append(convert(response.price) + '<span style="font-family: sans-serif; font-size: 18px;"> 원</span>')
                $('.clientExchangeDescription').append(response.content)
                $('.clientExchangeRequest').append(response.request)
                $('.clientExchangeLocation').append(response.location)

                $.each(response.file, (key, value) =>{
                    if(key == 0)
                        $('.thumbnailImage')[0].src = '/upload/' + value

                    otherThumbnailImage += '<img onclick="showThumbnail(this);" style="object-fit: cover; cursor: pointer; border: 1px solid rgb(227, 240, 245); margin: 10px 13px 0 0; width: 65px; height: 65px;" src="/upload/' + value + '">'
                })
                $('.otherImageThumbnail').append(otherThumbnailImage)
            }
        })
    })

    function showThumbnail(e){
        $('.thumbnailImage')[0].src = '/upload/' + e.src.split('/upload/')[1]
    }

    function convert(num){
        var len, point, str;
        num = num + "";
        point = num.length % 3 ;
        len = num.length;
        str = num.substring(0, point);
        while (point < len) {
            if (str != "") str += ",";
            str += num.substring(point, point + 3);
            point += 3;
        }
        return str;
    }
</script>