package action.fileAction;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import dao.fileDao.impl.FileManageDaoImpl;
import util.CommenClass;
import util.UploadAndDownloadUtil;
import vo.CommenVo;
import vo.fileVo.FileManageVo;
import vo.userVo.UserVo;

@SuppressWarnings(value={"all"})
public class FileManagerAction extends ActionSupport{
	private HttpServletResponse response=ServletActionContext.getResponse();
	FileManageVo fm=new FileManageVo();
	FileManageDaoImpl fdi=new FileManageDaoImpl();
	CommenClass cc=new CommenClass();
	private File myFile;//上传文件(tmp临时文件)
	private String myFileFileName;//文件名称
	
	public String execute(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);//会话存在则返回，不存在返回null，解决session过期调用session提示Session already invalidated问题
		UserVo uv =null;
		if(session!=null)
			uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		
		String type=request.getParameter("type");
		String currentPage1 = request.getParameter("currentPage");
		currentPage1=currentPage1==null?"1":currentPage1;
		String pageSize1 = cc.getProperty("pageSize");//每页显示记录数
		int totalCount=fdi.getCount(1,uv);//获取总记录数,实参1表示未删除文件
		int currentPage2=Integer.parseInt(currentPage1);//转为整型
		int pageSize2=Integer.parseInt(pageSize1);//转为整型
		int totalPages=0;//总页数
		String start1=request.getParameter("start");//开始页,用于列表显示可点击页码数的开始位置
		String end1=request.getParameter("end");//结束页,用于列表显示可点击页码数的结束位置
		String pageButtonNum1 = cc.getProperty("pageButtonNum");//显示可点击页码数
		int pageButtonNum2=Integer.parseInt(pageButtonNum1);
		int start2=0;
		int end2=0;
		String nextOrPre=request.getParameter("nextOrPre");//判断是点击上一页还是下一页
		List<Integer> itemList=new ArrayList<Integer>();
		
		if(totalCount%pageSize2==0) {//整除取商,totalCount和pageSize2必须是整型
			totalPages=totalCount/pageSize2;
		}else {//不能整除取商+1
			totalPages=totalCount/pageSize2+1;
		}

		/*获取start和end值*/
		if(start1==null&&end1==null) {//初始页面、跳转时页面和点击具体页码页面起止位
			if(currentPage2%pageButtonNum2==0) {//当前页是要显示数目的整数倍
				start2=currentPage2-pageButtonNum2+1;//起始位置,每pageButtonNum2换一个区间
				end2=currentPage2;//结束位置
			}else {//当前页不是要显示数目的整数倍
				if(totalPages<pageButtonNum2) {//总页数或当前页小于要显示页的数目
					start2=1;
					end2=totalPages;
				}else {//总页数大于要显示页数目
					if(currentPage2<totalPages/pageButtonNum2*pageButtonNum2) {//不是最后一个区页
						if(currentPage2<pageButtonNum2)
							start2=1;
						else
							start2=currentPage2/pageButtonNum2*pageButtonNum2+1;						
						end2=currentPage2/pageButtonNum2*pageButtonNum2+pageButtonNum2;
					}else {//是最后一个区页
						start2=currentPage2/pageButtonNum2*pageButtonNum2+1;
						end2=totalPages/pageButtonNum2*pageButtonNum2+totalPages%pageButtonNum2;
					}
				}
			}
		}else {//上一页或下一页时起止位置
			start2=Integer.parseInt(start1);
			end2=Integer.parseInt(end1);
			if(nextOrPre.equals("previous")){//点击上一页
				if(currentPage2%pageButtonNum2==0) {//进入上一个区间的尾页
					start2=currentPage2-pageButtonNum2+1;
					end2=currentPage2;
				}
			}else {//点击下一页
				if(currentPage2%pageButtonNum2==1) {//进入下一个区间的第一页
					start2=currentPage2;
					if(currentPage2<totalPages/pageButtonNum2*pageButtonNum2+1)
						end2=start2+pageButtonNum2-1;
					else
						end2=start2+totalPages%pageButtonNum2-1;//最后不满pageButtonNum2数目的页
				}
			}			
		}
		
