package com.zsgj.info.appframework.metadata.entity;

import com.zsgj.info.framework.dao.BaseObject;

/**
 *  ʵ������״̬
 *  
 *  �ݸ壬�ύ�������У���ʽ�棬ɾ����
 *  
 * @Class Name EntityObjectStatus
 * @Author sa
 * @Create In 2009-2-23
 */
public class EntityObjectStatus extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -198354101147239117L;
	private Long id;
	private String name;
//	�Ƿ����ʷ��־�����ύ��ɾ��
	private Integer eventFlag; 
	
	public Integer getEventFlag() {
		return eventFlag;
	}
	public void setEventFlag(Integer eventFlag) {
		this.eventFlag = eventFlag;
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
	

}
