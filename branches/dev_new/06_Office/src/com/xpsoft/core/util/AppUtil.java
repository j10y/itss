package com.xpsoft.core.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.xpsoft.core.model.OnlineUser;
import com.xpsoft.core.web.filter.SecurityInterceptorFilter;
import com.xpsoft.oa.model.system.AppFunction;
import com.xpsoft.oa.model.system.AppRole;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Company;
import com.xpsoft.oa.model.system.FunUrl;
import com.xpsoft.oa.model.system.SysConfig;
import com.xpsoft.oa.service.system.AppFunctionService;
import com.xpsoft.oa.service.system.CompanyService;
import com.xpsoft.oa.service.system.FunUrlService;
import com.xpsoft.oa.service.system.SysConfigService;

public class AppUtil implements ApplicationContextAware {
	/* 54 */private static Log logger = LogFactory.getLog(AppUtil.class);

	/* 59 */private static Map configMap = new HashMap();

	/* 63 */private static ServletContext servletContext = null;

	/* 66 */private static Map<String, OnlineUser> onlineUsers = new LinkedHashMap();
	private static ApplicationContext appContext;
	/* 78 */private static Document lefMenuDocument = null;

	/* 83 */private static Document publicDocument = null;

	/* 88 */private static Set<String> publicMenuIds = null;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		/* 71 */appContext = applicationContext;
	}

	public static Document getLeftMenuDocument() {
		/* 92 */return lefMenuDocument;
	}

	public static void setLeftMenuDocument(Document doc) {
		/* 96 */lefMenuDocument = doc;
	}

	public static Document getPublicDocument() {
		/* 101 */return publicDocument;
	}

	public static void setPublicDocument(Document pubDoc) {
		/* 105 */publicDocument = pubDoc;
	}

	public static void setPublicMenuIds(Set<String> pubIds) {
		/* 109 */publicMenuIds = pubIds;
	}

	public static Object getBean(String beanId) {
		/* 118 */return appContext.getBean(beanId);
	}

	public static Map<String, OnlineUser> getOnlineUsers() {
		/* 125 */return onlineUsers;
	}

	public static void removeOnlineUser(String sessionId) {
		/* 132 */onlineUsers.remove(sessionId);
	}

	public static void addOnlineUser(String sessionId, AppUser user) {
		/* 137 */if (!onlineUsers.containsKey(sessionId)) {
			/* 138 */OnlineUser onlineUser = new OnlineUser();
			/* 139 */onlineUser.setFullname(user.getFullname());
			/* 140 */onlineUser.setSessionId(sessionId);
			/* 141 */onlineUser.setUsername(user.getUsername());
			/* 142 */onlineUser.setUserId(user.getUserId());
			/* 143 */if (!user.getUserId().equals(AppUser.SUPER_USER)) {
				/* 144 */onlineUser.setDepPath("."
						+ user.getDepartment().getPath());
			}
			/* 146 */Set<AppRole> roles = user.getRoles();
			/* 147 */StringBuffer roleIds = new StringBuffer(",");
			/* 148 */for (AppRole role : roles) {
				/* 149 */roleIds.append(role.getRoleId() + ",");
			}
			/* 151 */onlineUser.setRoleIds(roleIds.toString());
			/* 152 */onlineUser.setTitle(user.getTitle());
			/* 153 */onlineUsers.put(sessionId, onlineUser);
		}
	}

	public static String getAppAbsolutePath() {
		/* 162 */return servletContext.getRealPath("/");
	}

	public static String getFlowFormAbsolutePath() {
		/* 170 */String path = (String) configMap.get("app.flowFormPath");
		/* 171 */if (path == null)
			path = "/WEB-INF/FlowForm/";
		/* 172 */return getAppAbsolutePath() + path;
	}

	public static void reloadSecurityDataSource() {
		/* 180 */SecurityInterceptorFilter securityInterceptorFilter = (SecurityInterceptorFilter) getBean("securityInterceptorFilter");
		/* 181 */securityInterceptorFilter.loadDataSource();
	}

	public static void init(ServletContext in_servletContext) {
		servletContext = in_servletContext;

		String filePath = servletContext.getRealPath("/WEB-INF/classes/conf/");

		//add by awen for change the config info to type of 'first load database info then load config file infos to override' on 2011-07-08 begin
		reloadSysConfig();
		//add by awen for change the config info to type of 'first load database info then load config file infos to override' on 2011-07-08 end
		Properties props = new Properties();
		try {
			InputStream is = new BufferedInputStream(new FileInputStream(filePath + "/config.properties"));
			props.load(is);
			Iterator it = props.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				configMap.put(key, props.get(key));
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}

		//delete by awen on 2011-07-08 begin
		/**
		 * 此处为清理掉配置文件的信息，从数据库加载配置信息
		 */
		//reloadSysConfig();
		//delete by awen on 2011-07-08 end
		
		CompanyService companyService = (CompanyService) getBean("companyService");
		List cList = companyService.findCompany();
		if (cList.size() > 0) {
			Company company = (Company) cList.get(0);
			configMap.put("app.logoPath", company.getLogo());
			configMap.put("app.companyName", company.getCompanyName());
		}

		String xslStyle = servletContext.getRealPath("/js/menu") + "/menu-left.xsl";
		Document doc = getOrignalMenuDocument();
		try {
			Document finalDoc = XmlUtil.styleDocument(doc, xslStyle);
			setLeftMenuDocument(finalDoc);
		} catch (Exception ex) {
			logger.error("menux.xml trasform has error:" + ex.getMessage());
		}

			String publicStyle = servletContext.getRealPath("/js/menu") + "/menu-public.xsl";
		try {
			Document publicDoc = XmlUtil.styleDocument(doc,	publicStyle);
			HashSet pubIds = new HashSet();
			Element rootEl = publicDoc.getRootElement();
			List idNodes = rootEl.selectNodes("/Menus//*");
			for (int i = 0; i < idNodes.size(); i++) {
				Element el = (Element) idNodes.get(i);
				Attribute attr = el.attribute("id");
				if (attr != null) {
					pubIds.add(attr.getValue());
				}
			}

			setPublicMenuIds(pubIds);
			setPublicDocument(publicDoc);
		} catch (Exception ex) {
			logger.error("menu.xml + menu-public.xsl transform has error:"
							+ ex.getMessage());
		}
	}

	public static Document getOrignalMenuDocument() {
		String menuFilePath = servletContext.getRealPath("/js/menu")
				+ "/menu.xml";
		Document doc = XmlUtil.load(menuFilePath);
		return doc;
	}

	public static Document getGrantMenuDocument() {
		String xslStyle = servletContext.getRealPath("/js/menu") + "/menu-grant.xsl";
		Document finalDoc = null;
		try {
			finalDoc = XmlUtil.styleDocument(getOrignalMenuDocument(), xslStyle);
		} catch (Exception ex) {
			logger.error("menu.xml + menu-grant.xsl transform has error:"
							+ ex.getMessage());
		}
		return finalDoc;
	}

	public static Document getPublicMenuDocument() {
		return publicDocument;
	}

	public static Set<String> getPublicMenuIds() {
		return publicMenuIds;
	}

	public static void synMenu() {
		AppFunctionService appFunctionService = (AppFunctionService) getBean("appFunctionService");
		FunUrlService funUrlService = (FunUrlService) getBean("funUrlService");

		List funNodeList = getOrignalMenuDocument().getRootElement().selectNodes("/Menus/Items//Item/Function");

		for (int i = 0; i < funNodeList.size(); i++) {
			Element funNode = (Element) funNodeList.get(i);

			String key = funNode.attributeValue("id");
			String name = funNode.attributeValue("text");

			AppFunction appFunction = appFunctionService.getByKey(key);

			if (appFunction == null)
				appFunction = new AppFunction(key, name);
			else {
				appFunction.setFunName(name);
			}

			List urlNodes = funNode.selectNodes("./url");

			appFunctionService.save(appFunction);

			for (int k = 0; k < urlNodes.size(); k++) {
				Node urlNode = (Node) urlNodes.get(k);
				String path = urlNode.getText();
				FunUrl fu = funUrlService.getByPathFunId(path, appFunction.getFunctionId());
				if (fu == null) {
					fu = new FunUrl();
					fu.setUrlPath(path);
					fu.setAppFunction(appFunction);
					funUrlService.save(fu);
				}
			}
		}
	}

	public static boolean getIsSynMenu() {
		String synMenu = (String) configMap.get("isSynMenu");

		return "true".equals(synMenu);
	}

	public static Map getSysConfig() {
		return configMap;
	}

	public static void reloadSysConfig() {
		configMap.clear();
		SysConfigService sysConfigService = (SysConfigService) getBean("sysConfigService");
		List<SysConfig> list = sysConfigService.getAll();
		for (SysConfig conf : list)
			configMap.put(conf.getConfigKey(), conf.getDataValue());
	}

	public static String getCompanyLogo() {
		String defaultLogoPath = "/images/xp-logo.png";
		String path = (String) configMap.get("app.logoPath");
		if (StringUtils.isNotEmpty(path)) {
			defaultLogoPath = "/attachFiles/" + path;
		}
		return defaultLogoPath;
	}

	public static String getCompanyName() {
		String defaultName = "北京极限软件有限公司";
		String companyName = (String) configMap.get("app.companyName");
		if (StringUtils.isNotEmpty(companyName)) {
			defaultName = companyName;
		}
		return defaultName;
	}
}
