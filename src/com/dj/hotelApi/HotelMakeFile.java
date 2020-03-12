package com.dj.hotelApi;

import java.util.ArrayList;
import java.util.List;

import com.dj.hotelApi.po.BusiInfo;
import com.dj.hotelApi.po.HotelFilePo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xmlUtil.xml.XMLNote;

public class HotelMakeFile {
	static Logger logger = LogManager.getLogger(HotelMakeFile.class.getName());
	
	public HotelFilePo AnalysisXml(String xmlStr){
		HotelFilePo hfpo = new HotelFilePo();
		
		XMLNote xml = null;
		try {
			xml = XMLNote.toNote(XMLNote.noHead(xmlStr));
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return null;
		}
		String billid = xml.getValue("billid");
		logger.info("billid:"+billid);
		String modelid = xml.getValue("modelid");
		logger.info("modelid:"+modelid);
		XMLNote busiList = xml.getByName("busi_list");
		List<XMLNote> notes = busiList.getChilds();
		logger.info("notes.size():"+notes.size());
		List<BusiInfo> datas = new ArrayList<BusiInfo>();
		for (XMLNote note : notes) {
			datas.addAll(getDatas(note));
		}
		
		logger.info("datas.size():"+datas.size());
		for(int i=0;i<datas.size();i++){
			BusiInfo bi = datas.get(i);
			logger.info(bi.getName()+":::"+bi.getValue());
		}
		hfpo.setBillid(billid);
		hfpo.setModel_id(modelid);
		hfpo.setDatas(datas);
		return hfpo;
	}
	
	private List<BusiInfo> getDatas(XMLNote xml){
		List<BusiInfo> datas = new ArrayList<BusiInfo>();
		if (xml.getChilds() == null) {
			BusiInfo bi = new BusiInfo();
			String name = xml.getName();
			String value = myTrimContext(xml.getValue());
			bi.setName(name);
			bi.setValue(value);
			datas.add(bi);
			return datas;
		} else {
			for (XMLNote note : xml.getChilds()) {
				datas.addAll(getDatas(note));
			}
		}
		return datas;
	}
	
	public static String myTrimContext(String str) {
		str = str.replaceAll("<br>", "");
		str = str.trim();
		return str;
	}
	
	
	public static void main(String[] args) throws Exception {
		String xmlstr = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><req><billid>108261549606</billid><modelid>1</modelid><busi_list><busi_info><guestname>张三</guestname><shenfenzhenghao>身份证号</shenfenzhenghao></busi_info></busi_list></req>";
		HotelMakeFile mf = new HotelMakeFile();
		mf.AnalysisXml(xmlstr);
	}
	
}
