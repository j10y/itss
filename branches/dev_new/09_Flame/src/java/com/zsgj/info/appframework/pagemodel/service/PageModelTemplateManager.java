package com.zsgj.info.appframework.pagemodel.service;

import java.util.List;

import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.appframework.pagemodel.entity.PageModelPanel;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;

public class PageModelTemplateManager {
	
	private PageModelPanelService templateService = (PageModelPanelService) getBean("pageModelPanelService");
	
	
	public void ajaxSavePageModelPanel(String id, String parentId, String name,
			 String pagePanelId, String pageModelId ,String order) {
	
		PageModelPanel obj = null;
		if (null != id && !"".equals(id)) {
			obj = templateService.findPageModelPanelById(id);
						
		} else {
			obj = new PageModelPanel();
			PagePanel parentPanel = null;
			if ("".equals(parentId) || "-100".equals(parentId)) {
				obj.setParentPagePanel(null);
			} else {
				parentPanel = templateService.findTemplateItemById(parentId);
				obj.setParentPagePanel(parentPanel);
			}

			if (!"".equals(pageModelId) && pageModelId != null) {
				PagePanel pagePanel = templateService.findPagePanelById(pagePanelId);				
				PageModel pageModel = templateService.findPageModelById(pageModelId);
				obj.setPageModel(pageModel);
				obj.setPagePanel(pagePanel);
				obj.setIsDisplay(1);
				obj.setTitleDisplayFlag(1);
				obj.setReadonly(0);
			} else {
				obj.setPageModel(null);
			}
		}
		Integer ord = Integer.parseInt(order);
		obj.setOrder(ord);
		templateService.savePageModelPanel(obj);

	}
	
	/*删除节点
	 * 思路：要分为叶子节点，目录节点，和根结点（根结点已经在前台判断了）,另外千万不要忘记修改状态为
	 * 为叶子节点的时候，首先要判断其父节点下有多少个子节点
	 * 当不是叶子节点的时候，全部删除其下面。另外还要注意本身节点的排序问题
	 * */
	public void ajaxDeletePageModelPanel(String id , String moduleId) {
	
			List<PageModelPanel> panel = null;
			PageModelPanel pageModelPanel = templateService.findPageModelPanelById(id);
			/*判断一下父节点是不是为null
			 * */
			if(pageModelPanel.getParentPagePanel()!=null){
				String parentId = pageModelPanel.getParentPagePanel().getId()+"";
				int nodeIndex = templateService.searchChildAmountByParentIdAndPageModelId(parentId,moduleId)-1;
				if(templateService.getChildPanels(pageModelPanel).size()==0){
					templateService.removeLeafPageModelPanel(pageModelPanel,nodeIndex);
				}else{
					templateService.changeOrderByDelete(parentId+"", pageModelPanel.getOrder(), nodeIndex, moduleId);
					templateService.removeRootNode(pageModelPanel);
					panel = templateService.getChildPanels(pageModelPanel);
					for(int i=0;i<panel.size();i++){
						PageModelPanel childPanel = panel.get(i);
						templateService.removeRootNode(childPanel);
					}
				}
			}else{//可能存在bug，没有排序
				int nodeIndex = templateService.searchChildAmountByParentIdAndPageModelId("-100", moduleId);
				if(templateService.getChildPanels(pageModelPanel).size()==0){
					templateService.removeLeafPageModelPanel(pageModelPanel,nodeIndex);
				}else{					
					templateService.changeOrderByDelete("-100", pageModelPanel.getOrder(), nodeIndex, moduleId);
					templateService.removeRootNode(pageModelPanel);
					panel = templateService.getChildPanels(pageModelPanel);
					for(int i=0;i<panel.size();i++){
						PageModelPanel childPanel = panel.get(i);
						templateService.removeRootNode(childPanel);
					}
				}
			}
			
			
		
		
	}
	
	/*移动节点
	 * 思路：要分为在同一父节点下，不同父节点
	 * */
	public void ajaxMovePageModelPanel(String moderName,String id, String oldParentId, String newParentId, String nodeIndex) {
		String modelId = templateService.findPageModelByName(moderName);
		templateService.savePageModelPanelMove(modelId, id, oldParentId, newParentId, nodeIndex);
	}	
	
	/*检测节点是否存在
	 * 思路：要根据传入的pageModelId来找对应的所有的PagePanel,在根据id判断
	 * */
	public boolean ajaxTestRepeatePanel(String parentId,String pagePanelId,String pageModelId) {
		List<PageModelPanel> list =null;
		try{
			PageModel pageModel = (PageModel)templateService.findPageModelById(pageModelId);
			list = templateService.findPageModelPanelByPageModel(pageModel);
			for(PageModelPanel pmp : list){
				if(pagePanelId.equals(pmp.getPagePanel().getId()+"")){
					return false;
				}			
			}		
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	
	/**判断一开始选择的是不是主pagePanel
	 * 思路就是把pageModelPanel当中有没有满足条件的pageModel和pagePanel
	 * @Methods Name ajaxTestMainPanel
	 * @Create In Dec 16, 2008 By Administrator
	 * @return boolean
	 */
	public boolean ajaxTestMainPanel(String pagePanelId,String pageModelId) {		
		
		PageModel pageModel = (PageModel)templateService.findPageModelById(pageModelId);
		PagePanel pagePanel = (PagePanel)templateService.findPagePanelById(pagePanelId);
		if(pageModel.getMainPagePanel().getId().equals(pagePanel.getId())){
			return true;
		}else{
			return false;
		}
//		boolean bool = templateService.findPageModelPanelByPageModelAndPagePanel(pageModel, pageModel.getMainPagePanel());
//		if(bool){
//			return true;
//		}else{
//			Long fid = pageModel.getMainPagePanel().getId();
//			Long sid = pagePanel.getId();
//			if(fid.equals(sid)){
//				return true;
//			}			
//			return false;
//		}		
	}	
	
	
	/**
	 * 返回spring管理的服务service
	 * 
	 * @param name
	 * @return
	 */
	protected static Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if (serviceBean == null) {
			throw new ServiceException("没有名字为：" + name + "的服务！！");
		}
		return serviceBean;
	}

}
