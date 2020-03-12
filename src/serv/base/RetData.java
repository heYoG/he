package serv.base;

public class RetData {
 
	public String RET_CODE;
	public String FILE_NO;
	public String FILE_MSG;
	public String getRET_CODE() {
		return RET_CODE;
	}
	public void setRET_CODE(String ret_code) {
		RET_CODE = ret_code;
	}
	
	public String getFILE_NO() {
		return FILE_NO;
	}
	public void setFILE_NO(String file_no) {
		FILE_NO = file_no;
	}
	public String getFILE_MSG() {
		return FILE_MSG;
	}
	public void setFILE_MSG(String file_msg) {
		FILE_MSG = file_msg;
	}
	
}
