<%@page contentType="text/html;charset=utf-8"%>
<%@include file="tag.jsp"%>
<html>
	<head>
		<title>主菜单</title>
		<script src="js/ccorrect_btn.js"></script>
		<link rel="stylesheet" type="text/css" href="../theme/${current_user.user_theme}/pheader.css">
		<link rel="stylesheet" type="text/css" href="../theme/${current_user.user_theme}/style.css">
		<link rel="stylesheet" type="text/css" href="../theme/${current_user.user_theme}/menu.css">
	</head>
	<body class="panel" onload="init();">
		<div id="sub_tabs" class="sub_tabs">
			<ul id="sub_tabs_ul">
				<li>
					<a id="link_1" href="../personInfoIndex.do" target="main" title="帐户信息"
					class="active"><span>帐户信息</span> </a>
				</li>
			</ul>
			<img id="expand_link" onclick="menu_expand();"
				src="../images/green_plus.gif" title="展开/折叠菜单" />
		</div>

		<div id="body">

			<!-- OA树开始-->
			<ul id="menu">
				<logic:iterate id="a" name="SESSION_AUTH_TOKEN" indexId="index" scope="session">
					<li class="L1">
						<a href="javascript:c('m${a.view_menu.menu_no }');"
							id="m${a.view_menu.menu_no }"><span><img
									src="../images/menu/${a.view_menu.menu_image }.gif"
									align="absMiddle" /> ${a.view_menu.menu_name }</span> </a>
					</li>
					<ul id="m${a.view_menu.menu_no }d" style="display: none;"
						class="U1">
						<c:forEach var="aa" items="${a.list_func }">
							<li class="L22">
								<a href="${aa.func_url }" target="main" id="f3"><span><img
											src="../images/menu/${aa.func_image }.gif" align="absMiddle" />
										${aa.func_name }</span> </a>
							</li>
						</c:forEach>
					</ul>
				</logic:iterate>
			</ul>
		</div>
		<div id="bottom">
			<span id="bottom_left"></span><span id="bottom_center"></span><span
				id="bottom_right"></span>
		</div>
		<div id="menu_code_1" style="display: none;"></div>
		<div id="menu_code_2" style="display: none;"></div>
		<div id="menu_code_3" style="display: none;"></div>
		<div id="menu_code_4" style="display: none;"></div>
		<div id="menu_code_5" style="display: none;"></div>

		<script src="inc/js/utility.js"></script>
		<script language="JavaScript">
window.onresize=function()
{
   if(!parent.document.getElementById('frame1')) return;
   var rows = parent.document.getElementById('frame1').rows.split(",");
   if(rows.length < 2 || rows[1]!="*") return;
   document.getElementById("bottom_center").style.width = "0px";

   if(document.body.clientHeight > document.getElementById("sub_tabs").clientHeight+document.getElementById("bottom").clientHeight)
      document.getElementById("body").style.height=(document.body.clientHeight-document.getElementById("sub_tabs").clientHeight-document.getElementById("bottom").clientHeight)+"px";

   var widthTotal = parseInt(document.getElementById("bottom").clientWidth);
   var widthLeft = parseInt(document.getElementById("bottom_left").clientWidth);
   var widthRight = parseInt(document.getElementById("bottom_right").clientWidth);
   if(!isNaN(widthTotal) && !isNaN(widthLeft) && !isNaN(widthRight))
   {
      document.getElementById("bottom_center").style.width = widthTotal - widthLeft - widthRight + "px";
   }
};
function init()
{
   window.onresize();
}

