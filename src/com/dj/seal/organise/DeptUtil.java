package com.dj.seal.organise;

import java.util.ArrayList;
import java.util.List;

import com.dj.seal.util.Constants;

public class DeptUtil {

	/*把字符类型转换成数字类型*/
	public static int change(String str){
		String key =Constants.DEPT_KEY_STR; 
		int re=0;
		int j=1;
		for(int i=str.length()-1;i>=0;i--){
			re=re+key.indexOf(str.charAt(i))*j;
			j=j*100;
		}
		return re;
	}
	
	/**
	 * 得到所有祖宗
	 * 
	 * @param str
	 * @return
	 */
	public static List<String> getAllP(String str) {
		List<String> list = new ArrayList<String>();
		while (str.length() > 0) {
			list.add(str);
			str = str.substring(0, str.length() - 4);
		}
		return list;
	}

	/**
	 * 得到并集
	 * 
	 * @param l1
	 * @param l2
	 * @return
	 */
	public static List<String> bing(List<String> l1, List<String> l2) {
		for (String str : l2) {
			if (!l1.contains(str)) {
				l1.add(str);
			}
		}
		return l1;
	}
	
	public static List<String> all(List<String> l){
		List<String> list=new ArrayList<String>();
		for (String str : l) {
			List<String> a=getAllP(str);
			list=bing(list, a);
		}
		System.out.println(list.size());
		return list;
	}

	public static void main(String[] args) {
		String str = "a0a2a5a8";
		String str2 = "a0a2a5a7";
		List<String> list = bing(getAllP(str), getAllP(str2));
		for (String string : list) {
			System.out.println(string);
		}
	}

}
