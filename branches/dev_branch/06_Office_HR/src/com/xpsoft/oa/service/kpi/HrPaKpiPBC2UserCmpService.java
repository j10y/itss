package com.xpsoft.oa.service.kpi;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.kpi.HrPaKpiPBC2UserCmp;

public interface HrPaKpiPBC2UserCmpService extends BaseService<HrPaKpiPBC2UserCmp> {
	
	public boolean saveHrPaKpiPBC2UserCmp(Long kpipbcid);
	public boolean saveHrPaKpiPBC2UserCmp(String kpipbcids);
	public boolean countScoreForKpiPbcUser(Long kpipbcid);
	public boolean countScoreForKpiPbcUser(String kpipbcids);
	
	public List isKpiItemScoreForUser(String userid,String depid);
	public String saveKpiItemScoreForUser(String userid,String depid);
	public String saveSalarDetail(String userid,String depid);
	
}
