package com.zsgj.itil.require.entity;

import com.zsgj.info.framework.dao.BaseObject;
/**
 * BASIS����ʦ������Ϣ
 * @Class Name BASISEngineerFeedback
 * @Author lee
 * @Create In Apr 8, 2009
 */
public class BASISEngineerFeedback extends BaseObject{
	private Long id;			//�Զ����
	private String feekback;	//����
	private String otherInfo;	//��ע
	private String attachment;	//����
	private ERP_NormalNeed erpNeed; //��������ʵ��
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
}
