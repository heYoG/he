package com.dj.seal.origunJob.job;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;

import com.dj.seal.hotel.po.NSHRecordPo;
import com.dj.seal.hotel.service.api.NSHRecordService;
import com.dj.seal.util.properyUtil.DJPropertyUtil;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class CheckJob{
	Logger logger = LogManager.getLogger(CheckJob.class.getName());
	private  NSHRecordService record_srv;
	
	public NSHRecordService getRecord_srv() {
		return record_srv;
	}


	public void setRecord_srv(NSHRecordService record_srv) {
		this.record_srv = record_srv;
	}


	private static Object getBean(String name) {
		Object obj = MyApplicationContextUtil.getContext().getBean(name);
		return obj;
	}

	
	public void executes(){
		logger.info("================后督数据导出开始==================");
		FileOutputStream out = null;
		try {
		deleteOldFile(DJPropertyUtil.getPropertyValue("bank_check_path"));//删除7天前的后督数据
		SimpleDateFormat sdfFileName = new SimpleDateFormat("yyyyMMdd");
		Date nowTime = new Date(System.currentTimeMillis());
		Date afterDay = new Date(nowTime.getTime()+(24*60*60*1000));
		String nowTimeStr = sdfFileName.format(nowTime);
		String fileName =DJPropertyUtil.getPropertyValue("bank_check_path")+nowTimeStr+File.separatorChar;
		String crlName = DJPropertyUtil.getPropertyValue("bank_check_path")+nowTimeStr+File.separatorChar;
		//创建文件夹
		File dirFile = new File(fileName);
		if(!dirFile.exists()){
			dirFile.mkdirs();
		}
		String afterTimeStr = sdfFileName.format(afterDay);
		fileName += "t_nshrecord"+nowTimeStr+".txt";
		crlName += "t_nshrecord"+nowTimeStr+".ctl";//文件最后生成，验证文件已经结束，后督系统根据此文件为标示判断是否结束
		List<NSHRecordPo> records = new ArrayList<NSHRecordPo>();
		String sql = "select * from T_NSHRECORD t where CUPLOADTIME between to_date('"+nowTimeStr+"','yyyy-mm-dd') and to_date('"+afterTimeStr+"','yyyy-mm-dd')";		
		//logger.info("导出后督数据sql:"+sql);
		records = record_srv.getRecordsBySql(sql);
		File checkFile = new File(fileName);
		if(!checkFile.exists()){
				checkFile.createNewFile();
			}
			out = new FileOutputStream(checkFile);
			StringBuilder sb = null;
			for (NSHRecordPo nshRecordPo : records) {
				sb = new StringBuilder();
				if(nshRecordPo.getCeid()!=null&&!"".equals(nshRecordPo.getCeid())){
					sb.append(nshRecordPo.getCid()).append("|"); // 通过UUID生成
					sb.append(nshRecordPo.getCip()).append("|"); //访问IP地址
					sb.append(nshRecordPo.getCuploadtime()).append("|"); // 上传时间
					sb.append(nshRecordPo.getCaseseqid()).append("|"); // 交易流水号
					sb.append(nshRecordPo.getTrancode()).append("|"); // 交易码
					sb.append(nshRecordPo.getTellerid()).append("|"); // 柜员号
					sb.append(nshRecordPo.getOrgunit()).append("|"); // 机构号
					sb.append(nshRecordPo.getCeid()).append("|");  //CE账号
					sb.append(nshRecordPo.getRemarks()).append("|"); //密码 
					sb.append(nshRecordPo.getD_jioyje()).append("|"); //交易金额
					sb.append(new java.sql.Date(nshRecordPo.getD_trandt().getTime()).toString().replace("-","")).append("|"); //交易时间
					sb.append(nshRecordPo.getD_cuacno()).append("|"); //交易账号
					sb.append(nshRecordPo.getVoucherno()).append("|"); //凭证编号
					sb.append(nshRecordPo.getD_tranname()).append("|"); //交易名称
					/*添加新增字段*/
					sb.append(nshRecordPo.getTranorgname()).append("|");//机构名称
					sb.append(nshRecordPo.getAuthtellerno()).append("|");//授权柜员号
					sb.append(nshRecordPo.getValcode()).append("|");//验证码
					sb.append(nshRecordPo.getStatus()).append("|");//状态码(0:无纸化,1:有纸化)
					sb.append(nshRecordPo.getRequirednum()).append("|");//请求次数
					sb.append(nshRecordPo.getBp1()).append("|");//凭证编号
					sb.append("\r\n");							
					out.write(sb.toString().getBytes("GBK"));
				}
				}
			File crkFile = new File(crlName);
			if(!crkFile.exists()){
				try {
					crkFile.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(out != null){
					out.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		logger.info("================后督数据导出结束==================");
	}
	
	/**
	 * 遍历文件删除7天之前的文件
	 * >7改为>=7
	 * modify 20180802
	 * @throws ParseException 
	 */
	public void deleteOldFile(String rootPath) throws ParseException{
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		Date nowDate = new Date(System.currentTimeMillis());
		File root = new File(rootPath);
		File[] files = root.listFiles();
		for (File file : files) {
			if(file.isDirectory()){
				String filename = file.getName();
				Date fileDate = sdf1.parse(filename);
				if((nowDate.getTime()-fileDate.getTime())/(24*60*60*1000)>=7){
					deleteOldFile(file.getAbsolutePath());
					file.delete();
				}
			}else{
				file.delete();
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		CheckJob cj=new CheckJob();
		JobExecutionContext arg0 = null;
		//cj.execute(arg0);
	}
}
