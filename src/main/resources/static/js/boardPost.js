$(document).ready(function(){

    $("#btn_upload").on("click", function(){

        const formData = new FormData();
        var inputFile = $('input[name="upload_file"]');
        var files = inputFile[0].files;
        
        if($("#inputTitle").val() || $("#inputPrice").val() || $("#inputDescript").val()){
        
        formData.append("title", $("#inputTitle").val());
        formData.append("price", $("#inputPrice").val());
        formData.append("descryption", $("#inputDescript").val());
        formData.append("writer", "hyungmin96");
        formData.append("location", $("#inputLocation").val());

        for(var i = 0; i < files.length; i++){
            formData.append('uploadFiles', files[i]);
        }

        $.ajax({
            // 일반적으로 Data는 query String 형태로 전달되기때문에 아래 2가지 파라미터는
            // false로 설정해주어야 하낟.
            type: "POST",
            url: '/upload',
            data: formData,
            dataType: "text",
            contentType: false,
            processData: false,
            success: function(result){
                if(result === '업로드 성공'){
                    location.href = "/";
                }
            }
        }).fail(function(error){
            if(JSON.stringify(error).includes("Data too long for column")){
                alert('제목은 15자 이하로만 작성이 가능합니다.');
            }
        });

    }else{
        alert("필수항목을 입력 후 다시 시도해주세요.");
    }

    });

});