/*
 * sample04.java
 * 
 * 
 */

package com.dj.test;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.grcb.pb.i.imageplatform.service.CESession;
import com.grcb.pb.i.imageplatform.service.QueryCondition;
import com.grcb.pb.i.imageplatform.service.Transaction;
import com.grcb.pb.i.imageplatform.service.VoucherImage;
import com.grcb.pb.i.imageplatform.util.CEConstants;

public class sample14 {
	
	public static void main(String[] args) {
		String username = "008023";
		String password = "d64c14b5e5b90837cb0f46a3b9e6681f";
		
		CESession cesession = new CESession();
		QueryCondition queryCondition = new QueryCondition();
		//1. logCE
		cesession.logon(username, password);
		//2. 
//		String transq = "Y1704120587000110";  //ce
//		String transq = "Y2111040679007945";  //20CE
//		
		//String transq = "100101880726";
		
		String transq = "Y2402270587001246";//交易流水
		
		queryCondition.setTransq(transq);
		
		//3.
		List<Transaction> transactions = cesession.queryTransactions(queryCondition);
		
		if(transactions == null)
		{
			System.out.println("Transq " + transq + " is not found in CE.");
		}
		else{
			System.out.println("Tranaction count is:"+transactions.size());
			for(Transaction tran:transactions){
				//4. 
				tran.retrieve(CEConstants.RETRIEVE_TRAN_INFO_IMAGECEID);
				String strCEID = tran.getCEID();
				String strTransq = tran.getTransq();
				String strTranCode = tran.getTranCode();
				String strTellerID = tran.getTellerID();
				String strOrgUnit = tran.getOrgUnit();
				Date createDate = tran.getCreateDateTime();
				Date updateDate = tran.getUpdateDateTime();
				int imageCount = tran.getImageCount();
				ArrayList<VoucherImage> vis = tran.getVoucherImages();
				
				System.out.println("Showing the transaction infomation stored in CE...");
				System.out.println("CEID is " + strCEID + ".");
				System.out.println("Transq is " + strTransq + ".");
				System.out.println("TranCode is " + strTranCode + ".");
				System.out.println("TellerID is " + strTellerID + ".");
				System.out.println("OrgUnit is " + strOrgUnit + ".");
				System.out.println("createDate is " + createDate + ".");
				System.out.println("updateDate is " + updateDate + ".");
				System.out.println("imageCount is " + imageCount + ".");
				if(vis != null && vis.size()>0) {
					for(int i=0;i<vis.size();i++) {
						VoucherImage vi = vis.get(i);
						if(vi != null) {
							System.out.println("VoucherImager[" + i + "] CEID is " + vi.getCEID() + ".");
							System.out.println("VoucherNo : " + vi.getVoucherNO());
							System.out.println("VoucherType:"+vi.getVoucherType());
							System.out.println("VoucherSubType:"+vi.getVoucherSubType());
						}
					}
				}
				//5.
				if(vis != null && vis.size()>0) {
					for(int i=0;i<vis.size();i++) {
						VoucherImage vi = vis.get(i);
						vi.retrieve(CEConstants.RETRIEVE_IMAGE_INFO_CONTENT);
						String ceid = vi.getCEID();
						String voucherNo = vi.getVoucherNO();
						String voucherType = vi.getVoucherType();
						System.out.println("VoucherNo : " + vi.getVoucherNO());
						System.out.println("VoucherType:"+vi.getVoucherType());
						System.out.println("VoucherSubType:"+vi.getVoucherSubType());
						System.out.println("Time:"+vi.getCreateDateTime());
						try {
							vi.getImage("D:/tmp/" + ceid +"_"+ voucherType + "."+vi.getFileExt());  
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					}
					
					
				}
			}
		}
		
				
		cesession.logoff();
	}

}
