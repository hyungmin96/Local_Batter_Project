<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp"%>

<div class="wrapper" style='margin: 80px 0 80px 0; display: flex; flex-direction: row; align-items: flex-start; position: relative; justify-content: center; padding: 30px; width: 90%'>
<div style="display: inline-flex">

    <input type="hidden" class="groupId" value="${Group_room_id.id}">
    <input type="hidden" class="user" value="${user_id.id}">

    <div>
        <section id="RoomInnerInfo" style="width: 300px; margin-left: 160px; text-align: right;">
                <c:if test="${Group_room_files[0].name ne null }">
                    <div class="product_img_thumbnail" style="margin: 0 0 15px auto; width: 300px; height: 250px; object-fit: cover;">
                        <img id="thumbnail" src="/upload/${Group_room_files[0].name}" style="width: 100%; height: 100%; object-fit: cover;">
                    </div>
                </c:if>

                <div class="room_inner_info">
                    <div class="Group_product">
                        그룹정보
                    </div>
                    <hr />

                    <div class="Group_product _room_title">
                        ${Group_room_id.title}
                    </div>
                    <div class="Group_product _room_description">
                        ${Group_room_id.description}
                    </div>

                    <hr />
                    <div class="Group_product _room_reader">
                        관리자 ${Group_room_id.owner}
                    </div>

                    <hr />
                    <div class="Group_product _room_members">
                        멤버 ${Group_room_id.currentUsers}명
                    </div>

                    <hr />
                    <div class="Group_product room_type">
                        그룹설정 : ${Group_room_id.type}
                    </div>
                </div>
        </section>
    </div>

    <section id="content" role="main">
        <div id="contentWrapper" class="container">

            <div class="main_container searchBox" style="margin-top: 0">
                <div class="Group_product search_bar">
                    <input type="text" id="search" placeholder="게시글, 작성자, 태그내용을 검색">
                    <div class="autocom-box"></div>
                    <div class="icon">
                        <i><img src="/images/search_26px.png"></i>
                    </div>
                </div>
            </div>

            <div class="main_container contentBox">
                <div class="Group_product contentWriterBox">

                    <div class="main _contentWriteBox">
                        <div style="padding: 10px 18px 10px 18px;">
                            <textarea cols="20" rows="2" class="commentWrite _use_keyup_event" id="board_content_box" placeholder="다른 멤버들과 소통해보세요." value=""></textarea>
                        </div>
                        <div class="upload _contentUploadBox" style="display: inline-flex; width: 100%">
                            <input type="file" id="uploadFile" multiple="multiple" style="display: none;">
                            <div class="fileArea _imgUploadDialog" style="padding: 16px 15px 10px 18px;">
                                <button><img src="/images/group/image_gallery_23px.png"></button>
                            </div>
                            <div class="uploadBtn" style="margin-left: auto; align-items: flex-end; padding: 10px 10px 10px 5px;">
                                <button style="height: 35px; width: 70px; padding: 3px 10px 3px 10px; color: white; background-color: rgb(56, 62, 76);">글 작성</button>
                            </div>
                        </div>
                    </div>
                    <div class="preview _imgPreviewSlider" style="height: 100%; background-color: white; ">

                    </div>
                </div>
            </div>

            <div style="display: none" class="main _notice">
                <div class="card-header" style="background-color: white">
                    공지사항
                </div>
                <div class="noticeContainer">

                </div>
            </div>

            <div class="main _contentListWrapper" style="height: 100%; background-color: white">
                <div class="card-header" style="background-color: white;">
                    게시글 목록
                </div>
                <div class="contentContainer">

                </div>
                 <div class="contentEmptyContiner" style="display: none; height: 340px;">
                     <div style="display: flex; height: 330px;">
                        <div class="emptyIcon" style="margin: auto;">
                            <div style="text-align: center;"><img src="/images/group/add_to_clipboard_50px.png"></div>
                            <div style="color: #8d8d8d">첫 게시글을 작성해보세요.<div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>

    <section class="aside _activitySection">
        <div class="card _memberList" style="height: 330px; padding: 0">
            <div class="card-header" style="background-color: white">
                <div style = 'display: flex; justify-content: space-between; margin-right: 5px;'>
                    <div>멤버 목록</div>
                    <div class="usersOptionContainer">
                        <button class="chattingButton">채팅하기</button>
                        <button class="moreUsersMenu">더 보기</button>
                    </div>
                </div>
            </div>
            <div class="card-body" style="flex-wrap: wrap; overflow: scroll; padding: 0">
                <div class="userList" style="display: flex; flex-direction: column;">
                    <c:forEach var="item" items="${group_users_entity}" varStatus="status">
                        <div class="userList _userItemBox" style="cursor: pointer;">
                            <div class="userProfileBox">
                                <img src="/upload/${item.user.profile.profilePath}">
                            </div>
                            <div class="userName" style="margin: auto 0 auto 8px;">
                                ${item.user.username}
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>

        <section class="aside _currentImages">
            <div class="card _memberList" style="height: 297px; padding: 0">
                <div class="card-header" style="background-color: white">
                    최근 게시물 사진
                </div>
                <div class="card-body _latestImageContainer" style="flex-wrap: wrap;display: inline-flex; padding: 10px 1px 1px 10px;">

                </div>
            </div>
        </section>

        <div class="card _articleCurrentList" style="height: 240px">
            <div class="card-header" style="background-color: white">
                최근 등록된 덧글
            </div>
            <div class="card-body _latestCommentContainer">

            </div>
        </div>
    </section>
</div>
</div>

<!-- modal -->
<div id="imgModal" style="padding: 20px; display: none; width: 1300px; height: 820px;">
    <div class = "modal_close_btn"><img src="/images/delete_35px.png" style="width: 30px; height: 30px;float: right; cursor: pointer;"></div>

    <div id="carouselExampleControls" class="carousel" data-interval="10000000" data-bs-ride="carousel">
        <div class="carousel-inner">

        </div>
        <div>
            <button class="orderButton carousel-control-prev" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
        </div>
        <div>
            <button class="orderButton carousel-control-next" type="button" data-bs-target="#carouselExampleControls" data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
    </div>

    <div class="imgviewer _page">
        <span class="number _currentPageNumber"></span> /
        <span class="number _lastPageNumber"></span>
    </div>

</div>
<!-- modal -->
<script>
    history.scrollRestoration = "manual"
    $(window).on('unload', function() {
        $(window).scrollTop(0);
    });
</script>
<script type="text/javascript" src="/js/buyingroom.js"></script>
<script type="text/javascript" src="/js/imgModal.js"></script>
<script type="text/javascript" src="/js/buyingchatroom.js"></script>
<link rel="stylesheet" href="/css/buyingroom.css">
<link rel="stylesheet" href="/css/imgModal.css">
<link rel="stylesheet" href="/css/imgGridGallery.css">
<%@ include file="../common/footer.jsp"%>