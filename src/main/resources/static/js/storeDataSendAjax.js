$(document).ready(function(){

    $("#btn_upload").on("click", function(){

        var formData = new FormData();
        var inputFile = $('input[name="upload_file"]');
        var files = inputFile[0].files;

        formData.append("key1", "value1");
        formData.append("key2", "value2");

        for(var i = 0; i < files.length; i++){
            formData.append('uploadFiles', files[i]);
        }

        $.ajax({
            // 일반적으로 Data는 query String 형태로 전달되기때문에 아래 2가지 파라미터는
            // false로 설정해주어야 하낟.
            contentType: false,
            processData: false,
            data: formData,
            url: '/upload',
            type: 'POST',
            success: function(result){
                if(result.result === 'success'){
                    alert('upload success');
                }else{
                    alert('upload failed');
                }
            }
        }).done(function(resp){
            alert(JSON.stringify(resp));
        }).fail(function(error){
            alert(JSON.stringify(error));
        });

    });

});