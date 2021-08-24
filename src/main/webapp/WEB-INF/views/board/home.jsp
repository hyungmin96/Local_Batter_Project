<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp" %>

        <div class="container" style="margin: 120px auto 25px auto;">
            <span class="fast__category">원하시는 활동을 선택해주세요!</span>
            <div class="mainImageWrapper" style="display:flex; text-align: center">
                <div class="groupCreate" style="width: 33%; text-align: center">
                    <div>
                        <img style="border: 1px solid #d3cfcf" src="/images/main/logo.jpg" class="logoImage exchangeBoards">
                    </div>
                    <div></div>
                </div>
                <div class="groupList" style="width: 33%">
                    <div>
                        <img style="border: 1px solid #d3cfcf" src="/images/main/group.jpg" class="logoImage groupList">
                    </div>
                </div>
                <div class="batterServiceList" style="width: 33%">
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
                <div style="width: 100%; height: 300px;">
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
    history.scrollRestoration = "manual"
    $(window).on('unload', function() {
        $(window).scrollTop(0);
    });
</script>
<script type="text/javascript" src="/js/infiniteScroll.js"></script>
<%@ include file="../common/footer.jsp" %>


