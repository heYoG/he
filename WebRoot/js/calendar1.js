function my_affair(AFF_ID)
{
  myleft=(screen.availWidth-250)/2;
  mytop=(screen.availHeight-200)/2;
  window.open("../affair/note.php?AFF_ID="+AFF_ID,"note_win"+AFF_ID,"height=200,width=250,status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes,top="+mytop+",left="+myleft);
}

function my_note(CAL_ID)
{
  myleft=(screen.availWidth-250)/2;
  mytop=(screen.availHeight-200)/2;
  window.open("note.php?CAL_ID="+CAL_ID,"note_win"+CAL_ID,"height=200,width=250,status=0,toolbar=no,menubar=no,location=no,scrollbars=yes,resizable=yes,top="+mytop+",left="+myleft);
}

function My_Submit()
{
  document.form1.submit();
}

function set_year(op)
{
  document.form1.BTN_OP.value=op+" year";
  My_Submit();
}

function set_mon(op)
{
  document.form1.BTN_OP.value=op+" month";
  My_Submit();
}

function set_week(op)
{
  document.form1.BTN_OP.value=op+" week";
  My_Submit();
}

function set_day(op)
{
  document.form1.BTN_OP.value=op+" day";
  My_Submit();
}

function set_status(status)
{
  document.form1.OVER_STATUS.value=status;
  My_Submit();
}

function set_option(option, id, className)
{
  hideMenu();
  option = typeof(option)=="undefined" ? "" : option;
  $(id.toUpperCase()+"_FIELD").value=option;
  
  $(id).innerHTML=$(id+'_'+option).innerHTML + $(id).innerHTML.substr($(id).innerHTML.indexOf("<"));
  $(id).className=className+option;
}

function display_front()
{
   var front=document.getElementById("front");
   if(!front)
      return;
   if(front.style.display=='')
      front.style.display='none';
   else
      front.style.display='';
}

function set_view(view, cname)
{
    if(cname=="" || typeof(cname)=='undefined') cname="cal_view";
    var exp = new Date();
    exp.setTime(exp.getTime() + 24*60*60*1000);
    document.cookie = cname+"="+ escape (view) + ";expires=" + exp.toGMTString()+";path=/";
    
    var url=view+'.php?OVER_STATUS='+document.form1.OVER_STATUS.value+'&YEAR='+document.form1.YEAR.value+'&MONTH='+document.form1.MONTH.value+'&DAY='+document.form1.DAY.value;
    if(document.form1.DEPT_ID) url+='&DEPT_ID='+document.form1.DEPT_ID.value;
    if(document.form1.USER_ID) url+='&USER_ID='+document.form1.USER_ID.value;
    location=url;
}

function new_cal(CAL_TIME, TIME_DIFF)
{
   window.open('new?CAL_TIME='+CAL_TIME+"&TIME_DIFF="+TIME_DIFF,'oa_sub_window','height=350,width=500,status=0,toolbar=no,menubar=no,location=no,left=300,top=200,scrollbars=yes,resizable=yes');
}

function new_diary(CAL_DATE)
{
   if(!CAL_DATE)
      CAL_DATE=document.form1.YEAR.value+document.form1.MONTH.value+document.form1.DAY.value;
   window.open('../../diary/new/?CAL_DATE='+CAL_DATE,'diary_sub_window','height=560,width=650,status=0,toolbar=no,menubar=no,location=no,left=180,top=50,scrollbars=yes,resizable=yes');
}

function del_cal(CAL_ID)
{
   var url='delete.php?CAL_ID='+CAL_ID+'&OVER_STATUS='+document.form1.OVER_STATUS.value+'&YEAR='+document.form1.YEAR.value+'&MONTH='+document.form1.MONTH.value+'&DAY='+document.form1.DAY.value;
   if(document.form1.DEPT_ID) url+='&DEPT_ID='+document.form1.DEPT_ID.value;
   if(document.form1.USER_ID) url+='&USER_ID='+document.form1.USER_ID.value;
   if(window.confirm("删除后将不可恢复，确定删除吗？"))
      location=url;
}

function new_arrange(USER_ID, CAL_TIME, TIME_DIFF)
{
   window.open('new.php?USER_ID='+USER_ID+'&CAL_TIME='+CAL_TIME+'&TIME_DIFF='+TIME_DIFF,'oa_sub_window','height=350,width=500,status=0,toolbar=no,menubar=no,location=no,left=300,top=200,scrollbars=yes,resizable=yes');
}
