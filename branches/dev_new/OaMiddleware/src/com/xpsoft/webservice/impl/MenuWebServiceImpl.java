package com.xpsoft.webservice.impl;

import java.util.List;

import com.xpsoft.webservice.MenuWebService;


/**
 * 重新加载菜单
 * @Class Name MenuWebService
 * @Author likang
 * @Create In Sep 1, 2010
 */
public class MenuWebServiceImpl implements MenuWebService {

	public String reloadMenu(String message, int num, List<String> list) {
		String back = "message:" + message + ",num:"+num + ",list" + list;
		System.out.println(back);
		// TODO Auto-generated method stub
		return back;
	}
	
}