package com.xpsoft.oa.action.shop;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.shop.SpPaAssessmentcriteria;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.shop.SpPaAssessmentcriteriaService;

import flexjson.JSONSerializer;

public class SpPaAssessmentcriteriaAction extends BaseAction{
	@Resource
	private SpPaAssessmentcriteriaService spPaAssessmentcriteriaService;
	private SpPaAssessmentcriteria spPaAssessmentcriteria;
	private long id;
	
	public SpPaAssessmentcriteriaService getSpPaAssessmentcriteriaService() {
		return spPaAssessmentcriteriaService;
	}
	public void setSpPaAssessmentcriteriaService(
			SpPaAssessmentcriteriaService spPaAssessmentcriteriaService) {
		this.spPaAssessmentcriteriaService = spPaAssessmentcriteriaService;
	}
	public SpPaAssessmentcriteria getSpPaAssessmentcriteria() {
		return spPaAssessmentcriteria;
	}
	public void setSpPaAssessmentcriteria(
			SpPaAssessmentcriteria spPaAssessmentcriteria) {
		this.spPaAssessmentcriteria = spPaAssessmentcriteria;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<SpPaAssessmentcriteria> list = this.spPaAssessmentcriteriaService.getAll(filter);
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer json = new JSONSerializer();
		buff.append(json.exclude(new String[] {}).serialize(list));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String multiDel() {
		String[] ids = this.getRequest().getParameterValues("ids");
		if(ids != null) {
			for(String id : ids) {
				SpPaAssessmentcriteria hpa = this.spPaAssessmentcriteriaService.get(new Long(id));
				hpa.setPublishStatus(new Integer("2"));//置为已删除状态
				this.spPaAssessmentcriteriaService.save(hpa);
			}
		}
		
		return "success";
	}
	
	public String get() {
		this.spPaAssessmentcriteria = (SpPaAssessmentcriteria)this.spPaAssessmentcriteriaService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.spPaAssessmentcriteria));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	public String save() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		//新建一个HrPaAssessmentcriteria并为其赋值
		SpPaAssessmentcriteria hpa = new SpPaAssessmentcriteria();
		if(this.spPaAssessmentcriteria.getPublishStatus() == 0) {//保存草稿
			//判断是新增还是修改
			if(this.spPaAssessmentcriteria.getId() == 0) {//新增
				//首先判断关键字是否重复
				if(this.spPaAssessmentcriteriaService.checkKey(this.spPaAssessmentcriteria.getAcKey())) {
					this.jsonString = "{msg:'关键字已存在，请重新输入！',failure:true}";
					return "success";
				}
				hpa.setAcKey(this.spPaAssessmentcriteria.getAcKey());
				hpa.setAcName(this.spPaAssessmentcriteria.getAcName());
				hpa.setAcDesc(this.spPaAssessmentcriteria.getAcDesc());
				if(this.spPaAssessmentcriteria.getIsSalesAC() == 1) {
					hpa.setIsSalesAC(1);
				} else {
					hpa.setIsSalesAC(0);
				}
				hpa.setCreateDate(currentDate);
				hpa.setCreatePerson(currentUser.getUserId());
				hpa.setModifyDate(currentDate);
				hpa.setModifyPerson(currentUser.getUserId());
				hpa.setFromAc(new Long(0));
				hpa.setPublishStatus(this.spPaAssessmentcriteria.getPublishStatus());
				this.spPaAssessmentcriteriaService.save(hpa);
			} else {//修改
				hpa = this.spPaAssessmentcriteriaService.get(this.spPaAssessmentcriteria.getId());
				if(hpa.getPublishStatus() == 3) {
					SpPaAssessmentcriteria hpaCopy = new SpPaAssessmentcriteria();
					hpaCopy.setAcKey(this.spPaAssessmentcriteria.getAcKey());
					hpaCopy.setAcName(this.spPaAssessmentcriteria.getAcName());
					hpaCopy.setAcDesc(this.spPaAssessmentcriteria.getAcDesc());
					if(this.spPaAssessmentcriteria.getIsSalesAC() == 1) {
						hpaCopy.setIsSalesAC(1);
					} else {
						hpaCopy.setIsSalesAC(0);
					}
					hpaCopy.setCreateDate(currentDate);
					hpaCopy.setCreatePerson(currentUser.getUserId());
					hpaCopy.setModifyDate(currentDate);
					hpaCopy.setModifyPerson(currentUser.getUserId());
					hpaCopy.setFromAc(this.spPaAssessmentcriteria.getId());
					hpaCopy.setPublishStatus(this.spPaAssessmentcriteria.getPublishStatus());
					this.spPaAssessmentcriteriaService.save(hpaCopy);
				} else {
					hpa.setAcKey(this.spPaAssessmentcriteria.getAcKey());
					hpa.setAcName(this.spPaAssessmentcriteria.getAcName());
					hpa.setAcDesc(this.spPaAssessmentcriteria.getAcDesc());
					if(this.spPaAssessmentcriteria.getIsSalesAC() == 1) {
						hpa.setIsSalesAC(1);
					} else {
						hpa.setIsSalesAC(0);
					}
					hpa.setModifyDate(currentDate);
					hpa.setModifyPerson(currentUser.getUserId());
					hpa.setFromAc(this.spPaAssessmentcriteria.getFromAc());
					hpa.setPublishStatus(this.spPaAssessmentcriteria.getPublishStatus());
					this.spPaAssessmentcriteriaService.save(hpa);
				}
			}
		} else if(this.spPaAssessmentcriteria.getPublishStatus() == 3) {
			//判断是新增还是修改
			if(this.spPaAssessmentcriteria.getId() == 0) {
				//首先判断关键字是否重复
				if(this.spPaAssessmentcriteriaService.checkKey(this.spPaAssessmentcriteria.getAcKey())) {
					this.jsonString = "{msg:'关键字已存在，请重新输入！',failure:true}";
					return "success";
				}
				hpa.setAcKey(this.spPaAssessmentcriteria.getAcKey());
				hpa.setAcName(this.spPaAssessmentcriteria.getAcName());
				hpa.setAcDesc(this.spPaAssessmentcriteria.getAcDesc());
				if(this.spPaAssessmentcriteria.getIsSalesAC() == 1) {
					hpa.setIsSalesAC(1);
				} else {
					hpa.setIsSalesAC(0);
				}
				hpa.setCreateDate(currentDate);
				hpa.setCreatePerson(currentUser.getUserId());
				hpa.setModifyDate(currentDate);
				hpa.setModifyPerson(currentUser.getUserId());
				hpa.setFromAc(new Long(0));
				hpa.setPublishStatus(this.spPaAssessmentcriteria.getPublishStatus());
				this.spPaAssessmentcriteriaService.save(hpa);
			} else {
				hpa = this.spPaAssessmentcriteriaService.get(this.spPaAssessmentcriteria.getId());
				if(hpa.getFromAc() == 0) {
					hpa.setAcKey(this.spPaAssessmentcriteria.getAcKey());
					hpa.setAcName(this.spPaAssessmentcriteria.getAcName());
					hpa.setAcDesc(this.spPaAssessmentcriteria.getAcDesc());
					if(this.spPaAssessmentcriteria.getIsSalesAC() == 1) {
						hpa.setIsSalesAC(1);
					} else {
						hpa.setIsSalesAC(0);
					}
					hpa.setModifyDate(currentDate);
					hpa.setModifyPerson(currentUser.getUserId());
					hpa.setFromAc(new Long(0));
					hpa.setPublishStatus(this.spPaAssessmentcriteria.getPublishStatus());
					this.spPaAssessmentcriteriaService.save(hpa);
				} else {
					SpPaAssessmentcriteria hpaOld = this.spPaAssessmentcriteriaService.get(hpa.getFromAc());
					hpaOld.setAcKey(this.spPaAssessmentcriteria.getAcKey());
					hpaOld.setAcName(this.spPaAssessmentcriteria.getAcName());
					hpaOld.setAcDesc(this.spPaAssessmentcriteria.getAcDesc());
					if(this.spPaAssessmentcriteria.getIsSalesAC() == 1) {
						hpaOld.setIsSalesAC(1);
					} else {
						hpaOld.setIsSalesAC(0);
					}
					hpaOld.setModifyDate(currentDate);
					hpaOld.setModifyPerson(currentUser.getUserId());
					hpaOld.setFromAc(new Long(0));
					hpaOld.setPublishStatus(this.spPaAssessmentcriteria.getPublishStatus());
					this.spPaAssessmentcriteriaService.save(hpaOld);
					this.spPaAssessmentcriteriaService.remove(hpa);
				}
			}
		} else {
			
		}
		
		this.jsonString = new String("{success:true}");
		
		return "success";
	}
	
	public String multiAudit() {
		String[] ids = this.getRequest().getParameterValues("ids");
		if(ids != null) {
			for(String id : ids) {
				SpPaAssessmentcriteria hpa = this.spPaAssessmentcriteriaService.get(new Long(id));
				hpa.setPublishStatus(new Integer("3"));
				this.spPaAssessmentcriteriaService.save(hpa);
			}
		}
		
		return "success";
	}
	
	public String load() {
		Map<String, String> map = this.spPaAssessmentcriteriaService.getKeyAndName();
		
		StringBuffer buff = new StringBuffer("[");
		for(Map.Entry<String, String> entry : map.entrySet()) {
			buff.append("['").append(entry.getKey()).append("',")
					.append("'").append(entry.getValue()).append("'],");
		}
		if(!map.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		this.jsonString = buff.toString();
		
		return "success";
	}
}
