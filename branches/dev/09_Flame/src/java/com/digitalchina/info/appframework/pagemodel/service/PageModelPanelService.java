package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;

import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelBtn;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelType;

public interface PageModelPanelService {
	
	/**
	 * 通过PagePanel获取其下的所有pagePanel
	 * @Methods Name findPagePanelByPageModel
	 * @Create In 2008-11-21 By sa
	 * @return List 返回值中的数据类型是PagePanel
	 */
	List<PageModelPanel> findPagePanelByNoParent(PageModel pageModel);
	/**
	 * 保存PageModelPanel
	 * @Methods Name savePageModelPanel
	 * @Create In 2008-11-23 By sa
	 * @param panel
	 * @return PageModelPanel
	 */
	PageModelPanel savePageModelPanel(PageModelPanel panel);
	/**
	 * 保存PageModelPanel的移动
	 * @Methods Name savePageModelPanelMove
	 * @Create In 2008-11-23 By sa
	 * @param model 当前操作的PageModel编号
	 * @param panelId 当前移动的panel编号
	 * @param oldParentId 当前移动的panel的父节点
	 * @param newParentId 移动到的目标父节点
	 * @param nodeIndex 当前移动的panel节点的排序号
	 * void
	 */
	void savePageModelPanelMove(String model, String panelId, String oldParentId, 
						String newParentId, String nodeIndex);
	
	/**
	 * 上移节点
	 * @Methods Name downNode
	 * @Create In 2008-11-23 By sa
	 * @param parentPanelId
	 * @param minIndex
	 * @param maxIndex void
	 */
	void downNode(Long pageModelId, Long parentPanelId, Integer minIndex, Integer maxIndex);
	
	/**
	 * 下移节点
	 * @Methods Name upNode
	 * @Create In 2008-11-23 By sa
	 * @param parentId
	 * @param minIndex
	 * @param maxIndex void
	 */
	void upNode(Long pageModelId, Long parentId, Integer minIndex, Integer maxIndex);
	
	/*根据id来查找相应的的实体信息
	 * */
	public PagePanel findTemplateItemById(String id) ;
	
	/*根据id来查找相应的所属的pageMode信息
	 * */
	public PageModel findPageModelById(String id) ;
	
	/*根据id来查找相应的所属的pageModePanel信息
	 * */
	public PageModelPanel findPageModelPanelById(String id) ;
	
	/*根据id来查找相应的所属的pagePanel信息
	 * */
	public PagePanel findPagePanelById(String id);
	
	/*根据模板id 和pagePanel的id来确定唯一的pageModelPanel
	 * */
	public List<PageModelPanel> findPageModelPanelByDoubleId(String pagePanelId);
	
	/*删除叶子节点
	 * */
	public void removeLeafPageModelPanel(PageModelPanel pageModelPanel , int nodeIndex); 
	
	/*删除非叶子节点
	 * */
	public void removePageModelPanel(PageModelPanel pageModelPanel);
	
	/*得到pageModelPanel的子节点
	 * */
	public List<PageModelPanel> getChildPanels(PageModelPanel pmp);
	
	/*得到pagePanelType的子节点
	 * */
	public PagePanelType findPagePanelTypeById(String id);
	
	/*根据pagModel的名字来确定唯一的一个pageModel
	 * */
	public String findPageModelByName(String name);
	
	/*根据pagModel的名字来查找相应的其所属的 pagePanel
	 * */
	public List<PageModelPanel> findPageModelPanelByPageModel(PageModel pageModel);
	
	/*根据父节点和pageModelId来查找子节点的数目
	 * */
	public int searchChildAmountByParentIdAndPageModelId(String parentId, String PageModelId);
	
	public void removeRootNode(PageModelPanel pageModelPanel);
	
	public void changeOrderByDelete(String ordParentId , int minIndex ,int nodeIndex ,String pageModelId);
	
	/**
	 * 根据pageModel来找到相应的一串按钮
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
