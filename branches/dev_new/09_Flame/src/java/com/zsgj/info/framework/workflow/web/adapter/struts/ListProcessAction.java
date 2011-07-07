package com.zsgj.info.framework.workflow.web.adapter.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.sap.mw.jco.JCO.Response;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.workflow.DefinitionService;
import com.zsgj.info.framework.workflow.info.ProcessInfo;

public class ListProcessAction extends BaseDispatchAction {
	DefinitionService ds = (DefinitionService)ContextHolder.getBean("definitionService");
	/**
	 * 2010-05-12 abate by 光顺安 for 具体失效缘由
	 * 删除该方法
	 * 用新的方法替代
	 * public ActionForward listAllActiveProcessInstance
	 */
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		java.util.List<ProcessInfo> list = ds.getAllActiveProcess();
		request.setAttribute("processes", list);
		request.setAttribute("length", list==null?0:list.size());
		return mapping.findForward("success");
	}
	/**
	 * 新建的查询所有活动流程
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ActionForward listAllActiveProcessInstance(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		java.util.List<ProcessInfo> list = ds.getAllActiveProcessInstance();
		request.setAttribute("processes", list);
		request.setAttribute("length", list==null?0:list.size());
		return mapping.findForward("success");
	}
	
	/**
	 * 查询所有活动的流程实例
	 * EXT版本
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ActionForward findAllActiveProcessInstance(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		StringBuffer json = new StringBuffer();
		
		try {
			java.util.List<ProcessInfo> list = ds.getAllActiveProcessInstance();
 
			for(int i=0; i < list.size(); i++){
				json.append("{");
					json.append("id:");
					json.append("'" + list.get(i).getId() + "'");
					json.append(",");

					json.append("virtualDefinitionDesc:");
					json.append("'" + list.get(i).getVirtualDefinitionDesc() + "'");
					json.append(",");
					
					json.append("definitionName:");
					json.append("'" + list.get(i).getDefinitionName() + "'");
					json.append(",");
					
					json.append("defVersion:");
					json.append("'" + list.get(i).getDefVersion() + "'");
					json.append(",");					
					
					json.append("start:");
					json.append("'" + list.get(i).getStart() + "'");
				if(i<list.size()-1){
					json.append("},");
				}else{
					json.append("}");
					
				}
			}
			json = new StringBuffer("{success: true, rowCount:" + list.size() + ",data:[" + json.toString() + "]}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = new StringBuffer("{success:false,msg:'服务器端异常'}");
		}
		httpServletResponse.setContentType("text/html;charset=gbk");
		httpServletResponse.getWriter().write(json.toString());
		httpServletResponse.getWriter().close();
		return null;
	}
}
