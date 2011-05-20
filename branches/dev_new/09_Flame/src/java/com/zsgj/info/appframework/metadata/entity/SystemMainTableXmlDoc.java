package com.zsgj.info.appframework.metadata.entity;

import java.util.Date;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 系统主表XML格式DOC描述
 * @Class Name SystemMainTableXmlDoc
 * @Author sa
 * @Create In Jun 5, 2009
 */
public class SystemMainTableXmlDoc extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -3759280785467225365L;
	private Long id;
	private SystemMainTable systemMainTable;
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
	 * @Return the String xmlDocUrl
	 */
	public String getXmlDocUrl() {
		return xmlDocUrl;
	}
	/**
	 * @Param String xmlDocUrl to set
	 */
	public void setXmlDocUrl(String xmlDocUrl) {
		this.xmlDocUrl = xmlDocUrl;
	}
	/**
	 * @Return the Date versionDate
	 */
	public Date getVersionDate() {
		return versionDate;
	}
	/**
	 * @Param Date versionDate to set
	 */
	public void setVersionDate(Date versionDate) {
		this.versionDate = versionDate;
	}
	private String xmlDocUrl;
	private Date versionDate;
}
