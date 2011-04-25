package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;

import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelRelation;

public interface PagePanelRelationService {

	/**
	 * ��ȡ��ǰ�ڵ�������ӽڵ�
	 * @Methods Name findPagePanelRelation
	 * @Create In Dec 18, 2008 By Administrator
	 * @param parent
	 * @return List<PagePanelRelation>
	 */
	List<PagePanelRelation> findPagePanelRelation(PagePanelRelation parent);
	/**
	 * ��ȡ����ģ���������ģ�壬ע��Ҫ����
	 * @Methods Name findPagePanelByNoParent
	 * @Create In 2008-12-18 By sa
	 * @param parentPagePanel
	 * @return List<PagePanel>
	 */
	List<PagePanel> findPagePanelByParent(PagePanel parentPagePanel);
	/**
	 * ����PagePanelRelation,
	 * ��һ������ģ���Ϸŵ�����ģ�����Ҫ������Ϣ��PagePanelRelation
	 * @Methods Name savePageModelPanel
	 * @Create In 2008-11-23 By sa
	 * @param panel
	 * @return PageModelPanel
	 */
	PagePanelRelation savePagePanelRelation(PagePanelRelation ppr);
	/**
	 * ����PagePanel���ƶ�, �ײ����PagePanelRelation���޸�parentPagePanel��order
	 * @Methods Name savePagePanelMove
	 * @Create In 2008-11-23 By sa
	 * @param panelId ��ǰ�ƶ�����panel���
	 * @param oldParentId ��ǰ�ƶ���panel�ĸ��ڵ�
	 * @param newParentId �ƶ�����Ŀ�길�ڵ�
	 * @param nodeIndex ��ǰ�ƶ���panel�ڵ�������
	 * void
	 */
	void savePagePanelMove(PagePanel currentPanel, PagePanel pagePanel,String oldParentId, 
						String newParentId, String nodeIndex);
	
	/**
	 * ���ƽڵ�
	 * @Methods Name downNode
	 * @Create In 2008-11-23 By sa
	 * @param parentPanelId
	 * @param minIndex
	 * @param maxIndex void
	 */
	
	public void downNode(Long currentPanelRelationId, Integer minIndex,Integer maxIndex); 
	/**
	 * ���ƽڵ�
	 * @Methods Name upNode
	 * @Create In 2008-11-23 By sa
	 * @param parentId
	 * @param minIndex
	 * @param maxIndex void
	 */
	
	
	public void upNode(Long currentPanelRelationId, Integer minIndex,Integer maxIndex);
	/**����id��������Ӧ�ĵ�ʵ����Ϣ
	 * */
	//public PagePanel findTemplateItemById(String id) ;
	
	/**����id��������Ӧ��������pageMode��Ϣ
	 * */
	public PagePanelRelation findPagePanelRelationById(String id) ;

	
	/**ɾ��Ҷ�ӽڵ�
	 * */
	public void removeLeafPagePanelRelation(PagePanelRelation PagePanelRelation, int nodeIndex, String pagePanelId); 

	/**
	 * ɾ����Ҷ�ӽڵ�
	 * @Methods Name removeRootNode
	 * @Create In Dec 19, 2008 By Administrator
	 * @param pagePanelRelation void
	 */
	public void removePanelRelation(PagePanelRelation PagePanelRelation);
	public void removePagePanelRelation(String[] pagePanelIds);
	
	public void changeOrderByDelete(String relationId , int minIndex ,int nodeIndex);
	/**
	 * ɾ�������
	 * @Methods Name removeRootNode
	 * @Create In Dec 19, 2008 By Administrator
	 * @param pagePanelRelation void
	 */
	public void removeRootNode(PagePanelRelation pagePanelRelation);
	
	/**
	 * ����Ψһ��PagePanelRelation
	 * @Methods Name findPagePanelRelation
	 * @Create In Dec 22, 2008 By Administrator
	 * @param pagePanel
	 * @param parentPagePanel
	 * @return PagePanelRelation
	 */
	public PagePanelRelation  findPagePanelRelation(PagePanel pagePanel ,PagePanel parentPagePanel);
	
	public void removePagePanelRelation(String pagePanelId);
	/**
	 * ͨ����ҳpanel��������Ӧ��pagePanelRelation
	 * @Methods Name findPagePanelRelationByParentPagePanel
	 * @Create In Dec 23, 2008 By Administrator
	 * @param parentPagePanel
	 * @return List
	 */
	public List findPagePanelRelationByParentPagePanel(PagePanel parentPagePanel);
	
	/**ɾ����Ӧ��ʵ��
	 * 
	 * @Methods Name deletePagePanelRelation
	 * @Create In Dec 23, 2008 By Administrator
	 * @param relation void
	 */
	public void deletePagePanelRelation(PagePanelRelation relation);
	/**
	 * ������Ĳ��ҷ���
	 * @Methods Name findPagePanelRelationByPageAddOrder
	 * @Create In Dec 23, 2008 By Administrator
	 * @param relation
	 * @return List
	 */
	public List findPagePanelRelationByPageAddOrder(PagePanel relation);
	/**
	 * �ҵ���Ӧ��pagePanelRelation������
	 * @Methods Name findPartPagePanelRelationByRelationObject
	 * @Create In Dec 28, 2008 By Administrator
	 * @param pagePanelRelation
	 * @return List
	 */
	public List findPartPagePanelRelationByRelationObject(PagePanelRelation pagePanelRelation);
	
}
