package com.dj.seal.hotel.vo;

public class ChartVO {
	//柱状图表
	private String text;
	private int value;
	
	public ChartVO(String t,int v){
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
