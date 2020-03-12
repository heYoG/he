package com.dj.websignServer;

import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.gif4j.GifDecoder;
import com.gif4j.GifEncoder;
import com.gif4j.GifImage;
import com.gif4j.Watermark;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class PictrueMyUtil {
	static Logger logger = LogManager.getLogger(PictrueMyUtil.class.getName());
	private final static  String waterPic="R0lGODlh3gAHAHAAACH5BAEAAPwALAAAAADeAAcAhwAAAAAAMwAAZgAAmQAAzAAA/wArAAArMwArZgArmQArzAAr/wBVAABVMwBVZgBVmQBVzABV/wCAAACAMwCAZgCAmQCAzACA/wCqAACqMwCqZgCqmQCqzACq/wDVAADVMwDVZgDVmQDVzADV/wD/AAD/MwD/ZgD/mQD/zAD//zMAADMAMzMAZjMAmTMAzDMA/zMrADMrMzMrZjMrmTMrzDMr/zNVADNVMzNVZjNVmTNVzDNV/zOAADOAMzOAZjOAmTOAzDOA/zOqADOqMzOqZjOqmTOqzDOq/zPVADPVMzPVZjPVmTPVzDPV/zP/ADP/MzP/ZjP/mTP/zDP//2YAAGYAM2YAZmYAmWYAzGYA/2YrAGYrM2YrZmYrmWYrzGYr/2ZVAGZVM2ZVZmZVmWZVzGZV/2aAAGaAM2aAZmaAmWaAzGaA/2aqAGaqM2aqZmaqmWaqzGaq/2bVAGbVM2bVZmbVmWbVzGbV/2b/AGb/M2b/Zmb/mWb/zGb//5kAAJkAM5kAZpkAmZkAzJkA/5krAJkrM5krZpkrmZkrzJkr/5lVAJlVM5lVZplVmZlVzJlV/5mAAJmAM5mAZpmAmZmAzJmA/5mqAJmqM5mqZpmqmZmqzJmq/5nVAJnVM5nVZpnVmZnVzJnV/5n/AJn/M5n/Zpn/mZn/zJn//8wAAMwAM8wAZswAmcwAzMwA/8wrAMwrM8wrZswrmcwrzMwr/8xVAMxVM8xVZsxVmcxVzMxV/8yAAMyAM8yAZsyAmcyAzMyA/8yqAMyqM8yqZsyqmcyqzMyq/8zVAMzVM8zVZszVmczVzMzV/8z/AMz/M8z/Zsz/mcz/zMz///8AAP8AM/8AZv8Amf8AzP8A//8rAP8rM/8rZv8rmf8rzP8r//9VAP9VM/9VZv9Vmf9VzP9V//+AAP+AM/+AZv+Amf+AzP+A//+qAP+qM/+qZv+qmf+qzP+q///VAP/VM//VZv/Vmf/VzP/V////AP//M///Zv//mf//zP///wAAAAAAAAAAAAAAAAhjAPcJHEiwoMGDCBMqXMiwocOHECNKnEixosWLGDNq3Mixo8ePIEOKHCkwUwwAKFMCyJTxpMqXMGPKnEmzps2bOHPq3Mmzp0+cAtH8HEq0qNGjSJMOFUOyqdOnUKNKnUq1KtSAADs=";
	
	public  static String gifOperate(String gifbase64) throws IOException{
		byte[] gifbytes=new BASE64Decoder().decodeBuffer(gifbase64);
		InputStream gifIs=new ByteArrayInputStream(gifbytes);
		//姘村嵃鍥剧墖	
		byte[] waterPicbytes=new BASE64Decoder().decodeBuffer(waterPic);
		InputStream waterPicIs=new ByteArrayInputStream(waterPicbytes);
		Image src = ImageIO.read(waterPicIs);
        int wideth = src.getWidth(null);
        int height = src.getHeight(null);
        BufferedImage renderedWatermarkText = new BufferedImage(wideth, height,
                BufferedImage.TYPE_INT_RGB);
        //鍥剧墖瀵硅薄
        GifImage gf = GifDecoder.decode(gifIs);
        //鑾峰彇鍥剧墖澶у皬
        int iw = gf.getScreenWidth();
        int ih = gf.getScreenHeight();
        //鑾峰彇姘村嵃澶у皬
        int tw = renderedWatermarkText.getWidth();
        int th = renderedWatermarkText.getHeight();
        //姘村嵃浣嶇疆
        Point p = new Point();
        p.x = 0;
        p.y =Math.abs((ih - th))/2;
		InputStream gifIs2=new ByteArrayInputStream(gifbytes);
        //鍔犳按鍗�
        Watermark watermark = new Watermark(renderedWatermarkText, p);
        gf = watermark.apply(GifDecoder.decode(gifIs2), true);   
        //杈撳嚭
        ByteArrayOutputStream out=new ByteArrayOutputStream(1024);
        GifEncoder.encode(gf, out);
        String base64String=new BASE64Encoder().encode(out.toByteArray());
        out.close();
        base64String=base64String.replace("\r\n", "");
        base64String=base64String.replace("\r", "");
        base64String=base64String.replace("\n", "");
        return base64String;       
	}
	
	
	public static void main(String[] args) throws IOException {
		String gifbase64="R0lGODlhEAAQAOcAAAAAAIAAAACAAICAAAAAgIAAgACAgMDAwMDcwKbK8P/w1P/isf/Ujv/Ga/+4SP+qJf+qANySALl6AJZiAHNKAFAyAP/j1P/Hsf+rjv+Pa/9zSP9XJf9VANxJALk9AJYxAHMlAFAZAP/U1P+xsf+Ojv9ra/9ISP8lJf4AANwAALkAAJYAAHMAAFAAAP/U4/+xx/+Oq/9rj/9Ic/8lV/8AVdwASbkAPZYAMXMAJVAAGf/U8P+x4v+O1P9rxv9IuP8lqv8AqtwAkrkAepYAYnMASlAAMv/U//+x//+O//9r//9I//8l//4A/twA3LkAuZYAlnMAc1AAUPDU/+Kx/9SO/8Zr/7hI/6ol/6oA/5IA3HoAuWIAlkoAczIAUOPU/8ex/6uO/49r/3NI/1cl/1UA/0kA3D0AuTEAliUAcxkAUNTU/7Gx/46O/2tr/0hI/yUl/wAA/gAA3AAAuQAAlgAAcwAAUNTj/7HH/46r/2uP/0hz/yVX/wBV/wBJ3AA9uQAxlgAlcwAZUNTw/7Hi/47U/2vG/0i4/yWq/wCq/wCS3AB6uQBilgBKcwAyUNT//7H//47//2v//0j//yX//wD+/gDc3AC5uQCWlgBzcwBQUNT/8LH/4o7/1Gv/xkj/uCX/qgD/qgDckgC5egCWYgBzSgBQMtT/47H/x47/q2v/j0j/cyX/VwD/VQDcSQC5PQCWMQBzJQBQGdT/1LH/sY7/jmv/a0j/SCX/JQD+AADcAAC5AACWAABzAABQAOP/1Mf/sav/jo//a3P/SFf/JVX/AEncAD25ADGWACVzABlQAPD/1OL/sdT/jsb/a7j/SKr/Jar/AJLcAHq5AGKWAEpzADJQAP//1P//sf//jv//a///SP//Jf7+ANzcALm5AJaWAHNzAFBQAPLy8ubm5tra2s7OzsLCwra2tqqqqp6enpKSkoaGhnp6em5ubmJiYlZWVkpKSj4+PjIyMiYmJhoaGg4ODv/78KCgpICAgP8AAAD/AP//AAAA//8A/wD//////yH5BAEAAP8ALAAAAAAQABAAAAh+ANG5c9duIEGDBQcK/AegocOHAP4dGAiAUMR/GDMCgATAHEWOGTFGrFgJQMGKFzUyhIQIwEeLEDcSKvkSEiFINnHebEnxps+cPkuexBmzIUuX7lCGDAmAZlKQS0W2PGkxqlSkG1MubWoyaUurDHm6M1cU4jl39wYmPKjWILqAADs=";
		new PictrueMyUtil();
		String newgif=PictrueMyUtil.gifOperate(gifbase64);
		newgif=newgif.replace("\r\n", "");
		newgif=newgif.replace("\r", "");
		newgif=newgif.replace("\n", "");
		logger.info(newgif);
//		byte[] data=new BASE64Decoder().decodeBuffer(gifbase64);
//		FileOutputStream os=new FileOutputStream("d:\\3.gif");
//		os.write(data);
//		os.close();
	}

}
