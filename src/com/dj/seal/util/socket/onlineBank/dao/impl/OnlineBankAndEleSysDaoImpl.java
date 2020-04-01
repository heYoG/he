package com.dj.seal.util.socket.onlineBank.dao.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.dj.seal.util.dao.BaseDAOJDBC;
import com.dj.seal.util.dao.ConstructSql;
import com.dj.seal.util.dao.DaoUtil;
import com.dj.seal.util.socket.onlineBank.dao.api.IOnlineBankAndEleSysDao;
import com.dj.seal.util.socket.onlineBank.po.OnlineBankAndEleSysPo;
import com.dj.seal.util.socket.onlineBank.table.OnlineBankAndEleSysTable;
import com.dj.seal.util.socket.onlineBank.util.ApplicationContextUtil;

public class OnlineBankAndEleSysDaoImpl extends BaseDAOJDBC implements IOnlineBankAndEleSysDao<OnlineBankAndEleSysPo> {
	private OnlineBankAndEleSysTable onlineTable = (OnlineBankAndEleSysTable) ApplicationContextUtil
			.getBean("onlineBankAndEleSysTable");
	private OnlineBankAndEleSysPo obj = (OnlineBankAndEleSysPo) ApplicationContextUtil.getBean("onlineBankAndEleSysPo");

	@Override
	public void onlineBankSave(OnlineBankAndEleSysPo op) {// 保存数据
		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:mi");
		String format2 = format1.format(System.currentTimeMillis());
		Timestamp timestamp = Timestamp.valueOf(format2);
		op.setCreatetime(timestamp);
		String insertSql = ConstructSql.composeInsertSql(op, onlineTable);
		add(insertSql);
	}

	@Override
	public OnlineBankAndEleSysPo getOnlineBankData(String seqNo,String date,String valcode) {//查询单条记录
		String sql = "select " + OnlineBankAndEleSysTable.getINFO_PLUS() + " from"
				+ OnlineBankAndEleSysTable.getTABLE_NAME() + " where 1=1";
		if(date==null||date.equals("")) {//未传日期
			if(seqNo==null||seqNo.equals(""))
				sql+=" and "+OnlineBankAndEleSysTable.VALCODE+"='"+valcode+"'";//按验证码查询
			else
				sql+=" and "+OnlineBankAndEleSysTable.ORGCONSUMERSEQNO+"='"+seqNo+"'";//按流水号查询
		}else {//传了日期
			StringBuffer sf = new StringBuffer(date);
			StringBuffer insert1 = sf.insert(4, "-");
			StringBuffer insert2 = insert1.insert(7, "-");
			if(seqNo==null||seqNo.equals(""))
				sql+=" and "+OnlineBankAndEleSysTable.VALCODE+"='"+valcode+"' and "+OnlineBankAndEleSysTable.getTRANDATE()+"=to_date('" + insert2.toString()+ "','yyyy-mm-dd')";//按验证码+日期查询
			else
				sql+=" and "+OnlineBankAndEleSysTable.ORGCONSUMERSEQNO+"='"+seqNo+"' and "+OnlineBankAndEleSysTable.getTRANDATE()+"=to_date('" + insert2.toString()+ "','yyyy-mm-dd')";//按流水号+日期查询
			
		}
		
//		if(form(sql)==null) {//历史表查询
//			sql = "select " + OnlineBankAndEleSysTable.getINFO_PLUS() + " from"
//					+ OnlineBankAndEleSysTable.getTABLE_NAME() + "_HIS where 1=1";
//			if(date==null||date.equals("")) {//未传日期
//				if(seqNo==null||seqNo.equals(""))
//					sql+=" and "+OnlineBankAndEleSysTable.VALCODE+"='"+valcode+"'";//按验证码查询
//				else
//					sql+=" and "+OnlineBankAndEleSysTable.ORGCONSUMERSEQNO+"='"+seqNo+"'";//按流水号查询
//			}else {//传了日期
//				StringBuffer sf = new StringBuffer(date);
//				StringBuffer insert1 = sf.insert(4, "-");
//				StringBuffer insert2 = insert1.insert(7, "-");
//				if(seqNo==null||seqNo.equals(""))
//					sql+=" and "+OnlineBankAndEleSysTable.VALCODE+"='"+valcode+"' and "+OnlineBankAndEleSysTable.getTRANDATE()+"=to_date('" + insert2.toString()+ "','yyyy-mm-dd')";//按验证码+日期查询
//				else
//					sql+=" and "+OnlineBankAndEleSysTable.ORGCONSUMERSEQNO+"='"+seqNo+"' and "+OnlineBankAndEleSysTable.getTRANDATE()+"=to_date('" + insert2.toString()+ "','yyyy-mm-dd')";//按流水号+日期查询
//			}
//		}
		return form(sql);
	}

	@Override
	public List<OnlineBankAndEleSysPo> getOnlineBankList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<OnlineBankAndEleSysPo> getOnlineBankList(String valcode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public OnlineBankAndEleSysPo form(String sql) {//类型转换
		List list = select(sql);
		int a = list.size();
		if (a != 0) {
			Map map = (Map) list.get(0);
			obj = (OnlineBankAndEleSysPo) DaoUtil.setPo(obj, map, onlineTable);
			return obj;
		} else {
			return null;
		}
	}
}
