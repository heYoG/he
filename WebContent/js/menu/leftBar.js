function doAction(index,src1,src2){
	var obj=document.getElementById("u"+index);
	if(obj.style.display=='none'){
		obj.style.display='block';
		if(index=='1')
			document.getElementById('img1').src=src1;
		else if(index=='2')
			document.getElementById('img2').src=src1;
		else if(index=='3')
			document.getElementById('img3').src=src1;
		else if(index=='4')
			document.getElementById('img4').src=src1;
		else if(index=='5')
			document.getElementById('img5').src=src1;
		else if(index=='6')
			document.getElementById('img6').src=src1;
		else if(index=='7')
			document.getElementById('img7').src=src1;
	}else{
		obj.style.display='none';
		if(index=='1')
			document.getElementById('img1').src=src2;
		else if(index=='2')
			document.getElementById('img2').src=src2;
		else if(index=='3')
			document.getElementById('img3').src=src2;
		else if(index=='4')
			document.getElementById('img4').src=src2;
		else if(index=='5')
			document.getElementById('img5').src=src2;
		else if(index=='6')
			document.getElementById('img6').src=src2;
		else if(index=='7')
			document.getElementById('img7').src=src2;
	}
}