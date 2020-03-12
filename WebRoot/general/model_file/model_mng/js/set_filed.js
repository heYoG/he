

function setParent(){
	var field_name;
  	var sss = document.getElementsByName('is_tongyong');
    if(sss[0].checked==true){
		field_name = document.getElementById('ITEM_NAME').value;
		
	}else if(sss[1].checked==true){
		field_name = document.getElementById('writeName').value;
	}
  	parent.setField(field_name,document.getElementById('ITEM_TYPE').value,document.getElementById('FONT_TYPE').value,document.getElementById('FONT_SIZE').value,document.getElementById('FONT_COLOR').value,document.getElementById('BORDER_TYPE').value,document.getElementById('FONT_X').value,document.getElementById('FONT_Y').value,document.getElementById('IS_READ_ONLY').value,document.getElementById('TIPSINFO').value,document.getElementById('ITEM_DATA_TYPE').value);
}

