function click_user(user_id)
{
  TO_VAL=to_id.value;
  TO_NAME=to_name.value;
  targetelement=$(user_id);
  user_name=targetelement.title;
  if(TO_VAL.indexOf(","+user_id+",")>0 || TO_VAL.indexOf(user_id+",")==0)
  {
    if(TO_VAL.indexOf(user_id+",")==0)
       to_id.value=to_id.value.replace(user_id+",","");
    else if(TO_VAL.indexOf(","+user_id+",")>0)
       to_id.value=to_id.value.replace(","+user_id+",",",");
    
    if(TO_NAME.indexOf(user_name+",")==0)
       to_name.value=to_name.value.replace(user_name+",","");
    else if(TO_NAME.indexOf(","+user_name+",")>0)
       to_name.value=to_name.value.replace(","+user_name+",",",");
    
    borderize_off(targetelement);
  }
  else
  {
    to_id.value+=user_id+",";
    to_name.value+=user_name+",";
    borderize_on(targetelement);
  }

}

function borderize_on(targetelement)
{
 color="#003FBF";
 targetelement.style.borderColor="black";
 targetelement.style.backgroundColor=color;
 targetelement.style.color="white";
 targetelement.style.fontWeight="bold";
}

function borderize_off(targetelement)
{
  targetelement.style.backgroundColor="";
  targetelement.style.borderColor="";
  targetelement.style.color="";
  targetelement.style.fontWeight="";
}

function begin_set()
{
  TO_VAL=to_id.value;

  for (step_i=0; step_i<allElements.length; step_i++)
  {
    if(allElements[step_i].className.indexOf("menulines")>=0)
    {
       user_id=allElements[step_i].id;
       if(TO_VAL.indexOf(","+user_id+",")>0 || TO_VAL.indexOf(user_id+",")==0)
          borderize_on(allElements[step_i]);
    }
  }
}

function add_all(flag)
{
  if(isUndefined(flag))
     flag="";
  TO_VAL=to_id.value;
  for (step_i=0; step_i<allElements.length; step_i++)
  {
    if(allElements[step_i].className.indexOf("menulines"+flag)>=0)
    {
       user_id=allElements[step_i].id;
       user_name=allElements[step_i].title;

       if(TO_VAL.indexOf(","+user_id+",")<0 && TO_VAL.indexOf(user_id+",")!=0)
       {
         to_id.value+=user_id+",";
         to_name.value+=user_name+",";
         borderize_on(allElements[step_i]);
       }
    }
  }
}

function del_all(flag)
{
  if(isUndefined(flag))
     flag="";
  for (step_i=0; step_i<allElements.length; step_i++)
  {
    TO_VAL=to_id.value;
    TO_NAME=to_name.value;
    if(allElements[step_i].className.indexOf("menulines"+flag)>=0)
    {
       user_id=allElements[step_i].id;
       user_name=allElements[step_i].title;
       if(TO_VAL.indexOf(user_id+",")==0)
          to_id.value=to_id.value.replace(user_id+",","");
       else if(TO_VAL.indexOf(","+user_id+",")>0)
          to_id.value=to_id.value.replace(","+user_id+",",",");
       
       if(TO_NAME.indexOf(user_name+",")==0)
          to_name.value=to_name.value.replace(user_name+",","");
       else if(TO_NAME.indexOf(","+user_name+",")>0)
          to_name.value=to_name.value.replace(","+user_name+",",",");
       
       borderize_off(allElements[step_i]);
    }
  }
}