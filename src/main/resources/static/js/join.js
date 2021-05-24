    $(document).ready(function(){

    $('#join-btn').on("click", function(){

        let data = {
            username: $('#username').val(),
            password: $('#password-chk').val(),
            nickname: $('#nickname').val(),
            phoneNum: $('#phone').val(),
            mannerScore: 0,
            mileage: 0
        }

        $.ajax({

            url: '/api/join',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(result){
                location.href = '/';
            }
        }).done(function(resp){
            console.log(resp);
            
        }).fail(function(error){
            console.log('error');
        });

    })

})