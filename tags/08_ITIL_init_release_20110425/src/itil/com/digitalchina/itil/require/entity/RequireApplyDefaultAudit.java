package com.digitalchina.itil.require.entity;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * ��������Ĭ�Ͻڵ�������ʵ��
 * @Class Name RequireApplyDefaultAudit
 * @Author sa
 * @Create In May 25, 2009
 */
public class RequireApplyDefaultAudit extends BaseObject {
	private Long id;
	private String departmentName;
	private UserInfo cadreBizAudit;			//����������
	private UserInfo cadreFinanceAudit;		//��������������
	private UserInfo groupFinanceAudit;		//���Ų���������
	private UserInfo cadreBusinessAudit;	//�������������� add by zhangzy in 2009 11 20
	private UserInfo clientItManager;			//�ͻ�IT���� add by zhangzy in 2009 11 25
	private Integer deleteFlag=0;				//�Ƿ����߼�ɾ�� add by zhangzy in 2010 02 02
	private Integer sortNum=0;					//����ֵ add by zhangzy in 2010 07 07
	private Integer enable=0;					//���� add by zhangzy in 2010 07 07
	
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
