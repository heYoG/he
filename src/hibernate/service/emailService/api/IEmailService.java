package hibernate.service.emailService.api;

import java.io.Serializable;
import java.util.List;

import util.CommenClass;
import vo.emailVo.EmailVo;

public interface IEmailService<T> {
	/**
	 * @function	发送邮件
	 * @param 		ev 邮件类型变量
	 * @return		插入数据返回值,主键
	 */
	public Serializable newEmail(EmailVo ev);
	
	/**
	 * @function	删除邮件
	 * @param t		泛型变量
	 * @param id	邮件id(主键),hibernate要根据主键先获取对象,再删除
	 * @return		删除数据返回值
	 */
	public int deleteEmail(T t,Serializable id);
	
	/**
	 * @function	修改邮件状态
	 * @param t		泛型变量
	 * @param id	要修改邮件id
	 * @return		修改数据返回值
	 */
	public int update(T t,int id);
	
	/**
	 * @function	获取单条邮件信息
	 * @param t		泛型变量
	 * @param id	要查找的邮件id
	 * @return
	 */
	public T emailInfo(T t,int id);
	
	/**
	 * @function	分页查询邮件记录
	 * @param t		泛型变量
	 * @param cc	通用类变量
	 * @param flag	查询类型	0：收件箱	1：发送箱	2：草稿箱	3：已删除邮件
	 * @return
	 */
	public List<T> emailPageList(T t,CommenClass cc,String flag);
	
	/**
	 * @function		统计记录数
	 * @param	flag	查询类型	0：收件箱	1：发送箱	2：草稿箱	3：已删除邮件
	 * @return			记录数
	 */
	public long getEmailCount(String flag);
}
