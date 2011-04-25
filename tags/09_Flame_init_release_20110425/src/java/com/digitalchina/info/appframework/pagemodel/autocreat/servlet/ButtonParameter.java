package com.digitalchina.info.appframework.pagemodel.autocreat.servlet;

import java.util.ArrayList;
import java.util.List;

public class ButtonParameter {
	private String buttonName;// 按钮名
	private String container;// 按钮容器名，即模板或面板名称
	private String buttonMainTable;// 按钮容器主表名
	private String buttonType;// 按钮类型
	private String buttonLink;// 按钮指定连接
	private String nextPageModel;// 按钮指定下一个pageModel
	private String imageUrl;// 按钮图标

	public String getButtonName() {
		return buttonName;
	}

	public void setButtonName(String buttonName) {
		this.buttonName = buttonName;
	}

	public String getContainer() {
		return container;
	}

	public void setContainer(String container) {
		this.container = container;
	}

	public String getButtonMainTable() {
		return buttonMainTable;
	}

	public void setButtonMainTable(String buttonMainTable) {
		this.buttonMainTable = buttonMainTable;
	}

	public String getButtonType() {
		return buttonType;
	}

	public void setButtonType(String buttonType) {
		this.buttonType = buttonType;
	}

	public String getButtonLink() {
		return buttonLink;
	}

	public void setButtonLink(String buttonLink) {
		this.buttonLink = buttonLink;
	}

	public String getNextPageModel() {
		return nextPageModel;
	}

	public void setNextPageModel(String nextPageModel) {
		this.nextPageModel = nextPageModel;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
