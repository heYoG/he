/*
 * sample01.java
 * 
 * ���״���������һ�ʽ��ף�������Ӱ��ƾ֤���������õ�CEID
 * ģ������ϴ���һ�ʽ��ף���Ӱ��ؼ����ؽ���ƾ֤Ӱ�񣬹�Ա����Ӱ���Ҫ�����ԣ�������ʽ��״���CE��
 * 
 * 
 * ���У�
 * 1. ��¼
 * 2. ���2�ű���Ʊ��jpg�ļ�������2��ƾ֤Ӱ��
 * 3. ����1�����ף������Ѵ��������ƾ֤Ӱ��
 * 4. ������ʽ��׼���Ӱ���õ�CEID
 * 5. �ǳ�
 */

package com.dj.test;

import com.dj.seal.util.Constants;
import com.grcb.pb.i.imageplatform.service.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

public class sample01 {
	
	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();
		String username = Constants.getProperty("username");
		String password = Constants.getProperty("password");
		CESession cesession = new CESession();
		//1. ��¼
		cesession.logon(username, password);		
		//2. ����ƾ֤Ӱ��
		VoucherImage vi1 = new VoucherImage(cesession);
		vi1.setVoucherNO("1001");
		vi1.setVoucherType("0201");
		vi1.setVoucherSubType("0200");//δ����
		vi1.setTellerID("zh2081");//δ����
		vi1.setCreateDateTime(new Date(System.currentTimeMillis()));
		vi1.setPositiveFlag(true);
		vi1.setVerifyStampFlag(1);
		File vi1File = new File("D:/jinmi-hui.jpg");
		System.out.println(vi1File.getName()+":::"+vi1File.length());
		String vi1FileName = vi1File.getName();
		String vi1FileExt = "jpg";
		vi1.setFileName(vi1FileName);
		vi1.setFileExt(vi1FileExt);
		FileInputStream vi1FileIS = null;
		try {
			vi1FileIS = new FileInputStream(vi1File.getAbsolutePath());
			vi1.setImageStream(vi1FileIS);
			//System.out.println("zbtzbtzbt" + vi1FileIS);
		} catch (FileNotFoundException e) {
			System.out.println("Specified voucher image file is not found.");
			e.printStackTrace();
			System.exit(0);
		}
		
		//Ӱ��2
		VoucherImage vi2 = new VoucherImage(cesession);
		vi2.setVoucherNO("1002");
		vi2.setVoucherType("0200");
		vi2.setVoucherSubType("0200");
		vi2.setTellerID("zh2081");
		vi2.setCreateDateTime(new Date(System.currentTimeMillis()));
		vi2.setPositiveFlag(true);
		vi2.setVerifyStampFlag(1);
		File vi2File = new File("D:/icka.jpg");
		String vi2FileName = vi2File.getName();
		String vi2FileExt = "jpg";
		vi2.setFileName(vi2FileName);
		vi2.setFileExt(vi2FileExt);
		FileInputStream vi2FileIS = null;
		try {
			vi2FileIS = new FileInputStream(vi2File.getAbsolutePath());
			vi2.setImageStream(vi2FileIS);
			//System.out.println(vi2FileIS);
		} catch (FileNotFoundException e) {
			System.out.println("Specified voucher image file is not found.");
			e.printStackTrace();
			System.exit(0);
		}
		
		ArrayList<VoucherImage> viList = new ArrayList<VoucherImage>();
		viList.add(vi1);
		viList.add(vi2);
		
		//3. ����һ�ʽ���
		Transaction tran = new Transaction(cesession);
		tran.setTransq("100101880728");
		tran.setTranCode("1111");
		tran.setTellerID("zh2081");
		tran.setOrgUnit("zh2");
		tran.setCreateDateTime(new Date(System.currentTimeMillis()));
		tran.setUpdateDateTime(new Date(System.currentTimeMillis()));
		tran.setImageCount(viList.size());
		tran.setVoucherImages(viList);
		
		//4. ����ý���(����Ӱ��)������CEID��
		tran.save();
		String tranCEID = tran.getCEID();
		System.out.println("The new transaction CEID is "  + tranCEID + ".");		
		//5. �ǳ�
		cesession.logoff();
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
		
	}
	
}
