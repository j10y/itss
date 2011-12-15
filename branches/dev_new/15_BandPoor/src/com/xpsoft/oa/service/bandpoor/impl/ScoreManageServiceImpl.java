package com.xpsoft.oa.service.bandpoor.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.oa.dao.bandpoor.ScoreManageDao;
import com.xpsoft.oa.model.bandpoor.InfoPoor;
import com.xpsoft.oa.model.bandpoor.PictureOrdoc;
import com.xpsoft.oa.model.system.FileAttach;
import com.xpsoft.oa.service.bandpoor.ScoreManageService;

public class ScoreManageServiceImpl extends BaseServiceImpl<InfoPoor> implements ScoreManageService{
	private ScoreManageDao dao;
	
	public ScoreManageServiceImpl(ScoreManageDao dao) {
		super(dao);
		this.dao = dao;
	}

	public boolean validateUnique(Map params) {
		// TODO Auto-generated method stub
		return false;
	}

	public void saveInfoPoor(List<InfoPoor> list) {
		// TODO Auto-generated method stub
		for(InfoPoor infoPoor:list){
			infoPoor.setMainPriceId(null);
			infoPoor.setMainPriceName(infoPoor.getMainPriceStart()+"~"+infoPoor.getMainPriceEnd());
			if(infoPoor.getId()==null){
				infoPoor.setCreatDate(new Date());
				infoPoor.setCreatUser(ContextUtil.getCurrentUser());
				infoPoor.setInfoStatus(InfoPoor.STATUS_CREATE);
				infoPoor.setInfoType(InfoPoor.TYPE_SCORE);
				infoPoor.setInfoSource(InfoPoor.SCOUCE_DIRCOLLECTION);
				infoPoor=this.save(infoPoor);
			}else{
				//InfoPoor ifp=scoreManageService.get(infoPoor.getId());
				infoPoor.setCreatDate(new Date());
				infoPoor.setCreatUser(ContextUtil.getCurrentUser());
				infoPoor.setInfoStatus(InfoPoor.STATUS_MODIFY);
				infoPoor.setInfoType(InfoPoor.TYPE_SCORE);
				infoPoor.setInfoSource(InfoPoor.SCOUCE_DIRCOLLECTION);
				infoPoor=this.save(infoPoor);
			}
		}
	}
}
