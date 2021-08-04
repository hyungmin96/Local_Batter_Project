<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../common/header.jsp"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="wrapper" style='margin: 80px 0 80px 0; display: flex; flex-direction: row; align-items: flex-start; position: relative; justify-content: center; padding: 30px; width: 90%'>
<div style="display: inline-flex">

    <input type="hidden" class="groupId" value="${groupObject.id}">

    <div>
        <section id="RoomInnerInfo" style="width: 300px; margin-left: 160px; text-align: right;">
                <c:if test="${groupObject.filePath ne null }">
                    <div class="product_img_thumbnail" style="margin: 0 0 15px auto; width: 300px; height: 250px; object-fit: cover;">
                        <img id="thumbnail" src="/upload/${groupObject.filePath}" style="width: 100%; height: 100%; object-fit: cover;">
                    </div>
                </c:if>

                <div class="groupAside room_inner_info">
                    <div style="display: inline-flex; width: 100%; justify-content: space-between;">
                        <div class="Group_product">그룹정보</div>
                        <div data-bs-toggle="modal" data-bs-target="#groupSettingModal" style="cursor: pointer;">
                                설정
                            <img style="width: 16px; height: 16px;" src="/images/settings_20px.png">
                        </div>
                    </div>

                    <hr />

                    <div class="Group_product _room_title">
                        ${groupObject.groupTitle}
                    </div>
                    <div class="Group_product _room_description">
                        ${groupObject.description}
                    </div>

                    <hr />
                    <div class="Group_product _room_reader">
                        관리자 ${groupObject.owner}
                    </div>

                    <hr />
                    <div class="Group_product _room_members">
                        멤버 ${groupObject.memberCount}명
                    </div>

                    <hr />
                    <div class="Group_product room_type">
                        그룹설정 : ${groupObject.type}
                    </div>
                </div>
                <div style="cursor: pointer; display: none; background-color:#cde4cc; text-align: center;" class="groupAside _groupEnterButton">
                    <button>그룹 가입하기</button>
                </div>
            <div style="cursor: pointer; display: none; background-color: rgb(220 218 218); text-align: center;" class="groupAside _groupExitButton">
                <button>그룹 나가기</button>
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
                            <div data-bs-toggle="modal" data-bs-target="#groupBoardWriteModal" style="color: #b2afaf; cursor: pointer;" class="commentWrite" value="">다른 멤버들과 소통해보세요.</div>
                        </div>
                    </div>
                    <div class="preview _imgPreviewSlider" style="height: 100%; background-color: white; ">

                    </div>
                </div>
            </div>

                <div style="display: none" class="main _notice">
                    <div class="card-header">
                        공지사항
                    </div>
                    <div class="noticeContainer">

                    </div>
            </div>


            <div class="main _contentListWrapper" style="height: 100%; background-color: white">
                <div class="card-header">
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
            <div class="card-header">
                <div style = 'display: flex; justify-content: space-between; margin-right: 5px;'>
                    <div>멤버 목록</div>
                    <div class="usersOptionContainer">
                        <button class="chattingButton">채팅하기</button>
                    </div>
                </div>
            </div>
            <div class="card-body" style="flex-wrap: wrap; overflow: scroll; padding: 0">
                <div class="memberList" style="display: flex; flex-direction: column;">

                </div>
            </div>
        </div>

        <section class="aside _currentImages">
            <div class="card _articleCurrentList" style="height: 297px; padding: 0">
                <div class="card-header">
                    최근 게시물 사진
                </div>
                <div class="card-body _latestImageContainer" style="flex-wrap: wrap;display: inline-flex; padding: 10px 1px 1px 10px;">
                    <c:forEach var="item" items="${group_files}" varStatus="i">
                        <div>
                            <div class='showBoard _boardIdInfo'>
                                <input type='hidden' name="boardId" class='boardId_${item.groupBoard.boardId}'>
                                <img src=/upload/${item.name}>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </section>

        <div class="card _commentCurrentList" style="max-height: 240px">
            <div class="card-header">
                최근 등록된 덧글
            </div>
            <div class="card-body _latestCommentContainer">
                <c:forEach var="item" items="${group_comments}" varStatus="i">
                    <div class='showBoard _boardIdInfo'>
                        <div class='latest _latestCommentBox'>
                            <input type='hidden' name="boardId" class='boardId_${item.groupId}'>
                            <span class='latest _commentContent'>${item.comment}</span>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </section>
