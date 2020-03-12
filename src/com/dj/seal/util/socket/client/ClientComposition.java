package com.dj.seal.util.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ClientComposition {
	public static void main(String[] args) throws IOException {
		// 客户端请求与本机在20006端口建立TCP连接
		 Socket client = new Socket("128.160.1.181", 20008);
//		Socket client = new Socket("128.160.6.150", 20008);
		client.setSoTimeout(10000);
		// 获取Socket的输出流，用来发送数据到服务端
		PrintStream out = new PrintStream(client.getOutputStream());
		// 获取Socket的输入流，用来接收从服务端发送过来的数据
		BufferedReader buf = new BufferedReader(new InputStreamReader(
				client.getInputStream(), "GBK"));
		StringBuilder sb = new StringBuilder();

		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		sb.append("<service version=\"2.0\">\r\n");
		sb.append("<SYS_HEAD>\r\n");
		sb.append("<ServiceScene>01</ServiceScene>\r\n");// 服务代码
//		sb.append("<ServiceCode>12002000052</ServiceCode>\r\n");// 服务场景,打印
		sb.append("<ServiceCode>12003000034</ServiceCode>\r\n");// 服务场景,查询
		sb.append("<ConsumerId>200001</ConsumerId>\r\n");// 消费系统编号
		sb.append("<OrgConsumerId>84</OrgConsumerId>\r\n");// 发起方系统编号(渠道号)
		sb.append("<SystemId>23</SystemId>\r\n");//服务方系统编号
		sb.append("<OrgConsumerSeqNo>082409062851</OrgConsumerSeqNo>\r\n");// 消费系统流水号
		sb.append("<ServSeqNo></ServSeqNo>\r\n");// 服务系统流水号
		sb.append("<TranDate>20180824</TranDate>\r\n");// 交易日期
		sb.append("<TranTime>090620</TranTime>\r\n");
		sb.append("</SYS_HEAD>\r\n");

		sb.append("<BODY>\r\n");// 主体
		
		/* 打印测试 */
//		 sb.append("<TxnSeqNo>Y2405201257000038</TxnSeqNo>\r\n");// 流水号
//		 sb.append("<TxnNo>45196</TxnNo>\r\n");// 交易码
//		 sb.append("<TxnDt>20240521</TxnDt>\r\n");// 交易日期
//		 sb.append("<TxnTlrNo>008081</TxnTlrNo>\r\n");// 交易柜员号
//		 sb.append("<TxnBrId>5900</TxnBrId>\r\n");// 交易机构号
//		 sb.append("<TxnBrIdNm></TxnBrIdNm>");//交易机构名称
//		 sb.append("<TxnNm></TxnNm>\r\n");// 交易名称
//		 sb.append("<ChkAhrTlrNo></ChkAhrTlrNo>\r\n");// 授权柜员号
//		 sb.append("<BsnVchrNo></BsnVchrNo>\r\n");// 凭证编号
//		 sb.append("<PreRsrvFld1Nm></PreRsrvFld1Nm>\r\n");//备用字段
//		 sb.append("<PreRsrvFld2Nm></PreRsrvFld2Nm>\r\n");
//		 sb.append("<PreRsrvFld3Nm>安阳珠江村镇银行</PreRsrvFld3Nm>\r\n");
//		 sb.append("<PreRsrvFld4Nm></PreRsrvFld4Nm>\r\n");
//		 sb.append("<PreRsrvFld5Nm></PreRsrvFld5Nm>\r\n");
//		 sb.append("<PreRsrvFld6Nm></PreRsrvFld6Nm>\r\n");
//		 sb.append("<PreRsrvFld7Nm></PreRsrvFld7Nm>\r\n");
//		 sb.append("<PreRsrvFld8Nm></PreRsrvFld8Nm>\r\n");
//		 sb.append("<PreRsrvFld9Nm></PreRsrvFld9Nm>\r\n");
//		 sb.append("<PreRsrvFld10Nm></PreRsrvFld10Nm>\r\n");
//		 sb.append("<VerfNo></VerfNo>\r\n");//验证码
//		 sb.append("<PlsInf>|+|销户账户账号：02572195000016886| </PlsInf>\r\n");//附加信息
//		 sb.append("<PrtNum>1</PrtNum>\r\n");//打印份数

		/* 查询测试 */
		sb.append("<EsalVerfNo>0U2U0S1N1V2E4G0N</EsalVerfNo>\r\n");// 验证码查询3D0011222U1T2V2A
//		sb.append("<TxnSeqNo>Y2405201257000038</TxnSeqNo>\r\n");// 流水号查询
//		sb.append("<TxnDt>20180423</TxnDt>\r\n");// 日期查询20180423
//		sb.append("<MblNo></MblNo>\r\n");//手机号11111111112
//		sb.append("<EnqrInd>84</EnqrInd>");
		
		/*国结系统和对账系统*/
//		sb.append("<TxnSeqNo>00220821</TxnSeqNo>\r\n");//流水号
//		sb.append("<EleRcnclTxnNo>BPTFPN</EleRcnclTxnNo>\r\n");//交易码
//		sb.append("<TxnDt>20180824</TxnDt>\r\n");//交易日期
//		sb.append("<MnpltPrsnNm>LLW</MnpltPrsnNm>\r\n");//操作人
//		sb.append("<TxnBrId>0679</TxnBrId>\r\n");//机构号
//		sb.append("<TxnNm>BPTFPN</TxnNm>\r\n");//交易名称
		
		sb.append("</BODY>\r\n");
		sb.append("</service>\r\n\n");

		int strlength = 0;
		strlength = new String(sb.toString().getBytes("UTF-8")).length();
		String newString = String.format("%08d", strlength); // 格式化数据,自动补零到8位数字
		String retmes = newString + sb.toString();
		System.out.println("客户端发送数据:"+retmes);
		out.println(retmes);// 发送数据到服务端
		try {
			char[] c = new char[1024 * 5];
			int r = 0;
			StringBuffer sbf = new StringBuffer();
			while ((r = buf.read(c)) != -1) {
				sbf.append(c);
			}
			
//			String str=new String(sbf.toString().getBytes(),"GB18030");
			
			System.out.println("服务端响应数据:" + sbf);
		} catch (SocketTimeoutException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		if (client != null) {
			// 如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭
			client.close(); // 只关闭socket，其关联的输入输出流也会被关闭
		}
	}
}
