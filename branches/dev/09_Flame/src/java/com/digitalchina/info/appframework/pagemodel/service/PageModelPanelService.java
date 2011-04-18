package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;

import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelBtn;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelType;

public interface PageModelPanelService {
	
	/**
	 * ͨ��PagePanel��ȡ���µ�����pagePanel
	 * @Methods Name findPagePanelByPageModel
	 * @Create In 2008-11-21 By sa
	 * @return List ����ֵ�е�����������PagePanel
	 */
	List<PageModelPanel> findPagePanelByNoParent(PageModel pageModel);
	/**
	 * ����PageModelPanel
	 * @Methods Name savePageModelPanel
	 * @Create In 2008-11-23 By sa
	 * @param panel
	 * @return PageModelPanel
	 */
	PageModelPanel savePageModelPanel(PageModelPanel panel);
	/**
	 * ����PageModelPanel���ƶ�
	 * @Methods Name savePageModelPanelMove
	 * @Create In 2008-11-23 By sa
	 * @param model ��ǰ������PageModel���
	 * @param panelId ��ǰ�ƶ���panel���
	 * @param oldParentId ��ǰ�ƶ���panel�ĸ��ڵ�
	 * @param newParentId �ƶ�����Ŀ�길�ڵ�
	 * @param nodeIndex ��ǰ�ƶ���panel�ڵ�������
	 * void
	 */
	void savePageModelPanelMove(String model, String panelId, String oldParentId, 
						String newParentId, String nodeIndex);
	
	/**
	 * ���ƽڵ�
	 * @Methods Name downNode
	 * @Create In 2008-11-23 By sa
	 * @param parentPanelId
	 * @param minIndex
	 * @param maxIndex void
	 */
	void downNode(Long pageModelId, Long parentPanelId, Integer minIndex, Integer maxIndex);
	
	/**
	 * ���ƽڵ�
	 * @Methods Name upNode
	 * @Create In 2008-11-23 By sa
	 * @param parentId
	 * @param minIndex
	 * @param maxIndex void
	 */
	void upNode(Long pageModelId, Long parentId, Integer minIndex, Integer maxIndex);
	
	/*����id��������Ӧ�ĵ�ʵ����Ϣ
	 * */
	public PagePanel findTemplateItemById(String id) ;
	
	/*����id��������Ӧ��������pageMode��Ϣ
	 * */
	public PageModel findPageModelById(String id) ;
	
	/*����id��������Ӧ��������pageModePanel��Ϣ
	 * */
	public PageModelPanel findPageModelPanelById(String id) ;
	
	/*����id��������Ӧ��������pagePanel��Ϣ
	 * */
	public PagePanel findPagePanelById(String id);
	
	/*����ģ��id ��pagePanel��id��ȷ��Ψһ��pageModelPanel
	 * */
	public List<PageModelPanel> findPageModelPanelByDoubleId(String pagePanelId);
	
	/*ɾ��Ҷ�ӽڵ�
	 * */
	public void removeLeafPageModelPanel(PageModelPanel pageModelPanel , int nodeIndex); 
	
	/*ɾ����Ҷ�ӽڵ�
	 * */
	public void removePageModelPanel(PageModelPanel pageModelPanel);
	
	/*�õ�pageModelPanel���ӽڵ�
	 * */
	public List<PageModelPanel> getChildPanels(PageModelPanel pmp);
	
	/*�õ�pagePanelType���ӽڵ�
	 * */
	public PagePanelType findPagePanelTypeById(String id);
	
	/*����pagModel��������ȷ��Ψһ��һ��pageModel
	 * */
	public String findPageModelByName(String name);
	
	/*����pagModel��������������Ӧ���������� pagePanel
	 * */
	public List<PageModelPanel> findPageModelPanelByPageModel(PageModel pageModel);
	
	/*���ݸ��ڵ��pageModelId�������ӽڵ����Ŀ
	 * */
	public int searchChildAmountByParentIdAndPageModelId(String parentId, String PageModelId);
	
	public void removeRootNode(PageModelPanel pageModelPanel);
	
	public void changeOrderByDelete(String ordParentId , int minIndex ,int nodeIndex ,String pageModelId);
	
	/**
	 * ����pageModel���ҵ���Ӧ��һ����ť
	 */
	public List<PageModelBtn> findPageModelBtnByPageModel(PageModel pageModel);
	
	public String findPageModelByRealName(String pageModelName);
	/**
	 * 
	 * @Methods Name findPageModelPanelByPageModelAndPagePanel
	 * @Create In Dec 17, 2008 By Administrator
	 * @param pageModelId
	 * @param pagePanelId
	 * @return boolean
	 */
	public boolean findPageModelPanelByPageModelAndPagePanel(PageModel pageModel ,PagePanel pagePanel);
}
