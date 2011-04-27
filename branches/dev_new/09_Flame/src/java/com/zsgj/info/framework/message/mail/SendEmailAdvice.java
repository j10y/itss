package com.zsgj.info.framework.message.mail;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import com.zsgj.info.framework.message.mail.base.BaseMail;

/**
 * 邮件发送  
 * @Class Name SendEmailAdvice
 * @Author zhangpeng
 * @Create In Mar 6, 2008
 */
public class SendEmailAdvice extends BaseMail implements AfterReturningAdvice, InitializingBean {

	private MailSender mailSender;


	public void setMailSender(MailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void afterPropertiesSet() throws Exception {
		if (this.mailSender == null) {
			throw new IllegalStateException("mailSender is required");
		}
	}

	public void afterReturning(Object returnValue, Method m, Object[] args, Object target) throws Throwable {
		
		Long processID = new Long(String.valueOf(args[0]));
		
		SimpleMailMessage mailMessage = getMail(processID.longValue());
		logger.info("------------------" + m.getName() + "执行并发送邮件-------------------------\n");
		
		try {
			this.mailSender.send(mailMessage);
			writeLog(mailMessage);
			logger.info("------------------" + m.getName() + "发送邮件完毕-------------------\n");
		}
		catch (MailException ex) {
			logger.error(ex.getMessage());
			logger.info("------------------" + m.getName() + "发送邮件失败-------------------\n");
		}
	}

}
