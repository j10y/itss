package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.pagemodel.entity.PageGroupPanelTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTable;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Module;

/**
 * 分组类型的面板管理服务
 * @Class Name PageGroupPanelService
 * @Author sa
 * @Create In 2008-12-18
 */
public interface PageGroupPanelService {
	/**
	 * 查询PagePanel，防止pagePanel过多，故可以按照所属的模块或panel的名称进行查询
	 * @Methods Name findPagePanel
	 * @Create In 2008-11-21 By sa
	 * @param module
	 * @param pageName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findPagePanel(Module module, String nameOrTitle, int pageNo, int pageSize);
	
	/**
	 * 查询PagePanel，防止pagePanel过多，故可以按照所属的模块或panel的名称进行查询
	 * @Methods Name findPagePanel
	 * @Create In 2008-11-23 By sa
	 * @param params
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findPagePanel(Map params, int pageNo, int pageSize);
	
	/**
	 * 保存pagePanel
	 * @Methods Name savePagePanel
	 * @Create In 2008-11-21 By sa
	 * @param panel
	 * @return PagePanel
	 */
	PagePanel savePagePanel(PagePanel panel);
	
	/**
	 * 保存PagePanel的移动
	 * @Methods Name savePagePanelMove
	 * @Create In 2008-11-23 By sa
	 * @param panelId 当前移动的panel编号
	 * @param oldParentId 当前移动的panel的父节点
	 * @param newParentId 移动到的目标父节点
	 * @param nodeIndex 当前移动的panel节点的排序号
	 * void
	 */
	void savePagePanelMove(String panelId, String oldParentId, String newParentId, String nodeIndex);
	
	/**
	 * 删除PagePanel, 删除上级panel时要清空子panel对
	 * @Methods Name removePagePanel
	 * @Create In 2008-11-21 By sa
	 * @param pagePanelId void
	 */
	void removePagePanel(String pagePanelId);
	
	/**
	 * 删除PagePanel
	 * @Methods Name removePagePanel
	 * @Create In 2008-11-21 By sa
	 * @param pagePanelId void
	 */
	void removePagePanel(String[] pagePanelIds);
	
	/**
	 * 通过ID获取PagePanel
	 * @Methods Name findPagePanelById
	 * @Create In 2008-11-21 By sa
	 * @param pagePanelId
	 * @return PagePanel
	 */
	PagePanel findPagePanelById(String pagePanelId);
	
	/**
	 * 获取所有PagePanelTable
	 * @Methods Name findPagePanelTable
	 * @Create In 2008-12-12 By sa
	 * @param panel
	 * @return List<PagePanelTable>
	 */
	List<PagePanelTable> findPagePanelTable(PagePanel panel);

	/**
	 * 获取某个面板的中的所有主表
	 * @Methods Name findMainTableByPanel
	 * @Create In 2008-12-30 By peixf
	 * @param pagePanel
	 * @return List
	 */
	List findMainTableByPanel(PagePanel pagePanel);

	/**
	 * 获取某个主表的所有字段
	 * @Methods Name findColumns
	 * @Create In 2008-12-30 By peixf
	 * @param smt
	 * @param settingType 列表页面还是输入页面
	 * @return List<Column>
	 */
	List<Column> findColumns(SystemMainTable smt, Integer settingType);
	
	/**
	 * 获取一个分组面板中，子面板的所有父面板依赖信息，
	 * 前端的grid面板获取查询条件时使用此方法获取父面板的主键字段名称
	 * @Methods Name findGroupPanelTable
	 * @Create In 2008-12-29 By sa
	 * @param groupPanel 分组面板，如客户信息分组面板
	 * @param subPanel 子面板，如内部客户联系人面板
	 * @return List<PageGroupPanelTable>
	 */
	List<PageGroupPanelTable> findGroupPanelTableBySub(PagePanel groupPanel, PagePanel subPanel);
	
	/**
	 * 获取一个分组面板中，父面板的所有子面板，
	 * 当分组面板表单中的数据保存时，先保存主面板的数据，然后
	 * 取所有的子面板信息，分别保存其主面板数据
	 * @Methods Name findGroupPanelTableByParent
	 * @Create In 2009-1-1 By sa
	 * @param groupPanel
	 * @param subPanel
	 * @return List<PageGroupPanelTable>
	 */
	List<PageGroupPanelTable> findGroupPanelTableByParent(PagePanel groupPanel, PagePanel subPanel);
	
	/**
	 * 通过分组面板获取其下所有面板的关系PageGroupPanelTable
	 * @Methods Name findAllPageGroupPanelTableByGroupPanel
	 * @Create In 2008-12-30 By peixf
	 * @param groupPanel
	 * @return List<PageGroupPanelTable>
	 */
	List<PageGroupPanelTable> findAllPageGroupPanelTableByGroupPanel(PagePanel groupPanel);
	
	void removePageGroupPanelTable(String id);
	public List findAllPagePanel();
	public SystemMainTable findSystemMainTable(String tableId);
	public SystemMainTable findSystemMainTableByName(String tableName);
	public List findAllSystemMainTableColumnByName(String tableName);
	public PageGroupPanelTable savePageGroupPanelTable(PageGroupPanelTable pageGroupPanelTable);
	public PageGroupPanelTable findPageGroupPanelTable(String id);
}
