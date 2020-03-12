package xmlUtil.xml;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class XMLData {
	static Logger logger = LogManager.getLogger(XMLData.class.getName());
	private String str;
	private String root;
	private String SysInfo;
	private String TransId;
	private String LoginCode;
	private String TransDate;
	private String DepCode;
	private String OrderNum;
	private String PhoneNum;
	private String PrintCont;
	
	public static void main(String[] args) {
		String str="asdfrnadsfa\r\nfasdas\r\n";
		logger.info(str);
		logger.info(str.replaceAll("\r\n", "<br>"));
	}

	public String getPrintCont() {
		return PrintCont;
	}

	public void setPrintCont(String printCont) {
		PrintCont = printCont;
	}

	public String getTransDate() {
		return TransDate;
	}

	public void setTransDate(String transDate) {
		TransDate = transDate;
	}

	public String getLoginCode() {
		return LoginCode;
	}

	public void setLoginCode(String loginCode) {
		LoginCode = loginCode;
	}

	public String getTransId() {
		return TransId;
	}

	public void setTransId(String transId) {
		TransId = transId;
	}

	public String getSysInfo() {
		return SysInfo;
	}

	public void setSysInfo(String sysInfo) {
		SysInfo = sysInfo;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getDepCode() {
		return DepCode;
	}

	public void setDepCode(String depCode) {
		DepCode = depCode;
	}

	public String getOrderNum() {
		return OrderNum;
	}

	public void setOrderNum(String orderNum) {
		OrderNum = orderNum;
	}

	public String getPhoneNum() {
		return PhoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		PhoneNum = phoneNum;
	}
}
