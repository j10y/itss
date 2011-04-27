package com.zsgj.info.framework.workflow.web.adapter.struts;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmException;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.identity.mail.IdentityAddressResolver;
import org.jbpm.jpdl.el.ELException;
import org.jbpm.jpdl.el.VariableResolver;
import org.jbpm.jpdl.el.impl.JbpmExpressionEvaluator;
import org.jbpm.util.ClassLoaderUtil;
import org.jbpm.util.XmlUtil;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.entity.ConfigUnitMailNodeSender;
import com.zsgj.info.framework.workflow.info.NodeInfo;

public class Mail implements ActionHandler {

	private ConfigUnitService cs = (ConfigUnitService)ContextHolder.getBean("configUnitService");
	private static final long serialVersionUID = 1L;

	String template = null;
	String actors = null;
	String to = null;
	String subject = null;
	String text = null;

	ExecutionContext executionContext = null;

	public Mail() {
	}

	public Mail(String template, String actors, String to, String subject,
			String text) {
		this.template = template;
		this.actors = actors;
		this.to = to;
		this.subject = subject;
		this.text = text;
	}

	public void execute(ExecutionContext ec) {
		this.executionContext = executionContext;
		send(this.getSmtpProps(), this.getFromAddress(), this.getRecipients(ec) , this.getSubject(ec), this.getContent(ec), getUser(), getPassword());
	}
	
	/**
	 * 得到后台配置的相应邮件发送基本信息
	 * @param ec
	 * @return
	 */
	public ConfigUnitMailNodeSender getBackGroundConfigMessage(ExecutionContext ec){
		
		Long virtualDefinintionId = (Long)ec.getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
		NodeInfo nodeInfo = new NodeInfo(ec.getNode());
		String NodeDesc = nodeInfo.getDesc();
		String NodeName = nodeInfo.getNodeName();
		Long nodeId = nodeInfo.getId();
		
		ConfigUnitMailNodeSender mailSender = cs.findConfigUnitMailNodeSenderById(String.valueOf(virtualDefinintionId), String.valueOf(nodeId));
		
		return mailSender;
	}
	
	/**
	 * 得到相应收件人，如果后台没有配置收件人，则发给默认收件人管理员
	 * @param ec
	 * @return
	 */
	public String getRecipients(ExecutionContext ec) {
		
		String recipient = "";//邮件的发送人
		ConfigUnitMailNodeSender mailSender = this.getBackGroundConfigMessage(ec);
		Set<UserInfo> userInfos = mailSender.getUserInfos();
		Iterator users = userInfos.iterator();
		while(users.hasNext()){
			UserInfo userInfo = (UserInfo)users.next();
			String email = userInfo.getEmail();
			if(email!=null&&!"".equals(email)){
				recipient+=userInfo.getEmail();
				recipient+=";";
			}
		}
		if(recipient.endsWith(";")){
			recipient= recipient.substring(0,recipient.length()-1);//这样就把发送人以分号形式形成了
		}
		if(recipient==null||"".equals(recipient)){//如果后台没有配置收件人
			recipient = "flameDemo@digitalchina.com";
		}
		return recipient;
	}
	/**
	 * 得到邮件主题，如果当前后台没有配置邮件信息，则会发送邮件通知管理员告知情况，请其处理
	 * @param ec
	 * @return
	 */
	public String getSubject(ExecutionContext ec) {
		
		String processDesc = ec.getProcessDefinition().getDescription();
		String nodeName = ec.getNode().getName();
		ConfigUnitMailNodeSender mailSender = this.getBackGroundConfigMessage(ec);
		String subject = mailSender.getSubject();//邮件标题
		if(mailSender==null||"".equals(mailSender)){
			subject = "管理员,在"+processDesc+"的"+nodeName+"节点，您并没有配置相应的收信人，请您及时处理";
		}
		return subject;
	}
	/**
	 * 得到邮件主体内容
	 * @param ec
	 * @return
	 */
	public String getContent(ExecutionContext ec) {
		
		ConfigUnitMailNodeSender mailSender = this.getBackGroundConfigMessage(ec);
		String content = mailSender.getContent();//邮件主体
		
		return content;
	}
	/**
	 * 得到发信人，如果配置文件中没有则制定一个默认用户
	 * @return
	 */
	public String getFromAddress() {
		
		String fromAddress = PropertiesUtil.getProperties("system.mail.sendmail.from");
		if(fromAddress==null||"".equals(fromAddress)){
			fromAddress = "tongjp@information.com";
		}
		return fromAddress;
	}

