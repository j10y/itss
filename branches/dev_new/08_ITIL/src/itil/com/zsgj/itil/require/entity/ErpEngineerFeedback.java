package com.zsgj.itil.require.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
/**
 * ERP工程师反馈信息
 * @Class Name ERPEngineerFeedback
 * @Author lee
 * @Create In Apr 8, 2009
 */
public class ErpEngineerFeedback extends BaseObject{
	public static final Integer NEED = 1;
	public static final Integer NOTNEED = 0;
	private Long id;			//自动编号
	private String feekback;	//反馈
	private String otherInfo;	//备注
	private String attachment;	//附件
	private ERP_NormalNeed erpNeed; //关联需求实体
	private Integer basisFlag;	//是否需要BASIS工程师处理
	private UserInfo basisEngineer;	//BASIS工程师
	private String transferRequestNumber; //传输请求号
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFeekback() {
		return feekback;
	}
	public void setFeekback(String feekback) {
		this.feekback = feekback;
	}
	public String getOtherInfo() {
		return otherInfo;
	}
	public void setOtherInfo(String otherInfo) {
		this.otherInfo = otherInfo;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public ERP_NormalNeed getErpNeed() {
		return erpNeed;
	}
	public void setErpNeed(ERP_NormalNeed erpNeed) {
		this.erpNeed = erpNeed;
	}
	public Integer getBasisFlag() {
		return basisFlag;
	}
	public void setBasisFlag(Integer basisFlag) {
		this.basisFlag = basisFlag;
	}
	public UserInfo getBasisEngineer() {
		return basisEngineer;
	}
	public void setBasisEngineer(UserInfo basisEngineer) {
		this.basisEngineer = basisEngineer;
	}
	public String getTransferRequestNumber() {
		return transferRequestNumber;
	}
	public void setTransferRequestNumber(String transferRequestNumber) {
		this.transferRequestNumber = transferRequestNumber;
	}


	
}
