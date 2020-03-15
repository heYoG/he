package util.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.dispatcher.ng.filter.StrutsPrepareAndExecuteFilter;

/**
 * 	自定义过滤器
 * @author Administrator
 *
 */
public class StrutsFilter extends StrutsPrepareAndExecuteFilter {
	static Logger log=LogManager.getLogger(StrutsFilter.class.getName());
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)req;
		/*不过滤含webService字符的求Web Service请求*/
		if(request.getRequestURI().contains("webService"))
			/*过滤含webService的请求路径,从此继续执行,将请求转发给过滤器链上下一个对象。
			 * 这里的下一个指的是下一个filter，如果没有filter那就是你请求的资源*/
			chain.doFilter(req, res);
		else
			/*必须是super.写法，否则变成递归调用;chain为过滤链,没有时直接执行doFileter(req,res)*/
			super.doFilter(req, res, chain);
	}
	
	@Override
	public void destroy() {
		log.info("filter has been destoyed...");
		super.destroy();
	}
}
