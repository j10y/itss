package com.digitalchina.info.appframework.pagemodel.service;

import java.util.List;
import java.util.Map;

import com.digitalchina.info.appframework.metadata.entity.Column;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.appframework.pagemodel.entity.PageGroupPanelTable;
import com.digitalchina.info.appframework.pagemodel.entity.PageModel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanel;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelBtn;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelColumn;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelFieldSet;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTable;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelTableRelation;
import com.digitalchina.info.appframework.pagemodel.entity.PagePanelType;
import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Module;

/**
 * PagePanel服务
 * @Class Name PagePanelService
 * @Author sa
 * @Create In 2008-11-21
 */
public interface PagePanelService {
	
	/**
	 * 通过panel的title来确定查找相应的panel
	 * @Methods Name findPagePanelByPanelName
	 * @Create In Dec 23, 2008 By Administrator
	 * @param panelName
	 * @return PagePanel
	 */
	public PagePanel findPagePanelByPanelName(String panelName);
	
	/**
	 * 通过分页来查找相应页码的panel
	 * @Methods Name findPagePanelByPageNoAndAmount
	 * @Create In Dec 23, 2008 By Administrator
	 * @param pageNo
	 * @param amount
	 * @return List
	 */
	public Page findPagePanelByPage(String factor ,String box,int pageNo, int pageSize);
	
	/**
	 * 根据是分组面板还是普通面板来查找相应的面板类型
	 * @Methods Name searchPagePanelByPanelName
	 * @Create In Dec 24, 2008 By Administrator
	 * @param panelName
	 * @return List
	 */
	public List searchPagePanelByPanelName(String panelName);
	
	/**
	 * 
	 * @Methods Name findPagePanelByTable
	 * @Create In 2008-12-12 By sa
	 * @param smt
	 * @return PagePanel
	 */
	PagePanel findPagePanelByTable(SystemMainTable smt, Integer settingType);
	/**
	 * 获取panel中所有的系统主表
	 * @Methods Name findTableByPanel
	 * @Create In 2008-12-1 By sa
	 * @param panel
	 * @return List<SystemMainTable>
	 */
	List<SystemMainTable> findTableByPanel(PagePanel panel);
	
	/**
	 * 通过关键字获取pagePanel
	 * @Methods Name findPagePanel
	 * @Create In 2008-12-1 By sa
	 * @param keyName
	 * @return PagePanel
	 */
	PagePanel findPagePanel(String keyName);
	/**
	 * 初始化PagePanel的可见字段
	 * @Methods Name saveColumnToPanelColumn
	 * @Create In 2008-11-24 By sa
	 * @param module
	 * @return List<PagePanelColumn>
	 */
	List<PagePanelColumn> saveColumnToPanelColumn(PagePanel panel, List<Column> columns);
	/**
	 * 提供指定模块下的所有系统主表
	 * @Methods Name findTableByModule
	 * @Create In 2008-11-24 By sa
	 * @param module
	 * @return List<SystemMainTable>
	 */
	List<SystemMainTable> findTableByModule(Module module);
	/**
	 * 获取系统主表的所有字段
	 * @Methods Name findSystemMainTableColumns
	 * @Create In 2008-7-16 By peixf
	 * @param smt
	 * @return List
	 */
	List<Column> findColumns(SystemMainTable smt);
	
	/**
	 * 获取主表指定设置类型的可见字段
	 * @Methods Name findColumns
	 * @Create In 2008-11-21 By sa
	 * @param smt
	 * @param settingType 1列表，
	 * @return List<Column>
	 */
	List<Column> findColumns(SystemMainTable smt, int settingType);
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
	Page findPagePanel(Module module, String pageName, int pageNo, int pageSize);
	
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
	 * 删除PagePanel
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
	void removePagePanel(String[] pagePanelId);
	
	/**
	 * 通过ID获取PagePanel
	 * @Methods Name findPagePanelById
	 * @Create In 2008-11-21 By sa
	 * @param pagePanelId
	 * @return PagePanel
	 */
	PagePanel findPagePanelById(String pagePanelId);
		
	/**
	 * 通过PagePanel获取其下的所有pagePanel
	 * @Methods Name findPagePanelByPageModel
	 * @Create In 2008-11-21 By sa
	 * @return List 返回值中的数据类型是PagePanel
	 */
	List<PagePanel> findPagePanelByPageModel(PageModel pageModel);

	/**
	 * 获取指定PagePanel的所有子pagePanel和字段
	 * @Methods Name findPagePaneAndColumnByPagePanel
	 * @Create In 2008-11-21 By sa
	 * @param parentPagePanel
	 * @return List
	 */
	List findPagePanelAndColumnByPagePanel(PagePanel parentPagePanel);
	
	/**
	 * 保存pagePanel
	 * @Methods Name savePagePanel
	 * @Create In 2008-11-21 By sa
	 * @param pagePanel
	 * @param childPanelIds
	 * @param smtId
	 * @return PagePanel
	 */
	PagePanel savePagePanel(PagePanel pagePanel, List childPanelIds, String smtId);
	
	/**
	 * 获取所有PagePanelTable
	 * @Methods Name findPagePanelTable
	 * @Create In 2008-12-12 By sa
	 * @param panel
	 * @return List<PagePanelTable>
	 */
	List<PagePanelTable> findPagePanelTable(PagePanel panel);
	
	
	/**
	 * 获取与pagePanel关联的系统主表
	 * @Methods Name findMainTableByPanel
	 * @Create In 2008-11-27 By lee
	 * @param pagePanel
	 * @return List
	 */
	List<SystemMainTable> findMainTableByPanel(PagePanel panel);
	
