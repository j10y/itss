package com.xpsoft.oa.service.bandpoor.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.oa.dao.bandpoor.BandLevelDao;
import com.xpsoft.oa.dao.bandpoor.BandPoorDao;
import com.xpsoft.oa.dao.bandpoor.BeElectedBandPoorDao;
import com.xpsoft.oa.model.bandpoor.BandLevel;
import com.xpsoft.oa.model.bandpoor.BandPoor;
import com.xpsoft.oa.model.bandpoor.BeElectedBandPoor;
import com.xpsoft.oa.model.bandpoor.InfoPoor;
import com.xpsoft.oa.service.bandpoor.BeElectedBandPoorService;

public class BeElectedBandPoorServiceImpl extends BaseServiceImpl<BeElectedBandPoor>implements BeElectedBandPoorService {
	private BeElectedBandPoorDao dao;
	
	public BandPoorDao bandPoorDao;
	
	public BandLevelDao bandLevelDao;
	
	
	public BandPoorDao getBandPoorDao() {
		return bandPoorDao;
	}

	public void setBandPoorDao(BandPoorDao bandPoorDao) {
		this.bandPoorDao = bandPoorDao;
	}

	public BandLevelDao getBandLevelDao() {
		return bandLevelDao;
	}

	public void setBandLevelDao(BandLevelDao bandLevelDao) {
		this.bandLevelDao = bandLevelDao;
	}

	public BeElectedBandPoorServiceImpl(BeElectedBandPoorDao dao) {
		super(dao);
		this.dao = dao;
	}

	public void saveCountScoreValue(List<BeElectedBandPoor> list,String year,String poorVersion ) {
		// TODO Auto-generated method stub
		for(BeElectedBandPoor bbp:list){
			Map valmap=new HashMap();
			valmap.put("Q_year_N_EQ", year);
			valmap.put("Q_poorVersion_N_EQ", poorVersion);
			valmap.put("Q_bandId.id_L_EQ", bbp.getBandId().getId()+"");
			valmap.put("Q_proClassId.id_L_EQ", bbp.getProClassId().getId()+"");
			QueryFilter valfilter = new QueryFilter(valmap);
			valfilter.addFilter("Q_status_N_NEQ", BeElectedBandPoor.STATUS_DELETE+"");
			valfilter.addFilter("Q_infoType_N_EQ", BeElectedBandPoor.TYPE_SCORE+"");
			List<BandPoor> vallist = this.bandPoorDao.getAll(valfilter);
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
				levelfilter.addFilter("Q_proClassId.id_L_EQ", bbp.getProClassId().getId()+"");
				List<BandLevel> levellist=bandLevelDao.getAll(levelfilter);
				if(levellist.size()>0){
					BandLevel bandLevel=levellist.get(0);
					bp.setBandLevel(bandLevel);
				}
				if(bbp.getBandScore()!=null&&scoreall>=bbp.getBandScore()){
					bp.setBandPoorStatus(BandPoor.BANDSTATUS_YYC);
				}else{
					bp.setBandPoorStatus(BandPoor.BANDSTATUS_BXC);
				}
			}else{
				bp.setProClassId(bbp.getProClassId());
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
				levelfilter.addFilter("Q_proClassId.id_L_EQ", bbp.getProClassId().getId()+"");
				List<BandLevel> levellist=bandLevelDao.getAll(levelfilter);
				if(levellist.size()>0){
					BandLevel bandLevel=levellist.get(0);
					bp.setBandLevel(bandLevel);
				}
				if(bbp.getBandScore()!=null&&scoreall>=bbp.getBandScore()){
					bp.setBandPoorStatus(BandPoor.BANDSTATUS_YYC);
				}else{
					bp.setBandPoorStatus(BandPoor.BANDSTATUS_BXC);
				}
				bp.setStatus(BandPoor.STATUS_CREATE);
			}
			bandPoorDao.save(bp);
		}
	}	
}
