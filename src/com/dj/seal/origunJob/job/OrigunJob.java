package com.dj.seal.origunJob.job;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.dj.seal.hotel.service.impl.HotelAdvertServiceImpl;
import com.dj.seal.organise.service.impl.SysDeptService;
import com.dj.seal.organise.service.impl.UserService;
import com.dj.seal.sealBody.service.impl.SealBodyServiceImpl;
import com.dj.seal.sealTemplate.service.impl.SealTemplateServiceImpl;
import com.dj.seal.structure.dao.po.SysDepartment;
import com.dj.seal.util.Constants;
import com.dj.seal.util.exception.GeneralException;
import com.dj.seal.util.spring.MyApplicationContextUtil;

public class OrigunJob implements Job{
	static Logger logger = LogManager.getLogger(OrigunJob.class.getName());
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		logger.info("================组织结构同步开始==================");
		SysDeptService dept_srv = (SysDeptService) getObject("ISysDeptService");
		UserService user_srv = (UserService) getObject("IUserService");
		SealBodyServiceImpl seal_srv = (SealBodyServiceImpl) getObject("ISealBodyService");
		SealTemplateServiceImpl sealTemp_srv = (SealTemplateServiceImpl) getObject("ISealTempService");
		HotelAdvertServiceImpl advert_srv = (HotelAdvertServiceImpl) getObject("hotelAdverService");
		List<SysDepartment> allDepts = null;
		List<String> orgList = new ArrayList<String>();
		
		//判断控制文件是否存在，如果存在则执行更新操作。否则等待40分钟后再执行一次
		String dataPath = Constants.getProperty("orgdata_path");//数据文件路径
		File crtlFile = new File(dataPath+"ok.ctl");
		
