package com.digitalchina.itil.config.action;

/**
 * Ê÷½Úµã
 * @Class Name TreeNode
 * @Author duxh
 * @Create In Nov 18, 2009
 */
public class TreeNode {
	public static final String SERVICEITEM_ICON="/images/cls/gears.gif";
	public static final String CONFIGITEM_ICON="/images/other/file.gif";
	private String itemCode;
	private Long rid;
	private String text;
	private String itemType;
	private boolean leaf;
	private String icon;
	private boolean doubleClickExpand;
	
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public Long getRid() {
		return rid;
	}
	public void setRid(Long rid) {
		this.rid = rid;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
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
	public boolean isDoubleClickExpand() {
		return doubleClickExpand;
	}
	public void setDoubleClickExpand(boolean doubleClickExpand) {
		this.doubleClickExpand = doubleClickExpand;
	}
	
}

