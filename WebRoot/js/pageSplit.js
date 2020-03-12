var $=function(id){return document.getElementById(id);};

function pageTest(){
	alert("测试一下下！");
}

var totalCount=0;
var pageSize=0;
var nowPage=0;
var totalPage=0;

var totalpage,pagesize,cpage,count,curcount,outstr,totalCount; 

//初始化 
function loadData(pageSplit){
	cpage=pageSplit.nowPage;
	totalpage=pageSplit.totalPage;
	pagesize=pageSplit.pageSize;
	totalCount=pageSplit.totalCount;
	outstr = "";
	setpage();
}

function gotopage(target) 
{
    cpage=target;        //把页面计数定位到第几页 
    setpage();
    reloadpage(target);    //调用显示页面函数显示第几页,这个功能是用在页面内容用ajax载入的情况 
}

function setpage() 
{ 
    if(totalpage<=10){        //总页数小于十页 
        for (count=1;count<=totalpage;count++) 
        {    if(count!=cpage) 
            { 
                outstr = outstr + "<a href='#' onclick='gotopage("+count+")'>"+count+"</a>"; 
            }else{ 
                outstr = outstr + "<span class='current' >"+count+"</span>"; 
            } 
        } 
    } 
    if(totalpage>10){        //总页数大于十页 
        if(parseInt((cpage-1)/10) == 0) 
        {             
            for (count=1;count<=10;count++) 
            {    if(count!=cpage) 
                { 
                    outstr = outstr + "<a href='#' onclick='gotopage("+count+")'>"+count+"</a>"; 
                }else{ 
                    outstr = outstr + "<span class='current'>"+count+"</span>"; 
                } 
            } 
            outstr = outstr + "<a href='#' onclick='gotopage("+count+")'> >> </a>"; 
        } 
        else if(parseInt((cpage-1)/10) == parseInt(totalpage/10)) 
        {     
            outstr = outstr + "<a href='#' onclick='gotopage("+(parseInt((cpage-1)/10)*10)+")'><<</a>"; 
            for (count=parseInt(totalpage/10)*10+1;count<=totalpage;count++) 
            {    if(count!=cpage) 
                { 
                    outstr = outstr + "<a href='#' onclick='gotopage("+count+")'>"+count+"</a>"; 
                }else{ 
                    outstr = outstr + "<span class='current'>"+count+"</span>"; 
                } 
            } 
        } 
        else 
        {     
            outstr = outstr + "<a href='#' onclick='gotopage("+(parseInt((cpage-1)/10)*10)+")'><<</a>"; 
            for (count=parseInt((cpage-1)/10)*10+1;count<=parseInt((cpage-1)/10)*10+10;count++) 
            {         
                if(count!=cpage) 
                { 
                    outstr = outstr + "<a href='#'  onclick='gotopage("+count+")'>"+count+"</a>"; 
                }else{ 
                    outstr = outstr + "<span class='current'>"+count+"</span>"; 
                } 
            } 
            outstr = outstr + "<a href='#' target='_self' onclick='gotopage("+count+")'> >> </a>"; 
        } 
    }
    $("pager").innerHTML="<div id='setpage'><span id='info'>共"+totalpage+"页|第"+cpage+"页<\/span>" 
    + outstr + "<span id='info'>共"+totalCount+"条记录<\/span>&nbsp;&nbsp;"+
    "<span id='info'>跳到第<input class='BigInput'  id='gopage' name='gopage' size='2' \/>页<\/span><button onclick='goPage($(\"gopage\").value);' >确定<\/button><\/div>";
    outstr = ""; 
} 

function goPage(p){//支持全角数字
	//alert(p.charCodeAt(0));
	p=p.toLow().Trim();//转换成半角并去除空格
	//var b=p.isNumeric();
	//alert(b);
	if(1<=p&&p<=totalpage){
		gotopage(p);
	}else{
		alert('请输入正常的页数！');
		$("gopage").select();
	}
}