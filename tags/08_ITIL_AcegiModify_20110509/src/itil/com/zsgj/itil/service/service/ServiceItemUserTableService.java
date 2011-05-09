package com.zsgj.itil.service.service;

import java.util.List;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.itil.config.entity.ConfigItemType;
import com.zsgj.itil.service.entity.ServiceItem;
import com.zsgj.itil.service.entity.ServiceItemUserTable;
import com.zsgj.itil.service.entity.ServiceType;
/**
 * 服务项关联需求主表关系实体服务
 * @Class Name ServiceItemUserTableService
 * @author lee
 * @Create In 2008-2-23 
 * TODO
 */
public interface ServiceItemUserTableService {
	
	/**
	 * 获取需求表的可见字段，一些程序需要显示的字段不让用户看见
	 * @Methods Name findRequireTableColumns
	 * @Create In 2009-3-18 By sa
	 * @param smt
	 * @return List
	 */
	List findRequireTableColumns(SystemMainTable smt);
	/**
	 * 通过服务项名称和需求主表名称获取关系
	 * @Methods Name findServiceItemUserTables
	 * @Create In 2009-2-23 By lee
	 * @param serviceItemName
	 * @param tableName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */ 
	Page findServiceItemUserTables(String serviceItemName,String tableName,int pageNo,int pageSize);
	/**
	 * 通过id获取服务项名称和需求主表关系
	 * @Methods Name findServiceItemUserTabelById
	 * @Create In 2009-2-23 By lee
	 * @param id
	 * @return ServiceItemUserTable
	 */
	ServiceItemUserTable findServiceItemUserTableById(String id);
	/**
	 * 通过服务项获取服务项需求主表关系
	 * @Methods Name findServiceItemUserTableByServiceItem
	 * @Create In Feb 25, 2009 By lee
	 * @param serviceItem
	 * @return ServiceItemUserTable
	 */
	ServiceItemUserTable findServiceItemUserTableByServiceItem(ServiceItem serviceItem);
	/**
	 * 生成服务项类型对应EXCEL模板文件
	 * @Methods Name saveConfigItemTypeExcel
	 * @Create In 2009-1-19 By sa
	 * @param citype
	 * @return String 返回生成的文件路径
	 */
	String saveUserMainTableExcel(SystemMainTable userTable, String fileRootPath);
	
	/**
	 * 获取所有基本信息面板
	 * @Methods Name findAllBasePanels
	 * @Create In 2008-12-29 By sa
	 * @return List<PagePanel>
	 */
	List<PagePanel> findAllBasePanels();
	
	/**
	 * 获取所有分组信息面板
	 * @Methods Name findAllGroupPanels
	 * @Create In 2008-12-29 By sa
	 * @return List<PagePanel>
	 */
	List<PagePanel> findAllGroupPanels();
	
	/**
	 * 获取服务项类型对应主表的历史表
	 * @Methods Name findUserTableEvent
	 * @Create In 2009-2-1 By sa
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable findUserTableEvent(SystemMainTable smt);
	
	/**
	 * 通过主表生成历史表
	 * @Methods Name saveEventTableByMainTable
	 * @Create In Apr 7, 2009 By sa
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable saveEventTableByMainTable(SystemMainTable smt);
	
	/**
	 * 发布系统主表，底层创建数据表和字段
	 * @Methods Name deployTable
	 * @Create In 2008-12-25 By sa
	 * @param smt void
	 */
	void saveSystemMainTableDeploy(ServiceItem scid, SystemMainTable smt);
	
	/**
	 * 保存服务项类型面板，保存和发布时都可以调用这个方法
	 * @Methods Name saveConfigItemTablePanel
	 * @Create In 2009-2-21 By sa
	 * @param smt
	 * @param deployFlag void
	 */
	void saveServiceItemUserTablePanel(ServiceItem sicd, SystemMainTable smt, int deployFlag);	
	/**
	 * 获取用户主表对应服务项类型
	 * @Methods Name findConfigItemTypeByTable
	 * @Create In 2008-12-23 By sa
	 * @param smt
	 * @return ConfigItemType
	 */
	ConfigItemType findConfigItemTypeByTable(SystemMainTable smt);
	/**
	 * 通过主表id获取主表对象
	 * @Methods Name findUserTableById
	 * @Create In 2008-12-23 By sa
	 * @param tableId
	 * @return SystemMainTable
	 */
	SystemMainTable findCustomerTableById(String tableId);
	/**
	 * 获取所有顶层服务项类型
	 * @Methods Name findAllConfigItemTop
	 * @Create In 2008-12-23 By sa
	 * @return List<ConfigItemType>
	 */
	List<ConfigItemType> findAllTopConfigItems();
	
	/**
	 * 获取所有的页面面板
	 * @Methods Name findAllPagePanels
	 * @Create In 2008-12-27 By sa
	 * @return List<PagePanel>
	 */
	List<PagePanel> findAllPagePanels();
	/**
	 * 利用用户新增的主表名称，包路径和目标路径生成实体和mapping，并更新session工厂。
	 * itil项目的例子调用如下:
	 * 	cts.genNewEntityAndMap("com.zsgj.itil.config.entity", "ServicePortfolio", ConfigItem.class);
	 * 第2个参数是对应服务项类型的扩展主表，注意表名必须大写开头，前端要验证。
	 * @Methods Name genNewEntityAndMap
	 * @Create In 2008-12-2 By sa
	 * @param sourcePkg
	 * @param sourceClassName
	 * @param targetClass void
	 */
	void saveTableEntity(ServiceItem scid, String sourcePkg, String sourceClassName, String targetClass);
	/**
	 * 获取所有的用户自定义主表
	 * @Methods Name findSystemMainTableByModule
	 * @Create In 2008-12-1 By sa
	 * @param module
	 * @param tableName
	 * @param pageNo
	 * @param pageSize
	 * @return Page
	 */
	Page findSystemMainTableByModule(Module module, String tableName, int pageNo, int pageSize);
	/**
	 * 保持用户自定义主表
	 * @Methods Name saveSystemMainTable
	 * @Create In 2008-11-27 By sa
	 * @param smt
	 * @return SystemMainTable
	 */
	SystemMainTable saveSystemMainTable(SystemMainTable smt);

	/**
	 * 保持用户自定义主表的字段新增或修改
	 * @Methods Name saveSystemMainTableColumn
	 * @Create In 2008-11-27 By sa
	 * @param smtc
	 * @return SystemMainTableColumn
	 */
	SystemMainTableColumn saveSystemMainTableColumn(SystemMainTableColumn smtc);
	
	/**
	 * 删除用户自定义主表
	 * @Methods Name removeSystemMainTable
	 * @Create In 2008-11-27 By sa
	 * @param smtIds void
	 */
	void removeSystemMainTable(String[] smtIds);
	
	/**
	 * 删除用户自定义表字段
	 * @Methods Name removeSystemMainTableColumn
	 * @Create In 2008-12-15 By sa
	 * @param smtcIds void
	 */
	void removeSystemMainTableColumn(String[] smtcIds);
	/**
	 * 通过自定义主表得到对应服务项
	 * @Methods Name findServiceItemByMainTable
	 * @Create In Mar 31, 2009 By lee
	 * @param smt
	 * @return ServiceItem
	 */
	ServiceItem findServiceItemByMainTable(SystemMainTable smt);
}
