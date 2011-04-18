package com.digitalchina.info.framework.workflow.web.adapter.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelNode;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.util.BeanUtil;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;

public class PageModelNodeAction extends BaseDispatchAction{
	private Service bs = getService();
	/**
	 * 显示所有资源
	 * @Methods Name list
	 * @Create In 2008-3-14 By peixf
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws Exception ActionForward
	 */
	public ActionForward listPageModeNodes(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
	
		List pageModelNodes = bs.findAll(PageModelNode.class);
		request.setAttribute("pageModelNodes", pageModelNodes);
		
		return mapping.findForward("listPageModeNodes");
	}
	
	/**
	 * 修改指定资源
	 * @Methods Name toEdit
	 * @Create In 2008-3-14 By peixf
	 * @param map
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward toEditPageModeNode(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List pageModelNodes = bs.findAll(PageModelNode.class);
		request.setAttribute("pageModelNodes", pageModelNodes);
		String pageModeId = request.getParameter("id");
		PageModelNode pageModelNode=(PageModelNode) bs.find(PageModelNode.class, pageModeId);
		request.setAttribute("pageModelNode", pageModelNode);
		List pageModels= bs.findAll(PageModel.class);
		request.setAttribute("pageModels", pageModels);
		
		return mapping.findForward("listPageModeNodes"); //本页快速修改
	}
	
	/**
	 * 去添加一个资源
	 * @Methods Name toAddResource
	 * @Create In 2008-3-14 By peixf
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward toAddPageModeNode(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		List pageModelNodes = bs.findAll(PageModelNode.class);
		request.setAttribute("pageModelNodes", pageModelNodes);
		List pageModels= bs.findAll(PageModel.class);
		request.setAttribute("pageModels", pageModels);
		
		return mapping.findForward("listPageModeNodes"); //本页快速修改
	}
	
	/**
	 * 保存资源
	 * @Methods Name saveResource
	 * @Create In 2008-3-14 By peixf
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception ActionForward
	 */
	public ActionForward savePageModeNode(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		PageModelNode pageModelNode = (PageModelNode) BeanUtil.getObject(request, PageModelNode.class);
		bs.save(pageModelNode);
		
		List pageModelNodes = bs.findAll(PageModelNode.class);
		request.setAttribute("pageModelNodes", pageModelNodes);
	
		return HttpUtil.redirect("/admin/pageModelNode.do?methodCall=listPageModeNodes");
	
	}
	
	public ActionForward removePageModeNode(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {

		String pageModeId = request.getParameter("id");
		bs.remove(PageModelNode.class, pageModeId);
		
		List pageModelNodes = bs.findAll(PageModelNode.class);
		request.setAttribute("pageModelNodes", pageModelNodes);
		
		return HttpUtil.redirect("/admin/pageModelNode.do?methodCall=listPageModeNodes");
	}

}

