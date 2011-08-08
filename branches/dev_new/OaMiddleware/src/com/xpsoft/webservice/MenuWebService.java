package com.xpsoft.webservice;

import java.util.List;


/**
 * 重新加载菜单
 * @Class Name MenuWebService
 * @Author likang
 * @Create In Sep 1, 2010
 */
public interface MenuWebService {
	/**
	 * 重新加载菜单
	 * @Methods Name reloadMenu
	 * @Create In Sep 1, 2010 By likang
	 * @param message
	 * @return String
	 */
	public String reloadMenu(String message,int num,List<String> list);
	
}