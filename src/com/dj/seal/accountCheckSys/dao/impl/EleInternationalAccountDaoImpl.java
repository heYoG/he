package com.dj.seal.accountCheckSys.dao.impl;

import java.io.File;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dj.seal.accountCheckSys.dao.api.EleInternationalAccountDao;
import com.dj.seal.accountCheckSys.po.EleInternationalAccountPo;
import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.properyUtil.DJPropertyUtil;
import com.dj.seal.util.table.EleInternationalAccountUtil;
import com.ibm.db2.jcc.DBTimestamp;

public class EleInternationalAccountDaoImpl extends BaseDAOJDBC implements EleInternationalAccountDao {
	private EleInternationalAccountUtil eacTable=new EleInternationalAccountUtil();
	
	@Override
	public int accountCheckRecord(EleInternationalAccountPo eac) {
		String insertSql = ConstructSql.composeInsertSql(eac, eacTable);
		Connection conn = null;
		int executeUpdate=0;
		PreparedStatement stmt=null;
		try {
//			if(insertSql.indexOf(eac.getSealPictureData())<insertSql.indexOf(eac.getTranDate())){//图片数据在交易日期前插入
//				String str1=insertSql.substring(0,insertSql.indexOf(eac.getSealPictureData())-1);//获取图片数据前的字符
//				String str2=insertSql.substring(insertSql.indexOf(eac.getSealPictureData())+eac.getSealPictureData().length()+1,insertSql.indexOf(eac.getTranDate())-1);//获取图片数据后到日期前的字符
//				String str3=insertSql.substring(insertSql.indexOf(eac.getTranDate())+eac.getTranDate().length()+1);//获取日期后的字符
//				insertSql=str1+"?"+str2+"dt?"+str3;//用占位符代替图片的大数据字段
//			}else{//图片数据在交易日期后插入
//				String str1=insertSql.substring(0,insertSql.indexOf(eac.getTranDate())-1);//获取日期数据前的字符
//				String str2=insertSql.substring(insertSql.indexOf(eac.getTranDate())+eac.getTranDate().length()+1,insertSql.indexOf(eac.getSealPictureData())-1);//获取日期后到图片数据后前的字符
//				String str3=insertSql.substring(insertSql.indexOf(eac.getSealPictureData())+eac.getSealPictureData().length()+1);//获取图片数据后的字符
//				insertSql=str1+"dt?"+str2+"?"+str3;//用占位符代替图片的大数据字段
//			}
//			insertSql=insertSql.replace("dt?", "to_date('"+eac.getTranDate()+"','yyyy-MM-dd')");//替换日期字符串为转换形式(有相同值时会取到第一个替换)
			
			String str1=insertSql.substring(0,insertSql.indexOf(eac.getSealPictureData())-1);//获取图片数据前的字符
			String str2=insertSql.substring(insertSql.indexOf(eac.getSealPictureData())+eac.getSealPictureData().length()+1);//获取图片数据后字符
			insertSql=str1+"?"+str2;
//			System.out.println("insertSql:"+insertSql);
			conn = getDataSource().getConnection();
			stmt = conn.prepareStatement(insertSql);//预编译
			Reader contentDataReader = new StringReader(eac.getSealPictureData()); // 将text转成流形式
			stmt.setCharacterStream(1, contentDataReader, eac.getSealPictureData().length());// 指定具有规定长度的参数设置为Reader对象(即将前面设置的占位符?设置为具体内容)
			executeUpdate = stmt.executeUpdate();// 执行SQL
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
				try {
					if(stmt!=null){
						stmt.close();
					}
					if(conn!=null){
						conn.close();
					}
				} catch (Exception e2) {
					System.out.println("连接资源释放异常!");
				}
		}
		return executeUpdate;
	}

