package com.xpsoft.oa.service.shop;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.shop.SpPaKpiPBC2UserCmp;

public interface SpPaKpiPBC2UserCmpService extends BaseService<SpPaKpiPBC2UserCmp> {
	
	public boolean saveSpPaKpiPBC2UserCmp(Long kpipbcid);
	public boolean saveSpPaKpiPBC2UserCmp(String kpipbcids);
	public String countScoreForKpiPbcUser(Long kpipbcid);
	public boolean countScoreForKpiPbcUser(String kpipbcids);
	
	public List isKpiItemScoreForUser(String userid,String depid,String pbc2userid);
	public String saveKpiItemScoreForUser(String userid,String depid,String pbc2userid);
	public String saveSalarDetail(String userid,String depid);
	
}
