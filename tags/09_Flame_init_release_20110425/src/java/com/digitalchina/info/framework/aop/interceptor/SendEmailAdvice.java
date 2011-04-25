package com.digitalchina.info.framework.aop.interceptor;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;

import com.digitalchina.info.framework.exception.ExceptionMessageFactory;
import com.digitalchina.info.framework.message.mail.service.MailSenderService;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.util.PropertiesUtil;

/**
 * AOP advice that sends confirmation email after order has been submitted
 * 
 * @author Dmitriy Kopylenko
 * @deprecated
 */
public class SendEmailAdvice implements AfterReturningAdvice, InitializingBean {

	private MailSenderService mailSenderService = null;

	private Log log = LogFactory.getLog("servicelog");

	public void afterPropertiesSet() throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.aop.AfterReturningAdvice#afterReturning(java.lang
	 * .Object, java.lang.reflect.Method, java.lang.Object[], java.lang.Object)
	 */
	public void afterReturning(Object returnValue, Method m, Object[] args,
			Object target) throws Throwable {
		Map map = (Map) returnValue;

		String appAddress = PropertiesUtil.getProperties("system.web.url",
				"http://10.1.180.167/b2b");

		String text = PropertiesUtil.getProperties("system.web.mailText", "您有一个消息");

		String subject = PropertiesUtil.getProperties("system.web.mailSubject", "您有一个消息");
		
		this.log.info("-----------Start build mail to sending----------");
		if (map.get("UserInfo") instanceof List) {
			try {

				List<UserInfo> userInfos = (List<UserInfo>) map.get("UserInfo");
				String[] to = new String[userInfos.size()];
				int i = 0;
				for (UserInfo userInfo : userInfos) {
					to[i++] = userInfo.getEmail();
					log.info("To User" + String.valueOf(i-1) + ": " + userInfo.getEmail());
				}
				String[] cc = new String[1];
				if (map.get("creator") instanceof UserInfo) {
					cc[0] = ((UserInfo) map.get("creator")).getEmail();
				} else if (map.get("creator") instanceof String) {
					if (((String) map.get("creator")).indexOf(",") != -1) {
						cc = ((String) map.get("creator")).split(",");
					} else {
						cc[0] = (String) map.get("creator");
					}
				}
				log.info("CC User" + ": "+ cc);
				
				if (text.indexOf("&") != -1) {
					String definationName = (String) map.get("definationName");
					String toNode = (String) map.get("toNodeName");
					text = text.replace("&", definationName + "--"
							+ toNode)
							+ appAddress;
				} else {
					text += appAddress;
				}
				log.info("mail context" + ": "+ text);
				this.getMailSenderService().sendSimplyMail(to, cc, null, subject, text);
				log.info(m.getName().toString() + "方法执行后发送邮件完毕!!\r\n");
			} catch (Exception e) {
				log.error(ExceptionMessageFactory
						.getExceptionMessage("10000310108"));
				log.error(e.getMessage());
			}
		} else {
			System.out.println("is not SolutionApply!");
			log.error("Cannot find email address to send mail!");
		}

		this.log.info("-----------end build mail to sending----------");
		return;
	}

	/**
	 * @Return the MailSenderService mailSenderService
	 */
	public MailSenderService getMailSenderService() {
		return mailSenderService;
	}

	/**
	 * @Param MailSenderService mailSenderService to set
	 */
	public void setMailSenderService(MailSenderService mailSenderService) {
		this.mailSenderService = mailSenderService;
	}

}
