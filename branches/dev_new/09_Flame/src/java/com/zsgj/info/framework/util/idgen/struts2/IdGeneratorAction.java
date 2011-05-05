package com.zsgj.info.framework.util.idgen.struts2;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableIdBuilder;
import com.zsgj.info.appframework.metadata.service.IdGeneratorService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;

public class IdGeneratorAction extends  BaseAction {
	
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
	private Service service = (Service) ContextHolder.getBean("baseService");
	private IdGeneratorService idGeneratorService;
	
	

	public IdGeneratorService getIdGeneratorService() {
		return idGeneratorService;
	}
	public void setIdGeneratorService(IdGeneratorService idGeneratorService) {
		this.idGeneratorService = idGeneratorService;
	}
	/**
	 * 查询编号生成器列表
	 * @Class Name query
	 * @Author zhangzy
	 * @Create In 04 02, 2010
	 */	
	public String query() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		int pageSize = HttpUtil.getInt(request, "pageSize",10);
		int start = HttpUtil.getInt(request, "start", 0);
		int pageNo=start/pageSize+1;
		String tableId = request.getParameter("systemMainTable");
		String deptId = request.getParameter("department");
		Page page = idGeneratorService.findSystemMainTableIdBuilder(tableId, deptId, pageNo, pageSize);
		Long total = page.getTotalCount();
		List<SystemMainTableIdBuilder> queryList = page.list();
		String json = "";//new String("{success: true, rowCount:" + total + ",data:[");
		for(SystemMainTableIdBuilder idBuilder : queryList){
			json += "{'id':'" +idBuilder.getId();
			json += "','systemMainTable':'" +idBuilder.getSystemMainTable().getTableCnName();
			json += "','department':'" +idBuilder.getDepartment().getDepartName();
			json += "','prefix':'" +idBuilder.getPrefix();
			json += "','length':'" +idBuilder.getLength();
			json += "','deployFlag':'" +idBuilder.getDeployFlag();
			json += "','ruleFileName':'" +idBuilder.getRuleFileName();
			json += "','latestValue':'" +idBuilder.getLatestValue()+"'},";
			
		}
		if(json!=""){
			json = json.substring(0, json.length()-1);
		}
		json = "{success: true, rowCount:" + total + ",data:["+json+"]}";
		json = json.replaceAll("\\\\", "\\\\\\\\");			
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 得到要修改的数据
	 * @Class Name preUpdate
	 * @Author zhangzy
	 * @Create In 04 02, 2010
	 */		
	public String preUpdate() throws Exception {
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		StringBuilder json = new StringBuilder("{success:true,");
		Long recordId =Long.parseLong(request.getParameter("recordId"));
		SystemMainTableIdBuilder sti = (SystemMainTableIdBuilder) service.findUnique(SystemMainTableIdBuilder.class, "id", recordId);
		json.append("id:'"+sti.getId()+"',");
		json.append("systemMainTable:'"+sti.getSystemMainTable()+"',");
		json.append("systemMainTableId:'"+sti.getSystemMainTable().getId()+"',");
		json.append("systemMainTableName:'"+sti.getSystemMainTable().getTableCnName()+"',");
		json.append("tableName:'"+sti.getTableName()+"',");
		json.append("department:'"+sti.getDepartment()+"',");
		json.append("departmentId:'"+sti.getDepartment().getId()+"',");
		json.append("departmentName:'"+sti.getDepartment().getDepartName()+"',");
		json.append("prefix:'"+sti.getPrefix()+"',");
		json.append("length:'"+sti.getLength()+"',");
		json.append("ruleFileName:'"+sti.getRuleFileName()+"',");
		json.append("deployFlag:'"+sti.getDeployFlag()+"',");
		json.append("latestValue:'"+sti.getLatestValue()+"'}"); 
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 保存数据()
	 * @Class Name save
	 * @Author zhangzy
	 * @Create In 04 02, 2010
	 */	
	public String save()throws Exception{
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		String StrSystemMainTable = request.getParameter("systemMainTableIdBuilder.SystemMainTable");
		SystemMainTable sm = (SystemMainTable) service.find(SystemMainTable.class,StrSystemMainTable);
		
		String StrDepartment = request.getParameter("systemMainTableIdBuilder.department");
		Department department = (Department) service.findUnique(Department.class, "departCode", Long.parseLong(StrDepartment));
		

		
		String StrPrefix = request.getParameter("systemMainTableIdBuilder.prefix");
		String StrLength = request.getParameter("systemMainTableIdBuilder.length");
		String StrDeployFlag = request.getParameter("systemMainTableIdBuilder.deployFlag");
		String StrLatestValue = request.getParameter("systemMainTableIdBuilder.latestValue");
		String StrRuleFileName = request.getParameter("ruleFileName");
		StrRuleFileName = StrRuleFileName.substring(StrRuleFileName
				.lastIndexOf("\\") + 1);		
		
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = upload.parseRequest(request);
		request.setCharacterEncoding(this.getProperties(
				"system.characterEncoding", "gbk"));
		Iterator iter = items.iterator();		
		String realPath = "\\WEB-INF\\classes\\com\\zsgj\\poc\\" + StrRuleFileName;
		while (iter.hasNext()) {
			FileItem item = (FileItem) iter.next();
			File uploadedFile = new File(realPath);
			item.write(uploadedFile);
		}
		

		String StrId = request.getParameter("systemMainTableIdBuilder.id");
		SystemMainTableIdBuilder smtib =null;
		if(StrId==null||StrId.equals("")){
			smtib = new SystemMainTableIdBuilder();
		}else{
			smtib = (SystemMainTableIdBuilder) service.find(SystemMainTableIdBuilder.class, StrId);
		}
		smtib.setSystemMainTable(sm);
		smtib.setTableName(sm.getTableName());
		smtib.setDepartment(department);
		smtib.setPrefix(StrPrefix);
		smtib.setLength(Long.parseLong(StrLength));
		smtib.setDeployFlag(Integer.parseInt(StrDeployFlag));
		smtib.setLatestValue(StrLatestValue);
		smtib.setRuleFileName(StrRuleFileName);
		service.save(smtib);
		StringBuilder json = new StringBuilder("{success:true,");
		json.append("id:'"+smtib.getId());
		json.append("'}");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "success";
	}
	/**
	 * 删除数据
	 * @Class Name delete
	 * @Author zhangzy
	 * @Create In 04 02, 2010
	 */	
	public String delete(){
		HttpServletRequest request = super.getRequest();
		HttpServletResponse response = super.getResponse();
		
		String id = request.getParameter("id");		
		SystemMainTableIdBuilder smtib = (SystemMainTableIdBuilder) service.find(SystemMainTableIdBuilder.class, id);
		service.remove(smtib);
		StringBuilder json = new StringBuilder("{success:true");
		json.append("}");
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");		
		PrintWriter out;
		try {
			out = response.getWriter();
			out.println(json.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return SUCCESS;		
	}
	/**
	 * 根据请求中的参数为下拉框查找数据
	 * 
	 * @Methods Name findDepartmentComboList
	 * @Create In 23 02, 2009 By zhangzy
	 * @return String
	 * @throws IOException
	 */
	@SuppressWarnings( { "unused", "unchecked" })
	public String findDepartmentComboList() throws IOException {
		try {
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			int pageSize = HttpUtil.getInt(getRequest(), "pageSize", 10);
			int start = HttpUtil.getInt(getRequest(), "start", 0);
			int pageNo = start / pageSize + 1;
			String StrDepartName = request.getParameter("departName");
			Page page = idGeneratorService.findAllDepartment(StrDepartName , pageNo, pageSize);
			Long total = page.getTotalCount();
			List<Map<String, Object>> listData = metaDataManager.getEntityMapDataForList(Department.class, page
					.list());
			StringBuilder json = new StringBuilder("{success: true, rowCount:" + total + ",data:[");
			for (int i = 0; i < listData.size(); i++) {
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("departCode:'" + listData.get(i).get("departCode") + "',");
				json.append("departName:'" + listData.get(i).get("departName") + "'");
				json.append("}");
			}
			json.append("]");
			json.append("}");
			
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");		
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	} catch (Exception e) {
		e.printStackTrace();
		throw new ApplicationException();
	}
	}
	
	/**
	 * 根据请求中的参数为下拉框查找数据
	 * 
	 * @Methods Name findSystemMainTableComboList
	 * @Create In 23 02, 2009 By zhangzy
	 * @return String
	 * @throws IOException
	 */
	@SuppressWarnings( { "unused", "unchecked" })
	public String findSystemMainTableComboList() throws IOException {
		try {
			HttpServletRequest request = super.getRequest();
			HttpServletResponse response = super.getResponse();
			int pageSize = HttpUtil.getInt(getRequest(), "pageSize", 10);
			int start = HttpUtil.getInt(getRequest(), "start", 0);
			int pageNo = start / pageSize + 1;

			String StrTableCnName = request.getParameter("tableCnName");		
			Page page = idGeneratorService.findAllSystemMainTable(StrTableCnName , pageNo, pageSize);
			Long total = page.getTotalCount();
			List<Map<String, Object>> listData = metaDataManager.getEntityMapDataForList(SystemMainTable.class, page
					.list());
			StringBuilder json = new StringBuilder("{success: true, rowCount:" + total + ",data:[");
			for (int i = 0; i < listData.size(); i++) {
				if (i != 0)
					json.append(",");
				json.append("{");
				json.append("id:'" + listData.get(i).get("id") + "',");
				json.append("tableCnName:'" + listData.get(i).get("tableCnName") + "'");
				json.append("}");
			}
			json.append("]");
			json.append("}");
			
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");		
			PrintWriter out;
			try {
				out = response.getWriter();
				out.println(json.toString());
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
	} catch (Exception e) {
		e.printStackTrace();
		throw new ApplicationException();
	}
	}
	

}
