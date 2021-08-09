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
    <input type="hidden" class="boardId" value="${boardId}">
    <div class="exchangeRequestContainer">

    </div>

</body>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script>

    $(document).ready(function(){

        const data = {
            page: 0,
            display: 10,
            groupId: 1,
            boardId: $('.boardId').val()
        }

        $.ajax({
            url: '/api/exchange/get_request_list',
            type: 'GET',
            data: data,
            success: function(response){
                $.each(response.content, function(key, value){
                    console.log(value)
                    let exchangeCancelButton = (value.status === 'process') ?  "<button class='exchangeCancelButton'>취소</button>" : ''
                    $('.exchangeRequestContainer').append(
                        "<div id=clientRequest_" + value.clientId + " class='clientRequestBox' style='margin: 3px 0 3px 0; border: 1px solid black;'>" +
                            value.content + "<br />" +
                            value.price + "<br />" +
                            value.request +
                        "<button class='exchangeButton'>교환</button>" +
                        "<button class='exchangeDelteButton'>삭제</button>" +
                        exchangeCancelButton +
                        "</div>"
                    )
                })
            }
        })
    })

    $(document).on('click', '.exchangeDelteButton', '.exchangeCancelButton', function () {
        const clientId = $(this)[0].parentElement.id.split('_')[1]
        const data = {
            groupId: 1,
            clientId: clientId
        }
        $.ajax({
            url: '/api/exchange/cancel/request',
            type: 'POST',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            success: function(response){
                console.log(response)
            }
        })
    })

    // 교환 요청
    $(document).on('click', '.exchangeButton', function (){
        const index = $('.exchangeButton').index(this)
        const clientId = $('.clientRequestBox')[index].getAttribute('id').split('_')[1]
        const data = {
            groupId: 1,
            boardId: $('.boardId').val(),
            clientId: clientId
        }
        $.ajax({
            url: '/api/exchange/select/request',
            type: 'POST',
            data: data,
            contentType: 'application/x-www-form-urlencoded',
            success: function(response){
                console.log(response)
            }
        })
    })

</script>





