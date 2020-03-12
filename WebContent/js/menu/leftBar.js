function doAction(index,src1,src2){
	var obj=document.getElementById("u"+index);
	if(obj.style.display=='none'){
		obj.style.display='block';
		if(index=='1')
			document.getElementById('img1').src=src1;
	}else{
		obj.style.display='none';
		if(index=='1')
			document.getElementById('img1').src=src2;
	}
}