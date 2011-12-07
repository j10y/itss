package com.xpsoft.oa.action.bandpoor;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.Band;
import com.xpsoft.oa.model.bandpoor.InfoPoor;
import com.xpsoft.oa.service.bandpoor.BandService;
import com.xpsoft.oa.service.bandpoor.ScoreManageService;

public class ScoreManageAction extends BaseAction{
	@Resource
	ScoreManageService scoreManageService;
	@Resource
	private BandService bandService;
	
	private InfoPoor infoPoor;
	
	
	public InfoPoor getInfoPoor() {
		return infoPoor;
	}

	public void setInfoPoor(InfoPoor infoPoor) {
		this.infoPoor = infoPoor;
	}

	public BandService getBandService() {
		return bandService;
	}

	public void setBandService(BandService bandService) {
		this.bandService = bandService;
	}

	public ScoreManageService getScoreManageService() {
		return scoreManageService;
	}

	public void setScoreManageService(ScoreManageService scoreManageService) {
		this.scoreManageService = scoreManageService;
	}
	public String list() {
		QueryFilter filter = new QueryFilter(getRequest());
		List<InfoPoor> list = this.scoreManageService.getAll(filter);

		Type type = new TypeToken<List<InfoPoor>>() {
		}
		.getType();
		StringBuffer buff = new StringBuffer(
				"{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(
				",result:");
		Gson gson = new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		this.jsonString = buff.toString();
		return "success";
	}
	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				this.scoreManageService.remove(new Long(id));
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}
	public String save(){
		
		return "success";
	}
	public String get(){
		
		return "success";
	}
	
	public String getBands(){
		Map map = new HashMap();
		map.put("Q_flag_N_EQ",  Band.CREATE+"");
		QueryFilter filter = new QueryFilter(map);
		List<Band> list=bandService.getAll(filter);
		StringBuffer sb = new StringBuffer("[");
	       for (Band band : list) {
	         sb.append("['").append(band.getId()).append("','").append(band.getBandChName()+"/"+band.getBandEnName()).append("'],");
	       }
	       if (list.size() > 0) {
	         sb.deleteCharAt(sb.length() - 1);
	       }
	      sb.append("]");
	      setJsonString(sb.toString());
		return "success";
	}
}
