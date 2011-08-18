package com.xpsoft.core.web.form;

import java.util.List;

public class TreeNode<E> {
	
	protected String id;
	protected String text;
	protected boolean leaf;
	protected String icon;
	protected String iconCls;
	protected boolean expanded;
	
	protected List<TreeNode<E>> children;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public List<TreeNode<E>> getChildren() {
		return children;
	}
	public void setChildren(List<TreeNode<E>> children) {
		this.children = children;
	}
	
}