	@Override
	public boolean delEleInternationalAccountSealPic(String rootPath) {//删除服务器图片
		SimpleDateFormat sdf =new SimpleDateFormat("yyyyMMdd");
		boolean flag=true;
		try {
			Date dt1=new Date(System.currentTimeMillis());//当前日期
			int savedDays=Integer.parseInt(DJPropertyUtil.getPropertyValue("eleInternationalSealPic_savedDay"));//已保存天数
			File file1=new File(rootPath);
			File[] listFiles1 = file1.listFiles();//配置文件设置的目录下的文件目录
			if(listFiles1.length>0){
				for(File f1:listFiles1){
					if(f1.isDirectory()){//指定删除日期文件夹文件
						String fileName=f1.getName().trim();//文件夹名称(日期)
						Date dt2=sdf.parse(fileName);//文件夹日期
						if((dt1.getTime()-dt2.getTime())/(24*60*60*1000)>=savedDays){//已保存天数是否达到清理时间
							File[] listFiles2 = f1.listFiles();
							if(listFiles2.length>0){
								for(File f2:listFiles2){//删除日期文件夹下的文件
									flag = f2.delete();
								}
							}
								flag=f1.delete();//不用else,否则清空后的空文件夹不会删除						
						}
					}else{//删除文件
						f1.delete();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("删除印章图片异常...");
			return false;
		}
		return flag;//没有要删除的图片时默认删除成功
	}

	@Override
	public int delEleInternationalAccountSealPicData() {//删除数据库对账系统原始表旧数据
		int savedDays=Integer.parseInt(DJPropertyUtil.getPropertyValue("eleInternationalSealPic_savedDay").trim());//已保存天数
		String today = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis());
		String delSealPicSql="delete from "+EleInternationalAccountUtil.TABLE_NAME+" where to_date('"+today+"','yyyy-MM-dd hh24:mi:ss')-"+EleInternationalAccountUtil.CREATETIME+">"+savedDays+"";
//		System.out.println("删除原始表旧数据sql:"+delSealPicSql);
		int update = getJdbcTemplate().update(delSealPicSql);
		getJdbcTemplate().execute("commit");
		return update;
	}


	@Override
	public int backEleInternationalAccountSealPicData() {//备份对账系统数据
		int savedDays=Integer.parseInt(DJPropertyUtil.getPropertyValue("eleInternationalSealPic_savedDay").trim());//已保存天数
		String today = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis());
		String backSealPicSql="insert into "+EleInternationalAccountUtil.TABLE_NAME+"_H select*from "+EleInternationalAccountUtil.TABLE_NAME+" where to_date('"+today+"','yyyy-MM-dd hh24:mi:ss')-"+EleInternationalAccountUtil.CREATETIME+">"+savedDays+"";
//		System.out.println("备份对账系统数据sql:"+backSealPicSql);
		int a=getJdbcTemplate().update(backSealPicSql);
		getJdbcTemplate().execute("commit");
		return a;
	}

	@Override
	public EleInternationalAccountPo getData(String valcode) {//托管银行查询
		String sql="select CHANNELNO,VALCODE,CREATETIME from "+EleInternationalAccountUtil.TABLE_NAME+" where VALCODE='"+valcode+"'";
		if(form(sql)==null)
			sql="select CHANNELNO,VALCODE,CREATETIME from "+EleInternationalAccountUtil.TABLE_NAME+"_H where VALCODE='"+valcode+"'";	
//		System.out.println("托管银行查询sql:"+sql);
		return form(sql);
	}
	
	private EleInternationalAccountPo form(String sql){
		List list = select(sql);
		EleInternationalAccountPo po=new EleInternationalAccountPo();
		if(list.size()>0){
			Map map=(Map) list.get(0);
			return (EleInternationalAccountPo)setPo(po, map, eacTable);
		}
		return null;
	}

	@Override
	public int delEleInternationalAccountOldTabData() {//删除对账系统历史表数据
		int savedDays=Integer.parseInt(DJPropertyUtil.getPropertyValue("eleInternationalHistoryData_savedDay").trim());//已保存天数
		String today = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis());
		String delSealPicSql="delete from "+EleInternationalAccountUtil.TABLE_NAME+"_H where to_date('"+today+"','yyyy-MM-dd hh24:mi:ss')-"+EleInternationalAccountUtil.CREATETIME+">"+savedDays+"";
//		System.out.println("删除历史表数据sql:"+delSealPicSql);
		int update = getJdbcTemplate().update(delSealPicSql);
		getJdbcTemplate().execute("commit");
		return update;
	}
	