	/**
	 * 
	 * Checks if is new features are enabled
	 * 
	 * @return 
	 * 
	 */
	public static boolean getAdvancedConfig() {
		if (JbpmConfiguration.Configs.hasObject("jbpm.mail.advanced.config")) {
			String config;
			config = JbpmConfiguration.Configs
					.getString("jbpm.mail.advanced.config");
			if (config.compareTo("true") == 0)
				return true;
		}
		return false;
	}

	/**
	 * 得到服务器验证的用户名
	 * @return
	 */
	public static String getUser() {
		
		String userName = PropertiesUtil.getProperties("system.mail.sendmail.userName");
		if("".equals(userName)||userName==null){
			userName = "tongjp";
		}
		return userName;
	}

	/**
	 * 得到服务器验证的密码
	 * @return
	 */
	public static String getPassword() {
		
		String password = PropertiesUtil.getProperties("system.mail.sendmail.passWord");
		if("".equals(password)||password==null){
			password = "000000";
		}
		return password;
	}

	/**
	 * 得到代理服务器的IP地址
	 * @return
	 */
	public String getSmtpServer() {

		String smtpServer = PropertiesUtil.getProperties("system.mail.sendmail.host");
		if("".equals(smtpServer)||smtpServer==null){
			smtpServer = "72.16.1.112";
		}
		return smtpServer;
	}

	/**
	 * 得到javamail必要的属性变量
	 * @return
	 */
	public Properties getSmtpProps() {

		String protocol = PropertiesUtil.getProperties("system.mail.sendmail.protocol");
		Properties props = new Properties();
		props.setProperty("mail.transpost.protocol", protocol);
		props.setProperty("mail.smtp.host", this.getSmtpServer());
		props.setProperty("mail.smtp.auth", "true");
		
		return props;
	}
	/*
	 * 
	 * End new functions
	 * 
	 */

	public void send(ExecutionContext ec) {
	/*********************************读配置文件的配置信息*****************************************************/
		//"smtp.sina.com.cn" smtp.163.com,"172.16.1.112"
		
	/*********************************读配置文件的配置信息*****************************************************/
		//getRecipients()getSubject(), getText()
	}

	public  void send(Properties mailServerProperties,String fromAddress, String recipients, String subject, String text ,String user,String password) {//List recipients
		
		if ((recipients == null) || (recipients.length()==0)) {
			log.debug("skipping mail because there are no recipients");
			return;
		}
		log.debug("sending email to '" + recipients + "' about '" + subject
				+ "'");
		Authenticator auth = new SMTPAuthenticator(user, password);
		Session session = Session.getInstance(mailServerProperties,auth);
		session.setDebug(true);
		MimeMessage message = new MimeMessage(session);//这个session对象用于收集客户端与邮件服务器之间的网络连接信息和定义整个邮件程序所需的环境信息

		try {
			if (fromAddress != null) {
				message.setFrom(new InternetAddress(fromAddress));//这是发件人地址
			}
			String[] mutiRecipient = recipients.split(";");
			for(int i=0 ;i<mutiRecipient.length;i++){
				InternetAddress recipient = new InternetAddress(mutiRecipient[i]);
				message.addRecipient(Message.RecipientType.TO, recipient);//在原有邮件基础上追加一个收件人地址（参数分别是类型和收件人地址）
			}
			if (subject != null) {
				message.setSubject(subject);//设置邮件主题
			}
			if (text != null) {
				message.setText(text);//这是邮件正文
			}
			message.setSentDate(new Date());//设置邮件发送日期
			message.saveChanges();
			/*
			 * 
			 * New features adaptation
			 * START
			 * 
			 */
			String protocol = PropertiesUtil.getProperties("system.mail.sendmail.protocol");
			Transport transport = session.getTransport(protocol);//设置成传输协议名称，并按照协议连接
			transport.connect( getSmtpServer(), user, password);
			transport.send(message);//利用传输协议发送邮件内容
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new JbpmException("couldn't send email", e);
		}
	}

