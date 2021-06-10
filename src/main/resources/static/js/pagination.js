function product_list_paging(e){

    let url = window.location.href;

    if(!url.includes('display='))
        display = '30';
    else
        display = url.split('display=')[1].split('&')[0];

    let test = document.getElementById(e.id).innerText;

    callBoardsDataToJson(page = (test - 1));
    
    Array.from(document.getElementsByClassName('page__number__box')[0].children, item=>{
        item.style.backgroundColor = 'white';
    });

    e.style.backgroundColor = 'rgb(236, 236, 236)';

    window.scrollTo(0,0);
}

function nextBtn(){
    
    var index = 0;

    var curPage = document.getElementsByClassName('curpage')[0];
    var lastPage = document.getElementsByClassName('lastpage')[0];

    curPage.dataset.value = (curPage.dataset.value * 1) + 1;
    document.getElementsByClassName('previous')[0].style.display = '';

    if(curPage.dataset.value >= lastPage.dataset.value){
        document.getElementsByClassName('next')[0].style.display = 'none';
    }else
        document.getElementsByClassName('next')[0].style.display = '';

    Array.from(document.getElementsByClassName('page__number__box')[0].children, item => {
        if(item.style.display != 'none' && index < 10){
            item.style.display = 'none';
            index++;
        }
    });

    console.log(document.getElementsByClassName('page__number__box')[0]);

}

function previousBtn(){
    
    document.getElementsByClassName('next')[0].style.display = '';
    
    var curPageNum = document.getElementsByClassName('curpage')[0];
    var totalPageArray = getPages();

    curPageNum.dataset.value = (curPageNum.dataset.value * 1) - 1;
    if(curPageNum.dataset.value <= 1)
        document.getElementsByClassName('previous')[0].style.display = 'none';

    Array.from(totalPageArray[curPageNum.dataset.value - 1], item =>{
        document.getElementById(item.id).style.display = '';
    });

}

function getPages(){

    var pageClasses = document.getElementsByClassName('page__number__box')[0];

    var totalPageArray = [];
    var pageArray = [];

    for(let i = 0; i < pageClasses.children.length; i ++){
        if(pageArray.length > 9){
            totalPageArray.push(pageArray);
            pageArray = [];
            index = 0;
        }
        pageArray.push(pageClasses.children[i]);
    }

    if(pageArray.length > 0) totalPageArray.push(pageArray);

    return totalPageArray

}