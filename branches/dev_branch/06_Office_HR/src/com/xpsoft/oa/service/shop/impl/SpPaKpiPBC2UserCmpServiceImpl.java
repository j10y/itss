package com.xpsoft.oa.service.shop.impl;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.oa.dao.shop.SpPaKpiPBC2UserCmpDao;
import com.xpsoft.oa.model.shop.SpPaAuthorizepbc;
import com.xpsoft.oa.model.shop.SpPaAuthpbccitem;
import com.xpsoft.oa.model.shop.SpPaKpiPBC2User;
import com.xpsoft.oa.model.shop.SpPaKpiPBC2UserCmp;
import com.xpsoft.oa.model.shop.SpPaKpiitem2user;
import com.xpsoft.oa.model.shop.SpPaKpiitem2userCmp;
import com.xpsoft.oa.model.shop.SpPaPerformanceindex;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.shop.SpPaAuthorizepbcService;
import com.xpsoft.oa.service.shop.SpPaAuthpbccitemService;
import com.xpsoft.oa.service.shop.SpPaKpiPBC2UserCmpService;
import com.xpsoft.oa.service.shop.SpPaKpiPBC2UserService;
import com.xpsoft.oa.service.shop.SpPaKpiitem2userCmpService;
import com.xpsoft.oa.service.shop.SpPaKpiitem2userService;
import com.xpsoft.oa.service.shop.SpPaPerformanceindexService;

