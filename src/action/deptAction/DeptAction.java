package action.deptAction;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import hibernate.dao.deptDao.impl.DeptDaoImpl;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import vo.deptVo.DeptVo;

@SuppressWarnings(value= {"all"})
public class DeptAction extends ActionSupport implements ModelDriven<DeptVo>{
	
	private static DeptDaoImpl<DeptVo> dept=new DeptDaoImpl<DeptVo>();
	DeptVo dv=new DeptVo();
	/**
	 * 获取部门信息列表
	 */
	@Override
	public String execute() {
		HttpServletRequest request = ServletActionContext.getRequest();
		
		List<DeptVo> list=dept.getDeptInfos(dv);
		request.setAttribute("deptList", list);
		return "deptList";
	}
	
	/*添加部门*/
	public String addDept() {
		HttpServletRequest request = ServletActionContext.getRequest();
		//int deptID=4;
		String deptName2 = dv.getDeptName();
		String deptName = request.getParameter("deptName");
//		System.out.println("deptName:"+deptName);
		int flag=0;
		//dv.setDeptID(deptID);//deptID数据库自动递增
		dv.setDeptName(deptName);
		dv.setFlag(flag);
		Serializable addDept = dept.addDept(dv);
//		System.out.println("addDept:"+addDept);
		List<DeptVo> list=dept.getDeptInfos(dv);
		request.setAttribute("deptList", list);
//		System.out.println("addDept:"+addDept);
		return "deptList";
	}
	
	/*删除部门*/
	public String delDept() {
		HttpServletRequest request = ServletActionContext.getRequest();
		String id=request.getParameter("id");
		dept.delDept(dv, Integer.parseInt(id));
		List<DeptVo> list=dept.getDeptInfos(dv);
		request.setAttribute("deptList", list);
		return "deptList";
	}
	
	public void isExit_DeptName() {//判断部门名称是否已存在
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
//			request.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("UTF-8");
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
