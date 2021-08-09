<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <title>로컬바터 - 지역 물품교환 및 거래</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>

<body>

    <div class="boardListContainer">

    </div>

    <div>
        <button onclick="window.location.href='/post/board'">교환 글 작성</button>
    </div>

</body>

<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>

    $(document).on('click', '.requestExchangeButton', function (){
        let boardId = $(this)[0].closest('.boardBoxId').getAttribute('id')
        window.location.href = '/request/exchange/' + boardId
    })

    $(document).ready(function (){

        const data = {
            page: 0,
            display: 10,
            groupId: 1
        }

        $.ajax({
            url: '/api/exchange/get_board_list',
            type: 'GET',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            success: function (response) {
                console.log(response)
                $.each(response.content, function (key, value){
                    let requestExchangeButton = (value.boardCategory !== 'article') ? "<button class='requestExchangeButton'>교환요청</button>" : ''
                    $('.boardListContainer').append(
                        "<div id=" + value.boardId + " class='boardBoxId' style='margin: 3px 0 3px 0; border: 1px solid black;'>" +
                                "<a href='/get_request_list/" + value.boardId + "'>" +
                                "<div class='boardBox'>" +
                                "<input type='hidden' class='boardId' value=" + value.boardId + ">" +
                                value.content + "<br />" +
                                value.username + "<br />" +
                                value.profilePath + "<br />" +
                                "</div>" +
                                "</a>" +
                            requestExchangeButton +
                        "</div>"
                    )
                })
            }
        })
    })
</script>