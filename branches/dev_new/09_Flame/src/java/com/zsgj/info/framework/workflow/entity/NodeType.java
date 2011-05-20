package com.zsgj.info.framework.workflow.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * NodeType标示的就是有哪些工作流节点，并且以后缀作为工作流节点的标示
 * @Class Name NodeType
 * @Author guangsa
 * @Create In Feb 11, 2009
 */
public class NodeType extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -2748254965983527709L;
	private Long id;
	private String name;
	private String namePattern;
	private String description;
	private String link;
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
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
	public String getNamePattern() {
		return namePattern;
	}
	public void setNamePattern(String namePattern) {
		this.namePattern = namePattern;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
