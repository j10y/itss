package com.zsgj.itil.service.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 * ���񼶱�SL
 * һ����������ˮƽĿ���Ԥ�����ҿɱ���ĳɹ���
 * �������ˮƽ��ʱҲ����ʽ����ָ����ˮƽĿ�ꡣ
 * ע�������ˮƽЭ��SLA�����֡�
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
	//�����ˮƽ
	private String level;
	//���������Ҫ��Сʱ��
	private Integer solveHour; 
}
