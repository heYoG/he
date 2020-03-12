package com.dj.seal.util.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GeneralException extends Exception {
	
	static Logger logger = LogManager.getLogger(GeneralException.class.getName());
	
	private static final long serialVersionUID = 4861216287134710674L;

	private String key;
	private String values[];
	private String msg;
	
	public GeneralException(String msg){
		super(msg);
		this.msg = msg;
	}
	/**
	 * 如果key不为null,则使用我们配置在资源文件中的key,msg是给开发人员看到的后台信息
	 * 如果key==null,则使用默认的key值即exception,给用户显示的错误就是msg(当然这个给开发人员报出的错误也是msg)
	 */
	public GeneralException(String key,String msg){
		super(msg);
		this.msg = msg;
		this.key = key;
	}
	
	public GeneralException(String key,String msg,String value){
		super(msg);
		this.key = key;
		this.msg = msg;
		values = new String[1];
		values[0] = value;
	}
	
	public GeneralException(String key,String msg,String value1,String value2){
		super(msg);
		this.key = key;
		this.msg = msg;
		values = new String[2];
		values[0] = value1;
		values[1] = value2;
	}
	
	public GeneralException(String key,String msg,String value1,String value2,String value3){
		super(msg);
		this.key = key;
		this.msg = msg;
		values = new String[3];
		values[0] = value1;
		values[1] = value2;
		values[2] = value3;
	}
	
	public GeneralException(String key,String msg,String[] values){
		super(msg);
		this.key = key;
		this.msg = msg;
		this.values = values;
	}
	
	public String getKey() {
		return key;
	}
	public String[] getValues() {
		return values;
	}
	public String getMsg() {
		return msg;
	}
}