</div>
</div>

<!--group board view modal -->
<div class="modal fade" id="groupBoardViewModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top: 150px; width: 800px; height: 700px;">
        <div class="modal-content" style="width: 600px; border-radius: 0;">
            <div class="boardModal" style="padding: 0.5rem;">

            </div>
        </div>
    </div>
</div>

<!-- group board wirte modal -->
<div class="modal fade" id="groupBoardWriteModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top: 150px; width: 800px; height: 700px;">
        <div class="modal-content" style="width: 600px;">
            <div class="modal-header" style="border: none;">
                <div class="modal-title" id="groupBoardModal" style="margin: 0 0 0 auto">그룹 게시글 작성</div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" style="padding: 0.5rem 0.5rem 0 0.5rem">
                <div style="padding: 10px; margin-top: 10px;">
                    <div id="board_content_box" class="commentWrite _use_keyup_event" contentEditable="true" data-text="그룹에 새로운 게시글을 작성해보세요" tabindex="0" spellcheck="true" role="textbox"></div>
                    <div class="upload _contentUploadBox" style="display: inline-flex; width: 100%">
                        <input type="file" id="uploadFile" multiple="multiple" style="display: none;">
                        <div class="fileArea _imgUploadDialog" style="cursor: pointer;">
                            <button><img src="/images/picture_26px.png"></button>
                        </div>
                        <div class="articleWordsBox">
                            <span class="currentWords">0</span> /
                            <span class="limitWords">300</span>
                        </div>
                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <div class="uploadBtn" style="margin-left: auto; align-items: flex-end;">
                    <button style="height: 35px; width: 70px; padding: 3px 10px 3px 10px; color: white; background-color: rgb(56, 62, 76);">글 작성</button>
                </div>
                <div class="updateBtn" style="display: none; margin-left: auto; align-items: flex-end;">
                    <button style="height: 35px; width: 70px; padding: 3px 10px 3px 10px; color: white; background-color: rgb(56, 62, 76);">글 수정</button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- group setting modal -->
<div class="modal fade" id="groupSettingModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="margin-top: 150px; width: 800px; height: 700px;">
        <div class="modal-content">
            <div class="modal-header">
                <div class="modal-title" id="exampleModalLabel">그룹 설정</div>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body" style="text-align: center">
                <div style="padding: 10px; margin-top: 10px;">
                    <div>
                        <img id="img__upload" src="/images/upload.png" style="border: 1px solid #f5f5f5; width: 198px; height: 198px; object-fit: cover; border-radius: 50%">
                        <input id="input__Group__img" type="file" accept="image/jpg, image/jpeg, image/png" class="custom-file-input" name="upload_file" style="display: none">
                        <div id="preview__img__container"></div>
                    </div>
                </div>

                <div class="article__item__box">
                    <input type="text" name="title" id="Group__title" class="inputbox" placeholder="그룹 이름을 설정해주세요."/>
                </div>

                <div class="article__item__box">
                    <textarea class="inputbox" id="Group__desciption" placeholder="그룹에 대한 설명을 입력해주세요." style="resize: none;" rows="3"></textarea>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-danger groupDeleteButton">삭제</button>
                <button type="button" class="btn btn-primary groupUpdateButton">저장</button>
            </div>
        </div>
    </div>
</div>

<!-- image view modal -->
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

<script type="text/javascript" src="/js/group/groupClickEvents.js"></script>
<script type="text/javascript" src="/js/group/groupAPis.js"></script>
<script type="text/javascript" src="/js/imgModal.js"></script>
<link rel="stylesheet" href="/css/buyingroom.css">
<link rel="stylesheet" href="/css/imgModal.css">
<link rel="stylesheet" href="/css/imgGridGallery.css">
<%@ include file="../common/footer.jsp"%>