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

public class AdvertCanOldVersion {
	private static String modelfolder="modelfolder";
	static Logger logger = LogManager.getLogger(AdvertCanOldVersion.class.getName());
//联通
	public static String getAllAdHtmlCode(List<HotelAdvertPO> adverts){
		Timestamp now_time = new Timestamp(System.currentTimeMillis());
		StringBuffer sb = new StringBuffer();
		sb.append("<!DOCTYPE html>").append("\r\n");
		sb.append("<html lang=\"zh-CN\">").append("\r\n");
		sb.append("<head>").append("\r\n");
		sb.append("    <meta charset=\"utf-8\">").append("\r\n");
		sb.append("    <title>ShowPic</title>").append("\r\n");
		sb.append("    <link rel=\"stylesheet\" href=\"style/jquery.fullPage.css\">").append("\r\n");
		sb.append("</head>").append("\r\n");
		sb.append("<body onload=\"ReImgSize()\">").append("\r\n");
		sb.append("").append("\r\n");
		sb.append("<div id=\"dowebok\">").append("\r\n");
		sb.append("    <div class=\"section\" >").append("\r\n");
		for (HotelAdvertPO hotelAdvertPO : adverts) {
			if(now_time.before(hotelAdvertPO.getAd_starttime())||now_time.after(hotelAdvertPO.getAd_endtime())){
				continue;
			}
			String[] fileArry = hotelAdvertPO.getAd_filename().split(",");
			for(int i = 0;i<fileArry.length;i++){
				sb.append("        <div class=\"slide\"><img src=\""+fileArry[i]+"\"> </div>").append("\r\n");
			}
		}
//		sb.append("        <div class=\"slide\"><img src=\"images/01.jpg\"> </div>").append("\r\n");
//		sb.append("        <div class=\"slide\"><img src=\"images/02.jpg\"> </div>").append("\r\n");
//		sb.append("        <div class=\"slide\"><img src=\"images/03.jpg\"> </div>").append("\r\n");
//		sb.append("        <div class=\"slide\"><img src=\"images/04.jpg\"> </div>").append("\r\n");
		sb.append("    </div>").append("\r\n");
		sb.append("</div>").append("\r\n");
		sb.append("<script src=\"js/jquery-1.11.1.min.js\"></script>").append("\r\n");
		sb.append("<script src=\"js/jquery.fullPage.min.js\"></script>").append("\r\n");
		sb.append("<script>").append("\r\n");
		sb.append("    $(function(){").append("\r\n");
		sb.append("        $('#dowebok').fullpage({").append("\r\n");
		sb.append("          //  slidesColor: ['#009999'],").append("\r\n");
		sb.append("            css3:true,").append("\r\n");
		sb.append("            controlArrows:false,").append("\r\n");
		sb.append("            scrollingSpeed:-1000,").append("\r\n");
		sb.append("            continuousVertical:true").append("\r\n");
		sb.append("           // loopBottom: true,").append("\r\n");
		sb.append("          //  menu: '#menu'").append("\r\n");
		sb.append("        });").append("\r\n");
		sb.append("        setInterval(function(){").append("\r\n");
		sb.append("            $.fn.fullpage.moveSlideRight();").append("\r\n");
		sb.append("        }, 20000);").append("\r\n");
		sb.append("    });").append("\r\n");
		sb.append("    function ReImgSize(){").append("\r\n");
		sb.append("        var j = 0;").append("\r\n");
		sb.append("         var clientWidth = document.documentElement.clientWidth;").append("\r\n");
		sb.append("        for (j=0;j<document.images.length;j++)").append("\r\n");
		sb.append("        {").append("\r\n");
		sb.append("            document.images[j].width=(document.images[j].width>clientWidth)?clientWidth:document.images[j].width;").append("\r\n");
		sb.append("        }").append("\r\n");
		sb.append("    }").append("\r\n");
		sb.append("</script>").append("\r\n");
		sb.append("<!--代码部分end-->").append("\r\n");
		sb.append("").append("\r\n");
		sb.append("</body>").append("\r\n");
		sb.append("</html>   ").append("\r\n");
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
