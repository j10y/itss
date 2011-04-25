package com.digitalchina.info.framework.util;

import org.apache.commons.lang.StringUtils;

public class Test {

	public static void main(String[] args) {
		String value = "管理员/admin/神州数码";
		int firstBias = value.indexOf("/");
		System.out.println("firstBias:"+ firstBias);
		int secondBias = StringUtils.indexOf(value, "/", firstBias+1);
		System.out.println("secondBias:"+ secondBias);
		String middle = value.substring(firstBias+1, secondBias);
		System.out.println("middle:"+ middle);
//		if(value!=null&& firstBias!=-1){ //管理员/admin/神州数码
//			int secondBias = StringUtils.indexOf(value, "/", firstBias);

	}

}
