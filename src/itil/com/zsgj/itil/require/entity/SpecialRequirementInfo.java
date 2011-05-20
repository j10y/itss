package com.zsgj.itil.require.entity;

import java.util.Date;
import java.util.Set;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * ������������Ϣ
 * @Class Name SpecialRequirementInfo
 * @Author lee
 * @Create In Nov 29, 2009
 */
public class SpecialRequirementInfo extends BaseObject {
	public static final Integer TRUE = 1;	//�Ƿ�ѡ���
	public static final Integer FALSE = 0;	//�Ƿ�ѡ���
	private Long id;
	private SpecialRequirement specialRequire;
	private String projectName;			//��Ŀ����
	private String content;				//ʵʩ����
	private Date startDate;				//�ƻ���ʼʱ��
	private Date testBeginDate;			//���Կ�ʼʱ��
	private Date testEndDate;			//���Խ���ʱ��
	private Date finishDate;			//�ύ����ʱ��
	private String analyseFile;			//�����������
	private String testFile;			//���Ը���
	private Integer isComplete;			//�Ƿ���������
	private Integer isContent;			//�Ƿ�����
	private Integer manHour;			//��ʱ
	private Integer isTransmis;			//�Ƿ���
	private UserInfo transmisEngineer;	//���乤��ʦ
	private Set erpApps = new java.util.HashSet();	//ERP�ǳ����Ӧ�Ĵ���ϵͳ
	private String transContent ;		//��������
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SpecialRequirement getSpecialRequire() {
		return specialRequire;
	}
	public void setSpecialRequire(SpecialRequirement specialRequire) {
		this.specialRequire = specialRequire;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getTestBeginDate() {
		return testBeginDate;
	}
	public void setTestBeginDate(Date testBeginDate) {
		this.testBeginDate = testBeginDate;
	}
	public Date getTestEndDate() {
		return testEndDate;
	}
	public void setTestEndDate(Date testEndDate) {
		this.testEndDate = testEndDate;
	}
	public Date getFinishDate() {
		return finishDate;
	}
	public void setFinishDate(Date finishDate) {
		this.finishDate = finishDate;
	}
	public String getAnalyseFile() {
		return analyseFile;
	}
	public void setAnalyseFile(String analyseFile) {
		this.analyseFile = analyseFile;
	}
	public String getTestFile() {
		return testFile;
	}
	public void setTestFile(String testFile) {
		this.testFile = testFile;
	}
	public Integer getIsComplete() {
		return isComplete;
	}
	public void setIsComplete(Integer isComplete) {
		this.isComplete = isComplete;
	}
	public Integer getIsContent() {
		return isContent;
	}
	public void setIsContent(Integer isContent) {
		this.isContent = isContent;
	}
	public Integer getManHour() {
		return manHour;
	}
	public void setManHour(Integer manHour) {
		this.manHour = manHour;
	}
	public Integer getIsTransmis() {
		return isTransmis;
	}
	public void setIsTransmis(Integer isTransmis) {
		this.isTransmis = isTransmis;
	}
	public UserInfo getTransmisEngineer() {
		return transmisEngineer;
	}
	public void setTransmisEngineer(UserInfo transmisEngineer) {
		this.transmisEngineer = transmisEngineer;
	}
	public Set getErpApps() {
		return erpApps;
	}
	public void setErpApps(Set erpApps) {
		this.erpApps = erpApps;
	}
	public String getTransContent() {
		return transContent;
	}
	public void setTransContent(String transContent) {
		this.transContent = transContent;
	}
	
}
