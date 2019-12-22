package action.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import dao.adDao.impl.AdDaoImpl;
import util.CommenClass;
import util.PageUtil;
import vo.adVo.AdVo;
import vo.userVo.UserVo;

@WebServlet(name="adManage",urlPatterns = "*.servletM")
public class AdManageServlet implements Servlet {
	private AdDaoImpl adi=new AdDaoImpl();
	private CommenClass cc=new CommenClass();
	private UserVo userVo=null;
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		HttpServletRequest request2 = ServletActionContext.getRequest();
		HttpServletResponse response2 = ServletActionContext.getResponse();
		HttpSession session = request2.getSession(false);
		request2.setCharacterEncoding("UTF-8");
		response2.setContentType("type=text/html;charset=UTF-8");
		response2.setCharacterEncoding("UTF-8");
		PrintWriter out = response2.getWriter();
		if (session != null) {// 系统登录过期
			userVo= (UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request2.setAttribute("user", "outtime");
			out.print("<script type='text/javascript'>alert('登录已失效,请重新登录!')</script>");
			out.print("<script type='text/javascript'>window.open('../../index.jsp','_parent','')</script>");
			return;//中止程序继续
		}
		int adCount = adi.getAdCount();
		cc.setTotalCount(adCount);
		cc = PageUtil.pageMethod(cc,request2);
		List<AdVo> ads = adi.getAds(cc);
		request2.setAttribute("adList", ads);
		request2.setAttribute("pageData", cc);
		request2.getRequestDispatcher("/jsp/ad/adManage.jsp").forward(request2, response2);
	}
}
