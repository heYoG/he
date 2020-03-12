package com.dj.seal.util.compara;

import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.structure.dao.po.SysFunc;

@SuppressWarnings("unchecked")
public class CapSysFunc implements Comparator{
	
	static Logger logger = LogManager.getLogger(CapSysFunc.class.getName());

	@Override
	public int compare(Object o1, Object o2) {
		SysFunc func1=(SysFunc)o1;
		SysFunc func2=(SysFunc)o2;
		if(Integer.valueOf(func1.getFunc_group())>Integer.valueOf(func2.getFunc_group())){
			return 1;
		}else if(func1.getFunc_group().equals(func2.getFunc_group())){
			if(func1.getFunc_value()>func2.getFunc_value()){
				return 1;
			}
		}
		return 0;
	}
	
}
