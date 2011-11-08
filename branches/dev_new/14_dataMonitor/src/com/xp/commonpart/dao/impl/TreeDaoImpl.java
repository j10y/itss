package com.xp.commonpart.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.xp.commonpart.bean.TreeObject;
import com.xp.commonpart.dao.SelectDataDao;
import com.xp.commonpart.dao.TreeDao;
/**
 * @author 127
 *
 */
public class TreeDaoImpl implements TreeDao{

	private SelectDataDao selectDataDao;
	
	public void setSelectDataDao(SelectDataDao selectDataDao) {
		this.selectDataDao = selectDataDao;
	}
	public SelectDataDao getSelectDataDao() {
		return selectDataDao;
	}
	public String getChildTree(String id,String ischecked,String treetype){
		
		List<Map> list=this.getNodeByUpId(id);
		String json="";
		for(Map node:list){
			String postion="";
			if(node.get("POSITION")!=null){
				postion=node.get("POSITION").toString();
			}
			if(postion.equals("0")){
				json+="{'icon':'mnode',";
				json+="'text':'"+node.get("NODENAME")+"',";
				json+="'id':'node_"+node.get("NODEID")+"',";
				json+="'pid':'node_"+node.get("UPNODEID")+"',";
				if(ischecked!=null&&ischecked.equals("1")){
					json+="'checked':'',";
				}
				json+="children:[";
				json+=this.getChildTree(node.get("NODEID").toString(), ischecked, treetype);
				json+="]},";
			}
		}
		if(treetype.equals("unit")||treetype.equals("user")){
			List<Map> units=this.getUnitByNodeId(id);
			for(Map unit:units){
				json+="{'icon':'unit',";
				json+="'text':'"+unit.get("UNITNAME")+"',";
				json+="'id':'unit_"+unit.get("UNITISN")+"',";
				if(ischecked!=null&&ischecked.equals("1")){
					json+="'checked':'',";
				}
				json+="children:[";
				if(treetype.equals("user")){
					List<Map> users=this.getUserByUnitId(unit.get("UNITISN").toString());
					for(Map user:users){
						json+="{'icon':'user',";
						json+="'text':'"+user.get("username")+"',";
						json+="'id':'user_"+user.get("userid")+"',";
						json+="'isn':'user_"+user.get("userisn")+"'";
						if(ischecked!=null&&ischecked.equals("1")){
							json+=",'checked':''";
						}
						json+="},";
					}
					if(json.length()!=0&&json.length()==json.lastIndexOf(",")+1){
						json=json.substring(0,json.length()-1);
					}
				}
				json+="]},";
			}
		}
		if(json.length()!=0&&json.length()==json.lastIndexOf(",")+1){
			json=json.substring(0,json.length()-1);
		}
		return json;
	}
	public String getTreeByParams(String ischecked, String treetype) {
		// TODO Auto-generated method stub
		String json="";
		//List<Node> list=this.getObjectByPar(Node.class,"upnodeid", "0");
		List<Map> list=this.getNodeByUpId("0");
		for(Map node:list){
			String postion="";
			if(node.get("POSITION")!=null){
				postion=node.get("POSITION").toString();
			}
			if(postion.equals("0")){
				json+="{'icon':'mnode',";
				json+="'text':'"+node.get("NODENAME")+"',";
				json+="'id':'node_"+node.get("NODEID")+"',";
				json+="'pid':'node_"+node.get("UPNODEID")+"',";
				if(ischecked!=null&&ischecked.equals("1")){
					json+="'checked':'',";
				}
				json+="children:[";
				json+=this.getChildTree(node.get("NODEID").toString(), ischecked, treetype);
				json+="]},";
			}
		}
		if(json.length()!=0&&json.length()==json.lastIndexOf(",")+1){
			json=json.substring(0,json.length()-1);
		}
		return json;
	}
	public String getTreeByParams(String ischecked, String treetype,String nodeid) {
		// TODO Auto-generated method stub
		String json="";
		//List<Node> list=this.getObjectByPar(Node.class,"nodeid", nodeid);
		List<Map> list=this.getNodeById(nodeid);
		for(Map node:list){
			json+="{'icon':'mnode',";
			json+="'text':'"+node.get("NODENAME")+"',";
			json+="'id':'node_"+node.get("NODEID")+"',";
			json+="'pid':'node_"+node.get("UPNODEID")+"',";
			if(ischecked!=null&&ischecked.equals("1")){
				json+="'checked':'',";
			}
			json+="children:[";
			json+=this.getChildTree(node.get("NODEID").toString(), ischecked, treetype);
			json+="]},";
		}
		if(json.length()!=0&&json.length()==json.lastIndexOf(",")+1){
			json=json.substring(0,json.length()-1);
		}
		return json;
	}
	public String getTreeByParams(String ischecked, String treetype,String nodeid,String treename) {
		// TODO Auto-generated method stub
		String json="";
		String[] nodeids=nodeid.split(",");
		for(String nid:nodeids){
			//List<Node> list=this.getObjectByPar(Node.class,"nodeid", nid);
			List<Map> list=this.getNodeById(nid);
			for(Map node:list){
				String postion="";
				if(node.get("POSITION")!=null){
					postion=node.get("POSITION").toString();
				}
				if(postion.equals("0")){
					json+="{'icon':'mnode',";
					json+="'text':'"+node.get("NODENAME")+"',";
					json+="'id':'node_"+node.get("NODEID")+"',";
					json+="'pid':'node_"+node.get("UPNODEID")+"',";
					if(ischecked!=null&&ischecked.equals("1")){
						json+="'checked':'',";
					}
					json+="children:[";
					json+=this.getChildTree(node.get("NODEID").toString(), ischecked, treetype);
					json+="]},";
				}
			}
		}
		if(json.length()!=0&&json.length()==json.lastIndexOf(",")+1){
			json=json.substring(0,json.length()-1);
		}
		json="{'text':'"+treename+"',children:["+json+"]}";
		return json;
	}
	

	
	public List getNodeById(String nodeid) {
		// TODO Auto-generated method stub\
		String sql="select * from SYS_SEC_NODE where NODEID='"+nodeid+"' order by NODEID asc";
		List list=selectDataDao.selectData(sql);
		return list;
	}

	
	public List getNodeByUpId(String nodeid) {
		// TODO Auto-generated method stub
		String sql="select * from SYS_SEC_NODE where UPNODEID='"+nodeid+"' order by NODEID asc";
		List list=selectDataDao.selectData(sql);
		return list;
	}
	
