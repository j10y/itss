package com.digitalchina.info.framework.util;

import org.apache.commons.lang.StringUtils;

public class Test {

	public static void main(String[] args) {
		String value = "����Ա/admin/��������";
		int firstBias = value.indexOf("/");
		System.out.println("firstBias:"+ firstBias);
		int secondBias = StringUtils.indexOf(value, "/", firstBias+1);
		System.out.println("secondBias:"+ secondBias);
		String middle = value.substring(firstBias+1, secondBias);
		System.out.println("middle:"+ middle);
//		if(value!=null&& firstBias!=-1){ //����Ա/admin/��������
//			int secondBias = StringUtils.indexOf(value, "/", firstBias);

	}

}
