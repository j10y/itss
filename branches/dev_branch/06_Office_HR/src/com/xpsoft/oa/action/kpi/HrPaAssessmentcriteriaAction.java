package com.xpsoft.oa.action.kpi;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.kpi.HrPaAssessmentcriteria;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.kpi.HrPaAssessmentcriteriaService;

import flexjson.JSONSerializer;

public class HrPaAssessmentcriteriaAction extends BaseAction{
	@Resource
	private HrPaAssessmentcriteriaService hrPaAssessmentcriteriaService;
	private HrPaAssessmentcriteria hrPaAssessmentcriteria;
	private long id;
	
	public HrPaAssessmentcriteriaService getHrPaAssessmentcriteriaService() {
		return hrPaAssessmentcriteriaService;
	}
	public void setHrPaAssessmentcriteriaService(
			HrPaAssessmentcriteriaService hrPaAssessmentcriteriaService) {
		this.hrPaAssessmentcriteriaService = hrPaAssessmentcriteriaService;
	}
	public HrPaAssessmentcriteria getHrPaAssessmentcriteria() {
		return hrPaAssessmentcriteria;
	}
	public void setHrPaAssessmentcriteria(
			HrPaAssessmentcriteria hrPaAssessmentcriteria) {
		this.hrPaAssessmentcriteria = hrPaAssessmentcriteria;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String list() {
		QueryFilter filter = new QueryFilter(this.getRequest());
		List<HrPaAssessmentcriteria> list = this.hrPaAssessmentcriteriaService.getAll(filter);
		
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
				HrPaAssessmentcriteria hpa = this.hrPaAssessmentcriteriaService.get(new Long(id));
				hpa.setPublishStatus(new Integer("2"));//置为已删除状态
				this.hrPaAssessmentcriteriaService.save(hpa);
			}
		}
		
