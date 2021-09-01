<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>

<div class="searchWrapperContainer" style="margin-top: 105px;">
    <div class="container" style="min-height: 814px;">

        <input type="hidden" class="searchKeywordValue" value="${search}"/>

        <div class="searchResultWrapper">

            <div class="searchResultBox">
                <span class="searchKeyword"></span>의 검색결과 <span class="searchCount"></span>개가 검색되었습니다.
            </div>

            <div class="searchItemsContainer" style="padding: 10px; display: flex; flex-wrap: wrap; flex-direction: row;">

            </div>

        </div>

    </div>
</div>

<%@ include file="../common/footer.jsp" %>
<link rel="stylesheet" type="text/css" href="/css/searchList.css">
<script>

    $(document).ready(() => {
        loadSearchDataAjax()
    })

</script>
<script>

    var searchPage = 0
    function loadSearchDataAjax(){

        const data = {
            search: $('.searchKeywordValue').val(),
            order: 'Latest',
            page: searchPage,
            display: 50
        }

        $.ajax({
            url: '/api/search/get_exchange_list/',
            type: 'GET',
            data: data,
            success: (response) => {
                console.log(response)
                $('#search-value')[0].value = data.search
                $('.searchKeyword')[0].innerHTML = data.search
                $('.searchCount')[0].innerHTML = response.numberOfElements

                if(response.numberOfElements > 0){
                    var html = ''
                    $.each(response.content, (key, value) =>{
                        html += '<div class="img_box fadein" style="border: 1px solid rgb(238, 238, 238); cursor: pointer; width: 196px; height: 316px; margin: 0 6px 0 6px;">';
                        html += '<div onclick="goToUrl(' + value.boardId + ')" style="width: 189px;" class="today-item-box">';
                        html += '<div class="today-box">';
                        html += '<img style="width: 189px; height: 196px;" src="/upload/' + value.thumbnail + '" onerror="this.style.display=none">';
                        html += '</div>';
                        html += '<div class="today-detail-box" style="width: 189px; background: white; border-top: 1px solid rgb(238, 238, 238)">';
                        html += '<div style="padding: 10px;" ss="type">';
                        html += '<div class="title">' + value.title + '</div>';
                        html += '<div class="board-line"></div>';
                        html += '<div class="line">';
                        html += '<div class="price">' + convert(value.price) + '<span class="k-money" style="font-size: 12px;"> 원</span></div>';
                        html += '</div>';
                        html += '</div>';
                        html += '<div style="border-top: 1px solid rgb(238, 238, 238); padding: 5px;">';
                        html += '<div style="border-radius: 0" class="badge bg-secondary">' + value.location + '</div>';
                        html += '</div>';
                        html += '</div>';
                        html += '</div>';
                        html += '</div>';
                    })
                    $('.searchItemsContainer').append(html)
                }
            }
        })

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

    function goToUrl(boardId){
        location.href = 'http://localhost:8000/request/exchange/' + boardId;
    }

</script>