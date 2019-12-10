package action.deptAction;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import hibernate.dao.deptDao.impl.DeptDaoImpl;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import util.CommenClass;
import util.PageUtil;
import vo.deptVo.DeptVo;
import vo.userVo.UserVo;

@SuppressWarnings(value= {"all"})
public class DeptAction extends ActionSupport implements ModelDriven<DeptVo>{
	
	private static DeptDaoImpl<DeptVo> dept=new DeptDaoImpl<DeptVo>();
	DeptVo dv=new DeptVo();
	CommenClass cc=new CommenClass();
	UserVo uv=null;
	
	/*获取部门信息列表*/
	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		if(session!=null)//判断session是否已过期
			uv=(UserVo) session.getAttribute("userVo");
		if(uv==null) {//session过期则跳转到登录页面
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		long count = dept.getCount(dv);//获取记录总数
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<DeptVo> list=dept.getDeptInfos(dv,cc);
		request.setAttribute("deptList", list);
		request.setAttribute("pageData", cc );
		return "deptList";
	}
	
	/*添加部门*/
	public String addDept() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		if(session!=null)//判断session是否已过期
			uv=(UserVo) session.getAttribute("userVo");
		if(uv==null) {//session过期则跳转到登录页面
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String deptName2 = dv.getDeptName();
		String deptName = request.getParameter("deptName");
		int flag=0;
		dv.setDeptName(deptName);
		dv.setFlag(flag);
		Serializable addDept = dept.addDept(dv);
		/*分页*/
		long count = dept.getCount(dv);//获取记录总数
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<DeptVo> list=dept.getDeptInfos(dv,cc);
		request.setAttribute("deptList", list);
		request.setAttribute("pageData", cc );
		return "deptList";
	}
	
	/*删除部门*/
	public String delDept() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(false);
		if(session!=null)//判断session是否已过期
			uv=(UserVo) session.getAttribute("userVo");
		if(uv==null) {//session过期则跳转到登录页面
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		String id=request.getParameter("id");
		dept.delDept(dv, Integer.parseInt(id));
		/*分页*/
		long count = dept.getCount(dv);//获取记录总数
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<DeptVo> list=dept.getDeptInfos(dv,cc);
		request.setAttribute("deptList", list);
		request.setAttribute("pageData", cc );
		return "deptList";
	}
	
	/**
	 *	判断部门名称是否已存在
	 *	返回json类型数据,前端通过ajax判断 
	 */
	public void isExit_DeptName() {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");//响应设置编码,是否可能返回乱码数据
			PrintWriter out = response.getWriter();
			JsonConfig jsonConfig = new JsonConfig();
			String deptName = request.getParameter("deptNm");
			String decode = URLDecoder.decode(deptName, "utf-8");
			List<DeptVo> list = dept.getDeptInfo(dv, decode);
			jsonConfig.setExcludes(new String[] {"user"});//去除级联中的user属性
			JSONArray ja=new JSONArray();
			String ret=null;
			if(list.size()>0)
				ret=ja.fromObject(list, jsonConfig).toString();
			out.print(ret);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public DeptVo getModel() {
		return dv;
	}
	public DeptVo getDv() {
		return dv;
	}
	public void setDv(DeptVo dv) {
		this.dv = dv;
	}

}
