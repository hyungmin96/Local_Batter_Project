<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html lang="en">

    <meta name="_csrf" th:content="${_csrf.token}">
    <meta name="_csrf_header" th:content="${_csrf.headerName}"> 

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
    </head>

    <body>

        <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
        <script src = "/js/storeDataSendAjax.js"></script>

        <h1>Ajax FormData로 파일 업로드하기</h1>
        <input type="file" name="upload_file" multiple />
        <input type="button" id="btn_upload" value="submit" />

    </body>

</html>