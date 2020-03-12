<%@ page language="java" contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script language="javascript" type="text/javascript">
//分页的方法
function jumpPage(flag){
	var pageIndex;
	if("select" == flag){
		var selectObj = document.getElementById("pageNumber");
	   	for(var i = 0; i < selectObj.length; i++) {
	   		if(selectObj.options[i].selected){
	   			pageIndex = selectObj.options[i].value;
	   			break;
	   		}   			
	  	}
	}else {
		pageIndex = flag;
	}

   	var allForm = document.all.tags("form");
   	if(allForm.length<=0){
    	allForm =  document.all.tags("html:form");
	}else if(allForm.length>0){
	    var form=allForm[1];
	    form.target="_self";      
	    form.action=form.action+"?pageIndex="+pageIndex;
	    form.submit();
 	}
}

  function jumpPageIndex(page){
  	var pageIndex = document.getElementById("pageIndex").value;
  	var maxPage=page;
  	if(pageIndex.length==0){
  		alert("请输入要查询的页数！");
  		  return false;
  	}else if(parseInt(pageIndex)<0 || isNaN(pageIndex)){
  	    alert("请输入的合法页数！");
  	    return false;
  	}else if(parseInt(pageIndex)>parseInt(maxPage))
  	{
  	    alert("最多只能输入 "+maxPage+" 页");
  	}
  	else{
  	 document.getElementById("form1").submit();
  	}
  
  }
</script>

<div align="right">
					共
					<font color="red">${pageSplit.totalCount}</font>
					条记录 每页<font color="red">${pageSplit.pageSize}</font>条 第[
					<font color="red">${pageSplit.nowPage}</font>/
					<font color="red">${pageSplit.totalPage}</font>]
					页
					<c:if test="${pageSplit.nowPage!=1}">
						<a href="" onclick="jumpPage(1); return false;">  首页</a>
					</c:if>
					<c:if test="${pageSplit.nowPage>1}">
						<a href="" onclick="jumpPage('${pageSplit.nowPage-1}'); return false;">  上一页 </a>
					</c:if>
					<c:if test="${pageSplit.totalPage > pageSplit.nowPage}">
						<a href="" onclick="jumpPage('${pageSplit.nowPage+1}'); return false;">  下一页</a>
					</c:if>
					<c:if test="${pageSplit.nowPage != pageSplit.totalPage}">
						<a href="" onclick="jumpPage('${pageSplit.totalPage}'); return false;">  尾页</a>
					</c:if>
                   
                           转到
						<input type="text" name="pageIndex" size=3>
						页
					    <input type="button" onclick="jumpPageIndex('${pageSplit.totalPage}');return false;" value="提交">
				
			</div>