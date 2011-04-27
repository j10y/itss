package com.zsgj.itil.require.entity;

import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
/**
 * ERP����ʦ������Ϣ
 * @Class Name ERPEngineerFeedback
 * @Author lee
 * @Create In Apr 8, 2009
 */
public class ErpEngineerFeedback extends BaseObject{
	public static final Integer NEED = 1;
	public static final Integer NOTNEED = 0;
	private Long id;			//�Զ����
	private String feekback;	//����
	private String otherInfo;	//��ע
	private String attachment;	//����
	private ERP_NormalNeed erpNeed; //��������ʵ��
	private Integer basisFlag;	//�Ƿ���ҪBASIS����ʦ����
	private UserInfo basisEngineer;	//BASIS����ʦ
	private String transferRequestNumber; //���������
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
