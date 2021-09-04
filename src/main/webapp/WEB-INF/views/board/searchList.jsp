<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ include file="../common/header.jsp" %>

<div class="searchWrapperContainer" style="margin-top: 105px;">
    <div class="container" style="min-height: 814px;">

        <input type="hidden" class="searchKeywordValue" value="${search}"/>

        <div class="searchResultWrapper">

            <div class="selectCategory" style="display: flex; flex-direction: row">
                <button class="selectButton selectBoardsButton" style="background: #e0dcdc; border-radius: 15px;">게시글</button>
                <button class="selectButton selectGroupButton" style="border-radius: 15px;">그룹</button>
                <div class="searchResultBox" style="padding: 3px; margin-left: 10px;">
                    <span class="searchKeyword"></span> 검색결과 <span class="searchCount"></span>개가 검색되었습니다.
                </div>
            </div>

            <hr style="border: none; height: 1px; background: #8a8888;"/>

            <div>
                <div class="searchItemsContainer" style="padding: 10px;">

                </div>
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

    var searchBoardPage = 0
    var searchGroupPage = 0
    function loadSearchDataAjax(){

        const data = {
            search: $('.searchKeywordValue').val(),
            order: 'Latest',
            page: searchBoardPage,
            display: 50
        }

        $.ajax({
            url: '/api/search/get_exchange_list/',
            type: 'GET',
            data: data,
            success: (response) => {
                $('#search-value')[0].value = data.search
                $('.searchKeyword')[0].innerHTML = data.search
                $('.searchCount')[0].innerHTML = response.numberOfElements

                $('.searchItemsContainer').empty()
                if(response.numberOfElements > 0){
                    var html = ''
                    $.each(response.content, (key, value) =>{
                        console.log(value)
                        html += '<div style="width: 100%;">';
                        html += '<div class="searchItemBox" style="padding: 10px; margin: auto auto 12px auto; height: 250px; width: 900px; background: white; border: 1px solid rgb(238, 238, 238);">';
                        html += '<div onclick="goToUrl(' + value.boardId + ')" style="cursor: pointer;">';
                        html += '<div class="searchItemTitle">' + value.title + '</div>';
                        html += '<hr style="border: none; height: 1px; background: #918f8f;">';
                        html += '<div style="padding: 0 10px 0 10px;">';
                        html += '<div style="display: flex; flex-direction: row; justify-content: space-between;">';
                        html += '<div class="searchItemContent">' + value.content + '</div>';
                        html += '<div class="searchItemThumbnail">'
                        html += '<img class="searchItemThumbnailImg" src=/upload/' + value.thumbnail + '>'
                        html += '</div>';
                        html += '</div>';
                        html += '</div>';
                        html += '<div class="searchItemUser" style="display: flex; flex-direction: row">'
                        html += '<div class="searchItemUserProfile">'
                        html += '<img class="searchItemUserProfileImg" src="/upload/' + value.writerProfile + '">'
                        html += '</div>'
                        html += '<div class="searchItemUsername" style="margin: auto 10px auto 10px;">' + value.writerUsername + '</div>'
                        html += '<div class="searchItemRegTime" style="margin: auto 10px auto 10px;">'
                        html += new Date(value.regTime).toLocaleDateString([], {
                            'year' : '2-digit',
                            'month' : '2-digit',
                            'day' : '2-digit',
                            'hour' : '2-digit',
                            'minute' : '2-digit',
                        }) + '분'
                        html += '</div>'
                        html += '</div>';
                        html += '</div>';
                        html += '<hr style="border: none; height: 1px; background: #918f8f; margin: 10px 0 10px 0">';
                        html += '<a class="searchGroupUrl" style="text-decoration: none;" href="http://localhost:8000/group/' + value.groupId + '"><div class="searchGroupId_' + value.groupId + '" style="display: flex; flex-direction: row;">'
                        html += '<div class="searchItemGroupProfile">'
                        html += '<img class="searchItemGroupProfileImg" src="/upload/' + value.groupProfile + '">'
                        html += '</div>'
                        html += '<div class="searchItemGroupTitle" style="margin: auto 5px auto 5px;">' + value.groupTitle + '</div>';
                        html += '</div></a>';
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
<script>

    function getSearchGroupList(){
        const data = {
            search: $('.searchKeywordValue').val(),
            order: 'Latest',
            page: searchGroupPage,
            display: 20
        }

        $.ajax({
            url: '/api/search/get_group_list/',
            type: 'GET',
            data: data,
            success: (response) =>{
                $('.searchItemsContainer').empty()
                $('#search-value')[0].value = data.search
                $('.searchKeyword')[0].innerHTML = data.search
                $('.searchCount')[0].innerHTML = response.numberOfElements
                console.log(response)

                $.each(response.content, function (key, value) {
                    var html = ''
                    const files = (value.filePath != null) ? value.filePath : ''
                    html += "<div id='groupId_" + value.id + "' onclick=location.href='/group/" + value.groupId + "' class='groupItemContainer' style='cursor: pointer; width: 100%; display: inline-flex; padding: 10px;'>"
                    html += "<div class='groupThumbnailBox' style='border: 1px solid #d2d2d2;'>"
                    html += "<img class='groupThumbnail' src='/upload/" + files + "'>"
                    html += "</div>"
                    html += "<div class='groupTextContainer' style='margin-left: 20px;'>"
                    html += "<div class='groupTitle'>"
                    html += value.groupTitle
                    html += "</div>"
                    html += "<div style='margin: 3px 0 0 5px;'>"
                    html += "<div class='groupDescription'>"
                    html += value.description
                    html += "</div>"
                    html += "<div class='groupMemberCount'>"
                    html += "멤버 " + value.memberCount + "명"
                    html += "</div>"
                    html += "</div>"
                    html += "</div>"
                    html += "</div>"

                    $('.searchItemsContainer').prepend(html)
                })

            }
        })
    }

</script>
<script>

    $(document).on('click', '.selectGroupButton', ()=>{
        $('.selectBoardsButton')[0].style.background = 'transparent'
        $('.selectGroupButton')[0].style.background = '#e0dcdc'
        getSearchGroupList()
    })

    $(document).on('click', '.selectBoardsButton', ()=>{
        $('.selectBoardsButton')[0].style.background = '#e0dcdc'
        $('.selectGroupButton')[0].style.background = 'transparent'
        loadSearchDataAjax()
    })

</script>