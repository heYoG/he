package com.dj.seal.util.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientElectronicAndInternationalAccount {
	public static void main(String[] args) {
		try {
			Socket socket=new Socket("128.160.14.98",20010);//socket通信地址
			socket.setSoTimeout(10000);//超时时间
			PrintStream prints=new PrintStream(socket.getOutputStream());//客户端请求
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));//服务端响应
			StringBuilder sbl=new StringBuilder();//request
			StringBuffer sbf=new StringBuffer();//response
			int length=0;//length of request data
			String newString=null;//formated data
			char[]c=new char[1024 * 10];//char array
			int r=0;
			
			/*系统头*/
			sbl.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			sbl.append("<service version=\"2.0\">");
			sbl.append("<SYS_HEAD>");
			sbl.append("<ServiceScene>02</ServiceScene>");// 服务代码
			sbl.append("<ServiceCode>12002000052</ServiceCode>");// 服务场景,打印
			sbl.append("<ConsumerId>200001</ConsumerId>");// 消费系统编号
			sbl.append("<OrgConsumerId>24</OrgConsumerId>");// 发起方系统编号(渠道号)
			sbl.append("<OrgConsumerSeqNo>Y1805100587000442</OrgConsumerSeqNo>");// 消费系统流水号
			sbl.append("<ServSeqNo></ServSeqNo>");// 服务系统流水号
			sbl.append("<TranDate>20240418</TranDate>");// 交易日期
			sbl.append("<TranTime></TranTime>");
			sbl.append("</SYS_HEAD>");
			sbl.append("<BODY>");
			
			/*获取印章图片*/
//			sbl.append("<TxnSeqNo>Y2405201257000038,Y2405201257000039,Y2405201257000040</TxnSeqNo>");//流水号
//			sbl.append("<EleRcnclTxnNo>61314,61315,61316</EleRcnclTxnNo>");//交易码
//			sbl.append("<TxnDt>20240522,20240523,20240524</TxnDt>");//交易日期
//			sbl.append("<MnpltPrsnNm>呵呵二将</MnpltPrsnNm>");//操作人
//			sbl.append("<TxnBrId>0257,0257,02507</TxnBrId>");//机构号
//			sbl.append("<TxnNm>存款</TxnNm>");//交易名称
			
			/*获取验证码*/
			sbl.append("<TxnSeqNo>Y2405201257000038</TxnSeqNo>");//Y2018120300001/6224396100155535791556154250954
			sbl.append("<TxnNo>6154250954</TxnNo>");//45196/6154250954
			sbl.append("<TxnDt>20190425</TxnDt>");//20180423/20190425
			sbl.append("<SysInd>24</SysInd>");//获取验证码系统标识24/36
//			sbl.append("<AcctAcctNo>622439610015553579</AcctAcctNo>");//帐户帐号6222103246995623655/622439610015553579
//			sbl.append("<AcctNm>张三</AcctNm>");//帐户名称张三/军军
//			sbl.append("<EnqrStrtDt>20240710</EnqrStrtDt>");//查询开始日期20190422/20240710
//			sbl.append("<EnqrEndDt>20240930</EnqrEndDt>");//查询结束日期20190424/20240930
//			sbl.append("<StmtDt>20190425</StmtDt>");//帐单生成日期20190423/20190425
			
			
			sbl.append("</BODY>");
			//报文结束要加\n(即加\n)换行符，否则服务端readLine()读取无法结束，导致Connection reset或根据报文头读取
			sbl.append("</service>");
			
//			sbl.append("test");//空报文用于F5探测端口
			
			/*request*/
			length=new String(sbl.toString().getBytes("UTF-8")).length();
			newString=String.format("%08d", length)+sbl.toString();
//			newString=sbl.toString();
			System.out.println("request information...\n"+newString);
			prints.print(newString);//send out
			
			/*receive*/
			while((r=br.read(c))!=-1){//read all
				sbf.append(c);//char stream
			}
			System.out.println("response information...\n"+new String(sbf));
			
			/*last,close the socket*/
			if(socket!=null){
				socket.close();
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
