package com.xpsoft.oa.action.bandpoor;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.bandpoor.Band;
import com.xpsoft.oa.model.bandpoor.BandChannel;
import com.xpsoft.oa.model.bandpoor.BandLevel;
import com.xpsoft.oa.model.bandpoor.BandPoor;
import com.xpsoft.oa.model.bandpoor.BandStyle;
import com.xpsoft.oa.model.bandpoor.BeElectedBandPoor;
import com.xpsoft.oa.model.bandpoor.BusinessArea;
import com.xpsoft.oa.model.bandpoor.Floor;
import com.xpsoft.oa.model.bandpoor.InfoPoor;
import com.xpsoft.oa.model.bandpoor.MainPrice;
import com.xpsoft.oa.model.bandpoor.PictureOrdoc;
import com.xpsoft.oa.model.bandpoor.ProClass;
import com.xpsoft.oa.model.bandpoor.SaleStore;
import com.xpsoft.oa.model.system.FileAttach;
import com.xpsoft.oa.service.bandpoor.BandChannelService;
import com.xpsoft.oa.service.bandpoor.BandLevelService;
import com.xpsoft.oa.service.bandpoor.BandPoorService;
import com.xpsoft.oa.service.bandpoor.BandService;
import com.xpsoft.oa.service.bandpoor.BandStyleService;
import com.xpsoft.oa.service.bandpoor.BeElectedBandPoorService;
import com.xpsoft.oa.service.bandpoor.BusinessAreaService;
import com.xpsoft.oa.service.bandpoor.FloorService;
import com.xpsoft.oa.service.bandpoor.MainPriceService;
import com.xpsoft.oa.service.bandpoor.PictureOrdocService;
import com.xpsoft.oa.service.bandpoor.ProClassService;
import com.xpsoft.oa.service.bandpoor.SaleStoreService;
import com.xpsoft.oa.service.bandpoor.ScoreManageService;
import com.xpsoft.oa.service.system.FileAttachService;

import flexjson.JSONSerializer;

public class ModifyScoreManageAction extends BaseAction{
	@Resource
	ScoreManageService scoreManageService;
	
	@Resource
	BandPoorService bandPoorService;
	
	@Resource
	ProClassService proClassService;
	
	
	@Resource
	private FileAttachService fileAttachService;
	
	@Resource
	private PictureOrdocService pictureOrdocService;
	
	
	@Resource
	private BeElectedBandPoorService beElectedBandPoorService;
	
	@Resource
	private BandLevelService bandLevelService;
	
	private InfoPoor infoPoor;
	public InfoPoor getInfoPoor() {
		return infoPoor;
	}

	public void setInfoPoor(InfoPoor infoPoor) {
		this.infoPoor = infoPoor;
	}
	public String multiDel() {
		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				InfoPoor ip=scoreManageService.get(new Long(id));
				Map map = new HashMap();
				map.put("Q_infoPoorId.id_L_EQ",  ip.getId()+"");
				QueryFilter filter = new QueryFilter(map);
				List<PictureOrdoc> list=pictureOrdocService.getAll(filter);
				for(PictureOrdoc po:list){
					po.setStatus(0);
					pictureOrdocService.save(po);
				}
				this.applyRecord(ip.getId(), InfoPoor.STATUS_DELETE,null,null);
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}
	
