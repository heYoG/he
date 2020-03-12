var $ = function(id) {return document.getElementById(id);};
function ShowDialog(id)
{
   $("overlay").style.display = 'block';
   $(id).style.display = 'block';

   var bb=(document.compatMode && document.compatMode!="BackCompat") ? document.documentElement : document.body;
   if(document.compatMode && document.compatMode!="BackCompat")
   {
      $("overlay").style.width = bb.scrollWidth+"px";
      $("overlay").style.height = bb.scrollHeight+"px";
   }
   else
   {
      $("overlay").style.width = bb.scrollWidth+"px";
      $("overlay").style.height = bb.scrollHeight+"px";
   }
   
   $(id).style.left = ((bb.offsetWidth - $(id).offsetWidth)/2)+"px";
   $(id).style.top  = (90 + bb.scrollTop)+"px";
}
function HideDialog(id)
{
   $("overlay").style.display = 'none';
   $(id).style.display = 'none';
}