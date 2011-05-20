package com.zsgj.itil.config.extci.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * 配置项类型生成实体参考模板，此类不要删除
 * @Class Name CITableTemplate
 * @Author sa
 * @Create In 2009-2-27
 */
@SuppressWarnings("serial")
public class CITableTemplate extends BaseObject {
	private Long id;

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
}
