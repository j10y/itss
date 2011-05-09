package com.zsgj.itil.require.entity;

import java.util.Date;
import java.util.Set;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;

/**
 * 定制类需求信息
 * @Class Name SpecialRequirementInfo
 * @Author lee
 * @Create In Nov 29, 2009
 */
public class SpecialRequirementInfo extends BaseObject {
	public static final Integer TRUE = 1;	//是否选项，是
	public static final Integer FALSE = 0;	//是否选项，否
	private Long id;
	private SpecialRequirement specialRequire;
	private String projectName;			//项目名称
	private String content;				//实施内容
	private Date startDate;				//计划开始时间
	private Date testBeginDate;			//测试开始时间
	private Date testEndDate;			//测试结束时间
	private Date finishDate;			//提交上线时间
	private String analyseFile;			//需求分析附件
	private String testFile;			//测试附件
	private Integer isComplete;			//是否满足需求
	private Integer isContent;			//是否满意
	private Integer manHour;			//工时
	private Integer isTransmis;			//是否传输
	private UserInfo transmisEngineer;	//传输工程师
	private Set erpApps = new java.util.HashSet();	//ERP非常规对应的传输系统
	private String transContent ;		//传输内容
	
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
