<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

        <div style="display: flex; margin: 100px auto 25px auto; padding: 10px 120px 10px 120px">
            <div class="mainImageWrapper" style="display:flex; text-align: center; margin: auto">
                <div class="bannerGroupCreate">
                    <div>
                        <img style="height: 480px; width: 880px; border: 1px solid #d3cfcf" src="/images/main/logo.jpg" class="logoImage exchangeBoards">
                    </div>
                    <div></div>
                </div>
                <div class="subBannerContainer">
                    <div class="bannerGroupList">
                        <div>
                            <img style="height: 270px; width: 480px; border: 1px solid #d3cfcf" src="/images/main/group.jpg" class="logoImage goGroupList">
                        </div>
                    </div>
                    <div class="bannerBatterServiceList">
                        <div >
                            <img style="object-fit: fill; height: 210px; width: 480px; border: 1px solid #d3cfcf" src="/images/main/delivery.jpg" class="logoImage batterService">
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="groupListContainer" style="padding: 25px; background: rgb(227, 230, 234);">
            <div class="container">
                <span class="fast__category">이런 그룹은 어떠신가요?</span>
                <span class="sub__category">| 특정 그룹 멤버들과 소통해보세요</span>
                    <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel" style="min-height: 535px;">
                        <ol class="carousel-indicators" style="bottom: -18px; filter: invert(40%);">
                            <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                        </ol>
                        <div class="carousel-inner" style="padding: 0 0 0 33px">
                            <div class="groupItemBox0 carousel-item active" style="display:flex; flex-direction: row; flex-wrap: wrap;">
                            </div>
                        </div>
                    </div>
            </div>
        </div>

        <div class="batterServiceContainer" style="padding: 25px;">
            <div class="container">
                    <div class="fast__category">배달서비스로 용돈벌기</div>
                    <span class="sub__category">| 물물교환을 대신 진행하고 용돈을 벌어보세요</span>

                <div style="padding: 0 0 0 33px; width: 100%; max-height: 500px;">
                    <div class="localBatterServiceColumn" style="border-bottom: 1px solid #d9d6d6; text-align: center; width: 100%; display: inline-flex;">
                        <div style="width: 25%">등록시간</div>
                        <div style="width: 40%">교환위치</div>
                        <div style="width: 15%">닉네임</div>
                        <div style="width: 10%">결제금액</div>
                        <div style="width: 10%"></div>
                    </div>
                    <div class="serviceListContainer" style="display:flex; width: 100%; max-height: 500px; background: white;">
                        <div class="emptyServiceView" style="margin: auto;">
                            등록된 서비스가 존재하지 않습니다.
                        </div>
                        <div class="serviceItemsContainer" style="padding: 10px; max-height: 500px; width: 100%; text-align: center">

                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="groupListContainer" style="padding: 25px; background: rgb(227, 230, 234);">
            <div class="container">
                <span class="fast__category">새로운 물품들과 교환해보세요</span>
                <span class="sub__category">| 원하시는 물품을 골라 물물교환 해보세요</span>
                <div class="groupExchangeBoardList">
                </div>
            </div>
        </div>
<link rel="stylesheet" href="/css/articleList.css">
<script>

    // global variables
    var flag = false
    var groupBoardPage = 0
    var groupPage = 0

    $(document).ready((e) =>{
        loadGroupChatRoomList(e, 0)
        loadServiceListAjax()
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
                    html += "<div id='groupId_" + value.id + "' onclick=location.href='/group/" + value.id + "' class='groupItemContainer' style='cursor: pointer; width: 545px; display: inline-flex; padding: 10px;'>"
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

                    // 하나의 indicator에서 표시할 그룹의 갯수
                    if (groupKeyIndex >= 10) {
                        groupKeyIndex = 0
                        groupPageIndex ++
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

    function loadServiceListAjax(){

        $.ajax({
            url: '/api/exchange/get_service_list',
            type: 'GET',
            data: '',
            success: (response) =>{
                if(response.length > 0){
                    $('.emptyServiceView').hide()
                    var html = ''
                    var moreViewButton = ''
                    $.each(response, (key, value) =>{
                        html += '<div class="localBatterServiceItem" id=localBatterServiceId_' + value.id + '>'
                        html += '<div style="padding: 10px; display: inline-flex; width: 100%;">'
                        html += '<div style="width: 25%; margin: auto 0 auto 0">'
                        html += new Date(value.regTime).toLocaleTimeString([], {
                            'year': '2-digit',
                            'month': '2-digit',
                            'day': '2-digit',
                            'hour': '2-digit',
                            'minute': '2-digit',
                        }) + '분'
                        html += '</div>'
                        html += '<div style="width: 40%;">'
                        html += '<div>' + value.exchangeAddr + '</div>'
                        html += '<div>' + value.exchangeDetailAddr + '</div>'
                        html += '</div>'
                        html += '<div style="width: 16%; margin: auto 0 auto 0;">작성자 닉네임</div>'
                        html += '<div style="width: 11%; margin: auto 0 auto 0;">' + value.price + '원</div>'
                        html += '<div style="width: 8%;"><button>보기</button></div>'
                        html += '</div>'
                        html += '</div>'
                    })
                    $('.serviceItemsContainer').append(html)
                    if(response.length >= 5){
                        moreViewButton += '<div style="padding: 10px; width: 100%">'
                        moreViewButton += '<button style="width: 100%; font-family: Pretendard-Regular">더 보기</button>'
                        moreViewButton += '</div>'
                        $('.serviceItemsContainer').append(moreViewButton)
                    }
                }
            }
        })

    }

    var isLastBoardPage = false;
    function loadData(){

        var display = 60;

        $.ajax({

            url: '/api/v2/group/board/get_exchange_list',
            type: 'GET',
            data: { pageNum: groupBoardPage, display: display},
            success: function(response){

                var html = '';
                var todayItemBox = $('.groupExchangeBoardList');

                if(!isLastBoardPage){
                    isLastBoardPage = (response.last == true)
                    $.each(response.content, function(key, value){

                        var boardId = value.boardId;
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
            }

        })

    }

    function goToUrl(boardId){
        location.href = 'http://localhost:8000/request/exchange/' + boardId;
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