public class SpPaKpiPBC2UserCmpServiceImpl extends BaseServiceImpl<SpPaKpiPBC2UserCmp>
		implements SpPaKpiPBC2UserCmpService {
	private SpPaKpiPBC2UserCmpDao dao;
	
	public SpPaKpiPBC2UserCmpServiceImpl(SpPaKpiPBC2UserCmpDao dao) {
		super(dao);
		this.dao = dao;
	}
	public boolean saveSpPaKpiPBC2UserCmp(String kpipbcids){
		String[] kpipbcidss=kpipbcids.split(",");
		for(String kpipbcid:kpipbcidss){
			this.saveSpPaKpiPBC2UserCmp(Long.parseLong(kpipbcid));
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
	public boolean saveSpPaKpiPBC2UserCmp(Long kpipbcid){
		SpPaKpiPBC2UserService spPaKpiPBC2UserService=(SpPaKpiPBC2UserService) AppUtil.getBean("spPaKpiPBC2UserService");
		SpPaKpiitem2userService spPaKpiitem2userService=(SpPaKpiitem2userService) AppUtil.getBean("spPaKpiitem2userService");
		SpPaKpiitem2userCmpService spPaKpiitem2userCmpService=(SpPaKpiitem2userCmpService) AppUtil.getBean("spPaKpiitem2userCmpService");
		SpPaAuthorizepbcService spPaAuthorizepbcService=(SpPaAuthorizepbcService) AppUtil.getBean("spPaAuthorizepbcService");
		SpPaAuthpbccitemService spPaAuthpbccitemService= (SpPaAuthpbccitemService) AppUtil.getBean("spPaAuthpbccitemService");
		SpPaKpiPBC2User spPaKpiPBC2User=spPaKpiPBC2UserService.get(kpipbcid);
		Map map=new HashMap();
		map.put("Q_pbc2User.id_L_EQ", spPaKpiPBC2User.getId()+"");
		QueryFilter filter=new QueryFilter(map);
		List<SpPaKpiitem2user> list=spPaKpiitem2userService.getAll(filter);
		SpPaKpiPBC2UserCmp spPaKpiPBC2UserCmp=new SpPaKpiPBC2UserCmp();
		spPaKpiPBC2UserCmp.setId(spPaKpiPBC2User.getId());
		spPaKpiPBC2UserCmp.setPbcName(spPaKpiPBC2User.getPbcName());
		spPaKpiPBC2UserCmp.setPublishStatus(spPaKpiPBC2User.getPublishStatus());
		spPaKpiPBC2UserCmp.setFromPBC(spPaKpiPBC2User.getFromPBC());
		spPaKpiPBC2UserCmp.setBelongUser(spPaKpiPBC2User.getBelongUser());
		spPaKpiPBC2UserCmp.setFrequency(spPaKpiPBC2User.getFrequency());
		spPaKpiPBC2UserCmp.setCreatePerson(spPaKpiPBC2User.getCreatePerson());
		spPaKpiPBC2UserCmp.setCreateDate(spPaKpiPBC2User.getCreateDate());
		spPaKpiPBC2UserCmp.setTotalScore(spPaKpiPBC2User.getTotalScore());
		spPaKpiPBC2UserCmp.setModifyPerson(spPaKpiPBC2User.getModifyPerson());
		spPaKpiPBC2UserCmp.setModifyDate(new Date());
		spPaKpiPBC2UserCmp.setCoefficient(spPaKpiPBC2User.getCoefficient());
		spPaKpiPBC2UserCmp=this.save(spPaKpiPBC2UserCmp);
		for(SpPaKpiitem2user hpu:list){
			SpPaKpiitem2userCmp spPaKpiitem2userCmp=new SpPaKpiitem2userCmp();
			spPaKpiitem2userCmp.setId(hpu.getId());
			spPaKpiitem2userCmp.setPbc2User(spPaKpiPBC2UserCmp);
			spPaKpiitem2userCmp.setPiId(hpu.getPiId());
			spPaKpiitem2userCmp.setWeight(hpu.getWeight());
			spPaKpiitem2userCmp.setResult(hpu.getResult());
			spPaKpiitem2userCmp.setCoefficient(hpu.getCoefficient());
			spPaKpiitem2userCmpService.save(spPaKpiitem2userCmp);
			Map aimap=new HashMap();
			aimap.put("Q_akpiItem2uId_L_EQ", hpu.getId()+"");
			QueryFilter aifilter=new QueryFilter(aimap);
			List<SpPaAuthpbccitem> ailist=spPaAuthpbccitemService.getAll(aifilter);
			for(SpPaAuthpbccitem ai:ailist){
				spPaAuthpbccitemService.remove(ai);
			}
			spPaKpiitem2userService.remove(hpu);
		}
		Map apbcmap=new HashMap();
		apbcmap.put("Q_userPbc.id_L_EQ", spPaKpiPBC2User.getId()+"");
		QueryFilter apbcfilter=new QueryFilter(apbcmap);
		List<SpPaAuthorizepbc> apbclist=spPaAuthorizepbcService.getAll(apbcfilter);
		for(SpPaAuthorizepbc apbc:apbclist){
			spPaAuthorizepbcService.remove(apbc);
		}
		spPaKpiPBC2UserService.remove(spPaKpiPBC2User);		
		return true;
	}
	//计算个人的kpi模板得分
	public String countScoreForKpiPbcUser(Long kpipbcid){
		SpPaKpiPBC2UserService spPaKpiPBC2UserService=(SpPaKpiPBC2UserService) AppUtil.getBean("spPaKpiPBC2UserService");
		SpPaKpiPBC2User spPaKpiPBC2User=spPaKpiPBC2UserService.get(kpipbcid);
		SpPaKpiitem2userService spPaKpiitem2userService=(SpPaKpiitem2userService) AppUtil.getBean("spPaKpiitem2userService");
		SpPaPerformanceindexService spPaPerformanceindexService= (SpPaPerformanceindexService) AppUtil.getBean("spPaPerformanceindexService");
		Map map=new HashMap();
		map.put("Q_pbc2User.id_L_EQ", spPaKpiPBC2User.getId()+"");
		QueryFilter filter=new QueryFilter(map);
		List<SpPaKpiitem2user> list=spPaKpiitem2userService.getAll(filter);
		float totalScore=0;
		Double totalCoefficient=0d;
		boolean isOnlyNegative=false;
		Double baseScore=null;
		Double baseCoefficient=0d;
		int totalCount = 0;
		for(SpPaKpiitem2user spPaKpiitem2user:list){
			SpPaPerformanceindex spPaPerformanceindex=spPaPerformanceindexService.get(spPaKpiitem2user.getPiId());
			if(spPaPerformanceindex.getPaIsOnlyNegative()==1&&spPaKpiitem2user.getResult()<=spPaPerformanceindex.getBaseScore()){
				//此处处理是否唯一否决条件为是的时候，直接给出得分，跳出循环
				if(baseScore==null||baseScore>spPaPerformanceindex.getFinalScore()){
					baseScore=spPaPerformanceindex.getFinalScore();
					baseCoefficient=spPaPerformanceindex.getFinalCoefficient();
					isOnlyNegative=true;
				}
			}else{
				totalScore+=spPaKpiitem2user.getResult();
				totalCoefficient+=spPaKpiitem2user.getCoefficient();
				totalCount++;
			}
		}
		if(isOnlyNegative){
			totalScore=baseScore.floatValue();
			totalCoefficient=baseCoefficient;
		} else {
			if(totalCount > 0) {
				totalScore = totalScore / totalCount;
				totalCoefficient = totalCoefficient / totalCount;
			} else {
				totalScore = 0.0f;
				totalCoefficient = 0.0d;
			}
		}
		DecimalFormat myformat = new DecimalFormat();
		myformat.applyPattern("###.00");
		totalScore=Float.parseFloat(myformat.format(totalScore));
		spPaKpiPBC2User.setTotalScore(totalScore);
		spPaKpiPBC2User.setCoefficient(Double.parseDouble(myformat.format(totalCoefficient)));
		try {
			spPaKpiPBC2UserService.save(spPaKpiPBC2User);
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			return "false,"+totalScore;
		}
		return "true,"+totalScore;
	}
	
	public List isKpiItemScoreForUser(String userid,String depid,String pbc2userid){
		String translateText="";
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(
					 AppUtil.getPropertity("job.server.url")));
			call.setOperationName("isKpiItemScoreForUser");
			AppUser appuser=ContextUtil.getCurrentUser();
			 translateText = (String) call
					.invoke(new Object[] {userid,depid,pbc2userid});
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		List list=new ArrayList();
		if(translateText!=null&&translateText.length()>0){
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
		}
		return list;
	}
	public String saveKpiItemScoreForUser(String userid,String depid,String pbc2userid){
		String translateText="";
		try {
			Service service = new Service();
			Call call = (Call) service.createCall();
			call.setTargetEndpointAddress(new java.net.URL(
					 AppUtil.getPropertity("job.server.url")));
			call.setOperationName("saveKpiItemScoreForUser");
			 translateText =call.invoke(new Object[] {userid,depid,pbc2userid}).toString();
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
			 translateText =call
			 	.invoke(new Object[] {userid,depid}).toString();
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
