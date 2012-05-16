package com.xp.commonpart.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.xp.commonpart.Page;
import com.xp.commonpart.bean.MainTable;
import com.xp.commonpart.dao.SelectDataDao;
import com.xp.commonpart.service.SelectDataService;
import com.xp.commonpart.util.ContextHolder;
import com.xp.commonpart.util.HttpUtil;
import com.xp.commonpart.util.JDBCUtil;
import com.xp.commonpart.util.PropertiesUtil;
import com.xp.commonpart.util.WebUtils;

public class SelectDataServiceImpl implements SelectDataService{
	protected final transient Log logger = LogFactory.getLog(getClass());
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
		MainTable maintable=(MainTable) request.getAttribute("maintable");
		int total=0;
		if(maintable.getPosition()!=null&&maintable.getPosition().equals("1")){
			total=this.getRemoteRowNum(maintable, sql);
		}else{
			total=this.getDataRowNum(sql);
		}
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
		if(maintable.getPosition()!=null&&maintable.getPosition().equals("1")){
			String datasql="select * from dataBaseList where id="+maintable.getDatabaseId();
			List<Map> list=this.getData(datasql);
			if(list!=null&&list.size()>0){
				Map map=list.get(0);
				String dbType=map.get("dataBaseType")!=null?map.get("dataBaseType").toString():"";
				switch(Integer.parseInt(dbType)){
		            case 0 : databasetype= "oracle" ; break;
		            case 1 : databasetype= "sqlserver" ; break;
		            case 2 : databasetype= "mysql" ; break;
		            default : databasetype= "mysql" ; break;
				}
			}
		}
		if(databasetype.indexOf("oracle")>=0){
			//sql="select rownum as oid, pagetable.* from ("+sql+") pagetable ";
			sqlpage="select * from ("+sql+") tables1 where tables1.oid>="+((page-1)*pageSize+1)+" and tables1.oid<="+page*pageSize;
		}else if(databasetype.indexOf("mysql")>=0){
			sqlpage=sql+" limit "+((page-1)*pageSize)+","+pageSize;
		}else if(databasetype.indexOf("sqlserver")>=0){
			 sqlpage="select top "+((page)*pageSize) +" tables3.* from ("+sql+") tables3 where tables3."+request.getParameter("keyColumnName")+" not in (select top "+((page-1)*pageSize)+" tables4."+request.getParameter("keyColumnName")+" from ("+sql+") tables4)";
		}
		List resoultList=null;
		if(maintable.getPosition()!=null&&maintable.getPosition().equals("1")){
			resoultList=this.getRemoteData(maintable, sqlpage);
		}else{
			resoultList=this.getData(sqlpage);
		}
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
	private Integer getRemoteRowNum(MainTable maintable,String mainsql){
		String sql="select * from dataBaseList where id="+maintable.getDatabaseId();
		List<Map> list=this.getData(sql);
		if(list!=null&&list.size()>0){
			Map map=list.get(0);
			String url=map.get("dataBaseUrl")!=null?map.get("dataBaseUrl").toString():"";
			String username=map.get("userName")!=null?map.get("userName").toString():"";
			String pwd=map.get("passwd")!=null?map.get("passwd").toString():"";
			String ip=map.get("ipaddress")!=null?map.get("ipaddress").toString():"";
			String port=map.get("port")!=null?map.get("port").toString():"";
			String sid=map.get("dataName")!=null?map.get("dataName").toString():"";
			String dbType=map.get("dataBaseType")!=null?map.get("dataBaseType").toString():"";
			if(dbType.equals("4")){
				
			}else{
				url=JDBCUtil.getUrl(ip, port, sid, Integer.parseInt(dbType));
			}
			Connection con=JDBCUtil.getConnection(url, username, pwd);
			LinkedHashMap resultMap=new LinkedHashMap();
			if(con==null){
				logger.warn("远程获取查询总数失败");
				return null;
			}else{
				try {
					Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
					sql=HttpUtil.EncodeToHtml(sql);
					ResultSet rs=st.executeQuery(mainsql);
					rs.last(); //结果集指针知道最后一行数据
					return rs.getRow();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	public List getRemoteData(MainTable maintable,String sqlpage){
		SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
		String sql="select * from dataBaseList where id="+maintable.getDatabaseId();
		List<Map> list=selectDataService.getData(sql);
		List datalist=new ArrayList();
		if(list!=null&&list.size()>0){
			Map map=list.get(0);
			String url=map.get("dataBaseUrl")!=null?map.get("dataBaseUrl").toString():"";
			String username=map.get("userName")!=null?map.get("userName").toString():"";
			String pwd=map.get("passwd")!=null?map.get("passwd").toString():"";
			String ip=map.get("ipaddress")!=null?map.get("ipaddress").toString():"";
			String port=map.get("port")!=null?map.get("port").toString():"";
			String sid=map.get("dataName")!=null?map.get("dataName").toString():"";
			String dbType=map.get("dataBaseType")!=null?map.get("dataBaseType").toString():"";
			if(dbType.equals("4")){
				
			}else{
				url=JDBCUtil.getUrl(ip, port, sid, Integer.parseInt(dbType));
			}
			Connection con=JDBCUtil.getConnection(url, username, pwd);
			if(con==null){
				logger.warn("远程获取查询数据失败");
				return null;
			}else{
				try {
					Statement st = con.createStatement();
					sql=HttpUtil.EncodeToHtml(sql);
					ResultSet rs=st.executeQuery(sqlpage);
					ResultSetMetaData rr=rs.getMetaData();
					int count=rr.getColumnCount();
					List titleList=new ArrayList();
					for(int i=1;i<=count;i++){
						String columnName=rr.getColumnName(i);
						titleList.add(columnName);
					}
					while(rs.next()){
						LinkedHashMap resultMap=new LinkedHashMap();
						for(int i=0;i<titleList.size();i++){
							Object o=rs.getObject(titleList.get(i).toString());
							resultMap.put(titleList.get(i).toString(), o);
						}
						datalist.add(resultMap);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return datalist;
	}
}
