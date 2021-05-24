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
            success: function(result){
                location.href = '/';
            }
        }).done(function(resp){
            console.log(JSON.stringify(resp));
        }).fail(function(error){
            console.log(error);
        });

    })

})