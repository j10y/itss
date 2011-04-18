package com.digitalchina.itil.service.entity;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 服务级别SL
 * 一个或多个服务水平目标可预测量且可报告的成果。
 * 术语服务水平有时也被正式用作指服务水平目标。
 * 注意如服务水平协议SLA相区分。
 * @Class Name ServiceLevel
 * @Author sa
 * @Create In 2008-11-11
 */
public class ServiceLevel extends BaseObject {
	private Long id;
	//服务的水平
	private String level;
	//解决问题需要的小时数
	private Integer solveHour; 
}
