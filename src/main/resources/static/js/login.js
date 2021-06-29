$(document).ready(function(){

    $('#login-btn').on("click", function(){

        let data = {
            username: $('#username').val(),
            password: $('#pw').val()
        }

        $.ajax({
            url: '/api/login',
            type: 'POST',
            contentType: 'application/x-www-form-urlencoded',
            data: data,
            success: function(){
                location.href = '/';
            }
        }).fail(function(error){
            alert('로그인 실패 확인 후 다시 시도해주세요.');
        });

    })

})