var sub_menu="1";
function view_menu(id)
{
   set_current("");
   if(document.getElementById("menu").innerHTML.toLowerCase().indexOf("<li") >= 0)
      document.getElementById("menu_code_"+sub_menu).innerText=document.getElementById("menu").innerHTML;

   if(document.getElementById("menu_code_"+id).innerText=="" || isUndefined(document.getElementById("menu_code_"+id).innerText))
   {
      document.getElementById("menu").innerHTML="<img src='../images/loading.gif' align='absMiddle'> 加载中，请稍候……";
      if(id==2) args="MENU_TYPE=SHORTCUT";
      else if(id==3) args="MENU_TYPE=OA";
      else if(id==4) args="MENU_TYPE=EA";
      else if(id==5) args="MENU_TYPE=FIS";
      else args="";
      _get("menu_code.php", args, update_menu, true);
   }
   else
   {
      document.getElementById("menu").innerHTML=document.getElementById("menu_code_"+id).innerText;
   }
   document.getElementById('link_'+sub_menu).className="";
   document.getElementById('link_'+id).className="active";
   sub_menu=id;
   setCookie("MENU_UI_1", id);
}
function update_menu(req)
{
   if(req.status == 200)
   {
      if(req.responseText=="")
         document.getElementById("menu").innerHTML="<div style='padding:10px;'>无可访问菜单</div>";
      else
         document.getElementById("menu").innerHTML=req.responseText;
   }
   else
   {
      // document.getElementById("menu").innerHTML="<div style='padding:10px;'>错误："+req.status+"</div>";
   }
}

var cur_id="",cur_expand="";
var flag=0,sflag=0;

//-------- 菜单点击事件 -------
function c(id)
{
  var targetid,targetelement;
  var strbuf;
  var el=document.getElementById(id);
  if(!el)
     return;
  //-------- 如果点击了展开或收缩按钮---------
  targetid=el.id+"d";
  targetelement=document.getElementById(targetid);
  var expandUL=document.getElementById(cur_expand+"d");
  var expandLink=document.getElementById(cur_expand);

  if (targetelement.style.display=="none")
  {
     el.className="active";
     targetelement.style.display='';

     menu_flag=0;
     document.getElementById("expand_link").src="../images/green_minus.gif";
  }
  else
  {
     el.className="";
     targetelement.style.display="none";

     menu_flag=1;
     document.getElementById("expand_link").src="../images/green_plus.gif";
     var links=document.getElementsByTagName("A");
     for (i=0; i<links.length; i++)
     {
       el=links[i];
       if(el.parentNode.className.toUpperCase()=="L1" && el.className=="active" && el.id.substr(0,1)=="m")
       {
          menu_flag=0;
          document.getElementById("expand_link").src="../images/green_minus.gif";
          break;
       }
     }
  }
}
//-------- 打开网址 -------
function a(URL,id,open_window)
{
   set_current(id);
   if(URL.substr(0,7)!="http://" && URL.substr(0,6)!="ftp://")
      URL = "/general/"+URL;
   URL="www.baidu.com";
   openURL(URL, open_window);
}
function b(URL,id,open_window)
{
   set_current(id);
   URL = "/app/"+URL;
   openURL(URL, open_window);
}
//-------- 菜单全部展开/收缩 -------
var menu_flag=1;
function menu_expand()
{
  if(sub_menu == "2") return;
  if(menu_flag==1)
     document.getElementById("expand_link").src="../images/green_minus.gif";
  else
     document.getElementById("expand_link").src="../images/green_plus.gif";

  menu_flag=1-menu_flag;

  var links=document.getElementsByTagName("A");
  for (i=0; i<links.length; i++)
  {
    var el=links[i];
    if(el.parentNode.className.toUpperCase()=="L1" || el.parentNode.className.toUpperCase()=="L21")
    {
      var elUL=document.getElementById(el.id+"d");
      if(menu_flag==0)
      {
        elUL.style.display='';
        el.className="active";
      }
      else
      {
        elUL.style.display="none";
        el.className="";
      }
    }
  }
}

//-------- 打开windows程序 -------
function winexe(NAME,PROG)
{
   URL="/general/winexe?PROG="+PROG+"&NAME="+NAME;
   window.open(URL,"winexe","height=100,width=350,status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top=0,left=0,resizable=no");
}

function set_current(id)
{
   cur_link=document.getElementById("f"+cur_id)
   if(cur_link)
      cur_link.className="";
   cur_link=document.getElementById("f"+id);
   if(cur_link)
      cur_link.className="active";
   cur_id=id;
}

function openURL(URL, open_window)
{
  if(open_window==1)
  {
    mytop=(screen.availHeight-500)/2-30;
    myleft=(screen.availWidth-780)/2;
    window.open(URL,"","height=500,width=780,status=0,toolbar=no,menubar=yes,location=no,scrollbars=yes,top="+mytop+",left="+myleft+",resizable=yes");
    window.close();
  }
  else
  {
    parent.openURL(URL,0);
  }
}
</script>

	</body>
</html>

