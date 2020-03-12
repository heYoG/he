package com.dj.seal.cert.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import oracle.sql.CLOB;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.cert.util.CertType;
import com.dj.seal.cert.web.form.CertForm;
import com.dj.seal.organise.service.api.IUserService;
import com.dj.seal.organise.vo.UserCertVo;
import com.dj.seal.sealBody.dao.api.ISealBodyDao;
import com.dj.seal.structure.dao.po.Cert;
import com.dj.seal.structure.dao.po.SealBody;
import com.dj.seal.structure.dao.po.UserCert;
import com.dj.seal.util.Constants;
import com.dj.seal.util.CreateTableForOracle;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DBTypeUtil;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.dao.PageSplit;
import com.dj.seal.util.service.SearchUtil;
import com.dj.seal.util.table.CertUtil;
import com.dj.seal.util.table.SealBodyUtil;
import com.dj.seal.util.table.SysUserUtil;
import com.dj.seal.util.table.UserCertUtil;
import com.dj.seal.util.web.SearchForm;
import com.dj.sign.Base64;

public class CertSrv {
	
	static Logger logger = LogManager.getLogger();
	
	private BaseDAOJDBC dao;
	private ISealBodyDao seal_dao;
	private IUserService user_srv;
	

	public IUserService getUser_srv() {
		return user_srv;
	}

	public void setUser_srv(IUserService user_srv) {
		this.user_srv = user_srv;
	}

	public ISealBodyDao getSeal_dao() {
		return seal_dao;
	}

	public void setSeal_dao(ISealBodyDao seal_dao) {
		this.seal_dao = seal_dao;
	}

