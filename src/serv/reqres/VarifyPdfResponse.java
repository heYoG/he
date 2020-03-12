package serv.reqres;

import xmlUtil.xml.XMLUtil;
import serv.base.RetData;

public class VarifyPdfResponse {

	public RetData RetData;
	
	@Override
	public String toString(){
		try {
			return XMLUtil.obj2xml(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public RetData getRetData() {
		return RetData;
	}

	public void setRetData(RetData retData) {
		RetData = retData;
	}
}
