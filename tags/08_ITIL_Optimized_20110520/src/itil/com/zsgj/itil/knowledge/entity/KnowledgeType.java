package com.zsgj.itil.knowledge.entity;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.pagemodel.entity.PagePanel;
import com.zsgj.info.framework.dao.BaseObject;
/**
 * ֪ʶ����
 * 		 1.	�ƶȺ͹涨
 *		 2.	�������������������������������γɣ�
 *		 3.	����ʹ���ֲ�
 *		 4.	��������ֲ�
 *		 5.	������ص�����ĵ�
 *		 6.	��ͬ
 * @Class Name KnowledgeType
 * @Author Administrator
 * @Create In Mar 5, 2009
 */
@SuppressWarnings("serial")
public class KnowledgeType extends BaseObject {
	private Long id;
	
	private String name;//֪ʶ��������
	
	private SystemMainTable systemMainTable; //����������Ͷ�Ӧ������
	
	private String className; //������
	
	private PagePanel pagePanel; //����壬����а�������
	private PagePanel pageListPanel;//�б����
	private PagePanel pageQueryPanel;//��ѯ���

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
