package com.dj.hotelApi;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.po.HotelAdvertPO;
import com.dj.seal.util.Constants;

public class AdvertCan {
	private static String modelfolder="modelfolder";
	static Logger logger = LogManager.getLogger(AdvertCan.class.getName());
//联通
	public static String getAllAdHtmlCode(List<HotelAdvertPO> adverts){
		Timestamp now_time = new Timestamp(System.currentTimeMillis());
		int speedImage = 500;
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html>").append("\r\n");
		sb.append("<!DOCTYPE html>").append("\r\n");
		sb.append("<html lang=\"zh-CN\">").append("\r\n");
		sb.append("<head>").append("\r\n");
		sb.append("<meta charset=\"charset=gb2312\" >").append("\r\n");
		sb.append("<title>广州农商行业务营销</title>").append("\r\n");
		sb.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=gb2312\" />").append("\r\n");
		sb.append("<style id=\"csty\">").append("\r\n");
		sb.append("* { margin: 0 auto; padding: 0;}").append("\r\n");
	//	sb.append(".flexslider { position: relative; overflow: hidden; background: url(images/loading.gif) 50% no-repeat;}").append("\r\n");
		sb.append(".slides { position: relative; z-index: 1;}").append("\r\n");
		sb.append(".flex-control-nav { position: absolute; bottom: 10px; z-index: 2; width: 100%; text-align: center;}").append("\r\n");
		sb.append(".flex-control-nav li { display: inline-block; width: 14px; height: 14px; margin: 0 5px; *display: inline; zoom: 1;}").append("\r\n");
		sb.append(".flex-control-nav a { display: inline-block; width: 14px; height: 14px; line-height: 40px; overflow: hidden; background: url(images/dot.png) right 0 no-repeat; cursor: pointer;}").append("\r\n");
		sb.append(".flex-control-nav .flex-active { background-position: 0 0;}").append("\r\n");
		sb.append("</style>").append("\r\n");
		sb.append("</head>").append("\r\n");
		sb.append("").append("\r\n");
		sb.append("<body style=\"overflow:hidden\" scroll=\"no\" > ").append("\r\n");
		sb.append(" ").append("\r\n");
		sb.append("").append("\r\n");
		sb.append("<div class=\"flexslider\">").append("\r\n");
		sb.append("	<ul class=\"slides\">").append("\r\n");
		for (HotelAdvertPO hotelAdvertPO : adverts) {
			speedImage =Integer.parseInt(hotelAdvertPO.getAd_scorlltime())*1000;//新增广告时前端控制轮播时间不能为空
			/*广告有效期*/
			if(now_time.before(hotelAdvertPO.getAd_starttime())||now_time.after(hotelAdvertPO.getAd_endtime())){
				continue;
			}
			String[] fileArry = hotelAdvertPO.getAd_filename().split(",");
			for(int i = 0;i<fileArry.length;i++){
				sb.append("			<li><img src=\""+fileArry[i]+"\"> </li>").append("\r\n");
			}
		}
		sb.append(" </ul>").append("\r\n");
		sb.append("</div>").append("\r\n");
		sb.append("").append("\r\n");
		sb.append("<script src=\"js/jquery-1.7.2.min.js\"></script>").append("\r\n");
		sb.append("<script src=\"js/jquery.flexslider-min.js\"></script>").append("\r\n");
		sb.append("<script>").append("\r\n");
		sb.append("$(window).load(function() {").append("\r\n");
		sb.append("	 var j = 0;").append("\r\n");
		sb.append("	var clientWidth = document.documentElement.clientWidth;").append("\r\n");
		sb.append("	var clientHeight = document.documentElement.clientHeight;").append("\r\n");
	//	sb.append("	var o = document.getElementById(\"csty\");").append("\r\n");
	//	sb.append("	s = \"\";").append("\r\n");
	//	sb.append("	o.styleSheet.rules[1].style.height = clientHeight-100;").append("\r\n");
		sb.append("	$('.flexslider').flexslider({").append("\r\n");
		sb.append("		directionNav: false,").append("\r\n");//是否显示左右切换剪头
		sb.append("		pauseOnAction: false,").append("\r\n");
		sb.append("		animation:\"fade\",//图片转换方式；淡入淡出(fade)或者滑动(slide)").append("\r\n");
		sb.append("		animationSpeed:0,//内容切换时间").append("\r\n");
		sb.append("		slideshowSpeed:"+speedImage+"").append("\r\n");
		sb.append("	});").append("\r\n");
		sb.append("	 for (j=0;j<document.images.length;j++)").append("\r\n");
		sb.append("        {").append("\r\n");
		sb.append("            document.images[j].width=(document.images[j].width>clientWidth)?clientWidth:document.images[j].width;").append("\r\n");
		sb.append("            document.images[j].height=clientHeight;").append("\r\n");
		sb.append("        }").append("\r\n");
		sb.append("});").append("\r\n");
		sb.append("</script>").append("\r\n");
		sb.append("<br />").append("\r\n");
		sb.append("<br />").append("\r\n");
		sb.append("").append("\r\n");
		sb.append("</body>").append("\r\n");
		sb.append("</html>").append("\r\n");
		return sb.toString();
	}
	
	
	public void writeAdHtml(List<HotelAdvertPO> adverts){
		//写入广告文件
		String filePath = Constants.getProperty("hotelAdvertSavePath")+"cloudplay.html";
		String fileCode = getAllAdHtmlCode(adverts);
		File file = new File(filePath);
		
		byte bfile[] = fileCode.getBytes();
		
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		
		try {
			OutputStream outFile = new FileOutputStream(file);
			outFile.write(bfile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	public String bpath() {
		String bpath = "";
		bpath = Constants.basePath;
		try {
			String is_type=Constants.getProperty("is_type");
			if(is_type.equals("1")){//从配置文件读取路径
				bpath=Constants.getProperty("save_path");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
		}
		return bpath;
	}
		
}
