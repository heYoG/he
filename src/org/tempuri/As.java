package org.tempuri;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import com.dj.seal.util.encrypt.Base64;

public class As {
	public static void main(String[] args) throws Exception {
		File fe = new File("e:\\testNo12.pdf");
		InputStream is;
		is = new FileInputStream(fe);
		byte[] by = new byte[(int) fe.length()];
		is.read(by);// 读取数组里的数据
		String pdfString=new String (by);
		String basepdf64=Base64.encodeToString(by);
		///String basepdf64=new sun.misc.BASE64Encoder().encode(by);
		System.out.println(basepdf64);
		is.close();
	}
}
