package com.xp.commonpart.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.xp.commonpart.bean.MainTable;
import com.xp.commonpart.bean.MainTableColumn;
import com.xp.commonpart.bean.StatisticsPicture;
import com.xp.commonpart.service.BaseService;
import com.xp.commonpart.service.MainTableService;
import com.xp.commonpart.service.SelectDataService;
import com.xp.commonpart.util.ContextHolder;


public class ManageAction extends ActionSupport{
	private SelectDataService selectDataService=(SelectDataService) ContextHolder.getBean("selectDataService");
	private BaseService baseService=(BaseService) ContextHolder.getBean("baseService");
	private MainTableService  mainTableService = (MainTableService) ContextHolder.getBean("mainTableService");
	public String toAdminManage(){
		return "success";
	}
	/**
	 * 查询主表列表
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findMainTableList() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		List list=mainTableService.findMainTableList();
		request.setAttribute("list", list);
		return "list";
	}
	/**
	 * 通过输入条件查询主表列表
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findMainTableListByPars() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		String tableName=request.getParameter("tableName");
		String tableCName=request.getParameter("tableCName");
		String[] propNames=new String[2];
		propNames[0]="tableName";
		propNames[1]="tableCName";
		Object[] propValues=new Object[2];
		propValues[0]=tableName;
		propValues[1]=tableCName;
		String[] tableids=new String[1];
		tableids[0]="tableid";
		String[] orders=new String[1];
		orders[0]="asc";
		List list=baseService.findObjectByLikeParsOrder(MainTable.class, propNames, propValues, tableids,orders);
		request.setAttribute("list", list);
		return "list";
	}
	
	/**
	 * 删除主表，删除同时也应删除主表相应的字段，或者逻辑删，增加标志为（待定）
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String removeMainTable() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		String maintableid=request.getParameter("maintableid");
		mainTableService.removeMainTable(Long.parseLong(maintableid));
		List list=mainTableService.findMainTableList();
		request.setAttribute("list", list);
		return "list";
	}
	
	/**
	 * 保存表信息
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String saveMainTable() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		String tableid=request.getParameter("tableid");
		//String className=request.getParameter("className");
		String tableCName=request.getParameter("tableCName");
		String tableName=request.getParameter("tableName");
		String tableRealName=request.getParameter("tableRealName");
		String sql=request.getParameter("sql");
		String formatType=request.getParameter("formatType");
		
		String isMulti=request.getParameter("isMulti");
		String multiSql=request.getParameter("multiSql");
		//String classPath=request.getParameter("classPath");
		String keyColumnName=request.getParameter("keyColumnName");
		String isPutTemp=request.getParameter("isPutTemp");
		
		if(keyColumnName==null||keyColumnName.equals("")){
			keyColumnName="ID";
		}
		tableCName=tableCName;
		if(sql.length()>0){
			sql=sql;
		}
		MainTable maintable=new MainTable();
		if(tableid.length()>0){
			maintable.setTableid(Long.parseLong(tableid));
		}
		//maintable.setClassName(className);
		maintable.setTableCName(tableCName);
		maintable.setTableName(tableName);
		maintable.setFormatType(formatType);
		maintable.setTableRealName(tableRealName);
		///maintable.setClassPath(classPath);
		maintable.setSql(sql);
		maintable.setIsMulti(isMulti);
		maintable.setMultiSql(multiSql);
		maintable.setKeyColumnName(keyColumnName);
		maintable.setIsPutTemp(isPutTemp);
		maintable=mainTableService.saveMainTable(maintable);
		List list=mainTableService.findMainTableList();
		request.setAttribute("list", list);
		request.setAttribute("maintable", maintable);
		return "list";
	}
	/**
	 * 修改表信息
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String modifyMainTable() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		String maintableid=request.getParameter("maintableid");
		if(maintableid!=null&&maintableid.length()>0){
			MainTable maintable=new MainTable();
			maintable=mainTableService.findMainTableByMainId(Long.parseLong(maintableid));
			List<MainTableColumn> columnlist=mainTableService.findMainTableColumnById(maintable.getTableid());
			request.setAttribute("maintable", maintable);
			request.setAttribute("columnlist", columnlist);
		}
		return "detail";
	}
	/**
	 * 主表字段列表
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String mainTableColumnList() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		String maintableid=request.getParameter("maintableid");
		MainTable maintable=new MainTable();
		maintable=mainTableService.findMainTableByMainId(Long.parseLong(maintableid));
		request.setAttribute("maintable", maintable);
		//MainTableColumn maintablecolumn=new MainTableColumn();
		List list=mainTableService.findMainTableColumnById(Long.parseLong(maintableid));
		request.setAttribute("list", list);
		return "maintablecolumnlist";
	}
	
	/**
	 * 获取设定值得列表信息
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String mainTableColumnListSet() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		String maintableid=request.getParameter("maintableid");
		MainTable maintable=new MainTable();
		maintable=mainTableService.findMainTableByMainId(Long.parseLong(maintableid));
		request.setAttribute("maintable", maintable);
		//MainTableColumn maintablecolumn=new MainTableColumn();
		List list=mainTableService.findMainTableColumnById(Long.parseLong(maintableid));
		request.setAttribute("list", list);
		return "maintablecolumnlistset";
	}
	/**
	 * 设定需要的导出或查询或显示或插入的值
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String saveMainTableColumnList() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		List list=mainTableService.saveMainTableColumns(request);
		request.setAttribute("list", list);
		return "maintablecolumnlist";
	}
	
	/**
	 * 修改表字段信息
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String modifyMainTableColumn() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		String columnid=request.getParameter("columnid");
		MainTableColumn maintablecolumn=new MainTableColumn();
		maintablecolumn=mainTableService.findMainTableColumnByColumnId(Long.parseLong(columnid));
		request.setAttribute("maintablecolumn", maintablecolumn);
		
		MainTable maintable=new MainTable();
		maintable=mainTableService.findMainTableByMainId(maintablecolumn.getMaintableid());
		request.setAttribute("maintable", maintable);
		List<MainTableColumn> columnlist=mainTableService.findMainTableColumnById(maintable.getTableid());
		request.setAttribute("columnlist", columnlist);
		return "maintablecolumndetail";
	}
	/**
	 * 删除字段信息
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String removeMainTableColumn() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		String columnid=request.getParameter("columnid");
		mainTableService.removeMainTableColumnById(Long.parseLong(columnid));
		String maintableid=request.getParameter("maintableid");
		MainTable maintable=new MainTable();
		maintable=mainTableService.findMainTableByMainId(Long.parseLong(maintableid));
		request.setAttribute("maintable", maintable);
		List list=mainTableService.findMainTableColumnById(Long.parseLong(maintableid));
		request.setAttribute("list", list);
		return "maintablecolumnlist";
	}
	
	/**
	 * 添加主表字段信息
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String addMainTableColumn() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		String tableid=request.getParameter("tableid");
		MainTable maintable=new MainTable();
		maintable=mainTableService.findMainTableByMainId(Long.parseLong(tableid));
		request.setAttribute("maintable", maintable);
		List<MainTableColumn> columnlist=mainTableService.findMainTableColumnById(maintable.getTableid());
		request.setAttribute("columnlist", columnlist);
		return "maintablecolumndetail";
	}
	
	/**
	 * 保存主表字段信息
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String saveMainTableColumn() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		String maintableid=request.getParameter("maintableid");
		String columnid=request.getParameter("columnid");
		String columnName=request.getParameter("columnName");
		String columnCName=request.getParameter("columnCName");
		String isQuery=request.getParameter("isQuery");
		String isUpdate=request.getParameter("isUpdate");
		String isExport=request.getParameter("isExport");
		String isInsert=request.getParameter("isInsert");
		String isInsertOrder=request.getParameter("isInsertOrder");
		String isList=request.getParameter("isList");
		String isQueryOrder=request.getParameter("isQueryOrder");
		String isExportOrder=request.getParameter("isExportOrder");
		String isListOrder=request.getParameter("isListOrder");
		String isMust=request.getParameter("isMust");
		String propertyType=request.getParameter("propertyType");
		String typeSql=request.getParameter("typeSql");
		String dataType=request.getParameter("dataType");
		String tolerant=request.getParameter("tolerant");
		String isChecked=request.getParameter("isChecked");
		String likescope=request.getParameter("likescope");
		
		String isUnique=request.getParameter("isUnique");
		String upColumnName=request.getParameter("upColumnName");
		String validDataType=request.getParameter("validDataType");
		String columnLength=request.getParameter("columnLength");
		String allTreeStatus=request.getParameter("allTreeStatus");
		MainTableColumn maintablecolumn=new MainTableColumn();
		maintablecolumn.setLikescope(likescope);
		if(columnid.length()>0){
			maintablecolumn.setColumnid(Long.parseLong(columnid));
		}
		if(maintableid.length()>0){
			maintablecolumn.setMaintableid(Long.parseLong(maintableid));
		}
		maintablecolumn.setColumnName(columnName);
		maintablecolumn.setColumnCName(columnCName);
		maintablecolumn.setIsExport(isExport);
		maintablecolumn.setIsQuery(isQuery);
		maintablecolumn.setIsUpdate(isUpdate);
		maintablecolumn.setIsInsert(isInsert);
		maintablecolumn.setIsMust(isMust);
		maintablecolumn.setIsChecked(isChecked);
		if(isInsertOrder.length()>0){
			maintablecolumn.setIsInsertOrder(Integer.parseInt(isInsertOrder));
		}
		if(isExportOrder.length()>0){
			maintablecolumn.setIsExportOrder(Integer.parseInt(isExportOrder));
		}
		if(isQueryOrder.length()>0){
			maintablecolumn.setIsQueryOrder(Integer.parseInt(isQueryOrder));
		}
		if(isListOrder.length()>0){
			maintablecolumn.setIsListOrder(Integer.parseInt(isListOrder));
		}
		maintablecolumn.setIsList(isList);
		maintablecolumn.setPropertyType(propertyType);
		
		maintablecolumn.setTolerant(tolerant);
		maintablecolumn.setTypeSql(typeSql);
		maintablecolumn.setDataType(dataType);
		maintablecolumn.setIsUnique(isUnique);
		maintablecolumn.setUpColumnName(upColumnName);
		maintablecolumn.setValidDataType(validDataType);
		maintablecolumn.setColumnLength(columnLength);
		maintablecolumn.setAllTreeStatus(allTreeStatus);
		mainTableService.saveMainTableColumn(maintablecolumn);
		MainTable maintable=new MainTable();
		maintable=mainTableService.findMainTableByMainId(Long.parseLong(maintableid));
		request.setAttribute("maintable", maintable);

		List list=mainTableService.findMainTableColumnById(Long.parseLong(maintableid));
		request.setAttribute("list", list);
		return "maintablecolumnlist";
	}
	 /**
	  * 加载表的字段，根据maintable中配置的sql语句返回的字段结果集来加载要显示的字段
	  */
	public String loadMainTableColumn() throws Exception {
		HttpServletRequest request=ServletActionContext.getRequest();
		String tableid=request.getParameter("tableid");
		MainTable maintable=new MainTable();
		maintable=mainTableService.findMainTableByMainId(Long.parseLong(tableid));
		List list=mainTableService.saveLoadMainTableColumn(tableid,maintable);
		request.setAttribute("list", list);
		request.setAttribute("maintable", maintable);
		return "maintablecolumnlist";
	}
	public String toUserManage(){
		HttpServletRequest request=ServletActionContext.getRequest();
		Map usermap=(Map) request.getSession().getAttribute("usermap");
		String username=(String) usermap.get("username");
		String operid=request.getParameter("operid");
		List list1=selectDataService.getData("select sys_sec_role.operateid from sys_sec_userinfo,sys_sec_role where sys_sec_role.id in (sys_sec_userinfo.role) and sys_sec_userinfo.username='"+username+"'");
		String operateid="0";
		if(list1!=null&&list1.size()>0){
			operateid=((Map)list1.get(0)).get("operateid").toString();
			String sql="select * from sys_sec_operate where sys_sec_operate.id in ("+operateid+") and sys_sec_operate.parentid='"+operid+"'";
			List<Map> list=selectDataService.getData(sql);
			request.setAttribute("list", list);
		}
		request.setAttribute("operid", operid);
		return "usermanage";
	}
	public String mainTableChartList(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String tableid=request.getParameter("maintableid");
		MainTable maintable=new MainTable();
		maintable=mainTableService.findMainTableByMainId(Long.parseLong(tableid));
		List list=baseService.findObjectByPar(StatisticsPicture.class, "sysMainTable", maintable);
		request.setAttribute("list", list);
		request.setAttribute("maintable", maintable);
		return "tjlist";
	}
	public String toChartDetail(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String tableid=request.getParameter("tableid");
		String id=request.getParameter("id");
		MainTable maintable=new MainTable();
		maintable=mainTableService.findMainTableByMainId(Long.parseLong(tableid));
		List<MainTableColumn> columnlist=mainTableService.findMainTableColumnById(maintable.getTableid());
		StatisticsPicture sp=new StatisticsPicture();
		if(id!=null&&id.length()>0){
			List chartlist=baseService.findObjectByPar(StatisticsPicture.class, "id", Long.parseLong(id));
			sp=(StatisticsPicture) chartlist.get(0);
			request.setAttribute("sp", sp);
		}else{
		
		}
		request.setAttribute("maintable", maintable);
		request.setAttribute("columnlist", columnlist);
		return "tjdetail";
	}
	public String removeChartColumn(){
		HttpServletRequest request=ServletActionContext.getRequest();
		String id=request.getParameter("id");
		String maintableid=request.getParameter("tableid");
		List chartlist=baseService.findObjectByPar(StatisticsPicture.class, "id", Long.parseLong(id));
		StatisticsPicture sp=(StatisticsPicture) chartlist.get(0);
		baseService.remove(sp);
		MainTable maintable=new MainTable();
		maintable=mainTableService.findMainTableByMainId(Long.parseLong(maintableid));
		List list=baseService.findObjectByPar(StatisticsPicture.class, "sysMainTable", maintable);
		request.setAttribute("list", list);
		request.setAttribute("maintable", maintable);
		return "tjlist";
	}
	public String saveChartDetail(){
		HttpServletRequest request=ServletActionContext.getRequest();
		HttpServletResponse response=ServletActionContext.getResponse();
		String maintableid=request.getParameter("maintableid");
		MainTable maintable=new MainTable();
		maintable=mainTableService.findMainTableByMainId(Long.parseLong(maintableid));
		String id=request.getParameter("id");
		String tjPictureName=request.getParameter("tjPictureName");
		String tjPictureURL=request.getParameter("tjPictureURL");
		String tjPictureType=request.getParameter("tjPictureType");
		String XName=request.getParameter("XName");
		String YName=request.getParameter("YName");
		String itemName=request.getParameter("itemName");
		String lableTickName=request.getParameter("lableTickName");
		String numberName=request.getParameter("numberName");
		StatisticsPicture sp=new StatisticsPicture();
		if(id!=null&&id.length()>0){
			sp.setId(Long.parseLong(id));
		}
		if(itemName!=null&&itemName.length()>0){
			MainTableColumn mtc=mainTableService.findMainTableColumnByColumnId(Long.parseLong(itemName));
			sp.setItemName(mtc);
		}
		if(lableTickName!=null&&lableTickName.length()>0){
			MainTableColumn mtc1=mainTableService.findMainTableColumnByColumnId(Long.parseLong(lableTickName));
			sp.setLableTickName(mtc1);
		}
		if(numberName!=null&&numberName.length()>0){
			MainTableColumn mtc2=mainTableService.findMainTableColumnByColumnId(Long.parseLong(numberName));
			sp.setNumberName(mtc2);
		}
		sp.setSysMainTable(maintable);
		sp.setTjPictureName(tjPictureName);
		sp.setTjPictureType(tjPictureType);
		sp.setTjPictureURL(tjPictureURL);
		sp.setXName(XName);
		sp.setYName(YName);
		baseService.save(sp, StatisticsPicture.class, "id");
		List list=baseService.findObjectByPar(StatisticsPicture.class, "sysMainTable", maintable);
		request.setAttribute("list", list);
		request.setAttribute("maintable", maintable);
		return "tjlist";
	}
}
