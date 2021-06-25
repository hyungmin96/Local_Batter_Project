var pageName = '';

function product_list_paging(e){

    let url = window.location.href;

    if(!url.includes('display='))
        display = '30';
    else
        display = url.split('display=')[1].split('&')[0];

    let test = document.getElementById(e.id).innerText;

    callBoardsDataToJson(page = (test - 1));
    console.log(e.className)
    Array.from(document.getElementsByClassName('page__number__box')[0].children, item=>{
        item.style.backgroundColor = 'white';
    });

    e.style.backgroundColor = 'rgb(236, 236, 236)';

    window.scrollTo(0,0);
}

function nextBtn(pageContainerName){
    
    var index = 0;

    var curPage = document.getElementsByClassName('curpage')[0];
    var lastPage = document.getElementsByClassName('lastpage')[0];

    curPage.dataset.value = (curPage.dataset.value * 1) + 1;
    document.getElementsByClassName('previous')[0].style.display = '';

    Array.from(document.getElementsByClassName(pageContainerName)[0].children, item => {
        if(item.style.display != 'none' && index < 10){
            item.style.display = 'none';
            index++;
        }
    });
}

function previousBtn(pageContainerName){
    
    document.getElementsByClassName('next')[0].style.display = '';
    
    var curPageNum = document.getElementsByClassName('curpage')[0];
    var totalPageArray = getPages(pageContainerName);

    curPageNum.dataset.value = ((curPageNum.dataset.value * 1) - 1) <= 0 ? 1 : ((curPageNum.dataset.value * 1) - 1);

    Array.from(totalPageArray[curPageNum.dataset.value - 1], item =>{
        document.getElementById(item.id).style.display = '';
    });

}

function getPages(pageContainerName){

    var pageClasses = document.getElementsByClassName(pageContainerName)[0];

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