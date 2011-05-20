package com.zsgj.info.framework.workflow.web.adapter.struts;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.jbpm.JbpmContext;

import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.workflow.base.JbpmContextFactory;
import com.zsgj.info.framework.workflow.entity.ConfigUnitTimer;

/**
 * 流程配置里面涉及到的action
 * 
 * @Class Name ProcessConfigAction
 * @author Yang Tao
 * @Create In Feb 11, 2009 TODO
 */
public class ConfigUnitTimerAction extends BaseDispatchAction {

//	private Service service = (Service) ContextHolder.getBean("baseService");
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
	@SuppressWarnings("deprecation")
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
//					String appendix = "";
					// 考虑到out文件可能不以.out为后缀
//					int indexOfDot = fileName.lastIndexOf(".");
//					if (indexOfDot >= 0) {
//						appendix = fileName.substring(indexOfDot);
//					}
					String timeName = fileName.substring(fileName.lastIndexOf("\\") + 1);										
					String filePath = FSP
							+ "\\WEB-INF\\classes\\com\\zsgj\\itil\\timerClass"
							+ FSP + timeName;
					String realPath = request.getRealPath(FSP) + filePath;
					File uploadedFile = new File(realPath);
					item.write(uploadedFile);
					timeName = "/com/zsgj/itil/timerClass/" + timeName;
					timePath=timeName;
					int p1 = fileName.lastIndexOf("\\");
					int p2 = fileName.lastIndexOf("/");
					if (p1 >= p2 && p1 >= 0) {
						fileName = fileName.substring(p1 + 1);
					}
					if (p2 >= p1 && p2 >= 0) {
						fileName = fileName.substring(p2 + 1);
					}		
				}
//				}else{
////					String name=item.getFieldName();					
//				}
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
