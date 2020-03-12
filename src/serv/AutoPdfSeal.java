package serv;

import java.io.UnsupportedEncodingException;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import xmlUtil.xml.XMLNote;

import com.dj.hotelApi.InterfaceClass;
import com.dj.seal.accumulationFund.service.impl.AccumulationDealImpl;
import com.dj.seal.structure.dao.po.SysUser;
import com.dj.seal.util.License;
import com.dj.seal.util.properyUtil.DJPropertyUtil;
import com.dj.seal.util.socket.getValcode.GetData_mobileAndwechatBank;
import com.dj.seal.util.socket.sealPicture.CompositeEntrance;
import com.dj.seal.util.socket.sealPictureOfCheckAccountSys.EleInternationalAccountResponse;
import com.dj.seal.util.socket.socketImpl.Compostion_res;
import com.dj.seal.util.socket.socketImpl.GetTradeData_res;
import com.dj.seal.util.socket.socketImpl.PrintSeal_res;
import com.dj.seal.util.socket.trusteeship.TrusteeshipGetData;

public class AutoPdfSeal {
	static Logger logger = LogManager.getLogger(AutoPdfSeal.class.getName());
	private static String errormsg;

	public String getErrormsg() {
		return errormsg;
	}

	public void setErrormsg(String errormsg) {
		AutoPdfSeal.errormsg = errormsg;
	}

	// 得到客户端ip
	public String getClientIp() {
		MessageContext mc = MessageContext.getCurrentMessageContext();
		if (mc == null) {
			return null;
		}
		HttpServletRequest request = (HttpServletRequest) mc
				.getProperty(HTTPConstants.MC_HTTP_SERVLETREQUEST);
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		logger.info("ip:" + ip);
		return ip;
	}

