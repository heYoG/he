package util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class PageUtil{
	
	/**
	 * 1.获取分页数据
	 * 2.按[start,end]区间显示可点击页码
	 * 3.单个区间的显示
	 * @param cc		工具类
	 * @param uv		用户类
	 * @param request	请服务请求对象
	 * @return			工具类引用
	 */
	public static CommenClass pageMethod(CommenClass cc,HttpServletRequest request) {
		
		String currentPage1 = request.getParameter("currentPage");//当前页
		String start1=request.getParameter("start");//开始页,用于列表显示可点击页码数的开始位置
		String end1=request.getParameter("end");//结束页,用于列表显示可点击页码数的结束位置
		String nextOrPre=request.getParameter("nextOrPre");//判断是点击上一页还是下一页
		String pageSize1 = CommenClass.getProperty("pageSize");//每页显示记录数
		String pageButtonNum1 = CommenClass.getProperty("pageButtonNum");//显示可点击页码数
		currentPage1=currentPage1==null?"1":currentPage1;//设置当前页的值,为空取1
		int currentPage2=Integer.parseInt(currentPage1);//转为整型
		int pageSize2=Integer.parseInt(pageSize1);//转为整型
		int totalPages=0;//总页数
		int pageButtonNum2=Integer.parseInt(pageButtonNum1);//转为整型
		int start2=0;//显示页码开始位置
		int end2=0;//显示页码结束位置
		List<Integer> itemList=new ArrayList<Integer>();//页码集合
		
		/*获取总页数*/
		if(cc.getTotalCount()%pageSize2==0) {//整除取商,totalCount和pageSize2必须是整型
			totalPages=cc.getTotalCount()/pageSize2;
		}else {//不能整除取商+1
			totalPages=cc.getTotalCount()/pageSize2+1;
		}
		if(start1==null&&end1==null) {//初始页面、跳转时页面和点击具体页码页面起止位
			if(currentPage2%pageButtonNum2==0) {//当前页是要显示数目的整数倍
				start2=currentPage2-pageButtonNum2+1;//起始位置,每pageButtonNum2换一个区间
				end2=currentPage2;//结束位置
			}else {//当前页不是要显示数目的整数倍
				if(totalPages<pageButtonNum2) {//总页数或当前页小于要显示页的数目
					start2=1;
					end2=totalPages;
				}else {//总页数大于要显示页数目
					if(currentPage2<totalPages/pageButtonNum2*pageButtonNum2) {//不是最后一个区页
						if(currentPage2<pageButtonNum2)
							start2=1;
						else
							start2=currentPage2/pageButtonNum2*pageButtonNum2+1;						
						end2=currentPage2/pageButtonNum2*pageButtonNum2+pageButtonNum2;
					}else {//是最后一个区页
						start2=currentPage2/pageButtonNum2*pageButtonNum2+1;
						end2=totalPages/pageButtonNum2*pageButtonNum2+totalPages%pageButtonNum2;
					}
				}
			}
		}else {//上一页或下一页时起止位置
			start2=Integer.parseInt(start1);
			end2=Integer.parseInt(end1);
			if(nextOrPre.equals("previous")){//点击上一页
				if(currentPage2%pageButtonNum2==0) {//进入上一个区间的尾页
					start2=currentPage2-pageButtonNum2+1;
					end2=currentPage2;
				}
			}else {//点击下一页
				if(currentPage2%pageButtonNum2==1) {//进入下一个区间的第一页
					start2=currentPage2;
					if(currentPage2<totalPages/pageButtonNum2*pageButtonNum2+1)
						end2=start2+pageButtonNum2-1;
					else
						end2=start2+totalPages%pageButtonNum2-1;//最后不满pageButtonNum2数目的页
				}
			}			
		}
		/*封装页码集合*/
		for(int i=start2;i<=end2;i++) {
			itemList.add(i);
		}
		/*封装分页数据*/
		cc.setCurrentPage(currentPage2);
		cc.setEnd(end2);
		cc.setItemList(itemList);
		cc.setPageSize(pageSize2);
		cc.setStart(start2);
		cc.setTotalCount(cc.getTotalCount());
		cc.setTotalPage(totalPages);
		if(StringUtils.isEmpty(cc.getType()))
			cc.setType(cc.getType());
		return cc;
	}
}
