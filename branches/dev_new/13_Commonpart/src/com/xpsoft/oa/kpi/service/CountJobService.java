package com.xpsoft.oa.kpi.service;

public interface CountJobService {
	public boolean saveKpiItemScoreForUser();
	
	public boolean saveSalarDetail();
	
	public boolean saveKpiItemScoreForUser(String userid,String depid,String pbc2userid);
	
	public boolean saveSalarDetail(String userid,String depid);
	
	public String isKpiItemScoreForUser(String userid,String depid,String pbc2userid);
	
}
