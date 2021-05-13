<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<!DOCTYPE html>

<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="_csrf" th:content="${_csrf.token}">
        <meta name="_csrf_header" th:content="${_csrf.headerName}"> 
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="stylesheet" href="/css/imgWrap.css" >
        <script src="//code.jquery.com/jquery-1.11.0.min.js"></script>
        <script src="/js/storeDataSendAjax.js"></script>
        <script src="/js/imagePreview.js"></script>
        <script src="/js/imgSlider.js"></script>

        <title>Document</title>

    </head>

    <body>

        <h1>Ajax FormData로 파일 업로드하기</h1>
        <div class="input_wrap">
            <input type="file" id="input_imgs" name="upload_file" multiple />
            <input type="button" id="btn_upload" value="submit" />
        </div>

    <div>
        <div class="imgs_wrap">
            <img id="img" />
        </div>
    </div>

    <div class="slideshow-container">
        <div class="mySlides fade2">
            <img class="main_slideImg" src="/images/upload_86px.png">
            <div class="text">Caption Text</div>
        </div>
        <div class="mySlides fade2">
            <img class="main_slideImg" src="/images/upload_86px.png">
            <div class="text">Caption Two</div>
        </div>
        <div class="mySlides fade2">
            <img class="main_slideImg" src="/images/upload_86px.png">
            <div class="text">Caption Three</div>
        </div>
        <a class="prev" onclick="plusSlides(-1)">❮</a>
        <a class="next"onclick="plusSlides(1)">❯</a>
    </div>
    <br>

    <div style="text-align: center">
        <span class="dot" onclick="currentSlide(1)"></span> 
        <span class="dot" onclick="currentSlide(2)"></span>
        <span class="dot" onclick="currentSlide(3)"></span>
    </div>

    </body>

</html>