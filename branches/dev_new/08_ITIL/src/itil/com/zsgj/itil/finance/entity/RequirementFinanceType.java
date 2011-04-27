package com.zsgj.itil.finance.entity;

import com.zsgj.info.framework.dao.BaseObject;
/**
 * 需求财务分类
 * @Class Name RequirementFinanceType
 * @Author lee
 * @Create In Apr 9, 2009
 */
public class RequirementFinanceType extends BaseObject{
	public static String TYPE_NORMAL = "normal";
	public static String TYPE_BATCH = "batch";
	public static String TYPE_REMARK = "remark";
	private Long id;
	private String name;
	private String keyWord;
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
	public String getKeyWord() {
		return keyWord;
	}
	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
}
