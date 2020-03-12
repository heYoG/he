package com.dj.seal.accumulationFund.service.api;

public interface IAccumulationDeal {
	public int addSealToAccumulation();//回单加盖印章
	
	public boolean copyFile(String originPath,String savePath);//复制清单文件到盖章文件保存目录
	
	public boolean txtToPdf(String openPath,String savePath);//txt转pdf
	
	public boolean clearNoSealAccumulation();//清理未盖章回单文件
	
	public boolean clearSealAccumulation();//清理盖章后回单文件
}
