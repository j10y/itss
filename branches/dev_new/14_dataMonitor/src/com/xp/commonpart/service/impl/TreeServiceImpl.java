package com.xp.commonpart.service.impl;

import java.util.List;

import com.xp.commonpart.bean.TreeObject;
import com.xp.commonpart.dao.TreeDao;
import com.xp.commonpart.service.TreeService;

public class TreeServiceImpl implements TreeService{
	TreeDao treeDao;
	public TreeDao getTreeDao() {
		return treeDao;
	}

	public void setTreeDao(TreeDao treeDao) {
		this.treeDao = treeDao;
	}

	public String getChildTree(String id, String ischecked, String treetype) {
		// TODO Auto-generated method stub
		return treeDao.getChildTree(id, ischecked, treetype);
	}


	public List getNodeById(String nodeid) {
		// TODO Auto-generated method stub
		return treeDao.getNodeById(nodeid);
	}


	public List getNodeByUpId(String nodeid) {
		// TODO Auto-generated method stub
		return treeDao.getNodeByUpId(nodeid);
	}

	public String getTreeByParams(String ischecked, String treetype) {
		// TODO Auto-generated method stub
		return treeDao.getTreeByParams(ischecked, treetype);
	}


	public String getTreeByParams(String ischecked, String treetype, String nodeid) {
		// TODO Auto-generated method stub
		return treeDao.getTreeByParams(ischecked, treetype, nodeid);
	}


	public String getTreeByParams(String ischecked, String treetype, String nodeid, String treename) {
		// TODO Auto-generated method stub
		return treeDao.getTreeByParams(ischecked, treetype, nodeid, treename);
	}


	public String getTreeJson(List<TreeObject> tlist, String ischecked) {
		// TODO Auto-generated method stub
		return treeDao.getTreeJson(tlist, ischecked);
	}


	public List getUnitByNodeId(String nodeid) {
		// TODO Auto-generated method stub
		return treeDao.getUnitByNodeId(nodeid);
	}


	public List getUnitByUnitId(String unitisn) {
		// TODO Auto-generated method stub
		return treeDao.getUnitByUnitId(unitisn);
	}

	public List getUserByUnitId(String unitisn) {
		// TODO Auto-generated method stub
		return treeDao.getUserByUnitId(unitisn);
	}

	public List getUserByUserId(String userid) {
		// TODO Auto-generated method stub
		return treeDao.getUserByUserId(userid);
	}

}
	
