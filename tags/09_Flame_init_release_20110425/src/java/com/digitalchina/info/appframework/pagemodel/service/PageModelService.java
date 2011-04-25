package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelBtn;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanelMiddleTable;
import com.digitalchina.info.appframework.pagemodel.entity.PageModelPanelTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Module;

public interface PageModelService{
	/**
	 * 通过id获取页面模版
	 * @Methods Name findPageModelById
	 * @Create In Jul 1, 2009 By lee
	 * @param id
	 * @return PageModel
	 */
	PageModel findPageModelById(String id);
	/**
	 * 获取页面路径by工作流节点
	 * @Methods Name findPageModelByNode
	 * @Create In 2009-1-5 By sa
	 * @param node
	 * @return PageModel
	 */
	PageModel findPageModelByNode(String node);
	/**
	 * 获取指定页面路径的页面模型个数
	 * @Methods Name findPageModelCountByPagePath
	 * @Create In 2008-12-30 By sa
	 * @param pagePath
	 * @return Long
	 */
	boolean existPageModelCountByPagePath(String pagePath);
	
	/**
	 * 获取PageModel，主动抓取其所有panel，即初始化其pagePanels集合
	 * @Methods Name findPageModelWithPanels
	 * @Create In 2008-11-16 By sa
	 * @param pageModelId
	 * @return PageModel
	 */
	PageModel findPageModelWithPanels(String pageModelId);
	
	/**
	 * 通过pageModel的关键字获取pageModel
	 * @Methods Name findPageModel
	 * @Create In 2008-12-5 By sa
	 * @param keyName
	 * @return PageModel
	 */
	PageModel findPageModel(String keyName);
	
	/**
	 * 通过pageModel的关键字获取pageModel
	 * @Methods Name findPageModel
	 * @Create In 2008-12-19 By sa
	 * @param keyName
	 * @return PageModel
	 */
	 PageModel findPageModel$$$$$(String keyName);
	/**
	 * 保存PageModel
	 * @Methods Name savePageModel
	 * @Create In 2008-11-16 By sa
	 * @param pageModel
	 * @return PageModel
	 */
	PageModel savePageModel(PageModel pageModel);
	
	/**
	 * 删除PagePanel
	 * @Methods Name removePagePanel
	 * @Create In 2008-11-21 By sa
	 * @param pagePanelId void
	 */
	void removePageModel(String[] pagePanelId);
	
	/**
	 * 显示PageModel列表，搜索条件module和pageModel名称或关键字
	 * @Methods Name findPageModel
	 * @Create In 2008-11-16 By sa
	 * @param module
	 * @param pageName
	 * @param pageNo
	 * @param pageSize
	 * @return Page 返回page里的集合数据是PageModel类型
	 */
	Page findPageModel(Module module, String pageName, int pageNo, int pageSize);
	
	/**
	 * 显示PageModel列表，搜索条件module和pageModel名称或关键字
	 * @Methods Name findPageModel
	 * @Create In 2008-11-23 By sa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findPageModel(Map params, int pageNo, int pageSize);

	/**根据pageModelName来查找相应的pageModel
	 * 
	 */
	PageModel findPageModelByPageModelName(String pageModelName);
	
	/**
	 * 根据修改的按钮id来查找相应的按钮记录
	 */
	PageModelBtn findPageModelBtnByModifyId(String id);
	/**
	 * 在保存pageModel的同时也要相应保存pagemModelBtn,根据settingType来判断什么样的按钮应该被相应
	 * 的保存。
	 */
	void savePageModelBtn(PageModel pageModel);
	
	/**
	 * 获取pageModel的所有PageModelPanelTable信息
	 * @Methods Name findPageModelPanelTableByModel
	 * @Create In 2008-12-5 By sa
	 * @param pageModel
	 * @return List<PageModelPanelTable>
	 */
	List<PageModelPanelTable> findPageModelPanelTableByModel(PageModel pageModel);
	
	/**
	 * 通过获取PageModelPanelTable
	 * @Methods Name findPageModelPanelTableByModel
	 * @Create In 2008-12-7 By sa
	 * @param pageModel
	 * @return List<PageModelPanelTable>
	 */
	List<PageModelPanelTable> findPageModelPanelTable(PageModel pageModel, PagePanel pagePanel);
	
	/**
	 * 通过子panel和子panel主表，获取PageModelPanelTable
	 * @Methods Name findPageModelPanelTableByModel
	 * @Create In 2008-12-7 By sa
	 * @param pageModel
	 * @return List<PageModelPanelTable>
	 */
	List<PageModelPanelTable> findPageModelPanelTableBySub(PageModel pageModel, PagePanel subPagePanel, SystemMainTable subTable);
	
	/**
	 * 通过页面模型，子面板和子面板主表获取
	 * @Methods Name findPageModelPanelMiddleTableBySub
	 * @Create In 2009-3-11 By sa
	 * @param pageModel
	 * @param subPagePanel
	 * @param subTable
	 * @return List<PageModelPanelTable>
	 */
	List<PageModelPanelMiddleTable> findPageModelPanelMiddleTableBySub(PageModel pageModel, 
				PagePanel subPagePanel, SystemMainTable subTable);
	
	
	/**
	 * 通过父panel和父panel主表，获取所有引用此panel的子panel和主表
	 * @Methods Name findPageModelPanelTableByParent
	 * @Create In 2008-12-8 By sa
	 * @param pageModel
	 * @param parentPagePanel
	 * @param parentTable
	 * @return List<PageModelPanelTable>
	 */
	List<PageModelPanelTable> findPageModelPanelTableByParent(PageModel pageModel, PagePanel parentPagePanel, SystemMainTable parentTable);
	
	/**
	 * 通过pageModel获取model中的按钮
	 * @Methods Name findPageModelBtnByModel
	 * @Create In 2008-12-16 By lee
	 * @param pageModel
	 * @return List<PageModelBtn>
	 */
	List<PageModelBtn> findPageModelBtnByModel(PageModel pageModel);
	
	/**
	 * 通过选择的模块来得到相应的系统主表
	 * @Methods Name findSystemMainTable
	 * @Create In Dec 17, 2008 By Administrator
	 * @param module
	 * @return List<SystemMainTable>
	 */
	List<SystemMainTable> findSystemMainTable(Module module);
	void removePageModelPanelTable(String id);
	List findAllPagePanel();
	PagePanel findPagePanelById(String id);
	List findAllMainTableByPanel(PagePanel pagePanel);
	SystemMainTable findSystemMainTable(String tableId);
	List findAllSystemMainTableColumnByName(String tableName);
	//SystemMainTableColumn findSystemMainTablePrimaryKeyColumn(SystemMainTableColumn systemMainTableColumn);
	PageModelPanelTable savePageModelPanelTable(PageModelPanelTable pageModelPanelTable);
	PageModelPanelTable findPageModelPanelTable(String id);
}
