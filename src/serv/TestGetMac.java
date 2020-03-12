package serv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestGetMac {
	/*
	 * 获取操作系统类型
	 */
	public static String getOSName(){
		return System.getProperty("os.name").toLowerCase();
	}
	/*
	 * 获取unix网卡mac地址
	 */
	public static String getUnixMac(){
		String mac=null;
		BufferedReader br=null;
		Process process=null;
		try {
			process=Runtime.getRuntime().exec("ifconfig eth0");//linux下命令，用eth0做本地网卡
			br=new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line=null;
			int index=-1;
			while((line=br.readLine())!=null){
				index=line.toLowerCase().indexOf("hwaddr");//寻找标识字符串
				if(index>=0){
					mac=line.substring(index+"hwaddr".length()+1).trim();
					//System.out.println(mac);
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{

			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			br=null;
			process=null;
		}
		return mac;
	}
	
	/*
	 * 获取windows网卡地址
	 */
	public static String getWindowsMac(){
		String mac=null;
		BufferedReader br=null;
		Process process=null;
		try {
			process=Runtime.getRuntime().exec("ipconfig /all");//windows下命令
			br=new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line=null;
			int index=-1;
			int index2=-1;
			while((line=br.readLine())!=null){
				index=line.toLowerCase().indexOf("physical address");//寻找标识字符串
				index2=line.toLowerCase().indexOf("物理地址");//寻找标识字符串
				if(index>=0){
					index=line.indexOf(":");
					if(index>=0){
						mac=line.substring(index+1).trim();
						//System.out.println(mac);
					}
					break;
				}else if(index2>=0){
					index2=line.indexOf(":");
					if(index2>=0){
						mac=line.substring(index2+1).trim();
						//System.out.println(mac);
					}
					break;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{

			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			br=null;
			process=null;
		}
		return mac;
	}
	public static void main(String[] args) {
		String os=TestGetMac.getOSName();
		System.out.println(os);
		if(os.startsWith("windows")){
			String mac=TestGetMac.getWindowsMac();
			System.out.println(mac);
		}else{
			String mac=TestGetMac.getUnixMac();
			System.out.println(mac);
		}
	}

}
