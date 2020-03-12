package com.dj.seal.util.exception;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ExceptionHandler;
import org.apache.struts.config.ExceptionConfig;

public class GeneralExceptionHandler extends ExceptionHandler {
	
	static Logger logger = LogManager.getLogger(GeneralExceptionHandler.class.getName());

	@Override
	public ActionForward execute(Exception ex, ExceptionConfig ec,
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws ServletException {
		
		//�жϵ�ǰ�判断当前异常是不是GeneralException的实例,如果不是它的实例说明是在开发时编码错误造成,我们不给于处理
		if(ex instanceof GeneralException){
			GeneralException gException = (GeneralException)ex;
			String key = gException.getKey();
			ActionMessage message = null;
			//如果key不为空,说明用我们自己的设置的key值
			if(key!=null){
				//判断value值以确定如果去构造message
				if(gException.getValues()==null){
					message = new ActionMessage(key);
				}else{
					message = new ActionMessage(key,gException.getValues());
				}
			}else{
				//如果key为空,则使用我们配置中的key值
				key = ec.getKey();
				message = new ActionMessage(key,gException.getMessage());
			}
			String path = ec.getPath();
			ActionForward forward = new ActionForward(path) ;
			this.storeException(request, key, message, forward, "request");
			return forward;
		}
		return super.execute(ex, ec, mapping, form, request, response);
	}
	
}
