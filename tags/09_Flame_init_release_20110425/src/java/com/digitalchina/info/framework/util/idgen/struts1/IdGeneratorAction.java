package com.digitalchina.info.framework.util.idgen.struts1;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jbpm.graph.def.ProcessDefinition;

import com.digitalchina.info.appframework.extjs.servlet.CoderForList;
import com.digitalchina.info.appframework.metadata.MetaDataManager;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableIdBuilder;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.Module;
import com.digitalchina.info.framework.security.entity.Role;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.util.PropertiesUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.info.framework.workflow.ConfigUnitService;
import com.digitalchina.info.framework.workflow.DefinitionService;
import com.digitalchina.info.framework.workflow.UpdateWorkflowService;
import com.digitalchina.info.framework.workflow.entity.ActionConfigUnit;
import com.digitalchina.info.framework.workflow.entity.ConfigModel;
import com.digitalchina.info.framework.workflow.entity.ConfigUnit;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitRole;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitRoleTable;
import com.digitalchina.info.framework.workflow.entity.DefinitionInfo;
import com.digitalchina.info.framework.workflow.entity.DefinitionType;
import com.digitalchina.info.framework.workflow.entity.PageModelConfigUnit;
import com.digitalchina.info.framework.workflow.entity.RuleConfigUnit;
import com.digitalchina.info.framework.workflow.entity.SubProcessConfigUnit;
import com.digitalchina.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.digitalchina.info.framework.workflow.entity.VirtualNodeInfo;
import com.digitalchina.info.framework.workflow.info.NodeInfo;

/**
 * 编号生成器Action
 * 
 * @Class Name IdGeneratorAction
 * @author zhangzy
 * @Create In 22 02, 2010 TODO
 */
public class IdGeneratorAction extends BaseDispatchAction {
	DefinitionService ds = (DefinitionService) ContextHolder
			.getBean("definitionService");

	private Service service = (Service) ContextHolder.getBean("baseService");
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder
			.getBean("metaDataManager");
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	private UpdateWorkflowService updateWorkflowService = (UpdateWorkflowService) ContextHolder
			.getBean("updateWorkflowService");
	private ConfigUnitService cs = (ConfigUnitService) ContextHolder
			.getBean("configUnitService");
	
	/**
	 * 保存数据
	 * @Class Name save
	 * @Author zhangzy
	 * @Create In 04 02, 2010
	 */	
	public ActionForward save(ActionMapping mapping,
			ActionForm actionForm,HttpServletRequest request,
			HttpServletResponse response)throws Exception{
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
		String systemRulePath = PropertiesUtil.getProperties("idgen.rule.package","\\WEB-INF\\classes\\com\\digitalchina\\info\\framework\\util\\idGen");
		String systemRulePathDb = PropertiesUtil.getProperties("idgen.rult.filePackage","/com/digitalchina/info/framework/util/idgen/");
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = upload.parseRequest(request);
		request.setCharacterEncoding(this.getProperties(
				"system.characterEncoding", "gbk"));
		Iterator iter = items.iterator();		

		while (iter.hasNext()) {		
			FileItem item = (FileItem) iter.next();
			if (!item.isFormField() && item.getName() != null
					&& !"".equals(item.getName())) {
				String fileName = item.getName();
				String ruleName = fileName.substring(fileName
						.lastIndexOf("\\") + 1);
				String filePath = FSP + systemRulePath + FSP + ruleName;
				String realPath = request.getRealPath(FSP) + filePath;
				File uploadedFile = new File(realPath);
				item.write(uploadedFile);
			}
		}
		String StrId = request.getParameter("systemMainTableIdBuilder.id");
		SystemMainTableIdBuilder smtib =null;
		if(StrId==null||StrId.equals("")){
			smtib = new SystemMainTableIdBuilder();
			if(StrRuleFileName!=null&&!StrRuleFileName.equals("")){
				smtib.setRuleFileName(systemRulePathDb+StrRuleFileName);
			}
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
		return null;
	}	
}
