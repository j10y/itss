package com.zsgj.itil.config.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.itil.event.entity.Problem;
import com.zsgj.itil.require.entity.SpecialRequirement;

/**
 * 配置项批量变更实体
 * @Class Name CIBatchModify
 * @Author lee
 * @Create In Aug 15, 2009
 */
public class CIBatchModifyShip extends BaseObject{
	
	
	
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = 2020907115557634793L;

	private Long id;			//自动编号
	
//	private Event event;
	//应该是问题与变更
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
