package com.digitalchina.itil.config.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.itil.event.entity.Problem;
import com.digitalchina.itil.require.entity.SpecialRequirement;

/**
 * �������������ʵ��
 * @Class Name CIBatchModify
 * @Author lee
 * @Create In Aug 15, 2009
 */
public class CIBatchModifyShip extends BaseObject{
	
	
	
	private Long id;			//�Զ����
	
//	private Event event;
	//Ӧ������������
	private Problem problem;
	
	private SpecialRequirement specialRequirement;
	
	private CIBatchModify ciBatchModify;
	
	private UserInfo submitUser;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public SpecialRequirement getSpecialRequirement() {
		return specialRequirement;
	}
	public void setSpecialRequirement(SpecialRequirement specialRequirement) {
		this.specialRequirement = specialRequirement;
	}
	public UserInfo getSubmitUser() {
		return submitUser;
	}
	public void setSubmitUser(UserInfo submitUser) {
		this.submitUser = submitUser;
	}
	public CIBatchModify getCiBatchModify() {
		return ciBatchModify;
	}
	public void setCiBatchModify(CIBatchModify ciBatchModify) {
		this.ciBatchModify = ciBatchModify;
	}
	public Problem getProblem() {
		return problem;
	}
	public void setProblem(Problem problem) {
		this.problem = problem;
	}
	
}
