package com.digitalchina.itil.require.entity;

import java.util.Date;

import com.digitalchina.info.framework.dao.BaseObject;

/**
 * 需求测试报告附件
 * @Class Name SRTestReportFile
 * @Author lee
 * @Create In Aug 6, 2009
 */
public class SRTestReportFile extends BaseObject{
	private Long id;
	private SpecialRequirement specialRequire;	//需求实体
	private String attachment;	//附件
	
	public SRTestReportFile(){
	}
	
	public SRTestReportFile(SpecialRequirement specialRequire){
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
