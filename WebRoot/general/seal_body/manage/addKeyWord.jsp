<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
	<head>
		<title>添加印章关键字</title>
		<link rel="stylesheet" type="text/css"
			href="/Seal/theme/${current_user.user_theme}/style.css">
		<script src="js/ccorrect_btn.js"></script>
		<script src="/Seal/js/util.js"></script>
		<script src="js/utility.js"></script>
		<script>
       function test(){
        var key_word=document.form1.key_word.value;
        if(key_word==""){
          alert("关键字不能为空!");
          return false;
        }else{
          var a=window.dialogArguments;
		  a.key_word.value=key_word;
		 // window.returnValue=$("key_word").value;
		  this.close();
        }  
      }
    //提交绑定回车事件      
    function onkey(){
      if(window.event.keyCode==13){
       var key_word=document.form1.key_word.value 
        if(key_word==""){
          alert("关键字不能为空!");
          return false;
        }
      }       
   }
		</script>
	</head>
	<body onkeydown="return onkey('doLogin')" class="bodycolor" topmargin="5" >
	<form name="form1">
	       <table border="0" cellspacing="0" cellpadding="3"
			class="small" align="center">
			<tr>
				<td class="Big">
					<img src="../../images/query.gif" align="absmiddle">
					<span class="big3"> 添加印章关键字</span>
				</td>
			</tr>
		  </table>
			<table border="0" cellspacing="0" cellpadding="3"
			class="small" align="center">
			<tr >
			<td >关键字：</td>
			<td><input name="key_word" type="text" id="key_word" size="50">
			</td>
			</tr>		
			<tr>
			<td align="center" colspan="3">
			<input type="button" id="doLogin" value="确定" onclick="return test();" class="BigButton">
			<input type="reset" value="重填" class="BigButton">
			</tr>
		  </table>
		  </form>
	</body>
</html>