	public List getUnitByNodeId(String nodeid) {
		// TODO Auto-generated method stub
		String sql="select * from SYS_SEC_UNIT where NODEID='"+nodeid+"' order by UNITISN asc";
		List list=selectDataDao.selectData(sql);
		return list;
	}

	
	public List getUnitByUnitId(String unitisn) {
		// TODO Auto-generated method stub
//		Criteria c=this.getSession().createCriteria(Unit.class);
//		c.add(Restrictions.eq("unitisn",unitisn));
//		return c.list();
		String sql="select * from SYS_SEC_UNIT where UNITISN='"+unitisn+"' order by UNITISN asc";
		List list=selectDataDao.selectData(sql);
		return list;
	}

	public List getUserByUnitId(String unitisn) {
		String sql="select * from SYS_SEC_USERINFO where UNITISN='"+unitisn+"'";
		List list=selectDataDao.selectData(sql);
		return list;
	}
	
	public List getServerName(String id){
		String sql="select s.FIELDNAME from dbw_ntns_e_nodes s where s.FIELDID='"+id+"'";
		List list=selectDataDao.selectData(sql);
		return list;
	}
	
	public List getNodesById(String nodeIds){
		String sql="select s.NODEID,s.NODENAME from sys_sec_node s where s.NODEID in ("+nodeIds+")";
		List list=selectDataDao.selectData(sql);
		return list;
	}
	
	
	
	public List getUserByUserId(String userid) {
		// TODO Auto-generated method stub
		
		return null;
	}

	public String getTreeJson(List<TreeObject> tlist,String ischecked) {
		// TODO Auto-generated method stub
		List<TreeObject> tlist1=new ArrayList();
		tlist1.addAll(tlist);
		String json="{'text':'节点树','id':'-1',children:[";
		for(TreeObject to:tlist){
			if(to.getPid().equals("")||to.getPid().equals("0")){
				tlist1.remove(to);
				json+="{'text':'"+to.getName()+"','pid':'"+to.getPid()+"',";
				if(ischecked.equals("1")){
					json+="'checked':'0',";
					json+="'id':'"+to.getId()+"_"+to.getName()+"',";
				}else{
					json+="'id':'"+to.getId()+"',";
				}
				if(to.getIcons()!=null&&to.getIcons().length()>0){
					json+="'icon':'"+to.getIcons()+"',";
				}
				json+="children:[";
				json+=this.getChildrenData(tlist1, to,ischecked);
				json+="]},";
			}
		} 
		json=json.substring(0,json.length()-1);
		json+="] ";
		json+="}";
		return json;
	}
	
	private String getChildrenData(List<TreeObject> tlist1,TreeObject to,String ischecked){
		String json="";
		List<TreeObject> tlist=new ArrayList();
		tlist.addAll(tlist1);
		for(TreeObject to1:tlist1){
			if(to.getId().equals(to1.getPid())){
				tlist.remove(to1);
				json+="{'text':'"+to1.getName()+"','pid':'"+to1.getPid()+"',";
				if(ischecked.equals("1")){
					json+="'checked':'0',";
					json+="'id':'"+to1.getId()+"_"+to1.getName()+"',";
				}else{
					json+="'id':'"+to1.getId()+"',";
				}
				if(to1.getIcons()!=null&&to1.getIcons().length()>0){
					json+="'icon':'"+to1.getIcons()+"',";
				}
				json+="children:[";
				json+=this.getChildrenData(tlist, to1,ischecked);
				json+="]},";
			}
		}
		if(json.length()!=0&&json.length()==json.lastIndexOf(",")+1){
			json=json.substring(0,json.length()-1);
		}
		return json;
	}
}