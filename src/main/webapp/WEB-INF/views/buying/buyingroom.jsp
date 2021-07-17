<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp"%>

<div class="wrapper" style='margin: 80px 0 80px 0; display: flex; flex-direction: row; align-items: flex-start; position: relative; justify-content: center; padding: 30px; width: 90%'>
<div style="display: inline-flex">

    <section id="RoomInnerInfo" style="width: 300px; margin-left: 160px; text-align: right;">

            <c:if test="${buying_room_files[0].name ne null }">
                <div class="product_img_thumbnail" style="margin: 0 0 15px auto; width: 300px; height: 250px; object-fit: cover;">
                    <img id="thumbnail" src="/upload/${buying_room_files[0].name}" style="width: 100%; height: 100%; object-fit: cover;">
                </div>
            </c:if>

            <div class="room_inner_info">

                <div class="buying_product _room_title">
                    ${buying_room_id.roomTitle}
                </div>
                <div class="buying_product _room_description">
                    ${buying_room_id.description}
                </div>

                <hr />
                <div class="buying_product _room_members">
                    멤버 ${buying_room_id.currentUsers}명
                </div>

                <hr />
                <div class="buying_product room_type">
                    그룹설정 : ${buying_room_id.type}
                </div>
            </div>

    </section>

    <section id="content" role="main">
        <div id="buyingroom" class="container">

            <div class="main_container searchBox" style="margin-top: 0">
                <div class="buying_product search_bar">
                    <input type="text" id="search" placeholder="게시글, 작성자, 태그내용을 검색">
                    <div class="autocom-box"></div>
                    <div class="icon">
                        <i><img src="/images/search_26px.png"></i>
                    </div>
                </div>
            </div>

            <div class="main_container contentBox">
                <div class="buying_product contentWriterBox">

                    <div class="main _contentWriteBox">
                        <div style="padding: 10px 18px 10px 18px;">
                            <textarea cols="20" rows="2" class="commentWrite _use_keyup_event" id="chatroom_comment_box" placeholder="다른 멤버들과 소통해보세요."></textarea>
                        </div>
                        <div class="fileArea" style="padding: 10px 18px 10px 18px;">
                            <button><img src="/images/image_gallery_23px.png"></button>
                        </div>
                    </div>

                    <div class="main _notice">

                    </div>

                    <div class="main _contentList" style="padding: 10px 18px 10px 18px;">

                    </div>

                </div>
            </div>

        </div>
    </section>

    <section class="aside _activitySection">
        <div class="card _memberList" style="height: 300px">
            <div class="card-header" style="background-color: white">
                멤버 목록
            </div>
            <div class="card-body">

            </div>
        </div>

        <div class="card _articleCurrentList" style="height: 200px">
            <div class="card-header" style="background-color: white">
                최근 등록된 게시글
            </div>
            <div class="card-body">

            </div>
        </div>
    </section>
</div>
</div>

<link rel="stylesheet" href="/css/buyingroom.css">
<%@ include file="../common/footer.jsp"%>

<script>
    $(document).ready(function(){
        document.getElementsByClassName('_room_price')[0].innerHTML = '금액 ' + convertPrice(document.getElementsByClassName('_room_price')[0].innerHTML.trim()) + '원';
    })

    function showImg(e){
        document.getElementById('thumbnail').src = e.src;
    }

    function convertPrice(num){
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