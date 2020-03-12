package serv;

import java.io.File;

import srvSeal.SrvSealUtil;

import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.structure.dao.po.Cert;
import com.dj.seal.util.Constants;
import com.dj.seal.util.ceUtil.TransCePo;
import com.dj.seal.util.encrypt.DesUtils;
import com.dj.seal.util.properyUtil.DJPropertyUtil;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class VarifyAddSeal {
	
	private static String saveBasePath = Constants.getProperty("savepdf");
		
	private static String bpath() {
		String bpath = "";
		bpath = Constants.basePath;
		try {
			String is_type=Constants.getProperty("is_type");
			if(is_type.equals("1")){//从配置文件读取路径 
				bpath=Constants.getProperty("save_path");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bpath;
	}
	
	private static Object getBean(String bean_name) {
		if (MyApplicationContextUtil.getContext() == null) {
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}
	
	public int varifyAddSeal() throws Exception{
		int addSealResult = 1;
		SrvSealUtil srv_seal = new SrvSealUtil();
		int nLoginType = 2;
		String ruleInfo = "AUTO_ADD:0,1,-5000,0,255,签名确认)|(8,";
		String filePath = DJPropertyUtil.getPropertyValue("savepdf")+File.separatorChar+"varify.pdf";
		String imageSavePath = DJPropertyUtil.getPropertyValue("savepdf")+File.separatorChar+"varify.jpg";
		String fileSavePath = DJPropertyUtil.getPropertyValue("savepdf")+File.separatorChar+"addSeal.pdf";
		String certName = "";
		String sealName = "varify";
		String certPwd = "";
		int imgRet = 0;
		int fileRet = 0;
		int ret = 0;
		TransCePo cpo = null;
		CertSrv certsrv = (CertSrv) getBean("CertService");
		String maxNo = certsrv.getCertMaxNo();
		Cert cert = certsrv.getObjByNo((Integer.parseInt(maxNo)-1)+"");
		if(cert == null){
			addSealResult=-4;
		}
		certName = cert.getCert_name();
		try {
			DesUtils des = new DesUtils();
			certPwd = des.decrypt(cert.getCert_psd());
		} catch (Exception e) {
			e.printStackTrace();
		}// 自定义密钥
		int nObjID = srv_seal.openObj(filePath, 0, 0);
		if(System.getProperty("os.name").toUpperCase().indexOf("WINDOWS")!=-1){
			int login = srv_seal.login(nObjID, nLoginType, "nsh", "1");
			ret = MyOper.addSealdesc(srv_seal, nObjID, ruleInfo, certName, sealName);                     
			if(ret == 1){
				imgRet = srv_seal.getPageImg(nObjID,0,1280,imageSavePath,"jpg");
				fileRet = srv_seal.saveFile(nObjID, fileSavePath,"pdf",0);
			}else{
				//关闭文档
				addSealResult = -2;
				srv_seal.saveFile(nObjID,"","",0);
			}
		}else{
			cpo=new TransCePo();
			ret = MyOper.addSealdescLinux(srv_seal, nObjID, fileSavePath, ruleInfo, certName, certPwd, sealName,cpo);
			if(ret == 1){
				nObjID = srv_seal.openObj(fileSavePath,0,0);
				imgRet = srv_seal.getPageImg(nObjID,0,300,"jpg",imageSavePath);
				int s = srv_seal.saveFile(nObjID, "","",0);
				if(s == 1){
				}else{
					addSealResult = -3;
				}
			}else{
				//关闭文档
				srv_seal.saveFile(nObjID,"","",0);
				addSealResult = -2;//文档无法关闭
			}
		}
		return addSealResult;
	}

}
