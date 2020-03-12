/**    
 * @{#} TableMonthConfigPO.java Create on 2015-7-19 下午10:40:56    
 *    
 * Copyright (c) 2013-2018 by 东软集团股份有限公司. All rights reserved. 
 */ 
package com.neusoft.po;

import com.dj.seal.util.dao.BasePO;

/**    
 * @author <a href="mailto:chenjuheng@neusoft.com">陈钜珩</a>   
 * @version 1.0    
 */
public class TableMonthConfigPO extends BasePO {
	private static final long serialVersionUID = -6954630474162941534L;
	private String beginyear;  // 开始年份
	private String beginmonth; // 开始月份
	private String endyear;    // 结束年份
	private String endmonth;   // 结束月份
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return 0;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBeginyear() {
		return beginyear;
	}
	public void setBeginyear(String beginyear) {
		this.beginyear = beginyear;
	}
	public String getBeginmonth() {
		return beginmonth;
	}
	public void setBeginmonth(String beginmonth) {
		this.beginmonth = beginmonth;
	}
	public String getEndyear() {
		return endyear;
	}
	public void setEndyear(String endyear) {
		this.endyear = endyear;
	}
	public String getEndmonth() {
		return endmonth;
	}
	public void setEndmonth(String endmonth) {
		this.endmonth = endmonth;
	}
	
}