		/*封装分页查询基本数据*/
		cc.setCurrentPage(currentPage2);//当前页
		cc.setPageSize(pageSize2);//每页记录数
		cc.setTotalCount(totalCount);//总记录数
		/*封装页码集合*/
		for(int i=start2;i<=end2;i++) {
			itemList.add(i);
		}
		cc.setTotalPage(totalPages);
		List<FileManageVo> listFile = fdi.getFileInfo(type,uv.getAv().getAuthVal(),fm,cc,uv);
		request.setAttribute("fileList", listFile);//文件集合
		request.setAttribute("itemList", itemList);//页码集合
		request.setAttribute("currentPage", currentPage2);//当前页
		request.setAttribute("totalPage", totalPages);//总页数
		request.setAttribute("totalCount", totalCount);//总记录数
		request.setAttribute("start", start2);//起始武林风
		request.setAttribute("end", end2);//结束位置
		return "fileInfo";
	}
	
	public String uploadFile(){//上传
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		fm.setUser(new UserVo());//必须设置，否则在后面fm.getUser()为空
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		UserVo uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		long length = myFile.length();//文件大小(bytes)
		String filePath = UploadAndDownloadUtil.uploadFile(myFile,getMyFileFileName());//文件保存路径
		String date = format.format(System.currentTimeMillis());//上传时间
		int status=1;//状态,默认正常可用
		fm.setMyFile(filePath);
		fm.setUploadTime(Timestamp.valueOf(date));
		fm.setOperator((String) uv.getUserNo());
		fm.setStatus(status);
		fm.getUser().setId(uv.getId());
		fm.setFileSize(length+"");
		fm.setOriginalFileName(getMyFileFileName());
		fdi.uploadFile(fm);
		return "uploadFileSuccess";
	}
	
	public String downloadFile(){//下载
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		UserVo uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		int id=Integer.parseInt(request.getParameter("id"));
		FileManageVo vo = fdi.getFileInfo(id);
		UploadAndDownloadUtil.downloadFile(vo.getMyFile(), request, response);
		return null;//下载完成后留在当前页面
		
	}
	
	/*虚拟删除*/
	public String delete_update() throws IOException{//删除文件
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		UserVo uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		int id=Integer.parseInt(request.getParameter("id"));
		fdi.deleteFile(id,1);
		String type=request.getParameter("type");
		String currentPage1 = request.getParameter("currentPage");
		currentPage1=currentPage1==null?"1":currentPage1;
		String pageSize1 = cc.getProperty("pageSize");//每页显示记录数
		int totalCount=fdi.getCount(1,uv);//获取总记录数,实参0表示已删除文件
		int currentPage2=Integer.parseInt(currentPage1);//转为整型
		int pageSize2=Integer.parseInt(pageSize1);//转为整型
		int totalPages=0;//总页数
		String start1=request.getParameter("start");//开始页,用于列表显示可点击页码数的开始位置
		String end1=request.getParameter("end");//结束页,用于列表显示可点击页码数的结束位置
		String pageButtonNum1 = cc.getProperty("pageButtonNum");//显示可点击页码数
		int pageButtonNum2=Integer.parseInt(pageButtonNum1);
		int start2=0;
		int end2=0;
		String nextOrPre=request.getParameter("nextOrPre");//判断是点击上一页还是下一页
		List<Integer> itemList=new ArrayList<Integer>();
		
		if(totalCount%pageSize2==0) {//整除取商,totalCount和pageSize2必须是整型
			totalPages=totalCount/pageSize2;
		}else {//不能整除取商+1
			totalPages=totalCount/pageSize2+1;
		}
		
		/**
		 * 1.获取start和end值
		 * 2.按[start,end]区间显示可点击页码
		 * 3.单个区间的显示
		 * */
		if(start1==null&&end1==null) {//初始页面、跳转时页面和点击具体页码页面起止位
			if(currentPage2%pageButtonNum2==0) {//当前页是要显示数目的整数倍
				start2=currentPage2-pageButtonNum2+1;//起始位置,每pageButtonNum2换一个区间
				end2=currentPage2;//结束位置
			}else {//当前页不是要显示数目的整数倍
				if(totalPages<pageButtonNum2) {//总页数或当前页小于要显示页的数目
					start2=1;
					end2=totalPages;
				}else {//总页数大于要显示页数目
					if(currentPage2<totalPages/pageButtonNum2*pageButtonNum2) {//不是最后一个区页
						if(currentPage2<pageButtonNum2)
							start2=1;
						else
							start2=currentPage2/pageButtonNum2*pageButtonNum2+1;						
						end2=currentPage2/pageButtonNum2*pageButtonNum2+pageButtonNum2;
					}else {//是最后一个区页
						start2=currentPage2/pageButtonNum2*pageButtonNum2+1;
						end2=totalPages/pageButtonNum2*pageButtonNum2+totalPages%pageButtonNum2;
					}
				}
			}
		}else {//上一页或下一页时起止位置
			start2=Integer.parseInt(start1);
			end2=Integer.parseInt(end1);
			if(nextOrPre.equals("previous")){//点击上一页
				if(currentPage2%pageButtonNum2==0) {//进入上一个区间的尾页
					start2=currentPage2-pageButtonNum2+1;
					end2=currentPage2;
				}
			}else {//点击下一页
				if(currentPage2%pageButtonNum2==1) {//进入下一个区间的第一页
					start2=currentPage2;
					if(currentPage2<totalPages/pageButtonNum2*pageButtonNum2+1)
						end2=start2+pageButtonNum2-1;
					else
						end2=start2+totalPages%pageButtonNum2-1;//最后不满pageButtonNum2数目的页
				}
			}			
		}
		
		/*封装分页查询基本数据*/
		cc.setCurrentPage(currentPage2);//当前页
		cc.setPageSize(pageSize2);//每页记录数
		cc.setTotalCount(totalCount);//总记录数
		
		/*封装页码集合*/
		for(int i=start2;i<=end2;i++) {
			itemList.add(i);
		}
		cc.setTotalPage(totalPages);
		List<FileManageVo> listFile = fdi.getFileInfo(type,uv.getAv().getAuthVal(),fm,cc,uv);
		request.setAttribute("fileList", listFile);//文件集合
		request.setAttribute("itemList", itemList);//页码集合
		request.setAttribute("currentPage", currentPage2);//当前页
		request.setAttribute("totalPage", totalPages);//总页数
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("start", start2);//起始武林风
		request.setAttribute("end", end2);//结束位置
		return "fileInfo";//返回文件管理
	}
	
	/*彻底删除*/
	public String delete_complete() {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		UserVo uv = (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		int id=Integer.parseInt(request.getParameter("id"));
		fdi.deleteFile(id,0);
		String type=request.getParameter("type");
		String currentPage1 = request.getParameter("currentPage");
		currentPage1=currentPage1==null?"1":currentPage1;
		String pageSize1 = cc.getProperty("pageSize");//每页显示记录数
		int totalCount=fdi.getCount(0,uv);//获取总记录数,实参0表示已删除文件
		int currentPage2=Integer.parseInt(currentPage1);//转为整型
		int pageSize2=Integer.parseInt(pageSize1);//转为整型
		int totalPages=0;//总页数
		String start1=request.getParameter("start");//开始页,用于列表显示可点击页码数的开始位置
		String end1=request.getParameter("end");//结束页,用于列表显示可点击页码数的结束位置
		String pageButtonNum1 = cc.getProperty("pageButtonNum");//显示可点击页码数
		int pageButtonNum2=Integer.parseInt(pageButtonNum1);
		int start2=0;
		int end2=0;
		String nextOrPre=request.getParameter("nextOrPre");//判断是点击上一页还是下一页
		List<Integer> itemList=new ArrayList<Integer>();
		
		/*封装分页查询基本数据*/
		cc.setCurrentPage(currentPage2);//当前页
		cc.setPageSize(pageSize2);//每页记录数
		cc.setTotalCount(totalCount);//总记录数
		if(totalCount%pageSize2==0) {//整除取商,totalCount和pageSize2必须是整型
			totalPages=totalCount/pageSize2;
		}else {//不能整除取商+1
			totalPages=totalCount/pageSize2+1;
		}
		/*获取start和end值*/
		if(start1==null&&end1==null) {//初始页面、跳转时页面和点击具体页码页面起止位
			if(currentPage2%pageButtonNum2==0) {//当前页是要显示数目的整数倍
				start2=currentPage2-pageButtonNum2+1;//起始位置,每pageButtonNum2换一个区间
				end2=currentPage2;//结束位置
			}else {//当前页不是要显示数目的整数倍
				if(totalPages<pageButtonNum2) {//总页数或当前页小于要显示页的数目
					start2=1;
					end2=totalPages;
				}else {//总页数大于要显示页数目
					if(currentPage2<totalPages/pageButtonNum2*pageButtonNum2) {//不是最后一个区页
						if(currentPage2<pageButtonNum2)
							start2=1;
						else
							start2=currentPage2/pageButtonNum2*pageButtonNum2+1;						
						end2=currentPage2/pageButtonNum2*pageButtonNum2+pageButtonNum2;
					}else {//是最后一个区页
						start2=currentPage2/pageButtonNum2*pageButtonNum2+1;
						end2=totalPages/pageButtonNum2*pageButtonNum2+totalPages%pageButtonNum2;
					}
				}
			}
		}else {//上一页或下一页时起止位置
			start2=Integer.parseInt(start1);
			end2=Integer.parseInt(end1);
			if(nextOrPre.equals("previous")){//点击上一页
				if(currentPage2%pageButtonNum2==0) {//进入上一个区间的尾页
					start2=currentPage2-pageButtonNum2+1;
					end2=currentPage2;
				}
			}else {//点击下一页
				if(currentPage2%pageButtonNum2==1) {//进入下一个区间的第一页
					start2=currentPage2;
					if(currentPage2<totalPages/pageButtonNum2*pageButtonNum2+1)
						end2=start2+pageButtonNum2-1;
					else
						end2=start2+totalPages%pageButtonNum2-1;//最后不满pageButtonNum2数目的页
				}
			}			
		}
		/*封装页码集合*/
		for(int i=start2;i<=end2;i++) {
			itemList.add(i);
		}
		cc.setTotalPage(totalPages);
		List<FileManageVo> listFile = fdi.getFileInfo(type,uv.getAv().getAuthVal(),fm,cc,uv);
		request.setAttribute("fileList", listFile);//文件集合
		request.setAttribute("itemList", itemList);//页码集合
		request.setAttribute("currentPage", currentPage2);//当前页
		request.setAttribute("totalPage", totalPages);//总页数
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("start", start2);//起始武林风
		request.setAttribute("end", end2);//结束位置
		return "recoveryFileList";//返回可恢复列表
	}
	
	public String recoveryFileList() {//查询出可恢复文件列表
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		UserVo uv=null;
		if(session!=null)
			uv= (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		fm.setOperator(request.getParameter("userNo"));
		fm.setOriginalFileName(request.getParameter("fileName"));
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		String date1 = request.getParameter("date");
//		Timestamp date2 = Timestamp.valueOf(date1);
//		fm.setUploadTime(date2);
		String type=request.getParameter("type");
		String currentPage1 = request.getParameter("currentPage");
		currentPage1=currentPage1==null?"1":currentPage1;
		String pageSize1 = cc.getProperty("pageSize");//每页显示记录数
		int totalCount=fdi.getCount(0,uv);//获取总记录数,实参0表示已删除文件
		int currentPage2=Integer.parseInt(currentPage1);//转为整型
		int pageSize2=Integer.parseInt(pageSize1);//转为整型
		int totalPages=0;//总页数
		String start1=request.getParameter("start");//开始页,用于列表显示可点击页码数的开始位置
		String end1=request.getParameter("end");//结束页,用于列表显示可点击页码数的结束位置
		String pageButtonNum1 = cc.getProperty("pageButtonNum");//显示可点击页码数
		int pageButtonNum2=Integer.parseInt(pageButtonNum1);
		int start2=0;
		int end2=0;
		String nextOrPre=request.getParameter("nextOrPre");//判断是点击上一页还是下一页
		List<Integer> itemList=new ArrayList<Integer>();
		
		/*封装分页查询基本数据*/
		cc.setCurrentPage(currentPage2);//当前页
		cc.setPageSize(pageSize2);//每页记录数
		cc.setTotalCount(totalCount);//总记录数
		/*计算总页数*/
		if(totalCount%pageSize2==0) {//整除取商,totalCount和pageSize2必须是整型
			totalPages=totalCount/pageSize2;
		}else {//不能整除取商+1
			totalPages=totalCount/pageSize2+1;
		}
		
		/*获取start和end值*/
		if(start1==null&&end1==null) {//初始页面、跳转时页面和点击具体页码页面起止位
			if(currentPage2%pageButtonNum2==0) {//当前页是要显示数目的整数倍
				start2=currentPage2-pageButtonNum2+1;//起始位置,每pageButtonNum2换一个区间
				end2=currentPage2;//结束位置
			}else {//当前页不是要显示数目的整数倍
				if(totalPages<pageButtonNum2) {//总页数或当前页小于要显示页的数目
					start2=1;
					end2=totalPages;
				}else {//总页数大于要显示页数目
					if(currentPage2<totalPages/pageButtonNum2*pageButtonNum2) {//不是最后一个区页
						if(currentPage2<pageButtonNum2)
							start2=1;
						else
							start2=currentPage2/pageButtonNum2*pageButtonNum2+1;						
						end2=currentPage2/pageButtonNum2*pageButtonNum2+pageButtonNum2;
					}else {//是最后一个区页
						start2=currentPage2/pageButtonNum2*pageButtonNum2+1;
						end2=totalPages/pageButtonNum2*pageButtonNum2+totalPages%pageButtonNum2;
					}
				}
			}
		}else {//上一页或下一页时起止位置
			start2=Integer.parseInt(start1);
			end2=Integer.parseInt(end1);
			if(nextOrPre.equals("previous")){//点击上一页
				if(currentPage2%pageButtonNum2==0) {//进入上一个区间的尾页
					start2=currentPage2-pageButtonNum2+1;
					end2=currentPage2;
				}
			}else {//点击下一页
				if(currentPage2%pageButtonNum2==1) {//进入下一个区间的第一页
					start2=currentPage2;
					if(currentPage2<totalPages/pageButtonNum2*pageButtonNum2+1)
						end2=start2+pageButtonNum2-1;
					else
						end2=start2+totalPages%pageButtonNum2-1;//最后不满pageButtonNum2数目的页
				}
			}			
		}
		
		/*封装页码集合*/
		for(int i=start2;i<=end2;i++) {
			itemList.add(i);
		}
		cc.setTotalPage(totalPages);
		List<FileManageVo> listFile = fdi.getFileInfo(type,uv.getAv().getAuthVal(),fm,cc,uv);
		request.setAttribute("fileList", listFile);//文件集合
		request.setAttribute("itemList", itemList);//页码集合
		request.setAttribute("currentPage", currentPage2);//当前页
		request.setAttribute("totalPage", totalPages);//总页数
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("start", start2);//起始武林风
		request.setAttribute("end", end2);//结束位置
		return "recoveryFileList";
	}
	
	public String recoveryFile() {//修改文件状态,恢复文件
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		UserVo uv=null;
		if(session!=null)
		 uv= (UserVo)session.getAttribute("userVo");
		if(uv==null){//系统登录过期
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String id = request.getParameter("id");
		fdi.updateFileStatus(Integer.parseInt(id));
		String type=request.getParameter("type");
		String currentPage1 = request.getParameter("currentPage");
		currentPage1=currentPage1==null?"1":currentPage1;
		String pageSize1 = cc.getProperty("pageSize");//每页显示记录数
		int totalCount=fdi.getCount(1,uv);//获取总记录数,实参0表示已删除文件
		int currentPage2=Integer.parseInt(currentPage1);//转为整型
		int pageSize2=Integer.parseInt(pageSize1);//转为整型
		int totalPages=0;//总页数
		String start1=request.getParameter("start");//开始页,用于列表显示可点击页码数的开始位置
		String end1=request.getParameter("end");//结束页,用于列表显示可点击页码数的结束位置
		String pageButtonNum1 = cc.getProperty("pageButtonNum");//显示可点击页码数
		int pageButtonNum2=Integer.parseInt(pageButtonNum1);
		int start2=0;
		int end2=0;
		String nextOrPre=request.getParameter("nextOrPre");//判断是点击上一页还是下一页
		List<Integer> itemList=new ArrayList<Integer>();
		
		/*封装分页查询基本数据*/
		cc.setCurrentPage(currentPage2);//当前页
		cc.setPageSize(pageSize2);//每页记录数
		cc.setTotalCount(totalCount);//总记录数
		if(totalCount%pageSize2==0) {//整除取商,totalCount和pageSize2必须是整型
			totalPages=totalCount/pageSize2;
		}else {//不能整除取商+1
			totalPages=totalCount/pageSize2+1;
		}
		/*获取start和end值*/
		if(start1==null&&end1==null) {//初始页面、跳转时页面和点击具体页码页面起止位
			if(currentPage2%pageButtonNum2==0) {//当前页是要显示数目的整数倍
				start2=currentPage2-pageButtonNum2+1;//起始位置,每pageButtonNum2换一个区间
				end2=currentPage2;//结束位置
			}else {//当前页不是要显示数目的整数倍
				if(totalPages<pageButtonNum2) {//总页数或当前页小于要显示页的数目
					start2=1;
					end2=totalPages;
				}else {//总页数大于要显示页数目
					if(currentPage2<totalPages/pageButtonNum2*pageButtonNum2) {//不是最后一个区页
						if(currentPage2<pageButtonNum2)
							start2=1;
						else
							start2=currentPage2/pageButtonNum2*pageButtonNum2+1;						
						end2=currentPage2/pageButtonNum2*pageButtonNum2+pageButtonNum2;
					}else {//是最后一个区页
						start2=currentPage2/pageButtonNum2*pageButtonNum2+1;
						end2=totalPages/pageButtonNum2*pageButtonNum2+totalPages%pageButtonNum2;
					}
				}
			}
		}else {//上一页或下一页时起止位置
			start2=Integer.parseInt(start1);
			end2=Integer.parseInt(end1);
			if(nextOrPre.equals("previous")){//点击上一页
				if(currentPage2%pageButtonNum2==0) {//进入上一个区间的尾页
					start2=currentPage2-pageButtonNum2+1;
					end2=currentPage2;
				}
			}else {//点击下一页
				if(currentPage2%pageButtonNum2==1) {//进入下一个区间的第一页
					start2=currentPage2;
					if(currentPage2<totalPages/pageButtonNum2*pageButtonNum2+1)
						end2=start2+pageButtonNum2-1;
					else
						end2=start2+totalPages%pageButtonNum2-1;//最后不满pageButtonNum2数目的页
				}
			}			
		}
		/*封装页码集合*/
		for(int i=start2;i<=end2;i++) {
			itemList.add(i);
		}
		cc.setTotalPage(totalPages);
		List<FileManageVo> listFile = fdi.getFileInfo(type,uv.getAv().getAuthVal(),fm,cc,uv);
		request.setAttribute("fileList", listFile);//文件集合
		request.setAttribute("itemList", itemList);//页码集合
		request.setAttribute("currentPage", currentPage2);//当前页
		request.setAttribute("totalPage", totalPages);//总页数
		request.setAttribute("totalCount", totalCount);//总记录数
		request.setAttribute("start", start2);//起始武林风
		request.setAttribute("end", end2);//结束位置
		return "fileInfo";//跳转到文件列表
	}
	
	
	public FileManageVo getFm() {
		return fm;
	}

	public void setFm(FileManageVo fm) {
		this.fm = fm;
	}
	
	public File getMyFile() {
		return myFile;
	}

	public void setMyFile(File myFile) {
		this.myFile = myFile;
	}

	public String getMyFileFileName() {
		return myFileFileName;
	}

	public void setMyFileFileName(String myFileFileName) {
		this.myFileFileName = myFileFileName;
	}
}
