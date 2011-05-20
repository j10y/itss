package com.zsgj.itil.config.extci.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.UserInfo;
/**
 * �����Ŷ�
 * @Class Name DeliveryTeam
 * @Author duxh
 * @Create In Jul 2, 2010
 */
public class DeliveryTeam extends BaseObject {

	private static final long serialVersionUID = 2957962761846547733L;
	
	private Long id;

	private String name;

	private Department department; // ��������

	private UserInfo principal; // ������

	private UserInfo contactUser; // ��ϵ��
	
	private UserInfo technicalLeader; //����������
	
	private UserInfo majordomo; //�ܼ�

	private String services; // ��Ҫ��������
	
	private String cisn;

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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public UserInfo getPrincipal() {
		return principal;
	}

	public void setPrincipal(UserInfo principal) {
		this.principal = principal;
	}

	public UserInfo getContactUser() {
		return contactUser;
	}

	public void setContactUser(UserInfo contactUser) {
		this.contactUser = contactUser;
	}

	public UserInfo getMajordomo() {
		return majordomo;
	}

	public void setMajordomo(UserInfo majordomo) {
		this.majordomo = majordomo;
	}


	public String getCisn() {
		return cisn;
	}

	public void setCisn(String cisn) {
		this.cisn = cisn;
	}

	public UserInfo getTechnicalLeader() {
		return technicalLeader;
	}

	public void setTechnicalLeader(UserInfo technicalLeader) {
		this.technicalLeader = technicalLeader;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}
	
}
