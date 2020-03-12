package xmlUtil.xml;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xmlUtil.aipData.AipTplData;

public class XMLNote {
	static Logger logger = LogManager.getLogger(XMLNote.class.getName());
	private String src_str;
	private String name;
	private String value;
	private List<XMLNote> childs;

	/**
	 * 根据xml返回aip模板支持的键值对形式字符串
	 * 
	 * @param xml
	 * @return
	 */
	public static AipTplData toAipTplData(XMLNote xml, String t) {
		return toAipTplData(xml, t, true);
	}
	public static AipTplData toAipTplData2(XMLNote xml, boolean hasP) {
		return toAipTplData2(xml, "", hasP);
	}
	public static AipTplData toAipTplData2(XMLNote xml, String t, boolean hasP) {
		if (!hasP) {
			t = "";
		} else {
			t = "".equals(t) ? "" : t + ">";
		}
		AipTplData ad = new AipTplData();
		if (xml.getChilds() == null) {
			String str = myTrimContext(xml.getValue());
			ad.add(t + xml.getName(), str);
			return ad;
		} else {
			for (XMLNote note : xml.getChilds()) {
				ad.add(XMLNote.toAipTplData2(note, t + xml.getName(), hasP));
			}
		}
		return ad;
	}
	public static AipTplData toAipTplData(XMLNote xml, String t, boolean hasP) {
		if (!hasP) {
			t = "";
		} else {
			t = "".equals(t) ? "" : t + ">";
		}
		AipTplData ad = new AipTplData();
		if (xml.getChilds() == null) {
			String str = myTrimContext(xml.getValue());
			ad.add(t + xml.getName(), str);
			return ad;
		} else {
			for (XMLNote note : xml.getChilds()) {
				ad.add(XMLNote.toAipTplData(note, t + xml.getName(), hasP));
			}
		}
		return ad;
	}

	public static AipTplData toAipTplData(XMLNote xml) {
		return toAipTplData(xml, "", true);
	}

	public static AipTplData toAipTplData(XMLNote xml, boolean hasP) {
		return toAipTplData(xml, "", hasP);
	}

