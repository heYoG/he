package com.dj.seal.util.httpClient;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class XMLClient {
	
	   /**
     * HTTP Client Object,used HttpClient Class before(version 3.x),but now the
     * HttpClient is an interface
     */
   private DefaultHttpClient client;

  /**
      * Get XML String
      * 
      * @return XML-Formed string
    */
public String getXMLString() {
// A StringBuffer Object
  StringBuffer sb = new StringBuffer();
  sb.append(IClient.XML_HEADER).append("\r\n");
   sb.append("<ROOT>").append("\r\n");
   sb.append("  <SRCTRANCODE>WZH001</SRCTRANCODE>").append("\r\n");
   sb.append("  <SRCSYSID>WZH</SRCSYSID>").append("\r\n");
   sb.append("  <SRCTRNDT>20150803</SRCTRNDT>").append("\r\n");
   sb.append("  <SRCSEQNO>20150803000000000001</SRCSEQNO>").append("\r\n");
   sb.append("  <TRANBRCHNO>801011</TRANBRCHNO>").append("\r\n");
   sb.append("  <TRANTLR>73001</TRANTLR>").append("\r\n");
   sb.append("  <TRANTRX>12345678</TRANTRX>").append("\r\n");
   sb.append("  <TRANNM>123</TRANNM>").append("\r\n");
   sb.append("  <JDGDTTM>20150801105230</JDGDTTM>").append("\r\n");
   sb.append("  <JDGRSLT>A</JDGRSLT>").append("\r\n");
   sb.append("  <TRANCODE>10001</TRANCODE>").append("\r\n");
   sb.append("</ROOT>").append("\r\n");
   System.out.println(sb.toString());
   int strlength = 0;
   strlength = sb.toString().trim().getBytes().length;
   String newString = String.format("%08d", strlength);  
   return newString+sb.toString();
 }



/**
   * Send a XML-Formed string to HTTP Server by post method
   * 
   * @param url
   *            the request URL string
   * @param xmlData
   *            XML-Formed string ,will not check whether this string is
   *            XML-Formed or not
   * @return the HTTP response status code ,like 200 represents OK,404 not
   *         found
   * @throws IOException
   * @throws ClientProtocolException
   */
public Integer sendXMLDataByPost(String url, String xmlData)
    throws ClientProtocolException, IOException {
   Integer statusCode = -1;
   if (client == null) {
    // Create HttpClient Object
    client = new DefaultHttpClient();
   }
// Send data by post method in HTTP protocol,use HttpPost instead of
   // PostMethod which was occurred in former version
  HttpPost post = new HttpPost(url);
// Construct a string entity
  System.out.println(xmlData);
  StringEntity entity = new StringEntity(xmlData);
   // Set XML entity
  post.setEntity(entity);
   // Set content type of request header
  post.setHeader("Content-Type", "text/xml;charset=GBK");
// Execute request and get the response
  HttpResponse response = client.execute(post);
  // Response Header - StatusLine - status code
  statusCode = response.getStatusLine().getStatusCode();
   return statusCode;
 }

/**
   * Main method 
   * @param args
   * @throws IOException 
   * @throws ClientProtocolException 
   */
public static void main(String[] args) throws ClientProtocolException, IOException {
//   XMLClient client = new XMLClient();
//   Integer statusCode = client.sendXMLDataByPost("http://10.1.88.143:21281", client.getXMLString());
//   if(statusCode==200){
//    System.out.println("Request Success,Response Success!!!");
//   }else{
//    System.out.println("Response Code :"+statusCode);
//   }
//	XMLClient t = new XMLClient();
//	t.getXMLString();
	try{
			XMLClient xmlC = new XMLClient();
			String xmlData = xmlC.getXMLString();
			
			Socket socket=new Socket("10.1.88.143",21281);

			PrintWriter os=new PrintWriter(socket.getOutputStream());
			System.out.println(xmlData);
			 //������Ϣ  
			os.println(xmlData);  
	          
			os.flush();  
	          
			InputStream in = socket.getInputStream();
			byte[] b = new byte[1024];
			StringBuilder sb = new StringBuilder();
			int i = 0;
			while((i = in.read(b))!=-1){
				String mesage = new String(b);
				sb.append(mesage);
				int indexQ = sb.toString().indexOf("</ROOT>");
				if(indexQ > 0){
					in.close();
					socket.close();
					break;
				}
			}
		
			String message = sb.toString().trim();
			System.out.println(message);
			int headIndex = message.indexOf("<?xml");
			String receiveStr = message.substring(headIndex,message.length());
		//System.out.println(sb.toString());
	}catch(Exception e){
		e.printStackTrace();
	}

 }

}
