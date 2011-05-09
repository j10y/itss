package com.zsgj.itil.config.service;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hibernate.mapping.PersistentClass;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.framework.dao.support.Page;
import com.zsgj.info.framework.security.entity.Module;
import com.zsgj.itil.config.entity.ConfigItemType;

/**
 * 用户自定义主表服务
 * @Class Name CustomerTableService
 * @Author sa
 * @Create In 2008-11-27
 */
public interface CustomerTableService {
	
	/**
	 * 为导入配置项关系数据，导出配置项数据
	 * @Methods Name exportAllConfigItemForRelation
	 * @Create In Jun 22, 2009 By sa
	 * @param fileRootPath
	 * @return String
	 */
	String exportAllConfigItemForRelation(String fileRootPath);
	/**
	 * 获得配置项数据Excel
	 * @Methods Name getConfigItemExcel
	 * @Create In Feb 1, 2010 By duxh
	 * @param citype
	 * @return 
	 * @Return HSSFWorkbook
	 */
	HSSFWorkbook getConfigItemExcel(ConfigItemType citype);
	
	/**
	 * 获得配置项模板
	 * @Methods Name getConfigItemTemplateExcel
	 * @Create In Feb 1, 2010 By duxh
	 * @Return String
	 */
	HSSFWorkbook getConfigItemTemplateExcel(SystemMainTable userTable);
	/**
	 * 将Excel数据更新到数据库。
	 * @Methods Name saveConfigItemExcel
	 * @Create In Feb 2, 2010 By duxh
	 * @param wb
	 * @param citype 
	 * @Return void
	 */
	public void saveConfigItemExcel(HSSFWorkbook wb,SystemMainTable smt);
	
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
	 * 获取配置项类型对应主表的历史表
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
	void saveSystemMainTableDeploy(SystemMainTable smt);
	
	/**
	 * 保存配置项类型面板，保存和发布时都可以调用这个方法
	 * @Methods Name saveConfigItemTablePanel
	 * @Create In 2009-2-21 By sa
	 * @param smt
	 * @param deployFlag void
	 */
	PagePanel saveConfigItemTablePanel(SystemMainTable smt, int deployFlag);	
	/**
	 * 获取用户主表对应配置项类型
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
	 * 获取所有顶层配置项类型
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
	 * 第2个参数是对应配置项类型的扩展主表，注意表名必须大写开头，前端要验证。
	 * @Methods Name genNewEntityAndMap
	 * @Create In 2008-12-2 By sa
	 * @param sourcePkg
	 * @param sourceClassName
	 * @param targetClass void
	 */
	void saveTableEntity(String sourcePkg, String sourceClassName, String targetClass);
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
	 * 生成hibernate持久化类型，返回系统主表
	 * @Methods Name genPersistentClass
	 * @Create In 2009-2-24 By sa
	 * @param model
	 * @param sourcePkg
	 * @param sourceClassName
	 * @param targetClass
	 * @param prefix 生成表结构的前缀
	 * @return SystemMainTable
	 */
	SystemMainTable genPersistentClass(PersistentClass model, String sourcePkg, 
			String sourceClassName, String targetClass, String prefix);
	
	/**
	 * 保存发布后又新增的字段，也就是扩展字段
	 * @Methods Name saveExtendProps
	 * @Create In 2009-2-24 By sa
	 * @param sourceClassName
	 * @param model void
	 */
	void saveExtendProps(String sourceClassName, PersistentClass model);
	
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
	 * 将配置项关系excel同步到数据库
	 * @Methods Name saveCIRExcel
	 * @Create In Feb 23, 2010 By duxh
	 * @param wb 
	 * @Return void
	 */
	public void saveCIRExcel(HSSFWorkbook wb);
}