	public String toString(int i) {
		String blank = "";
		for (int j = 0; j < i; j++) {
			blank += "  ";
		}
		StringBuffer sb = new StringBuffer();
		if (value != null) {
			return "\r\n" + blank + " " + name + ":" + value;
		} else {
			sb.append("\r\n").append(blank).append("-").append(name);
			for (XMLNote note : childs) {
				sb.append(note.toString(i + 1));
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) throws Exception {
		StringBuffer xmlStr = new StringBuffer();
		xmlStr.append("<?xml version=\"1.0\" encoding=\"GBK\"?>");
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

		// String xml2 = "<root><a><ccc>CCCCC</ccc></a><bb>BBB</bb></root>";

		// toNote(xmlStr.toString());

		String test = "<returnData><errorCode>0</errorCode><errorMsg></errorMsg><batch><objType>1</objType><uniqueCode>103</uniqueCode><operType>1</operType><dept><uniCode>103</uniCode><fullName>合作方</fullName><deptName>合作方</deptName><isCorp>0</isCorp><showNum>0</showNum><deptParent><uniCode>1</uniCode></deptParent><deptCorp><uniCode>1</uniCode></deptCorp></dept></batch></returnData>";
		// logger.info(toNote(xmlStr.toString()).toString(0));
		logger.info(toNote(test).toString(0));
		logger.info((XMLNote.class.getClassLoader().getResource("")));
	}

	public static String noHead(String xmlStr)
			throws StringIndexOutOfBoundsException {
		String head = xmlStr.substring(xmlStr.indexOf("<?"), xmlStr
				.indexOf("?>") + 2);
		return xmlStr.substring(xmlStr.indexOf(head) + head.length());
	}

	public static XMLNote toNote(String xmlStr) throws Exception {
		if (xmlStr == null) {
			return null;
		}
		XMLNote obj = new XMLNote();
		obj.setSrc_str(xmlStr);
		xmlStr = myTrim(xmlStr);
		if (!xmlStr.startsWith("<")) {
			throw new Exception("错误的xml格式，请检查!");
		}
		String name = xmlStr.substring(xmlStr.indexOf("<") + 1, xmlStr
					.indexOf(">"));// 找到第一个标签名
		obj.setName(name);// 设置name
		if (name.charAt(name.length() - 1) == '/') {
			obj.setName(name.substring(0, name.length() - 1));
			obj.setValue("");
		} else {
			String begin = "<" + name + ">";// 起始标签
			String end = "</" + name + ">";// 结束标签
			String str = "";
			try {
				try{
					str = xmlStr.substring(xmlStr.indexOf(begin) + begin.length(),
							xmlStr.indexOf(end));
				}catch(Exception e){
					throw new Exception(name+":错误的xml格式，请检查!");
				}	
			} catch (Exception e) {
			  e.printStackTrace();
			  if(name.indexOf("<")!=-1){
				int no=name.indexOf("<");
				throw new Exception(name.substring(0, no)+":错误的xml格式，请检查!");
			  }else{
	
				throw new Exception(name+":错误的xml格式，请检查!");
			  }
			}

			str = myTrim(str);
			if ("".equals(str)) {
				obj.setValue(str);
			} else if ('<' != str.charAt(0)) {
				if (isSubXml(str)) {
					throw new Exception("错误的xml格式，请检查!");
				}
				if (str.indexOf("\r\n") != -1) {
					str = str.replaceAll("\r\n", "<br>");
				}
				obj.setValue(str);
			} else {
				if (!isSubXml(str)) {
					obj.setValue(str);
				} else {
					
					obj.setChilds(toListNote(str));
				}
			}
		}
		return obj;
	}

	private static boolean isSubXml(String xmlStr) throws Exception {
		try {
			String name = xmlStr.substring(xmlStr.indexOf("<") + 1, xmlStr
					.indexOf(">"));// 找到第一个标签名
			if (name.indexOf("<") != -1) {
				return false;
			} else if (name.charAt(name.length() - 1) == '/') {
				return true;
			}
		   // try{
		    	String begin = "<" + name + ">";// 起始标签
				String end = "</" + name + ">";// 结束标签
				xmlStr.substring(xmlStr.indexOf(begin) + begin.length(), xmlStr
						.indexOf(end));
//		    }catch(Exception e){
//		    	throw new Exception(name+":错误的xml格式，请检查!");
//		    }
		} catch (StringIndexOutOfBoundsException e) {
			return false;
		}
		return true;
	}

	private static String myTrim(String str) throws Exception {
		String tem = str;
		while (tem.startsWith(" ") || tem.startsWith("\r")
				|| tem.startsWith("\n") || tem.startsWith("\t")
				|| tem.startsWith("<!--")) {
			if (tem.startsWith("<!--")) {
				int end = tem.indexOf("-->") + 3;
				if (end <= 6) {
					throw new Exception("错误的xml格式，注释有错，请检查!");
				}
				tem = tem.substring(end);
			}  
			else {
				tem = tem.substring(1);
				
			}
		}
		if (tem.startsWith("<") || "".equals(tem)) {
			return tem;
		}
		return str;
	}
	private static List<XMLNote> toListNote(String xmlStr) throws Exception {
		List<XMLNote> list = new ArrayList<XMLNote>();
		while (!"".equals(xmlStr)) {
			if (!xmlStr.startsWith("<")) {
				throw new Exception("错误的xml格式，请检查!");
			}
			String name = xmlStr.substring(xmlStr.indexOf("<") + 1, xmlStr
					.indexOf(">"));
			String begin = "<" + name + ">";
			String end = "</" + name + ">";
			String str = xmlStr.substring(xmlStr.indexOf(begin), xmlStr
					.indexOf(end)
					+ end.length());
			XMLNote obj = toNote(str);
			list.add(obj);
			xmlStr = xmlStr.substring(xmlStr.indexOf(end) + end.length());
			xmlStr = myTrim(xmlStr);	
		}
		return list;
	}
	public String getValue(String str) {
		String name = (str.indexOf(".") == -1) ? str : str.substring(0, str
				.indexOf("."));
		XMLNote obj = getByName(name);
		if (name.equals(this.getName())) {
			obj = this;
		}
		if (obj == null) {
			return null;
		} else {
			if (null == obj.getValue()) {
				if (str.indexOf(".") == -1) {
					return null;
				}
				return obj.getValue(str.substring(str.indexOf(".") + 1));
			} else {
				return myTrimContext(obj.getValue());
			}
		}
	}

	public int numOfChilds(String name) {
		int sum = 0;
		for (XMLNote note : childs) {
			logger.info(note.name);
			if (name.equals(note.name)) {
				sum++;
			}
		}
		return sum;
	}

	public XMLNote getByName(String name) {
		int i = 0;
		int begin = name.indexOf("[");
		if (begin != -1) {
			int end = name.indexOf("]");
			i = Integer.parseInt(name.substring(begin + 1, end));
			name = name.substring(0, begin);
		}
		int k = 0;
		if (childs == null) {
			return null;
		}
		for (int j = 0; j < childs.size(); j++) {
			if (name.equals(childs.get(j).name)) {
				if (k == i) {
					return childs.get(j);
				}
				k++;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<XMLNote> getChilds() {
		return childs;
	}

	public void setChilds(List<XMLNote> childs) {
		this.childs = childs;
	}

	public String getValue() {
		return value;
	}

	public int countOfChild(String name) {
		int num = 0;
		if (this.getChilds() == null) {
			return 0;
		}
		for (XMLNote xml : this.getChilds()) {
			if (name.equals(xml.getName())) {
				num++;
			}
		}
		return num;
	}
	public int countOfChild2(String name) {
		int num = 0;
		if (this.getChilds() == null) {
			return 0;
		}
		for (XMLNote xml : this.getChilds()) {
			if (name.equals(xml.getName())) {
				num++;
			}
		}
		return num;
	}
	public String[] StringChild2(String name,int n) {
		int num = 0;
		String str[]=new String[n];
		if (this.getChilds() == null) {
			return null;
		}
		for (XMLNote xml : this.getChilds()) {
			if (name.equals(xml.getName())) {
                logger.info("xml.getName()---"+xml.getName());			
				String value=xml.getValue(xml.getName());
                str[num]=value;
                logger.info("value---"+str[num]);
				num++;
			}
		}
		return str;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String getSrc_str() {
		return src_str;
	}

	public void setSrc_str(String src_str) {
		this.src_str = src_str;
	}

	public static String myTrimContext(String str) {
		str = str.replaceAll("<br>", "");
		str = str.trim();
		return str;
	}
}
