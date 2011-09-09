package com.xp.commonpart.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.xp.commonpart.Page;
import com.xp.commonpart.dao.SelectDataDao;
import com.xp.commonpart.service.SelectDataService;
import com.xp.commonpart.util.PropertiesUtil;
import com.xp.commonpart.util.WebUtils;

public class SelectDataServiceImpl implements SelectDataService{
	private SelectDataDao selectDataDao;
	public void setSelectDataDao(SelectDataDao selectDataDao) {
		this.selectDataDao = selectDataDao;
	}
	public SelectDataDao getSelectDataDao() {
		return selectDataDao;
	}
	public List getData(String sql) {
		// TODO Auto-generated method stub
		List list=selectDataDao.selectData(sql);
		return list;
	}
	public boolean remove(String sql) {
		// TODO Auto-generated method stub
		return selectDataDao.deletebyid(sql);
	}
	public String[] getColumnName(String sql) {
		// TODO Auto-generated method stub
		
		return selectDataDao.getColumnName(sql);
	}
	public int getDataRowNum(String sql) {
		// TODO Auto-generated method stub
		int i=0;
		i=selectDataDao.selectDataRowNum(sql);
		return i;
	}
	public void saveRealTable(String sql) {
		// TODO Auto-generated method stub
		selectDataDao.insertRealTable(sql);
	}
	/*sql语句分页*/
	public Page getListForPage(HttpServletRequest request, String sql) {
		// TODO Auto-generated method stub
		int total=this.getDataRowNum(sql);
		int page=1;
		if(request.getParameter("page")!=null){
			page=Integer.parseInt(request.getParameter("page"));
		}
		int pageSize=Integer.parseInt(PropertiesUtil.getProperties("system.pagesize", "12"));
		if(request.getParameter("pageSize")!=null){
			pageSize=Integer.parseInt(request.getParameter("pageSize"));
		}
		if(request.getParameter("rows")!=null){
			pageSize=Integer.parseInt(request.getParameter("rows"));
		}
		if(request.getParameter("order")!=null&&request.getParameter("sort")!=null){
			sql=sql+" order by "+request.getParameter("sort")+" "+request.getParameter("order");
		}
		Page p=new Page();
		p.setPage(page);
		p.setPageSize(pageSize);
		p.setTotal(total);
		String sqlpage="";
		String databasetype=PropertiesUtil.getProperties("jdbc.driverClassName");
		if(databasetype.indexOf("oracle")>=0){
			sql="select rownum as oid, pagetable.* from ("+sql+") pagetable ";
			sqlpage="select * from ("+sql+") tables1 where tables1.oid>="+((page-1)*pageSize+1)+" and tables1.oid<="+page*pageSize;
		}else if(databasetype.indexOf("mysql")>=0){
			sqlpage=sql+" limit "+((page-1)*pageSize)+","+pageSize;
		}else if(databasetype.indexOf("sqlserver")>=0){
			 sqlpage="select top "+((page)*pageSize) +" tables3.* from ("+sql+") tables3 where tables3."+request.getParameter("keyColumnName")+" not in (select top "+((page-1)*pageSize)+" tables4."+request.getParameter("keyColumnName")+" from ("+sql+") tables4)";
		}
		
		List resoultList=this.getData(sqlpage);
//		Map resultMap=new HashMap();
//		resultMap.put("resoultList", resoultList);
//		resultMap.put("page", p);
		p.setData(resoultList);
		p.setSql(sqlpage);
		return p;
	}
	public void setManage(HttpServletRequest request) {
		String opid=request.getParameter("opid");
		List<Map> list = new ArrayList();
		String usergroupISN = WebUtils.getCookieByName(request, "userGroupIsn");
		String userID = WebUtils.getCookieByName(request, "userID");
		list = getData("SELECT S.* FROM Sys_Sec_Operate S,sys_sec_usergroup_operate o WHERE s.operateid=o.operateid and S.OPERATEID like '"+opid+"__%' and o.usergroupisn in('"+usergroupISN.replace(",", "','").replace("\"", "")+"')");
		Map resousemap=new HashMap();
		for(Map map:list){
			String opname=map.get("OPERATENAME").toString();
			String opeid=map.get("OPERATEID").toString();
			String[] spnames=opname.split("_");
			Map mapr=new HashMap();
			mapr.put("opname", spnames[1]);
			mapr.put("opeid", opeid);
			resousemap.put(spnames[1], mapr);
		}
		request.setAttribute("resousemap", resousemap);
	}

}
