package autoUtil;

import serv.SealServiceStub;
import serv.WebSignServiceStub;

public class AutoClient {

	private String str = "/Seal/services/SealService.SealServiceHttpSoap12Endpoint/";
	private String str2 = "/Seal/services/WebSignService.WebSignServiceHttpSoap12Endpoint/";
	private String ip;
	private String port;

	public String getStr() {
		return str;
	}

	public void setStr(String str) {
		this.str = str;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public WebSignServiceStub getObjWebSign() throws Exception {
		String ws_url = "http://" + ip + ":" + port + str2;
		WebSignServiceStub obj = new WebSignServiceStub(ws_url);
		//System.out.println("超时："+obj._getServiceClient().getOptions().getTimeOutInMilliSeconds());
		obj._getServiceClient().getOptions().setTimeOutInMilliSeconds(1800000);//30分 1000毫秒=1秒
		//System.out.println("超时："+obj._getServiceClient().getOptions().getTimeOutInMilliSeconds());
		return obj;
	}
	
	public SealServiceStub getObj() throws Exception {
		String ws_url = "http://" + ip + ":" + port + str;
		SealServiceStub obj = new SealServiceStub(ws_url);
		//System.out.println("超时："+obj._getServiceClient().getOptions().getTimeOutInMilliSeconds());
		obj._getServiceClient().getOptions().setTimeOutInMilliSeconds(1800000);//30分 1000毫秒=1秒
		//System.out.println("超时："+obj._getServiceClient().getOptions().getTimeOutInMilliSeconds());
		return obj;
	}
	/**
	 * 模板合成
	 * 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public String modelAutoMerger(String xmlStr) throws Exception {
		SealServiceStub.ModelAutoMerger obj = new SealServiceStub.ModelAutoMerger();
		obj.setXmlStr(xmlStr);
		return getObj().modelAutoMerger(obj).get_return();
	}
	/**
	 * 自动盖章
	 * 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public String sealAutoPdf(String xmlStr) throws Exception {
		SealServiceStub.SealAutoPdf obj = new SealServiceStub.SealAutoPdf();
		obj.setXmlStr(xmlStr);
		return getObj().sealAutoPdf(obj).get_return();
	}
	/**
	 * pdf验证
	 * 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public String pdfVarify(String xmlStr) throws Exception {
		SealServiceStub.PdfVarify obj = new SealServiceStub.PdfVarify();
		obj.setXmlStr(xmlStr);
		return getObj().pdfVarify(obj).get_return();
	}
	
	/**
	 * html5文档转换
	 * 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public String html5syn(String xmlStr)throws Exception {
		WebSignServiceStub.UploadAndSynFile obj = new WebSignServiceStub.UploadAndSynFile();
		System.out.println(xmlStr);
		obj.setXmlStr(xmlStr);
		return getObjWebSign().UploadAndSynFile(obj).get_return();
	}
	
	/**
	 * html5文档保存
	 * 
	 * @param xmlStr
	 * @return
	 * @throws Exception
	 */
	public String html5save(String xmlStr)throws Exception {
		WebSignServiceStub.SaveFile obj = new WebSignServiceStub.SaveFile();
		System.out.println(xmlStr);
		obj.setXmlStr(xmlStr);
		return getObjWebSign().SaveFile(obj).get_return();
	}
}
