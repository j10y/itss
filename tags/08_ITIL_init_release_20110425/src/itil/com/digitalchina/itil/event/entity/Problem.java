package com.digitalchina.itil.event.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;
import com.digitalchina.info.framework.security.entity.UserInfo;

/**
 * ����ʵ��
 * @Class Name Problem
 * @Author sa
 * @Create In 2008-11-9
 */
public class Problem extends BaseObject {
	private Long id;
	//private String problemName;//��������
	private String summary; //ժҪ
	private String description; //����
	private String remark; //������Ϣ,��ע
	
	private ProblemStatus status;//״̬
	//�����ύ��
	private UserInfo submitUser;
	//�������ϵ������
	private UserInfo contactUser;
	//������ϵ���ʼ�
	private String contactEmail;
	//������ϵ�˵绰
	private String contactPhone;
	
	//���ⴴ��ʱ��
	private Date submitTime;
	//��������޸�ʱ��
	private Date modifyTime;
	//����ر�����
	private Date closedDate;
	//add by guoxl in 20090803 begin
	private String problemCisn;
	//add by guoxl in 20090803 end
	private String files; //���и��� 
	public Date getClosedDate() {
		return closedDate;
	}
	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}
	public String getContactEmail() {
		return contactEmail;
	}
	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	
	public UserInfo getContactUser() {
		return contactUser;
	}
	public void setContactUser(UserInfo contactUser) {
		this.contactUser = contactUser;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public ProblemStatus getStatus() {
		return status;
	}
	public void setStatus(ProblemStatus status) {
		this.status = status;
	}
	public Date getSubmitTime() {
		return submitTime;
	}
	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}
	public UserInfo getSubmitUser() {
		return submitUser;
	}
	public void setSubmitUser(UserInfo submitUser) {
		this.submitUser = submitUser;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public String getProblemCisn() {
		return problemCisn;
	}
	public void setProblemCisn(String problemCisn) {
		this.problemCisn = problemCisn;
	}
	
//	//������ϵ��ʱ��
	
//	//��������ͻ�
//	private Customer customer;
//	//��������
//	private Category category; 
//	//�������ȼ�
//	private Priority priority;
	//Ĭ�Ϸ����֧����
//	private SupportGroup supportGroup;
	
//	//�����Ӱ�����
//	private String impactAnalysis;
//	
//	//Ԥ�����Сʱ��
//	private Double devEstTime;
//	//ʱ�����Сʱ��
//	private Double devTime;
//	//�ѻ���ʱ��
//	private Double timeSpent;
//	//�״λ�Ӧ����
//	private Date firstResponseDate;
//	//ĩ�λ�Ӧ����
//	private Date lastResponseDate;
//	//���һ���û��޸�����ʱ��
//	private Date lastCustActionDate;
//	//�û������������
//	private Date expectedResolveDate;
	
//	private String contactTimezone;

//	�鿴���
//	private Integer userViewFlag; //
//	�Ƿ��Խ�����
//	private Integer selfResolveFlag;
	
}