	/**
	 * 获取panel之间表的关系
	 * @Methods Name findMainTableRelationByPanel
	 * @Create In 2008-12-7 By sa
	 * @param panel
	 * @return List
	 */
	List findMainTableRelationByPanel(PagePanel panel);
	
	/**
	 * 根据制定的Module来查找相应的pagePanel
	 */
	List findPagePanelByModule(Module module);
	/**
	 * 获取直接与pagePanel关联的PagePanelColumn集合
	 * @Methods Name findPagePanelColumnNoParent
	 * @Create In 2008-11-28 By lee
	 * @param pagePanel
	 * @return List
	 */
	List findPagePanelColumnNoParent(PagePanel pp);
	/**
	 * 获取直接与pagePanel关联的PagePanelColumn集合
	 * @Methods Name findPagePanelColumnByPagePanelIdAndParentId
	 * @Create In 2008-11-28 By lee
	 * @param pagePanelId
	 * @param parentId
	 * @return List
	 */
	List findChildenColumnByParentId(String parentId);
	/**
	 * 直接将系统主表中的字段转换成pagePanelColumn的形式加入pagePanel中
	 * @Methods Name savePanelColumnsFormSysMainTable
	 * @Create In 2008-11-28 By lee
	 * @param pagePanelId
	 * @param smtId
	 * @return void
	 */
	void savePanelColumnsFormSysMainTable(String ppId,String smtId);
	
	/**
	 * 获取panel中某个表的子表信息，如获取同一个panel中的配置项基本信息的财务信息子表
	 * @Methods Name findPanelTableRelByParent
	 * @Create In 2008-12-8 By sa
	 * @param panel
	 * @param parentSmt
	 * @return List<PagePanelTableRelation>
	 */
	List<PagePanelTableRelation> findPanelTableRelByParent(PagePanel panel, SystemMainTable parentSmt);
	
	/**
	 * 获取panel中某个表的所有父表，如申请信息是主实体，但他依赖于服务商，要显示服务商的多个字段
	 * @Methods Name findPanelTableRelBySub
	 * @Create In 2008-12-8 By sa
	 * @param panel
	 * @param subSmt
	 * @return List<PagePanelTableRelation>
	 */
	List<PagePanelTableRelation> findPanelTableRelBySub(PagePanel panel, SystemMainTable subSmt);
	/**
	 * 通过cnName这个字段来得到系统主表的对象
	 * @Methods Name findSystemMainTable
	 * @Create In Dec 17, 2008 By Administrator
	 * @param tableCnName
	 * @return SystemMainTable
	 */
	public SystemMainTable findSystemMainTable(String tableId);
	public SystemMainTable findSystemMainTableByName(String tableName);
	/**
	 * 通过xtype这个字段来得到panel的对象
	 * @Methods Name findPagePanelByXtype
	 * @Create In Dec 18, 2008 By Administrator
	 * @param xtype
	 * @return PagePanel
	 */
	public PagePanelType findPagePanelTypeByXtype(String xtype);
	
	/**
	 * 获取所有的分组类型面板类型
	 * @Methods Name findAllGroupPanelTypes
	 * @Create In 2008-12-28 By sa
	 * @return List<PagePanelType>
	 */
	public List<PagePanelType> findAllGroupPanelTypes();
	
	/**
	 * 获取所有的基本类型面板类型
	 * @Methods Name findAllBasePanelTypes
	 * @Create In 2008-12-28 By sa
	 * @return List<PagePanelType>
	 */
	public List<PagePanelType> findAllBasePanelTypes();
	
	public List findAllTable();
	public SystemMainTable findSystemMainTableByCnName(String cnName);
	public List findAllSystemMainTableColumnByName(String tableName);
	public SystemMainTableColumn findSystemMainTableColumn(String CID);
	PagePanelTableRelation savePagePanelTableRelation(PagePanelTableRelation pptr);
	void removePagePanelTableRelation(String id);
	
	/**
	 * 系统主表combobox的分页
	 * @Methods Name findSystemMainTable
	 * @Create In Dec 29, 2008 By Administrator
	 * @param pageNo
	 * @param pageSize
	 * @return List
	 */
	public Page findSystemMainTable(int pageNo,int pageSize);
	/**
	 * 根据系统主表的id来查找外键combobox的分页
	 * @Methods Name findForeignKey
	 * @Create In Dec 29, 2008 By Administrator
	 * @param sysTabelId
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findForeignKey(Long sysTabelId , int pageNo,int pageSize);

    /**
	 * 根据系统module来查找pagePanel的分页
	 * @Methods Name findForeignKey
	 * @Create In Dec 29, 2008 By Administrator
	 * @param sysTabelId
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	public Page findPanelByPageModule(Module module, int pageNo,int pageSize);
	
	public List<PageGroupPanelTable> findPageGroupPanelTableByPanel(PagePanel pagePanel);
	
	void removePageGroupPanelTable(String id);
	public List findAllPagePanel();
	public PageGroupPanelTable savePageGroupPanelTable(PageGroupPanelTable pageGroupPanelTable);
	public PageGroupPanelTable findPageGroupPanelTable(String id);
	/**
	 * 保存PagePanelColumn
	 * @Methods Name savePagePanelColumn
	 * @Create In May 13, 2009 By Administrator
	 * @param pagePanel
	 * @param index
	 * @return PagePanelColumn
	 */
	public PagePanelColumn savePagePanelColumn(PagePanel pagePanel,String index);
	public PagePanelFieldSet savePagePanelFieldSet(PagePanelColumn pagePanelColumn,PagePanel pagePanel ,String title);
	
}
