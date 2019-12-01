package action.userAction;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import hibernate.dao.userDao.impl.AuthDaoImpl;
import vo.userVo.AuthorityVo;

@SuppressWarnings(value={"all"})
public class AuthorityAction extends ActionSupport implements ModelDriven<AuthorityVo> {
	HttpServletRequest request=ServletActionContext.getRequest();
	AuthorityVo av=new AuthorityVo();
	AuthDaoImpl adi=new AuthDaoImpl();
	List<AuthorityVo> list=null;
	
	public String authInfo(){
		
		return "authInfo";
	}
	
	public String authList(){
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
