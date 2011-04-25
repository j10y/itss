package com.digitalchina.itil.account.service;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.message.mail.service.MailSenderService;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.service.BaseService;
import com.digitalchina.info.framework.util.DateUtil;
import com.digitalchina.info.framework.util.PropertiesUtil;
import com.digitalchina.itil.account.entity.PersonFormalAccount;
import com.digitalchina.itil.account.entity.SpecialAccount;
import com.digitalchina.itil.event.entity.Event;

public class DeptAccountLockJob extends QuartzJobBean{
	
	protected static final Log log=LogFactory.getLog(DeptAccountLockJob.class);
	private AccountService accountService;
	private BaseService baseService;
    
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		String accountType="DeptMail";
		List<SpecialAccount> specialAccount=accountService.findSpecailAccountByType(accountType);
		for(SpecialAccount account:specialAccount){
			if(account.getIsConfrim()==null){
				account.setIfLocked(1);
				baseService.save(account);
			}
		}
	}
	
	public AccountService getAccountService() {
		return accountService;
	}

	public void setAccountService(AccountService accountService) {
		this.accountService = accountService;
	}

	public BaseService getBaseService() {
		return baseService;
	}

	public void setBaseService(BaseService baseService) {
		this.baseService = baseService;
	}

	public static Log getLog() {
		return log;
	}
	
	
	

}
