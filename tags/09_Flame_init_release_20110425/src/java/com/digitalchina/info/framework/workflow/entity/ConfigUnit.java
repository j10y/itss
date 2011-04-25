package com.digitalchina.info.framework.workflow.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * ConfigUnit实体是一个配置单元，里面用相应的表示字符串去实现具体的执行保存等方法
 * @Class Name ConfigUnit
 * @Author guangsa
 * @Create In Feb 11, 2009
 */
public class ConfigUnit extends BaseObject{
	private Long id;
	private String name;
	private String description;
	private String url;
	private String executes;
	private String saves;
	private String handler;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}	
	public String getExecutes() {
		return executes;
	}
	public void setExecutes(String executes) {
		this.executes = executes;
	}
	
	public String getSaves() {
		return saves;
	}
	public void setSaves(String saves) {
		this.saves = saves;
	}
	public String getHandler() {
		return handler;
	}
	public void setHandler(String handler) {
		this.handler = handler;
	}

}
