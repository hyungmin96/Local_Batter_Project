<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

        <div class="container" style="margin: 120px auto 25px auto;">
            <span class="fast__category">원하시는 활동을 선택해주세요!</span>
            <div class="mainImageWrapper" style="display:flex; text-align: center">
                <div class="bannerGroupCreate" style="width: 33%; text-align: center">
                    <div>
                        <img style="border: 1px solid #d3cfcf" src="/images/main/logo.jpg" class="logoImage exchangeBoards">
                    </div>
                    <div></div>
                </div>
                <div class="bannerGroupList" style="width: 33%">
                    <div>
                        <img style="border: 1px solid #d3cfcf" src="/images/main/group.jpg" class="logoImage goGroupList">
                    </div>
                </div>
                <div class="bannerBatterServiceList" style="width: 33%">
                    <div >
                        <img style="border: 1px solid #d3cfcf" src="/images/main/delivery.jpg" class="logoImage batterService">
                    </div>
                </div>
            </div>
        </div>

        <div class="fast__sale_products" style="padding: 25px; background: rgb(227, 230, 234);">
            <div class="container">
                <span class="fast__category">완전 급해요</span>
                <span class="sub__category">| 빠른 교환을 원하는 물품들이에요</span>
                <div style="width: 100%; height: 300px;">
                </div>
            </div>
        </div>

        <div class="groupListContainer" style="padding: 25px">
            <div class="container">
                <span class="fast__category">이런 그룹은 어떠신가요?</span>
                <span class="sub__category">| 특정 그룹 멤버들과 소통해보세요</span>
                    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel" style="min-height: 606px;">
                        <ol class="carousel-indicators" style="bottom: -18px; filter: invert(40%);">
                            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                        </ol>
                        <div class="carousel-inner">
                            <div class="groupItemBox0 carousel-item active">
                            </div>
                        </div>
                    </div>
            </div>
        </div>

        <div class="batterServiceContainer" style="padding: 25px; background: rgb(227, 230, 234);">
            <div class="container">
                <span class="fast__category">배달서비스로 용돈벌기</span>
                <span class="sub__category">| 물물교환을 대신 진행하고 용돈을 벌어보세요</span>
                <div style="width: 100%; height: 300px;">
                </div>
            </div>
        </div>

        <div class="groupListContainer" style="padding: 25px">
            <div class="container">
                <span class="fast__category">새로운 물품들과 교환해보세요</span>
                <span class="sub__category">| 원하시는 물품을 골라 물물교환 해보세요</span>
                <div class="groupExchangeBoardList">
                </div>
            </div>
        </div>

<script>

    // global variables
    var flag = false
    var groupBoardPage = 0
    var groupPage = 0

    $(document).ready((e) =>{
        loadGroupChatRoomList(e, 0)
    })

    function loadGroupChatRoomList(e, groupPage = 0){

        var groupPageIndex = 0
        var groupKeyIndex = 0

        const data = {
            orderType: false,
            page: groupPage,
            display: 30
        };

        $.ajax({
            url: '/api/group/random/get_group_list',
            type: 'GET',
            data: data,
            success: function (response) {
                $('.groupList').empty()

                $.each(response, function (key, value) {
                    var html = ''
                    const files = (value.filePath != null) ? value.filePath : ''
                    html += "<div class='groupItemContainer' style='cursor: pointer; width: 1400px; display: inline-flex; padding: 15px;'>"
                    html += "<div class='groupThumbnailBox'>"
                    html += "<img class='groupThumbnail' src='/upload/" + files + "'>"
                    html += "</div>"
                    html += "<div class='groupTextContainer' style='margin-left: 20px;'>"
                    html += "<div class='groupTitle'>"
                    html += value.groupTitle
                    html += "</div>"
                    html += "<div class='groupDescription'>"
                    html += value.description
                    html += "</div>"
                    html += "<div class='groupMemberCount'>"
                    html += "멤버 " + value.memberCount + "명"
                    html += "</div>"
                    html += "</div>"
                    html += "</div>"

                    if (groupKeyIndex >= 5) {
                        groupKeyIndex = 0
                        groupPageIndex++
                        $('.carousel-inner').append(
                            '<div class="groupItemBox' + groupPageIndex + ' carousel-item">' +
                            '</div>'
                        )
                        $('.carousel-indicators').append(
                            '<li data-target="#carouselExampleIndicators" data-slide-to="' + groupPageIndex + '"></li>'
                        )
                    }
                    groupKeyIndex++
                    $('.groupItemBox' + groupPageIndex).prepend(html)
                })
            }
        })
    }

    $(window).scroll(function(){
        if($(window).scrollTop() + 10 >= $(document).height() - $(window).height()){
            if(!flag){
                flag = true;
                loadData();
            }
        }
    });

    function loadData(){

        var display = 60;

        $.ajax({

            url: '/api/v2/group/board/get_exchange_list',
            type: 'GET',
            data: { pageNum: groupBoardPage, display: display},
            success: function(data){

                var html = '';

                var todayItemBox = $('.groupExchangeBoardList');

                $.each(data.content, function(key, value){

                    console.log(value)
                    var boardId = value.id;
                    var title = value.title;
                    var price = value.price;
                    var location = value.location;

                    let fileName = value.thumbnail != null ? value.thumbnail : '';
                    html += '<div class="img_box fadein" style="width: 230px; margin: 0 12px 0 12px;">';
                    html += '<div onclick="goToUrl(' + boardId + ')" class="today-item-box">';
                    html += '<div class="today-box">';
                    html += '<img src="/upload/' + fileName + '" onerror="this.style.display=none">';
                    html += '</div>';
                    html += '<div class="today-detail-box">';
                    html += '<div class="type">';
                    html += '<div class="title">' + title + '</div>';
                    html += '<div class="board-line"></div>';
                    html += '<div class="line">';
                    html += '<div class="price">' + convert(price) + '<span class="k-money">원</span></div>';
                    html += '</div>';
                    html += '<hr style="border: none; height: 1px; background-color: #848484; margin: 5px 0 5px 0;"/>';
                    html += '<span class="badge bg-secondary">' + location + '</span>';
                    html += '</div>';
                    html += '</div>';
                    html += '</div>';
                    html += '</div>';

                });

                todayItemBox.append(html);

                groupBoardPage++;
                flag = false;
            }

        })

    }

    function goToUrl(id){
        location.href = 'http://localhost:8000/board/article/' + id;
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

    history.scrollRestoration = "manual"
    $(window).on('unload', function() {
        $(window).scrollTop(0);
    });
</script>
<%@ include file="../common/footer.jsp" %>

