package com.zsgj.info.framework.message.mail.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.zsgj.info.appframework.metadata.entity.SystemFile;
import com.zsgj.info.framework.exception.ExceptionMessageFactory;
import com.zsgj.info.framework.message.mail.entity.MailMessage;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.dao.UserInfoDao;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.security.service.UserInfoService;
import com.zsgj.info.framework.service.BaseService;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.workflow.WorkflowConstants;
public class MailSenderServiceImpl extends BaseService implements
		MailSenderService {

	private static final String DEFAULT_MAIL_FROM = "zhangpengf@digitalchina.com";
	// 邮件发送对象
	private JavaMailSender mailSender;

	// 日至记录器
	private Log logger = LogFactory.getLog("servicelog");

	private UserInfoDao uid ;

	public UserInfoDao getUid() {
		return uid;
	}

	public void setUid(UserInfoDao uid) {
		this.uid = uid;
	}

	public MailSenderServiceImpl() {

	}

	/**
	 * 记录邮件发送日志
	 * 
	 * @Methods Name writeLog
	 * @Create In Dec 13, 2007 By 张鹏
	 * @param message
	 *            MailMessage实体对象
	 */
	private void writeLog(MailMessage message) {
		String msgTo = "";
		String msgCC = "";
		String msgBCC = "";
		if (message.getTo() != null) {
			for (int i = 0; i < message.getTo().length; i++) {
				msgTo = (i == 0 ? "[" + message.getTo()[i].trim() + ";"
						: (i == message.getTo().length ? msgTo
								+ message.getTo()[i].trim() + "]" : msgTo
								+ message.getTo()[i].trim() + ";"));
			}
			logger.info("发送邮件到:" + msgTo + "]");
		}

		if (message.getCc() != null) {
			for (int i = 0; i < message.getCc().length; i++) {
				msgCC = (i == 0 ? "[" + message.getCc()[i].trim() + ";"
						: (i == message.getCc().length ? msgCC
								+ message.getCc()[i].trim() + "]" : msgCC
								+ message.getCc()[i].trim() + ";"));
			}
			logger.info("抄送:" + msgCC + "]");
		}

		if (message.getBcc() != null) {
			for (int i = 0; i < message.getBcc().length; i++) {
				msgBCC = (i == 0 ? "[" + message.getBcc()[i].trim() + ";"
						: (i == message.getBcc().length ? msgBCC
								+ message.getBcc()[i].trim() + "]" : msgBCC
								+ message.getBcc()[i].trim() + ";"));
			}
			logger.info("密送:" + msgBCC + "]");
		}
		logger.info("邮件主题:" + message.getSubject());
		logger.info("邮件内容:" + message.getText());
	}

	/**
	 * 记录具体错误日志
	 * 
	 * @Methods Name writeErrorLog
	 * @Create In Dec 14, 2007 By 张鹏
	 * @param message
	 *            邮件实体
	 * @param systemError
	 *            系统错误信息
	 * @param NatifInfo
	 *            自定义错误信息
	 */
	private void writeErrorLog(MailMessage message, String systemError,
			String NatifInfo) {

		logger.error("***********" + NatifInfo + "Start ***********");
		logger.error("以下为邮件内容");
		writeLog(message);
		logger.error("系统错误信息");
		logger.error(systemError);
		logger.error("***********" + NatifInfo + "End ***********\r\n");
	}

	/**
	 * @Return the JavaMailSender mailSender
	 */
	public JavaMailSender getMailSender() {
		return mailSender;
	}

	/**
	 * @Param JavaMailSender mailSender to set
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.digitalchina.info.framework.message.mail.service.MailSenderService
	 * #sendSimplyMail(java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	public boolean sendSimplyMail(String to, String cc, String bcc,
			String Subject, String Context) {
		String mailFrom = PropertiesUtil.getProperties(
				"system.mail.sendmail.from", "zhangpengf@digitalchina.com");

		int errorLogFlag = Integer.valueOf(
				PropertiesUtil.getProperties("system.mail.sendmail.logflag",
						"1")).intValue();
		MailMessage message = new MailMessage();

		if (to.indexOf(";") != -1) {
			String[] ato = to.split(";");
			message.setTo(ato);
		} else {
			message.setTo(to);
		}

		if (cc != null && !"".equalsIgnoreCase(cc) && cc.indexOf(";") != -1) {
			String[] acc = cc.split(";");
			message.setCc(acc);
		} else if (cc != null && !"".equalsIgnoreCase(cc)) {
			message.setCc(cc);
		}

		if (bcc != null && !"".equalsIgnoreCase(bcc) && bcc.indexOf(";") != -1) {
			String[] abcc = bcc.split(";");
			message.setBcc(abcc);
		} else if (bcc != null && !"".equalsIgnoreCase(bcc)) {
			message.setBcc(bcc);
		}

		message.setFrom(mailFrom);
		message.setText(Context);
		message.setSubject(Subject);

		try {
			mailSender.send(message);
			writeLog(message);
			return true;
		} catch (MailException e) {
			if (errorLogFlag == 0) {
				logger.error(e.getMessage());
				return false;
			} else {
				writeErrorLog(message, e.getMessage(), ExceptionMessageFactory
						.getExceptionMessage("10000310104"));
				return false;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.digitalchina.info.framework.message.mail.service.MailSenderService
	 * #sendSimplyMail(java.lang.String, java.lang.String[], java.lang.String[],
	 * java.lang.String, java.lang.String)
	 */
	public boolean sendSimplyMail(String[] to, String[] cc, String[] bcc,
			String Subject, String Context) {
		String mailFrom = PropertiesUtil.getProperties(
				"system.mail.sendmail.from", "zhangpengf@digitalchina.com");

		int errorLogFlag = Integer.valueOf(
				PropertiesUtil.getProperties("system.mail.sendmail.logflag",
						"1")).intValue();
		MailMessage message = new MailMessage();
		message.setTo(to);
		message.setFrom(mailFrom);
		message.setText(Context);
		message.setSubject(Subject);
		if (cc != null && cc.length > 0) {
			message.setCc(cc);
		}
		if (bcc != null && bcc.length > 0) {
			message.setBcc(bcc);
		}
		try {
			mailSender.send(message);
			writeLog(message);
			return true;
		} catch (MailException e) {
			if (errorLogFlag == 0) {
				logger.error(e.getMessage());
				return false;
			} else {
				writeErrorLog(message, e.getMessage(), ExceptionMessageFactory
						.getExceptionMessage("10000310104"));
				return false;
			}
		}
	}

	/**
	 * 发送富文本邮件
	 * @Methods Name sendMimeMail
	 * @Create In 2009-3-26 By 张鹏
	 * @param to 可以使一个邮件地址,也可以是由";" 分割开的多个邮件地址
	 * @param cc 可以使一个邮件地址,也可以是由";" 分割开的多个邮件地址
	 * @param bcc 可以使一个邮件地址,也可以是由";" 分割开的多个邮件地址
	 * @param Subject
	 * @param context
	 * @param filePath 可以使一个附件位置,也可以是由";" 分割开的多个附件位置
	 * @return boolean
	 */
	public boolean sendMimeMail(final String to, final String cc,
			final String bcc, final String subject, final String context,
			final String filePath) {
		// TODO Auto-generated method stub
		final String mailFrom = PropertiesUtil.getProperties(
				"system.mail.sendmail.from", "zhangpengf@digitalchina.com");

		final int errorLogFlag = Integer.valueOf(
				PropertiesUtil.getProperties("system.mail.sendmail.logflag",
						"1")).intValue();
		MimeMessagePreparator mimeMail = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException {

				mimeMessage.setFrom(new InternetAddress(mailFrom));

				if (to.indexOf(";") != -1) {
					String[] ato = to.split(";");
					InternetAddress[] addTo = new InternetAddress[ato.length];
					for (int i = 0; i < ato.length; i++) {
						addTo[i] = new InternetAddress(ato[i]);
					}
					mimeMessage.setRecipients(Message.RecipientType.TO, addTo);
				} else {
					mimeMessage.setRecipient(Message.RecipientType.TO,
							new InternetAddress(to));
				}

				if (cc != null && !"".equalsIgnoreCase(cc)
						&& cc.indexOf(";") != -1) {
					String[] acc = cc.split(";");
					InternetAddress[] addCc = new InternetAddress[acc.length];
					for (int i = 0; i < acc.length; i++) {
						addCc[i] = new InternetAddress(acc[i]);
					}
					mimeMessage.setRecipients(Message.RecipientType.CC, addCc);
				} else if (cc != null && !"".equalsIgnoreCase(cc)) {
					mimeMessage.setRecipient(Message.RecipientType.CC,
							new InternetAddress(cc));
				}

				if (bcc != null && !"".equalsIgnoreCase(bcc)
						&& bcc.indexOf(";") != -1) {
					String[] abcc = bcc.split(";");
					InternetAddress[] addBcc = new InternetAddress[abcc.length];
					for (int i = 0; i < abcc.length; i++) {
						addBcc[i] = new InternetAddress(abcc[i]);
					}
					mimeMessage
							.setRecipients(Message.RecipientType.BCC, addBcc);
				} else if (bcc != null && !"".equalsIgnoreCase(bcc)) {
					mimeMessage.setRecipient(Message.RecipientType.BCC,
							new InternetAddress(bcc));
				}

				mimeMessage.setSubject(subject, getProperties(
						"system.characterEncoding", "gbk"));

				Multipart mp = new MimeMultipart();

				// 向Multipart添加正文

				MimeBodyPart content = new MimeBodyPart();
				content.setContent(context, "text/html;charset="
						+ getProperties("system.characterEncoding", "gbk"));

				// 向MimeMessage添加（Multipart代表正文）
				mp.addBodyPart(content);

				// 向Multipart添加附件
				if (filePath != null && !"".equalsIgnoreCase(filePath)
						&& filePath.indexOf(";") != -1) {
					String[] afilePath = filePath.split(";");
					for (int i = 0; i < afilePath.length; i++) {
						try {
							MimeBodyPart attachFile = new MimeBodyPart();
							String filename = afilePath[i];
							FileDataSource fds = new FileDataSource(filename);
							attachFile.setDataHandler(new DataHandler(fds));
							String file=getFileName(fds.getName());
							if(file.equals("")){
								file=fds.getName();
							}
							attachFile.setFileName(MimeUtility.encodeText(file,"gbk",null));
							mp.addBodyPart(attachFile);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

				} else if (filePath != null && !"".equalsIgnoreCase("filePath")
						&& filePath.indexOf(";") == -1) {
					try {
						MimeBodyPart attachFile = new MimeBodyPart();
						String filename = filePath;
						FileDataSource fds = new FileDataSource(filename);
						attachFile.setDataHandler(new DataHandler(fds));
						String file=getFileName(fds.getName());
						if(file.equals("")){
							file=fds.getName();
						}
						attachFile.setFileName(MimeUtility.encodeText(file,"gbk",null));
						mp.addBodyPart(attachFile);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				// 向Multipart添加MimeMessage
				mimeMessage.setContent(mp);
				mimeMessage.setSentDate(new Date());
			}
		};
		try {
			mailSender.send(mimeMail);
			// writeLog(mimeMail);
			return true;
		} catch (MailException e) {
			logger.error(e.getMessage());
			return false;
		}
	}
	
	/**
	 * 根据系统文件名获得真实文件名
	 * By gaowen 2009-10-14
	 * @param systemFileName
	 * @return String 真实文件名
	 */
	public String getFileName(String systemFileName){
		String fileName="";
		if(!systemFileName.equals("")&&systemFileName!=null){
			SystemFile syf=(SystemFile) super.findUnique(SystemFile.class, "systemFileName", systemFileName);
			if(syf!=null){
				fileName=syf.getFileName();
			}
		}
		return fileName;
		
	}

	/**
	 * 发送富文本邮件
	 * @Methods Name sendMimeMail
	 * @Create In 2009-3-26 By 张鹏
	 * @param to 邮件地址数组String[]
	 * @param cc 邮件地址数组String[]
	 * @param bcc 邮件地址数组String[]
	 * @param Subject 
	 * @param context
	 * @param filePath 附件位置String[]
	 * @return boolean
	 */
	public boolean sendMimeMail(final String[] to, final String[] cc,
			final String[] bcc, final String subject, final String context,
			final String[] filePath) {
		// TODO Auto-generated method stub
		final String mailFrom = PropertiesUtil.getProperties(
				"system.mail.sendmail.from", "zhangpengf@digitalchina.com");

		final int errorLogFlag = Integer.valueOf(
				PropertiesUtil.getProperties("system.mail.sendmail.logflag",
						"1")).intValue();
		MimeMessagePreparator mimeMail = new MimeMessagePreparator() {
			public void prepare(MimeMessage mimeMessage)
					throws MessagingException {

				mimeMessage.setFrom(new InternetAddress(mailFrom));

				if (to.length > 0) {
					InternetAddress[] addTo = new InternetAddress[to.length];
					for (int i = 0; i < to.length; i++) {
						addTo[i] = new InternetAddress(to[i]);
					}
					mimeMessage.setRecipients(Message.RecipientType.TO, addTo);
				}

				if (cc != null && cc.length > 0) {
					InternetAddress[] addCc = new InternetAddress[cc.length];
					for (int i = 0; i < cc.length; i++) {
						addCc[i] = new InternetAddress(cc[i]);
					}
					mimeMessage.setRecipients(Message.RecipientType.CC, addCc);
				}

				if (bcc != null && bcc.length > 0) {
					InternetAddress[] addBcc = new InternetAddress[bcc.length];
					for (int i = 0; i < bcc.length; i++) {
						addBcc[i] = new InternetAddress(bcc[i]);
					}
					mimeMessage
							.setRecipients(Message.RecipientType.BCC, addBcc);
				}

				mimeMessage.setSubject(subject, getProperties(
						"system.characterEncoding", "gbk"));

				Multipart mp = new MimeMultipart();

				// 向Multipart添加正文

				MimeBodyPart content = new MimeBodyPart();
				content.setContent(context, "text/html;charset="
						+ getProperties("system.characterEncoding", "gbk"));

				// 向MimeMessage添加（Multipart代表正文）
				mp.addBodyPart(content);

				// 向Multipart添加附件
				if (filePath != null) {
					for (int i = 0; i < filePath.length; i++) {
						MimeBodyPart attachFile = new MimeBodyPart();
						String filename = filePath[i];
						FileDataSource fds = new FileDataSource(filename);
						attachFile.setDataHandler(new DataHandler(fds));
						attachFile.setFileName(fds.getName());
						mp.addBodyPart(attachFile);
					}
				}
				// 向Multipart添加MimeMessage
				mimeMessage.setContent(mp);
				mimeMessage.setSentDate(new Date());
			}
		};
		try {
			mailSender.send(mimeMail);
			return true;
		} catch (MailException e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.digitalchina.info.framework.message.mail.service.MailSenderService
	 * #getMapFromInform(java.util.Map)
	 */
	public Map getMapFromInform(Map map) {
		// TODO Auto-generated method stub
		Map newMap = buildAdviceMailInfo(map);
		// newMap.put("type", map.get("type"));
		// newMap.put("object", map.get("object"));
		return newMap;
	}

	/**
	 * 组装邮件相关信息
	 * 
	 * @Methods Name buildAdviceMailInfo
	 * @Create In 2009-3-26 By 张鹏
	 * @param map
	 * @return Map
	 */
	private Map buildAdviceMailInfo(Map map) {
		String toActorIds = null, email = null, creator = null, thisNodeName = "Start",toNodeName = "Next", definationName = "workFlow";
		Map newMap = new HashMap();

		this.logger.info("-----------Start build mail fields --------");
		if (map.get("creator") != null) {
			creator = (String) map.get("creator");
			// 判断发起人为多个邮件地址,并且以;分割的情况
			if (creator.indexOf(",") != -1 && creator.indexOf("@") != -1) {
				String[] cc = creator.split(",");
				String userName = "";
				logger.info("CC User Length " + ": " + cc.length);
				for(int i=1; i<cc.length; i++){
					String item = cc[i].substring(0,cc[i].indexOf("@"));
					UserInfo ui = uid.selectUserByUserName(item);
					userName += ui == null ? "" : (ui.getRealName() == null ? "" : ui.getRealName()) + "(" + (ui.getUserName() == null ? "" : ui.getUserName()) + "),";
				}
				userName = userName.substring(0,userName.length() - 1);
				newMap.put("creator", cc);
				newMap.put("creatorName", userName);
				
				// 判断发起人为一个邮件地址的情况
			} else if (creator.indexOf(",") == -1 && creator.indexOf("@") != -1) {
				String cc = (String) map.get("creator");
				String userName = cc.substring(0,cc.indexOf("@"));
				
				UserInfo ui = uid.selectUserByUserName(userName);
				userName = ui == null ? "" : (ui.getRealName() == null ? "" : ui.getRealName()) + "(" + (ui.getUserName() == null ? "" : ui.getUserName()) + ")";
				logger.info("CC User " + ": " + cc);

				newMap.put("creator", cc);
				newMap.put("creatorName", userName);
				
				// 判断发起人为一个用户名的情况
			} else if (creator.indexOf(",") == -1 && creator.indexOf("@") == -1) {
				UserInfo ui = uid.selectUserByUserName(creator);
				String cc = ui == null ? "" : (ui.getEmail() == null ? "" : ui.getEmail());
				logger.info("CC User " + ": " + cc);
				String creatorName = ui == null ? "" : (ui.getRealName() == null ? "" : ui.getRealName()) + "(" + (ui.getUserName() == null ? "" : ui.getUserName()) + ")";
				
				newMap.put("creator", cc);
				newMap.put("creatorName", creatorName);
				
				// 判断发起人为多个人，并且为;分割的情况
			} else if (creator.indexOf(",") != -1 && creator.indexOf("@") == -1) {
				String[] aCreator = ((String) map.get("creator")).split(",");
				String[] cc = new String[aCreator.length];
				String creatorName = "";
				logger.info("CC User Length " + ": " + cc.length);
				for (int j = 0; j < aCreator.length; j++) {
					UserInfo ui = uid.selectUserByUserName(aCreator[j]);
					cc[j] = ui == null ? "" : (ui.getEmail() == null ? "" : ui.getEmail());
					if(j != 0){
						creatorName += ui == null ? "" : (ui.getRealName() == null ? "" : ui.getRealName()) + "(" + (ui.getUserName() == null ? "" : ui.getUserName()) + "),";
					}
					logger.info("CC User " + ": " + cc[j]);
				}
				creatorName = creatorName.substring(0,creatorName.length() - 1);
				
				newMap.put("creator", cc);
				newMap.put("creatorName", creatorName);
			}
			this.logger.info("creator: " + creator);
		}
		
		if(map.get("thisNodeName") != null){
			thisNodeName = (String)map.get("thisNodeName");
			newMap.put("thisNodeName", thisNodeName);
			this.logger.info("thisNodeName: " + toNodeName);
		}
		
		if (map.get("toNodeName") != null) {
			toNodeName = (String) map.get("toNodeName");
			newMap.put("toNodeName", toNodeName);
			this.logger.info("toNodeName: " + toNodeName);
		}
		if (map.get("definationName") != null) {
			definationName = (String) map.get("definationName");
			newMap.put("definationName", definationName);
			this.logger.info("definationName: " + definationName);
		}

		if (map.get("toActorId") != null) {
			toActorIds = (String) map.get("toActorId");
			this.logger.info("toActorIds: " + toActorIds);
		}
		if (map.get("email") != null) {
			email = (String) map.get("email");
			this.logger.info("email: " + email);
		}

		if (toActorIds != null && email == null) {
			List userList = new ArrayList();
			String[] toActorIdss = toActorIds.split(",");
			for (String toActorId : toActorIdss) {
				UserInfo user = (UserInfo) uid.selectUserByUserName(toActorId);
				userList.add(user);
			}
			newMap.put("UserInfo", userList);
		} else if (email != null) {
			newMap.put("emails", email);
		}
		
		if(map.get("result") != null){
			String result = (String)map.get("result");
			newMap.put("result", result);
		}
		this.logger.info("-----------end build mail fields --------");
		return newMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.digitalchina.info.framework.message.mail.service.MailSenderService
	 * #sendWorkFlowSimplyMail(java.util.Map)
	 */
	public boolean sendWorkFlowSimplyMail(Map smap) {
		// TODO Auto-generated method stub
		Map map = buildAdviceMailInfo(smap);

		String appAddress = PropertiesUtil.getProperties("system.web.url",
				"http://10.1.180.167/b2b");

		String text = PropertiesUtil.getProperties("system.web.mailText",
				"您有一个消息");

		String subject = "";

		this.logger.info("-----------Start build mail to sending----------");
		if (map.get("UserInfo") instanceof List) {
			try {

				List<UserInfo> userInfos = (List<UserInfo>) map.get("UserInfo");
				String[] to = new String[userInfos.size()];
				int i = 0;
				for (UserInfo userInfo : userInfos) {
					if (userInfo.getEmail() != null
							&& !"".equals(userInfo.getEmail())) {
						to[i++] = userInfo.getEmail();
						logger.info("To User" + String.valueOf(i - 1) + ": "
								+ userInfo.getEmail());
					}
				}
				String[] cc = new String[1];
				if (map.get("creator") instanceof UserInfo) {
					cc[0] = ((UserInfo) map.get("creator")).getEmail();
				} else if (map.get("creator") instanceof String) {
					cc[0] = (String) map.get("creator");
				} else if (map.get("creator") instanceof String[]) {
					cc = (String[]) map.get("creator");
				} else if (map.get("creator") instanceof List) {
					List lcc = (List) map.get("creator");
					if (lcc.size() > 0) {
						cc = new String[lcc.size()];
						for (int j = 0; j < lcc.size(); j++) {
							cc[j] = ((UserInfo)lcc).getEmail();
						}
					}
				}
				
				String creatorName = (String) map.get("creatorName");
				String definationName = (String) map.get("definationName");
				String toNode = (String) map.get("toNodeName");
				String thisNode = (String) map.get("thisNodeName");
				String result = (String)map.get("result");
				
				if(map.get("thisNodeName") == null && map.get("toNodeName") != null){
					Object[] arg = new Object[3];
					arg[0] = creatorName;
					arg[1] = definationName;
					arg[2] = toNode; 
					subject = this.getProperties("system.mail.sendmail.mailSubject1",
							"您有一个待处理的{0}提交的{1}的{2}审批");
					subject = PropertiesUtil.format(subject, arg);
					
					text = subject + "。" + text  + appAddress;
					
					logger.info("mail context" + ": " + text);
					this.sendSimplyMail(to, cc, null, subject, text);
					logger.info("发送邮件完毕!!\r\n");
					
				}else if(map.get("thisNodeName") != null && map.get("toNodeName") == null){
					Object[] arg = new Object[3];
					arg[0] = creatorName;
					arg[1] = definationName;
					subject = this.getProperties("system.mail.sendmail.mailSubject3",
							"您有一个{0}提交的{1}已经处理完毕");
					subject = PropertiesUtil.format(subject, arg);
					
					text = subject + "。" + text  + appAddress;
					
					logger.info("mail context" + ": " + text);
					this.sendSimplyMail(to, cc, null, subject, text);
					logger.info("发送邮件完毕!!\r\n");
					
				}else{
					Object[] arg = new Object[4];
					arg[0] = creatorName;
					arg[1] = thisNode; 
					arg[2] = definationName;
					arg[3] = toNode; 
					if(WorkflowConstants.RESULT_YES.equals(result)){
						subject = this.getProperties("system.mail.sendmail.mailSubject2",
						"您有一个待处理的{0}提交的{1}同意的{2}的{3}审批");
						subject = PropertiesUtil.format(subject, arg);
						text = subject + "。" + text  + appAddress;
						
						logger.info("mail context" + ": " + text);
						this.sendSimplyMail(to, cc, null, subject, text);
						logger.info("发送邮件完毕!!\r\n");
						
					}else if (WorkflowConstants.RESULT_NO.equals(result)){
						subject = this.getProperties("system.mail.sendmail.mailSubject4",
						"您有一个待处理的{0}提交的{1}驳回的{2}的{3}审批");
						subject = PropertiesUtil.format(subject, arg);
						text = subject + "。" + text  + appAddress;
						
						logger.info("mail context" + ": " + text);
						this.sendSimplyMail(to, cc, null, subject, text);
						logger.info("发送邮件完毕!!\r\n");
					}
					
					
				}
				
				
			} catch (Exception e) {
				logger.error(ExceptionMessageFactory
						.getExceptionMessage("10000310108"));
				logger.error(e.getMessage());
			}
		} else {
			System.out.println("is not SolutionApply!");
			logger.error("Cannot find email address to send mail!");
		}

		this.logger.info("-----------end build mail to sending----------");
		return false;
	}

	public static void main(String[] args) {
		Map a = new HashMap();
		System.out.println(a.get("a"));
	}
}
