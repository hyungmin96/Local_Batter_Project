
function modal(id, userId, sellerId, boardId, writer) {
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
        backgroundColor: 'rgba(0,0,0,0.4)'
    });
    document.body.append(bg);

    modal.querySelector('.modal_close_btn').addEventListener('click', function() {
        bg.remove();
        modal.style.display = 'none';
    });

    modal.setStyle({
        position: 'fixed',
        display: 'block',
        boxShadow: '0 4px 8px 0 rgba(0, 0, 0, 0.2), 0 6px 20px 0 rgba(0, 0, 0, 0.19)',
        zIndex: zIndex + 1,
        top: '50%',
        left: '50%',
        transform: 'translate(-50%, -50%)',
        msTransform: 'translate(-50%, -50%)',
        webkitTransform: 'translate(-50%, -50%)'
    });

    $('.text__container').empty();
    $('.text__container').append(
        "<button onclick='commentWrite(" + userId + ", " + sellerId + ", " + boardId + ", " + writer + ");' style='margin-top: 10px; float: right;' id='comment_btn' class='btn btn-primary'>작성</button>"
    );

}

Element.prototype.setStyle = function(styles) {
    for (var k in styles) this.style[k] = styles[k];
    return this;
};

function showModal(images){

}

function showModal(userId = null, sellerId = null, boardId = null, writer = null){
    modal('my_modal', userId, sellerId, boardId, writer);
}