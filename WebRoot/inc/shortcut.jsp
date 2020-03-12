<%@page contentType="text/html;charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>快捷按钮</title>
<link rel="stylesheet" type="text/css" href="../theme/${current_user.user_theme}/style.css">
<link rel="stylesheet" type="text/css" href="../theme/${current_user.user_theme}/shortcut.css">
<script type='text/javascript' src='/Seal/dwr/interface/SysUser.js'></script>
<script type='text/javascript' src='/Seal/dwr/engine.js'></script>
<script type='text/javascript' src='/Seal/dwr/util.js'></script>
<script src="/Seal/js/logOper.js"></script>
<script src="/Seal/js/util.js"></script>
<script>
var user_no="${current_user.user_id}";
		var user_ip="${user_ip}";
		if(user_no==""){
			top.location="/Seal/login.jsp";
		}
function openURL(URL,open_window)
{
  	 if(open_window)
  	    window.open(URL);
  	 else
  	    parent.main.location=URL;
}

function winexe(NAME,PROG)
{
   URL="/general/winexe?PROG="+PROG+"&NAME="+NAME;
   window.open(URL,"winexe","height=100,width=350,status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top=0,left=0,resizable=no");
}
//注销
function re_login()
{
	msg="您好，${current_user.user_name}，确认要注销么？";
  if(window.confirm(msg))
  { 
     dwr.engine.setAsync(false); //设置方法调用是否同步，false表示同步
     // logAdd2("注销系统成功","${current_user.user_id}","${current_user.user_name}","注销系统成功","系统管理");//logOper.js
     parent.parent.relogin=1;
     parent.parent.location="/Seal/logout.do";
   }
}

var view_flag1=1;
var frame_rows = parent.parent.parent.document.getElementById("frame1").rows;
function my_menu_view1(id)
{
	var el=document.getElementById(id);
	if(view_flag1==1)
	{
     parent.parent.parent.document.getElementById("frame1").rows="0"+frame_rows.substr(frame_rows.indexOf(","));
     el.className="call_down";
     }
	else
	{
      parent.parent.parent.document.getElementById("frame1").rows=frame_rows;
      el.className="call_up";
    }
   
  view_flag1=1-view_flag1;
}

var view_flag2=1;
var frame_cols = parent.parent.parent.document.getElementById("frame2").cols;
function my_menu_view2(id)
{
	var el=document.getElementById(id);
	if(view_flag2==1)
	{
      parent.parent.document.getElementById("frame2").cols="0"+frame_cols.substr(frame_cols.indexOf(","));
      el.className="call_right";
   }
   else
	{
      parent.parent.document.getElementById("frame2").cols=frame_cols;
      el.className="call_left";
   }

  view_flag2=1-view_flag2;
}
function startmarquee()
{
   var t,p=true,movepixel=1;
   var tb=document.getElementById("NAV");
   var o=document.getElementById("Nav_div");
   var m=document.getElementById("menu_tb");
   var r=document.getElementById("NavRight");
   var lineHeight=o.scrollHeight/m.rows.length;
   if(m.rows.length>1)
   {
      tb.onmouseover=function(){r.style.display="";}
      tb.onmouseout =function(){r.style.display="none";}
      r.onmouseover=function(){r.src="/images/nav_r2.gif";}
      r.onmouseout =function(){r.src="/images/nav_r1.gif";}
      p=false;
   }
   r.onclick=function(){if(p) return; movepixel=1; t=setInterval(scroll_up,10); p=true;}
   document.body.onmousewheel=function(){if(p) return; if(event.wheelDelta>0) movepixel=-1; else movepixel=1; t=setInterval(scroll_up,10); p=true;}

   function scroll_up()
   {
      o.scrollTop+=movepixel;
      if(movepixel>0)
      {
         if(o.scrollTop % (lineHeight) == lineHeight-1)
         {
            clearInterval(t);
            p=false;
         }
         if(o.scrollTop>=lineHeight*(m.rows.length-1))
         {
            clearInterval(t);
            o.scrollTop=0;
            p=false;
         }
      }
      else
      {
         if(o.scrollTop % (lineHeight) == 1)
         {
            clearInterval(t);
            p=false;
         }
         if(o.scrollTop-1<0)
         {
            clearInterval(t);
            o.scrollTop=lineHeight*(m.rows.length-1);
            p=false;
         }
      }
   }
}

//add by YZQ 2008-03-05 begin
function bindFunc() {
  var args = [];
  for (var i = 0, cnt = arguments.length; i < cnt; i++) {
    args[i] = arguments[i];
  }
  var __method = args.shift();
  var object = args.shift();
  return (
    function(){
      var argsInner = [];
		  for (var i = 0, cnt = arguments.length; i < cnt; i++) {
		    argsInner[i] = arguments[i];
		  }
      return __method.apply(object, args.concat(argsInner));
    });
}
var timerId = null;
var firstTime = true;
//add by YZQ 2008-03-05 end
function d(URL,id)
{
   //add by YZQ 2008-03-05 begin
   var winMgr = parent.main.winManager;
	 if (!winMgr) {
	   if (firstTime) {
	     openURL("/fis/common/frame.jsp");
	     firstTime = false;
	   }
	   timerId = setTimeout(bindFunc(d, window, URL, id), 100);
	   return;
	 }
	 firstTime = true;
	 if (timerId) {
	   clearTimeout(timerId);
	 }
	 if (winMgr) {
	   winMgr.openActionPort("/fis/"+URL, document.getElementById("f" + id).innerText);
	   return;
	 }
   //add by YZQ 2008-03-05 end

   URL = "/fis/"+URL;
   openURL(URL);
}
</script>
</head>

<body onload="startmarquee();">

<table id="NAV" border="0" cellspacing="0" cellpadding="0" class="small" width="100%">
<tr height="30">
<td nowrap width="18" valign="top">
  <a href="javascript:my_menu_view2('arrow2')" title="显示/隐藏左侧面板" id="arrow2" class="call_left"></a>
</td>
<td nowrap title="此栏显示您定义的菜单快捷组，鼠标滚轮上下翻页">
   <div id="Nav_div">
     <table id="menu_tb" class="small" border="0" cellspacing="0" cellpadding="0">
        <tr height="30">
          <td><ul>
         </ul></td>
        </tr>
      </table>
    </div>
</td>
<td nowrap align="right">
  <img id="NavRight" src="../images/nav_r1.gif" style="display:none;cursor:pointer;" align="absMiddle" title="显示下一行菜单">&nbsp;
   <a href="../personInfoIndex.do" target="main"><img src="../images/menu/theme.gif" align="absmiddle" width="16" height="16" border="0" alt="帐户信息"> 帐户信息</a>&nbsp;
   <a href="javascript:re_login();"><img src="../images/login.gif" border="0" align="absmiddle" alt="注销${current_user.user_id}"> 注销 </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <a href="javascript:my_menu_view1('arrow1')" id="arrow1" class="call_up" alt="显示/隐藏顶部标题栏"></a>
</td>

</tr>
</table>

</body>
</html>