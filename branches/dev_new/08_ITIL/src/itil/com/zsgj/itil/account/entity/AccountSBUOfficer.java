package com.zsgj.itil.account.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * ��ͬ�����˺�����SBU��ǩ����Ϣ
 * @Class Name AccountSBUOfficer
 * @Author lee
 * @Create In Jun 1, 2009
 */
public class AccountSBUOfficer extends BaseObject{
	/**
	 * @Field long serialVersionUID 
	 */
	private static final long serialVersionUID = -5010561921396505447L;
	private Long id;
	private String processNameDescription;		//��������������(�磺BI�ʺ�����)
	private String nodeName;		//���̽ڵ���
	private String personScope;		//�����ӷ�Χ
	private String confirmUser;	//�ڵ�������
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	
	public String getProcessNameDescription() {
		return processNameDescription;
	}
	public void setProcessNameDescription(String processNameDescription) {
		this.processNameDescription = processNameDescription;
	}
	public String getConfirmUser() {
		return confirmUser;
	}
	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}
	public String getPersonScope() {
		return personScope;
	}
	public void setPersonScope(String personScope) {
		this.personScope = personScope;
	}
}
