package com.xp.commonpart.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xp.commonpart.bean.TreeObject;

public interface ComQueryService {
	public Map queryTableForAjaxService(HttpServletRequest request);
	public String getTreeJson(List<TreeObject> tlist,String ischecked);
	public List getTitleColumn(String tableName);
	//通过父id获取树
	public String getTreeJson(List<TreeObject> tlist,String ischecked,String pid);
}
