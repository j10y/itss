package com.zsgj.itil.service.entity;

import com.zsgj.info.framework.dao.BaseObject;

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
	/**
	 * @Return the String level
	 */
	public String getLevel() {
		return level;
	}
	/**
	 * @Param String level to set
	 */
	public void setLevel(String level) {
		this.level = level;
	}
	/**
	 * @Return the Integer solveHour
	 */
	public Integer getSolveHour() {
		return solveHour;
	}
	/**
	 * @Param Integer solveHour to set
	 */
	public void setSolveHour(Integer solveHour) {
		this.solveHour = solveHour;
	}
	//服务的水平
	private String level;
	//解决问题需要的小时数
	private Integer solveHour; 
}
