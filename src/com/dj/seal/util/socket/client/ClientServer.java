package com.dj.seal.util.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class ClientServer {
	static Logger logger=LogManager.getLogger(ClientServer.class.getName());
	
	public static void main(String[] args) throws Exception, IOException {
		Socket socket = new Socket("128.160.14.98",3089);
		socket.setSoTimeout(10000);
		PrintStream out=new PrintStream(socket.getOutputStream());//输出流
		InputStreamReader reader = new InputStreamReader(socket.getInputStream(),"UTF-8");//输入流
		BufferedReader buf=new BufferedReader(reader);
		StringBuffer sbs=new StringBuffer();//发送
		StringBuffer sbr=new StringBuffer();//接收
		
		sbs.append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>\r\n");
		sbs.append("<SealDocRequest>\r\n");
		sbs.append("	<BASE_DATA>\r\n");
		sbs.append("		<SYS_ID>sysId</SYS_ID>\r\n");
		sbs.append("	</BASE_DATA>\r\n");
		sbs.append("	<META_DATA>\r\n");
		sbs.append("		<IS_MERGER>false</IS_MERGER>\r\n");
		//sbs.append("		<!--是否签章true签章，false不签章-->\r\n");
		sbs.append("		<IS_SEAL>true</IS_SEAL>\r\n");
		//sbs.append("		<!--凭证编号-->\r\n");
		sbs.append("		<CEVOUCHERNO>0219</CEVOUCHERNO>\r\n");
		//sbs.append("		 <!--凭证类型-->\r\n");
		sbs.append("		<CEVOUCHERTYPE>0175</CEVOUCHERTYPE>\r\n");
		//sbs.append("		<!--交易流水号-->\r\n");
		sbs.append("		<CETRANSQ>Z20240408A00007548280</CETRANSQ>\r\n");
		//sbs.append("		  <!--交易码-->\r\n");
		sbs.append("		<CETRANCODE>15323</CETRANCODE>\r\n");
		//sbs.append("		<!--操作柜员号-->\r\n");
		sbs.append("		<CETELLERID>ceadmin</CETELLERID>\r\n");
		//sbs.append("		<!--组织机构号-->\r\n");
		sbs.append("		<CEORGUNIT>01</CEORGUNIT>\r\n");
		sbs.append("	</META_DATA>\r\n");
		sbs.append("	<FILE_LIST>\r\n");
		sbs.append("		<TREE_NODE>\r\n");
		//sbs.append("			<!--生成文件名称-->\r\n");
		sbs.append("			<FILE_NO>Y20180206.pdf</FILE_NO>\r\n");
		//sbs.append("			<!--是否添加二维码-->\r\n");
		sbs.append("			<IS_CODEBAR>false</IS_CODEBAR>\r\n");
		//sbs.append("			<!-- 印章类型 1.普通公章 2.冻结印章 3.解冻印章 -->\r\n");
		sbs.append("			<SEAL_TYPE>2</SEAL_TYPE>\r\n");
		//sbs.append("			<!--固定值3:按照机构编号进行签章-->\r\n");
		sbs.append("			<RULE_TYPE>3</RULE_TYPE>\r\n");
		//sbs.append("			 <!--应用场景data是模板数据合成-->\r\n");
		sbs.append("			<CJ_TYPE>data</CJ_TYPE>\r\n");
		//sbs.append("			<!--模板名称-->\r\n");
		sbs.append("			<MODEL_NAME>hz</MODEL_NAME>\r\n");
		//sbs.append("			<!--是否添加标记印章-->\r\n");
		sbs.append("			<AREA_SEAL>false</AREA_SEAL>\r\n");
		sbs.append("			<APP_DATA>\r\n");
		sbs.append("				<Info>\r\n");
		sbs.append("					<title>最高人民法院</title>\r\n");
		sbs.append("					<content>最高人民法院：\r\n    你院-258号裁定书、(2017)法 控字第4号协助执行通知书收悉，" +
				"我行处理结果如下   被执行人亢八在我行622439610015652843_0001账户内的存款已被冻结，已冻结可用金额人民币100000.00元，" +
				"额度冻结金额人民币100000.00元，冻结期限自2024年04月08日至2024年10月08日.</content>\r\n");
		sbs.append("					<inscribe>2024年04月08日 </inscribe>\r\n");
		sbs.append("				</Info>\r\n");
		sbs.append("			</APP_DATA>\r\n");
		sbs.append("		</TREE_NODE>\r\n");
		sbs.append("	</FILE_LIST>\r\n");
		sbs.append("</SealDocRequest>\r\n");
		
		String format = String.format("%06d", sbs.toString().getBytes("utf-8").length);
		String newStr=format+sbs.toString();
		newStr="      ";
		out.println(newStr);//发送数据到服务端
		
		System.out.println("客户端请求数据:"+newStr);
		
		char[] ch=new char[1024*10];
		int m=0;
		while((m=buf.read(ch))!=-1){
			sbr.append(ch);
		}
		System.out.print("服务端响应数据:"+sbr);
		if(socket!=null){//构造函数建立了连接，关闭套字节
			socket.close();
		}
	}
}
