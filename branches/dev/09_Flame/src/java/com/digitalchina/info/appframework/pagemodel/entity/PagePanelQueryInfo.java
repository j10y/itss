package com.digitalchina.info.appframework.pagemodel.entity;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 查询面板与查询相关的配置信息
 * @Class Name PagePanelQueryInfo
 * @Author sa
 * @Create In 2009-3-12
 */
public class PagePanelQueryInfo extends BaseObject {
	private Long id;
	private PagePanel resultPanel;
	private SystemMainTable rootTable;
	private SystemMainTableColumn projectionColumn;
//	private Result
	
}
