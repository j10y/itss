package com.xp.commonpart.service.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xp.commonpart.bean.MainTable;
import com.xp.commonpart.bean.MainTableColumn;
import com.xp.commonpart.bean.MonitorInfo;
import com.xp.commonpart.bean.QueryPanel;
import com.xp.commonpart.bean.TreeObject;
import com.xp.commonpart.dao.BaseDao;
import com.xp.commonpart.service.ComQueryService;
import com.xp.commonpart.service.SelectDataService;
import com.xp.commonpart.util.ContextHolder;
import com.xp.commonpart.util.ExceptionMessage;
import com.xp.commonpart.util.HttpUtil;
import com.xp.commonpart.util.JDBCUtil;
import com.xp.commonpart.util.SqlUtil;

public class ComQueryServiceImpl implements ComQueryService{
	protected final Logger log = LoggerFactory.getLogger(getClass());
	private BaseDao baseDao;
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public Map queryTableForAjaxService(HttpServletRequest request){
		String tableName=request.getParameter("tableName");
		List list=baseDao.findObjectByPar(MainTable.class, "tableName", tableName);
		Map resultMap=new HashMap();
		if(list.size()==0){
			resultMap.put("error", "没有要显示的表，请查看配置");
			return resultMap;
		}else if(list.size()>1){
			resultMap.put("error", "你可能又相同的表在表描述信息中，请查看配置");
			return resultMap;
		}
		MainTable maintable=new MainTable();
		maintable=(MainTable) list.get(0);
		List<MainTableColumn> mtclist=baseDao.findObjectByParOrder(MainTableColumn.class, "maintableid", maintable.getTableid(),"columnid","asc");
		if(mtclist.size()==0){
			resultMap.put("error", "表的字段信息没有配置");
			return resultMap;
		}
		//需要哪些查询条件的列表
		List queryList=new ArrayList();
		String[] propNames=new String[2];
		propNames[0]="isQuery";
		propNames[1]="maintableid";
		Object[] propValues=new Object[2];
		propValues[0]="1";
		propValues[1]=maintable.getTableid();
		List<MainTableColumn> qmtclist=baseDao.findObjectByParsOrder(MainTableColumn.class,propNames, propValues,"isQueryOrder","asc");
		for(MainTableColumn mainTableColumn:qmtclist){
			//查询的表头
			QueryPanel querypanel=new QueryPanel();
				querypanel.setName(mainTableColumn.getColumnName());
				querypanel.setCname(mainTableColumn.getColumnCName());
				querypanel.setType(mainTableColumn.getPropertyType());
				querypanel.setMainTableColumn(mainTableColumn);
				//如果是时间或时期
				if(mainTableColumn.getPropertyType().equals("5")||mainTableColumn.getPropertyType().equals("10")){
					//如果数据类型是时间类型则是时间段
					if(mainTableColumn.getDataType().equals("3")){
						String startdate=request.getParameter(mainTableColumn.getColumnName()+"start");
						querypanel.setStartdate(startdate);
						String enddate=request.getParameter(mainTableColumn.getColumnName()+"end");
						querypanel.setEnddate(enddate);
					}else{//否则是时间点
						String value=request.getParameter(mainTableColumn.getColumnName());
						querypanel.setValue(value);
					}
				}else if (mainTableColumn.getPropertyType().equals("2")|mainTableColumn.getPropertyType().equals("4")|mainTableColumn.getPropertyType().equals("8")){
					String value=request.getParameter(mainTableColumn.getColumnName());
					querypanel.setValue(value);
				}else {
					String value=request.getParameter(mainTableColumn.getColumnName());
					querypanel.setValue(value);
				}
				queryList.add(querypanel);
		}
		//根据条件拼sql语句查询
		//查处全部
		Object managednodeid=request.getAttribute("managednodeid");
		if(managednodeid!=null){
			String srcsql=maintable.getSql();
			String[] managednodeids=managednodeid.toString().split(",");
			srcsql="select * from ("+srcsql+") unittable where ";
			srcsql+=" unittable.nodeid like '"+managednodeids[0]+"%'";
			for(int i=1;i<managednodeids.length;i++){
				srcsql+=" or unittable.nodeid like '"+managednodeids[i]+"%'";
			}
			maintable.setSql(srcsql);
		}
		String selectsql=SqlUtil.getMainSql(null, queryList, maintable);
		//导出数据
		List exportList=new ArrayList();
		propNames=new String[2];
		propNames[0]="isExport";
		propNames[1]="maintableid";
		propValues=new Object[2];
		propValues[0]="1";
		propValues[1]=maintable.getTableid();
		List<MainTableColumn> emtclist=baseDao.findObjectByParsOrder(MainTableColumn.class, propNames,propValues,"isExportOrder","asc");
		Map listExportMap=new HashMap();
		for(MainTableColumn mainTableColumn:emtclist){
			//在列表信息中要显示的字段
			QueryPanel exportpanel=new QueryPanel();
				exportpanel.setName(mainTableColumn.getColumnName());
				exportpanel.setCname(mainTableColumn.getColumnCName());
				exportpanel.setType(mainTableColumn.getPropertyType());
				exportpanel.setMainTableColumn(mainTableColumn);
				exportList.add(exportpanel);
		}
		if(exportList.size()==0||exportList.isEmpty()){
			resultMap.put("error", "无导出字段");
			return resultMap;
		}
		//查询出所有关于下拉列表的数据
		propNames=new String[1];
		propNames[0]="maintableid";
		propValues=new Object[1];
		propValues[0]=maintable.getTableid();
		List<MainTableColumn> listData=baseDao.findObjectByParsOrder(MainTableColumn.class, propNames,propValues,"isExportOrder","asc");
		Map listValueMap=new HashMap();
		for(MainTableColumn mainTableColumn:listData){
			//在列表信息中要显示的字段
				if (mainTableColumn.getPropertyType().equals("2")|mainTableColumn.getPropertyType().equals("4")|mainTableColumn.getPropertyType().equals("8")){
					String value=request.getParameter(mainTableColumn.getColumnName());
					Map map=SqlUtil.getExtListForAjax(mainTableColumn,value);
					listValueMap.put(mainTableColumn.getColumnName(), map);
				}
		}
		resultMap.put("sql", selectsql);
		resultMap.put("listValueMap", listValueMap);
		resultMap.put("maintable", maintable);
		resultMap.put("queryList", queryList);
		resultMap.put("exportList", exportList);
		return resultMap;
	}
	public String getTreeJson(List<TreeObject> tlist,String ischecked) {
		// TODO Auto-generated method stub
		List<TreeObject> tlist1=new ArrayList();
		tlist1.addAll(tlist);
		String json="{'text':'节点树','id':'-1','icon':'mnode',children:[";
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
				if(to.getCheckType()!=null&&to.getCheckType().length()>0){
					json+="'checktype':'"+to.getCheckType()+"',";
				}
				if(to.getIsCheckType()!=null&&to.getIsCheckType().length()>0){
					json+="'ischecktype':'"+to.getIsCheckType()+"',";
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
				if(to1.getUrl()!=null&&to1.getUrl().length()>0){
					json+="'url':'"+to1.getUrl()+"',";
				}
				if(to1.getCheckType()!=null&&to1.getCheckType().length()>0){
					json+="'checktype':'"+to1.getCheckType()+"',";
				}
				if(to1.getIsCheckType()!=null&&to1.getIsCheckType().length()>0){
					json+="'ischecktype':'"+to1.getIsCheckType()+"',";
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

	public List getTitleColumn(String tableName) {
		// TODO Auto-generated method stub
		List list=baseDao.findObjectByPar(MainTable.class, "tableName", tableName);
		Map resultMap=new HashMap();
		if(list.size()==0){
			return null;
		}else if(list.size()>1){
			return null;
		}
		MainTable maintable=new MainTable();
		maintable=(MainTable) list.get(0);
		String[] propNames=new String[2];
		propNames[0]="isList";
		propNames[1]="maintableid";
		Object[] propValues=new Object[2];
		propValues[0]="1";
		propValues[1]=maintable.getTableid();
		List<MainTableColumn> vmtclist=baseDao.findObjectByParsOrder(MainTableColumn.class, propNames,propValues,"isListOrder","asc");
		return vmtclist;
	}
	public String getTreeJson(List<TreeObject> tlist,String ischecked,String pid) {
		// TODO Auto-generated method stub
		List<TreeObject> tlist1=new ArrayList();
		tlist1.addAll(tlist);
		String json="{'text':'节点树','id':'-1','icon':'mnode',children:[";
		String[]pids=pid.split(",");
		for(int i=0;i<pids.length;i++){
			for(TreeObject to:tlist){
				if(!pids[i].equals("0")&&to.getId().equals(pids[i])){
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
					if(to.getUrl()!=null&&to.getUrl().length()>0){
						json+="'url':'"+to.getUrl()+"',";
					}
					if(to.getCheckType()!=null&&to.getCheckType().length()>0){
						json+="'checktype':'"+to.getCheckType()+"',";
					}
					if(to.getIsCheckType()!=null&&to.getIsCheckType().length()>0){
						json+="'ischecktype':'"+to.getIsCheckType()+"',";
					}
					json+="children:[";
					json+=this.getChildrenData(tlist1, to,ischecked);
					json+="]},";
				}else if(pids[i].equals("0")&&(to.getPid().equals(pids[i])||to.getPid().equals(""))){
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
					if(to.getUrl()!=null&&to.getUrl().length()>0){
						json+="'url':'"+to.getUrl()+"',";
					}
					if(to.getCheckType()!=null&&to.getCheckType().length()>0){
						json+="'checktype':'"+to.getCheckType()+"',";
					}
					if(to.getIsCheckType()!=null&&to.getIsCheckType().length()>0){
						json+="'ischecktype':'"+to.getIsCheckType()+"',";
					}
					json+="children:[";
					json+=this.getChildrenData(tlist1, to,ischecked);
					json+="]},";
				}
			} 
		}
		json=json.substring(0,json.length()-1);
		json+="] ";
		json+="}";
		return json;
	}
	
	public boolean connectDataBase(){
		SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
		String sql="select * from dataBaseList where isClose=1 ";
		List<Map> list=selectDataService.getData(sql);
		String usersql="select * from sys_sec_userinfo where issendmessage=1";
		List<Map> userlist=selectDataService.getData(usersql);
		//需要把监控日期放到外面来
		Date curDate=new Date();
		for(Map map:list){
			String id=map.get("id")!=null?map.get("id").toString():"";
			String url=map.get("dataBaseUrl")!=null?map.get("dataBaseUrl").toString():"";
			String username=map.get("userName")!=null?map.get("userName").toString():"";
			String pwd=map.get("passwd")!=null?map.get("passwd").toString():"";
			String ip=map.get("ipaddress")!=null?map.get("ipaddress").toString():"";
			String port=map.get("port")!=null?map.get("port").toString():"";
			String sid=map.get("dataName")!=null?map.get("dataName").toString():"";
			String dbType=map.get("dataBaseType")!=null?map.get("dataBaseType").toString():"";
			MonitorInfo monitorInfo=new MonitorInfo();
			monitorInfo.setCreateDate(curDate);
			monitorInfo.setDatabaseId(Long.parseLong(id));
			if(dbType.equals("4")){
				
			}else{
				url=JDBCUtil.getUrl(ip, port, sid, Integer.parseInt(dbType));
			}
			Connection con=JDBCUtil.getConnection(url, username, pwd);
			int count=0;
			while(con==null){
				con=JDBCUtil.getConnection(url, username, pwd);
				count++;
				if(count>=2){
					for(Map userMap:userlist){
						String realname=userMap.get("realname")!=null?userMap.get("realname").toString():"";
						String telp=userMap.get("mobiphone")!=null?userMap.get("mobiphone").toString():"";
						String message="连接数据库连续2次失败！url:"+url;
						ExceptionMessage.sendMessage(message,telp,realname);
					}
					monitorInfo.setStatus(0);
					monitorInfo.setDescrpition("连接数据库连续2次失败！url:"+url);
					baseDao.save(monitorInfo,MonitorInfo.class,"id");
					log.info("连接数据库连续2次失败！url:"+url);
					break;
				}
			}
			if(con==null){
				continue;
			}
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				for(Map userMap:userlist){
					String realname=userMap.get("realname")!=null?userMap.get("realname").toString():"";
					String telp=userMap.get("mobiphone")!=null?userMap.get("mobiphone").toString():"";
					String message="连接数据库关闭时失败！url:"+url;
					ExceptionMessage.sendMessage(message,telp,realname);
				}
				monitorInfo.setStatus(0);
				monitorInfo.setDescrpition("连接数据库关闭时失败！url:"+url);
				baseDao.save(monitorInfo,MonitorInfo.class,"id");
				log.info("连接数据库关闭时失败！url:"+url);
				continue;
			}
			monitorInfo.setStatus(1);
			monitorInfo.setDescrpition("连接数据库成功！url:"+url);
			baseDao.save(monitorInfo,MonitorInfo.class,"id");
			log.info("连接数据库成功！url:"+url);
			this.connectDataBaseByBaseId(id, "0");
		}
		return true;
	}
	public Map connectDataBaseByBaseId(String id,String type){
		SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
		String sql="select * from dataBaseList where id="+id;
		List<Map> list=selectDataService.getData(sql);
		String usersql="select * from sys_sec_userinfo where issendmessage=1";
		List<Map> userlist=selectDataService.getData(usersql);
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
				return null;
			}else{
				String pisql="select * from datapi where dataBaseID="+id;
				//start by tongjp 判断如果是从定时任务来的话，只查需要发送消息的性能指标
						if(type.equals("0")){
							pisql+=" and isMessage=1";
						}
				//end by tongjp 判断如果是从定时任务来的话，只查需要发送消息的性能指标
						pisql+=" order by id";
				List<Map> pilist=selectDataService.getData(pisql);
				int index=0;
				String message="";
				for(Map pimap:pilist){
					index++;
					try {
						String piSql=pimap.get("piSql").toString();
						String piName=pimap.get("piName").toString();
						String piid=pimap.get("id").toString();
						String comParMark=pimap.get("comParMark")+"";
						String threshold=pimap.get("threshold")+"";
						String colName=pimap.get("columnName")+"";
						Statement st=con.createStatement();
						piSql=HttpUtil.EncodeToHtml(piSql);
						ResultSet rs=st.executeQuery(piSql);
						HashMap datamap=new HashMap();
						int colid=0;
						try {
							ResultSetMetaData rr=rs.getMetaData();
							int count=rr.getColumnCount();
							List titleList=new ArrayList();
							for(int i=1;i<=count;i++){
								String columnName=rr.getColumnName(i);
								titleList.add(columnName);
								if(columnName.equalsIgnoreCase(colName)){
									colid=i;
								}
							}
							datamap.put("title", titleList);
							List dataList=new ArrayList();
							Map alarmMap=new HashMap();
							while(rs.next()){
								List subdataList=new ArrayList();
								for(int i=1;i<=count;i++){
									Object columnvalue=rs.getObject(i);
									subdataList.add(columnvalue);
								}
								if(colName!=null&&colName.length()>0){
									String compval=rs.getObject(colName)+"";
									boolean alarmflag=this.conpar(comParMark, compval, threshold);
									if(alarmflag){
										alarmMap.put(rs.getRow(), colid);
										if(type.equals("0")){
											message+=piName+":"+colName+"已到达警告值"+threshold+",目前为："+compval+",";
										}
									}
								}
								dataList.add(subdataList);
							}
							datamap.put("data", dataList);
							datamap.put("alarm", alarmMap);
							resultMap.put(index+"_"+piName, datamap);
							
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						return null;
					}
				}
				if(message!=null&&message.length()>0){
					for(Map userMap:userlist){
						String realname=userMap.get("realname")!=null?userMap.get("realname").toString():"";
						String telp=userMap.get("mobiphone")!=null?userMap.get("mobiphone").toString():"";
						message="url:"+url+message;
						ExceptionMessage.sendMessage(message,telp,realname);
					}
				}
			}
			try {
				con.close();
			} catch (SQLException e) {
				log.info("连接数据库关闭时失败！url:"+url);
				return null;
			}
			log.info("连接数据库成功！url:"+url);
			return resultMap;
		}
		return null;
	}
	public boolean conpar(String comParMark,String compval,String threshold){
		if(comParMark.equals("0")){
			Double cv=Double.parseDouble(compval);
			Double cv2=Double.parseDouble(threshold);
			if(cv>cv2){
				return true;
			}else{
				return false;
			}
		}else if(comParMark.equals("1")){
			Double cv=Double.parseDouble(compval);
			Double cv2=Double.parseDouble(threshold);
			if(cv>=cv2){
				return true;
			}else{
				return false;
			}
		}else if(comParMark.equals("2")){
			Double cv=Double.parseDouble(compval);
			Double cv2=Double.parseDouble(threshold);
			if(cv<cv2){
				return true;
			}else{
				return false;
			}								
		}else if(comParMark.equals("3")){
			Double cv=Double.parseDouble(compval);
			Double cv2=Double.parseDouble(threshold);
			if(cv<=cv2){
				return true;
			}else{
				return false;
			}
		}else if(comParMark.equals("4")){
			Double cv=Double.parseDouble(compval);
			Double cv2=Double.parseDouble(threshold);
			if(cv==cv2){
				return true;
			}else{
				return false;
			}
		}else if(comParMark.equals("5")){
			Double cv=Double.parseDouble(compval);
			Double cv2=Double.parseDouble(threshold);
			if(cv!=cv2){
				return true;
			}else{
				return false;
			}
		}else if(comParMark.equals("6")){
			String cv=compval;
			String cv2=threshold;
			if(cv.equalsIgnoreCase(cv2)){
				return true;
			}else{
				return false;
			}
		}else if(comParMark.equals("7")){
			String cv=compval;
			String cv2=threshold;
			if(!cv.equalsIgnoreCase(cv2)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
