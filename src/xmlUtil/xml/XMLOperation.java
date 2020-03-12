package xmlUtil.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XMLOperation {
	static Logger logger = LogManager.getLogger(XMLOperation.class.getName());
	private static String xml;

	public static XMLNote toNote(String xmlStr) throws Exception {
		xmlStr = xmlStr.substring(xmlStr.indexOf("?>") + 2);
		return XMLNote.toNote(xmlStr);
	}

	public static XMLNote tempNote() throws Exception {
		StringBuffer xmlStr = new StringBuffer();
		xmlStr.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		xmlStr
				.append("<root><SysInfo><TransId>1110042655</TransId><LoginCode>ZHANGCHUN</LoginCode><TransDate>20101122132250</TransDate><DepCode>测试客户-1-12342-5</DepCode><OrderNum>1-995649213</OrderNum><PhoneNum>13901000022</PhoneNum></SysInfo><PrintCont><TitleInfo>2010|11|22|13|28|13901000022|110111199904151111|13901000022|ZHANGCHUN|□证件办理|1000042655|尊敬的13901000022客户：");

		xmlStr
				.append("\r\n您好！您办理的是变更业务|</TitleInfo><ContInfo>尊敬的13901000022客户：");

		xmlStr.append("\r\n\r\n\r\n您好！您办理的是变更业务");

		xmlStr.append("\r\n\r\n订单编号/操作流水号:1-99989213");

		xmlStr.append("\r\n\r\n手机号码：13901000022  SIM卡号：9001971992762");

		xmlStr.append("\r\n\r\n开通：");

		xmlStr.append("\r\n\r\n测试号码专用套餐");

		xmlStr
				.append("\r\n证件办理业务，人工校验通过：13901000022</ContInfo><TemplateType>4</TemplateType></PrintCont></root>");

		return toNote(xmlStr.toString());
	}

	public static XMLData toData(String xmlStr) {
		xml = xmlStr;
		XMLData obj = new XMLData();
		String head = xmlStr.substring(xmlStr.indexOf("<?"), xmlStr
				.indexOf("?>") + 2);
		obj.setStr(head);
		obj.setRoot(getNote("root"));
		obj.setSysInfo(getNote("SysInfo"));
		obj.setTransId(getNote("TransId"));
		obj.setLoginCode(getNote("LoginCode"));
		// logger.info(obj.getRoot());
		return obj;
	}

	private static String getNote(String note) {
		String begin = "<" + note + ">";
		String end = "</" + note + ">";
		String ret = xml.substring(xml.indexOf(begin), xml.indexOf(end)
				+ end.length());
		return ret;
	}

	public static void main(String[] args) throws Exception {
		StringBuffer xmlStr = new StringBuffer();
		// xmlStr.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
		xmlStr
				.append("<root><SysInfo><TransId>1110042655</TransId><LoginCode>ZHANGCHUN</LoginCode><TransDate>20101122132250</TransDate><DepCode>测试客户-1-12342-5</DepCode><OrderNum>1-995649213</OrderNum><PhoneNum>13901000022</PhoneNum></SysInfo><PrintCont><TitleInfo>2010|11|22|13|28|13901000022|110111199904151111|13901000022|ZHANGCHUN|□证件办理|1000042655|尊敬的13901000022客户：");

		xmlStr
				.append("\r\n您好！您办理的是变更业务|</TitleInfo><ContInfo>尊敬的13901000022客户：");

		xmlStr.append("\r\n您好！您办理的是变更业务");

		xmlStr.append("\r\n订单编号/操作流水号:1-99989213");

		xmlStr.append("\r\n手机号码：13901000022  SIM卡号：9001971992762");

		xmlStr.append("\r\n开通：");

		xmlStr.append("\r\n测试号码专用套餐");

		xmlStr
				.append("\r\n证件办理业务，人工校验通过：13901000022</ContInfo><TemplateType>4</TemplateType></PrintCont></root>");

		XMLNote obj = XMLNote.toNote(xmlStr.toString());
		// XMLNote obj = XMLNote.toNote(null);
		String str = "PrintCont.TitleInfo";
		logger.info(obj.getValue(str));
	}

	public static String getXml() {
		return xml;
	}

	public static void setXml(String xml) {
		XMLOperation.xml = xml;
	}

}