		if(crtlFile.exists()){
		logger.info("================控制文件ok.ctl存在开始同步==================");
		List<String> phbieList = readOrigunData("phbie.unl");
		try {
			Thread.currentThread();
			Thread.sleep(1*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<String> pjgcsList = readOrigunData("pjgcs.unl");
		orgList.addAll(phbieList);
		orgList.addAll(pjgcsList);
		//threeLevel("pjggx.unl");
		//判断是否有删除的部门，如果有更新到数据库部门表中，并将该部门下的人员，印章做删除处理
//		try {
//			allDepts = dept_srv.showAll();
//			for (SysDepartment dept : allDepts) {
//				if(dept.getDept_no().equals(Constants.UNIT_DEPT_NO)){
//					continue;
//				}
//				if(orgList.contains(dept.getBank_dept())){
//					continue;
//				}
//				//删除印章和印章模型
//				List<SealBody> seals = seal_srv.showSealBodyByDeptNo(dept.getDept_no());
//				for (SealBody sealBody : seals) {
//					sealTemp_srv.deleteTemp(sealBody.getTemp_id().toString());
//					seal_srv.deleteSeal(sealBody.getSeal_id().toString());
//				}
//				//删除广告
//				List<HotelAdvertPO> adverts = advert_srv.getDeptAdverts(dept.getDept_no());
//				for (HotelAdvertPO hotelAdvertPO : adverts) {
//					advert_srv.deleteAdvert(hotelAdvertPO.getAd_id());
//				}
//				//删除用户
//				List<SysUser> users = dept_srv.showUsersbyDept(dept.getDept_no());
//				for (SysUser sysUser : users) {
//					user_srv.deleteSysUser(sysUser.getUser_id());
//				}
//				//删除部门
//				dept_srv.deleteDept(dept.getDept_no());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		//crtlFile.delete();
		}else{
			logger.info("================控制文件ok.ctl不存在不做任何操作==================");
		}
		logger.info("================组织结构同步结束==================");
	}
	
	public List<String> readOrigunData(String filename){
		List<String> arrayList = new ArrayList<String>();//用于存放银行的组织机构号
		SysDeptService dept_srv = (SysDeptService) getObject("ISysDeptService");
		String dataPath = Constants.getProperty("orgdata_path");//数据文件路径
		//读取数据文件，首先更新一级部门数据信息
		File file = new File(dataPath + filename);
		InputStreamReader read = null;
		BufferedReader bufRead = null;
		try {
			read = new InputStreamReader(new FileInputStream(file),"gbk");
			bufRead = new BufferedReader(read);
			String tempString = null;
			SysDepartment sysdept = null;//部门po
			SysDepartment hasDept = null;//已经存在的部门
			String[] dataArry = null;
			while((tempString = bufRead.readLine())!= null){
				logger.info(tempString);
				//System.out.println("line "+line+": "+tempString);
				dataArry = tempString.split("\\|");
				if(filename.equals("phbie.unl")){
					//首先根据序列号查询是否有次部门，如果存在则更新，否则增加
					if((hasDept = dept_srv.showDeptByBankNo(dataArry[0]))!=null){
						hasDept.setBank_dept(dataArry[0]);//银行级别序列号
						hasDept.setDept_name(dataArry[1]);//银行名称
						dept_srv.updateDept(hasDept);
					}else{
						sysdept = new SysDepartment();
						sysdept.setDept_tab(dept_srv.getMaxDeptTab(Constants.UNIT_DEPT_NO));
						sysdept.setBank_dept(dataArry[0]);//银行级别序列号
						sysdept.setDept_name(dataArry[1]);//银行名称
						sysdept.setDept_parent(Constants.UNIT_DEPT_NO);
						dept_srv.addDept(sysdept);
					}
					arrayList.add(dataArry[0]);
				}else{
					//首先根据序列号查询是否有次部门，如果存在则更新，否则增加
					if((hasDept = dept_srv.showDeptByBankNo(dataArry[1]))!=null){
						//根据银行部门上级序列号获取对应的无纸化部门编号
						SysDepartment parent_dept = dept_srv.showDeptByBankNo(dataArry[0]);
						hasDept.setDept_parent(parent_dept.getDept_no());
						hasDept.setBank_dept(dataArry[1]);//银行级别序列号
						hasDept.setDept_name(dataArry[2]);//银行名称
						dept_srv.updateDept(hasDept);
					}else{
						sysdept = new SysDepartment();
						SysDepartment parent_dept = dept_srv.showDeptByBankNo(dataArry[0]);
						sysdept.setDept_tab(dept_srv.getMaxDeptTab(parent_dept.getDept_no()));
						sysdept.setBank_dept(dataArry[1]);//银行级别序列号
						sysdept.setDept_name(dataArry[2]);//银行名称
						sysdept.setDept_parent(parent_dept.getDept_no());
						dept_srv.addDept(sysdept);
					}
					arrayList.add(dataArry[1]);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralException e) {
			e.printStackTrace();
		}finally{
			try {
				if(bufRead != null){
					bufRead.close();
				}
				if(read != null){
					read.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//file.delete();
		}
		
		return arrayList;
	}
	
	public static void threeLevel(String filename){
		SysDeptService dept_srv = (SysDeptService) getObject("ISysDeptService");
		String dataPath = Constants.getProperty("orgdata_path");//数据文件路径
		//读取数据文件，首先更新一级部门数据信息
		File file = new File(dataPath + filename);
		InputStreamReader read = null;
		BufferedReader bufRead = null;
		try {
			read=new InputStreamReader(new FileInputStream(file),"gbk");
			bufRead=new BufferedReader(read);
			String[] dataArray=null;
			String tempString=null;
			String dept_parent=null;
			while((tempString = bufRead.readLine())!= null){
				logger.info(tempString);
				dataArray = tempString.split("\\|");
				if(!dept_srv.getIsnull(dataArray[0])){
					SysDepartment sys=new SysDepartment();
					sys=dept_srv.getDeptById(dataArray[0]);
					dept_parent=dept_srv.showDeptByBankNo(dataArray[1]).getDept_no();
					if(!sys.getDept_parent().equals(dept_parent)){
						sys.setDept_parent(dept_parent);
						dept_srv.updDeptById(sys);
					}
				}				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				if(bufRead != null){
					bufRead.close();
				}
				if(read != null){
					read.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//file.delete();
		}
	}
	
	private static Object getObject(String bean_name){
		if(MyApplicationContextUtil.getContext() == null){
			return null;
		}
		return MyApplicationContextUtil.getContext().getBean(bean_name);
	}
	
	public static void main(String[] args) {
		String dataPath = Constants.getProperty("orgdata_path");//数据文件路径
		//读取数据文件，首先更新一级部门数据信息
		File file = new File(dataPath + "pjgcs.txt");
		InputStreamReader read = null;
		BufferedReader bufRead = null;
		
		try {
			read = new InputStreamReader(new FileInputStream(file), "gbk");
			bufRead = new BufferedReader(read);
			String tempString = null;
			String[] dataArry = null;
			while((tempString = bufRead.readLine())!= null){
				dataArry = tempString.split("\\|");
				System.out.println(dataArry.length);
				for(int i=0;i<dataArry.length;i++){
					System.out.print(dataArry[i]+"@");
				}
				//System.out.println(tempString);
				//System.out.println("line "+line+": "+tempString);
			}
			bufRead.close();
			read.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				if(bufRead != null){
					bufRead.close();
				}
				if(read != null){
					read.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
