let GroupRoomInfoImgs = [];
let chatRoomImgDeleteIndex = [-1];
let index;

$(document).ready(function(){
    $('#smartwizard').smartWizard({
    selected: 0,
    theme: 'dots',
    autoAdjustHeight:true,
    transitionEffect:'fade',
    showStepURLhash: false,
    });
    loadGroupChatRoomList();
});

$(function(){

    $('#enter__btn').click(function(){
        enterRoom();
    })
    $('#createGroupButton').click(function(){
        createGroup();
    })
    $('#img__upload').click(function(){
        $('#input__Group__img').click();
    })
    $("#input__Group__img").change(function(e){
        GroupPreview(e);
    });
})

$(document).on('click', '.group_item_box', function(){
    const groupId = $(this)[0].getAttribute('id').split('box__')[1]
    window.location.href = '/group/' + groupId
})


function loadGroupChatRoomList(e, page = 0){

    const data = {
        userId: $('.g_user_id').val(),
        orderType: false,
        page: page,
        display: 30
    };

    $.ajax({
        url: '/api/group/join/get_group_list',
        type: 'GET',
        data: data,
        success: function(response){
            $.each(response.content, function(key, value){
                const files = (value.filePath != null) ? value.filePath : '';
                $('.Group__room__list').append(
                    "<div id='item__box__" + value.id + "' class='group_item_box' style='width: 190px;'>" +
                    "<div style='border-bottom: 1px solid #f1eeee'>" +
                        "<img style='padding: 0 1px 0 1px' src=/upload/" + files + ">" +
                    "</div>" +
                    "<div class='Group__room__content'>" +
                        "<div class = 'Group__room__title'>" + value.groupTitle + "</div>" +
                            "<div class='user_number'>" +
                                "<span>인원 : </span>" +
                                "<span class = 'Group__room__users'>" + value.memberCount + "명</span>" +
                            "</div>" +
                        "<hr style='margin: 7px 0 7px 0; border: none; height: 1px; background: #cccccc'>" +
                        "<div class = 'groupLocation'>" + value.location + "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>" +
                    "</div>"
                );
            })
        }
    })
}


function createGroup(){

    let i;
    const formData = new FormData();
    const checkboxArary = $(".checkbox");

    formData.append('userId', $('.g_user_id').val());
    formData.append('title', $('.groupTitle').val());
    formData.append('description', $('.groupDescriptionText').val());
    formData.append('owner', $('.g_user_name').val());
    formData.append('location', $('#location').val());
    formData.append('tag', $('#tags').val());
    formData.append('chk_1', $('#' + checkboxArary[0].children[0].id).val());
    formData.append('chk_2', $('#' + checkboxArary[1].children[0].id).val());
    formData.append('chk_3', $('#' + checkboxArary[2].children[0].id).val());

    for(i = GroupRoomInfoImgs.length - 1; i > -1; i--)
        if(GroupRoomInfoImgs[i] == null) GroupRoomInfoImgs.splice(i, 1);

    for (i = 0; i < GroupRoomInfoImgs.length; i++)
        formData.append('files', GroupRoomInfoImgs[i]);

    $.ajax({

        url: '/api/group/create',
        type: 'POST',
        data: formData,
        contentType: false,
        processData: false,
        success: function(){
            alert('그룹을 생성하였습니다.')
            window.location.reload();
        }
    })
}

function GroupPreview(e){

    const files = e.target.files;
    const filesArr = Array.prototype.slice.call(files);

    filesArr.forEach(function(f){
        if(!f.type.match("image.*")){
            alert("이미지파일만 업로드가 가능합니다.");
            return;
        }
        GroupRoomInfoImgs.push(f);
        const reader = new FileReader();
        reader.onload = function(e){
            const html = "<a href=\"javascript:void(0);\"" +
                "onclick=\"previewDelete(" + index + ")\"" +
                "id=\"img_id_" + index + "\"><img style='margin: 10px 0 0 10px; object-fit: cover; display: inline-flex;' src=\"" + e.target.result +
                "\"boarder='10' height='190px' width='190px' data-file='" + f.name + "'" +
                "' class='selProductFile' title='사진 삭제'></a>";
            $('#preview__img__container').empty()
            $("#preview__img__container").append(html);
            index++;
        }
        reader.readAsDataURL(f);
    });
}

function previewDelete(index){
    GroupRoomInfoImgs[index] = null;
    const img_id = "#img_id_" + index;
    $(img_id).remove();
    chatRoomImgDeleteIndex.push(index);
}

function checkboxOnLoad(e){
    const checkboxArary = $(".checkbox");

    for(let i = 0; i < checkboxArary.length; i ++){
        if(checkboxArary[i].children[0].id !== e.childNodes[0].id){
            document.getElementById(checkboxArary[i].children[0].id).checked = false;
            document.getElementById(checkboxArary[i].children[0].id).value = '0';
        }
        else{
            document.getElementById(e.childNodes[0].id).checked = true;
            document.getElementById(checkboxArary[i].children[0].id).value = '1';
        }
    }
}