	/**
	 * android发送数据，服务端模板合成数据
	 * 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public String arAutoMerger(String xmlStr) throws Exception {
		String s = MyOper.arMakeFiles(xmlStr);
		return s;
	}

	/**
	 * aip转换pdf
	 * 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public String aipAutoPdf(String xmlStr) throws Exception {
		String s = MyOper.aipPdf(xmlStr);
		return s;
	}

	/**
	 * 模板数据自动合成
	 * 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public String modelAutoMerger(String xmlStr) throws Exception {
		logger.info("---------------");
		XMLNote xml = null;
		String ad = License.getAbleDate();
		if (ad.equals("1")) {
			return MyXml.modelMergerXml("1", "对不起，系统已过有效期，请联系管理员！");
		}
		try {
			xml = XMLNote.toNote(XMLNote.noHead(xmlStr));
		} catch (Exception e1) {
			e1.printStackTrace();
			return MyXml.modelMergerXml("1", "XML格式错误");
		}
		try {
			int p = MyXml.isSysIP(xml, getClientIp());// 客户端请求ip是否在应用系统内
			if (p == 1) {
				return MyXml.modelMergerXml("1", "IP:" + getClientIp()
						+ "不在应用系统内！");
			}
			String u = MyXml.isUserAllow(xml);// 检查基础信息是否正确
			if (u != "信息完整") {
				return MyXml.modelMergerXml("1", u);
			}
			String is_merger = MyXml.isMerger(xml);// 判断模板是否合并
			String s = "";// 返回的响应结果
			if ("合".equals(is_merger)) {
				try {
					s = MyOper.makeMergerFile(xml);// 生成合并后的文档
				} catch (Exception e) {
					// logger.error(e.getMessage());
					return MyXml.modelMergerXml("1", e.toString());
				}
			} else {
				try {
					s = MyOper.makeFiles(xml);// 生成不合并的文档
				} catch (Exception e) {
					// logger.error(e.getMessage());
					return MyXml.modelMergerXml("1", s);
				}
			}
			return MyXml.modelMergerXml("0", s);
		} catch (Exception e) {
			return MyXml.modelMergerXml("1", e.toString());
		}
	}

	/**
	 * 文档自动盖章
	 * 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public String sealAutoPdf(String xmlStr) throws Exception {

		return MyOper.haiGuanSealPdf(xmlStr, getClientIp());

		// logger.info("sealAutoPdf接收到的XML：");
		// logger.info(xmlStr);
		// String is_merger="";
		// XMLNote xml = null;
		// try {
		// xml = XMLNote.toNote(XMLNote.noHead(xmlStr));
		// } catch (Exception e1) {
		// // e1.printStackTrace();
		// return MyXml.sealPdfXml(is_merger, xml, getClientIp(), "1",
		// "XML格式错误:" + e1.toString());
		// }
		// is_merger = MyXml.isMerger(xml);// 判断模板或者PDF是否合并
		// String ad = License.getAbleDate();
		// if (ad .equals("1")) {
		// return MyXml.sealPdfXml(is_merger, xml, getClientIp(), "1",
		// "对不起，系统已过有效期，请联系管理员！");
		// }
		// try {
		// // int p = MyXml.isSysIP(xml, getClientIp());// 客户端请求ip是否在应用系统内
		// // if (p == 1) {
		// // return MyXml.sealPdfXml(is_merger, xml, getClientIp(), "1",
		// // "IP:" + getClientIp() + "不在应用系统内！");
		// // }
		// // String z=MyXml.isInfoAble(xml);//检查报文节点信息是否正确
		// // if(z!="信息正确"){
		// // return MyXml.sealPdfXml(is_merger, xml, getClientIp(), "1", z);
		// // }
		// // String u = MyXml.isUserAllow(xml);// 检查基础信息是否正确
		// // if (u != "信息完整") {
		// // return MyXml.sealPdfXml(is_merger, xml, getClientIp(), "1", u);
		// // }
		// String s = "";
		// if ("合".equals(is_merger)) {
		// try {
		// s = MyOper.makeMergerFile(xml);// 生成合并后的文档
		// if(!s.equals("成功")){
		// return MyXml.sealPdfXml(is_merger, xml,
		// getClientIp(), "1", s);
		// }
		// } catch (Exception e) {
		// // logger.error(e.getMessage());
		// return MyXml.sealPdfXml(is_merger, xml, getClientIp(), "1",
		// e.toString());
		// }
		// String is_coderbar = MyXml.isCODEBAR(is_merger, xml);// 是否加盖二维码
		// if (is_coderbar .equals("1")) {
		// try {
		// String ewm = MyOper.addSealCODEBAR(is_merger, xml);
		// if (ewm != "加盖二维码成功") {
		// return MyXml.sealPdfXml(is_merger, xml,
		// getClientIp(), "1", ewm);
		// }
		// } catch (Exception e) {
		// return MyXml.sealPdfXml(is_merger, xml, getClientIp(),
		// "1", e.toString());
		// }
		// }
		// try {
		// s = MyOper.addSeal(is_merger, xml, getClientIp());// pdf盖章
		// if (s.equals("盖章成功")) {
		// return MyXml.sealPdfXml(is_merger, xml, getClientIp(),
		// "0", s);
		// } else {
		// if (s.indexOf("5") != -1) {
		// return MyXml.sealPdfXml(is_merger, xml,
		// getClientIp(), "1", "规则，印章没有和证书关联，请检查！");
		// }
		// }
		// } catch (Exception e) {
		// return MyXml.sealPdfXml(is_merger, xml, getClientIp(), "1",
		// e.toString());
		// }
		// } else {
		// try {
		// s = MyOper.makeFiles(xml);// 生成不合并的文档
		// if(!s.equals("成功")){
		// return MyXml.sealPdfXml(is_merger, xml,
		// getClientIp(), "1", s);
		// }
		// } catch (Exception e) {
		// return MyXml.sealPdfXml(is_merger, xml, getClientIp(), "1",
		// s);
		// }
		// String is_coderbar = MyXml.isCODEBAR(is_merger, xml);// 是否加盖二维码
		// if (is_coderbar.equals("1")) {
		// try {
		// String ewm = MyOper.addSealCODEBAR(is_merger, xml);
		// if (!ewm.equals("加盖二维码成功")) {
		// return MyXml.sealPdfXml(is_merger, xml,
		// getClientIp(), "1", ewm);
		// }
		// } catch (Exception e) {
		// return MyXml.sealPdfXml(is_merger, xml, getClientIp(),
		// "1", e.toString());
		// }
		// }
		// try {
		// s = MyOper.addSeal(is_merger, xml, getClientIp());// pdf盖章
		// if (s.equals("盖章成功")) {
		// return MyXml.sealPdfXml(is_merger, xml, getClientIp(),
		// "0", s);
		// } else {
		// if (s.indexOf("5") != -1) {
		// return MyXml.sealPdfXml(is_merger, xml,
		// getClientIp(), "1", "规则，印章没有和证书关联，请检查！");
		// }
		// }
		// } catch (Exception e) {
		// return MyXml.sealPdfXml(is_merger, xml, getClientIp(), "1",
		// e.toString());
		// }
		// }
		// return MyXml.sealPdfXml(is_merger, xml, getClientIp(), "1", s);
		// } catch (Exception e) {
		// return MyXml.sealPdfXml(is_merger, xml, getClientIp(), "1", e
		// .toString());
		// }
	}

	/**
	 * 文档验证接口
	 * 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public String pdfVarify(String xmlStr) throws Exception {
		String ad = License.getAbleDate();
		if (ad.equals("1")) {
			return MyXml.varityPdfXml("1", "对不起，系统已过有效期，请联系管理员！");
		}
		XMLNote xml = null;
		try {
			xml = XMLNote.toNote(XMLNote.noHead(xmlStr));
		} catch (Exception e1) {
			e1.printStackTrace();
			return MyXml.varityPdfXml("1", "XML格式错误");
		}
		try {
			int p = MyXml.isSysIP(xml, getClientIp());// 客户端请求ip是否在应用系统内
			if (p == 1) {
				return MyXml.varityPdfXml("1", "IP:" + getClientIp()
						+ "不在应用系统内！");
			}
			String u = MyXml.isUserAllow(xml);// 检查基础信息是否正确
			if (u.equals("信息完整")) {
				return MyXml.varityPdfXml("1", u);
			}
			String s = "";
			try {
				s = MyOper.pdfVarity(xml);// 文档验证
			} catch (Exception e) {
				return MyXml.varityPdfXml("1", e.toString());
			}
			if (s.indexOf("验证通过") != -1) {
				return MyXml.varityPdfXml("0", s);
			} else {
				return MyXml.varityPdfXml("1", s);
			}
		} catch (Exception e) {
			return MyXml.varityPdfXml("1", e.toString());
		}
	}
	
	/**
	 * 打印、查询接口
	 * @param xmlData 接收报文
	 * @return 返回报文
	 */
	public static String compostion_Response(String xmlData) {
		System.out.println(Compostion_res.compostion_Response(xmlData));
		return Compostion_res.compostion_Response(xmlData);
	}
	
