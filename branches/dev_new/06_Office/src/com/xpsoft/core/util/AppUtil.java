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
		/* 189 */servletContext = in_servletContext;

		/* 192 */String filePath = servletContext
				.getRealPath("/WEB-INF/classes/conf/");

		//add by awen for change the config info to type of 'first load database info then load config file infos to override' on 2011-07-08 begin
		reloadSysConfig();
		//add by awen for change the config info to type of 'first load database info then load config file infos to override' on 2011-07-08 end
		/* 194 */Properties props = new Properties();
		try {
			/* 196 */InputStream is = new BufferedInputStream(
					new FileInputStream(filePath + "/config.properties"));
			/* 197 */props.load(is);
			/* 198 */Iterator it = props.keySet().iterator();
			/* 199 */while (it.hasNext()) {
				/* 200 */String key = (String) it.next();
				/* 201 */configMap.put(key, props.get(key));
			}
		} catch (Exception ex) {
			/* 204 */logger.error(ex.getMessage());
		}

		//delete by awen on 2011-07-08 begin
		/**
		 * 此处为清理掉配置文件的信息，从数据库加载配置信息
		 */
		//reloadSysConfig();
		//delete by awen on 2011-07-08 end
		
		/* 211 */CompanyService companyService = (CompanyService) getBean("companyService");
		/* 212 */List cList = companyService.findCompany();
		/* 213 */if (cList.size() > 0) {
			/* 214 */Company company = (Company) cList.get(0);
			/* 215 */configMap.put("app.logoPath", company.getLogo());
			/* 216 */configMap.put("app.companyName", company.getCompanyName());
		}

		/* 219 */String xslStyle = servletContext.getRealPath("/js/menu")
				+ "/menu-left.xsl";
		/* 220 */Document doc = getOrignalMenuDocument();
		try {
			/* 223 */Document finalDoc = XmlUtil.styleDocument(doc, xslStyle);
			/* 224 */setLeftMenuDocument(finalDoc);
		} catch (Exception ex) {
			/* 226 */logger.error("menux.xml trasform has error:"
					+ ex.getMessage());
		}

		/* 230 */String publicStyle = servletContext.getRealPath("/js/menu")
				+ "/menu-public.xsl";
		try {
			/* 232 */Document publicDoc = XmlUtil.styleDocument(doc,
					publicStyle);
			/* 233 */HashSet pubIds = new HashSet();
			/* 234 */Element rootEl = publicDoc.getRootElement();
			/* 235 */List idNodes = rootEl.selectNodes("/Menus//*");
			/* 236 */for (int i = 0; i < idNodes.size(); i++) {
				/* 237 */Element el = (Element) idNodes.get(i);
				/* 238 */Attribute attr = el.attribute("id");
				/* 239 */if (attr != null) {
					/* 240 */pubIds.add(attr.getValue());
				}
			}

			/* 244 */setPublicMenuIds(pubIds);
			/* 245 */setPublicDocument(publicDoc);
		} catch (Exception ex) {
			/* 248 */logger
					.error("menu.xml + menu-public.xsl transform has error:"
							+ ex.getMessage());
		}
	}

	public static Document getOrignalMenuDocument() {
		/* 257 */String menuFilePath = servletContext.getRealPath("/js/menu")
				+ "/menu.xml";
		/* 258 */Document doc = XmlUtil.load(menuFilePath);
		/* 259 */return doc;
	}

	public static Document getGrantMenuDocument() {
		/* 267 */String xslStyle = servletContext.getRealPath("/js/menu")
				+ "/menu-grant.xsl";
		/* 268 */Document finalDoc = null;
		try {
			/* 270 */finalDoc = XmlUtil.styleDocument(getOrignalMenuDocument(),
					xslStyle);
		} catch (Exception ex) {
			/* 272 */logger
					.error("menu.xml + menu-grant.xsl transform has error:"
							+ ex.getMessage());
		}
		/* 274 */return finalDoc;
	}

	public static Document getPublicMenuDocument() {
		/* 282 */return publicDocument;
	}

	public static Set<String> getPublicMenuIds() {
		/* 290 */return publicMenuIds;
	}

	public static void synMenu() {
		/* 294 */AppFunctionService appFunctionService = (AppFunctionService) getBean("appFunctionService");
		/* 295 */FunUrlService funUrlService = (FunUrlService) getBean("funUrlService");

		/* 299 */List funNodeList = getOrignalMenuDocument().getRootElement()
				.selectNodes("/Menus/Items//Item/Function");

		/* 301 */for (int i = 0; i < funNodeList.size(); i++) {
			/* 302 */Element funNode = (Element) funNodeList.get(i);

			/* 304 */String key = funNode.attributeValue("id");
			/* 305 */String name = funNode.attributeValue("text");

			/* 307 */AppFunction appFunction = appFunctionService.getByKey(key);

			/* 309 */if (appFunction == null)
				/* 310 */appFunction = new AppFunction(key, name);
			else {
				/* 312 */appFunction.setFunName(name);
			}

			/* 315 */List urlNodes = funNode.selectNodes("./url");

			/* 317 */appFunctionService.save(appFunction);

			/* 319 */for (int k = 0; k < urlNodes.size(); k++) {
				/* 320 */Node urlNode = (Node) urlNodes.get(k);
				/* 321 */String path = urlNode.getText();
				/* 322 */FunUrl fu = funUrlService.getByPathFunId(path,
						appFunction.getFunctionId());
				/* 323 */if (fu == null) {
					/* 324 */fu = new FunUrl();
					/* 325 */fu.setUrlPath(path);
					/* 326 */fu.setAppFunction(appFunction);
					/* 327 */funUrlService.save(fu);
				}
			}
		}
	}

	public static boolean getIsSynMenu() {
		/* 338 */String synMenu = (String) configMap.get("isSynMenu");

		/* 340 */return "true".equals(synMenu);
	}

	public static Map getSysConfig() {
		/* 349 */return configMap;
	}

	public static void reloadSysConfig() {
		/* 353 */configMap.clear();
		/* 354 */SysConfigService sysConfigService = (SysConfigService) getBean("sysConfigService");
		/* 355 */List<SysConfig> list = sysConfigService.getAll();
		/* 356 */for (SysConfig conf : list)
			/* 357 */configMap.put(conf.getConfigKey(), conf.getDataValue());
	}

	public static String getCompanyLogo() {
		/* 362 */String defaultLogoPath = "/images/ht-logo.png";
		/* 363 */String path = (String) configMap.get("app.logoPath");
		/* 364 */if (StringUtils.isNotEmpty(path)) {
			/* 365 */defaultLogoPath = "/attachFiles/" + path;
		}
		/* 367 */return defaultLogoPath;
	}

	public static String getCompanyName() {
		/* 371 */String defaultName = "北京极限软件有限公司";
		/* 372 */String companyName = (String) configMap.get("app.companyName");
		/* 373 */if (StringUtils.isNotEmpty(companyName)) {
			/* 374 */defaultName = companyName;
		}
		/* 376 */return defaultName;
	}
}
