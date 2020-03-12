var userAgent = navigator.userAgent.toLowerCase();
var is_opera = userAgent.indexOf('opera') != -1 && opera.version();
var is_moz = (navigator.product == 'Gecko') && userAgent.substr(userAgent.indexOf('firefox') + 8, 3);
var is_ie = (userAgent.indexOf('msie') != -1 && !is_opera) && userAgent.substr(userAgent.indexOf('msie') + 5, 3);

function LoadDialogWindow(URL, parent, loc_x, loc_y, width, height)
{
  if(is_ie)//window.open(URL);
     window.showModalDialog(URL,parent,"edge:raised;scroll:1;status:0;help:0;resizable:1;dialogWidth:"+width+"px;dialogHeight:"+height+"px;dialogTop:"+loc_y+"px;dialogLeft:"+loc_x+"px",true);
  else
     window.open(URL,parent,"height="+height+",width="+width+",status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,top="+loc_y+",left="+loc_x+",resizable=yes,modal=yes,dependent=yes,dialog=yes,minimizable=no",true);
}
function SelectUserSingle(MODULE_ID,TO_ID, TO_NAME, MANAGE_FLAG, FORM_NAME)
{
  URL="/module/user_select_single?MODULE_ID="+MODULE_ID+"&TO_ID="+TO_ID+"&TO_NAME="+TO_NAME+"&MANAGE_FLAG="+MANAGE_FLAG+"&FORM_NAME="+FORM_NAME;
  loc_y=loc_x=200;
  if(is_ie)
  {
     loc_x=document.body.scrollLeft+event.clientX-100;
     loc_y=document.body.scrollTop+event.clientY+170;
  }
  LoadDialogWindow(URL,self,loc_x, loc_y, 400, 350);
}
function SelectDept(MODULE_ID,TO_ID, TO_NAME, PRIV_OP)
{
  URL="/module/dept_select?MODULE_ID="+MODULE_ID+"&TO_ID="+TO_ID+"&TO_NAME="+TO_NAME+"&PRIV_OP="+PRIV_OP;
  loc_y=loc_x=200;
  if(is_ie)
  {
     loc_x=document.body.scrollLeft+event.clientX-100;
     loc_y=document.body.scrollTop+event.clientY+170;
  }
  LoadDialogWindow(URL,self,loc_x, loc_y, 400, 350);
}
function SelectDeptSingle(MODULE_ID,TO_ID, TO_NAME, PRIV_OP)
{
  URL="/module/dept_select_single?MODULE_ID="+MODULE_ID+"&TO_ID="+TO_ID+"&TO_NAME="+TO_NAME+"&PRIV_OP="+PRIV_OP;
  loc_y=loc_x=200;
  if(is_ie)
  {
     loc_x=document.body.scrollLeft+event.clientX-100;
     loc_y=document.body.scrollTop+event.clientY+170;
  }
  LoadDialogWindow(URL,self,loc_x, loc_y, 200, 350);
}

function SelectPriv(MODULE_ID,TO_ID, TO_NAME, PRIV_OP,DEPT_ID)
{
  URL="/module/priv_select?MODULE_ID="+MODULE_ID+"&TO_ID="+TO_ID+"&TO_NAME="+TO_NAME+"&PRIV_OP="+PRIV_OP+"&DEPT_ID="+DEPT_ID;
  loc_y=loc_x=200;
  if(is_ie)
  {
     loc_x=document.body.scrollLeft+event.clientX-100;
     loc_y=document.body.scrollTop+event.clientY+170;
  }
  LoadDialogWindow(URL,self,loc_x, loc_y, 250, 350);
}

function SelectPrivDept(MODULE_ID,TO_ID, TO_NAME, PRIV_OP,PRIV_NO_FLAG)
{
  URL="/module/priv_select_dept?MODULE_ID="+MODULE_ID+"&TO_ID="+TO_ID+"&TO_NAME="+TO_NAME+"&PRIV_OP="+PRIV_OP+"&PRIV_NO_FLAG="+PRIV_NO_FLAG;
  loc_y=loc_x=200;
  if(is_ie)
  {
     loc_x=document.body.scrollLeft+event.clientX-100;
     loc_y=document.body.scrollTop+event.clientY+170;
  }
  LoadDialogWindow(URL,self,loc_x, loc_y, 400, 350);
}

function SelectMytable(TO_ID, TO_NAME, POS)
{
  URL="/module/mytable?TO_ID="+TO_ID+"&TO_NAME="+TO_NAME+"&POS="+POS;
  loc_y=loc_x=200;
  if(is_ie)
  {
     loc_x=document.body.scrollLeft+event.clientX-100;
     loc_y=document.body.scrollTop+event.clientY+170;
  }
  LoadDialogWindow(URL,self,loc_x, loc_y, 500, 350);
}
function SelectShortcut(TO_ID, TO_NAME)
{
  URL="/module/shortcut?TO_ID="+TO_ID+"&TO_NAME="+TO_NAME;
  loc_y=loc_x=200;
  if(is_ie)
  {
     loc_x=document.body.scrollLeft+event.clientX-100;
     loc_y=document.body.scrollTop+event.clientY+170;
  }
  LoadDialogWindow(URL,self,loc_x, loc_y, 500, 350);
}
function td_calendar(fieldname)
{
	var URL="/inc/calendar.php?FIELDNAME="+fieldname;
	loc_y=loc_x=200;
	if(is_ie)
  {
    loc_x=document.body.scrollLeft+event.clientX-event.offsetX-80;
    loc_y=document.body.scrollTop+event.clientY-event.offsetY+140;
  }
  LoadDialogWindow(URL,self,loc_x, loc_y, 300, 230);
}
function td_clock(fieldname)
{
	var URL="/inc/clock.php?FIELDNAME="+fieldname;
	loc_y=loc_x=200;
	if(is_ie)
  {
    loc_x=document.body.scrollLeft+event.clientX-event.offsetX-80;
    loc_y=document.body.scrollTop+event.clientY-event.offsetY+140;
  }
  LoadDialogWindow(URL,self,loc_x, loc_y, 280, 120);
}
function SelectAddr(FIELD, TO_ID, FORM_NAME)
{
  URL="/module/addr_select?FIELD="+FIELD+"&TO_ID="+TO_ID+"&FORM_NAME="+FORM_NAME;
  loc_y=loc_x=200;
  if(is_ie)
  {
     loc_x=document.body.scrollLeft+event.clientX-event.offsetX-100;
     loc_y=document.body.scrollTop+event.clientY-event.offsetY+170;
  }
  LoadDialogWindow(URL,self,loc_x, loc_y, 400, 350);//这里设置了选人窗口的宽度和高度
}
function ClearAddr(TO_ID)
{
  if(TO_ID=="" || TO_ID=="undefined" || TO_ID== null)
     return;
  document.getElementsByName(TO_ID)[0].value="";
}