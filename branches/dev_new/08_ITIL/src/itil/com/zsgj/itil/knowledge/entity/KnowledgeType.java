package com.zsgj.itil.knowledge.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.framework.dao.BaseObject;
/**
 * 知识类型
 * 		 1.	制度和规定
 *		 2.	常见问题解决方法（在问题解决过程中形成）
 *		 3.	服务使用手册
 *		 4.	服务管理手册
 *		 5.	需求及相关的设计文档
 *		 6.	合同
 * @Class Name KnowledgeType
 * @Author Administrator
 * @Create In Mar 5, 2009
 */
@SuppressWarnings("serial")
public class KnowledgeType extends BaseObject {
	private Long id;
	
	private String name;//知识类型名称
	
	private SystemMainTable systemMainTable; //解决方案类型对应的主表
	
	private String className; //类名称
	
	private PagePanel pagePanel; //表单面板，面板中包含主表
	private PagePanel pageListPanel;//列表面板
	private PagePanel pageQueryPanel;//查询面板

	/**
	 * @Return the PagePanel pageQueryPanel
	 */
	public PagePanel getPageQueryPanel() {
		return pageQueryPanel;
	}

	/**
	 * @Param PagePanel pageQueryPanel to set
	 */
	public void setPageQueryPanel(PagePanel pageQueryPanel) {
		this.pageQueryPanel = pageQueryPanel;
	}

	/**
	 * @Return the PagePanel pageListPanel
	 */
	public PagePanel getPageListPanel() {
		return pageListPanel;
	}

	/**
	 * @Param PagePanel pageListPanel to set
	 */
	public void setPageListPanel(PagePanel pageListPanel) {
		this.pageListPanel = pageListPanel;
	}

	/**
	 * @Return the Long id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @Param Long id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @Return the String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @Param String name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @Return the SystemMainTable systemMainTable
	 */
	public SystemMainTable getSystemMainTable() {
		return systemMainTable;
	}

	/**
	 * @Param SystemMainTable systemMainTable to set
	 */
	public void setSystemMainTable(SystemMainTable systemMainTable) {
		this.systemMainTable = systemMainTable;
	}

	/**
	 * @Return the String className
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @Param String className to set
	 */
	public void setClassName(String className) {
		this.className = className;
	}

	/**
	 * @Return the PagePanel pagePanel
	 */
	public PagePanel getPagePanel() {
		return pagePanel;
	}

	/**
	 * @Param PagePanel pagePanel to set
	 */
	public void setPagePanel(PagePanel pagePanel) {
		this.pagePanel = pagePanel;
	}
	
}
