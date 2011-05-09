package com.zsgj.itil.account.service;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.zsgj.itil.account.dao.PersonnelScopeSynDao;

/**
 * 
 * @Class Name PersonnelScopeInfoSynJob
 * @Author lee
 * @Create In Jun 25, 2010
 * @deprecated UserInfoSynJob已经包含了此类的功能
 */
public class PersonnelScopeInfoSynJob extends QuartzJobBean{

	private PersonnelScopeSynDao personnelScopeSynDao;
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		System.out.println("synchronize dws 人事子范围码表数据 ");
//		personnelScopeSynDao.saveOrUpdatePersonnelScope();
//		personnelScopeSynDao.updateUserInfo();
	}
	public PersonnelScopeSynDao getPersonnelScopeSynDao() {
		return personnelScopeSynDao;
	}
	public void setPersonnelScopeSynDao(PersonnelScopeSynDao personnelScopeSynDao) {
		this.personnelScopeSynDao = personnelScopeSynDao;
	}
	
	

}
