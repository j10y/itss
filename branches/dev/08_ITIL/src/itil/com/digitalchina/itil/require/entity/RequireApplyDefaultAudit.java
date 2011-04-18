package com.digitalchina.itil.require.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * 需求申请默认节点审批人实体
 * @Class Name RequireApplyDefaultAudit
 * @Author sa
 * @Create In May 25, 2009
 */
public class RequireApplyDefaultAudit extends BaseObject {
	private Long id;
	private String departmentName;
	private UserInfo cadreBizAudit;			//本部审批人
	private UserInfo cadreFinanceAudit;		//本部财务审批人
	private UserInfo groupFinanceAudit;		//集团财务审批人
	private UserInfo cadreBusinessAudit;	//本部商务审批人 add by zhangzy in 2009 11 20
	private UserInfo clientItManager;			//客户IT经理 add by zhangzy in 2009 11 25
	private Integer deleteFlag=0;				//是否是逻辑删除 add by zhangzy in 2010 02 02
	private Integer sortNum=0;					//排序值 add by zhangzy in 2010 07 07
	private Integer enable=0;					//可用 add by zhangzy in 2010 07 07
	
	public UserInfo getClientItManager() {
		return clientItManager;
	}
	public void setClientItManager(UserInfo clientItManager) {
		this.clientItManager = clientItManager;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public UserInfo getCadreBizAudit() {
		return cadreBizAudit;
	}
	public void setCadreBizAudit(UserInfo cadreBizAudit) {
		this.cadreBizAudit = cadreBizAudit;
	}
	public UserInfo getCadreFinanceAudit() {
		return cadreFinanceAudit;
	}
	public void setCadreFinanceAudit(UserInfo cadreFinanceAudit) {
		this.cadreFinanceAudit = cadreFinanceAudit;
	}
	public UserInfo getGroupFinanceAudit() {
		return groupFinanceAudit;
	}
	public void setGroupFinanceAudit(UserInfo groupFinanceAudit) {
		this.groupFinanceAudit = groupFinanceAudit;
	}
	public UserInfo getCadreBusinessAudit() {
		return cadreBusinessAudit;
	}
	public void setCadreBusinessAudit(UserInfo cadreBusinessAudit) {
		this.cadreBusinessAudit = cadreBusinessAudit;
	}
	public Integer getDeleteFlag() {
		return deleteFlag;
	}
	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	public Integer getSortNum() {
		return sortNum;
	}
	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}
	public Integer getEnable() {
		return enable;
	}
	public void setEnable(Integer enable) {
		this.enable = enable;
	}
	
	
}
