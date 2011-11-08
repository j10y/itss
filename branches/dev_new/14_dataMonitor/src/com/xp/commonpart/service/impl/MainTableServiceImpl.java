package com.xp.commonpart.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;


import com.xp.commonpart.bean.MainTable;
import com.xp.commonpart.bean.MainTableColumn;
import com.xp.commonpart.bean.MenuInfo;
import com.xp.commonpart.bean.TreeObject;
import com.xp.commonpart.dao.BaseDao;
import com.xp.commonpart.dao.MainTableDao;
import com.xp.commonpart.service.MainTableService;
import com.xp.commonpart.service.SelectDataService;
import com.xp.commonpart.util.ContextHolder;
import com.xp.commonpart.util.PropertiesUtil;
import com.xp.commonpart.util.SqlUtil;

public class MainTableServiceImpl implements MainTableService{
	private MainTableDao mainTableDao ;
	private SelectDataService selectDataService;
	private BaseDao baseDao;
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}

	public SelectDataService getSelectDataService() {
		return selectDataService;
	}

	public void setSelectDataService(SelectDataService selectDataService) {
		this.selectDataService = selectDataService;
	}

	public MainTableDao getMainTableDao() {
		return mainTableDao;
	}

	public void setMainTableDao(MainTableDao mainTableDao) {
		this.mainTableDao = mainTableDao;
	}

	public List findMainTableList() {
		// TODO Auto-generated method stub
		return mainTableDao.selectMainTable();
	}

	public MainTable saveMainTable(MainTable maintable) {
		// TODO Auto-generated method stub
		
		return mainTableDao.saveMainTable(maintable);
	}

	public void removeMainTable(MainTable maintable) {
		// TODO Auto-generated method stub
		mainTableDao.removeMainTable(maintable);
	}

	public MainTable findMainTableByMainId(Long maintableid) {
		// TODO Auto-generated method stub
		return mainTableDao.selectMainTableByMainId(maintableid);
	}

	public void removeMainTable(Long maintableid) {
		// TODO Auto-generated method stub
		MainTable maintable=this.findMainTableByMainId(maintableid);
		this.removeMainTable(maintable);
	}

	public List findMainTableColumn() {
		// TODO Auto-generated method stub
		return mainTableDao.selectMainTableColumnList();
	}

	public List findMainTableColumnById(Long mainTableId) {
		// TODO Auto-generated method stub
		
		return mainTableDao.selectMainTableColumnById(mainTableId);
	}

	public MainTableColumn findMainTableColumnByColumnId(Long columnid) {
		// TODO Auto-generated method stub
		return mainTableDao.selectMainTableColumnByColumnId(columnid);
	}

	public MainTableColumn saveMainTableColumn(MainTableColumn mainTableColumn) {
		// TODO Auto-generated method stub
		return mainTableDao.saveMainTableColumn(mainTableColumn);
	}

	public void removeMainTableColumnById(Long columnid) {
		// TODO Auto-generated method stub
		MainTableColumn mtc=this.findMainTableColumnByColumnId(columnid);
		if(mtc!=null){
			mainTableDao.removeObject(mtc);
		}
	}

	public String getTreeJson(List<TreeObject> tlist,String ischecked,String pid) {
		// TODO Auto-generated method stub
		List<TreeObject> tlist1=new ArrayList();
		tlist1.addAll(tlist);
		String json="{'text':'','id':'-1',children:[";
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
	
	public String getMenuTreeJson(List<MenuInfo> tlist) {
		// TODO Auto-generated method stub
		List<MenuInfo> tlist1=new ArrayList();
		tlist1.addAll(tlist);
		String json="{'text':'','id':'0','url':'',children:[";
		for(MenuInfo to:tlist){
			if(to.getPid()==null||to.getPid().equals("")||to.getPid().toString().equals("0")){
				tlist1.remove(to);
				json+="{'text':'"+to.getName()+"','id':'"+to.getId();
				json+="','pid':'"+to.getPid();
				if(to.getIschecked()!=null){
					json+="','checked':'"+to.getIschecked();	
				}
				if(to.getUrl()!=null){
					json+="','url':'"+to.getUrl();	
				}
				json+="',children:[";
				json+=this.getMenuChildrenData(tlist1, to);
				json+="]},";
			}
		} 
		if(json.length()!=0&&json.length()==json.lastIndexOf(",")+1){
			json=json.substring(0,json.length()-1);
		}
		json+="] ";
		json+="}";
		return json;
	}
	
	private String getMenuChildrenData(List<MenuInfo> tlist1,MenuInfo to){
		String json="";
		List<MenuInfo> tlist=new ArrayList();
		tlist.addAll(tlist1);
		for(MenuInfo to1:tlist1){
			if(to.getId().equals(to1.getPid())){
				tlist.remove(to1);
				json+="{'text':'"+to1.getName()+"','id':'"+to1.getId()+"','pid':'"+to1.getPid();
				if(to.getIschecked()!=null){
					json+="','checked':'"+to1.getIschecked();	
				}
				if(to.getUrl()!=null){
					json+="','url':'"+to1.getUrl();	
				}
				json+="',children:[";
				json+=this.getMenuChildrenData(tlist, to1);
				json+="]},";
			}
		}
		if(json.length()!=0&&json.length()==json.lastIndexOf(",")+1){
			json=json.substring(0,json.length()-1);
		}
		return json;
	}
	//加载字段
	public List  saveLoadMainTableColumn(String tableid,MainTable maintable){
		List<MainTableColumn> listc=this.findMainTableColumnById(Long.parseLong(tableid));
		String[] columnNames=selectDataService.getColumnName(SqlUtil.getSqlForLoadColumn(maintable));
		for(String columnName:columnNames){
			String[] propNames=new String[2];
			propNames[0]="columnName";
			propNames[1]="maintableid";
			Object[] propValues=new Object[2];
			propValues[0]=columnName;
			propValues[1]=maintable.getTableid();
			List list=baseDao.findObjectByPars(MainTableColumn.class, propNames, propValues);
			if(list.size()==0){
				MainTableColumn mainTableColumn=new MainTableColumn();
				mainTableColumn.setMaintableid(maintable.getTableid());
				mainTableColumn.setColumnName(columnName);
				mainTableColumn.setColumnCName(columnName);
				mainTableColumn.setPropertyType("1");
				mainTableColumn.setIsList("1");
				mainTableColumn.setIsQuery("0");
				mainTableColumn.setIsExport("1");
				mainTableColumn.setIsExportOrder(999);
				mainTableColumn.setIsListOrder(999);
				mainTableColumn.setIsQueryOrder(999);
				this.saveMainTableColumn(mainTableColumn);
			}else{
				MainTableColumn mainTableColumn=(MainTableColumn) list.get(0);
				listc.remove(mainTableColumn);
			}
		}
		for(MainTableColumn mainTableColumn:listc){
			this.removeMainTableColumnById(mainTableColumn.getColumnid());
		}
		List list=this.findMainTableColumnById(Long.parseLong(tableid));
		return list;
	}
	public List saveMainTableColumns(HttpServletRequest request){
		String[] isLists=request.getParameterValues("isList");
		String[] isQuerys=request.getParameterValues("isQuery");
		String[] isExport=request.getParameterValues("isExport");
		String[] isInserts=request.getParameterValues("isInsert");
		request.getParameterValues("isListOrder");
		Map maplist=new HashMap();
		Map mapquery=new HashMap();
		Map mapexport=new HashMap();
		Map mapinsert=new HashMap();
		if(isLists!=null){
			for(String islist:isLists){
				maplist.put(islist, islist);
			}
		}
		if(isQuerys!=null){
			for(String isquery:isQuerys){
				mapquery.put(isquery, isquery);
			}	
		}
		if(isExport!=null){
			for(String isexport:isExport){
				mapexport.put(isexport, isexport);
			}
		}
		if(isInserts!=null){
			for(String isinsert:isInserts){
				mapinsert.put(isinsert, isinsert);
			}
		}

		String maintableid=request.getParameter("maintableid");
		MainTable maintable=new MainTable();
		maintable=this.findMainTableByMainId(Long.parseLong(maintableid));
		request.setAttribute("maintable", maintable);
		//MainTableColumn maintablecolumn=new MainTableColumn();
		List list=this.findMainTableColumnById(Long.parseLong(maintableid));
		for(int i=0;i<list.size();i++){
			MainTableColumn mainTableColumn=(MainTableColumn) list.get(i);
			String id=mainTableColumn.getColumnid().toString();
			String listorder=request.getParameter("isListOrder_"+id);
			mainTableColumn.setIsListOrder(listorder.length()==0?999:Integer.valueOf(listorder));
			String queryorder=request.getParameter("isQueryOrder_"+id);
			mainTableColumn.setIsQueryOrder(queryorder.length()==0?999:Integer.valueOf(queryorder));
			String exportorder=request.getParameter("isExportOrder_"+id);
			mainTableColumn.setIsExportOrder(exportorder.length()==0?999:Integer.valueOf(exportorder));
			String insertorder=request.getParameter("isInsertOrder_"+id);
			mainTableColumn.setIsInsertOrder(insertorder.length()==0?999:Integer.valueOf(insertorder));
			
			//if(maplist.get(id)!=null||mapquery.get(id)!=null||mapexport.get(id)!=null){
				mainTableColumn.setIsList(maplist.get(id)!=null?"1":"0");
				mainTableColumn.setIsQuery(mapquery.get(id)!=null?"1":"0");
				mainTableColumn.setIsExport(mapexport.get(id)!=null?"1":"0");
				mainTableColumn.setIsInsert(mapinsert.get(id)!=null?"1":"0");
				this.saveMainTableColumn(mainTableColumn);
			//}
		}
		return list;
	}
	private String deleteFile(String path){
		File file=new File(path);
		if(!file.isDirectory()){
			file.delete();
		}else if(file.isDirectory()){
			String[] filelist=file.list();
			Date currDate=new Date();
			Long currDates=currDate.getTime();
			for(int i=0;i<filelist.length;i++){
				File delfile=new File(path+"\\"+filelist[i]);
				Long lastModified=delfile.lastModified();
				String times=PropertiesUtil.getProperties("system.removefiletime","5");
				//Calendar cal=Calendar.getInstance();
				//cal.setTimeInMillis(lastModified);
				//System.out.println(lastModified+"  "+cal.getTime().toLocaleString());
				Integer time=Integer.parseInt(times);
				if((currDates-lastModified)>time*60*1000){
					delfile.delete();
				}
			}
		}
		return null;
	}
}