	/**
	 *最高院盖章 
	 * @param xmlData 接收报文
	 * @param ClientIp 客户端ip
	 * @return 返回信息
	 * @throws Exception
	 */
	
	public String haiGuanAddSeal(String xmlData,String ClientIp) throws Exception{
		String response = MyOper.haiGuanSealPdf(xmlData, ClientIp);
		return response;
	}
	
	/**
	 * 获取模板
	 * @param userId
	 * @return
	 */
	public static String getModels(String userId){
		return InterfaceClass.adLoginVerfy(userId,"127.0.0.1","00:50:55:99:w3:as");
	}
	
	/**
	 * 电子对账系统
	 * @param xmlData
	 * @return
	 */
	public String getSealPic(String xmlData){
		if(xmlData.length()<17){
			logger.info("F5探测报文:"+xmlData);
			return "1";
		}
		String response = CompositeEntrance.ComEntrance(xmlData.trim());//含印章图片base64值的报文
//		String response = Compostion_res.compostion_Response(xmlData.trim());// 执行自定义请求解析方法，生成相应的response
		String newString = null;
		try {
			newString = String.format("%08d",response.getBytes("UTF-8").length) + response;
			//logger.info("返回客户端报文(含长度头)：" + newString);
			logger.info("S_length:" + newString.length());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return newString;
	}
	
	/**
	 * 公积金回单盖章
	 */
	public int addSealToAccumulation(){
		return new AccumulationDealImpl().addSealToAccumulation();
	}
}
