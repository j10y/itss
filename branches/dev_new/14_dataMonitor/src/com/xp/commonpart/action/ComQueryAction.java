package com.xp.commonpart.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.struts2.ServletActionContext;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.PieDataset;

import com.opensymphony.xwork2.ActionSupport;
import com.xp.commonpart.Page;
import com.xp.commonpart.bean.MainTable;
import com.xp.commonpart.bean.MainTableColumn;
import com.xp.commonpart.bean.QueryPanel;
import com.xp.commonpart.bean.StatisticsPicture;
import com.xp.commonpart.bean.SysTemUserLog;
import com.xp.commonpart.bean.TreeObject;
import com.xp.commonpart.service.BaseService;
import com.xp.commonpart.service.ComQueryService;
import com.xp.commonpart.service.SelectDataService;
import com.xp.commonpart.util.ContextHolder;
import com.xp.commonpart.util.DES;
import com.xp.commonpart.util.DateUtil;
import com.xp.commonpart.util.HttpUtil;
import com.xp.commonpart.util.JfreeChartUtil;
import com.xp.commonpart.util.PropertiesUtil;
import com.xp.commonpart.util.SqlUtil;

public class ComQueryAction extends ActionSupport{
	private SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
	private BaseService baseService=(BaseService) ContextHolder.getBean("baseService");
	private ComQueryService comqueryService=(ComQueryService) ContextHolder.getBean("comqueryService");
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	//跳转到通用查询页面
	public String getPage(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String istitle=request.getParameter("istitle");
		if(istitle!=null&&istitle.equals("0")){
			
		}else{
			istitle="1";
		}
		request.setAttribute("istitle", istitle);
		String tableName=request.getParameter("tableName");
		int pageSize=Integer.parseInt(PropertiesUtil.getProperties("system.pagesize", "12"));
		if(request.getParameter("pageSize")!=null){
			pageSize=Integer.parseInt(request.getParameter("pageSize"));
		}
		request.setAttribute("pageSize", pageSize);
		List list=baseService.findObjectByPar(MainTable.class, "tableName", tableName);
		Map resultMap=new HashMap();
		if(list.size()==0){
			resultMap.put("error", "没有要显示的表，请查看配置");
		}else if(list.size()>1){
			resultMap.put("error", "你可能又相同的表在表描述信息中，请查看配置");
		}
		MainTable maintable=new MainTable();
		maintable=(MainTable) list.get(0);

		//需要哪些查询条件的列表
		List queryList=new ArrayList();
		String[] propNames=new String[2];
		propNames[0]="isQuery";
		propNames[1]="maintableid";
		Object[] propValues=new Object[2];
		propValues[0]="1";
		propValues[1]=maintable.getTableid();
		List<MainTableColumn> qmtclist=baseService.findObjectByParsOrder(MainTableColumn.class,propNames, propValues,"isQueryOrder","asc");
		for(MainTableColumn mainTableColumn:qmtclist){
			//查询的表头
			QueryPanel querypanel=new QueryPanel();
				querypanel.setName(mainTableColumn.getColumnName());
				querypanel.setCname(mainTableColumn.getColumnCName());
				querypanel.setType(mainTableColumn.getPropertyType());
				querypanel.setMainTableColumn(mainTableColumn);
				if(mainTableColumn.getPropertyType().equals("2")||mainTableColumn.getPropertyType().equals("4")){
					List extlist=SqlUtil.getExtList(mainTableColumn,null);
					querypanel.setLists(extlist);
				}
				String value=request.getParameter(mainTableColumn.getColumnName());
				querypanel.setValue(value);
				queryList.add(querypanel);
				
				
		}
		request.setAttribute("queryList", queryList);
		request.setAttribute("tableName", tableName);
		request.setAttribute("maintable", maintable);
		//获取权限信息
		Map usermap=(Map) request.getSession().getAttribute("usermap");
		String username=(String) usermap.get("username");
		String operid=request.getParameter("operid");
		List list1=selectDataService.getData("select sys_sec_role.operateid from sys_sec_userinfo,sys_sec_role where sys_sec_role.id in (sys_sec_userinfo.role) and sys_sec_userinfo.username='"+username+"'");
		String operateid="0";
		if(list1!=null&&list1.size()>0){
			operateid=((Map)list1.get(0)).get("operateid").toString();
			String sql="select * from sys_sec_operate where sys_sec_operate.id in ("+operateid+") and sys_sec_operate.parentid='"+operid+"'";
			List<Map> rulelist=selectDataService.getData(sql);
			request.setAttribute("rulelist", rulelist);
		}
		if(operid==null){
			List<Map> rulelist=new ArrayList();
				Map map1=new HashMap();
				map1.put("operatename", "增加");
				Map map2=new HashMap();
				map2.put("operatename", "修改");
				Map map3=new HashMap();
				map3.put("operatename", "删除");
				Map map4=new HashMap();
				map4.put("operatename", "导出");
				rulelist.add(map1);
				rulelist.add(map2);
				rulelist.add(map3);
				rulelist.add(map4);
			request.setAttribute("rulelist", rulelist);
		}
		String sqlchart="select * from sys_statisticspicture where sysMainTable="+maintable.getTableid()+"";
		List chartlist=selectDataService.getData(sqlchart);
		request.setAttribute("chartlist", chartlist);
		return "page";
	}
	//获取数据
	public void toPage(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		Map map=comqueryService.queryTableForAjaxService(request);
		Map listValueMap=(Map) map.get("listValueMap");
		MainTable maintable=(MainTable) map.get("maintable");
		Page page=selectDataService.getListForPage(request, map.get("sql").toString());
		List<MainTableColumn> vmtclist=comqueryService.getTitleColumn(request.getParameter("tableName"));
		List list=(List)page.getData();
		String json="{\"total\":"+page.getTotal()+",\"rows\":[";
		for(int i=0;i<list.size();i++){
			Map st=(Map) list.get(i);
			json+="{\"id\":\""+st.get(maintable.getKeyColumnName())+"\",";
			for(MainTableColumn mc:vmtclist){
				if(mc.getPropertyType().equals("9")&&st.get(mc.getColumnName())!=null){
					String comment=st.get(mc.getColumnName()).toString();
					String comment1=st.get(mc.getColumnName()).toString();
					comment=HttpUtil.htmlOutEncode(comment);
					comment1=comment1.replace("\n", " ");
					comment1=comment1.replace("\r", "");
//					if(comment1.length()>=15){
//						comment1=comment1.substring(0,15);
//					}
					json+="\""+mc.getColumnName()+"\":\"<div height='100%'title=\'"+comment+"\'>"+comment1+"</div>\",";
				}else if((mc.getPropertyType().equals("5")||mc.getPropertyType().equals("10"))&&st.get(mc.getColumnName())!=null){
					json+="\""+mc.getColumnName()+"\":\""+st.get(mc.getColumnName()).toString().substring(0,19)+"\",";
				}else if(mc.getPropertyType().equals("7")){
					String comment=mc.getTypeSql();
					String[] comments=comment.split("#");
					String paramsurl="";
					if(comments!=null&&comments.length>1){
						String parms=comments[1];
						String[] parmsarray=parms.split("&");
						for(String parmse:parmsarray){
							String[] parmses=parmse.split("=");
							String parm0=parmses[0];
							String parm1=parmses[1];
							if(parm1.indexOf("'")==0&&parm1.lastIndexOf("'")==parm1.length()-1){
								paramsurl+=parm0+"="+parm1.substring(1,parm1.length()-1)+"&";
							}else if(parm1.indexOf("$")==0){
								
							}else{
								paramsurl+=parm0+"="+st.get(parm1)+"&";
							}
						}
					}
					json+="\""+mc.getColumnName()+"\":\"<a href='"+comments[0]+"?"+paramsurl+"'>"+mc.getColumnCName()+"</a>\",";
					
				}else if(mc.getPropertyType().equals("2")||mc.getPropertyType().equals("4")){
					Map valueMap=(Map) listValueMap.get(mc.getColumnName());
					String columnvalue="";
					String va="";
					if(st.get(mc.getColumnName()) instanceof BigDecimal){
						BigDecimal bd=(BigDecimal) st.get(mc.getColumnName());
						if(bd!=null){
							va= bd.toString();
						}
					}else{
						if(st.get(mc.getColumnName())!=null){
							va=st.get(mc.getColumnName()).toString();
						}
					}
					if(valueMap.get(va)!=null){
						columnvalue=valueMap.get(va).toString();
					}
					json+="\""+mc.getColumnName()+"\":\""+columnvalue+"\",";
				}else if(mc.getPropertyType().equals("8")){
					Map valueMap=(Map) listValueMap.get(mc.getColumnName());
					String columnvalue="";
					if(st.get(mc.getColumnName())!=null&&st.get(mc.getColumnName()).toString().length()>0){
						String value=st.get(mc.getColumnName()).toString();
						String[] values=value.split(",");
						for(String val:values){
							columnvalue+=valueMap.get(val)+",";
						}
						if(columnvalue.length()>0){
							columnvalue=columnvalue.substring(0,columnvalue.length()-1);
						}
					}
					json+="\""+mc.getColumnName()+"\":\""+columnvalue+"\",";
				}else{
					String columnvalue="";
					if(st.get(mc.getColumnName())!=null){
						columnvalue=st.get(mc.getColumnName()).toString();
						columnvalue=columnvalue.replace("\"", "\\\"");
					}
					json+="\""+mc.getColumnName()+"\":\""+columnvalue+"\",";
				}
				json+="\"comoption\":\""+st.get(maintable.getKeyColumnName())+"\",";
			}
			if(vmtclist!=null&&vmtclist.size()>0){
				json=json.substring(0,json.length()-1);
			}
			json+="},";
		}
		if(list!=null&&list.size()>0){
			json=json.substring(0,json.length()-1);
		}
		json+="]}";
		//json=json.replaceAll("\n", "").replaceAll("\r", "").replaceAll("<", "&lt;").replaceAll(">", "&gt;").replaceAll("\'", "&apos;");
		try {
			baseService.saveSysTemUserLog(request, "获取列表数据"+page.getSql());
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getTreeData(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String columnid=request.getParameter("columnid");
		//MainTableColumn mtc=mainTableService.findMainTableColumnByColumnId(Long.parseLong(columnid));
		MainTableColumn mtc=(MainTableColumn) baseService.findObjectByPar(MainTableColumn.class, "columnid", Long.parseLong(columnid)).get(0);
		String sql=mtc.getTypeSql();
		String sqls[]=sql.split("#");
		String pid="0";
		sql=sqls[0];
		Object parmsMap=request.getSession().getAttribute("parmsMap");
		if(sqls.length>=2&&parmsMap!=null){
			Map pmap=(Map) parmsMap;
			Object pa=pmap.get(sqls[1]);
			pid=pa.toString();
		}
		List<ListOrderedMap> list=selectDataService.getData(sql);
		List<TreeObject> tlist=new ArrayList();
		for(ListOrderedMap map:list){
			TreeObject to=new TreeObject();
			to.setId(map.get("id").toString());
			to.setName(map.get("name").toString());
			to.setPid(map.get("pid")!=null?map.get("pid").toString():"");
			to.setIcons(map.get("icons")!=null?map.get("icons").toString():"mnode");
			to.setCheckType(map.get("checktype")!=null?map.get("checktype").toString():"");
			to.setIsCheckType(map.get("ischecktype")!=null?map.get("ischecktype").toString():"");
			tlist.add(to);
		}
		String ischecked="0";
		if(mtc.getIsChecked()!=null&&mtc.getIsChecked().equals("1")){
			ischecked="1";
		}
		String json=comqueryService.getTreeJson(tlist,ischecked,pid);
		 try {
	        	response.setCharacterEncoding("utf-8");
				PrintWriter out=response.getWriter();
				out.print(json.toString());   
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
	//读取字段名字
	public String getTitleList(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String tableName=request.getParameter("tableName");
		List<MainTableColumn> vmtclist=comqueryService.getTitleColumn(tableName);
		 String json="[";
		 json+="{'columnName':'id','columnType':'checked'},";
		 for(MainTableColumn mc:vmtclist){
			 if(mc.getPropertyType().equals("5")||mc.getPropertyType().equals("10")){
				 json+="{'columnName':'"+mc.getColumnName()+"','columnCName':'"+mc.getColumnCName()+"','columnWidth':'130'},";
			 }else{
				String cl= mc.getColumnLength();
				if(cl!=null&&cl.length()>0){
				}else{
					cl="70";
				}
				json+="{'columnName':'"+mc.getColumnName()+"','columnCName':'"+mc.getColumnCName()+"','columnWidth':'"+cl+"'},";
			 }
			
		 }
		 json+="{'columnName':'comoption','columnCName':'操作','columnWidth':'70','columnType':'optioned'},";
		 if(json.length()>0){
			 json=json.substring(0,json.length()-1);
		 }
		 json+="]";
		try {
	        	response.setCharacterEncoding("utf-8");
				PrintWriter out=response.getWriter();
				out.print(json.toString());   
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
	//删除数据
	public String removeRealTable() {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String tableName=request.getParameter("tableName");
		List list=baseService.findObjectByPar(MainTable.class, "tableName", tableName);
		MainTable maintable=new MainTable();
		maintable=(MainTable) list.get(0);
		String ids=request.getParameter("ids");
		String []realtableids=ids.split(",");
		for(String realtableid:realtableids){
			String sql=SqlUtil.getDeleteSql(maintable.getTableRealName(),maintable.getKeyColumnName() , realtableid);
			try {
				selectDataService.remove(sql);
				//用户日志
				baseService.saveSysTemUserLog(request, "删除成功,"+sql);
			} catch (RuntimeException e) {
				//用户日志
				baseService.saveSysTemUserLog(request, "删除失败,"+sql);
				e.printStackTrace();
				try {
		        	response.setCharacterEncoding("utf-8");
					PrintWriter out=response.getWriter();
					out.print(false);   
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		try {
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(true);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//显示数据详细信息
	public String tableDetail(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String tableName=request.getParameter("tableName");
		//UserInfo loginuser=(UserInfo) request.getSession().getAttribute("loginuser");
		List list=baseService.findObjectByPar(MainTable.class, "tableName", tableName);
		MainTable maintable=new MainTable();
		maintable=(MainTable) list.get(0);
		List<MainTableColumn> mtclist=baseService.findObjectByParOrder(MainTableColumn.class, "maintableid", maintable.getTableid(),"columnid","asc");	
		//需要哪些显示的字段用来保存
		List detailList=new ArrayList();
		String[] propNames=new String[2];
		propNames[0]="isInsert";
		propNames[1]="maintableid";
		Object[] propValues=new Object[2];
		propValues[0]="1";
		propValues[1]=maintable.getTableid();
		List<MainTableColumn> qmtclist=baseService.findObjectByParsOrder(MainTableColumn.class,propNames, propValues,"isInsertOrder","asc");
		List queryList =new ArrayList();
		String realtableid=request.getParameter("realtableid");
		Map map=new HashMap();
		for(MainTableColumn mainTableColumn:qmtclist){
			QueryPanel querypanel=new QueryPanel();
				querypanel.setName(mainTableColumn.getColumnName());
				querypanel.setCname(mainTableColumn.getColumnCName());
				querypanel.setType(mainTableColumn.getPropertyType());
				querypanel.setMainTableColumn(mainTableColumn);
				if(mainTableColumn.getPropertyType().equals("2")||mainTableColumn.getPropertyType().equals("4")){
					List extlist=SqlUtil.getExtList(mainTableColumn,null);
					querypanel.setLists(extlist);
				}
				detailList.add(querypanel);
				if(mainTableColumn.getColumnName().equals(maintable.getKeyColumnName())){
					querypanel.setValue(realtableid);
					queryList.add(querypanel);
				}
		}
		if(realtableid!=null&&realtableid.length()>0){
			String selectsql=SqlUtil.getMainSql(detailList, queryList, maintable);
			List resoultList=selectDataService.getData(selectsql);
			map=(Map) resoultList.get(0);
			for(int i=0;i<detailList.size();i++){
				QueryPanel qp=(QueryPanel) detailList.get(i);
				 if(qp.getMainTableColumn().getPropertyType().equals("8")){
						String value=map.get(qp.getName())==null?"":map.get(qp.getName()).toString();
						qp.setValue(value);
						if(value!=null&&value.length()>0){
							List extlist=SqlUtil.getExtList(qp.getMainTableColumn(),value);
							qp.setLists(extlist);
						}
				}else if(qp.getMainTableColumn().getPropertyType().equals("11")){
					String value=map.get(qp.getName())==null?"":map.get(qp.getName()).toString();
					DES des;
					try {
						des = new DES();
						value = des.decrypt(value);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					map.put(qp.getName(), value);
				}
				 detailList.set(i, qp);
			}
		}
		request.setAttribute("realTableMap",map );
		request.setAttribute("maintable", maintable);
		request.setAttribute("detailList", detailList);
		String isView=request.getParameter("isView");
		String deleterule=request.getParameter("deleterule");
		request.setAttribute("deleterule", deleterule);
		if(isView!=null&&isView.equals("true")){
			return "tabledetailview";
		}else
		return "tabledetail";
	}
	//添加数据信息
	public String addRealTable() {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String tableName=request.getParameter("tableName");
		String userruledelete="0";
		if(request.getAttribute("userruledelete")!=null){
			userruledelete=request.getAttribute("userruledelete").toString();
		}
		List list=baseService.findObjectByPar(MainTable.class, "tableName", tableName);		
		MainTable maintable=new MainTable();
		maintable=(MainTable) list.get(0);
		List<MainTableColumn> mtclist=baseService.findObjectByParOrder(MainTableColumn.class, "maintableid", maintable.getTableid(),"columnid","asc");
		if(mtclist.size()==0){
			System.out.println("表的字段信息没有配置");
		}
		String json="true";
		//需要哪些显示的字段用来保存
		List detailList=new ArrayList();
		String[] propNames=new String[2];
		propNames[0]="isInsert";
		propNames[1]="maintableid";
		Object[] propValues=new Object[2];
		propValues[0]="1";
		propValues[1]=maintable.getTableid();
		List<MainTableColumn> qmtclist=baseService.findObjectByParsOrder(MainTableColumn.class,propNames, propValues,"isInsertOrder","asc");
		for(MainTableColumn mainTableColumn:qmtclist){
			QueryPanel querypanel=new QueryPanel();
				querypanel.setName(mainTableColumn.getColumnName());
				querypanel.setCname(mainTableColumn.getColumnCName());
				querypanel.setType(mainTableColumn.getPropertyType());
				querypanel.setMainTableColumn(mainTableColumn);
				String value=request.getParameter(mainTableColumn.getColumnName());
				if(value!=null&&value.length()>0){
					value=value.replaceAll("<br/>", "\n");
					if(mainTableColumn.getPropertyType().equals("11")){
						DES des;
						try {
							des = new DES();
							value = des.encrypt(value);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
				if(mainTableColumn.getTolerant()!=null&&mainTableColumn.getTolerant().length()>0&&value.length()==0){
					String[] tos=mainTableColumn.getTolerant().split(";");
					for(String to:tos){
						if(to.indexOf("'")==0&&to.lastIndexOf("'")==to.length()-1){
							value+=to.substring(1,to.length()-1);
						}else if(to.equals("Date()")){
							value+=DateUtil.convertDateToString(new Date());
							
						}else if(to.equals("DateTime()")){
							value+=DateUtil.convertDateTimeToString(new Date());
						}else if(to.equals("LongDateTime()")){
							value+=DateUtil.getDateToStringFull2(new Date())+System.currentTimeMillis()+DateUtil.getRandom(1000,9999);
						}else{
							value+=request.getParameter(to);
						}
					}
				}
				querypanel.setValue(value);
				detailList.add(querypanel);
		}
		String keyid=request.getParameter(maintable.getKeyColumnName());
		boolean haskey=keyid.length()>0?true:false;
		if(keyid.length()>0){
			String isidsql="select * from "+maintable.getTableRealName()+" where "+maintable.getKeyColumnName()+"='"+keyid+"'";
			List islist=selectDataService.getData(isidsql);
			if(islist.size()==0){
				haskey=false;
			}
		}
		String sql=SqlUtil.getInsertSql(detailList, maintable,haskey);
		try {
			selectDataService.saveRealTable(sql);
			//用户日志
			if(haskey==false){
				baseService.saveSysTemUserLog(request,"新增"+maintable.getTableCName()+"数据:"+sql);
				
			}else{
				baseService.saveSysTemUserLog(request,"修改"+maintable.getTableCName()+"数据:"+sql);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(haskey==false){
				baseService.saveSysTemUserLog(request,"新增"+maintable.getTableCName()+"数据失败:"+sql);
				
			}else{
				baseService.saveSysTemUserLog(request,"修改"+maintable.getTableCName()+"数据失败:"+sql);
			}
			json="false";
		}
		try {
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	//导出数据列表
	public String exportPageTableByAjax()  {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.getContentType();
		String tableName=request.getParameter("tableName");
		Map resultMap=comqueryService.queryTableForAjaxService(request);
		String json="";
		if(resultMap.get("error")!=null){
			json=(String) resultMap.get("error");
			json="{'flag':'false','desc':'"+json+"'}";
			baseService.saveSysTemUserLog(request,"导出"+tableName+"获取必要基础信息时失败！");
			try {
	        	response.setCharacterEncoding("utf-8");
				PrintWriter out=response.getWriter();
				out.print(json);   
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		Map listValueMap= (Map) resultMap.get("listValueMap");
		MainTable maintable= (MainTable) resultMap.get("maintable");
		List<QueryPanel> exportList= (List) resultMap.get("exportList");
		int count=exportList.size();
		String[] titles=new String[count];
		String[] pros=new String[count];
		String[] propertytype=new String[count];
		for(int i=0;i<count;i++){
			//在列表信息中要显示的字段
			QueryPanel exportpanel=exportList.get(i);
			titles[i]=exportpanel.getCname();
			pros[i]=exportpanel.getName();
			propertytype[i]=exportpanel.getMainTableColumn().getPropertyType();
		}
		String exportsql=resultMap.get("sql").toString();
		List list;
		try {
			list = selectDataService.getData(exportsql);
			String fileRootPath=request.getRealPath("exportFile");
			List<StatisticsPicture> splist=baseService.findObjectByPar(StatisticsPicture.class, "sysMainTable", maintable);
			String itemName= "";
			String lableTickName= "";
			String numberName="";
			Map imagepath=new HashMap();
			for(StatisticsPicture stp:splist){
				if(stp.getItemName()!=null){
					itemName=stp.getItemName().getColumnName();
				}
				if(stp.getLableTickName()!=null){
					lableTickName=stp.getLableTickName().getColumnName();
				}
				if(stp.getNumberName()!=null){
					numberName=stp.getNumberName().getColumnName();
				}
				String chartsql="";
				if(stp.getTjPictureType().equals("1")){
					chartsql="select chartsql."+itemName+", sum(chartsql."+numberName+") as numberName from ("+exportsql+") chartsql group by chartsql."+itemName;
				}else{
					chartsql="select chartsql."+itemName+",chartsql."+lableTickName+", sum(chartsql."+numberName+") as numberName from ("+exportsql+") chartsql group by chartsql."+itemName+",chartsql."+lableTickName;
				}
				List<Map> listdata = selectDataService.getData(chartsql);
				List listdateset=new ArrayList();
			    if(stp.getTjPictureType().equals("1")){
			    	for(Map mapdata:listdata){
			    		String[] values =new String[2];
			    		values[0]=mapdata.get(itemName)+"";
			   			values[1]=mapdata.get("numberName")+"";
			   			listdateset.add(values);
			    	}
			    }else{
			    	for(Map mapdata:listdata){
			    		String[] values =new String[3];
			    		values[0]=mapdata.get("numberName")+"";
			    		values[1]=mapdata.get(itemName)+"";
			   			values[2]=mapdata.get(lableTickName)+"";
			   			listdateset.add(values);
			    	}
			    }
			    PrintWriter out;
			    String webRoot = request.getSession().getServletContext().getRealPath(FSP)+FSP+"exportFile";
				try {
					out = response.getWriter();
					if(stp.getTjPictureType().equals("1")){//
						PieDataset dataset=JfreeChartUtil.getDataSetPie(listdateset);
						Map maps=new HashMap();
						maps.put("title", stp.getTjPictureName());
						String fileName=JfreeChartUtil.generatePieChart(request.getSession(),new PrintWriter(out),480,250,maps,dataset,webRoot);
						imagepath.put(stp.getTjPictureName(), fileName);
					}else if(stp.getTjPictureType().equals("2")){//
						CategoryDataset dataset=JfreeChartUtil.getDataSetBar(listdateset);
						Map maps=new HashMap();
						maps.put("title", stp.getTjPictureName());
						maps.put("X", stp.getXName());
						maps.put("Y", stp.getYName());
						String fileName=JfreeChartUtil.generateBarChart(request.getSession(),new PrintWriter(out),700,250,maps,dataset,webRoot);
						imagepath.put(stp.getTjPictureName(), fileName);
					}else if(stp.getTjPictureType().equals("3")){//
						CategoryDataset dataset=JfreeChartUtil.getDataSetBar(listdateset);
						Map maps=new HashMap();
						maps.put("title", stp.getTjPictureName());
						maps.put("X", stp.getXName());
						maps.put("Y", stp.getYName());
						String fileName=JfreeChartUtil.generateBarChartY(request.getSession(),new PrintWriter(out),700,250,maps,dataset,webRoot);
						imagepath.put(stp.getTjPictureName(), fileName);
					}else if(stp.getTjPictureType().equals("4")){//
						CategoryDataset dataset=JfreeChartUtil.getDataSetLine(listdateset);
						Map maps=new HashMap();
						maps.put("title", stp.getTjPictureName());
						maps.put("X", stp.getXName());
						maps.put("Y", stp.getYName());
						String fileName=JfreeChartUtil.generateLineChart(request.getSession(),new PrintWriter(out),700,250,maps,dataset,webRoot);
						imagepath.put(stp.getTjPictureName(), fileName);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			json=baseService.exportDataForlist(fileRootPath, maintable.getTableCName(), maintable.getTableName(), titles, pros, list,propertytype,listValueMap, "maplist",imagepath);
		} catch (RuntimeException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			json="{'flag':'false','desc':'导出失败！'}";
			baseService.saveSysTemUserLog(request,"导出"+maintable.getTableCName()+"表,执行sql"+exportsql+"时失败！");
			try {
	        	response.setCharacterEncoding("utf-8");
				PrintWriter out=response.getWriter();
				out.print(json);   
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			json="{'flag':'true','url':'exportFile/"+json+"','desc':'"+maintable.getTableCName()+"'}";
			baseService.saveSysTemUserLog(request,"导出"+maintable.getTableCName()+"表,执行sql"+exportsql+"时成功！");
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json); 
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 验证数据的唯一性
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String checkData() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
			String json=null;
			String tablename=request.getParameter("tablename");
			String value=request.getParameter("value");
			String columnName=request.getParameter("columnName");
			String keyColumnName=request.getParameter("keyColumnName");
			String realtableid=request.getParameter("realtableid");
			value=java.net.URLDecoder.decode(value,"UTF-8");
			realtableid=java.net.URLDecoder.decode(realtableid,"UTF-8");
			String sql="select * from "+tablename+" where "+columnName+"='"+value+"'";
			List list=selectDataService.getData(sql);
			if(list.size()>=2){
				json=list.size()+"";
			}else if(list.size()==1){
				if(realtableid.length()>0){
					String sql1="select * from "+tablename+" where "+keyColumnName+"='"+realtableid+"'";
					List list1=selectDataService.getData(sql1);
					Map dataMap=(Map) list1.get(0);
					String value1=dataMap.get(columnName).toString();
					if(!value1.equals(value)){
						json="1";
					}else{
						json="0";
					}
				}else{
					json="1";
				}
			}else{
				json="0";
			}
		try {
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json);   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	}
	return null;
	}
	public String getMenuTreeData(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String operid=request.getParameter("operid");
		String json="";
		String sql="select * from sys_sec_operate where urltype='1'";
		List<ListOrderedMap> list=selectDataService.getData(sql);
		List<TreeObject> tlist=new ArrayList();
		for(ListOrderedMap map:list){
			TreeObject to=new TreeObject();
			to.setId(map.get("id").toString());
			to.setName(map.get("operatename").toString());
			to.setPid(map.get("parentid")!=null?map.get("parentid").toString():"");
			to.setIcons("mnode");
			to.setUrl(map.get("url")+"");
			tlist.add(to);
		}
		String ischecked="0";
		
		json=comqueryService.getTreeJson(tlist,ischecked,operid);
		try {
        	response.setCharacterEncoding("utf-8");
			PrintWriter out=response.getWriter();
			out.print(json.toString());   
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
	}
}
