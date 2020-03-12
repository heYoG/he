package com.dj.seal.hotel.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dj.seal.hotel.vo.ChartVO;

public class ChartServiceImpl {
	
	static Logger logger = LogManager.getLogger(ChartServiceImpl.class.getName());
	
//图表生成
	
	public List<ChartVO> getTodayShenHeData() {
		List<ChartVO> list = new ArrayList<ChartVO>();
		list.add(new ChartVO("通过", 100));
		list.add(new ChartVO("未通过", 50));
		return list;
	}
	
	public List<ChartVO> getTodayYeWuData() {
		List<ChartVO> list = new ArrayList<ChartVO>();
		list.add(new ChartVO("入网登记单", 1000));
		list.add(new ChartVO("业务变更单", 600));
		return list;
	}
	
}
