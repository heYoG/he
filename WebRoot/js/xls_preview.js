var $ = function(id) {return document.getElementById(id);};
function show_ws(id)
{
   var tables=document.getElementsByTagName("table");
   for(var i=0;i<tables.length;i++)
   {
      if(tables[i].id.substr(0,3)=="ws_")
      {
         tables[i].style.display="none";
         $('ws_menu_'+tables[i].id.substr(3)).className="";
      }
   }
   $('ws_'+id).style.display="";
   $('ws_menu_'+id).className="active";
}
window.onload=function()
{
   self.moveTo(0,0);
   self.resizeTo(screen.availWidth,screen.availHeight);
   self.focus();
   
   $('ws').style.height=(document.body.clientHeight-30)+"px";
};
window.onresize=function()
{
   $('ws').style.height=(document.body.clientHeight-30)+"px";
};