package com.dj.seal.util.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StatData {
	static Logger logger = LogManager.getLogger(StatData.class.getName());
	private int value;
	private String text;
	
	public StatData(String t,int v){
		setText(t);
		setValue(v);
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
