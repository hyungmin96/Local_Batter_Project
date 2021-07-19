<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp"%>

<div class="wrapper" style='margin: 80px 0 80px 0; display: flex; flex-direction: row; align-items: flex-start; position: relative; justify-content: center; padding: 30px; width: 90%'>
<div style="display: inline-flex">

    <input type="hidden" class="groupId" value="${Group_room_id.id}">
    <input type="hidden" class="user" value="${group_user_entity.id}">

    <section id="RoomInnerInfo" style="width: 300px; margin-left: 160px; text-align: right;">

            <c:if test="${Group_room_files[0].name ne null }">
                <div class="product_img_thumbnail" style="margin: 0 0 15px auto; width: 300px; height: 250px; object-fit: cover;">
                    <img id="thumbnail" src="/upload/${Group_room_files[0].name}" style="width: 100%; height: 100%; object-fit: cover;">
                </div>
            </c:if>

            <div class="room_inner_info">

                <div class="Group_product _room_title">
                    ${Group_room_id.roomTitle}
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
                            <textarea cols="20" rows="2" class="commentWrite _use_keyup_event" id="chatroom_comment_box" placeholder="다른 멤버들과 소통해보세요."></textarea>
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

            <div class="main _notice">
                <div class="card-header" style="background-color: white">
                    공지사항
                </div>
                <div class="noticeContainer">

                </div>
            </div>

            <div class="main _contentListWrapper" style="height: 340px; background-color: white">
                <div class="card-header" style="background-color: white;">
                    게시글 목록
                </div>
                 <div class="contentEmptyContiner" style="height: 80%; display: flex;">
                    <div class="emptyIcon" style="margin: auto auto; display: none;">
                        <div style="margin-left: 45px;"><img src="/images/group/add_to_clipboard_50px.png"></div>
                        <div style="color: #8d8d8d">첫 게시글을 작성해보세요.<div>
                    </div>
                </div>
            </div>
    </section>

    <section class="aside _activitySection">
        <div class="card _memberList" style="height: 330px; padding: 0">
            <div class="card-header" style="background-color: white">
                멤버 목록
            </div>
            <div class="card-body" style="flex-wrap: wrap; overflow: scroll; padding: 0">
                <div class="userList" style="display: flex; flex-direction: column;">
                    <c:forEach var="item" items="${Group_room_id.users}" varStatus="status">
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

<script type="text/javascript" src="/js/buyingroom.js"></script>
<link rel="stylesheet" href="/css/buyingroom.css">
<link rel="stylesheet" href="/css/imgGridGallery.css">
<%@ include file="../common/footer.jsp"%>