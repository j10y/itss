package com.digitalchina.itil.require.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * ���󸶿�ƻ�����
 * @Class Name SRPaymentPlanFile
 * @Author lee
 * @Create In Nov 26, 2009
 */
public class SRPaymentPlanFile extends BaseObject{
	private Long id;
	private SpecialRequirement specialRequire;	//����ʵ��
	private String attachment;	//����
	
	public SRPaymentPlanFile(){
	}
	
	/**
	 * ���ع���
	 * @param specialRequire
	 */
	public SRPaymentPlanFile(SpecialRequirement specialRequire){
		this.specialRequire = specialRequire;
		this.attachment = String.valueOf(new Date().getTime());
	}
	
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
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}

}
