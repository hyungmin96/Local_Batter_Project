
function modal(id, images) {
    var zIndex = 9999;
    var modal = document.getElementById(id);

    var bg = document.createElement('div');
    bg.id = 'modal__layer';
    bg.setStyle({
        position: 'fixed',
        zIndex: zIndex,
        left: '0px',
        top: '0px',
        width: '100%',
        height: '100%',
        overflow: 'auto',
        // 레이어 색갈은 여기서 바꾸면 됨
        backgroundColor: 'rgba(0, 0, 0, 0.85)'
    });
    document.body.append(bg);

    // 닫기 버튼 처리, 시꺼먼 레이어와 모달 div 지우기
    modal.querySelector('.modal_close_btn').addEventListener('click', function() {
        document.body.className = ''
        bg.remove();
        modal.style.display = 'none';
    });

    modal.setStyle({
        position: 'fixed',
        display: 'block',
        zIndex: zIndex + 1,
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        msTransform: 'translate(-50%, -50%)',
        webkitTransform: 'translate(-50%, -50%)'
    });

    var html = '';
    for(let i = 0; i < images.length; i ++){
        var activeBoolean = '';
        if(i == 0)
            activeBoolean = "<div class='carousel-item active'>";
        else
            activeBoolean = "<div class='carousel-item'>";

            html += activeBoolean +
                "<img src=" + images[i] + " class='d-block w-100'>" +
                "</div>"
    }

    $('.carousel-inner').append(html);

}

// Element 에 style 한번에 오브젝트로 설정하는 함수 추가
Element.prototype.setStyle = function(styles) {
    for (var k in styles) this.style[k] = styles[k];
    return this;
};

function showImgModal(e){
    $('body').addClass('stop-scrolling')
    var imgArray = document.getElementById(e.id).children;
    var filenameArray = [];
    for(let i = 0; i < imgArray.length; i++){
        filenameArray.push(imgArray[i].children[0].firstChild.attributes[0].nodeValue);
    }

    modal('imgModal', filenameArray);
}