	protected List tokenize(String text) {
		if (text == null) {
			return null;
		}
		List list = new ArrayList();
		StringTokenizer tokenizer = new StringTokenizer(text, ";:");
		while (tokenizer.hasMoreTokens()) {
			list.add(tokenizer.nextToken());
		}
		return list;
	}

	protected Collection resolveAddresses(List actorIds) {
		List emailAddresses = new ArrayList();
		Iterator iter = actorIds.iterator();
		while (iter.hasNext()) {
			String actorId = (String) iter.next();
			IdentityAddressResolver addressResolver = (IdentityAddressResolver) JbpmConfiguration.Configs
					.getObject("jbpm.mail.address.resolver");
			Object resolvedAddresses = addressResolver.resolveAddress(actorId);
			if (resolvedAddresses != null) {
				if (resolvedAddresses instanceof String) {
					emailAddresses.add((String) resolvedAddresses);
				} else if (resolvedAddresses instanceof Collection) {
					emailAddresses.addAll((Collection) resolvedAddresses);
				} else if (resolvedAddresses instanceof String[]) {
					emailAddresses.addAll(Arrays
							.asList((String[]) resolvedAddresses));
				} else {
					throw new JbpmException(
							"Address resolver '"
									+ addressResolver
									+ "' returned '"
									+ resolvedAddresses.getClass().getName()
									+ "' instead of a String, Collection or String-array: "
									+ resolvedAddresses);
				}
			}
		}
		return emailAddresses;
	}

//	Properties getMailServerProperties() {
//		Properties mailServerProperties = new Properties();
//
//		if (JbpmConfiguration.Configs.hasObject("resource.mail.properties")) {
//			String mailServerPropertiesResource = JbpmConfiguration.Configs
//					.getString("resource.mail.properties");
//			try {
//				InputStream mailServerStream = ClassLoaderUtil
//						.getStream(mailServerPropertiesResource);
//				mailServerProperties.load(mailServerStream);
//			} catch (Exception e) {
//				throw new JbpmException(
//						"couldn't get configuration properties for jbpm mail server from resource '"
//								+ mailServerPropertiesResource + "'", e);
//			}
//
//		} else if (JbpmConfiguration.Configs.hasObject("jbpm.mail.smtp.host")) {
//			String smtpServer = JbpmConfiguration.Configs
//					.getString("jbpm.mail.smtp.host");
//			mailServerProperties.put("mail.smtp.host", smtpServer);
//			/*
//			 * 
//			 * New features adaptation
//			 * START
//			 * 
//			 */
//			if (getAdvancedConfig()) {
//				String auth = getSmtpAuth();
//				String debug = getDebug();
//				String tls = getSmtpStarttls();
//				String port = getSmtpPort();
//				String sfport = getSocketFactoryPort();
//				String sfclass = getSocketFactoryClass();
//				String sffallback = getSocketFactoryFallback();
//
//				mailServerProperties.put("mail.smtp.auth", auth);
//				mailServerProperties.put("mail.debug", debug);
//				mailServerProperties.put("mail.smtp.starttls.enable", tls);
//				mailServerProperties.put("mail.smtp.port", port);
//				mailServerProperties
//						.put("mail.smtp.socketFactory.port", sfport);
//				mailServerProperties.put("mail.smtp.socketFactory.class",
//						sfclass);
//				mailServerProperties.put("mail.smtp.socketFactory.fallback",
//						sffallback);
//			}
//			/*
//			 * 
//			 * New features adaptation
//			 * END
//			 * 
//			 */
//		} else {
//
//			log.error("couldn't get mail properties");
//		}
//
//		return mailServerProperties;
//	}

	static Map templates = null;
	static Map templateVariables = null;

