$(document).ready(function(){

    $('#login-btn').on("click", function(){

        let data = {
            email: 'bana_na21@naver.com',
            account: $('#input-id').val(),
            password: $('#input-pw').val(),
            nickname: "hyungmin96",
            mannerScore: 1,
            mileage: 1
        }

        $.ajax({

            url: '/api/login',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function(result){
                alert(result);
            }
        }).done(function(resp){
            console.log(resp);
        }).fail(function(error){
            console.log(error);
        });

    })

})