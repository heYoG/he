package action.test;

import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ActionSupport;

import test.propertyDriven.DriverVo;

@Controller(value="driverAct")
public class DriverAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	DriverVo dv=new DriverVo();
	
	public String execute() {
		System.out.println("name:"+dv.getName()+",age:"+dv.getAge()+",degress:"+dv.getDegress());
		return "test";
	}

	public DriverVo getDv() {
		return dv;
	}

	public void setDv(DriverVo dv) {
		this.dv = dv;
	}
	
}
