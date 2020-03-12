package decSeal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
/**
 * 调用本地dll类，解析印章数据格式，从印章中获取图片
 * @author wj
 *
 */
public class DecSealUtil {
	static Logger logger = LogManager.getLogger(DecSealUtil.class.getName());
	private static String dllname = "DecSealJni";
	private static DecSealUtil dec_seal;
	static {
		init();// 写入DLL
		System.loadLibrary(dllname);
		dec_seal=new DecSealUtil();
	}


	/**
	 * 写入DLL到java的 bin目录下
	 */
	static void init() {
		// 得到类路径
		String path = System.getProperty("java.library.path");
		// 得到第一个路径
		String dir = path.substring(0, path.indexOf(";"));
		//logger.info("dll存放路径：" + dir);
		// 得到dll的名称
		File dll = new File(dir + "/" + dllname + ".dll");
		try {
			InputStream stream = new DecSealUtil().getClass()
					.getResourceAsStream("/" + dllname + ".dll");
			if (stream != null) {
				FileOutputStream fos = new FileOutputStream(dll);
				byte[] b = new byte[1000];
				int n;
				while ((n = stream.read(b)) != -1)
					fos.write(b, 0, n);
				stream.close();
				fos.close();
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		} catch (IOException e) {
			logger.error(e.getMessage());

		}
	}
	/**
	 * 功能：从印章数据中解析出gif图片的base64数据
	 * @param sealData
	 * 				印章数据
	 * @param imgType
	 * 				图片类型，暂无意思，传“”
	 * @return   gif的base64
	 */
	public native String getSealImg(String sealData, String imgType);
	/**
	 * 功能：将旧的印章格式转成新的印章格式
	 * @param wbData
	 * 				旧盖章结果
	 * @return  新的格式的盖章结果
	 */
	public native String convertWebsignData(String wbData);

	public static DecSealUtil getDec_seal() {
		return dec_seal;
	}


	public static void setDec_seal(DecSealUtil dec_seal) {
		DecSealUtil.dec_seal = dec_seal;
	}
	
	public static void main(String[] args){
		String oldWebsignData="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";
		//String oldWebsignData="WebSignDataBegin::cert_id:6126 E530 0000 0000 004C;seal_x:90;seal_y:0;user_id:宋翠英4850768820041006;oriData_sha1:7RkVBMr29YPqmLkiKNK4ugH6q3A=;signRes:7E5xMi+RemzwGhlPBopKR6G3s9GDDoryoccOOCDWzKLtHhS7d3eUqLggp71fnl9y5i93teHWxHfQME6AKmmxV7yBWbjRj1yedwE+ysX4L8k9VaGuZvEr2yVl4u/QYkyFjTEVbI31ORRd63CihSMFTVpMXAijARj3AHFXZu0H/Zo=;signMode:0;cert_content:MIID9QYJKoZIhvcNAQcCoIID5jCCA+ICAQExADALBgkqhkiG9w0BBwGgggPKMIIDxjCCAy+gAwIBAgIKYSblMAAAAAAATDANBgkqhkiG9w0BAQUFADAsMSowKAYDVQQDDCHpmLPpu47pq5jpgJ/lhazot6/lu7rorr7nrqHnkIblpIQwHhcNMTEwMjE0MDEwOTA1WhcNMjAxMDMwMDMyOTE0WjCBpTELMAkGA1UEBhMCQ04xDzANBgNVBAgMBuWxseilvzEPMA0GA1UEBwwG5aSq5Y6fMSowKAYDVQQKDCHpmLPpu47pq5jpgJ/lhazot6/lu7rorr7nrqHnkIblpIQxEDAOBgNVBAsMByDpmLPlt6YxIjAgBgNVBAMMGeWui+e/oOiLsTQ4NTA3Njg4MjAwNDEwMDYxEjAQBgkqhkiG9w0BCQEWAzAwMTCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAp1UiFwVLFupGzNzeFWrsLC7II/gbuX2f6l0Q3IG5JEtwkz1hyo298OukxuRTP00/ct0K8ZWWoNr49EIty5HhveSxBeJLtdZcg/2OFe0sjI/ozC3XftFmUzYRRtiSPhyATf5hh/yHek4SUkuaJLb/Amb5kCskKrnb1AS8M/Xi7lkCAwEAAaOCAXMwggFvMA4GA1UdDwEB/wQEAwIE8DATBgNVHSUEDDAKBggrBgEFBQcDAjAdBgNVHQ4EFgQUTmdojKNoDgaz5JSAX96tozznylUwHwYDVR0jBBgwFoAUNpCho0LFW2/hvLWbZ1zTL8pRxUgwbgYDVR0fBGcwZTBjoGGgX4ZdZmlsZTovL1dJTi1PMFFHQlQzMUZRSy9DZXJ0RW5yb2xsLyE5NjMzITllY2UhOWFkOCE5MDFmITUxNmMhOGRlZiE1ZWZhIThiYmUhN2JhMSE3NDA2ITU5MDQuY3JsMIGJBggrBgEFBQcBAQR9MHsweQYIKwYBBQUHMAKGbWZpbGU6Ly9XSU4tTzBRR0JUMzFGUUsvQ2VydEVucm9sbC9XSU4tTzBRR0JUMzFGUUtfITk2MzMhOWVjZSE5YWQ4ITkwMWYhNTE2YyE4ZGVmITVlZmEhOGJiZSE3YmExITc0MDYhNTkwNC5jcnQwDAYDVR0TAQH/BAIwADANBgkqhkiG9w0BAQUFAAOBgQAb4h5IP0wK15TkCRJGJiluo186UDJg8IYqoX+k9ew0mSVEewO3wMPx125MFy8JC7ovXa/rG89tv2rAKtGV7Pp77kiJY7lUDE7iMe5os0jkf4WYnT9ATeZtGT8mlMKmiJdhsrRGmHW/Zwz4wbFi6Baj9e0Us6n6/KKzx+WehuCFATEA;seal_time:20121206120821;seal_data:TEFFU6UFAADL4bxfZOMKuP0XGmVhnlhQBgAAAGwAAAAoEAAAAAAAAP4WAAD6QK05ETmLIb3NXMQqdijTYgtpyRI5boaRCDhJqe9dqoXlUrTmacc3PuH5ZReLYj1uRqFqnAd4NwFaqKHpuTDqMMVUO2wC1kuW7Zi0/nLA3rVKZup9Qa9iDrBphIvD7CxWOVIniG/xK6/g6irOcYfkE0Gi2R86bGIWKMnXZyKBsZ3t6rCy+jd+vUBu7i0+ARwh1Z59go+3r5B+VUjf4pK6HtnnZZDHsXXfKaW+JyBIixHwqlI8rKtTQqSl2IF+BHIYrEyzoNC6nnEzMLqAnRm50T21TUlteYHPgXaVH4bOwy+Kt7jCsNKTIShTJNjPwZu+02ptKoUngeHVfZHVMstvyQvYmRrjQoL9uRZ92d5nxY6KO1YGOWBIsZdWfZMDxPqmxo8KEdD4+9sUprwVieaSos6KM3EGFFk6lqLtqqS/VfOAXBNX0PLduhVSVvmqu9lVpqNCsZ3uv69vx2Pari5hthXFT2xUjiphVNnd73IQH35tGUvGkuUzTRZBIqlPay/JMZM3DGkdzEM1PlaJEqPHQ/NKZzkzJdC/UEwaAO+fPDLEN3HUw1ctbf8+he0XGVowa0vnGgG14Ww4/VxJZXp+Fz9ihJYO7GOqGLFe5nhEEeDMcZveMm8znCN/+0f1a8u3rEHVqxphXhHsO3HlUNobotS4HJuQkOnDehUxKnOBK+r5Ai4VVKROL1g69u3NbkmjPcXQxR7/1a5IHCscfD9G4kfNjiFwDKzbkxeH7lYrDuflU9AZFCFsVpsw6cVs6uS5Hdlkt0cE7p9BfQwVIspyOyIj7916bdve73B2i9Ij8gCMlTt0sJTC5OG+HmmcXxymQxXDZrD6WYem6wWTWCO3ZHvAGvA5HGxI3ZJL9sJoEwRMizoURkl5/Sfl3ZYMcnNw6/QonZytQrCBMRKfIV+L3gLYhag0Cw0ADdu/Rwg4vDsg7t7P01PIHn9UxjxlaNfGmYlRqNLFkX4opbpILqDCpz5NYnuX1TtU8VEg2Bueuj2L4RZJnSoOCvEatQiMFdGS4lLviUkNFUqbwyurnwMwRJyESaS1Pd/pxtDSHh2D6jmP8xQoR9qXx12vDln+50rGIEVU6WKASEg6H1aRmShTDGAvLBn+YQH2CzBv1dtl1dumcoFvOedz4WZJqjcVBkdllgEYqDDcAo2fwDHUNTZhlg8Ky2vlCbVuo3cybC5ua1fW1i3vs5+JF5cK5i3/0ndvVBbUAO6GKzfJuy0YNat1a9uQo7hCsLKLoytYWUx54IAW2HG5dJXeSHD8RuIVIojdY1vi6tncXyaV/K/YRmazVlYfR8BnxBL+SHiXA43rG5BgsrdGDhJAfYAhDIBtrxYcLH+rHbCs1dCx9hzprem8nAM1qweg58CXiqf33MKhOO+2n7IeCSjxwwo8Pa1GlsfSKVfcUW6sy+tzW7P4HoQzPii6ub99c8LJWCX2ulv5vUcf+Q7o4Tks1LGLyVVvjLhgIn8bpVca7h9XnZeg/F5MVlrg1Fl2LIzwDEpVfxPmEIFMMxH62sjMfXK8pa6eVsZ5dR5iNtYEgv7w+bVSMXhc3xRVwsUQ1+cdedUwCP8IA2vDUnUYpZz3yNY0Y6eKZjVhzXZIGv0jubMnc/4hjdj6N73kd3Ei1UFiRYExi0T+r7UGDfqTtTiJGpJvw1Gfp+Ue7mNlTQMaS3pIMi67l98pR1IL7R9AaMyGG4wamK1Sfx5CivSxHkTvvVP+CkMoVNl9XJC7vLChnQvlvA2wxywU2RtacB2U8OKS7LobToiG65lZ+GYeZUz1CwBL2szNaBFXTq4zLbkA/+MrwdcgPNHWc2ojcxdZw/H7KRAX+jBuOzyjM72TgLQ4SLxxzzGzcFq8IzNMZr2WKgA=;seal_name:项目计量工程师;seal_position:tdSealB;::WebSignDataEnd;";
		//String oldWebsignData="111111";
		DecSealUtil dec_seal=DecSealUtil.getDec_seal();
		String newWebsignData=dec_seal.convertWebsignData(oldWebsignData);
		logger.info(newWebsignData);
	}
}
