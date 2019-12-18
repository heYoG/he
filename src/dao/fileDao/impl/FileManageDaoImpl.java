package dao.fileDao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import dao.fileDao.api.IFileManageDao;
import hibernate.utils.SessionClass;
import util.BaseDao;
import util.CommenClass;
import util.TableManager;
import vo.fileVo.FileManageVo;
import vo.userVo.UserVo;

public class FileManageDaoImpl extends BaseDao<FileManageVo> implements IFileManageDao<FileManageVo,CommenClass,UserVo>{

	private SessionClass sessionCls;
	
	@Override
	public FileManageVo getFileInfo(int id) {
		FileManageVo vo=new FileManageVo();
		String sql="select id,myFile,UploadTime,operator,status from "+TableManager.FILETABLE+" where id=?";
		ResultSet rs = executeQuery(sql,1,new Object[]{id});//参数1代表要设置占位符参数
		try {
			while (rs.next()) {
				vo.setId(rs.getInt("id"));
				vo.setMyFile(rs.getString("myFile"));
				vo.setUploadTime(rs.getTimestamp("uploadTime"));
				vo.setOperator(rs.getString("operator"));
				vo.setStatus(rs.getInt("status"));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			close();
		}
		return vo;
	}


	@Override
	public int deleteFile(int id,int flag) {
		String sql=null;
		if(flag==0) {
			sql="delete from "+TableManager.FILETABLE+" where id=?";
		}else {
			sql="update "+TableManager.FILETABLE+" set status=0 where id=?";
		}
		int i = executeUpdate(sql, new Object[]{id});
//		System.out.println("删除单个文件返回值:"+i);
		return i;
	}

	@Override
	public int deleteFiles(String ids,int flag) {
		String sql=null;
		if(flag==0) {
			sql="delete from "+TableManager.FILETABLE+" where id in("+ids+")";
		}else {
			sql="update "+TableManager.FILETABLE+" set status=0 where id in("+ids+")";
		}
//		System.out.println("delSql:"+sql);
		int i = executeUpdate(sql);
//		System.out.println("批量删除文件返回值:"+i);
		return i;
	}

	@Override
	public void browse(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void downFile(int id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void downFiles(int... id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void uploadFile(FileManageVo vo) {
		String sql="insert into "+TableManager.FILETABLE+" (id,myFile,fileSize,uploadTime,operator,status,user_id,originalFileName) values (null,?,?,?,?,?,?,?)";
		executeUpdate(sql,new Object[]{vo.getMyFile(),vo.getFileSize(),vo.getUploadTime(),vo.getOperator(),vo.getStatus(),vo.getUser().getId(),vo.getOriginalFileName()});
		close();
		
	}

	@Override
	public void uploadFiles() {
		System.out.println("come here!");
	}

	/*hql实现修改*/
	@Override
	public int updateFileStatus(String ids,int batchFiles) {
		sessionCls=new SessionClass();
		Session session = sessionCls.getOpenedSession();
		Transaction transaction = session.beginTransaction();
		String hql="update "+FileManageVo.class.getSimpleName()+" set status=1 where 1=1";
		if(batchFiles==0)//单个恢复
			hql+=" and id="+ids;
		else//批量恢复
			hql+=" and id in("+ids+")";
//		System.out.println("hql:"+hql);
		Query query = session.createQuery(hql);
		query.executeUpdate();
		transaction.commit();
		session.close();
		return 0;
	}

	@Override
	public List<FileManageVo> getFileInfo(FileManageVo vo,CommenClass cc,UserVo uv) {
		String sql=null;
		List<FileManageVo> list=new ArrayList<FileManageVo>();
		ResultSet rs=null;
		int flag=0;//0：不设置占位符，1：设置占位符
		
		try {
			if (cc.getType().equals("1")) {// 查询正常文件
				if(uv.getAv().getAuthVal()==1)//管理员查询
					sql = "select t1.id,t1.myFile,t1.fileData,t1.fileSize,t1.uploadTime,t1.operator,t1.status,t1.originalFileName,t1.fileSize from "
							+ TableManager.FILETABLE + " t1," + TableManager.USERTABLE
							+ " t2 where t1.user_id=t2.id and t1.status=1 limit "+(cc.getCurrentPage()-1)*cc.getPageSize()+","+cc.getPageSize();
				else//普通用户查询
					sql = "select t1.id,t1.myFile,t1.fileData,t1.fileSize,t1.uploadTime,t1.operator,t1.status,t1.originalFileName,t1.fileSize from "
							+ TableManager.FILETABLE + " t1," + TableManager.USERTABLE
							+ " t2 where t1.user_id=t2.id and t1.status=1 and t1.user_id=" + uv.getId()+" limit "+(cc.getCurrentPage()-1)*cc.getPageSize()+","+cc.getPageSize();
			} else {// 查询已删除文件
				if (vo.getOperator() != null && !vo.getOperator().equals("") && vo.getOriginalFileName() != null
						&& !vo.getOriginalFileName().equals("") && vo.getUploadTime() != null) {// 条件都不为空
					sql = "select t1.id,t1.myFile,t1.fileData,t1.fileSize,t1.uploadTime,t1.operator,t1.status,t1.originalFileName,t1.fileSize from "
							+ TableManager.FILETABLE + " t1," + TableManager.USERTABLE
							+ " t2 where t1.user_id=t2.id and t2.userNo=?"
							+ " and t1.originalFileName=? and t1.uploadtime=? and t1.status=0 and t1.user_id=" + uv.getId();
					flag = 1;
				}
//				else if (vo.getOriginalFileName() != null && !vo.getOriginalFileName().equals("")
//						&& vo.getUploadTime() != null && vo.getOperator() == null || vo.getOperator().equals("")) {// 操作者为空,必须放最后判断,否则操作者为空直接结束判断
//					flag = 1;
//
//				} else if (vo.getOperator() != null && !vo.getOperator().equals("") && vo.getUploadTime() != null
//						&& vo.getOriginalFileName() == null || vo.getOriginalFileName().equals("")) {// 文件名为空,同上理
//					flag = 1;
//				} else if (vo.getOperator() != null && !vo.getOperator().equals("") && vo.getOriginalFileName() != null
//						&& !vo.getOriginalFileName().equals("") && vo.getUploadTime() == null) {// 日期为空
//					flag = 1;
//				} else if(vo.getOperator() != null && !vo.getOperator().equals("") && vo.getOriginalFileName() == null
//				|| vo.getOriginalFileName().equals("") && vo.getUploadTime() == null){//仅操作者不为空
				
//			    } else if(vo.getOperator() == null || vo.getOperator().equals("") && vo.getOriginalFileName() != null
//				&& !vo.getOriginalFileName().equals("") && vo.getUploadTime() == null) {//仅文件名不为空
//			    
//			    } else if(vo.getOperator() == null && vo.getOperator().equals("") && vo.getOriginalFileName() == null
//				&& vo.getOriginalFileName().equals("") && vo.getUploadTime() != null) {//仅日期不为空
//			    
//			    }
			    	  	
				else {// 条件都为空
					if (uv.getAv().getAuthVal()== 1)// 管理员查询
						sql = "select t1.id,t1.myFile,t1.fileData,t1.fileSize,t1.uploadTime,t1.operator,t1.status,t1.originalFileName,t1.fileSize from "
								+ TableManager.FILETABLE + " t1," + TableManager.USERTABLE
								+ " t2 where t1.user_id=t2.id and t1.status=0 limit "+(cc.getCurrentPage()-1)*cc.getPageSize()+","+cc.getPageSize();
					else// 普通用户查询
						sql = "select t1.id,t1.myFile,t1.fileData,t1.fileSize,t1.uploadTime,t1.operator,t1.status,t1.originalFileName,t1.fileSize from "
								+ TableManager.FILETABLE + " t1," + TableManager.USERTABLE
								+ " t2 where t1.user_id=t2.id and t1.status=0 and t1.user_id=" + uv.getId()+" limit "+(cc.getCurrentPage()-1)*cc.getPageSize()+","+cc.getPageSize();
				}
			}
			rs = executeQuery(sql,flag,new Object[] {vo.getOperator(), vo.getOriginalFileName(), vo.getUploadTime() });
			while (rs.next()) {
				//指定字段查询时rs中的值按查询顺序封装,注意对应赋值
				vo = new FileManageVo(rs.getInt("id"), rs.getString("myFile"), rs.getBlob("fileData"), rs.getTimestamp("uploadtime"), rs.getString("operator"),
						rs.getInt("status"), rs.getString("originalFileName"),rs.getString("fileSize"));
				list.add(vo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close();
		}
		return list;
	}


	@Override
	public int getCount(String flag,UserVo uv) {
		String sql = "";
		if (flag.equals("0")) {//查询已删除文件
			if(uv.getAv().getAuthVal()==1)//管理员查询
				sql = "select count(id) from " + TableManager.FILETABLE + " where status=0";
			else//普通用户查询
				sql = "select count(t1.id) from " + TableManager.FILETABLE+" t1,"+TableManager.USERTABLE + " t2 where t1.user_id=t2.id and t1.status=0 and t1.user_id="+uv.getId();
		}else{//查询未删除文件
			if(uv.getAv().getAuthVal()==1)//管理员查询
				sql = "select count(id) from " + TableManager.FILETABLE + " where status=1";
			else//普通用户查询
				sql = "select count(t1.id) from " + TableManager.FILETABLE+" t1,"+TableManager.USERTABLE + " t2 where t1.user_id=t2.id and t1.status=1 and t1.user_id="+uv.getId();
		}
		ResultSet rs = executeQuery(sql, 0);
		int val = 0;
		try {
			while (rs.next())
				val = rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return val;
	}

}
