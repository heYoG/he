package dao.fileDao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import dao.fileDao.api.IFileManageDao;
import hibernate.utils.SessionClass;
import util.BaseDao;
import util.TableManager;
import vo.CommenVo;
import vo.fileVo.FileManageVo;
import vo.userVo.UserVo;

public class FileManageDaoImpl extends BaseDao<FileManageVo> implements IFileManageDao<FileManageVo>{

	private SessionClass sessionCls;
	private static HttpServletRequest request = ServletActionContext.getRequest();
	private static HttpSession session = request.getSession();
	
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
	public int deleteFile(int id) {
		String sql="update "+TableManager.FILETABLE+" set status=0 where id=?";
		int i = executeUpdate(sql, new Object[]{id});
		System.out.println("删除单个文件返回值:"+i);
		return i;
	}

	@Override
	public int deleteFiles(int... id) {
		// TODO Auto-generated method stub
		return 0;
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
		int i = executeUpdate(sql,new Object[]{vo.getMyFile(),vo.getFileSize(),vo.getUploadTime(),vo.getOperator(),vo.getStatus(),vo.getUser().getId(),vo.getOriginalFileName()});
		System.out.println("单个文件上传返回值:"+i);
		close();
		
	}

	@Override
	public void uploadFiles() {
		System.out.println("come here!");
	}

	/*hibernate实现修改*/
	@Override
	public int updateFileStatus(int id) {
		sessionCls=new SessionClass();
		Session session = sessionCls.getOpenedSession();
		Transaction transaction = session.beginTransaction();
		String sql="update "+FileManageVo.class.getSimpleName()+" set status=1 where id="+id;
//		System.out.println("class:"+FileManageVo.class+"\nsql:"+sql);
		Query query = session.createQuery(sql);
		query.executeUpdate();
		transaction.commit();
		session.close();
		return 0;
	}

	@Override
	public List<FileManageVo> getFileInfo(String type,int authValue,FileManageVo vo) {
		String sql=null;
		List<FileManageVo> list=new ArrayList<FileManageVo>();
		ResultSet rs=null;
		UserVo uv = (UserVo)session.getAttribute("userVo");
		int userId=uv.getId();
		int flag=0;//0：不设置占位符，1：设置占位符
		
		try {
			if (type.equals("1")) {// 查询正常文件
				if(authValue==1)//管理员查询
					sql = "select t1.id,t1.myFile,t1.fileData,t1.fileSize,t1.uploadTime,t1.operator,t1.status,t1.originalFileName,t1.fileSize from "
							+ TableManager.FILETABLE + " t1," + TableManager.USERTABLE
							+ " t2 where t1.user_id=t2.id and t1.status=1";
				else//普通用户查询
					sql = "select t1.id,t1.myFile,t1.fileData,t1.fileSize,t1.uploadTime,t1.operator,t1.status,t1.originalFileName,t1.fileSize from "
							+ TableManager.FILETABLE + " t1," + TableManager.USERTABLE
							+ " t2 where t1.user_id=t2.id and t1.status=1 and t1.user_id=" + userId;
			} else {// 查询已删除文件
				if(!vo.getOperator().equals("")&&!vo.getOriginalFileName().equals("")&&vo.getUploadTime()!=null) {//条件都不为空
					sql = "select t1.id,t1.myFile,t1.fileData,t1.fileSize,t1.uploadTime,t1.operator,t1.status,t1.originalFileName,t1.fileSize from "
							+ TableManager.FILETABLE + " t1," + TableManager.USERTABLE
							+ " t2 where t1.user_id=t2.id and t2.userNo=?"
							+ " and t1.originalFileName=? and t1.uploadtime=? and t1.status=0 and t1.user_id="+userId;	
					flag=1;
				}else if(vo.getOperator().equals("")&&!vo.getOriginalFileName().equals("")&&vo.getUploadTime()!=null){
					flag=1;
					
				}else if(!vo.getOperator().equals("")&&vo.getOriginalFileName().equals("")&&vo.getUploadTime()!=null){
					flag=1;
				}else if(!vo.getOperator().equals("")&&!vo.getOriginalFileName().equals("")&&vo.getUploadTime()==null){
					flag=1;
				}else{//条件都为空
					if(authValue==1)//管理员查询
						sql = "select t1.id,t1.myFile,t1.fileData,t1.fileSize,t1.uploadTime,t1.operator,t1.status,t1.originalFileName,t1.fileSize from "
								+ TableManager.FILETABLE + " t1," + TableManager.USERTABLE
								+ " t2 where t1.user_id=t2.id and t1.status=0";
					else//普通用户查询
						sql = "select t1.id,t1.myFile,t1.fileData,t1.fileSize,t1.uploadTime,t1.operator,t1.status,t1.originalFileName,t1.fileSize from "
								+ TableManager.FILETABLE + " t1," + TableManager.USERTABLE
								+ " t2 where t1.user_id=t2.id and t1.status=0 and t1.user_id=" + userId;
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

}