	/**
	 * 审批信息
	 * @return
	 */
	public String applyRecordForModify(){
		String[] ids = getRequest().getParameterValues("ids");
		String year=getRequest().getParameter("modfiyInfoPoor.year");
		String poorVersion=getRequest().getParameter("modfiyInfoPoor.poorVersion");
		if (ids != null) {
			for (String id : ids) {
				this.applyRecord(Long.parseLong(id), InfoPoor.STATUS_CREATE,year,poorVersion);
			}
		}
		this.jsonString = "{success:true}";
		return "success";
	}
	public Boolean applyRecord(Long infoPoorId,Integer flag,String year,String poorVersion){
		InfoPoor ip=scoreManageService.get(infoPoorId);
		Map map = new HashMap();
		map.put("Q_bandId.id_L_EQ",  ip.getBandId().getId()+"");
		map.put("Q_status_N_NEQ",  BeElectedBandPoor.STATUS_DELETE+"");
		QueryFilter filter = new QueryFilter(map);
		List beElectedBandPoorlist=beElectedBandPoorService.getAll(filter);
		if(flag==InfoPoor.STATUS_CREATE){
			BeElectedBandPoor beElectedBandPoor=new BeElectedBandPoor();
			if(beElectedBandPoorlist==null||beElectedBandPoorlist.size()==0){
				beElectedBandPoor.setBandId(ip.getBandId());
				beElectedBandPoor.setBandName(ip.getBandName());
				beElectedBandPoor.setCreatDate(new Date());
				beElectedBandPoor.setCreateUser(ContextUtil.getCurrentUser());
				beElectedBandPoor.setInfoType(BeElectedBandPoor.TYPE_SCORE);
				beElectedBandPoor.setStatus(BeElectedBandPoor.STATUS_CREATE);
				Set<InfoPoor> ips=beElectedBandPoor.getInfoPoors();
				ips.add(ip);
				beElectedBandPoor.setInfoPoors(ips);
				beElectedBandPoorService.save(beElectedBandPoor);
			}else{
				beElectedBandPoor=(BeElectedBandPoor) beElectedBandPoorlist.get(0);
				Set<InfoPoor> ips=beElectedBandPoor.getInfoPoors();
				ips.add(ip);
				beElectedBandPoor.setInfoPoors(ips);
				beElectedBandPoor.setModifyDate(new Date());
				beElectedBandPoor.setModifyUser(ContextUtil.getCurrentUser());
				beElectedBandPoorService.save(beElectedBandPoor);
			}
			ip.setInfoStatus(InfoPoor.STATUS_PASS);
			scoreManageService.save(ip);
			this.countScoreValueForModify(year, poorVersion, beElectedBandPoor);
		}else if(flag==InfoPoor.STATUS_DELETE){
			if(beElectedBandPoorlist!=null&&beElectedBandPoorlist.size()>0){
				BeElectedBandPoor beElectedBandPoor=(BeElectedBandPoor) beElectedBandPoorlist.get(0);
				beElectedBandPoor.setModifyDate(new Date());
				beElectedBandPoor.setModifyUser(ContextUtil.getCurrentUser());
				Set<InfoPoor> ips=beElectedBandPoor.getInfoPoors();
				Set<InfoPoor> bandips=new HashSet();
				for(InfoPoor inp:ips){
					if(inp.getId()==ip.getId()){
					}else{
						bandips.add(inp);
					}
				}
				beElectedBandPoor.setInfoPoors(bandips);
				beElectedBandPoorService.save(beElectedBandPoor);
			}
			ip.setInfoStatus(InfoPoor.STATUS_DELETE);
			scoreManageService.save(ip);
			this.countScoreValueForDelete(ip);
		}
		return true;
	}
	public String countScoreValueForModify(String year,String poorVersion,BeElectedBandPoor bbp){
			Map valmap=new HashMap();
			valmap.put("Q_year_N_EQ", year);
			valmap.put("Q_poorVersion_N_EQ", poorVersion);
			valmap.put("Q_bandId.id_L_EQ", bbp.getBandId().getId()+"");
			QueryFilter valfilter = new QueryFilter(valmap);
			valfilter.addFilter("Q_status_N_NEQ", BeElectedBandPoor.STATUS_DELETE+"");
			valfilter.addFilter("Q_infoType_N_EQ", BeElectedBandPoor.TYPE_SCORE+"");
			List<BandPoor> vallist = this.bandPoorService.getAll(valfilter);
			BandPoor bp=new BandPoor();
			if(vallist!=null&&vallist.size()>0){
				bp=vallist.get(0);
				bp.setBandName(bbp.getBandName());
				bp.setModifyDate(new Date());
				bp.setModifyUser(ContextUtil.getCurrentUser());
				bp.setBandScore(bbp.getBandScore());
				bp.setInfoType(BandPoor.TYPE_SCORE);
				Set<InfoPoor> infopoors=bbp.getInfoPoors();
				Set<InfoPoor> bandpoors=new HashSet();
				Double scoreall=0d;
				for(InfoPoor infopoor:infopoors){
					scoreall+=infopoor.getSaleStoreid().getStoreScore()!=null?infopoor.getSaleStoreid().getStoreScore():0;
					bandpoors.add(infopoor);
				}
				bp.setInfoPoors(bandpoors);
				bp.setBandRealScore(scoreall);
				
				Map levelmap=new HashMap();
				QueryFilter levelfilter = new QueryFilter(levelmap);
				levelfilter.addFilter("Q_startValue_DB_LE", scoreall+"");
				levelfilter.addFilter("Q_endValue_DB_GT", scoreall+"");
				List<BandLevel> levellist=bandLevelService.getAll(levelfilter);
				if(levellist.size()>0){
					BandLevel bandLevel=levellist.get(0);
					bp.setBandLevel(bandLevel);
				}
				if(scoreall>=bbp.getBandScore()){
					bp.setBandPoorStatus(BandPoor.BANDSTATUS_YYC);
				}else{
					bp.setBandPoorStatus(BandPoor.BANDSTATUS_BXC);
				}
			}else{
				bp.setBandId(bbp.getBandId());
				bp.setBandName(bbp.getBandName());
				bp.setCreatDate(new Date());
				bp.setCreateUser(ContextUtil.getCurrentUser());
				bp.setYear(Integer.parseInt(year));
				bp.setPoorVersion(Integer.parseInt(poorVersion));
				bp.setBandScore(bbp.getBandScore());
				bp.setInfoType(BandPoor.TYPE_SCORE);
				Set<InfoPoor> infopoors=bbp.getInfoPoors();
				Set<InfoPoor> bandpoors=new HashSet();
				Double scoreall=0d;
				for(InfoPoor infopoor:infopoors){
					scoreall+=infopoor.getSaleStoreid().getStoreScore()!=null?infopoor.getSaleStoreid().getStoreScore():0;
					bandpoors.add(infopoor);
				}
				bp.setInfoPoors(bandpoors);
				bp.setBandRealScore(scoreall);
				Map levelmap=new HashMap();
				QueryFilter levelfilter = new QueryFilter(levelmap);
				levelfilter.addFilter("Q_startValue_DB_LE", scoreall+"");
				levelfilter.addFilter("Q_endValue_DB_GT", scoreall+"");
				List<BandLevel> levellist=bandLevelService.getAll(levelfilter);
				if(levellist.size()>0){
					BandLevel bandLevel=levellist.get(0);
					bp.setBandLevel(bandLevel);
				}
				if(scoreall>=bbp.getBandScore()){
					bp.setBandPoorStatus(BandPoor.BANDSTATUS_YYC);
				}else{
					bp.setBandPoorStatus(BandPoor.BANDSTATUS_BXC);
				}
				bp.setStatus(BandPoor.STATUS_CREATE);
			}
			bandPoorService.save(bp);
		this.jsonString = "{success:true}";
		return "success";
	}
	public Boolean countScoreValueForDelete(InfoPoor ip){
			Map valmap=new HashMap();
			QueryFilter filter = new QueryFilter(valmap);
			filter.addFilter("Q_bandId.id_L_EQ", ip.getBandId().getId()+"");
			List<BandPoor> vallist = this.bandPoorService.getAll(filter);
			BandPoor bp=new BandPoor();
			if(vallist!=null&&vallist.size()>0){
				bp=vallist.get(0);
				bp.setModifyUser(ContextUtil.getCurrentUser());
				bp.setInfoType(BandPoor.TYPE_SCORE);
				Set<InfoPoor> infopoors=bp.getInfoPoors();
				Double scoreall=bp.getBandRealScore();
				Set<InfoPoor> bandips=new HashSet();
				for(InfoPoor infopoor:infopoors){
					if(infopoor.getId()==ip.getId()){
						scoreall=scoreall-(infopoor.getSaleStoreid().getStoreScore()!=null?infopoor.getSaleStoreid().getStoreScore():0d);
					}else{
						bandips.add(infopoor);
					}
				}
				bp.setInfoPoors(bandips);
				bp.setBandRealScore(scoreall);
				Map levelmap=new HashMap();
				QueryFilter levelfilter = new QueryFilter(levelmap);
				levelfilter.addFilter("Q_startValue_DB_LE", scoreall+"");
				levelfilter.addFilter("Q_endValue_DB_GT", scoreall+"");
				List<BandLevel> levellist=bandLevelService.getAll(levelfilter);
				if(levellist.size()>0){
					BandLevel bandLevel=levellist.get(0);
					bp.setBandLevel(bandLevel);
				}
				if(scoreall>=bp.getBandScore()){
					bp.setBandPoorStatus(BandPoor.BANDSTATUS_YYC);
				}else{
					bp.setBandPoorStatus(BandPoor.BANDSTATUS_BXC);
				}
			}
			bandPoorService.save(bp);		
		return true;
	}
}
