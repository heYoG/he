package com.dj.seal.util.httpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class MyClient {
	
	  public static String clientResultByGet(String httpurl){
		   //String httpUrl = "http://10.1.88.65:9800/SunIntegrator/CheckSunfrontTicket?userid=00204&sunfront_ticket=c2e7a2a8267bc0131141ca89de1a8606&logout_url=http://ip:port/system/logout.jsp?userid=00204&sessionid=19Abd334rtRaq34";
	        String result = "";
			CloseableHttpClient httpclient = HttpClients.createDefault();
	        try {
	            HttpGet httpget = new HttpGet(httpurl);
	            //System.out.println(httpurl);
	            CloseableHttpResponse response = httpclient.execute(httpget);
	            try {
	            	HttpEntity entity = response.getEntity();
	                if(entity != null){
	                	InputStream in = entity.getContent();
	                	result = getResponseResult(in);
	                	System.out.println(result);
	                }
	            } finally {
	                response.close();
	            }
	        } catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
	            try {
					httpclient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    	return result;
	    }
	  
		public static String getResponseResult(InputStream in){
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
	    	StringBuilder sb = new StringBuilder();
	    	String line = null;
	    	try {
				while((line = reader.readLine()) != null){
					sb.append(line+"\n");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return sb.toString();
		}

}
