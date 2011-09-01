package com.xpsoft.oa.service.kpi.impl;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.google.gson.Gson;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.oa.dao.kpi.HrPaKpiPBC2UserCmpDao;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserCmpService;
import com.xpsoft.oa.service.kpi.HrPaKpiPBC2UserService;
import com.xpsoft.oa.service.kpi.HrPaKpiitem2userCmpService;
import com.xpsoft.oa.service.kpi.HrPaKpiitem2userService;
import com.xpsoft.oa.service.kpi.HrPaPerformanceindexService;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2User;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2UserCmp;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2user;
import com.xpsoft.oa.model.kpi.HrPaKpiitem2userCmp;
import com.xpsoft.oa.model.kpi.HrPaPerformanceindex;
import com.xpsoft.oa.model.system.AppUser;

public class HrPaKpiPBC2UserCmpServiceImpl extends BaseServiceImpl<HrPaKpiPBC2UserCmp>
		implements HrPaKpiPBC2UserCmpService {
	private HrPaKpiPBC2UserCmpDao dao;
	
	public HrPaKpiPBC2UserCmpServiceImpl(HrPaKpiPBC2UserCmpDao dao) {
		super(dao);
		this.dao = dao;
	}
	public boolean saveHrPaKpiPBC2UserCmp(String kpipbcids){
		String[] kpipbcidss=kpipbcids.split(",");
		for(String kpipbcid:kpipbcidss){
			this.saveHrPaKpiPBC2UserCmp(Long.parseLong(kpipbcid));
		}
		return true;
	}
	
	public boolean countScoreForKpiPbcUser(String kpipbcids){
		String[] kpipbcidss=kpipbcids.split(",");
		for(String kpipbcid:kpipbcidss){
			this.countScoreForKpiPbcUser(Long.parseLong(kpipbcid));
		}
		return true;
	}
	//当用户的kpi模板审核通过之后，转入完成的kpi模板之中，并删除原来的模板
	public boolean saveHrPaKpiPBC2UserCmp(Long kpipbcid){
		HrPaKpiPBC2UserService hrPaKpiPBC2UserService=(HrPaKpiPBC2UserService) AppUtil.getBean("hrPaKpiPBC2UserService");
		HrPaKpiitem2userService hrPaKpiitem2userService=(HrPaKpiitem2userService) AppUtil.getBean("hrPaKpiitem2userService");
		HrPaKpiitem2userCmpService hrPaKpiitem2userCmpService=(HrPaKpiitem2userCmpService) AppUtil.getBean("hrPaKpiitem2userCmpService");
		HrPaKpiPBC2User hrPaKpiPBC2User=hrPaKpiPBC2UserService.get(kpipbcid);
		Map map=new HashMap();
		map.put("pbc2User.id", hrPaKpiPBC2User.getId());
		QueryFilter filter=new QueryFilter(map);
		List<HrPaKpiitem2user> list=hrPaKpiitem2userService.getAll(filter);
		HrPaKpiPBC2UserCmp hrPaKpiPBC2UserCmp=new HrPaKpiPBC2UserCmp();
		hrPaKpiPBC2UserCmp.setId(hrPaKpiPBC2User.getId());
		hrPaKpiPBC2UserCmp.setPbcName(hrPaKpiPBC2User.getPbcName());
		hrPaKpiPBC2UserCmp.setPublishStatus(hrPaKpiPBC2User.getPublishStatus());
		hrPaKpiPBC2UserCmp.setFromPBC(hrPaKpiPBC2User.getFromPBC());
		hrPaKpiPBC2UserCmp.setBelongUser(hrPaKpiPBC2User.getBelongUser());
		hrPaKpiPBC2UserCmp.setFrequency(hrPaKpiPBC2User.getFrequency());
		hrPaKpiPBC2UserCmp.setCreatePerson(hrPaKpiPBC2User.getCreatePerson());
		hrPaKpiPBC2UserCmp.setCreateDate(hrPaKpiPBC2User.getCreateDate());
		hrPaKpiPBC2UserCmp.setTotalScore(hrPaKpiPBC2User.getTotalScore());
		hrPaKpiPBC2UserCmp.setModifyPerson(hrPaKpiPBC2User.getModifyPerson());
		hrPaKpiPBC2UserCmp.setModifyDate(hrPaKpiPBC2User.getModifyDate());
		hrPaKpiPBC2UserCmp=this.save(hrPaKpiPBC2UserCmp);
		for(HrPaKpiitem2user hpu:list){
			HrPaKpiitem2userCmp hrPaKpiitem2userCmp=new HrPaKpiitem2userCmp();
			hrPaKpiitem2userCmp.setId(hpu.getId());
			hrPaKpiitem2userCmp.setPbc2User(hrPaKpiPBC2UserCmp);
			hrPaKpiitem2userCmp.setPiId(hpu.getPiId());
			hrPaKpiitem2userCmp.setWeight(hpu.getWeight());
			hrPaKpiitem2userCmp.setResult(hpu.getResult());
			hrPaKpiitem2userCmpService.save(hrPaKpiitem2userCmp);
			hrPaKpiitem2userService.remove(hpu);
		}
		hrPaKpiPBC2UserService.remove(hrPaKpiPBC2User);
		return true;
	}
	//计算个人的kpi模板得分
	public boolean countScoreForKpiPbcUser(Long kpipbcid){
		HrPaKpiPBC2UserService hrPaKpiPBC2UserService=(HrPaKpiPBC2UserService) AppUtil.getBean("hrPaKpiPBC2UserService");
		HrPaKpiPBC2User hrPaKpiPBC2User=hrPaKpiPBC2UserService.get(kpipbcid);
		HrPaKpiitem2userService hrPaKpiitem2userService=(HrPaKpiitem2userService) AppUtil.getBean("hrPaKpiitem2userService");
		HrPaPerformanceindexService hrPaPerformanceindexService= (HrPaPerformanceindexService) AppUtil.getBean("hrPaPerformanceindexService");
		Map map=new HashMap();
		map.put("pbc2User.id", hrPaKpiPBC2User.getId());
		QueryFilter filter=new QueryFilter(map);
		List<HrPaKpiitem2user> list=hrPaKpiitem2userService.getAll(filter);
		float totalScore=0;
		boolean isOnlyNegative=false;
		Double baseScore=null;
		for(HrPaKpiitem2user hrPaKpiitem2user:list){
			HrPaPerformanceindex hrPaPerformanceindex=hrPaPerformanceindexService.get(hrPaKpiitem2user.getPiId());
			if(hrPaPerformanceindex.getPaIsOnlyNegative()==1&&hrPaKpiitem2user.getResult()<=hrPaPerformanceindex.getBaseScore()){
				//此处处理是否唯一否决条件为是的时候，直接给出得分，跳出循环
				if(baseScore==null||baseScore>hrPaPerformanceindex.getFinalScore()){
					baseScore=hrPaPerformanceindex.getFinalScore();
					isOnlyNegative=true;
				}
			}else{
				totalScore+=hrPaKpiitem2user.getResult()*hrPaKpiitem2user.getWeight();
			}
		}
		if(isOnlyNegative){
			totalScore=baseScore.floatValue();
		}
		hrPaKpiPBC2User.setTotalScore(totalScore);
		hrPaKpiPBC2UserService.save(hrPaKpiPBC2User);
		return true;
	}
	
	public List isKpiItemScoreForUser(String userid,String depid){
		String translateText="";
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(
					 AppUtil.getPropertity("job.server.url")));
			call.setOperationName("isKpiItemScoreForUser");
			AppUser appuser=ContextUtil.getCurrentUser();
			 translateText = (String) call
					.invoke(new Object[] {userid,depid});
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		List list=new ArrayList();
		JSONObject json = JSONObject.fromObject(translateText);   
		JSONArray ja=(JSONArray) json.get("data");
		for(Object ob:ja){
			JSONObject jb=(JSONObject) ob;
			Map map=new HashMap();
			map.put("pbcName", jb.get("pbcName"));
			map.put("paName", jb.get("paName"));
			map.put("desc", jb.get("desc"));
			list.add(map);
		}
		return list;
	}
	public String saveKpiItemScoreForUser(String userid,String depid){
		String translateText="";
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(
					 AppUtil.getPropertity("job.server.url")));
			call.setOperationName("saveKpiItemScoreForUser");
			AppUser appuser=ContextUtil.getCurrentUser();
			 translateText = (String) call
			 .invoke(new Object[] {userid,depid});
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return translateText;
	}
	public String saveSalarDetail(String userid,String depid){
		String translateText="";
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(
					 AppUtil.getPropertity("job.server.url")));
			call.setOperationName("saveSalarDetail");
			AppUser appuser=ContextUtil.getCurrentUser();
			 translateText = (String) call
			 	.invoke(new Object[] {userid,depid});
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return translateText;
	}
}
