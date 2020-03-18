package webservice;

import javax.xml.namespace.QName;

import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 	接口直接测试Web Service
 * @author Administrator
 *
 */
public class TestWebService {
	static Logger log=LogManager.getLogger(TestWebService.class.getName());
	public static void main(String[] args) {
		/*address为wsdl路径?前部分*/
		EndpointReference endpointReference = new EndpointReference("http://localhost:8080/ssh/services/SshWebService");
		try {
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = serviceClient.getOptions();
			options.setTimeOutInMilliSeconds(20000);//20s超时
			options.setTo(endpointReference);//设置接收地址
			
			/**
			 * namespaceURI:targetNamespace路径,从wsdl中得到
			 * localPart:方法名
			 */
			QName qname=new QName("http://webservice", "testWebService2");//调用方法
			String str="客户端调用成功!";//方法的入参
			Object[] param=new Object[] {};
			Class<?> [] type=new Class[] {String.class};//返回值类型
//			Object[] invokeBlocking = serviceClient.invokeBlocking(qname, param, type);//有返回值方法调用
			serviceClient.invokeRobust(qname, param);//无返回值方法调用
//			log.info(invokeBlocking[0]);//输出返回值			
		} catch (AxisFault e) {
			e.printStackTrace();
		}
	}
}
