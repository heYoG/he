package com.dj.seal.highcharts.service.aip;

import java.util.List;

import com.dj.seal.highcharts.form.ChartServiceForm;
import com.dj.seal.structure.dao.po.ChartReport;

public interface ChartService {
	
	public List<ChartReport> showRecordDataByDept(ChartServiceForm serviceForm);

}
