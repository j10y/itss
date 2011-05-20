package com.zsgj.info.appframework.menu.entity;

/**
 * ��ʱ��¼Ŀ¼����
 * @Class Name TempMenuItem
 * @Author lee
 * @Create In Aug 17, 2010
 */
@SuppressWarnings("serial")
public class TempMenuItem  extends TemplateMenuItem{
	public static Integer ENABLE = 1;
	public static Integer DISABLE = 0;
	private Integer enable;

	public TempMenuItem(TemplateMenuItem templateMenuItem,Integer enable){
		this.setId(templateMenuItem.getId());
		this.setMenuName(templateMenuItem.getMenuName());
		this.setMenuOrder(templateMenuItem.getMenuOrder());
		this.setMenuUrl(templateMenuItem.getMenuUrl());
		this.setParentItem(templateMenuItem.getParentItem());
		this.setEnable(enable);
	}
	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}
}