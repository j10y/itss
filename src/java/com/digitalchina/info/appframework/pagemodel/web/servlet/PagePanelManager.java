package com.digitalchina.info.appframework.pagemodel.web.servlet;

import java.util.List;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
//import com.digitalchina.info.appframework.metadata.entity.SystemMainTableExtColumn;
import com.digitalchina.info.appframework.metadata.service.SystemColumnService;
//import com.digitalchina.info.appframework.metadata.service.SystemExtColumnServcie;
import com.digitalchina.info.appframework.metadata.service.SystemMainColumnService;
import com.digitalchina.info.appframework.metadata.service.SystemMainTableService;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelRelation;
import com.digitalchina.info.appframework.pagemodel.service.PagePanelColumnService;
import com.digitalchina.info.appframework.pagemodel.service.PagePanelRelationService;
import com.digitalchina.info.appframework.pagemodel.service.PagePanelService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;
/**
 * dwr��ʽ����pagePanel
 * @Class Name PagePanelManager
 * @author lee
 * @Create In 2008-11-24 
 * TODO
 */
public class PagePanelManager {
	private PagePanelService pps = (PagePanelService) getBean("pagePanelService");
	private PagePanelColumnService ppcs = (PagePanelColumnService) getBean("pagePanelColumnService");
	private SystemMainTableService smts = (SystemMainTableService) getBean("systemMainTableService");
	private SystemColumnService scs=(SystemColumnService) getBean("systemColumnService");
	private SystemMainColumnService smcs = (SystemMainColumnService) getBean("systemMainColumnService");	
	private PagePanelRelationService pprs=(PagePanelRelationService)getBean("pagePanelRelationService");
	/**
	 * 
	 * Sep 11, 2008 By hp
	 * @param id  ���Id
	 * @param parentId  ���ڵ�ID
	 * @param smtId    ϵͳ����ID
	 * @param smtItemId  ϵͳ������һ�����ݵ�ID
	 * @param panelId  ģ��ID
	 * @param flag  ��־����������ϵͳ��������չ��mainTableColumn,extendColumn��
	 * @param order  �����ֵ��Ⱥ����
	 */
	public void ajaxSavePanelColumn(String id,String parentId, String smtId,String stcId,String pagePanelId,String isMainColumn,String order){
		
		PagePanelColumn obj = null;
		//�Ƿ�PagePanelColumn�Ѵ���
		//System.out.print(id+"||"+parentId+"||"+smtId+"||"+stcId+"||"+pagePanelId+"||"+order);
		if(null != id && !"".equals(id)){
			obj = ppcs.findPagePaneColumnlById(id);
		}else{
			obj = new PagePanelColumn();
			PagePanelColumn parentPagePanelColumn = null;
			if("".equals(parentId) || "0".equals(parentId)){
				obj.setParentPagePanelColumn(null);
			}else{
				parentPagePanelColumn = ppcs.findPagePaneColumnlById(parentId);
				obj.setParentPagePanelColumn(parentPagePanelColumn);
			}
			
			if(!"".equals(pagePanelId) && pagePanelId != null){
				PagePanel pagePanel = pps.findPagePanelById(pagePanelId);
				obj.setPagePanel(pagePanel);
			}else{
				obj.setPagePanel(null);
			}
		}
		SystemMainTable smt = smts.findSystemMainTable(smtId);
		obj.setSystemMainTable(smt);
		if(isMainColumn.equals("true")){
			SystemMainTableColumn mainTableColumn =smcs.findSystemMainTableColumnById(stcId);
				obj.setMainTableColumn(mainTableColumn);
//		if(isMainColumn.equals("false")){  //��չ�ֶ�
//			SystemMainTableExtColumn extendTableColumn =smts.findSystemMainTableExtColumnByColumnId(smtId);
//				obj.setExtendTableColumn(extendTableColumn);
//			}
		}
		obj.setIsDisplay(1);
		obj.setOrder(new Integer(order));
		ppcs.savePagePanelColumn(obj);
	}
	public void ajaxRemoveNode(String ppcId){
		ppcs.removePanelColumn(ppcId);
	}
	public void ajaxIsDisplayNode(String nodeId,String isDisplay){
		ppcs.saveColumnIsDisplay(nodeId,isDisplay);
	}
	public void ajaxSaveColumns(String ppId,String smtId){
		pps.savePanelColumnsFormSysMainTable(ppId,smtId);
	}
	public void ajaxMoveNode(String id, String oldParentId, String newParentId, String nodeIndex){
		ppcs.savePagePanelColumnMove(id, oldParentId, newParentId, nodeIndex);
	}
	
	
	public void ajaxSavePagePanel(String id, String parentId, String name,
			 String pagePanelId, String groupPagePanelId ,String order) {
		
		PagePanelRelation obj = new PagePanelRelation();
		PagePanel parentPanel = null;		
		if(parentId.equals("-100")){
			if (!"".equals(groupPagePanelId) && groupPagePanelId != null) {
				
					//�������ȱ��汾ҳ�����
					parentPanel = pps.findPagePanelById(groupPagePanelId);		
					obj.setParentPagePanel(parentPanel);
					//��������id 
					PagePanel pagePanel = pps.findPagePanelById(pagePanelId);	
					obj.setPagePanel(pagePanel);
					obj.setIsDisplay(1);
					obj.setTitleDisplayFlag(1);
					obj.setReadonly(1);					
			} else {
				obj.setParentPagePanel(null);
			}		
			Integer ord = Integer.parseInt(order);
			obj.setOrder(ord);
			pprs.savePagePanelRelation(obj);
			}	
	}
	/**���ڵ��Ƿ����
	 * ˼·��Ҫ���ݴ����pageModelId���Ҷ�Ӧ�����е�PagePanel,�ڸ���id�ж�
	 * @Methods Name ajaxTestRepeatePanel
	 * @Create In Dec 22, 2008 By Administrator
	 * @param parentId
	 * @param pagePanelId
	 * @param pageModelId
	 * @return boolean
	 */	
	public boolean ajaxTestRepeatePanel(String parentId,String pagePanelId) {
		List<PagePanelRelation> list =null;
		try{	
			PagePanel parentPagePanel = (PagePanel)pps.findPagePanelById(parentId);			
			list = pprs.findPagePanelRelationByParentPagePanel(parentPagePanel);
			if(list != null){
				
				for(PagePanelRelation ppr : list){
					if(pagePanelId.equals(String.valueOf(ppr.getPagePanel().getId()))){
						return false;
					}			
				}	
			}				
		}catch(Exception e){
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * ɾ����Ӧ�Ĺ�ϵ
	 * @Methods Name ajaxDeletePagePanelRelation
	 * @Create In Dec 23, 2008 By Administrator
	 * @param deleteRelationId
	 * @param currentPagePanelId void
	 */
	public void ajaxDeletePagePanelRelation(String deleteRelationId, String currentPagePanelId){
		
		PagePanelRelation pagePanelRelation = pprs.findPagePanelRelationById(deleteRelationId);
		List list = pprs.findPagePanelRelationByParentPagePanel(pagePanelRelation.getParentPagePanel());
		pprs.changeOrderByDelete(deleteRelationId, pagePanelRelation.getOrder(), list.size()-1);
		pprs.deletePagePanelRelation(pagePanelRelation);		
		
	}
	/**
	 * �ڵ��ƶ���ʱ��������¼�
	 * @Methods Name ajaxMovePagePanel
	 * @Create In Dec 23, 2008 By Administrator void
	 */
	public void ajaxMovePagePanel(String panelName,String relationId, String oldParentId, String newParentId, String nodeIndex){
		
		//String currentPanelId = ((PagePanel)pps.findPagePanelByPanelName(panelName)).getId()+"";
		PagePanelRelation panelRelation = pprs.findPagePanelRelationById(relationId);
		PagePanel pagePanel = panelRelation.getPagePanel();//�϶����Ǹ����
		PagePanel currentPanel = pps.findPagePanelByPanelName(panelName);
		pprs.savePagePanelMove(currentPanel, pagePanel ,oldParentId, newParentId, nodeIndex);		
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
