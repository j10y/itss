package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;

import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelRelation;

public interface PagePanelRelationService {

	/**
	 * 获取当前节点的所有子节点
	 * @Methods Name findPagePanelRelation
	 * @Create In Dec 18, 2008 By Administrator
	 * @param parent
	 * @return List<PagePanelRelation>
	 */
	List<PagePanelRelation> findPagePanelRelation(PagePanelRelation parent);
	/**
	 * 获取分组模板的所有子模板，注意要排序
	 * @Methods Name findPagePanelByNoParent
	 * @Create In 2008-12-18 By sa
	 * @param parentPagePanel
	 * @return List<PagePanel>
	 */
	List<PagePanel> findPagePanelByParent(PagePanel parentPagePanel);
	/**
	 * 保存PagePanelRelation,
	 * 即一个基本模板拖放到分组模板后，需要保存信息到PagePanelRelation
	 * @Methods Name savePageModelPanel
	 * @Create In 2008-11-23 By sa
	 * @param panel
	 * @return PageModelPanel
	 */
	PagePanelRelation savePagePanelRelation(PagePanelRelation ppr);
	/**
	 * 保存PagePanel的移动, 底层操作PagePanelRelation表，修改parentPagePanel或order
	 * @Methods Name savePagePanelMove
	 * @Create In 2008-11-23 By sa
	 * @param panelId 当前移动的子panel编号
	 * @param oldParentId 当前移动的panel的父节点
	 * @param newParentId 移动到的目标父节点
	 * @param nodeIndex 当前移动的panel节点的排序号
	 * void
	 */
	void savePagePanelMove(PagePanel currentPanel, PagePanel pagePanel,String oldParentId, 
						String newParentId, String nodeIndex);
	
	/**
	 * 上移节点
	 * @Methods Name downNode
	 * @Create In 2008-11-23 By sa
	 * @param parentPanelId
	 * @param minIndex
	 * @param maxIndex void
	 */
	
	public void downNode(Long currentPanelRelationId, Integer minIndex,Integer maxIndex); 
	/**
	 * 下移节点
	 * @Methods Name upNode
	 * @Create In 2008-11-23 By sa
	 * @param parentId
	 * @param minIndex
	 * @param maxIndex void
	 */
	
	
	public void upNode(Long currentPanelRelationId, Integer minIndex,Integer maxIndex);
	/**根据id来查找相应的的实体信息
	 * */
	//public PagePanel findTemplateItemById(String id) ;
	
	/**根据id来查找相应的所属的pageMode信息
	 * */
	public PagePanelRelation findPagePanelRelationById(String id) ;

	
	/**删除叶子节点
	 * */
	public void removeLeafPagePanelRelation(PagePanelRelation PagePanelRelation, int nodeIndex, String pagePanelId); 

	/**
	 * 删除非叶子节点
	 * @Methods Name removeRootNode
	 * @Create In Dec 19, 2008 By Administrator
	 * @param pagePanelRelation void
	 */
	public void removePanelRelation(PagePanelRelation PagePanelRelation);
	public void removePagePanelRelation(String[] pagePanelIds);
	
	public void changeOrderByDelete(String relationId , int minIndex ,int nodeIndex);
	/**
	 * 删除根结点
	 * @Methods Name removeRootNode
	 * @Create In Dec 19, 2008 By Administrator
	 * @param pagePanelRelation void
	 */
	public void removeRootNode(PagePanelRelation pagePanelRelation);
	
	/**
	 * 发现唯一的PagePanelRelation
	 * @Methods Name findPagePanelRelation
	 * @Create In Dec 22, 2008 By Administrator
	 * @param pagePanel
	 * @param parentPagePanel
	 * @return PagePanelRelation
	 */
	public PagePanelRelation  findPagePanelRelation(PagePanel pagePanel ,PagePanel parentPagePanel);
	
	public void removePagePanelRelation(String pagePanelId);
	/**
	 * 通过本页panel来查找相应的pagePanelRelation
	 * @Methods Name findPagePanelRelationByParentPagePanel
	 * @Create In Dec 23, 2008 By Administrator
	 * @param parentPagePanel
	 * @return List
	 */
	public List findPagePanelRelationByParentPagePanel(PagePanel parentPagePanel);
	
	/**删除相应的实体
	 * 
	 * @Methods Name deletePagePanelRelation
	 * @Create In Dec 23, 2008 By Administrator
	 * @param relation void
	 */
	public void deletePagePanelRelation(PagePanelRelation relation);
	/**
	 * 带排序的查找方法
	 * @Methods Name findPagePanelRelationByPageAddOrder
	 * @Create In Dec 23, 2008 By Administrator
	 * @param relation
	 * @return List
	 */
	public List findPagePanelRelationByPageAddOrder(PagePanel relation);
	/**
	 * 找到相应的pagePanelRelation的属性
	 * @Methods Name findPartPagePanelRelationByRelationObject
	 * @Create In Dec 28, 2008 By Administrator
	 * @param pagePanelRelation
	 * @return List
	 */
	public List findPartPagePanelRelationByRelationObject(PagePanelRelation pagePanelRelation);
	
}