	@SuppressWarnings("all")
	public static Object setPo(Object obj, Map map, Object objUtil) {
		Map mapObjUtil = ConstructSql.objReflect(objUtil);
		Iterator it = mapObjUtil.entrySet().iterator();
		Class c = obj.getClass();
		while (it.hasNext()) {
			Map.Entry mapEntry = (Map.Entry) it.next();
			Object value = map.get(mapEntry.getValue());//根据表类值(对应数据库字段)作为键找map找的值(数据库得到的值)
			String key=(String)mapEntry.getKey();//表类属性
			//解决oracle认为空字符串为null的问题
			if(value==null){
				continue;
			}
			if (value != null) {
				try {
					Method[] methods = c.getDeclaredMethods();
					Method mGet = null;
					for(int i=0;i<methods.length;i++){//解决属性必须是小写格式问题
						if(methods[i].getName().equalsIgnoreCase("get"+key)){
							key=methods[i].getName().substring(3);
							mGet=methods[i];
							break;
						}
					}
					if (value.getClass().equals(BigDecimal.class)) {// 如果是oracle里的int转换来的
						value = Integer.valueOf(value.toString());// 转换成Integer类型
					}
					if (value.getClass().equals(BigDecimal.class)) {// 如果是db2里的decimal转换来的
						BigDecimal bd = (BigDecimal) value;
						value = bd.doubleValue();// 转换成double类型
					}
					if (value.getClass().equals(DBTimestamp.class)) {// 如果是db2里的timestamp类型
						DBTimestamp dbt = (DBTimestamp) value;
						value = new Timestamp(dbt.getTime());
					}
					if (value.getClass().equals(Date.class)) {// 如果是java.sql.Date类型
						Date date = (Date) value;
						value = new Timestamp(date.getTime());

					}
					if (value.getClass().equals(Integer.class)) {// 如果是Integer类型
						Method mSet = c.getDeclaredMethod("set" + key,mGet.getReturnType());
						mSet.invoke(obj, value);
					}
					if (mGet.getReturnType().equals(Integer.class)&& "".equals(value)) {
						continue;
					}
					if (mGet.getReturnType().equals(value.getClass())) {// 属性设置值
						Method mSet = c.getDeclaredMethod("set" + key,mGet.getReturnType());
						mSet.invoke(obj, value);
					}
				} catch (Throwable e) {
					System.err.println(e);
				}
			}
		}
		return obj;
	}

	/*移动银行和微信银行查询*/
	@Override
	public EleInternationalAccountPo getDataOfWechatAndMobileBank(
			String valcode, String TranDate) {
		// TODO Auto-generated method stub
		String sql =null;
		if(TranDate==null||TranDate.equals("")){//没传日期
			sql = "select ORGCONSUMERSEQNO,TRANNO,TRANDATE,ACCOUNTNUMBER,ACCOUNTNAME,STARTDATE,ENDDATE,ACCUMULATIONCREATEDATE from "
					+ EleInternationalAccountUtil.TABLE_NAME
					+ " where VALCODE='"
					+ valcode + "'";
			if (form(sql) == null)//为空，查历史表
				sql = "select ORGCONSUMERSEQNO,TRANNO,TRANDATE,ACCOUNTNUMBER,ACCOUNTNAME,STARTDATE,ENDDATE,ACCUMULATIONCREATEDATE from "
						+ EleInternationalAccountUtil.TABLE_NAME
						+ "_H where VALCODE='" + valcode + "'";			
		}else{//传了日期
			StringBuffer sf = new StringBuffer(TranDate);
			StringBuffer insert = sf.insert(4, "-").insert(7, "-");
			sql = "select ORGCONSUMERSEQNO,TRANNO,TRANDATE,ACCOUNTNUMBER,ACCOUNTNAME,STARTDATE,ENDDATE,ACCUMULATIONCREATEDATE from "
					+ EleInternationalAccountUtil.TABLE_NAME
					+ " where VALCODE='"
					+ valcode + "' and TRANDATE=to_date('"+insert+"','yyyy-MM-dd')";
			if(form(sql)==null)//为空，查历史表
				sql = "select ORGCONSUMERSEQNO,TRANNO,TRANDATE,ACCOUNTNUMBER,ACCOUNTNAME,STARTDATE,ENDDATE,ACCUMULATIONCREATEDATE from "
						+ EleInternationalAccountUtil.TABLE_NAME
						+ "_H where VALCODE='"
						+ valcode + "' and TRANDATE=to_date('"+insert+"','yyyy-MM-dd')";
		}
//				System.out.println("sql:"+sql);
		return form(sql);
	}

}
