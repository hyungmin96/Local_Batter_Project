
    function product_list_paging(e){

        let url = window.location.href;

        let keyword, display, order
        keyword = url.split('search=')[1].split('&')[0];

        if(!url.includes('display=') && !url.includes('order=')){
            display = '30';
            order = 'accuracy';
        }else{
            display = url.split('display=')[1].split('&')[0];
            order = url.split('order=')[1].split('&')[0];
        }

        let test = document.getElementById(e.id).innerText;
        location.href = '/board/search=' + keyword + '&display=' + display + '&order=' + order + '&page=' + test;
        console.log(test);

    }
