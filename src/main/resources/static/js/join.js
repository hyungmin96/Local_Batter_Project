$(document).ready(function () {
    $('#join-btn').on('click', join);
    $('.id-certification').on('click', checkUseranme);
    $('#password').on('keyup', checkPwd);
    $('#password-chk').on('keyup', checkPwd);
});

let checkUsernameResult = false;
let checkUserPwd = false;

function join() {
    let data = {
        username: $('#username').val(),
        password: $('#password-chk').val(),
        nickname: $('#nickname').val(),
    };
    
    if (!checkUsernameResult) {
        alert('아이디 중복확인 후 다시 시도해주세요.');
    } else if (!checkUserPwd) {
        alert('비밀번호가 일치하지 않습니다.');
    } else {
        $.ajax({
            url: '/api/join',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                alert('회원가입이 완료되었습니다. 재로그인후 사용 가능합니다.');
                location.href = '/';
            },
        })
            .done(function (resp) {
                console.log(resp);
            })
            .fail(function (error) {
                console.log('error');
            });
    }
}

function checkUseranme() {
    let data = 'username=' + $('#username').val();

    $.ajax({
        url: '/api/checkUserName',
        type: 'POST',
        contentType: 'application/x-www-form-urlencoded',
        data: data,
        success: function (result) {
            if (result == true) {
                checkUsernameResult = true;
                alert('사용 가능한 아이디 입니다.');
            } else {
                alert('이미 가입된 아이디 입니다. 다른 이메일로 시도해주세요');
            }
        },
    });
}

function checkPwd() {
    var info = document.createElement('div');
    info.className = 'pw__check__false';

    if ($('#password').val() != $('#password-chk').val()) {
        $('.pw__check__container').html(
            '<div class=pwd__check__false>' +
                '<span>설정한 비밀번호와 일치하지 않습니다.</span></div></div>'
        );
        checkUserPwd = false;
    } else {
        $('.pw__check__container').html(
            '<div class=pwd__check__true>' +
                '<span>설정한 비밀번호와 일치합니다.</span></div></div>'
        );
        checkUserPwd = true;
    }
}
