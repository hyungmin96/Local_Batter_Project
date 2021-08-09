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

    <div class="exchangeViewContainer">

    </div>

</body>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/tempusdominus-bootstrap-4/5.0.1/js/tempusdominus-bootstrap-4.min.js"></script>
<script type="text/javascript" src="/js/mapAPI.js"></script>
<script>
    $(document).ready(function(){
        const data = {
            groupId: 1,
        }

        $.ajax({
            url: '/api/exchange/get_exchang_list',
            type: 'GET',
            data: data,
            success: function (response){
                $.each(response, function(key, value){
                    console.log(value)
                    $('.exchangeViewContainer').append(
                        "<a href=/info/board/exchange/" + value.boardId + "><div style='border: 1px solid black; margin: 5px; 5px;'>" +
                            "<div>" + value.content + "</div>" +
                            "<div>" + value.location + "</div>" +
                        "</div>"
                    )
                })
            }
        })
    })
</script>