	synchronized Properties getMailTemplateProperties(String templateName) {
		if (templates == null) {
			templates = new HashMap();
			String mailTemplatesResource = JbpmConfiguration.Configs
					.getString("resource.mail.templates");
			org.w3c.dom.Element mailTemplatesElement = XmlUtil
					.parseXmlResource(mailTemplatesResource)
					.getDocumentElement();
			List mailTemplateElements = XmlUtil.elements(mailTemplatesElement,
					"mail-template");
			Iterator iter = mailTemplateElements.iterator();
			while (iter.hasNext()) {
				org.w3c.dom.Element mailTemplateElement = (org.w3c.dom.Element) iter
						.next();

				Properties templateProperties = new Properties();
				addTemplateProperty(mailTemplateElement, "actors",
						templateProperties);
				addTemplateProperty(mailTemplateElement, "to",
						templateProperties);
				addTemplateProperty(mailTemplateElement, "subject",
						templateProperties);
				addTemplateProperty(mailTemplateElement, "text",
						templateProperties);

				templates.put(mailTemplateElement.getAttribute("name"),
						templateProperties);
			}

			templateVariables = new HashMap();
			List variableElements = XmlUtil.elements(mailTemplatesElement,
					"variable");
			iter = variableElements.iterator();
			while (iter.hasNext()) {
				org.w3c.dom.Element variableElement = (org.w3c.dom.Element) iter
						.next();
				templateVariables.put(variableElement.getAttribute("name"),
						variableElement.getAttribute("value"));
			}
		}
		return (Properties) templates.get(templateName);
	}

	void addTemplateProperty(org.w3c.dom.Element mailTemplateElement,
			String property, Properties templateProperties) {
		org.w3c.dom.Element element = XmlUtil.element(mailTemplateElement,
				property);
		if (element != null) {
			templateProperties.put(property, XmlUtil.getContentText(element));
		}
	}

	String evaluate(String expression) {
		if (expression == null) {
			return null;
		}
		VariableResolver variableResolver = JbpmExpressionEvaluator
				.getUsedVariableResolver();
		if (variableResolver != null) {
			variableResolver = new MailVariableResolver(templateVariables,
					variableResolver);
		}
		return (String) JbpmExpressionEvaluator.evaluate(expression,
				executionContext, variableResolver, null);
	}

	class MailVariableResolver implements VariableResolver, Serializable {
		private static final long serialVersionUID = 1L;
		Map templateVariables = null;
		VariableResolver variableResolver = null;

		public MailVariableResolver(Map templateVariables,
				VariableResolver variableResolver) {
			this.templateVariables = templateVariables;
			this.variableResolver = variableResolver;
		}

		public Object resolveVariable(String pName) throws ELException {
			if ((templateVariables != null)
					&& (templateVariables.containsKey(pName))) {
				return templateVariables.get(pName);
			}
			return variableResolver.resolveVariable(pName);
		}
	}

	private static Log log = LogFactory.getLog(Mail.class);
}

class SMTPAuthenticator extends Authenticator {
	private String username;

	private String password;

	public SMTPAuthenticator(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password);
	}
	
	/**
	 * 
	 * Gets socket factory port from config file
	 * 
	 * @return
	 */
	public String getSocketFactoryPort() {
		if (JbpmConfiguration.Configs
				.hasObject("jbpm.mail.smtp.socketFactory.port")) {
			return JbpmConfiguration.Configs
					.getString("jbpm.mail.smtp.socketFactory.port");
		}
		return null;
	}

	/**
	 * 
	 * Gets socket factory class from config file
	 * 
	 * @return
	 */
	public String getSocketFactoryClass() {
		if (JbpmConfiguration.Configs
				.hasObject("jbpm.mail.smtp.socketFactory.class")) {
			return JbpmConfiguration.Configs
					.getString("jbpm.mail.smtp.socketFactory.class");
		}
		return null;
	}

	/**
	 * 
	 * Gets socket factory fallback from config file
	 * 
	 * @return
	 */
	public String getSocketFactoryFallback() {
		if (JbpmConfiguration.Configs
				.hasObject("jbpm.mail.smtp.socketFactory.fallback")) {
			return JbpmConfiguration.Configs
					.getString("jbpm.mail.smtp.socketFactory.fallback");
		}
		return null;
	}
	

	/**
	 * 
	 * Gets smtp start TLS from config file
	 * 
	 * @return
	 */
	public String getSmtpStarttls() {
		if (JbpmConfiguration.Configs
				.hasObject("jbpm.mail.smtp.starttls.enable")) {
			return JbpmConfiguration.Configs
					.getString("jbpm.mail.smtp.starttls.enable");
		}
		return null;
	}

	/**
	 * 
	 * Gets debug mode from config file
	 * 
	 * @return
	 */
	public String getDebug() {
		if (JbpmConfiguration.Configs.hasObject("jbpm.mail.debug")) {
			return JbpmConfiguration.Configs.getString("jbpm.mail.debug");
		}
		return null;
	}


}
