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
	
	/*ɾ���ڵ�
	 * ˼·��Ҫ��ΪҶ�ӽڵ㣬Ŀ¼�ڵ㣬�͸���㣨������Ѿ���ǰ̨�ж��ˣ�,����ǧ��Ҫ�����޸�״̬Ϊ
	 * ΪҶ�ӽڵ��ʱ������Ҫ�ж��丸�ڵ����ж��ٸ��ӽڵ�
	 * ������Ҷ�ӽڵ��ʱ��ȫ��ɾ�������档���⻹Ҫע�Ȿ��ڵ����������
	 * */
	public void ajaxDeletePageModelPanel(String id , String moduleId) {
	
			List<PageModelPanel> panel = null;
			PageModelPanel pageModelPanel = templateService.findPageModelPanelById(id);
			/*�ж�һ�¸��ڵ��ǲ���Ϊnull
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
			}else{//���ܴ���bug��û������
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
	
	/*�ƶ��ڵ�
	 * ˼·��Ҫ��Ϊ��ͬһ���ڵ��£���ͬ���ڵ�
	 * */
	public void ajaxMovePageModelPanel(String moderName,String id, String oldParentId, String newParentId, String nodeIndex) {
		String modelId = templateService.findPageModelByName(moderName);
		templateService.savePageModelPanelMove(modelId, id, oldParentId, newParentId, nodeIndex);
	}	
	
	/*���ڵ��Ƿ����
	 * ˼·��Ҫ���ݴ����pageModelId���Ҷ�Ӧ�����е�PagePanel,�ڸ���id�ж�
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
	
	/**�ж�һ��ʼѡ����ǲ�����pagePanel
	 * ˼·���ǰ�pageModelPanel������û������������pageModel��pagePanel
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
	 * ����spring����ķ���service
	 * 
	 * @param name
	 * @return
	 */
	protected static Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if (serviceBean == null) {
			throw new ServiceException("û������Ϊ��" + name + "�ķ��񣡣�");
		}
		return serviceBean;
	}

}
