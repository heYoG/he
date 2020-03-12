package controllers.deptAction;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.URLDecoder;
import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import dao.deptDao.impl.DeptDaoImpl;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import util.CommenClass;
import util.PageUtil;
import vo.deptVo.DeptVo;
import vo.userVo.UserVo;

@Controller
@RequestMapping("deptCtrl")
public class DeptAction{
	static Logger log=LogManager.getLogger(DeptAction.class.getName());
	@Autowired
	private DeptDaoImpl<DeptVo> dept;
	@Autowired
	private DeptVo dv;
	@Autowired
	private CommenClass cc;
	private UserVo uv=null;
	
	/*获取部门信息列表*/
	@RequestMapping("/deptInfo")
	public String deptList(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session!=null)//判断session是否已过期
			uv=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		if(uv==null) {//session过期则跳转到登录页面
			request.setAttribute("user", "outtime");
			return "redirect:/jsp/errorsPage.jsp";	
		}
		long count = dept.getCount(dv);//获取记录总数
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<DeptVo> list=dept.getDeptInfos(dv,cc);
		request.setAttribute("deptList", list);
		request.setAttribute("pageData", cc );
		log.info("查询部门列表信息...");
		return "/dept/deptList";
	}
	
	/*添加部门*/
	@RequestMapping("/addDept")
	public String addDept(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session!=null)//判断session是否已过期
			uv=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		if(uv==null) {//session过期则跳转到登录页面
			request.setAttribute("user", "outtime");
			return "redirect:/jsp/errorsPage.jsp";
		}
		String deptName = request.getParameter("deptName");
		int flag=0;
		dv.setDeptName(deptName);
		dv.setFlag(flag);
		Serializable addDept = dept.addDept(dv);
		if(!addDept.equals("0"))
			log.info("添加部门成功!");
		else
			log.info("添加部门失败!");
		/*分页*/
		long count = dept.getCount(dv);//获取记录总数
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<DeptVo> list=dept.getDeptInfos(dv,cc);
		request.setAttribute("deptList", list);
		request.setAttribute("pageData", cc );
		return "/dept/deptList";
	}
	
	/*删除部门*/
	@RequestMapping("/deleteDept")
	public String delDept(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session!=null)//判断session是否已过期
			uv=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		if(uv==null) {//session过期则跳转到登录页面
			request.setAttribute("user", "outtime");
			return "redirect:/jsp/errorsPage.jsp";
		}
		String id=request.getParameter("id");
		dept.delDept(dv, Integer.parseInt(id));
		log.info("删除部门成功!");
		/*分页*/
		long count = dept.getCount(dv);//获取记录总数
		cc.setTotalCount((int)count);
		cc = PageUtil.pageMethod(cc, request);
		List<DeptVo> list=dept.getDeptInfos(dv,cc);
		request.setAttribute("deptList", list);
		request.setAttribute("pageData", cc );
		return "/dept/deptList";
	}
	
	/**
	 *	判断部门名称是否已存在
	 *	返回json类型数据,前端通过ajax判断 
	 */
	@RequestMapping("/deptNameIfExit")
	public void isExit_DeptName(HttpServletRequest request,HttpServletResponse response) {
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

}