	public boolean isExist(String name) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(CertUtil.TABLE_NAME);
		sb.append(" where ").append(CertUtil.CERT_NAME);
		sb.append("='").append(name).append("'");
		int num = dao.count(sb.toString());
		if (num == 0) {
			return false;
		}
		return true;
	}

	public boolean isCertExist(String cert) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ").append(CertUtil.TABLE_NAME);
		sb.append(" where ").append(CertUtil.CERT_DETAIL);
		sb.append("='").append(cert).append("'");
		int num = dao.count(sb.toString());
		if (num == 0) {
			return false;
		}
		return true;
	}

	public String getSignCertStr() throws Exception {
		return Constants.getProperty("sign_cert_str");
	}
  
	public void addCert(Cert obj) throws Exception {
		if(obj.getCert_no()==null||obj.getCert_no().equals("")){
			obj.setCert_no(dao.getNo(CertUtil.TABLE_NAME, CertUtil.CERT_NO));
		}		
		if (obj.getBegin_time() == null) {
			obj.setBegin_time(new Timestamp(new Date().getTime()));
		}
		if (obj.getEnd_time() == null) {
			obj.setEnd_time(new Timestamp(new Date().getTime()));
		}
		obj.setReg_time(new Timestamp(new Date().getTime()));
		obj.setCert_status(Constants.UCERT_STATE);// 默认证书是可用的
		//dao.add(ConstructSql.composeInsertSql(obj, new CertUtil()));
		saveCert(obj);
	}

	public void updCert(Cert obj) throws Exception {
		// obj.setCert_no(dao.getNo(CertUtil.TABLE_NAME, CertUtil.CERT_NO));
		if (obj.getBegin_time() == null) {
			obj.setBegin_time(new Timestamp(new Date().getTime()));
		}
		if (obj.getEnd_time() == null) {
			obj.setEnd_time(new Timestamp(new Date().getTime()));
		}
		String parasStr = CertUtil.CERT_NO + "='" + obj.getCert_no() + "'";
		dao
				.update(ConstructSql.composeUpdateSql(obj, new CertUtil(),
						parasStr));
	}

	public void addCert(CertForm f) throws Exception {
		addCert(fToPo(f));
	}
	
	public void addCertData(CertForm f) throws Exception {
		Cert cert=new Cert();
		BeanUtils.copyProperties(cert, f);
		if (cert.getBegin_time() == null) {
			cert.setBegin_time(new Timestamp(new Date().getTime()));
		}
		if (cert.getEnd_time() == null) {
			cert.setEnd_time(new Timestamp(new Date().getTime()));
		}
		cert.setReg_time(new Timestamp(new Date().getTime()));
		cert.setCert_status(Constants.UCERT_STATE);// 默认证书是可用的
		logger.info("zhi:"+dao.getNo(CertUtil.TABLE_NAME, CertUtil.CERT_NO));
		cert.setCert_no(dao.getNo(CertUtil.TABLE_NAME, CertUtil.CERT_NO));
		//try{
		
	//	}catch(Exception e){
		//	e.printStackTrace();
		//}
		saveCertPath(cert);
	}
	public void dbToFile(Cert obj) throws Exception {
		FileOutputStream fout = new FileOutputStream(obj.getCert_detail());
		StringBuffer sb = new StringBuffer();
		sb.append("select ").append(CertUtil.FILE_CONTENT);
		sb.append(" from ").append(CertUtil.TABLE_NAME);
		sb.append(" where ").append(CertUtil.CERT_NAME);
		sb.append("='").append(obj.getCert_name()).append("'");
		//logger.info("sb:"+sb.toString());
		if(Constants.DB_TYPE==DBTypeUtil.DT_ORCL){
			CLOB clob = dao.getClob(sb.toString(), CertUtil.FILE_CONTENT);
			String certBase64=dao.ClobToString(clob);
			byte b[] = Base64.decode(certBase64);
   		    fout.write(b);
   		    fout.close();
//			byte[] b = blob.getBytes(1, (int) blob.length());
//			fout.write(b, 0, b.length);
//			fout.close();
		}else if(Constants.DB_TYPE==DBTypeUtil.DT_MYSQL){
			String certBase64=dao.getStr(sb.toString(),CertUtil.FILE_CONTENT);
			byte b[] = Base64.decode(certBase64);
   		    fout.write(b);
   		    fout.close();
		}
	
	}
	public void updCert(CertForm f) throws Exception {
		updCert(fToPo(f));
	}

	@SuppressWarnings("unchecked")
	public PageSplit showObjBySch(int pageIndex, int pageSize, SearchForm f)
			throws Exception {
		String str = SearchUtil.certSch(f);
		String sql = DBTypeUtil.splitSql(str, pageIndex, pageSize,
				Constants.DB_TYPE);
		List<CertForm> datas = listObjs(sql);
		PageSplit ps = new PageSplit();
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = dao.count(str);
		ps.setTotalCount(totalCount);
		return ps;
	}
	
	public PageSplit showAllCert(int pageIndex , int pageSize) throws Exception{
		String str = "select * from "+CertUtil.TABLE_NAME +" where "+CertUtil.CERT_SRC+"='server' or "+CertUtil.CERT_SRC+"='sign'";;
		String sql = DBTypeUtil.splitSql(str, pageIndex, pageSize, Constants.DB_TYPE);
		List<CertForm> datas = listObjs(sql);
		PageSplit ps = new PageSplit();
		ps.setDatas(datas);
		ps.setNowPage(pageIndex);
		ps.setPageSize(pageSize);
		int totalCount = dao.count(str);
		ps.setTotalCount(totalCount);
		return ps;
	}
	
	@SuppressWarnings("unchecked")
	private List<CertForm> listObjs(String sql) throws Exception {
		List<CertForm> list_obj = new ArrayList<CertForm>();
		List<Map> list = dao.select(sql);
		for (Map map : list) {
			Cert obj = new Cert();
			obj = (Cert) DaoUtil.setPo(obj, map, new CertUtil());
			CertForm form = new CertForm();
			BeanUtils.copyProperties(form, obj);
			form.setReg_time_str(obj.getReg_time().toString());
			//logger.info(form.getReg_time_str());
//			boolean fla = isSeal(form.getCert_no());
//			if (fla) {
//				form.setIs_Sealbody("1");// 绑定印章
//			} else {
//				form.setIs_Sealbody("2");// 未绑定印章
//			}
//			StringBuffer sb = new StringBuffer();
//			sb.append("select ").append(SealBodyUtil.SEAL_ID);
//			sb.append(" from ").append(SealBodyUtil.TABLE_NAME);
//			sb.append(" where ").append(SealBodyUtil.KEY_SN);
//			sb.append("='").append(form.getCert_no()).append("'");
//			logger.info("sql："+sb.toString());
//			String seal_sel = dao.getStrOfInt(sb.toString(), SealBodyUtil.SEAL_ID);
//			if(seal_sel!=null){
//				Integer seal_num = dao.count(sb.toString());
//				form.setSeal_num(seal_num);
//				form.setSeal_sel(seal_sel);
//				logger.info(obj.getReg_time().toString());
//				form.setReg_time_str(obj.getReg_time().toString());
//			}
			list_obj.add(reset(form));
			
		}
		return list_obj;
	}

	@SuppressWarnings("unchecked")
	public boolean isSeal(String cert_no) throws Exception {
		String sql = "select * from " + SealBodyUtil.TABLE_NAME + " where "
				+ SealBodyUtil.KEY_SN + "='" + cert_no + "'";
		List<Map> list = dao.select(sql);
		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	public CertForm getObj(String no) throws Exception {
		Cert obj = getObjByNo(no);
		CertForm f = new CertForm();
		BeanUtils.copyProperties(f, obj);
		return reset(f);
	}

	@SuppressWarnings("unchecked")
	public Cert getObjByNo(String no) throws Exception {
		Cert obj = new Cert();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(CertUtil.TABLE_NAME);
		sb.append(" where ");
		sb.append(CertUtil.CERT_NO).append("='");
		sb.append(no).append("'");
		List list = dao.select(sb.toString());// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			obj = (Cert) DaoUtil.setPo(obj, map, new CertUtil());
			return obj;
		} else {
			return null;
		}
	}

	public String getCertMaxNo() throws Exception{
		return dao.getNo(CertUtil.TABLE_NAME, CertUtil.CERT_NO);
	}
	@SuppressWarnings("unchecked")
	public Cert getObjByName(String name) throws Exception {
		Cert obj = new Cert();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(CertUtil.TABLE_NAME);
		sb.append(" where ");
		sb.append(CertUtil.CERT_NAME).append("='");
		sb.append(name).append("'");
		List list = dao.select(sb.toString());// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			obj = (Cert) DaoUtil.setPo(obj, map, new CertUtil());
			return obj;
		} else {
			return null;
		}
	}
	public Cert getObjByDN(String dn) throws Exception {
		Cert obj = new Cert();
		StringBuffer sb = new StringBuffer();
		sb.append("select * from ");
		sb.append(CertUtil.TABLE_NAME);
		sb.append(" where ");
		sb.append(CertUtil.CERT_DN).append("='");
		sb.append(dn).append("'");
		List list = dao.select(sb.toString());// 调用父类方法
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			obj = (Cert) DaoUtil.setPo(obj, map, new CertUtil());
			return obj;
		} else {
			return null;
		}
	}

	private CertForm reset(CertForm f) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select ").append(SysUserUtil.USER_NAME);
		sb.append(" from ").append(SysUserUtil.TABLE_NAME);
		sb.append(" where ").append(SysUserUtil.USER_ID).append("='");
		sb.append(f.getReg_user()).append("'");
		String user_name = (String) dao.getObject(sb.toString(),
				SysUserUtil.USER_NAME, false);
		f.setReg_user_name(user_name);
		return f;
	}
	private static String bpath() {
		String bpath = "";
	    bpath = Constants.basePath;
		return bpath;
	}
	public void objDel(String no,String name) throws Exception {
		String bpath = bpath();
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append(CertUtil.TABLE_NAME);
		sb.append(" where ");
		sb.append(CertUtil.CERT_NO).append("='");
		sb.append(no).append("'");
		dao.delete(sb.toString());
		if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") == -1){
		File file = new File(Constants.getProperty("certAllPath")+"/");
		if (file.isDirectory()) {
			String[] filelist = file.list();
			for (int i = 0; i < filelist.length; i++) {
				File delfile = new File(Constants.getProperty("certAllPath") + "/" + filelist[i]);
				String filename = filelist[i];
//				 if(filename.equals(name+".pfx")){
//				   delfile.delete();
//				 }
//				 if(filename.equals(name+".p12")){
//				   delfile.delete();
//				 }
				if(filename.split("\\.")[0].equals(name)){
					delfile.delete();
				}
			}
		  }
	    }else{
	    	File file = new File(bpath + "doc/certs/");
			if (file.isDirectory()) {
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File delfile = new File(bpath + "doc/certs/" + filelist[i]);
					String filename = filelist[i];
					if(filename.split("\\.")[0].equals(name)){
						delfile.delete();
					}
				}
			 }
	    }
	}

	// 注销证书
	public void zhuDel(String no) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append(CertUtil.TABLE_NAME);
		sb.append(" set " + CertUtil.CERT_STATUS + "=" + Constants.ZCERT_STATE);
		sb.append(" where ");
		sb.append(CertUtil.CERT_NO).append("='");
		sb.append(no).append("'");
		dao.update(sb.toString());
	}

	// 撤销证书
	public void cheDel(String no) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("update ");
		sb.append(CertUtil.TABLE_NAME);
		sb.append(" set " + CertUtil.CERT_STATUS + "=" + Constants.UCERT_STATE);
		sb.append(" where ");
		sb.append(CertUtil.CERT_NO).append("='");
		sb.append(no).append("'");
		dao.update(sb.toString());
	}

	public void plDel(String selStr) throws Exception {
		String[] r_nos = selStr.split(",");
		StringBuffer sb = new StringBuffer();
		sb.append("delete from ");
		sb.append(CertUtil.TABLE_NAME).append(" where 1=2 ");
		for (String str : r_nos) {
			StringBuffer sb1=new StringBuffer();
			sb1.append("select * from ").append(SealBodyUtil.TABLE_NAME);
			sb1.append(" where ").append(SealBodyUtil.KEY_SN);
			sb1.append("='").append(str).append("'");
			int i1=dao.count(sb1.toString());
			boolean b=i1==0?false:true;
			if(!b){
				sb.append(" or ").append(CertUtil.CERT_NO);
				sb.append("='").append(str).append("'");
			}
		}
		dao.delete(sb.toString());
	}

	private Cert fToPo(CertForm f) throws Exception {
		Cert obj = new Cert();
		BeanUtils.copyProperties(obj, f);
		return obj;
	}
    private void saveCertPath(Cert obj)throws Exception {
    	String sql = ConstructSql.composeInsertSql(obj, new CertUtil());
    	//logger.info("sql"+sql);
    	//logger.info("cert_src"+obj.getCert_src());
    	if(obj.getCert_src().equals("server")){
    		if(Constants.DB_TYPE == DBTypeUtil.DT_ORCL){
        			String str1 = sql.substring(0,
        					sql.indexOf(obj.getFile_content()) - 1);
        		//	logger.info("str1"+str1);
        			String str2 = sql.substring(sql.indexOf(obj.getFile_content())
        					+ obj.getFile_content().length() + 1, sql.length());
        			sql = str1 + "?" + str2;
        		//	logger.info("str2"+str2);
        			Connection conn = null;
        			if (dao == null) {
        				conn = CreateTableForOracle.getConn();
        			} else {
        				conn = dao.getDataSource().getConnection();
        			}
        			try {
        				PreparedStatement stmt = conn.prepareStatement(sql);
        				Reader clobReader = new StringReader(obj.getFile_content()); // 将text转成流形式
        				stmt.setCharacterStream(1, clobReader, obj.getFile_content()
        						.length());// 替换sql语句中的？
        				stmt.executeUpdate();// 执行SQL
        				stmt.close();
        				conn.close();
        			} catch (SQLException e) {
        				// TODO Auto-generated catch block
        				e.printStackTrace();
        			}	
    		}else{
    			  dao.add(sql);
    	   }    			  
    	}else{
 		  dao.add(sql);
    	}	
    }
    public void deleteCertInfo(String user_id)throws Exception{ 
    	String sql="select * from "+UserCertUtil.TABLE_NAME+" where "+UserCertUtil.USER_ID+"='"+user_id+"'";
    	List list = dao.select(sql);// 调用父类方法
    	String cert_no="";
		if (list.size() != 0) {
			Map map = (Map) list.get(0);
			UserCert obj=new UserCert();
			obj = (UserCert) DaoUtil.setPo(obj, map, new UserCertUtil());
			cert_no=obj.getCert_no();
		} 
		String sql1="delete from "+CertUtil.TABLE_NAME+" where "+CertUtil.CERT_NO+"='"+cert_no+"'";
    	dao.delete(sql1);
    	String sql2="delete from "+UserCertUtil.TABLE_NAME+" where "+UserCertUtil.USER_ID+"='"+user_id+"'";
    	dao.delete(sql2);
    }
    public void saveCert(Cert obj)throws Exception {
    	String sql = ConstructSql.composeInsertSql(obj, new CertUtil());
    	if(!obj.getFile_content().equals("")){
    		if(Constants.DB_TYPE == DBTypeUtil.DT_ORCL){
//    			String str1 = sql.substring(0,
//    					sql.indexOf(obj.getFile_content()) - 1);
//
//    			String str2 = sql.substring(sql.indexOf(obj.getFile_content())
//    					+ obj.getFile_content().length() + 1, sql.length());
//    			sql = str1 + "?" + str2;
    			String str1 = sql.substring(0, sql.indexOf(obj.getFile_content()) - 1);
    			String str2 = sql.substring(sql.indexOf(obj.getFile_content())
				+ obj.getFile_content().length() + 1, sql.indexOf(obj.getPfx_content())-1);
    			String str3 = sql.substring(sql.indexOf(obj.getPfx_content())+obj.getPfx_content().length() + 1,sql.length());
    			sql = str1 + "?" + str2 +"?" + str3;
    			Connection conn = null;
    			if (dao == null) {
    				conn = CreateTableForOracle.getConn();
    			} else {
    				conn = dao.getDataSource().getConnection();
    			}
    			try {
    				PreparedStatement stmt = conn.prepareStatement(sql);
    				Reader clobReader = new StringReader(obj.getFile_content()); // 将text转成流形式
    				stmt.setCharacterStream(1, clobReader, obj.getFile_content()
    						.length());// 替换sql语句中的？
    				stmt.executeUpdate();// 执行SQL
    				stmt.close();
    				conn.close();
    			} catch (SQLException e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
    		}else{
    			dao.add(sql);
    		}
    	}
    	else{
    		dao.add(sql);
    	}	
    }
	@SuppressWarnings("unchecked")
	public List<CertForm> showCerts() throws Exception {
		String sql = "select * from " + CertUtil.TABLE_NAME + " where "
				+ CertUtil.CERT_STATUS +"='" + Constants.UCERT_STATE + "' and "
				+CertUtil.CERT_SRC +"='"+CertType.server+"' or "+CertUtil.CERT_SRC +"='"+CertType.sign+"'";
		return listObjs(sql);
	}

	//获取服务器公用证书列表
	public List<CertForm> showPublicCerts() throws Exception {
		String sql = "select * from " + CertUtil.TABLE_NAME + " where "
				+ CertUtil.CERT_STATUS + "='" + Constants.UCERT_STATE + "' and "
				+CertUtil.CERT_SRC +"='"+CertType.server+"' or "+CertUtil.CERT_SRC +"='"+CertType.sign+"'";
		return listObjs(sql);
	}
	
	@SuppressWarnings("unchecked")
	public String CertName(String cert_no) throws Exception {
		String sql = "select * from " + CertUtil.TABLE_NAME + " where "
				+ CertUtil.CERT_STATUS + "='" + Constants.UCERT_STATE
				+ "' and " + CertUtil.CERT_NO + "='" + cert_no + "'";
		List<CertForm> list = listObjs(sql);
		String cert_name = "";
		for (CertForm form : list) {
			cert_name = form.getCert_name();
		}
		return cert_name;
	}
	/**
	 * 添加用户和证书的关联关系到用户证书表
	 * @param uc
	 */
	public void addUserCert(UserCert uc){
		dao.add(ConstructSql.composeInsertSql(uc, new UserCertUtil()));
	}
	/**
	 * 修改用户和证书的关联关系表中用户当前使用的证书状态为非激活状态
	 * 将用户现在绑定的所有证书制非
	 * @param userid 用户id
	 */
	public void updateUserCert(String userid){
		String sql="update "+UserCertUtil.TABLE_NAME+" set "+UserCertUtil.IS_ACTIVE+" = '0' where "+UserCertUtil.USER_ID+" ='"+userid+"'";
		dao.update(sql);
	}
	/**
	 * 修改用户和证书的关联关系表中用户当前使用的证书状态为激活状态
	 * 将当前证书激活
	 * @param certno 证书序列号
	 */
	public void updateUserCertToIsActive(String certno){
		String sql="update "+UserCertUtil.TABLE_NAME+" set "+UserCertUtil.IS_ACTIVE+" = '1' where "+UserCertUtil.CERT_NO+" ='"+certno+"'";
		dao.update(sql);
	}

	public BaseDAOJDBC getDao() {
		return dao;
	}

	public void setDao(BaseDAOJDBC dao) {
		this.dao = dao;
	}
	
	public List<CertForm> showCersBySeal_id(String seal_id) throws Exception {
		SealBody sealBodys = seal_dao.getSealBodys(Integer.valueOf(seal_id));
		String cert_no = sealBodys.getKey_sn();
		logger.info("-----");
		String sql = "select * from "+CertUtil.TABLE_NAME +" where "
	 	+CertUtil.CERT_SRC+"='server' or "+CertUtil.CERT_SRC+"='sign' and "+CertUtil.CERT_NO + " = '" +cert_no  +"'";
	       logger.info(sql);
		List<CertForm> list = listObjs(sql);
		return list;
	}
	/**
	 * 查询客户端盖章用户的绑定的证书历史信息
	 * @param userid 用户id
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	public PageSplit showClientCertsByUserid(int pageIndex , int pageSize,String userid) throws IllegalAccessException, InvocationTargetException{
		String sql="select "+UserCertUtil.CERT_NO+" from "+UserCertUtil.TABLE_NAME+" where "+UserCertUtil.USER_ID+" ='"+userid+"'";
		List<Map> uclist = dao.select(sql);
		if(uclist.size()>0){
			StringBuffer s1=new StringBuffer();
			for (Map map : uclist) {
				UserCert usercert = new UserCert();
				usercert = (UserCert) DaoUtil.setPo(usercert, map, new UserCertUtil());
				s1.append("'"+usercert.getCert_no()+"',");
			}
			s1.deleteCharAt(s1.lastIndexOf(","));
			String str="select * from "+CertUtil.TABLE_NAME+" where "+CertUtil.CERT_NO+" in("+s1.toString()+")";
			logger.info("str:"+str);
			sql = DBTypeUtil.splitSql(str, pageIndex, pageSize, Constants.DB_TYPE);
			List list = dao.select(sql);// 调用父类方法
			List<UserCertVo> certs=new ArrayList<UserCertVo>();
			for(int i=0;i<list.size();i++){
				Map map = (Map) list.get(i);
				Cert obj = new Cert();
				UserCertVo vo=new UserCertVo();
				obj = (Cert) DaoUtil.setPo(obj, map, new CertUtil());
				UserCert userCert=getUserCert(obj.getCert_no());
				BeanUtils.copyProperties(vo, obj);
				vo.setIs_active(userCert.getIs_active());
				certs.add(vo);
			}
			PageSplit ps = new PageSplit();
			ps.setDatas(certs);
			ps.setNowPage(pageIndex);
			ps.setPageSize(pageSize);
			int totalCount = dao.count(str);
			ps.setTotalCount(totalCount);
			return ps;
		}
	    return null;
	}
	
	public UserCert getUserCert(String certno){
		String sql="select * from "+UserCertUtil.TABLE_NAME+" where "+UserCertUtil.CERT_NO+" ='"+certno+"'";
		List list=dao.select(sql);
		UserCert usercert=new UserCert();
		if(list.size()>0){
			Map map=(Map)list.get(0);
			usercert=(UserCert)DaoUtil.setPo(usercert, map, new UserCertUtil());
		}
		return usercert;
	}
	
}
