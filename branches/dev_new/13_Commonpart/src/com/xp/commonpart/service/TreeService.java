package com.xp.commonpart.service;
import java.util.List;

import com.xp.commonpart.bean.TreeObject;

public interface TreeService{
	public String getChildTree(String id,String ischecked,String treetype);
	
	public List getNodeById(String nodeid);
	
	public List getNodeByUpId(String nodeid);
	
	public List getUnitByUnitId(String unitisn);
	
	public List getUnitByNodeId(String nodeid);
	
	public List getUserByUserId(String userid);
	
	public List getUserByUnitId(String unitisn);
	
	public String getTreeByParams(String ischecked,String treetype);
	
	public String getTreeByParams(String ischecked, String treetype,String nodeid);
	
	public String getTreeByParams(String ischecked,String  treetype,String nodeid,String treename);
	
	public String getTreeJson(List<TreeObject> tlist,String ischecked);
}
