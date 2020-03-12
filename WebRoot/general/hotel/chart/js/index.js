
function load(){
	chartSrv.getTodayShenHeData(cb_basic);
	chartSrv.getTodayYeWuData(cb_busi);
}
function cb_basic(d){
	if(d){
		var pic=new QerPic(1);
		for(var i=0;i<d.length;i=i+1){
			if(i%2==0){
				pic.AddItem(new QerPicItem(d[i].value,d[i].text));
			}else{
				pic.AddItem(new QerPicItem(d[i].value,d[i].text,"green"));
			}
		}
		$("div_basic").innerHTML=pic.HtmlCode();
	}
}

function cb_busi(d){
	if(d){
		var pic=new QerPic(2);
		for(var i=0;i<d.length;i=i+1){
			if(i%2==0){
				pic.AddItem(new QerPicItem(d[i].value,d[i].text));
			}else{
				pic.AddItem(new QerPicItem(d[i].value,d[i].text,"green"));
			}
		}
		$("div_busi").innerHTML=pic.HtmlCode();
	}
}

