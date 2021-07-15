$(document).ready($(function(){

    $('#slider-div').slick({
        slide: '.container-item',		//슬라이드 되어야 할 태그 ex) div, li 
        infinite : true, 	//무한 반복 옵션	 
        slidesToShow : 4,		// 한 화면에 보여질 컨텐츠 개수
        slidesToScroll : 4,		//스크롤 한번에 움직일 컨텐츠 개수
        speed : 600,	 // 다음 버튼 누르고 다음 화면 뜨는데까지 걸리는 시간(ms)
        arrows : false, 		// 옆으로 이동하는 화살표 표시 여부
        dots : false, 		// 스크롤바 아래 점으로 페이지네이션 여부
        autoplay : true,			// 자동 스크롤 사용 여부
        autoplaySpeed : 3000, 		// 자동 스크롤 시 다음으로 넘어가는데 걸리는 시간 (ms)
        pauseOnHover : true,		// 슬라이드 이동	시 마우스 호버하면 슬라이더 멈추게 설정
        vertical : false,		// 세로 방향 슬라이드 옵션
        dotsClass : "slick-dots", 	//아래 나오는 페이지네이션(점) css class 지정
        draggable : true, 	//드래그 가능 여부 
        
        responsive: [
            {  
                breakpoint: 960,
                settings: {
                    slidesToShow:3 
                } 
            },
            { 
                breakpoint: 768,
                settings: {	
                    slidesToShow:2 
                } 
            }
        ]

    });

  }));