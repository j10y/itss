package com.digitalchina.info.framework.workflow.web.adapter.struts;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.jbpm.graph.def.Node;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.ProcessDefinition;


import com.digitalchina.info.appframework.extjs.servlet.CoderForList;
import com.digitalchina.info.appframework.extjs.servlet.CoderForSave;
import com.digitalchina.info.appframework.metadata.MetaDataManager;
import com.digitalchina.info.appframework.metadata.entity.UserTableSetting;
import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.entity.Role;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.info.framework.workflow.DefinitionService;
import com.digitalchina.info.framework.workflow.base.JbpmConfig;
import com.digitalchina.info.framework.workflow.base.JbpmContextFactory;
import com.digitalchina.info.framework.workflow.entity.ActionConfigUnit;
import com.digitalchina.info.framework.workflow.entity.ConfigUnit;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitRole;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitRoleTable;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitTimer;
import com.digitalchina.info.framework.workflow.entity.DefinitionInfo;
import com.digitalchina.info.framework.workflow.entity.DefinitionType;
import com.digitalchina.info.framework.workflow.entity.NodeType;
import com.digitalchina.info.framework.workflow.entity.PageModelConfigUnit;
import com.digitalchina.info.framework.workflow.entity.RuleConfigUnit;
import com.digitalchina.info.framework.workflow.info.NodeInfo;
import com.digitalchina.info.framework.workflow.entity.ConfigModel;

/**
 * 流程配置里面涉及到的action
 * 
 * @Class Name ProcessConfigAction
 * @author Yang Tao
 * @Create In Feb 11, 2009 TODO
 */
public class ConfigUnitTimerAction extends BaseDispatchAction {

	private Service service = (Service) ContextHolder.getBean("baseService");
	JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	
	/**
	 * 上传调度文件，调度文件路径保存起来了
	 * @Methods Name uploadRuleFile
	 * @Create In Mar 17, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public ActionForward uploadTimerClass(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws Exception {

		String result = null;
		String id = request.getParameter("timerId");
		ConfigUnitTimer configUnitTimer = (ConfigUnitTimer)super.getService().find(ConfigUnitTimer.class, id);
			
		String timePath=null;
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		List items = upload.parseRequest(request);
		Iterator iter = items.iterator();
		request.setCharacterEncoding("utf-8");
		try {
			while (iter.hasNext()) {				
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					String fileName = item.getName();
					String appendix = "";
					// 考虑到out文件可能不以.out为后缀
					int indexOfDot = fileName.lastIndexOf(".");
					if (indexOfDot >= 0) {
						appendix = fileName.substring(indexOfDot);
					}
					String timeName = fileName.substring(fileName.lastIndexOf("\\") + 1);										
					String filePath = FSP
							+ "\\WEB-INF\\classes\\com\\digitalchina\\itil\\timerClass"
							+ FSP + timeName;
					String realPath = request.getRealPath(FSP) + filePath;
					File uploadedFile = new File(realPath);
					item.write(uploadedFile);
					timeName = "/com/digitalchina/itil/timerClass/" + timeName;
					timePath=timeName;
					int p1 = fileName.lastIndexOf("\\");
					int p2 = fileName.lastIndexOf("/");
					if (p1 >= p2 && p1 >= 0) {
						fileName = fileName.substring(p1 + 1);
					}
					if (p2 >= p1 && p2 >= 0) {
						fileName = fileName.substring(p2 + 1);
					}					
				}else{
					String name=item.getFieldName();					
				}
			}
			configUnitTimer.setTimerPath(timePath)	;	
			super.getService().save(configUnitTimer);
			result = "{success:true,message:'上传成功'}";
		} catch (RuntimeException e) {
			result = "{success:flase,message:'上传失败'}";
			throw e;
		}
		response.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
		return null;
	}

}