		return "success";
	}
	
	public String get() {
		this.hrPaAssessmentcriteria = (HrPaAssessmentcriteria)this.hrPaAssessmentcriteriaService.get(this.id);
		
		JSONSerializer json = new JSONSerializer();
		StringBuffer buff = new StringBuffer("{success:true,data:");
		buff.append(json.exclude(new String[] {}).serialize(this.hrPaAssessmentcriteria));
		buff.append("}");
		this.jsonString = buff.toString();
		
		return "success";
	}
	
	/*
	public String save2() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		//首先判断关键字是否重复
		if(this.hrPaAssessmentcriteriaService.checkKey(this.hrPaAssessmentcriteria.getAcKey())) {
			this.jsonString = "{msg:'关键字已存在，请重新输入！',failure:true}";
			return "success";
		}
		//新建一个HrPaAssessmentcriteria并为其赋值
		//1. 新增和修改有区别的项
		HrPaAssessmentcriteria hpa = new HrPaAssessmentcriteria();
		if(this.hrPaAssessmentcriteria.getId() == 0) {//新增
			hpa.setCreateDate(currentDate);
			hpa.setCreatePerson(currentUser.getUserId());
			hpa.setFromAc(new Long(0));
		} else {//修改
			hpa = this.hrPaAssessmentcriteriaService.get(this.hrPaAssessmentcriteria.getId());
			//判断是已发布的还是草稿
			if(hpa.getPublishStatus() == 3) {//已发布
				HrPaAssessmentcriteria hpaCopy = new HrPaAssessmentcriteria();
				hpaCopy.setCreateDate(currentDate);
				hpaCopy.setCreatePerson(currentUser.getUserId());
				hpaCopy.setFromAc(fromAc)
			} else {
				
			}
			hpa.setId(this.hrPaAssessmentcriteria.getId());
			Date createDate = new Date(Long.parseLong(this.getRequest().getParameter("hrPaAssessmentcriteria.createDate")));
			hpa.setCreateDate(createDate);
			hpa.setCreatePerson(this.hrPaAssessmentcriteria.getCreatePerson());
		}
		//2. 新增和修改没有区别的项
		hpa.setAcName(this.hrPaAssessmentcriteria.getAcName());
		hpa.setAcKey(this.hrPaAssessmentcriteria.getAcKey());
		if(this.hrPaAssessmentcriteria.getIsSalesAC() == 1) {
			hpa.setIsSalesAC(1);
		} else {
			hpa.setIsSalesAC(0);
		}
		hpa.setAcDesc(this.hrPaAssessmentcriteria.getAcDesc());
		hpa.setPublishStatus(this.hrPaAssessmentcriteria.getPublishStatus());
		hpa.setModifyDate(currentDate);
		hpa.setModifyPerson(ContextUtil.getCurrentUserId());
		//将数据插入数据库
		this.hrPaAssessmentcriteriaService.save(hpa);
		
		this.jsonString = new String("{success:true}");
		
		return "success";
	}
	*/
	
	public String save() {
		Date currentDate = new Date();
		AppUser currentUser = ContextUtil.getCurrentUser();
		//新建一个HrPaAssessmentcriteria并为其赋值
		HrPaAssessmentcriteria hpa = new HrPaAssessmentcriteria();
		if(this.hrPaAssessmentcriteria.getPublishStatus() == 0) {//保存草稿
			//判断是新增还是修改
			if(this.hrPaAssessmentcriteria.getId() == 0) {//新增
				//首先判断关键字是否重复
				if(this.hrPaAssessmentcriteriaService.checkKey(this.hrPaAssessmentcriteria.getAcKey())) {
					this.jsonString = "{msg:'关键字已存在，请重新输入！',failure:true}";
					return "success";
				}
				hpa.setAcKey(this.hrPaAssessmentcriteria.getAcKey());
				hpa.setAcName(this.hrPaAssessmentcriteria.getAcName());
				hpa.setAcDesc(this.hrPaAssessmentcriteria.getAcDesc());
				if(this.hrPaAssessmentcriteria.getIsSalesAC() == 1) {
					hpa.setIsSalesAC(1);
				} else {
					hpa.setIsSalesAC(0);
				}
				hpa.setCreateDate(currentDate);
				hpa.setCreatePerson(currentUser.getUserId());
				hpa.setModifyDate(currentDate);
				hpa.setModifyPerson(currentUser.getUserId());
				hpa.setFromAc(new Long(0));
				hpa.setPublishStatus(this.hrPaAssessmentcriteria.getPublishStatus());
				this.hrPaAssessmentcriteriaService.save(hpa);
			} else {//修改
				hpa = this.hrPaAssessmentcriteriaService.get(this.hrPaAssessmentcriteria.getId());
				if(hpa.getPublishStatus() == 3) {
					HrPaAssessmentcriteria hpaCopy = new HrPaAssessmentcriteria();
					hpaCopy.setAcKey(this.hrPaAssessmentcriteria.getAcKey());
					hpaCopy.setAcName(this.hrPaAssessmentcriteria.getAcName());
					hpaCopy.setAcDesc(this.hrPaAssessmentcriteria.getAcDesc());
					if(this.hrPaAssessmentcriteria.getIsSalesAC() == 1) {
						hpaCopy.setIsSalesAC(1);
					} else {
						hpaCopy.setIsSalesAC(0);
					}
					hpaCopy.setCreateDate(currentDate);
					hpaCopy.setCreatePerson(currentUser.getUserId());
					hpaCopy.setModifyDate(currentDate);
					hpaCopy.setModifyPerson(currentUser.getUserId());
					hpaCopy.setFromAc(this.hrPaAssessmentcriteria.getId());
					hpaCopy.setPublishStatus(this.hrPaAssessmentcriteria.getPublishStatus());
					this.hrPaAssessmentcriteriaService.save(hpaCopy);
				} else {
					hpa.setAcKey(this.hrPaAssessmentcriteria.getAcKey());
					hpa.setAcName(this.hrPaAssessmentcriteria.getAcName());
					hpa.setAcDesc(this.hrPaAssessmentcriteria.getAcDesc());
					if(this.hrPaAssessmentcriteria.getIsSalesAC() == 1) {
						hpa.setIsSalesAC(1);
					} else {
						hpa.setIsSalesAC(0);
					}
					hpa.setModifyDate(currentDate);
					hpa.setModifyPerson(currentUser.getUserId());
					hpa.setFromAc(this.hrPaAssessmentcriteria.getFromAc());
					hpa.setPublishStatus(this.hrPaAssessmentcriteria.getPublishStatus());
					this.hrPaAssessmentcriteriaService.save(hpa);
				}
			}
		} else if(this.hrPaAssessmentcriteria.getPublishStatus() == 3) {
			//判断是新增还是修改
			if(this.hrPaAssessmentcriteria.getId() == 0) {
				//首先判断关键字是否重复
				if(this.hrPaAssessmentcriteriaService.checkKey(this.hrPaAssessmentcriteria.getAcKey())) {
					this.jsonString = "{msg:'关键字已存在，请重新输入！',failure:true}";
					return "success";
				}
				hpa.setAcKey(this.hrPaAssessmentcriteria.getAcKey());
				hpa.setAcName(this.hrPaAssessmentcriteria.getAcName());
				hpa.setAcDesc(this.hrPaAssessmentcriteria.getAcDesc());
				if(this.hrPaAssessmentcriteria.getIsSalesAC() == 1) {
					hpa.setIsSalesAC(1);
				} else {
					hpa.setIsSalesAC(0);
				}
				hpa.setCreateDate(currentDate);
				hpa.setCreatePerson(currentUser.getUserId());
				hpa.setModifyDate(currentDate);
				hpa.setModifyPerson(currentUser.getUserId());
				hpa.setFromAc(new Long(0));
				hpa.setPublishStatus(this.hrPaAssessmentcriteria.getPublishStatus());
				this.hrPaAssessmentcriteriaService.save(hpa);
			} else {
				hpa = this.hrPaAssessmentcriteriaService.get(this.hrPaAssessmentcriteria.getId());
				if(hpa.getFromAc() == 0) {
					hpa.setAcKey(this.hrPaAssessmentcriteria.getAcKey());
					hpa.setAcName(this.hrPaAssessmentcriteria.getAcName());
					hpa.setAcDesc(this.hrPaAssessmentcriteria.getAcDesc());
					if(this.hrPaAssessmentcriteria.getIsSalesAC() == 1) {
						hpa.setIsSalesAC(1);
					} else {
						hpa.setIsSalesAC(0);
					}
					hpa.setModifyDate(currentDate);
					hpa.setModifyPerson(currentUser.getUserId());
					hpa.setFromAc(new Long(0));
					hpa.setPublishStatus(this.hrPaAssessmentcriteria.getPublishStatus());
					this.hrPaAssessmentcriteriaService.save(hpa);
				} else {
					HrPaAssessmentcriteria hpaOld = this.hrPaAssessmentcriteriaService.get(hpa.getFromAc());
					hpaOld.setAcKey(this.hrPaAssessmentcriteria.getAcKey());
					hpaOld.setAcName(this.hrPaAssessmentcriteria.getAcName());
					hpaOld.setAcDesc(this.hrPaAssessmentcriteria.getAcDesc());
					if(this.hrPaAssessmentcriteria.getIsSalesAC() == 1) {
						hpaOld.setIsSalesAC(1);
					} else {
						hpaOld.setIsSalesAC(0);
					}
					hpaOld.setModifyDate(currentDate);
					hpaOld.setModifyPerson(currentUser.getUserId());
					hpaOld.setFromAc(new Long(0));
					hpaOld.setPublishStatus(this.hrPaAssessmentcriteria.getPublishStatus());
					this.hrPaAssessmentcriteriaService.save(hpaOld);
					this.hrPaAssessmentcriteriaService.remove(hpa);
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
				HrPaAssessmentcriteria hpa = this.hrPaAssessmentcriteriaService.get(new Long(id));
				hpa.setPublishStatus(new Integer("3"));
				this.hrPaAssessmentcriteriaService.save(hpa);
			}
		}
		
		return "success";
	}
	
	public String load() {
		Map<String, String> map = this.hrPaAssessmentcriteriaService.getKeyAndName();
		
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
