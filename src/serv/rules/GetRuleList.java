package serv.rules;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.cert.service.impl.CertSrv;
import com.dj.seal.gaizhangRule.dao.impl.GaiZhangRuleDaoImpl;
import com.dj.seal.gaizhangRule.po.GaiZhangRule;
import com.dj.seal.sealBody.service.impl.SealBodyServiceImpl;
import com.dj.seal.structure.dao.po.Cert;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.util.table.GaiZhangRuleUtil;

public class GetRuleList{
	static Logger logger = LogManager.getLogger(GetRuleList.class.getName());
	public Map<String,GetRules> map=new HashMap<String,GetRules>();
	private SealBodyServiceImpl sealbody_srv;
	private GaiZhangRuleDaoImpl rule_dao;
	private CertSrv cert_srv;
	public Map<String, GetRules> getMap() {
		return map;
	}
	public void setMap(Map<String, GetRules> map) {
		this.map = map;
	}
	public void cleanMap(){
		this.map=null;
	}
	public int GetRuleListP(){
	  try {
			String sql = "select * from " + GaiZhangRuleUtil.TABLE_NAME;
			List<GaiZhangRule> gaizhang = rule_dao.showAllGaiZhangRule(sql);
			for (GaiZhangRule obj : gaizhang) {
				Cert objcert = new Cert();
				SealBody sealbody = sealbody_srv
						.getSealBodyID(obj.getSeal_id());
				if (obj.getUse_cert() == 1) {
					objcert = cert_srv.getObjByNo(obj.getCert_no());
				} else {
					if (sealbody.getKey_sn() != "") {
						objcert = cert_srv.getObjByNo(sealbody.getKey_sn());
					} else {
						return 5;
					}
				}
				GetRules objRules = new GetRules();
				objRules.setRule_no(obj.getRule_no());
				objRules.setSeal_name(sealbody.getSeal_name());
				objRules.setCert_name(objcert.getCert_name());
				objRules.setCert_pwd(objcert.getCert_psd());
				objRules.setRule_desc(obj.getArg_desc());
				objRules.setRule_type(obj.getRule_type());
				map.put(obj.getRule_no(), objRules);
				System.out.println("添加成功");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	public SealBodyServiceImpl getSealbody_srv() {
		return sealbody_srv;
	}
	public void setSealbody_srv(SealBodyServiceImpl sealbody_srv) {
		this.sealbody_srv = sealbody_srv;
	}
	public GaiZhangRuleDaoImpl getRule_dao() {
		return rule_dao;
	}
	public void setRule_dao(GaiZhangRuleDaoImpl rule_dao) {
		this.rule_dao = rule_dao;
	}
	public CertSrv getCert_srv() {
		return cert_srv;
	}
	public void setCert_srv(CertSrv cert_srv) {
		this.cert_srv = cert_srv;
	}

}
