package org.tempuri;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class TestFax {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		 //testfax=new TestFax();
		 TestFax.FaxXml();
	}
    public static String FaxXml() throws Exception{
    	StringBuffer s1=new StringBuffer();
    	TestFax testfax=new TestFax();
    	String tokenId=testfax.SignIn();
    	System.out.println("tokenId:"+tokenId);
    	String sendreturn=testfax.Send(tokenId);
    	System.out.println("sendreturn:"+sendreturn);
    	return s1.toString();
    }
	public FaxServiceStub getObj() throws Exception {
		String ws_url = "http://172.20.99.20/services/faxservice.asmx?wsdl";
		FaxServiceStub obj = new FaxServiceStub(ws_url);
		return obj;
	}
	public String SignIn() throws Exception {
		FaxServiceStub.SignIn obj=new FaxServiceStub.SignIn();
		obj.setStrUserName("fax");
		obj.setStrPassword("fax");
		return getObj().SignIn(obj).getSignInResult();
	}
	public String Send(String tokenId) throws Exception {
		FaxServiceStub.Send obj=new FaxServiceStub.Send();
		File fe = new File("D://SendTest.xml");
		InputStream is = new FileInputStream(fe);
		byte[] by = new byte[(int) fe.length()];
		is.read(by);//
		is.close();
		String docString=new String (by);
		obj.setTokenId(tokenId);
		obj.setStrSendFax(docString);
		return getObj().Send(obj).getSendResult();
	}
}
