package action.userAction;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import hibernate.dao.userDao.impl.AuthDaoImpl;
import util.CommenClass;
import vo.userVo.AuthorityVo;
import vo.userVo.UserVo;

@SuppressWarnings(value={"all"})
public class AuthorityAction extends ActionSupport implements ModelDriven<AuthorityVo> {
	HttpServletRequest request=ServletActionContext.getRequest();
	AuthorityVo av=new AuthorityVo();
	AuthDaoImpl adi=new AuthDaoImpl();
	List<AuthorityVo> list=null;
	UserVo userVo=null;
	
	public String authInfo(){
		
		return "authInfo";
	}
	
	public String authList(){
		HttpSession session = request.getSession(false);
		if(session!=null) {
			userVo=(UserVo) session.getAttribute(CommenClass.CURRENTUSERSESSION);
		}
		if(userVo==null) {
			request.setAttribute("user", "outtime");
			return ERROR;
		}
		int count = adi.getCount();
		list=adi.pageListAuth(1, 10, 0, av);
		request.setAttribute("listInfo", list);
		return "authList";
	}

	@Override
	public AuthorityVo getModel() {
		return av;
	}

}
