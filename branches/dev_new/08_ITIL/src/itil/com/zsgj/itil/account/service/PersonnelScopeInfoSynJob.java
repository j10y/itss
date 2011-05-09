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
 * @deprecated UserInfoSynJob�Ѿ������˴���Ĺ���
 */
public class PersonnelScopeInfoSynJob extends QuartzJobBean{

	private PersonnelScopeSynDao personnelScopeSynDao;
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		System.out.println("synchronize dws �����ӷ�Χ������� ");
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
