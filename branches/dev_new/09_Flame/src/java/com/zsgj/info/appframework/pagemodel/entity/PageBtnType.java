package com.zsgj.info.appframework.pagemodel.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 按钮组件,表单还是跳转
 * @Class Name PageModelButtonType
 * @Author sa
 * @Create In 2008-11-30
 */
public class PageBtnType extends BaseObject {
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 7507333088903314842L;
	private Long id;
	private String name;//add,modify,remove,submit,save,return
	private String cnName;
	@Override
	public String getUniquePropName(){
		return name;
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
	public String getCnName() {
		return cnName;
	}
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}
}
